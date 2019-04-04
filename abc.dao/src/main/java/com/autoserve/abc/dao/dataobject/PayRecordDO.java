package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * PayRecordDO abc_pay_record
 */
public class PayRecordDO {
    /**
     * 主键id abc_pay_record.pr_id
     */
    private Integer    prId;

    /**
     * 还款计划id abc_pay_record.pr_payment_id
     */
    private Integer    prPaymentId;

    /**
     * 贷款id abc_pay_record.pr_loan_id
     */
    private Integer    prLoanId;

    /**
     * 还款计划期数 abc_pay_record.pr_pay_period
     */
    private Integer    prPayPeriod;

    /**
     * 实还总额 abc_pay_record.pr_pay_total
     */
    private BigDecimal prPayTotal;

    /**
     * 实还本金 abc_pay_record.pr_pay_money
     */
    private BigDecimal prPayMoney;

    /**
     * 实还利息 abc_pay_record.pr_pay_interest
     */
    private BigDecimal prPayInterest;

    /**
     * 实还罚金 abc_pay_record.pr_pay_fine
     */
    private BigDecimal prPayFine;

    /**
     * 实还平台服务费 abc_pay_record.pr_pay_service_fee
     */
    private BigDecimal prPayServiceFee;

    /**
     * 实还担保服务费 abc_pay_record.pr_pay_guar_fee
     */
    private BigDecimal prPayGuarFee;

    /**
     * 剩余罚金 因罚金是时时计算的，当第一次罚金未还清时，保留剩余未还罚金 abc_pay_record.pr_pay_remain_money
     */
    private BigDecimal prPayRemainMoney;

    /**
     * 借款人/投资人 对账表id abc_pay_record.pr_cash_id
     */
    private Integer    prCashId;

    /**
     * 还款状态 1：正常还款 2：平台代还 3：强制还款 abc_pay_record.pr_pay_type
     */
    private Integer    prPayType;

    /**
     * 实还日期 abc_pay_record.pr_paytime
     */
    private Date       prPaytime;

    public Integer getPrId() {
        return prId;
    }

    public void setPrId(Integer prId) {
        this.prId = prId;
    }

    public Integer getPrPaymentId() {
        return prPaymentId;
    }

    public void setPrPaymentId(Integer prPaymentId) {
        this.prPaymentId = prPaymentId;
    }

    public Integer getPrLoanId() {
        return prLoanId;
    }

    public void setPrLoanId(Integer prLoanId) {
        this.prLoanId = prLoanId;
    }

    public Integer getPrPayPeriod() {
        return prPayPeriod;
    }

    public void setPrPayPeriod(Integer prPayPeriod) {
        this.prPayPeriod = prPayPeriod;
    }

    public BigDecimal getPrPayTotal() {
        return prPayTotal;
    }

    public void setPrPayTotal(BigDecimal prPayTotal) {
        this.prPayTotal = prPayTotal;
    }

    public BigDecimal getPrPayMoney() {
        return prPayMoney;
    }

    public void setPrPayMoney(BigDecimal prPayMoney) {
        this.prPayMoney = prPayMoney;
    }

    public BigDecimal getPrPayInterest() {
        return prPayInterest;
    }

    public void setPrPayInterest(BigDecimal prPayInterest) {
        this.prPayInterest = prPayInterest;
    }

    public BigDecimal getPrPayFine() {
        return prPayFine;
    }

    public void setPrPayFine(BigDecimal prPayFine) {
        this.prPayFine = prPayFine;
    }

    public BigDecimal getPrPayServiceFee() {
        return prPayServiceFee;
    }

    public void setPrPayServiceFee(BigDecimal prPayServiceFee) {
        this.prPayServiceFee = prPayServiceFee;
    }

    public BigDecimal getPrPayGuarFee() {
        return prPayGuarFee;
    }

    public void setPrPayGuarFee(BigDecimal prPayGuarFee) {
        this.prPayGuarFee = prPayGuarFee;
    }

    public BigDecimal getPrPayRemainMoney() {
        return prPayRemainMoney;
    }

    public void setPrPayRemainMoney(BigDecimal prPayRemainMoney) {
        this.prPayRemainMoney = prPayRemainMoney;
    }

    public Integer getPrCashId() {
        return prCashId;
    }

    public void setPrCashId(Integer prCashId) {
        this.prCashId = prCashId;
    }

    public Integer getPrPayType() {
        return prPayType;
    }

    public void setPrPayType(Integer prPayType) {
        this.prPayType = prPayType;
    }

    public Date getPrPaytime() {
        return prPaytime;
    }

    public void setPrPaytime(Date prPaytime) {
        this.prPaytime = prPaytime;
    }
}
