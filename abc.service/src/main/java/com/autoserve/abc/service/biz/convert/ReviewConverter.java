package com.autoserve.abc.service.biz.convert;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.autoserve.abc.dao.dataobject.ReviewDO;
import com.autoserve.abc.service.biz.entity.Employee;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.entity.ReviewSendLog;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.ReviewType;
import com.google.common.collect.Lists;

/**
 * @author yuqing.zheng Created on 2014-11-19,21:35
 */
public class ReviewConverter {

    public static ReviewDO toReviewDO(Review review) {
        ReviewDO reviewDO = new ReviewDO();
        if (review == null) {
            return reviewDO;
        }

        reviewDO.setReviewId(review.getReviewId());
        reviewDO.setReviewApplyId(review.getApplyId());
        reviewDO.setReviewType(review.getType().type);
        reviewDO.setReviewInfo(StringUtils.isBlank(review.getInfo()) ? null : review.getInfo());
        reviewDO.setReviewSuspend(review.isSuspend());
        reviewDO.setReviewEnd(review.isEnd());
        reviewDO.setReviewState(review.getState().state);
        reviewDO.setReviewCreateTime(review.getCreateTime());
        reviewDO.setReviewUpdateTime(review.getUpdateTime());
        reviewDO.setReviewVersion(review.getVersion());

        if (review.getCurrRole() != null) {
            reviewDO.setReviewCurrRoleIdx(review.getCurrRole().index);
        }

        if (review.getCurrEmp() != null) {
            reviewDO.setReviewCurrEmpId(review.getCurrEmp().getEmpId());
        }

        if (review.getLastOp() != null) {
            reviewDO.setReviewLastOpLogId(review.getLastOp().getOpLogId());
        }

        if (review.getLastSendLog() != null) {
            reviewDO.setReviewLastSendLogId(review.getLastSendLog().getId());
        }

        if (review.getOriginReview() != null) {
            reviewDO.setReviewOriginReviewId(review.getOriginReview().getReviewId());
        }

        return reviewDO;
    }

    public static Review toReview(ReviewDO reviewDO) {
        Review review = new Review();
        if (reviewDO == null) {
            return review;
        }

        review.setReviewId(reviewDO.getReviewId());
        review.setApplyId(reviewDO.getReviewApplyId());
        review.setEnd(reviewDO.getReviewEnd());
        review.setSuspend(reviewDO.getReviewSuspend());
        review.setInfo(reviewDO.getReviewInfo());
        review.setVersion(reviewDO.getReviewVersion());
        review.setCreateTime(reviewDO.getReviewCreateTime());
        review.setUpdateTime(reviewDO.getReviewUpdateTime());

        Integer originReviewId = reviewDO.getReviewOriginReviewId();
        review.setOriginReview(new Review(originReviewId));

        ReviewType reviewType = ReviewType.valueOf(reviewDO.getReviewType());
        review.setType(reviewType);

        BaseRoleType currRole = BaseRoleType.valueOf(reviewDO.getReviewCurrRoleIdx());
        review.setCurrRole(currRole);

        Integer empId = reviewDO.getReviewCurrEmpId();
        review.setCurrEmp(new Employee(empId == null ? Integer.valueOf(0) : empId));

        ReviewState state = ReviewState.valueOf(reviewDO.getReviewState());
        review.setState(state);

        Integer opLogIdId = reviewDO.getReviewLastOpLogId();
        review.setLastOp(new ReviewOp(opLogIdId == null ? Integer.valueOf(0) : opLogIdId));

        Integer lastSendLogId = reviewDO.getReviewLastSendLogId();
        review.setLastSendLog(new ReviewSendLog(lastSendLogId));

        return review;
    }

    public static List<Review> toReviewList(List<ReviewDO> reviewDOs) {
        if (CollectionUtils.isEmpty(reviewDOs)) {
            return Collections.emptyList();
        }

        List<Review> reviews = Lists.newArrayListWithCapacity(reviewDOs.size());
        for (ReviewDO reviewDO : reviewDOs) {
            Review review = toReview(reviewDO);
            reviews.add(review);
        }

        return reviews;
    }
}
