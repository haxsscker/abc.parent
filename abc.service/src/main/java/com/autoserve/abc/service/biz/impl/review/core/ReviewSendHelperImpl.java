package com.autoserve.abc.service.biz.impl.review.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.entity.ReviewSendLog;
import com.autoserve.abc.service.biz.entity.ReviewSendStatus;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.review.ReviewOpLogService;
import com.autoserve.abc.service.biz.intf.review.ReviewSendLogService;
import com.autoserve.abc.service.biz.intf.review.ReviewSendStatusService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * @author yuqing.zheng Created on 2015-01-03,17:20
 */
@Service
public class ReviewSendHelperImpl implements ReviewSendHelper {
    private static final Logger     logger = LoggerFactory.getLogger(ReviewSendHelperImpl.class);

    @Autowired
    private ReviewSendStatusService sendStatusService;

    @Autowired
    private ReviewService           reviewService;

    @Autowired
    private ReviewSendLogService    sendLogService;

    @Autowired
    private ReviewOpLogService      reviewOpLogService;

    @Autowired
    private LoanQueryService        loanQueryService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult doSend(ReviewOp reviewOp) {
        Review review = reviewOp.getReview();
       // ReviewType tempReviewType = review.getType();
        if (reviewOp.getNextRole() == BaseRoleType.PLATFORM_SERVICE) {
            // 发送平台时需要审核状态为“已通过”,并且审核为已结束
            if (review.getState() != ReviewState.PASS_REVIEW || !review.isEnd()) {
                logger.warn("发送前需要先审核通过, reviewId={}", review.getReviewId());
                return new BaseResult(CommonResultCode.BIZ_ERROR, "未审核通过，不能发送至平台审核！");
            }
            PlainResult<ReviewSendStatus> rSStatus = sendStatusService.queryByReviewId(review.getReviewId());
            if(rSStatus.isSuccess()&&rSStatus.getData().getSendPlatform()){
            	logger.warn("该审核已发送至平台，不能重复发送, reviewId={}", review.getReviewId());
                return new BaseResult(CommonResultCode.BIZ_ERROR, "该审核已发送至平台，不能重复发送！");
            }
        } else {
            //发送到小贷和担保需要审核状态为“待审核”，并且审核为未结束
//            if (review.getState() != ReviewState.PENDING_REVIEW || review.isEnd()) {
//                logger.warn("审核已通过，不能发送, reviewId={}", review.getReviewId());
//                return new BaseResult(CommonResultCode.BIZ_ERROR, "审核已通过，不能发送！");
//            }
       //     review.setType(ReviewType.GUAR_REVIEW);
        }

        // 创建、更新审核发送状态
        BaseResult sendStatusRes = createOrUpdateSendStatus(reviewOp);
        if (!sendStatusRes.isSuccess()) {
            logger.error("创建或更新审核发送状态出错");
            return sendStatusRes;
        }

        // 创建审核操作记录
        BaseResult result = reviewOpLogService.creatReviewOpLog(reviewOp);
        if (!result.isSuccess()) {
            logger.error("创建审核操作记录出错");
            return new BaseResult(CommonResultCode.BIZ_ERROR, "创建审核操作记录出错");
        }

        // 创建审核发送记录
        ReviewSendLog sendLog = createSendLog(reviewOp);

        // 更新审核信息
        review.setLastOp(reviewOp);
        review.setLastSendLog(sendLog);

        // 如果是发送平台审核，该审核已结束不需要更新currRole与currEmp
        if (BaseRoleType.PLATFORM_SERVICE != reviewOp.getNextRole()&&BaseRoleType.INSURANCE_GOVERNMENT != reviewOp.getNextRole()) {
            review.setCurrRole(reviewOp.getNextRole());
            review.setCurrEmp(reviewOp.getNextEmp());
        }

        BaseResult reviewRes = reviewService.updateReview(review);
        if (!reviewRes.isSuccess()) {
            logger.error("更新审核状态出错");
            throw new BusinessException("更新审核状态出错");
        }
//        if (reviewOp.getNextRole() == BaseRoleType.INSURANCE_GOVERNMENT) {
//            // 发送担保审核后将审核类型改为融资审核，以便后面回调不会出错
//        	review.setType(tempReviewType);
//        }
        return BaseResult.SUCCESS;
    }

    /**
     * 创建审核发送记录
     */
    private ReviewSendLog createSendLog(ReviewOp reviewOp) {
        ReviewSendLog sendLog = new ReviewSendLog();
        Review review = reviewOp.getReview();

        sendLog.setReview(reviewOp.getReview());
        sendLog.setFromRole(reviewOp.getRole());
        sendLog.setFromEmployee(reviewOp.getEmployee());
        sendLog.setToRole(reviewOp.getNextRole());
        sendLog.setToEmployee(reviewOp.getNextEmp());

        // 发送时, prev设置为review的lastSendLog
        sendLog.setPrev(review.getLastSendLog());

        // send_log表中记录的审核version应为当前审核version+1
        // 因为随后会更新审核
        sendLog.setReviewVersion(review.getVersion() + 1);

        // 当不是发送到平台审核时，不需要发起新的项目初审，nextReview字段仍是当前审核
        if (BaseRoleType.PLATFORM_SERVICE != reviewOp.getNextRole()&&BaseRoleType.INSURANCE_GOVERNMENT != reviewOp.getNextRole()) {
            sendLog.setNextReview(review);
        }

        // 无论是否发送到平台审核，都先创建ReviewSendLog
        // 因为新创建项目初审时，需要本次sendLog的ID
        PlainResult<ReviewSendLog> sendLogRes = sendLogService.createReviewSendLog(sendLog);
        if (!sendLogRes.isSuccess()) {
            logger.error("创建ReviewSendLog出错");
            throw new BusinessException("创建ReviewSendLog出错");
        }
        sendLog = sendLogRes.getData();

        // 当发送到平台审核时，发起新的项目初审，
        // 并更新本次创建的ReviewSendLog的nextReview为新创建的项目初审的ID
        if (BaseRoleType.PLATFORM_SERVICE == reviewOp.getNextRole()) {
            Review loanReview = new Review();
            loanReview.setType(ReviewType.LOAN_FIRST_REVIEW);
            loanReview.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
            loanReview.setLastSendLog(sendLog);

            // 如果当前审核是意向审核，新的项目初审的applyID应该是intent的相关loanId
            // 如果当前审核时融资审核，新的项目初审的applyID就是融资审核的applyId
            if (review.getType() == ReviewType.FINANCING_REVIEW) {
                loanReview.setApplyId(review.getApplyId());
            } else if (review.getType() == ReviewType.INTENTION_REVIEW) {
                PlainResult<Loan> loanRes = loanQueryService.queryByIntentApplyId(review.getApplyId());
                if (!loanRes.isSuccess()) {
                    logger.error("未找到意向ID为{}的相关项目", review.getApplyId());
                    throw new BusinessException("未找到相关意向的项目信息");
                }
                loanReview.setApplyId(loanRes.getData().getLoanId());
            } else if (review.getType() == ReviewType.GUAR_REVIEW) {
                PlainResult<Loan> loanRes = loanQueryService.queryByIntentApplyId(review.getApplyId());
                if (!loanRes.isSuccess()) {
                	loanReview.setApplyId(review.getApplyId());
                }else{
                    loanReview.setApplyId(loanRes.getData().getLoanId());
                }
            }

            PlainResult<Review> newReviewRes = reviewService.initiateReview(loanReview, review);
            if (!newReviewRes.isSuccess()) {
                logger.error("发起新的项目初审失败");
                throw new BusinessException("发起新的项目初审失败");
            } else {
                logger.info("发起新的项目初审，reviewId={}", newReviewRes.getData().getReviewId());
            }

            // 更新创建的ReviewSendLog的nextReview
            sendLog.setNextReview(newReviewRes.getData());
            sendLogService.updateReviewSendLog(sendLog);
        }

        
        // 当发送到平台审核时，发起新的项目初审，
        // 并更新本次创建的ReviewSendLog的nextReview为新创建的项目初审的ID
        if (BaseRoleType.INSURANCE_GOVERNMENT == reviewOp.getNextRole()) {
            Review loanReview = new Review();
            loanReview.setType(ReviewType.GUAR_REVIEW);
            loanReview.setCurrRole(BaseRoleType.INSURANCE_GOVERNMENT);
            loanReview.setCurrEmp(reviewOp.getNextEmp());
            loanReview.setLastSendLog(sendLog);
            
            // 如果当前审核是意向审核，新的担保审核的applyID应该是intent的相关loanId
            // 如果当前审核时融资审核，新的担保审核的applyID就是融资审核的applyId
            if (review.getType() == ReviewType.FINANCING_REVIEW) {
                loanReview.setApplyId(review.getApplyId());
            } else if (review.getType() == ReviewType.INTENTION_REVIEW) {
                PlainResult<Loan> loanRes = loanQueryService.queryByIntentApplyId(review.getApplyId());
                if (!loanRes.isSuccess()) {
                    logger.error("未找到意向ID为{}的相关项目", review.getApplyId());
                    throw new BusinessException("未找到相关意向的项目信息");
                }
                loanReview.setApplyId(loanRes.getData().getLoanId());
            }

            PlainResult<Review> newReviewRes = reviewService.initiateReview(loanReview, review);
            if (!newReviewRes.isSuccess()) {
                logger.error("发起新的担保审核失败");
                throw new BusinessException("发起新的担保审核失败");
            } else {
                logger.info("发起新的担保审核，reviewId={}", newReviewRes.getData().getReviewId());
            }

            // 更新创建的ReviewSendLog的nextReview
            sendLog.setNextReview(newReviewRes.getData());
            sendLogService.updateReviewSendLog(sendLog);
        }
        
        return sendLog;
    }

    private BaseResult createOrUpdateSendStatus(ReviewOp reviewOp) {
        switch (reviewOp.getNextRole()) {
            case LOAN_GOVERNMENT: {
                return sendStatusService.sendToLoanGov(reviewOp.getReview().getReviewId());
            }
            case INSURANCE_GOVERNMENT: {
                return sendStatusService.sendToGuarGov(reviewOp.getReview().getReviewId());
            }
            case PLATFORM_SERVICE: {
                return sendStatusService.sendToPlatform(reviewOp.getReview().getReviewId());
            }
        }

        return new BaseResult(CommonResultCode.ILEEGAL_ARGUMENT);
    }

}
