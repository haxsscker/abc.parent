/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.PayType;

/**
 * PaymentPlanDO与PaymentPlan互相转换
 *
 * @author segen189 2014年11月26日 上午10:33:26
 */
public class PaymentPlanConverter {
    public static PaymentPlanDO toPaymentPlanDO(PaymentPlan paymentPlan) {
        if (paymentPlan == null) {
            return null;
        }

        PaymentPlanDO paymentPlanDO = new PaymentPlanDO();

        paymentPlanDO.setPpId(paymentPlan.getId());
        paymentPlanDO.setPpFullTransRecordId(paymentPlan.getFullTransRecordId());
        paymentPlanDO.setPpLoanId(paymentPlan.getLoanId());
        paymentPlanDO.setPpPayCapital(paymentPlan.getPayCapital());
        paymentPlanDO.setPpPayInterest(paymentPlan.getPayInterest());
        paymentPlanDO.setPpPayFine(paymentPlan.getPayFine());
        
        //add by 夏同同  增加违约罚金 20160411
        paymentPlanDO.setPpPayBreachFine(paymentPlan.getPayBreachFine());
        
        paymentPlanDO.setPpPayServiceFee(paymentPlan.getPayServiceFee());
        paymentPlanDO.setPpPayGuarFee(paymentPlan.getPayGuarFee());
        paymentPlanDO.setPpPayTotalMoney(paymentPlan.getPayTotalMoney());
        paymentPlanDO.setPpPayCollectCapital(paymentPlan.getPayCollectCapital());
        paymentPlanDO.setPpPayCollectInterest(paymentPlan.getPayCollectInterest());
        paymentPlanDO.setPpPayCollectFine(paymentPlan.getPayCollectFine());
        
        //add by 夏同同  增加违约罚金 20160411
        paymentPlanDO.setPpPayCollectBreachFine(paymentPlan.getPayCollectBreachFine());
        
        paymentPlanDO.setPpCollectServiceFee(paymentPlan.getCollectServiceFee());
        paymentPlanDO.setPpCollectGuarFee(paymentPlan.getCollectGuarFee());
        paymentPlanDO.setPpCollectTotal(paymentPlan.getCollectTotal());
        paymentPlanDO.setPpRemainFine(paymentPlan.getRemainFine());
        
        //add by 夏同同  增加违约罚金 20160411
        paymentPlanDO.setPpRemainBreachFine(paymentPlan.getRemainBreachFine());
        
        paymentPlanDO.setPpLoanPeriod(paymentPlan.getLoanPeriod());
        paymentPlanDO.setPpIsClear(paymentPlan.getIsClear());
        paymentPlanDO.setPpPaytime(paymentPlan.getPaytime());
        paymentPlanDO.setPpCollecttime(paymentPlan.getCollecttime());
        if (paymentPlan.getPayType() != null) {
            paymentPlanDO.setPpPayType(paymentPlan.getPayType().getType());
        }
        if (paymentPlan.getPayState() != null) {
            paymentPlanDO.setPpPayState(paymentPlan.getPayState().getState());
        }
        paymentPlanDO.setPpReplaceState(paymentPlan.getReplaceState());
        paymentPlanDO.setPpLoanee(paymentPlan.getLoanee());
        paymentPlanDO.setPpInnerSeqNo(paymentPlan.getInnerSeqNo());
        return paymentPlanDO;
    }

    public static PaymentPlan toPaymentPlan(PaymentPlanDO paymentPlanDO) {
        if (paymentPlanDO == null) {
            return null;
        }

        PaymentPlan paymentPlan = new PaymentPlan();

        paymentPlan.setId(paymentPlanDO.getPpId());
        paymentPlan.setFullTransRecordId(paymentPlanDO.getPpFullTransRecordId());
        paymentPlan.setLoanId(paymentPlanDO.getPpLoanId());
        paymentPlan.setPayCapital(paymentPlanDO.getPpPayCapital());
        paymentPlan.setPayInterest(paymentPlanDO.getPpPayInterest());
        paymentPlan.setPayFine(paymentPlanDO.getPpPayFine());
        paymentPlan.setPayServiceFee(paymentPlanDO.getPpPayServiceFee());
        paymentPlan.setPayGuarFee(paymentPlanDO.getPpPayGuarFee());
        paymentPlan.setPayTotalMoney(paymentPlanDO.getPpPayTotalMoney());
        paymentPlan.setPayCollectCapital(paymentPlanDO.getPpPayCollectCapital());
        paymentPlan.setPayCollectInterest(paymentPlanDO.getPpPayCollectInterest());
        paymentPlan.setPayCollectFine(paymentPlanDO.getPpPayCollectFine());
        paymentPlan.setCollectServiceFee(paymentPlanDO.getPpCollectServiceFee());
        paymentPlan.setCollectGuarFee(paymentPlanDO.getPpCollectGuarFee());
        paymentPlan.setCollectTotal(paymentPlanDO.getPpCollectTotal());
        paymentPlan.setRemainFine(paymentPlanDO.getPpRemainFine());
        paymentPlan.setLoanPeriod(paymentPlanDO.getPpLoanPeriod());
        paymentPlan.setIsClear(paymentPlanDO.getPpIsClear());
        paymentPlan.setPaytime(paymentPlanDO.getPpPaytime());
        paymentPlan.setCollecttime(paymentPlanDO.getPpCollecttime());
        paymentPlan.setPayType(PayType.valueOf(paymentPlanDO.getPpPayType()));
        paymentPlan.setPayState(PayState.valueOf(paymentPlanDO.getPpPayState()));
        paymentPlan.setReplaceState(paymentPlanDO.getPpReplaceState());
        paymentPlan.setLoanee(paymentPlanDO.getPpLoanee());
        paymentPlan.setInnerSeqNo(paymentPlanDO.getPpInnerSeqNo());
        /**
         * @content:新增违约罚金属性
         * @author:夏同同
         * @date:2016年4月10日 上午14:33:54
         */
        paymentPlan.setPayBreachFine(paymentPlanDO.getPpPayBreachFine());
        paymentPlan.setPayCollectBreachFine(paymentPlanDO.getPpPayCollectBreachFine());
        paymentPlan.setRemainBreachFine(paymentPlanDO.getPpRemainBreachFine());
        return paymentPlan;
    }

    public static List<PaymentPlan> toPaymentPlanList(List<PaymentPlanDO> paymentPlanDOList) {
        if (paymentPlanDOList == null) {
            return null;
        }

        List<PaymentPlan> paymentPlanList = new ArrayList<PaymentPlan>(paymentPlanDOList.size());
        for (PaymentPlanDO paymentPlanDO : paymentPlanDOList) {
            paymentPlanList.add(toPaymentPlan(paymentPlanDO));
        }

        return paymentPlanList;
    }

    public static List<PaymentPlanDO> toPaymentPlanDOList(List<PaymentPlan> paymentPlanList) {
        if (paymentPlanList == null) {
            return null;
        }

        List<PaymentPlanDO> paymentPlanDOList = new ArrayList<PaymentPlanDO>(paymentPlanList.size());
        for (PaymentPlan paymentPlan : paymentPlanList) {
            paymentPlanDOList.add(toPaymentPlanDO(paymentPlan));
        }

        return paymentPlanDOList;
    }
}
