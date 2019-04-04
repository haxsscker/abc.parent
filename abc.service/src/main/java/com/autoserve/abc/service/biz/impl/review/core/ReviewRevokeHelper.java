package com.autoserve.abc.service.biz.impl.review.core;

import com.autoserve.abc.service.biz.entity.ReviewOp;
import com.autoserve.abc.service.biz.result.BaseResult;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-12,15:54
 */
public interface ReviewRevokeHelper {
    public BaseResult doRevoke(ReviewOp reviewOp);
}
