package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * CashBorrowerDO abc_cash_borrower
 */
public class CashBorrowerDO {
    /**
     * 主键id abc_cash_borrower.cb_id
     */
    private Integer    cbId;

    /**
     * 用户id abc_cash_borrower.cb_user_id
     */
    private Integer    cbUserId;

    /**
     * 募集总额 abc_cash_borrower.cb_total_money
     */
    private BigDecimal cbTotalMoney;

    /**
     * 已还款总额 abc_cash_borrower.cb_total_pay
     */
    private BigDecimal cbTotalPay;

    /**
     * 未还款总额 abc_cash_borrower.cb_total_sett
     */
    private BigDecimal cbTotalSett;

    /**
     * 已还服务费 abc_cash_borrower.cb_pay_fee
     */
    private BigDecimal cbPayFee;

    /**
     * 未还服务费 abc_cash_borrower.cb_not_pay_fee
     */
    private BigDecimal cbNotPayFee;

    /**
     * 已还本金 abc_cash_borrower.cb_pay_money
     */
    private BigDecimal cbPayMoney;

    /**
     * 已还利息 abc_cash_borrower.cb_pay_rate
     */
    private BigDecimal cbPayRate;

    /**
     * 已还罚金 abc_cash_borrower.cb_pay_over_rate
     */
    private BigDecimal cbPayOverRate;

    /**
     * 未还本金 abc_cash_borrower.cb_not_pay_money
     */
    private BigDecimal cbNotPayMoney;

    /**
     * 未还利息 abc_cash_borrower.cb_not_pay_rate
     */
    private BigDecimal cbNotPayRate;

    /**
     * 未还罚金 abc_cash_borrower.cb_not_pay_over_rate
     */
    private BigDecimal cbNotPayOverRate;

    /**
     * 充值总额 abc_cash_borrower.cb_total_charge
     */
    private BigDecimal cbTotalCharge;

    /**
     * 成功提现金额 abc_cash_borrower.cb_out_cash
     */
    private BigDecimal cbOutCash;

    /**
     * 提现到账金额 abc_cash_borrower.cb_to_account
     */
    private BigDecimal cbToAccount;

    /**
     * 提现费用 abc_cash_borrower.cb_out_fee
     */
    private BigDecimal cbOutFee;

    /**
     * 交易记录id abc_cash_borrower.cb_business_record
     */
    private String     cbSeqNo;

    public Integer getCbId() {
        return cbId;
    }

    public void setCbId(Integer cbId) {
        this.cbId = cbId;
    }

    public Integer getCbUserId() {
        return cbUserId;
    }

    public void setCbUserId(Integer cbUserId) {
        this.cbUserId = cbUserId;
    }

    public BigDecimal getCbTotalMoney() {
        return cbTotalMoney;
    }

    public void setCbTotalMoney(BigDecimal cbTotalMoney) {
        this.cbTotalMoney = cbTotalMoney;
    }

    public BigDecimal getCbTotalPay() {
        return cbTotalPay;
    }

    public void setCbTotalPay(BigDecimal cbTotalPay) {
        this.cbTotalPay = cbTotalPay;
    }

    public BigDecimal getCbTotalSett() {
        return cbTotalSett;
    }

    public void setCbTotalSett(BigDecimal cbTotalSett) {
        this.cbTotalSett = cbTotalSett;
    }

    public BigDecimal getCbPayFee() {
        return cbPayFee;
    }

    public void setCbPayFee(BigDecimal cbPayFee) {
        this.cbPayFee = cbPayFee;
    }

    public BigDecimal getCbNotPayFee() {
        return cbNotPayFee;
    }

    public void setCbNotPayFee(BigDecimal cbNotPayFee) {
        this.cbNotPayFee = cbNotPayFee;
    }

    public BigDecimal getCbPayMoney() {
        return cbPayMoney;
    }

    public void setCbPayMoney(BigDecimal cbPayMoney) {
        this.cbPayMoney = cbPayMoney;
    }

    public BigDecimal getCbPayRate() {
        return cbPayRate;
    }

    public void setCbPayRate(BigDecimal cbPayRate) {
        this.cbPayRate = cbPayRate;
    }

    public BigDecimal getCbPayOverRate() {
        return cbPayOverRate;
    }

    public void setCbPayOverRate(BigDecimal cbPayOverRate) {
        this.cbPayOverRate = cbPayOverRate;
    }

    public BigDecimal getCbNotPayMoney() {
        return cbNotPayMoney;
    }

    public void setCbNotPayMoney(BigDecimal cbNotPayMoney) {
        this.cbNotPayMoney = cbNotPayMoney;
    }

    public BigDecimal getCbNotPayRate() {
        return cbNotPayRate;
    }

    public void setCbNotPayRate(BigDecimal cbNotPayRate) {
        this.cbNotPayRate = cbNotPayRate;
    }

    public BigDecimal getCbNotPayOverRate() {
        return cbNotPayOverRate;
    }

    /**
     * @param BigDecimal cbNotPayOverRate
     *            (abc_cash_borrower.cb_not_pay_over_rate )
     */
    public void setCbNotPayOverRate(BigDecimal cbNotPayOverRate) {
        this.cbNotPayOverRate = cbNotPayOverRate;
    }

    public BigDecimal getCbTotalCharge() {
        return cbTotalCharge;
    }

    public void setCbTotalCharge(BigDecimal cbTotalCharge) {
        this.cbTotalCharge = cbTotalCharge;
    }

    public BigDecimal getCbOutCash() {
        return cbOutCash;
    }

    public void setCbOutCash(BigDecimal cbOutCash) {
        this.cbOutCash = cbOutCash;
    }

    public BigDecimal getCbToAccount() {
        return cbToAccount;
    }

    public void setCbToAccount(BigDecimal cbToAccount) {
        this.cbToAccount = cbToAccount;
    }

    public BigDecimal getCbOutFee() {
        return cbOutFee;
    }

    public void setCbOutFee(BigDecimal cbOutFee) {
        this.cbOutFee = cbOutFee;
    }

    public String getCbSeqNo() {
        return cbSeqNo;
    }

    public void setCbBusinessRecord(String cbSeqNo) {
        this.cbSeqNo = cbSeqNo;
    }
}
