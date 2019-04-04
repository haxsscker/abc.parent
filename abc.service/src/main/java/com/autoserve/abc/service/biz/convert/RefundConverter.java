/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.util.Date;

import com.autoserve.abc.dao.dataobject.RefundRecordDO;
import com.autoserve.abc.service.biz.entity.Refund;
import com.autoserve.abc.service.biz.enums.RefundState;
import com.autoserve.abc.service.biz.enums.ReviewState;

/**
 * Refund 与 RefundDO 转换
 * 
 * @author J.YL 2014年11月19日 下午2:13:00
 */
public class RefundConverter {
    public static RefundRecordDO toRefundRecordDO(Refund refund) {
        RefundRecordDO refundRecordDO = new RefundRecordDO();
        if (refund == null) {
            return refundRecordDO;
        }
        refundRecordDO.setRefundAccountNo(refund.getRefundAccountNo());
        refundRecordDO.setRefundAmount(refund.getRefundAmount());
        refundRecordDO.setRefundApplicant(refund.getRefundOperator());
        refundRecordDO.setRefundApplyState(RefundState.PROCESSING.getState());
        refundRecordDO.setRefundReason(refund.getRefundReason());
        refundRecordDO.setRefundUserId(refund.getRefundUserId());
        refundRecordDO.setRefundUserPhone(refund.getRefundUserPhone());
        refundRecordDO.setRefundState(ReviewState.PENDING_REVIEW.getState());
        refundRecordDO.setRefundApplyDate(new Date());
        return refundRecordDO;
    }
}
