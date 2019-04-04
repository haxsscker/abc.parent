package com.autoserve.abc.web.module.screen.review.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.TransferReviewSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.TransferCheckVOConverter;
import com.autoserve.abc.web.util.VOUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.review.TransferCheckVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 加载转让初审与转让满标审核页面的数据
 *
 * @author yuqing.zheng Created on 2014-12-03,21:05
 */
public class TransferListSearch {
    private static final Logger logger = LoggerFactory.getLogger(TransferListSearch.class);

    @Autowired
    private ReviewService       reviewService;

    @Autowired
    private LoanQueryService    loanQueryService;

    @Autowired
    private TransferLoanService transferService;

    @Autowired
    private UserService         userService;

    public JsonPageVO<TransferCheckVO> execute(@Params TransferReviewSearchDO searchDO,
                                               @Param("page") int page,
                                               @Param("rows") int rows) {
        JsonPageVO<TransferCheckVO> result = new JsonPageVO<TransferCheckVO>();
        PageCondition pageCondition = new PageCondition(page, rows);

        PageResult<Review> reviewRes = reviewService.searchTransferReview(searchDO, pageCondition);
        if (!reviewRes.isSuccess() || CollectionUtils.isEmpty(reviewRes.getData())) {
            logger.info("未找到相关转让审核");
            return VOUtil.emptyPageVO("没有相关审核数据");
        }

        result.setTotal(reviewRes.getTotalCount());
        List<Review> reviews = reviewRes.getData();
        List<Integer> transferIds = Lists.transform(reviews, new Function<Review, Integer>() {
            @Override
            public Integer apply(Review review) {
                return review.getApplyId();
            }
        });

        ListResult<TransferLoan> transRes = transferService.queryByIds(transferIds);
        if (!transRes.isSuccess()) {
            logger.error("未找到相关转让信息, transferIds={}", transferIds);
            return VOUtil.emptyPageVO("没有相关审核数据");
        }
        List<TransferLoan> transfers = transRes.getData();

        List<Integer> loanIds = Lists.transform(transfers, new Function<TransferLoan, Integer>() {
            @Override
            public Integer apply(TransferLoan transferLoan) {
                return transferLoan.getOriginId();
            }
        });
        ListResult<Loan> loanRes = loanQueryService.queryByIds(loanIds);
        if (!loanRes.isSuccess()) {
            logger.error("未找到相关项目信息, loanIds={}", loanIds);
            return VOUtil.emptyPageVO("没有相关审核数据");
        }
        List<Loan> loans = loanRes.getData();

        List<TransferCheckVO> voList = generateTransferCheckVOList(reviews, transfers, loans);
        if (CollectionUtils.isEmpty(voList)) {
            logger.error("生成VO失败");
            return VOUtil.emptyPageVO("没有相关审核数据");
        }

        result.setRows(voList);
        return result;
    }

    private List<TransferCheckVO> generateTransferCheckVOList(List<Review> reviews, List<TransferLoan> transfers, List<Loan> loans) {
        // 构造key为tranfer的ID,value为相应Review的map
        Map<Integer, Review> transferIdReviewMap = Maps.uniqueIndex(reviews, new Function<Review, Integer>() {
            @Override
            public Integer apply(Review review) {
                return review.getApplyId();
            }
        });

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

        // 转让人
        List<Integer> cstIds = Lists.transform(transfers, new Function<TransferLoan, Integer>() {
            @Override
            public Integer apply(TransferLoan transferLoan) {
                return transferLoan.getUserId();
            }
        });
        ListResult<UserDO> cstRes = userService.findByList(cstIds);
        if (!cstRes.isSuccess()) {
            logger.error("查询转让人信息出错");
            return Collections.emptyList();
        }
        List<UserDO> csts = cstRes.getData();

        // 转让人map
        Map<Integer, UserDO> cstIdMap = Maps.uniqueIndex(csts, new Function<UserDO, Integer>() {
            @Override
            public Integer apply(UserDO userDO) {
                return userDO.getUserId();
            }
        });

        // 转让的原始项目map
        Map<Integer, Loan> loanIdMap = Maps.uniqueIndex(loans, new Function<Loan, Integer>() {
            @Override
            public Integer apply(Loan loan) {
                return loan.getLoanId();
            }
        });

        List<TransferCheckVO> voList = Lists.newArrayList();
        for (TransferLoan transfer : transfers) {
            Review review = transferIdReviewMap.get(transfer.getId());
            Loan loan = loanIdMap.get(transfer.getOriginId());
            UserDO user = userIdMap.get(loan.getLoanUserId());
            UserDO cst = cstIdMap.get(transfer.getUserId());

            voList.add(TransferCheckVOConverter.toVO(review, transfer, loan, user, cst));
        }

        return voList;
    }
}
