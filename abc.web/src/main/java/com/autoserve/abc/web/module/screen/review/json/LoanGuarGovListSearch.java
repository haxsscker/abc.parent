package com.autoserve.abc.web.module.screen.review.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.LoanGuarGovSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewSendStatus;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.review.ReviewSendStatusService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.GovCheckVOConverter;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.util.VOUtil;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.review.GovCheckVO;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 担保审核与信贷审核页面的数据加载与搜索
 * 
 * @author yuqing.zheng Created on 2015-01-07,15:40
 */
public class LoanGuarGovListSearch {
    private static final Logger     logger = LoggerFactory.getLogger(LoanGuarGovListSearch.class);

    @Autowired
    private ReviewService           reviewService;

    @Autowired
    private LoanQueryService        loanQueryService;

    @Autowired
    private ReviewSendStatusService sendStatusService;

    @Autowired
    private LoanIntentApplyService  intentService;

    @Autowired
    private UserService             userService;

    public JsonPageVO<GovCheckVO> execute(@Params LoanGuarGovSearchDO searchDO, @Param("page") int page,
                                          @Param("rows") int rows, ParameterParser params) {
        JsonPageVO<GovCheckVO> result = new JsonPageVO<GovCheckVO>();
        PageCondition pageCondition = new PageCondition(page, rows);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date timeFrom = params.getDate("applyTimeFrom", dateFormat);
        Date timeTo = params.getDate("applyTimeTo", dateFormat);
        if (timeFrom != null) {
            searchDO.setApplyTimeFrom(timeFrom);
        }
        if (timeTo != null) {
            searchDO.setApplyTimeTo(timeTo);
        }
        // searchDO中填入当前登录人的ID
        Integer empId = LoginUserInfoHelper.getLoginUserInfo().getEmpId();
        searchDO.setCurrEmp(empId);

        PageResult<Review> reviewRes = reviewService.searchLoanGuarReview(searchDO, pageCondition);
        if (!reviewRes.isSuccess() || CollectionUtils.isEmpty(reviewRes.getData())) {
            logger.info("未找到相关审核");
            return VOUtil.emptyPageVO("没有相关审核数据");
        }

        result.setTotal(reviewRes.getTotalCount());

        List<Review> reviews = reviewRes.getData();
        Map<Integer, Review> reviewIdMap = Maps.uniqueIndex(reviews, new Function<Review, Integer>() {
            @Override
            public Integer apply(Review review) {
                return review.getReviewId();
            }
        });

        // 将所有Review对象，按照ReviewType分类放到两个map中，相应的ReviewSendStatus均设为null
        Map<Review, ReviewSendStatus> intentReviewSendMap = Maps.newLinkedHashMap();
        Map<Review, ReviewSendStatus> loanReviewSendMap = Maps.newLinkedHashMap();
        for (Review review : reviews) {
            if (review.getType() == ReviewType.GUAR_REVIEW) {
                loanReviewSendMap.put(review, null);
            } else if (review.getType() == ReviewType.GUAR_REVIEW) {
                intentReviewSendMap.put(review, null);
            }
        }

        // 查询所有审核的发送状态
        // 并将审核发送状态填入相应的Map<Review, ReviewSendStatus>中
        ListResult<ReviewSendStatus> sendRes = sendStatusService.queryByReviewIdList(Lists.newArrayList(reviewIdMap
                .keySet()));
        if (sendRes.isSuccess()) {
            List<ReviewSendStatus> sendStatusList = sendRes.getData();
            for (ReviewSendStatus sendStatus : sendStatusList) {
                Review review = reviewIdMap.get(sendStatus.getReview().getReviewId());
                if (review != null && review.getType() == ReviewType.GUAR_REVIEW) {
                    loanReviewSendMap.put(review, sendStatus);
                } else if (review != null && review.getType() == ReviewType.GUAR_REVIEW) {
                    intentReviewSendMap.put(review, sendStatus);
                }
            }
        }

        // 按两类审核，分别生成VO
        List<GovCheckVO> intentReviewVOs = transformIntentReviews(intentReviewSendMap);
        List<GovCheckVO> loanReviewVOs = transformLoanReviews(loanReviewSendMap);

        // 将结果合并，返回
        List<GovCheckVO> resultVOs = Lists.newArrayList();
        resultVOs.addAll(intentReviewVOs);
        resultVOs.addAll(loanReviewVOs);

        result.setTotal(reviewRes.getTotalCount());
        result.setRows(resultVOs);
        return result;
    }

    /**
     * 将传入的融资审核转换为GovCheckVO
     */
    private List<GovCheckVO> transformLoanReviews(Map<Review, ReviewSendStatus> loanReviewSendMap) {
        List<GovCheckVO> list = Lists.newArrayList();
        if (CollectionUtils.isEmpty(loanReviewSendMap)) {
            logger.info("没有融资审核需要展示");
            return list;
        }

        // 查出审核相关的Loan信息
        List<Review> loanReviews = new ArrayList<Review>(loanReviewSendMap.keySet());
        Map<Review, Loan> reviewLoanMap = buildReviewLoanMap(loanReviews);

        // 查出Loan相关的借款人User信息
        List<Loan> loans = Lists.newArrayList(reviewLoanMap.values());
        Map<Loan, UserDO> loanUserMap = buildLoanUserMap(loans);

        for (Map.Entry<Review, ReviewSendStatus> entry : loanReviewSendMap.entrySet()) {
            Review review = entry.getKey();
            ReviewSendStatus sendStatus = entry.getValue();
            Loan loan = reviewLoanMap.get(review);

            // 由于Loan对象中只有UserID，而没有UserName，所以需要调用UserService查询出借款人姓名
            UserDO userDO = loanUserMap.get(loan);

            list.add(GovCheckVOConverter.toVO(review, loan, userDO, sendStatus));
        }

        return list;
    }

    /**
     * 将传入的意向审核转换为IntentCheckVO
     */
    private List<GovCheckVO> transformIntentReviews(Map<Review, ReviewSendStatus> intentReviewSendMap) {
        List<GovCheckVO> list = Lists.newArrayList();
        if (CollectionUtils.isEmpty(intentReviewSendMap)) {
            logger.info("没有意向核需要展示");
            return list;
        }

        List<Review> intentReviews = new ArrayList<Review>(intentReviewSendMap.keySet());
        Map<Review, Loan> reviewLoanMap = buildReviewLoanMapForIntent(intentReviews);

        List<Loan> loans = Lists.newArrayList(reviewLoanMap.values());
        Map<Loan, UserDO> loanUserMap = buildLoanUserMap(loans);

        for (Map.Entry<Review, ReviewSendStatus> entry : intentReviewSendMap.entrySet()) {
            Review review = entry.getKey();
            ReviewSendStatus sendStatus = entry.getValue();
            Loan loan = reviewLoanMap.get(review);

            // 由于Loan对象中只有UserID，而没有UserName，所以需要调用UserService查询出借款人姓名
            UserDO userDO = loanUserMap.get(loan);

            list.add(GovCheckVOConverter.toVO(review, loan, userDO, sendStatus));
        }

        return list;
    }

    /**
     * 查询融资审核的Loan的信息，并构建map返回
     * 
     * @param loanReviews
     * @return
     */
    private Map<Review, Loan> buildReviewLoanMap(List<Review> loanReviews) {
        Map<Review, Loan> reviewLoanMap = Maps.newHashMap();

        List<Integer> loanIds = Lists.transform(loanReviews, new Function<Review, Integer>() {
            @Override
            public Integer apply(Review review) {
                return review.getApplyId();
            }
        });
        ListResult<Loan> loanRes = loanQueryService.queryByIds(loanIds);
        if (!loanRes.isSuccess()) {
            logger.error("根据Review的applyId未在Loan表中找到相关数据, applyIds={}", loanIds.toString());
            return reviewLoanMap;
        }

        List<Loan> loans = loanRes.getData();
        for (Review review : loanReviews) {
            for (Loan loan : loans) {
                if (review.getApplyId().equals(loan.getLoanId())) {
                    reviewLoanMap.put(review, loan);
                    break;
                }
            }
        }

        return reviewLoanMap;
    }

    /**
     * 查询所有意向审核对应的Loan的信息，并构建map返回
     */
    private Map<Review, Loan> buildReviewLoanMapForIntent(List<Review> intentReviews) {
        Map<Review, Loan> reviewLoanMap = Maps.newHashMap();

        // key为LoanIntentApply的ID，value为Review的map
        Map<Integer, Review> intentIdReviewMap = Maps.uniqueIndex(intentReviews, new Function<Review, Integer>() {
            @Override
            public Integer apply(Review review) {
                return review.getApplyId();
            }
        });
        ListResult<LoanIntentApply> intentRes = intentService.queryByIntentIds(new ArrayList<Integer>(intentIdReviewMap
                .keySet()));
        if (!intentRes.isSuccess()) {
            logger.error("根据Review的applyId未在LoanIntentApply表中找到相关数据, applyIds={}", intentIdReviewMap.keySet()
                    .toString());
            return reviewLoanMap;
        }

        List<LoanIntentApply> intentList = intentRes.getData();
        // key为LoanId，value为LoanIntentApply的ID的map
        Map<Integer, Integer> loanIntentIdMap = Maps.newHashMap();
        for (LoanIntentApply intent : intentList) {
            loanIntentIdMap.put(intent.getLoanId(), intent.getId());
        }
        ListResult<Loan> loanRes = loanQueryService.queryByIds(new ArrayList<Integer>(loanIntentIdMap.keySet()));
        if (!loanRes.isSuccess()) {
            logger.error("根据Review的applyId未在Loan表中找到相关数据, applyIds={}", loanIntentIdMap.keySet().toString());
            return reviewLoanMap;
        }

        List<Loan> loans = loanRes.getData();
        for (Loan loan : loans) {
            Integer intentId = loanIntentIdMap.get(loan.getLoanId());
            if (intentId != null) {
                Review review = intentIdReviewMap.get(intentId);
                if (review != null) {
                    reviewLoanMap.put(review, loan);
                }
            }
        }

        return reviewLoanMap;
    }

    /**
     * 构建Loan与其借款人UserDO的map，用于之后查询Loan的借款人姓名
     * 
     * @param loans
     * @return
     */
    private Map<Loan, UserDO> buildLoanUserMap(List<Loan> loans) {
        Map<Loan, UserDO> loanUserMap = Maps.newHashMap();

        List<Integer> userIdList = Lists.transform(loans, new Function<Loan, Integer>() {
            @Override
            public Integer apply(Loan loan) {
                return loan.getLoanUserId();
            }
        });
        ListResult<UserDO> userRes = userService.findByList(userIdList);
        if (!userRes.isSuccess() || CollectionUtils.isEmpty(userRes.getData())) {
            logger.error("未根据项目用户ID找到用户信息");
            return loanUserMap;
        }

        for (Loan loan : loans) {
            for (UserDO userDO : userRes.getData()) {
                if (loan.getLoanUserId().equals(userDO.getUserId())) {
                    loanUserMap.put(loan, userDO);
                    break;
                }
            }
        }

        return loanUserMap;
    }
}
