/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Thu Dec 18 15:48:12 CST 2014
 * Description:
 */
package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * CashBorrowerViewDO abc_view_cash_borrower
 */
public class CashBorrowerViewDO {
    /**
     * 应还本金 abc_view_cash_borrower.pp_pay_capital
     */
    private BigDecimal ppPayCapital;

    /**
     * 应还利息 abc_view_cash_borrower.pp_pay_interest
     */
    private BigDecimal ppPayInterest;

    /**
     * 应还罚金 abc_view_cash_borrower.pp_pay_fine
     */
    private BigDecimal ppPayFine;

    /**
     * 应还平台服务费 abc_view_cash_borrower.pp_pay_service_fee
     */
    private BigDecimal ppPayServiceFee;

    /**
     * 应还平台担保费 abc_view_cash_borrower.pp_pay_guar_fee
     */
    private BigDecimal ppPayGuarFee;

    /**
     * 应还总额 abc_view_cash_borrower.pp_pay_total_money
     */
    private BigDecimal ppPayTotalMoney;

    /**
     * 实还本金 abc_view_cash_borrower.pp_pay_collect_capital
     */
    private BigDecimal ppPayCollectCapital;

    /**
     * 实还利息 abc_view_cash_borrower.pp_pay_collect_interest
     */
    private BigDecimal ppPayCollectInterest;

    /**
     * 实还罚金 abc_view_cash_borrower.pp_pay_collect_fine
     */
    private BigDecimal ppPayCollectFine;
    
    /**
     * 实还违约罚金
     * add by 夏同同  20160419
     */
    private BigDecimal ppPayCollectBreachFine;

	/**
     * 实还平台服务费 abc_view_cash_borrower.pp_collect_service_fee
     */
    private BigDecimal ppCollectServiceFee;

    /**
     * 实还担保服务费 abc_view_cash_borrower.pp_collect_guar_fee
     */
    private BigDecimal ppCollectGuarFee;

    /**
     * 实还总额 abc_view_cash_borrower.pp_collect_total
     */
    private BigDecimal ppCollectTotal;

    /**
     * 剩余罚金 因罚金是时时计算的，当第一次罚金未还清时，保留剩余未还罚金 abc_view_cash_borrower.pp_remain_fine
     */
    private BigDecimal ppRemainFine;

    /**
     * 期数 abc_view_cash_borrower.pp_loan_period
     */
    private Integer    ppLoanPeriod;

    /**
     * 是否还清 1:是 0:否 abc_view_cash_borrower.pp_is_clear
     */
    private Byte       ppIsClear;

    /**
     * 还款状态 0:未还 1:正常还款 2:平台代还 3:强制还款 abc_view_cash_borrower.pp_pay_state
     */
    private Integer    ppPayState;

    /**
     * 代还是否已还 1:是 0:否 abc_view_cash_borrower.pp_replace_state
     */
    private Integer    ppReplaceState;

    /**
     * 借款人 abc_view_cash_borrower.pp_loanee
     */
    private Integer    ppLoanee;

    /**
     * 用户名 abc_view_cash_borrower.user_name
     */
    private String     userName;

    /**
     * 真实姓名 abc_view_cash_borrower.user_real_name
     */
    private String     userRealName;

    /**
     * 用户类型 1：个人 2：企业 abc_view_cash_borrower.user_type
     */
    private Integer    userType;

    public BigDecimal getPpPayCapital() {
        return ppPayCapital;
    }

    public void setPpPayCapital(BigDecimal ppPayCapital) {
        this.ppPayCapital = ppPayCapital;
    }

    public BigDecimal getPpPayInterest() {
        return ppPayInterest;
    }

    public void setPpPayInterest(BigDecimal ppPayInterest) {
        this.ppPayInterest = ppPayInterest;
    }

    public BigDecimal getPpPayFine() {
        return ppPayFine;
    }

    public void setPpPayFine(BigDecimal ppPayFine) {
        this.ppPayFine = ppPayFine;
    }

    public BigDecimal getPpPayServiceFee() {
        return ppPayServiceFee;
    }

    /**
     * @param BigDecimal ppPayServiceFee
     *            (abc_view_cash_borrower.pp_pay_service_fee )
     */
    public void setPpPayServiceFee(BigDecimal ppPayServiceFee) {
        this.ppPayServiceFee = ppPayServiceFee;
    }

    public BigDecimal getPpPayGuarFee() {
        return ppPayGuarFee;
    }

    public void setPpPayGuarFee(BigDecimal ppPayGuarFee) {
        this.ppPayGuarFee = ppPayGuarFee;
    }

    public BigDecimal getPpPayTotalMoney() {
        return ppPayTotalMoney;
    }

    /**
     * @param BigDecimal ppPayTotalMoney
     *            (abc_view_cash_borrower.pp_pay_total_money )
     */
    public void setPpPayTotalMoney(BigDecimal ppPayTotalMoney) {
        this.ppPayTotalMoney = ppPayTotalMoney;
    }

    public BigDecimal getPpPayCollectCapital() {
        return ppPayCollectCapital;
    }

    /**
     * @param BigDecimal ppPayCollectCapital
     *            (abc_view_cash_borrower.pp_pay_collect_capital )
     */
    public void setPpPayCollectCapital(BigDecimal ppPayCollectCapital) {
        this.ppPayCollectCapital = ppPayCollectCapital;
    }

    public BigDecimal getPpPayCollectInterest() {
        return ppPayCollectInterest;
    }

    /**
     * @param BigDecimal ppPayCollectInterest
     *            (abc_view_cash_borrower.pp_pay_collect_interest )
     */
    public void setPpPayCollectInterest(BigDecimal ppPayCollectInterest) {
        this.ppPayCollectInterest = ppPayCollectInterest;
    }

    public BigDecimal getPpPayCollectFine() {
        return ppPayCollectFine;
    }

    /**
     * @param BigDecimal ppPayCollectFine
     *            (abc_view_cash_borrower.pp_pay_collect_fine )
     */
    public void setPpPayCollectFine(BigDecimal ppPayCollectFine) {
        this.ppPayCollectFine = ppPayCollectFine;
    }

    public BigDecimal getPpCollectServiceFee() {
        return ppCollectServiceFee;
    }

    /**
     * @param BigDecimal ppCollectServiceFee
     *            (abc_view_cash_borrower.pp_collect_service_fee )
     */
    public void setPpCollectServiceFee(BigDecimal ppCollectServiceFee) {
        this.ppCollectServiceFee = ppCollectServiceFee;
    }

    public BigDecimal getPpCollectGuarFee() {
        return ppCollectGuarFee;
    }

    /**
     * @param BigDecimal ppCollectGuarFee
     *            (abc_view_cash_borrower.pp_collect_guar_fee )
     */
    public void setPpCollectGuarFee(BigDecimal ppCollectGuarFee) {
        this.ppCollectGuarFee = ppCollectGuarFee;
    }

    public BigDecimal getPpCollectTotal() {
        return ppCollectTotal;
    }

    /**
     * @param BigDecimal ppCollectTotal (abc_view_cash_borrower.pp_collect_total
     *            )
     */
    public void setPpCollectTotal(BigDecimal ppCollectTotal) {
        this.ppCollectTotal = ppCollectTotal;
    }

    public BigDecimal getPpRemainFine() {
        return ppRemainFine;
    }

    public void setPpRemainFine(BigDecimal ppRemainFine) {
        this.ppRemainFine = ppRemainFine;
    }

    public Integer getPpLoanPeriod() {
        return ppLoanPeriod;
    }

    public void setPpLoanPeriod(Integer ppLoanPeriod) {
        this.ppLoanPeriod = ppLoanPeriod;
    }

    public Byte getPpIsClear() {
        return ppIsClear;
    }

    public void setPpIsClear(Byte ppIsClear) {
        this.ppIsClear = ppIsClear;
    }

    public Integer getPpPayState() {
        return ppPayState;
    }

    public void setPpPayState(Integer ppPayState) {
        this.ppPayState = ppPayState;
    }

    public Integer getPpReplaceState() {
        return ppReplaceState;
    }

    public void setPpReplaceState(Integer ppReplaceState) {
        this.ppReplaceState = ppReplaceState;
    }

    public Integer getPpLoanee() {
        return ppLoanee;
    }

    public void setPpLoanee(Integer ppLoanee) {
        this.ppLoanee = ppLoanee;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
    
    public BigDecimal getPpPayCollectBreachFine() {
		return ppPayCollectBreachFine;
	}

	public void setPpPayCollectBreachFine(BigDecimal ppPayCollectBreachFine) {
		this.ppPayCollectBreachFine = ppPayCollectBreachFine;
	}

}
