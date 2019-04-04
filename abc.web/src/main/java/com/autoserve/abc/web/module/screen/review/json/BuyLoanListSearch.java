package com.autoserve.abc.web.module.screen.review.json;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.BuyLoanReviewSearchDO;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.intf.employee.EmployeeRoleService;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.RoleUtil;
import com.autoserve.abc.web.convert.BuyLoanCheckVOConverter;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.util.VOUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.review.BuyLoanCheckVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 加载收购初审与收购满标审核页面的数据
 *
 * @author jyl Created on 2014-12-03,21:05
 */
public class BuyLoanListSearch {
    private static final Logger logger = LoggerFactory.getLogger(BuyLoanListSearch.class);

    @Autowired
    private ReviewService       reviewService;

    @Autowired
    private LoanQueryService    loanQueryService;

    @Autowired
    private BuyLoanService      bugLoanService;

    @Autowired
    private UserService         userService;

    @Autowired
    private EmployeeRoleService empRoleService;

    public JsonPageVO<BuyLoanCheckVO> execute(@Params BuyLoanReviewSearchDO searchDO, @Param("page") int page,
            @Param("rows") int rows) {
        JsonPageVO<BuyLoanCheckVO> result = new JsonPageVO<BuyLoanCheckVO>();
        PageCondition pageCondition = new PageCondition(page, rows);

        if (searchDO.getLoanCategory() != null && searchDO.getLoanCategory() == -1) {
            searchDO.setLoanCategory(null);
        }

        if (searchDO.getApplyState() != null && searchDO.getApplyState() == -1) {
            searchDO.setApplyState(null);
        }

        PageResult<Review> reviewRes = reviewService.searchBuyLoanReview(setRoleAndEmp(searchDO), pageCondition);
        if (!reviewRes.isSuccess() && CollectionUtils.isEmpty(reviewRes.getData())) {
            logger.info("未找到相关收购审核");
            return VOUtil.emptyPageVO("未找到相关审核数据");
        }
        result.setTotal(reviewRes.getTotalCount());

        List<Review> reviews = reviewRes.getData();
        List<Integer> buyLoanIds = Lists.transform(reviews, new Function<Review, Integer>() {
            @Override
            public Integer apply(Review review) {
                return review.getApplyId();
            }
        });

        ListResult<BuyLoan> buyLoanRes = bugLoanService.queryByIds(buyLoanIds);
        if (!buyLoanRes.isSuccess()) {
            logger.info("未找到相关收购信息, buyLoanIds={}", buyLoanIds);
            return VOUtil.emptyPageVO("未找到相关审核数据");
        }
        List<BuyLoan> buyLoans = buyLoanRes.getData();

        List<Integer> loanIds = Lists.transform(buyLoans, new Function<BuyLoan, Integer>() {
            @Override
            public Integer apply(BuyLoan buyLoan) {
                return buyLoan.getOriginId();
            }
        });
        ListResult<Loan> loanRes = loanQueryService.queryByIds(loanIds);
        if (!loanRes.isSuccess()) {
            logger.info("未找到相关项目信息, loanIds={}", loanIds);
            return VOUtil.emptyPageVO("未找到相关审核数据");
        }
        List<Loan> loans = loanRes.getData();
        List<BuyLoanCheckVO> voList = generateBuyLoanCheckVOList(buyLoans, loans);
        if (CollectionUtils.isEmpty(voList)) {
            logger.error("生成VO失败");
            return VOUtil.emptyPageVO("未找到相关审核数据");
        }
        result.setRows(voList);
        return result;
    }

    /**
     * 检查员工角色是否是平台客服或平台财务，如果是，搜索条件需设置审核角色，否则设置员工EmployeeID
     */
    private BuyLoanReviewSearchDO setRoleAndEmp(BuyLoanReviewSearchDO searchDO) {
        LoginUserInfo emp = LoginUserInfoHelper.getLoginUserInfo();
        Integer empId = emp.getEmpId();
        List<RoleDO> roleList = empRoleService.queryRoleBySingleEmp(empId).getData();
        List<BaseRoleType> roles = Lists.newArrayList(BaseRoleType.PLATFORM_SERVICE, BaseRoleType.PLATFORM_FINANCIAL);
        if (RoleUtil.checkRoles(roleList, roles)) {
            searchDO.setReviewCurrRoleIdxList(RoleUtil.toRoleIdxList(roleList));
        } else {
            searchDO.setReviewCurrEmpId(empId);
        }
        return searchDO;
    }

    private List<BuyLoanCheckVO> generateBuyLoanCheckVOList(List<BuyLoan> buyLoans, List<Loan> loans) {
        // 借款人
        List<Integer> userIds = Lists.transform(loans, new Function<Loan, Integer>() {
            @Override
            public Integer apply(Loan loan) {
                return loan.getLoanUserId();
            }
        });
        ListResult<UserDO> userRes = userService.findByList(userIds);
        if (!userRes.isSuccess()) {
            logger.error("查询借款人信息出错");
            return Collections.emptyList();
        }
        List<UserDO> userDOs = userRes.getData();

        // 借款人map
        Map<Integer, UserDO> userIdMap = Maps.uniqueIndex(userDOs, new Function<UserDO, Integer>() {
            @Override
            public Integer apply(UserDO userDO) {
                return userDO.getUserId();
            }
        });

        // 收购人
        List<Integer> cstIds = Lists.transform(buyLoans, new Function<BuyLoan, Integer>() {
            @Override
            public Integer apply(BuyLoan buyLoan) {
                return buyLoan.getUserId();
            }
        });
        ListResult<UserDO> cstRes = userService.findByList(cstIds);
        if (!cstRes.isSuccess()) {
            logger.error("查询收购人信息出错");
            return Collections.emptyList();
        }
        List<UserDO> csts = cstRes.getData();

        // 收购人map
        Map<Integer, UserDO> cstIdMap = Maps.uniqueIndex(csts, new Function<UserDO, Integer>() {
            @Override
            public Integer apply(UserDO userDO) {
                return userDO.getUserId();
            }
        });

        // 收购原始项目map
        Map<Integer, Loan> loanIdMap = Maps.uniqueIndex(loans, new Function<Loan, Integer>() {
            @Override
            public Integer apply(Loan loan) {
                return loan.getLoanId();
            }
        });

        List<BuyLoanCheckVO> voList = Lists.newArrayList();
        for (BuyLoan buyLoan : buyLoans) {
            Loan loan = loanIdMap.get(buyLoan.getOriginId());
            UserDO user = userIdMap.get(loan.getLoanUserId());
            UserDO cst = cstIdMap.get(buyLoan.getUserId());

            voList.add(BuyLoanCheckVOConverter.toBuyLoanCheckVO(buyLoan, loan, user, cst));
        }

        return voList;
    }
}
