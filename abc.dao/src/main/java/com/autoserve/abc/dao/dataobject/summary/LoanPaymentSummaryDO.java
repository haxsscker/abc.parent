/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject.summary;

/**
 * 普通标的还款概要信息
 *
 * @author segen189 2014年12月24日 下午4:24:16
 */
public class LoanPaymentSummaryDO {
    /**
     * 普通标id
     */
    private Integer loanId;

    /**
     * 已还期数
     */
    private Integer payedPaymentPeriod;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getPayedPaymentPeriod() {
        return payedPaymentPeriod;
    }

    public void setPayedPaymentPeriod(Integer payedPaymentPeriod) {
        this.payedPaymentPeriod = payedPaymentPeriod;
    }

}
