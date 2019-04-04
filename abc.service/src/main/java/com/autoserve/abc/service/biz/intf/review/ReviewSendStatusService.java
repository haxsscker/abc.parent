package com.autoserve.abc.service.biz.intf.review;

import com.autoserve.abc.service.biz.entity.ReviewSendStatus;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

import java.util.List;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-03,16:20
 */
public interface ReviewSendStatusService {
    public BaseResult sendToLoanGov(Integer reviewId);

    public BaseResult sendToGuarGov(Integer reviewId);

    public BaseResult sendToPlatform(Integer reviewId);

    public PlainResult<ReviewSendStatus> queryById(Integer id);

    public ListResult<ReviewSendStatus> queryByReviewIdList(List<Integer> reviewIdList);

    public PlainResult<ReviewSendStatus> queryByReviewId(Integer reviewId);

    public BaseResult updateReviewSendStatus(ReviewSendStatus sendStatus);

    public BaseResult removeReviewSendStatus(BaseRoleType toRemove, Integer reviewId);

}
