package com.autoserve.abc.web.convert;

import com.autoserve.abc.dao.dataobject.RefundRecordDO;
import com.autoserve.abc.web.vo.refund.RefundRecordVO;

/**
 * 类RefundRecordConverter.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月18日 上午11:52:46
 */
public class RefundRecordVOConverter {

    public static RefundRecordVO toRefundRecord(RefundRecordDO refundRecordDO) {
        RefundRecordVO vo = new RefundRecordVO();
        vo.setPhone_number(refundRecordDO.getRefundUserPhone());
        vo.setRefund_account(refundRecordDO.getRefundAccountNo());
        vo.setReturn_costs(refundRecordDO.getRefundAmount());
        vo.setReturn_reasons(refundRecordDO.getRefundReason());
        vo.setReturns_object(refundRecordDO.getRefundUserId().toString());
        vo.setReturns_id(refundRecordDO.getRefundId());
        vo.setRefund_state(refundRecordDO.getRefundState());
        return vo;

    }
}
