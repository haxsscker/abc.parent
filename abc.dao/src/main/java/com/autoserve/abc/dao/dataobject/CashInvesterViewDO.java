package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * CashInvesterViewDO abc_view_cash_invester
 */
public class CashInvesterViewDO {
    /**
     * 用户id abc_view_cash_invester.account_user_id
     */
    private Integer    accountUserId;

    /**
     * 用户姓名(企业名称) abc_view_cash_invester.account_user_name
     */
    private String     accountUserName;

    /**
     * 真实姓名 abc_view_cash_invester.user_real_name
     */
    private String     userRealName;

    /**
     * 用户类型 abc_view_cash_invester.account_user_type
     */
    private Integer    accountUserType;
    /**
     * 用户账户
     */
    private String     accountNo;
    /**
     * 应还本金 abc_view_cash_invester.pi_pay_capital
     */
    private BigDecimal piPayCapital;

    /**
     * 应还利息 abc_view_cash_invester.pi_pay_interest
     */
    private BigDecimal piPayInterest;

    /**
     * 应还罚金 abc_view_cash_invester.pi_pay_fine
     */
    private BigDecimal piPayFine;

    /**
     * 应还总额 abc_view_cash_invester.pi_pay_total_money
     */
    private BigDecimal piPayTotalMoney;

    /**
     * 实还本金 abc_view_cash_invester.pi_collect_capital
     */
    private BigDecimal piCollectCapital;

    /**
     * 实还利息 abc_view_cash_invester.pi_collect_interest
     */
    private BigDecimal piCollectInterest;

    /**
     * 实还罚金 abc_view_cash_invester.pi_collect_fine
     */
    private BigDecimal piCollectFine;

    /**
     * 实还总额 abc_view_cash_invester.pi_collect_total
     */
    private BigDecimal piCollectTotal;

    /**
     * 剩余罚金 因罚金是时时计算的，当第一次罚金未还清时，保留剩余未还罚金 abc_view_cash_invester.pi_remain_fine
     */
    private BigDecimal piRemainFine;

    /**
     * 收益计划状态 -2:未激活 -1: 已删除 0:待收益 2:已结清 3:已被转出 4:已被收购
     * abc_view_cash_invester.pi_income_plan_state
     */
    private Integer    piIncomePlanState;

    /**
     * 投资金额 abc_view_cash_invester.in_invest_money
     */
    private BigDecimal inInvestMoney;

    /**
     * 有效投资金额 abc_view_cash_invester.in_valid_invest_money
     */
    private BigDecimal inValidInvestMoney;

    /**
     * 转让有效投资金额 abc_view_cash_invester.in_valid_invest_money
     */
    private BigDecimal inValidInvestMoneyTransfer;

    /**
     * 投资状态 -1：已删除 0：未支付 1:支付失败 2:支付成功 3:已撤资 4:待收益 5:被转让 6:被收购 7:收益完成
     * abc_view_cash_invester.in_invest_state
     */
    private Integer    inInvestState;

    /**
     * 转让债权总额 abc_view_cash_invester.tl_transfer_total
     */
    private BigDecimal tlTransferTotal;

    /**
     * 转让手续费 abc_view_cash_invester.tl_transfer_fee
     */
    private BigDecimal tlTransferFee;

    /**
     * 转让状态 0：待审核 1：初审已通过 2：初审未通过 3：转让中 4：满标待审 5：满标审核通过 6：满标审核未通过 7：已流标 8：划转中
     * 9：已划转 abc_view_cash_invester.tl_state
     */
    private Integer    tlState;

    /**
     * 收购份额 abc_view_cash_invester.bl_money
     */
    private BigDecimal blBuyTotal;

    /**
     * 收购手续费 abc_view_cash_invester.bl_fee
     */
    private BigDecimal blFee;

    /**
     * 收购状态 -1:已删除 1:收购申请待审核 2：收购申请审核通过 3：收购申请审核未通过 4:收购中 5：满标待审 6：满标审核已通过
     * 7：满标审核未通过 8：已流标 9:划转中 10:已划转 abc_view_cash_invester.bl_state
     */
    private Integer    blState;

    /**
     * 转让金额
     */
    private BigDecimal blsTransferMoney;
    /**
     * －1:已删除 1:待认购 2:认购中 3:认购成功 4:认购失败 5:拒绝认购 6:暂时忽略
     */
    private Integer    blsState;

    public Integer getAccountUserId() {
        return accountUserId;
    }

    public void setAccountUserId(Integer accountUserId) {
        this.accountUserId = accountUserId;
    }

    public String getAccountUserName() {
        return accountUserName;
    }

    public void setAccountUserName(String accountUserName) {
        this.accountUserName = accountUserName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public Integer getAccountUserType() {
        return accountUserType;
    }

    public void setAccountUserType(Integer accountUserType) {
        this.accountUserType = accountUserType;
    }

    public BigDecimal getPiPayCapital() {
        return piPayCapital;
    }

    public void setPiPayCapital(BigDecimal piPayCapital) {
        this.piPayCapital = piPayCapital;
    }

    public BigDecimal getPiPayInterest() {
        return piPayInterest;
    }

    public void setPiPayInterest(BigDecimal piPayInterest) {
        this.piPayInterest = piPayInterest;
    }

    public BigDecimal getPiPayFine() {
        return piPayFine;
    }

    public void setPiPayFine(BigDecimal piPayFine) {
        this.piPayFine = piPayFine;
    }

    public BigDecimal getPiPayTotalMoney() {
        return piPayTotalMoney;
    }

    public void setPiPayTotalMoney(BigDecimal piPayTotalMoney) {
        this.piPayTotalMoney = piPayTotalMoney;
    }

    public BigDecimal getPiCollectCapital() {
        return piCollectCapital;
    }

    public void setPiCollectCapital(BigDecimal piCollectCapital) {
        this.piCollectCapital = piCollectCapital;
    }

    public BigDecimal getPiCollectInterest() {
        return piCollectInterest;
    }

    public void setPiCollectInterest(BigDecimal piCollectInterest) {
        this.piCollectInterest = piCollectInterest;
    }

    public BigDecimal getPiCollectFine() {
        return piCollectFine;
    }

    public void setPiCollectFine(BigDecimal piCollectFine) {
        this.piCollectFine = piCollectFine;
    }

    public BigDecimal getPiCollectTotal() {
        return piCollectTotal;
    }

    public void setPiCollectTotal(BigDecimal piCollectTotal) {
        this.piCollectTotal = piCollectTotal;
    }

    public BigDecimal getPiRemainFine() {
        return piRemainFine;
    }

    public void setPiRemainFine(BigDecimal piRemainFine) {
        this.piRemainFine = piRemainFine;
    }

    public Integer getPiIncomePlanState() {
        return piIncomePlanState;
    }

    public void setPiIncomePlanState(Integer piIncomePlanState) {
        this.piIncomePlanState = piIncomePlanState;
    }

    public BigDecimal getInInvestMoney() {
        return inInvestMoney;
    }

    public void setInInvestMoney(BigDecimal inInvestMoney) {
        this.inInvestMoney = inInvestMoney;
    }

    public BigDecimal getInValidInvestMoney() {
        return inValidInvestMoney;
    }

    public void setInValidInvestMoney(BigDecimal inValidInvestMoney) {
        this.inValidInvestMoney = inValidInvestMoney;
    }

    public Integer getInInvestState() {
        return inInvestState;
    }

    public void setInInvestState(Integer inInvestState) {
        this.inInvestState = inInvestState;
    }

    public BigDecimal getTlTransferTotal() {
        return tlTransferTotal;
    }

    public void setTlTransferTotal(BigDecimal tlTransferTotal) {
        this.tlTransferTotal = tlTransferTotal;
    }

    public BigDecimal getTlTransferFee() {
        return tlTransferFee;
    }

    public void setTlTransferFee(BigDecimal tlTransferFee) {
        this.tlTransferFee = tlTransferFee;
    }

    public Integer getTlState() {
        return tlState;
    }

    public void setTlState(Integer tlState) {
        this.tlState = tlState;
    }

    public BigDecimal getBlBuyTotal() {
        return blBuyTotal;
    }

    public void setBlBuyTotal(BigDecimal blBuyMoney) {
        this.blBuyTotal = blBuyMoney;
    }

    public BigDecimal getBlFee() {
        return blFee;
    }

    public void setBlFee(BigDecimal blFee) {
        this.blFee = blFee;
    }

    public Integer getBlState() {
        return blState;
    }

    public void setBlState(Integer blState) {
        this.blState = blState;
    }

    public BigDecimal getBlsTransferMoney() {
        return blsTransferMoney;
    }

    public void setBlsTransferMoney(BigDecimal blsTransferMoney) {
        this.blsTransferMoney = blsTransferMoney;
    }

    public Integer getBlsState() {
        return blsState;
    }

    public void setBlsState(Integer blsState) {
        this.blsState = blsState;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public BigDecimal getInValidInvestMoneyTransfer() {
        return inValidInvestMoneyTransfer;
    }

    public void setInValidInvestMoneyTransfer(BigDecimal inValidInvestMoneyTransfer) {
        this.inValidInvestMoneyTransfer = inValidInvestMoneyTransfer;
    }

}
