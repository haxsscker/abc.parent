package com.autoserve.abc.service.biz.impl.review.core;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.entity.ReviewSendLog;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.intf.employee.EmployeeRoleService;
import com.autoserve.abc.service.biz.intf.review.ReviewOpLogService;
import com.autoserve.abc.service.biz.intf.review.ReviewSendLogService;
import com.autoserve.abc.service.biz.intf.review.ReviewSendStatusService;
import com.autoserve.abc.service.biz.intf.review.ReviewService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.RoleUtil;

/**
 * @author yuqing.zheng Created on 2015-01-05,14:05
 */
@Service
public class ReviewRevokeHelperImpl implements ReviewRevokeHelper {
    private static final Logger     logger = LoggerFactory.getLogger(ReviewRevokeHelperImpl.class);

    @Autowired
    private ReviewSendLogService    sendLogService;

    @Autowired
    private ReviewOpLogService      reviewOpLogService;

    @Autowired
    private ReviewSendStatusService sendStatusService;

    @Autowired
    private ReviewService           reviewService;

    @Autowired
    private EmployeeRoleService     employeeRoleService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResult doRevoke(ReviewOp reviewOp) {
        Review review = reviewOp.getReview();

        // 查询审核最后发送记录
        PlainResult<ReviewSendLog> sendLogRes = sendLogService.queryReviewLastSendLog(review.getReviewId());
        if (!sendLogRes.isSuccess()) {
            logger.error("未查到审核的最后发送记录，不能撤回, 审核ID为{}", review.getReviewId());
            return new BaseResult(CommonResultCode.BIZ_ERROR, "该审核未发送，不能撤回");
        }
        ReviewSendLog lastSendLog = sendLogRes.getData();

        // 撤回校验
        // 1.校验是不是自己发送的
        // 2.校验审核version是否有变化
        boolean check = checkAuthAndVersion(lastSendLog, reviewOp);
        if (!check) {
            logger.warn("审核不可撤回");
            return new BaseResult(CommonResultCode.BIZ_ERROR, "该审核不可撤回");
        }

        // 通过校验 可以撤回
        // 清除审核发送状态表中的发送状态
        removeSendStatus(review, lastSendLog);

        // 判断之前的发送的操作是否新创建了其他审核
        // 如果是，则需要删除
        deleteLoanReviewIfNeeded(lastSendLog);

        // 撤回操作成功，创建操作记录
        createRevokeOpLog(lastSendLog, reviewOp);

        // 更新review状态
        updateReview(review, lastSendLog, reviewOp);

        return BaseResult.SUCCESS;
    }

    /**
     * 判断能否撤回
     */
    private boolean checkAuthAndVersion(ReviewSendLog sendLog, ReviewOp reviewOp) {
        // 检查最后发送记录中的发送者是否为自己
        boolean check = checkRoleAndEmp(sendLog, reviewOp.getEmployee());
        if (!check) {
            logger.warn("不是该审核的发送者，不能撤回");
            return false;
        }

        // 如果发送记录中review与nextReview相同，则检查当前审核的version与审核最后发送记录中的version是否相同
        // 如果不同，则检查审核最后发送记录中的version与新审核的version是否相同
        Integer reviewId = sendLog.getReview().getReviewId();
        Integer nextReviewId = sendLog.getNextReview().getReviewId();
        if (ObjectUtils.equals(reviewId, nextReviewId)) {
            Integer reviewVer = reviewOp.getReview().getVersion();
            Integer sendLogVer = sendLog.getReviewVersion();
            logger.info("审核当前的version={}，发送记录中的version为{}", reviewVer, sendLogVer);
            return ObjectUtils.equals(reviewVer, sendLogVer);
        } else {
            PlainResult<Review> reviewRes = reviewService.queryById(nextReviewId);
            if (!reviewRes.isSuccess()) {
                logger.error("未查到审核最后发送记录中的nextReview对应的审核信息, 当前审核ID={}, 最后发送记录ID={}，nextReviewId={}", reviewId,
                        sendLog.getId(), nextReviewId);
                throw new BusinessException("撤回操作出错！");
            }

            int newReviewVer = reviewRes.getData().getVersion();
            int sendLogVer = sendLog.getReviewVersion();
            return ObjectUtils.equals(newReviewVer, sendLogVer);
        }
    }

    /**
     * 检查审核发送记录中的发送者是否是自己
     */
    private boolean checkRoleAndEmp(ReviewSendLog sendLog, Employee employee) {
        ListResult<RoleDO> roleListRes = employeeRoleService.queryRoleBySingleEmp(employee.getEmpId());
        if (!roleListRes.isSuccess()) {
            logger.error("找不到当前审核人的角色列表，审核人EmployeeID={}", employee.getEmpId());
            throw new BusinessException("查不到审核人角色列表");
        }
        List<RoleDO> roleList = roleListRes.getData();

        BaseRoleType fromRole = sendLog.getFromRole();
        logger.info("当前审核人角色={}，发送记录中的发出者角色={}", RoleUtil.printRoleList(roleList), fromRole.roleName);

        boolean rolePass = RoleUtil.checkRole(roleList, fromRole);
        // 如果发送者角色是平台 不用继续判断Employee
        if (fromRole == BaseRoleType.PLATFORM_SERVICE) {
            return rolePass;
        } else if (rolePass) {
            Employee fromEmp = sendLog.getFromEmployee();
            logger.info("当前审核人员工ID={}， 发送记录中的发出者ID={}", employee.getEmpId(), fromEmp.getEmpId());
            return fromEmp.getEmpId().equals(employee.getEmpId());
        }

        return false;
    }

    /**
     * 创建撤回操作记录
     *
     * @return 创建的记录主键
     */
    private Integer createRevokeOpLog(ReviewSendLog sendLog, ReviewOp reviewOp) {
        reviewOp.setRole(sendLog.getFromRole());
        // 撤回操作的nextRole与nextEmp都是自己本身
        reviewOp.setNextRole(sendLog.getFromRole());
        reviewOp.setNextEmp(reviewOp.getEmployee());
        PlainResult<Integer> opLogRes = reviewOpLogService.creatReviewOpLog(reviewOp);
        if (!opLogRes.isSuccess()) {
            logger.error("创建审核操作记录失败");
            throw new BusinessException("审核撤回失败");
        }

        return opLogRes.getData();
    }

    /**
     * 清除审核发送状态表中的发送状态
     */
    private void removeSendStatus(Review review, ReviewSendLog sendLog) {
        BaseRoleType role = sendLog.getToRole();
        BaseResult sendStatusRes = sendStatusService.removeReviewSendStatus(role, review.getReviewId());
        if (!sendStatusRes.isSuccess()) {
            logger.error("清除审核发送状态失败");
            throw new BusinessException("退回失败！");
        }
    }

    /**
     * 如果是从项目初审退回，则需要删除响应的圣母初审
     */
    private void deleteLoanReviewIfNeeded(ReviewSendLog lastSendLog) {
        Integer fromReviewId = lastSendLog.getReview().getReviewId();
        Integer nextReviewId = lastSendLog.getNextReview().getReviewId();
        if (!ObjectUtils.equals(fromReviewId, nextReviewId)) {
            // 发送记录中的reviewId与nextReviewId不一致，说明新创建了审核，需要将其删除
            BaseResult delRes = reviewService.deleteReview(nextReviewId);
            if (!delRes.isSuccess()) {
                logger.error("删除审核审核出错，撤回失败！ 需要删除的reviewId={}", nextReviewId);
                throw new BusinessException("撤回操作失败");
            }
        }
    }

    /**
     * 更新审核的状态 1. 将审核的最后发送记录更新为前一条 2. 如果审核未结束，则需更新审核的当前待审状态
     */
    private void updateReview(Review review, ReviewSendLog sendLog, ReviewOp reviewOp) {
        // 将审核的最后发送记录更新为上一条
        ReviewSendLog prevSendLog = sendLog.getPrev();
        if (prevSendLog == null) {
            prevSendLog = ReviewSendLog.NULL_SEND_LOG;
        }
        review.setLastSendLog(prevSendLog);

        // 如果审核未结束
        // 将待审角色与员工更新为发送记录中的发出者
        if (!review.isEnd()) {
            review.setCurrRole(sendLog.getFromRole());
            review.setCurrEmp(sendLog.getFromEmployee());
        }

        // 更新最后操作记录
        review.setLastOp(reviewOp);
        // 撤回之后可以继续审核
        if (review.isEnd() || !review.getState().equals(ReviewState.PENDING_REVIEW)) {
            review.setEnd(false);
            review.setState(ReviewState.PENDING_REVIEW);
        }
        BaseResult updateRes = reviewService.updateReview(review);
        if (!updateRes.isSuccess()) {
            logger.error("更新审核信息出错，撤回失败");
            throw new BusinessException("撤回操作失败！");
        }
    }
}
