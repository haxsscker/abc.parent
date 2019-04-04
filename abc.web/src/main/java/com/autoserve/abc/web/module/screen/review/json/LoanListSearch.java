package com.autoserve.abc.web.module.screen.review.json;

import java.util.ArrayList;
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
import com.autoserve.abc.dao.dataobject.GovernmentDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.LoanReviewSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.intf.government.GovernmentService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.GuavaUtil;
import com.autoserve.abc.web.convert.LoanCheckVOConverter;
import com.autoserve.abc.web.util.VOUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.review.LoanCheckVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 项目初审与满标审核页面的搜索功能
 *
 * @author yuqing.zheng Created on 2014-12-22,13:53
 */
public class LoanListSearch {
    private static final Logger logger = LoggerFactory.getLogger(LoanListSearch.class);

    @Autowired
    private ReviewService       reviewService;

    @Autowired
    private LoanQueryService    loanQueryService;

    @Autowired
    private UserService         userService;

    @Autowired
    private GovernmentService   governmentService;

    public JsonPageVO<LoanCheckVO> execute(@Params LoanReviewSearchDO searchDO, @Param("page") int page,
            @Param("rows") int rows) {
        JsonPageVO<LoanCheckVO> result = new JsonPageVO<LoanCheckVO>();
        PageCondition pageCondition = new PageCondition(page, rows);

        PageResult<Review> reviewRes = reviewService.searchLoanReview(searchDO, pageCondition);
        if (!reviewRes.isSuccess() || CollectionUtils.isEmpty(reviewRes.getData())) {
            logger.info("未找到相关审核");
            return VOUtil.emptyPageVO("没有相关审核数据");
        }

        result.setTotal(reviewRes.getTotalCount());
        List<Review> reviews = reviewRes.getData();
        List<Integer> loanIds = Lists.transform(reviews, new Function<Review, Integer>() {
            @Override
            public Integer apply(Review review) {
                return review.getApplyId();
            }
        });

        ListResult<Loan> loanRes = loanQueryService.queryByIds(loanIds);
        if (!loanRes.isSuccess()) {
            logger.error("未找到任何相关项目信息, loanIds={}", loanIds);
            return VOUtil.emptyPageVO("未找到相关审核");
        }
        List<Loan> loans = loanRes.getData();

        Map<Loan, Review> loanReviewMap = Maps.newLinkedHashMap();
        Map<Integer, Review> reviewMap = Maps.uniqueIndex(reviews, new Function<Review, Integer>() {
            @Override
            public Integer apply(Review review) {
                return review.getApplyId();
            }
        });
        for (Loan loan : loans) {
            Review review = reviewMap.get(loan.getLoanId());
            loanReviewMap.put(loan, review);
        }

        List<LoanCheckVO> voList = generateLoanCheckVOList(loanReviewMap);
        if (CollectionUtils.isEmpty(voList)) {
            logger.error("生成VO出错，VO集合为空");
            return VOUtil.emptyPageVO("未找到相关审核");
        }
        voList = GuavaUtil.OrderByParamDate("pro_add_date", voList);
        result.setRows(voList);
        return result;
    }

    private List<LoanCheckVO> generateLoanCheckVOList(Map<Loan, Review> loanReviewMap) {
        List<Loan> loans = new ArrayList<Loan>(loanReviewMap.keySet());

        List<Integer> userIds = Lists.newArrayList();
        List<Integer> govIdsWithNull = Lists.newArrayList();
        for (Loan loan : loans) {
            userIds.add(loan.getLoanUserId());
            govIdsWithNull.add(loan.getLoanGuarGov());
        }

        ListResult<UserDO> userRes = userService.findByList(userIds);
        if (!userRes.isSuccess()) {
            logger.error("未找到项目相关的用户信息");
            return Collections.emptyList();
        }

        List<UserDO> users = userRes.getData();
        Map<Integer, UserDO> userIdMap = Maps.uniqueIndex(users, new Function<UserDO, Integer>() {
            @Override
            public Integer apply(UserDO user) {
                return user.getUserId();
            }
        });

        // 查询项目相关担保机构信息
        List<Integer> govIds = Lists.newArrayList();
        for (Integer govId : govIdsWithNull) {
            if (govId != null) {
                govIds.add(govId);
            }
        }

        List<GovernmentDO> govs = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(govIds)) {
            ListResult<GovernmentDO> govRes = governmentService.findByList(govIds);
            govs = govRes.getData();
        }
        Map<Integer, GovernmentDO> govIdMap = Maps.uniqueIndex(govs, new Function<GovernmentDO, Integer>() {
            @Override
            public Integer apply(GovernmentDO government) {
                return government.getGovId();
            }
        });

        List<LoanCheckVO> loanCheckVOList = Lists.newArrayList();
        for (Loan loan : loans) {
            Review review = loanReviewMap.get(loan);
            UserDO user = userIdMap.get(loan.getLoanUserId());
            GovernmentDO gov = govIdMap.get(loan.getLoanGuarGov());
            LoanCheckVO vo = LoanCheckVOConverter.toVO(loan, user, gov, review);
            loanCheckVOList.add(vo);
        }

        return loanCheckVOList;
    }
}
