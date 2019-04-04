/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.PayType;

/**
 * 还款记录
 *
 * @author segen189 2014年12月20日 下午5:09:49
 */
public class PayRecord {
    /**
     * 主键id
     */
    private Integer    id;

    /**
     * 还款计划
     */
    private Integer    paymentId;

    /**
     * 贷款id
     */
    private Integer    loanId;

    /**
     * 还款计划期数
     */
    private Integer    payPeriod;

    /**
     * 实还总额
     */
    private BigDecimal payTotal;

    /**
     * 实还本金
     */
    private BigDecimal payMoney;

    /**
     * 实还利息
     */
    private BigDecimal payInterest;

    /**
     * 实还罚金
     */
    private BigDecimal payFine;

    /**
     * 实还平台服务费
     */
    private BigDecimal payServiceFee;

    /**
     * 实还担保服务费
     */
    private BigDecimal payGuarFee;

    /**
     * 剩余罚金 因罚金是时时计算的，当第一次罚金未还清时，保留剩余未还罚金
     */
    private BigDecimal payRemainMoney;

    /**
     * 借款人/投资人 对账表id
     */
    private Integer    cashId;

    /**
     * 还款状态 1：正常还款 2：平台代还 3：强制还款
     */
    private PayType  payType;

    /**
     * 实还日期
     */
    private Date       paytime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(Integer payPeriod) {
        this.payPeriod = payPeriod;
    }

    public BigDecimal getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(BigDecimal payTotal) {
        this.payTotal = payTotal;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getPayInterest() {
        return payInterest;
    }

    public void setPayInterest(BigDecimal payInterest) {
        this.payInterest = payInterest;
    }

    public BigDecimal getPayFine() {
        return payFine;
    }

    public void setPayFine(BigDecimal payFine) {
        this.payFine = payFine;
    }

    public BigDecimal getPayServiceFee() {
        return payServiceFee;
    }

    public void setPayServiceFee(BigDecimal payServiceFee) {
        this.payServiceFee = payServiceFee;
    }

    public BigDecimal getPayGuarFee() {
        return payGuarFee;
    }

    public void setPayGuarFee(BigDecimal payGuarFee) {
        this.payGuarFee = payGuarFee;
    }

    public BigDecimal getPayRemainMoney() {
        return payRemainMoney;
    }

    public void setPayRemainMoney(BigDecimal payRemainMoney) {
        this.payRemainMoney = payRemainMoney;
    }

    public Integer getCashId() {
        return cashId;
    }

    public void setCashId(Integer cashId) {
        this.cashId = cashId;
    }

    public PayType getRepayType() {
        return payType;
    }

    public void setRepayType(PayType payType) {
        this.payType = payType;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

}
