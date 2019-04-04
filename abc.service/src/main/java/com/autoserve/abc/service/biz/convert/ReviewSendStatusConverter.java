package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.ReviewSendStatusDO;
import com.autoserve.abc.service.biz.entity.Review;
import com.autoserve.abc.service.biz.entity.ReviewSendStatus;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-03,15:51
 */
public class ReviewSendStatusConverter {
    public static ReviewSendStatusDO toReviewSendStatusDO(ReviewSendStatus reviewSendStatus) {
        ReviewSendStatusDO reviewSendStatusDO = new ReviewSendStatusDO();
        if (reviewSendStatus == null) {
            return reviewSendStatusDO;
        }

        reviewSendStatusDO.setRssId(reviewSendStatus.getId());
        reviewSendStatusDO.setRssSendLoanGov(reviewSendStatus.getSendLoanGov());
        reviewSendStatusDO.setRssSendGuarGov(reviewSendStatus.getSendGuarGov());
        reviewSendStatusDO.setRssSendPlatform(reviewSendStatus.getSendPlatform());

        if (reviewSendStatus.getReview() != null) {
            reviewSendStatusDO.setRssReviewId(reviewSendStatus.getReview().getReviewId());
        }

        return reviewSendStatusDO;
    }

    public static ReviewSendStatus toReviewSendStatus(ReviewSendStatusDO reviewSendStatusDO) {
        ReviewSendStatus reviewSendStatus = new ReviewSendStatus();
        if (reviewSendStatusDO == null) {
            return  reviewSendStatus;
        }

        reviewSendStatus.setId(reviewSendStatusDO.getRssId());
        reviewSendStatus.setSendLoanGov(reviewSendStatusDO.getRssSendLoanGov());
        reviewSendStatus.setSendGuarGov(reviewSendStatusDO.getRssSendGuarGov());
        reviewSendStatus.setSendPlatform(reviewSendStatusDO.getRssSendPlatform());

        Integer reviewId = reviewSendStatusDO.getRssReviewId();
        reviewSendStatus.setReview(new Review(reviewId));

        return reviewSendStatus;
    }

    public static List<ReviewSendStatus> toReviewSendStatusList(List<ReviewSendStatusDO> reviewSendStatusDOList) {
        if (CollectionUtils.isEmpty(reviewSendStatusDOList)) {
            return Collections.emptyList();
        }

        List<ReviewSendStatus> reviewSendStatusList = Lists.newArrayListWithCapacity(reviewSendStatusDOList.size());
        for (ReviewSendStatusDO sendStatusDO : reviewSendStatusDOList) {
            reviewSendStatusList.add(toReviewSendStatus(sendStatusDO));
        }

        return reviewSendStatusList;
    }
}
