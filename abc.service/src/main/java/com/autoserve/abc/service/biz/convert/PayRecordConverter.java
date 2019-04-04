/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.PayRecordDO;
import com.autoserve.abc.service.biz.entity.PayRecord;
import com.autoserve.abc.service.biz.enums.PayType;

/**
 * PaymentPlanDO与PaymentPlan互相转换
 *
 * @author segen189 2014年12月20日 下午5:12:50
 */
public class PayRecordConverter {
    public static PayRecordDO toPayRecordDO(PayRecord payRecord) {
        if (payRecord == null) {
            return null;
        }

        PayRecordDO payRecordDO = new PayRecordDO();

        payRecordDO.setPrId(payRecord.getId());
        payRecordDO.setPrPaymentId(payRecord.getPaymentId());
        payRecordDO.setPrLoanId(payRecord.getLoanId());
        payRecordDO.setPrPayPeriod(payRecord.getPayPeriod());
        payRecordDO.setPrPayTotal(payRecord.getPayTotal());
        payRecordDO.setPrPayMoney(payRecord.getPayMoney());
        payRecordDO.setPrPayInterest(payRecord.getPayInterest());
        payRecordDO.setPrPayFine(payRecord.getPayFine());
        payRecordDO.setPrPayServiceFee(payRecord.getPayServiceFee());
        payRecordDO.setPrPayGuarFee(payRecord.getPayGuarFee());
        payRecordDO.setPrPayRemainMoney(payRecord.getPayRemainMoney());
        payRecordDO.setPrCashId(payRecord.getCashId());
        payRecordDO.setPrPayType(payRecord.getRepayType().getType());
        payRecordDO.setPrPaytime(payRecord.getPaytime());

        return payRecordDO;
    }

    public static PayRecord toPayRecord(PayRecordDO payRecordDO) {
        if (payRecordDO == null) {
            return null;
        }

        PayRecord payRecord = new PayRecord();

        payRecord.setId(payRecordDO.getPrId());
        payRecord.setPaymentId(payRecordDO.getPrPaymentId());
        payRecord.setLoanId(payRecordDO.getPrLoanId());
        payRecord.setPayPeriod(payRecordDO.getPrPayPeriod());
        payRecord.setPayTotal(payRecordDO.getPrPayTotal());
        payRecord.setPayMoney(payRecordDO.getPrPayMoney());
        payRecord.setPayInterest(payRecordDO.getPrPayInterest());
        payRecord.setPayFine(payRecordDO.getPrPayFine());
        payRecord.setPayServiceFee(payRecordDO.getPrPayServiceFee());
        payRecord.setPayGuarFee(payRecordDO.getPrPayGuarFee());
        payRecord.setPayRemainMoney(payRecordDO.getPrPayRemainMoney());
        payRecord.setCashId(payRecordDO.getPrCashId());
        payRecord.setRepayType(PayType.valueOf(payRecordDO.getPrPayType()));
        payRecord.setPaytime(payRecordDO.getPrPaytime());

        return payRecord;
    }

    public static List<PayRecord> toPayRecordList(List<PayRecordDO> payRecordDOList) {
        if (payRecordDOList == null) {
            return null;
        }

        List<PayRecord> payRecordList = new ArrayList<PayRecord>(payRecordDOList.size());
        for (PayRecordDO payRecordDO : payRecordDOList) {
            payRecordList.add(toPayRecord(payRecordDO));
        }

        return payRecordList;
    }
}
