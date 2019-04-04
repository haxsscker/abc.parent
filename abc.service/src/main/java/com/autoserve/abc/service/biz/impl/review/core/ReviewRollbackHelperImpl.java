package com.autoserve.abc.service.biz.impl.review.core;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.entity.ReviewSendLog;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.intf.review.ReviewOpLogService;
import com.autoserve.abc.service.biz.intf.review.ReviewSendLogService;
import com.autoserve.abc.service.biz.intf.review.ReviewSendStatusService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * @author yuqing.zheng Created on 2015-01-05,19:56
 */
@Service
public class ReviewRollbackHelperImpl implements ReviewRollbackHelper {
    private static final Logger     logger = LoggerFactory.getLogger(ReviewRollbackHelperImpl.class);

    @Autowired
    private ReviewOpLogService      reviewOpLogService;

    @Autowired
    private ReviewSendLogService    sendLogService;

    @Autowired
    private ReviewSendStatusService sendStatusService;

    @Autowired
    private ReviewService           reviewService;

    @Autowired
    private EmployeeService         employeeService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult doRollback(ReviewOp reviewOp) {
        Review review = reviewOp.getReview();

        // 判断review的end状态，只有未结束的review才能退回
        if (review.isEnd()) {
            logger.warn("当前审核已结束（已通过或否决），不能退回！reviewId={}", review.getReviewId());
            return new BaseResult(CommonResultCode.BIZ_ERROR, "该审核不能退回");
        }

        // 找出审核最后的发送记录，其发送者即是要退回到的角色
        PlainResult<ReviewSendLog> sendLogRes = sendLogService.queryReviewLastSendLog(review.getReviewId());
        if (!sendLogRes.isSuccess()) {
            logger.error("未找到审核的最后发送记录，reviewId={}", review.getReviewId());
            return new BaseResult(CommonResultCode.ERROR_DATA_EXISTS, "该审核不能退回！");
        }
        ReviewSendLog lastSendLog = sendLogRes.getData();

        BaseRoleType lastRole = lastSendLog.getFromRole();
        Employee lastEmp = lastSendLog.getFromEmployee();

        // 如果上一次操作人不是平台客服(小贷、担保)，需要校验其是否仍然存在
        if (lastRole != BaseRoleType.PLATFORM_SERVICE) {
            int empId = lastEmp.getEmpId();
            PlainResult<Employee> empRes = employeeService.findEntityById(empId);
            if (!empRes.isSuccess() || empRes.getData().getEmpState() == EntityState.STATE_DELETED.state) {
                logger.warn("审核的发送人现已不存在, ReviewId={}, 发送者EmployeeID={}", review.getReviewId(), empId);
                return new BaseResult(CommonResultCode.ERROR_DATA_NOT_EXISTS, "审核发送者不存在，退回失败！");
            }
        }

        // 创建审核操作记录
        reviewOp.setNextRole(lastRole);
        reviewOp.setNextEmp(lastEmp);
        BaseResult res = reviewOpLogService.creatReviewOpLog(reviewOp);
        if (!res.isSuccess()) {
            logger.error("创建审核操作记录出错");
            return new BaseResult(CommonResultCode.BIZ_ERROR, "退回失败！");
        }

        // 清除审核发送状态记录
        removeCurrSendStatus(reviewOp, lastSendLog);

        // 判断是否是从项目初审退回，如果是，需要删除相应的项目初审
        deleteLoanReviewIfNeeded(lastSendLog);

        return updateReviewStatus(reviewOp, lastSendLog);
    }

    /**
     * 清除审核现有的发送状态 从ReviewOp中找到当前审核角色（平台、小贷、担保），从而清楚相应的发送状态
     */
    private void removeCurrSendStatus(ReviewOp reviewOp, ReviewSendLog lastSendLog) {
        // 如果当前审核的lastSendLog中的Review和NextReview相同，则更新当前审核的发送状态
        // 如果不同，则需要更新lastSendLog中的Review的发送状态
        Review review;
        Integer fromReviewId = lastSendLog.getReview().getReviewId();
        Integer nextReviewId = lastSendLog.getNextReview().getReviewId();
        if (ObjectUtils.equals(fromReviewId, nextReviewId)) {
            review = reviewOp.getReview();
        } else {
            review = lastSendLog.getReview();
        }

        BaseRoleType role = reviewOp.getRole();
        BaseResult sendStatusRes = sendStatusService.removeReviewSendStatus(role, review.getReviewId());
        if (!sendStatusRes.isSuccess()) {
            logger.error("清除审核发送状态失败");
            throw new BusinessException("退回失败！");
        }
    }

    /**
     * 如果是从项目初审退回，则需要删除响应的项目初审
     */
    private void deleteLoanReviewIfNeeded(ReviewSendLog lastSendLog) {
        Integer fromReviewId = lastSendLog.getReview().getReviewId();
        Integer nextReviewId = lastSendLog.getNextReview().getReviewId();
        if (!ObjectUtils.equals(fromReviewId, nextReviewId)) {
            // 发送记录中的reviewId与nextReviewId不一致，说明新创建了审核，需要将其删除
            BaseResult delRes = reviewService.deleteReview(nextReviewId);
            if (!delRes.isSuccess()) {
                logger.error("删除审核审核出错，退回失败！ 需要删除的reviewId={}", nextReviewId);
                throw new BusinessException("退回操作失败");
            }
        }
    }

    /**
     * 更新审核状态
     */
    private BaseResult updateReviewStatus(ReviewOp reviewOp, ReviewSendLog lastSendLog) {
        Review review = reviewOp.getReview();
        review.setLastOp(reviewOp);

        // 如果上次发送记录是发送到平台初审，则退回时需要改变审核的待审信息
        // 因为审核已经结束了
        if (lastSendLog.getToRole() != BaseRoleType.PLATFORM_SERVICE) {
            review.setCurrRole(lastSendLog.getFromRole());
            review.setCurrEmp(lastSendLog.getFromEmployee());
        }
        if (review.getType().equals(ReviewType.LOAN_FIRST_REVIEW)) {
            // 退回之后可以继续审核
            Integer lastReviewId = lastSendLog.getReview().getReviewId();
            PlainResult<Review> reviewRes = reviewService.queryById(lastReviewId);
            if (!reviewRes.isSuccess()) {
                logger.error("查询上次审核状态出错");
                throw new BusinessException("查询上次审核状态出错");
            }
            review = reviewRes.getData();
            review.setEnd(false);
            review.setState(ReviewState.PENDING_REVIEW);
            //退到前一个状态时需要将项目状态更改，故此处将前一个审核信息带入callback;
            reviewOp.setReview(review);
        }else if (review.getType().equals(ReviewType.GUAR_REVIEW)) {
        	//删除融资审核记录，解决担保退回时,修改审核类型造成重复的融资审核记录
        	reviewService.deleteReviewByApplyId(review.getApplyId(), ReviewType.FINANCING_REVIEW.type);
            review.setEnd(false);
            review.setState(ReviewState.PENDING_REVIEW);
            review.setType(ReviewType.FINANCING_REVIEW);
            review.setCurrRole(BaseRoleType.PLATFORM_SERVICE);
            //退到前一个状态时需要将项目状态更改，故此处将前一个审核信息带入callback;
            reviewOp.setReview(review);
        }
        BaseResult reviewRes = reviewService.updateReview(review);
        if (!reviewRes.isSuccess()) {
            logger.error("更新审核状态出错");
            throw new BusinessException("更新审核状态出错");
        }

        return BaseResult.SUCCESS;
    }
}
