/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.IncomePlanDO;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.enums.IncomePlanState;

/**
 * IncomePlan与IncomePlanDO转换
 *
 * @author segen189 2014年11月26日 上午10:36:03
 */
public class IncomePlanConverter {
    public static IncomePlanDO toIncomePlanDO(IncomePlan incomePlan) {
        if (incomePlan == null) {
            return null;
        }

        IncomePlanDO incomePlanDO = new IncomePlanDO();

        incomePlanDO.setPiId(incomePlan.getId());
        incomePlanDO.setPiFullTransRecordId(incomePlan.getFullTransRecordId());
        incomePlanDO.setPiPaymentPlanId(incomePlan.getPaymentPlanId());
        incomePlanDO.setPiInvestId(incomePlan.getInvestId());
        incomePlanDO.setPiLoanId(incomePlan.getLoanId());
        incomePlanDO.setPiPayCapital(incomePlan.getPayCapital());
        incomePlanDO.setPiPayInterest(incomePlan.getPayInterest());
        incomePlanDO.setPiPayFine(incomePlan.getPayFine());
        incomePlanDO.setPiPayTotalMoney(incomePlan.getPayTotalMoney());
        incomePlanDO.setPiCollectCapital(incomePlan.getCollectCapital());
        incomePlanDO.setPiCollectInterest(incomePlan.getCollectInterest());
        incomePlanDO.setPiCollectFine(incomePlan.getCollectFine());
        incomePlanDO.setPiCollectTotal(incomePlan.getCollectTotal());
        incomePlanDO.setPiPaytime(incomePlan.getPaytime());
        incomePlanDO.setPiCollecttime(incomePlan.getCollecttime());
        incomePlanDO.setPiRemainFine(incomePlan.getRemainFine());
        incomePlanDO.setPiLoanPeriod(incomePlan.getLoanPeriod());
        if (incomePlan.getIncomePlanState() != null) {
            incomePlanDO.setPiIncomePlanState(incomePlan.getIncomePlanState().getState());
        }
        incomePlanDO.setPiBeneficiary(incomePlan.getBeneficiary());
        incomePlanDO.setPiInnerSeqNo(incomePlan.getInnerSeqNo());
        incomePlanDO.setPiTransferLast(incomePlan.getTransferLast());

        return incomePlanDO;
    }

    public static IncomePlan toIncomePlan(IncomePlanDO incomePlanDO) {
        if (incomePlanDO == null) {
            return null;
        }

        IncomePlan incomePlan = new IncomePlan();

        incomePlan.setId(incomePlanDO.getPiId());
        incomePlan.setFullTransRecordId(incomePlanDO.getPiFullTransRecordId());
        incomePlan.setPaymentPlanId(incomePlanDO.getPiPaymentPlanId());
        incomePlan.setInvestId(incomePlanDO.getPiInvestId());
        incomePlan.setLoanId(incomePlanDO.getPiLoanId());
        incomePlan.setPayCapital(incomePlanDO.getPiPayCapital());
        incomePlan.setPayInterest(incomePlanDO.getPiPayInterest());
        incomePlan.setPayFine(incomePlanDO.getPiPayFine());
        incomePlan.setPayTotalMoney(incomePlanDO.getPiPayTotalMoney());
        incomePlan.setCollectCapital(incomePlanDO.getPiCollectCapital());
        incomePlan.setCollectInterest(incomePlanDO.getPiCollectInterest());
        incomePlan.setCollectFine(incomePlanDO.getPiCollectFine());
        incomePlan.setCollectTotal(incomePlanDO.getPiCollectTotal());
        incomePlan.setPaytime(incomePlanDO.getPiPaytime());
        incomePlan.setCollecttime(incomePlanDO.getPiCollecttime());
        incomePlan.setRemainFine(incomePlanDO.getPiRemainFine());
        incomePlan.setLoanPeriod(incomePlanDO.getPiLoanPeriod());
        incomePlan.setIncomePlanState(IncomePlanState.valueOf(incomePlanDO.getPiIncomePlanState()));
        incomePlan.setBeneficiary(incomePlanDO.getPiBeneficiary());
        incomePlan.setInnerSeqNo(incomePlanDO.getPiInnerSeqNo());
        incomePlan.setCreateDate(incomePlanDO.getPiCreateTime());
        return incomePlan;
    }

    public static List<IncomePlan> toIncomePlanList(List<IncomePlanDO> incomePlanDOList) {
        if (incomePlanDOList == null) {
            return null;
        }

        List<IncomePlan> incomePlanList = new ArrayList<IncomePlan>(incomePlanDOList.size());
        for (IncomePlanDO incomePlanDO : incomePlanDOList) {
            incomePlanList.add(toIncomePlan(incomePlanDO));
        }

        return incomePlanList;
    }

    public static List<IncomePlanDO> toIncomePlanDOList(List<IncomePlan> incomePlanList) {
        if (incomePlanList == null) {
            return null;
        }

        List<IncomePlanDO> incomePlanDOList = new ArrayList<IncomePlanDO>(incomePlanList.size());
        for (IncomePlan incomePlan : incomePlanList) {
            incomePlanDOList.add(toIncomePlanDO(incomePlan));
        }

        return incomePlanDOList;
    }
}
