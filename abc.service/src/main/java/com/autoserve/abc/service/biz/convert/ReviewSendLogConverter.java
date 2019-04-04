package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.ReviewSendLogDO;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewSendLog;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-30,20:37
 */
public class ReviewSendLogConverter {
    public static ReviewSendLog toReviewSendLog(ReviewSendLogDO reviewSendLogDO) {
        ReviewSendLog reviewSendLog = new ReviewSendLog();
        if (reviewSendLogDO == null) {
            return reviewSendLog;
        }

        reviewSendLog.setId(reviewSendLogDO.getRslId());
        reviewSendLog.setReview(new Review(reviewSendLogDO.getRslReviewId()));
        reviewSendLog.setReviewVersion(reviewSendLogDO.getRslReviewVersion());
        reviewSendLog.setPrev(new ReviewSendLog(reviewSendLogDO.getRslPrevId()));
        reviewSendLog.setFromRole(BaseRoleType.valueOf(reviewSendLogDO.getRslFromRole()));
        reviewSendLog.setNextReview(new Review(reviewSendLogDO.getRslNextReviewId()));
        reviewSendLog.setToRole(BaseRoleType.valueOf(reviewSendLogDO.getRslToRole()));
        reviewSendLog.setCreateTime(reviewSendLogDO.getRslCreateTime());
        reviewSendLog.setUpdateTime(reviewSendLogDO.getRslUpdateTime());

        if (reviewSendLogDO.getRslFromEmp() != null) {
            reviewSendLog.setFromEmployee(new Employee(reviewSendLogDO.getRslFromEmp()));
        }

        if (reviewSendLogDO.getRslToEmp() != null) {
            reviewSendLog.setToEmployee(new Employee(reviewSendLogDO.getRslToEmp()));
        }

        return reviewSendLog;
    }

    public static ReviewSendLogDO toReviewSendLogDO(ReviewSendLog reviewSendLog) {
        ReviewSendLogDO reviewSendLogDO = new ReviewSendLogDO();
        if (reviewSendLog == null) {
            return reviewSendLogDO;
        }

        reviewSendLogDO.setRslId(reviewSendLog.getId());
        reviewSendLogDO.setRslReviewId(reviewSendLog.getReview().getReviewId());
        reviewSendLogDO.setRslReviewVersion(reviewSendLog.getReviewVersion());
        reviewSendLogDO.setRslPrevId(reviewSendLog.getPrev().getId());
        reviewSendLogDO.setRslFromRole(reviewSendLog.getFromRole().index);
        if (reviewSendLog.getFromEmployee() != null) {
            reviewSendLogDO.setRslFromEmp(reviewSendLog.getFromEmployee().getEmpId());
        }

        reviewSendLogDO.setRslToRole(reviewSendLog.getToRole().index);
        if (reviewSendLog.getToEmployee() != null) {
            reviewSendLogDO.setRslToEmp(reviewSendLog.getToEmployee().getEmpId());
        }

        if (reviewSendLog.getNextReview() != null) {
            reviewSendLogDO.setRslNextReviewId(reviewSendLog.getNextReview().getReviewId());
        }
        reviewSendLogDO.setRslCreateTime(reviewSendLog.getCreateTime());
        reviewSendLogDO.setRslUpdateTime(reviewSendLog.getUpdateTime());

        return reviewSendLogDO;
    }

    public static List<ReviewSendLog> toReviewSendLogList(List<ReviewSendLogDO> sendLogDOList) {
        if (CollectionUtils.isEmpty(sendLogDOList)) {
            return Collections.emptyList();
        }

        List<ReviewSendLog> sendLogList = Lists.newArrayListWithCapacity(sendLogDOList.size());
        for (ReviewSendLogDO sendLogDO : sendLogDOList) {
            sendLogList.add(toReviewSendLog(sendLogDO));
        }

        return sendLogList;
    }
}
