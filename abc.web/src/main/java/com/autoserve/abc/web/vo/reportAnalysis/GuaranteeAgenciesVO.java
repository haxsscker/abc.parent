/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.reportAnalysis;

import java.math.BigDecimal;

/**
 * 类GuaranteeAgenciesVO.java的实现描述：借款人资金对账
 * 
 * @author J.YL 2014年12月19日 上午11:01:04
 */
public class GuaranteeAgenciesVO {
    /**
     * 申请人
     */
    private String     proposer;
    /**
     * 申请人真实姓名
     */
    private String     proposerRealName;
    /**
     * 申请人类型
     */
    private String     proposertype;
    /**
     * 借款人
     */
    private String     pro_user_name;
    /**
     * 融资总额
     */
    private BigDecimal recharge_amount;
    /**
     * 已还本金
     */
    private BigDecimal recovery_principal;
    /**
     * 已还利息
     */
    private BigDecimal recovery_interest;
    /**
     * 已还罚金
     */
    private BigDecimal recovery_amount;
    /**
     * 已还违约罚金
     * add by 夏同同 20160419
     */
    private BigDecimal recovery_breach_amount;
    /**
     * 已还服务费
     */
    private BigDecimal pro_collect_service_fee;
    /**
     * 已还担保费
     */
    private BigDecimal pro_collect_guar_fee;
    /**
     * 已还款总额
     */
    private BigDecimal pro_collect_total;
    /**
     * 未还款总额
     */
    private BigDecimal foreclosure_investing;
    /**
     * 未还本金
     */
    private BigDecimal not_pay_money;
    /**
     * 未还利息
     */
    private BigDecimal not_pay_rate;
    /**
     * 未还罚金
     */
    private BigDecimal not_pay_over_rate;
    /**
     * 未还服务费
     */
    private BigDecimal not_pay_service_fee;
    /**
     * 可用余额
     */
    private BigDecimal assignment;
    
    /**
     * @return the customer_name
     */
    public String getProposer() {
        return proposer;
    }

    /**
     * @param customer_name the customer_name to set
     */
    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    /**
     * @return the proposertype
     */
    public String getProposertype() {
        return proposertype;
    }

    /**
     * @param proposertype the proposertype to set
     */
    public void setProposertype(String proposertype) {
        this.proposertype = proposertype;
    }

    /**
     * @return the pro_user_name
     */
    public String getPro_user_name() {
        return pro_user_name;
    }

    /**
     * @param pro_user_name the pro_user_name to set
     */
    public void setPro_user_name(String pro_user_name) {
        this.pro_user_name = pro_user_name;
    }

    /**
     * @return the recharge_amount
     */
    public BigDecimal getRecharge_amount() {
        return recharge_amount;
    }

    /**
     * @param recharge_amount the recharge_amount to set
     */
    public void setRecharge_amount(BigDecimal recharge_amount) {
        this.recharge_amount = recharge_amount;
    }

    /**
     * @return the recovery_principal
     */
    public BigDecimal getRecovery_principal() {
        return recovery_principal;
    }

    /**
     * @param recovery_principal the recovery_principal to set
     */
    public void setRecovery_principal(BigDecimal recovery_principal) {
        this.recovery_principal = recovery_principal;
    }

    /**
     * @return the recovery_interest
     */
    public BigDecimal getRecovery_interest() {
        return recovery_interest;
    }

    /**
     * @param recovery_interest the recovery_interest to set
     */
    public void setRecovery_interest(BigDecimal recovery_interest) {
        this.recovery_interest = recovery_interest;
    }

    /**
     * @return the recovery_amount
     */
    public BigDecimal getRecovery_amount() {
        return recovery_amount;
    }

    /**
     * @param recovery_amount the recovery_amount to set
     */
    public void setRecovery_amount(BigDecimal recovery_amount) {
        this.recovery_amount = recovery_amount;
    }

    /**
     * @return the pro_collect_service_fee
     */
    public BigDecimal getPro_collect_service_fee() {
        return pro_collect_service_fee;
    }

    /**
     * @param pro_collect_service_fee the pro_collect_service_fee to set
     */
    public void setPro_collect_service_fee(BigDecimal pro_collect_service_fee) {
        this.pro_collect_service_fee = pro_collect_service_fee;
    }

    /**
     * @return the pro_collect_guar_fee
     */
    public BigDecimal getPro_collect_guar_fee() {
        return pro_collect_guar_fee;
    }

    /**
     * @param pro_collect_guar_fee the pro_collect_guar_fee to set
     */
    public void setPro_collect_guar_fee(BigDecimal pro_collect_guar_fee) {
        this.pro_collect_guar_fee = pro_collect_guar_fee;
    }

    /**
     * @return the pro_collect_total
     */
    public BigDecimal getPro_collect_total() {
        return pro_collect_total;
    }

    /**
     * @param pro_collect_total the pro_collect_total to set
     */
    public void setPro_collect_total(BigDecimal pro_collect_total) {
        this.pro_collect_total = pro_collect_total;
    }

    /**
     * @return the foreclosure_investing
     */
    public BigDecimal getForeclosure_investing() {
        return foreclosure_investing;
    }

    /**
     * @param foreclosure_investing the foreclosure_investing to set
     */
    public void setForeclosure_investing(BigDecimal foreclosure_investing) {
        this.foreclosure_investing = foreclosure_investing;
    }

    /**
     * @return the not_pay_money
     */
    public BigDecimal getNot_pay_money() {
        return not_pay_money;
    }

    /**
     * @param not_pay_money the not_pay_money to set
     */
    public void setNot_pay_money(BigDecimal not_pay_money) {
        this.not_pay_money = not_pay_money;
    }

    /**
     * @return the not_pay_rate
     */
    public BigDecimal getNot_pay_rate() {
        return not_pay_rate;
    }

    /**
     * @param not_pay_rate the not_pay_rate to set
     */
    public void setNot_pay_rate(BigDecimal not_pay_rate) {
        this.not_pay_rate = not_pay_rate;
    }

    /**
     * @return the not_pay_over_rate
     */
    public BigDecimal getNot_pay_over_rate() {
        return not_pay_over_rate;
    }

    /**
     * @param not_pay_over_rate the not_pay_over_rate to set
     */
    public void setNot_pay_over_rate(BigDecimal not_pay_over_rate) {
        this.not_pay_over_rate = not_pay_over_rate;
    }

    /**
     * @return the not_pay_service_fee
     */
    public BigDecimal getNot_pay_service_fee() {
        return not_pay_service_fee;
    }

    /**
     * @param not_pay_service_fee the not_pay_service_fee to set
     */
    public void setNot_pay_service_fee(BigDecimal not_pay_service_fee) {
        this.not_pay_service_fee = not_pay_service_fee;
    }

    /**
     * @return the assignment
     */
    public BigDecimal getAssignment() {
        return assignment;
    }

    /**
     * @param assignment the assignment to set
     */
    public void setAssignment(BigDecimal assignment) {
        this.assignment = assignment;
    }

    public String getProposerRealName() {
        return proposerRealName;
    }

    public void setProposerRealName(String proposerRealName) {
        this.proposerRealName = proposerRealName;
    }

	public BigDecimal getRecovery_breach_amount() {
		return recovery_breach_amount;
	}

	public void setRecovery_breach_amount(BigDecimal recovery_breach_amount) {
		this.recovery_breach_amount = recovery_breach_amount;
	}
}
