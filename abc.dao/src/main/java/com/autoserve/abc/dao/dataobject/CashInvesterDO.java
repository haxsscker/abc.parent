package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * CashInvesterDO abc_cash_invester
 */
public class CashInvesterDO {
    /**
     * 主键id abc_cash_invester.ci_id
     */
    private Integer    ciId;

    /**
     * 用户id abc_cash_invester.ci_user_id
     */
    private Integer    ciUserId;

    /**
     * 资金总额 abc_cash_invester.ci_total_cash
     */
    private BigDecimal ciTotalCash;

    /**
     * 可用余额 abc_cash_invester.ci_useable_money
     */
    private BigDecimal ciUseableMoney;

    /**
     * 回收本金 abc_cash_invester.ci_collect_money
     */
    private BigDecimal ciCollectMoney;

    /**
     * 回收利息 abc_cash_invester.ci_collect_rate
     */
    private BigDecimal ciCollectRate;

    /**
     * 回收罚金 abc_cash_invester.ci_collect_over_rate
     */
    private BigDecimal ciCollectOverRate;

    /**
     * 转让债权 abc_cash_invester.ci_transfer_money
     */
    private BigDecimal ciTransferMoney;

    /**
     * 转让手续费 abc_cash_invester.ci_transfer_fee
     */
    private BigDecimal ciTransferFee;

    /**
     * 买入债权 abc_cash_invester.ci_buy_money
     */
    private BigDecimal ciBuyMoney;

    /**
     * 投资金额 abc_cash_invester.ci_invest_money
     */
    private BigDecimal ciInvestMoney;

    /**
     * 投资冻结金额 abc_cash_invester.ci_invest_frozen
     */
    private BigDecimal ciInvestFrozen;

    /**
     * 充值总额 abc_cash_invester.ci_total_incharge
     */
    private BigDecimal ciTotalIncharge;

    /**
     * 成功提现金额 abc_cash_invester.ci_out_cash
     */
    private BigDecimal ciOutCash;

    /**
     * 提现到账金额 abc_cash_invester.ci_to_account
     */
    private BigDecimal ciToAccount;

    /**
     * 提现费用 abc_cash_invester.ci_out_fee
     */
    private BigDecimal ciOutFee;

    /**
     * 交易记录id abc_cash_invester.ci_business_record
     */
    private String     ciSeqNo;

    public Integer getCiId() {
        return ciId;
    }

    public void setCiId(Integer ciId) {
        this.ciId = ciId;
    }

    public Integer getCiUserId() {
        return ciUserId;
    }

    public void setCiUserId(Integer ciUserId) {
        this.ciUserId = ciUserId;
    }

    public BigDecimal getCiTotalCash() {
        return ciTotalCash;
    }

    public void setCiTotalCash(BigDecimal ciTotalCash) {
        this.ciTotalCash = ciTotalCash;
    }

    public BigDecimal getCiUseableMoney() {
        return ciUseableMoney;
    }

    public void setCiUseableMoney(BigDecimal ciUseableMoney) {
        this.ciUseableMoney = ciUseableMoney;
    }

    public BigDecimal getCiCollectMoney() {
        return ciCollectMoney;
    }

    public void setCiCollectMoney(BigDecimal ciCollectMoney) {
        this.ciCollectMoney = ciCollectMoney;
    }

    public BigDecimal getCiCollectRate() {
        return ciCollectRate;
    }

    public void setCiCollectRate(BigDecimal ciCollectRate) {
        this.ciCollectRate = ciCollectRate;
    }

    public BigDecimal getCiCollectOverRate() {
        return ciCollectOverRate;
    }

    public void setCiCollectOverRate(BigDecimal ciCollectOverRate) {
        this.ciCollectOverRate = ciCollectOverRate;
    }

    public BigDecimal getCiTransferMoney() {
        return ciTransferMoney;
    }

    public void setCiTransferMoney(BigDecimal ciTransferMoney) {
        this.ciTransferMoney = ciTransferMoney;
    }

    public BigDecimal getCiTransferFee() {
        return ciTransferFee;
    }

    public void setCiTransferFee(BigDecimal ciTransferFee) {
        this.ciTransferFee = ciTransferFee;
    }

    public BigDecimal getCiBuyMoney() {
        return ciBuyMoney;
    }

    public void setCiBuyMoney(BigDecimal ciBuyMoney) {
        this.ciBuyMoney = ciBuyMoney;
    }

    public BigDecimal getCiInvestMoney() {
        return ciInvestMoney;
    }

    public void setCiInvestMoney(BigDecimal ciInvestMoney) {
        this.ciInvestMoney = ciInvestMoney;
    }

    public BigDecimal getCiInvestFrozen() {
        return ciInvestFrozen;
    }

    public void setCiInvestFrozen(BigDecimal ciInvestFrozen) {
        this.ciInvestFrozen = ciInvestFrozen;
    }

    public BigDecimal getCiTotalIncharge() {
        return ciTotalIncharge;
    }

    public void setCiTotalIncharge(BigDecimal ciTotalIncharge) {
        this.ciTotalIncharge = ciTotalIncharge;
    }

    public BigDecimal getCiOutCash() {
        return ciOutCash;
    }

    public void setCiOutCash(BigDecimal ciOutCash) {
        this.ciOutCash = ciOutCash;
    }

    public BigDecimal getCiToAccount() {
        return ciToAccount;
    }

    public void setCiToAccount(BigDecimal ciToAccount) {
        this.ciToAccount = ciToAccount;
    }

    public BigDecimal getCiOutFee() {
        return ciOutFee;
    }

    public void setCiOutFee(BigDecimal ciOutFee) {
        this.ciOutFee = ciOutFee;
    }

    public String getCiSeqNo() {
        return ciSeqNo;
    }

    public void setCiSeqNo(String ciSeqNo) {
        this.ciSeqNo = ciSeqNo;
    }
}
