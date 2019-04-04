package com.autoserve.abc.service.biz.intf.review;

import com.autoserve.abc.service.biz.entity.ReviewSendLog;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-03,22:58
 */
public interface ReviewSendLogService {
    PlainResult<ReviewSendLog> createReviewSendLog(ReviewSendLog sendLog);

    PlainResult<ReviewSendLog> queryById(Integer id);

    ListResult<ReviewSendLog> queryByReviewId(Integer reviewId);

    PlainResult<ReviewSendLog> queryReviewLastSendLog(Integer reviewId);

    BaseResult updateReviewSendLog(ReviewSendLog sendLog);
}
