package com.autoserve.abc.web.module.screen.account.myInvest.json;

import java.math.BigDecimal;

import com.autoserve.abc.service.biz.enums.IncomePlanState;

public class IncomePlanVO {
	/**
     * 应还日期
     */
    private String      paytimeStr;

    /**
     * 期数
     */
    private Integer         loanPeriod;
    /**
     * 实还总额
     */
    private BigDecimal      collectTotal;
    /**
     * 应还总额
     */
    private BigDecimal      payTotalMoney;
    /**
     * 
     * 待回款
     */
    private BigDecimal     backMoney;
    /**
     * 实还本金
     */
    private BigDecimal      collectCapital;

    /**
     * 实还利息
     */
    private BigDecimal      collectInterest;
    /**
     * 实还罚金
     */
    private BigDecimal collectFine;
    
    /**
     * 收益计划状态
     */
    private IncomePlanState incomePlanState;
    
	public BigDecimal getCollectFine() {
		return collectFine;
	}
	public void setCollectFine(BigDecimal collectFine) {
		this.collectFine = collectFine;
	}
	public String getPaytimeStr() {
		return paytimeStr;
	}
	public void setPaytimeStr(String paytimeStr) {
		this.paytimeStr = paytimeStr;
	}
	public Integer getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public BigDecimal getCollectTotal() {
		return collectTotal;
	}
	public void setCollectTotal(BigDecimal collectTotal) {
		this.collectTotal = collectTotal;
	}
	public BigDecimal getPayTotalMoney() {
		return payTotalMoney;
	}
	public void setPayTotalMoney(BigDecimal payTotalMoney) {
		this.payTotalMoney = payTotalMoney;
	}
	public BigDecimal getBackMoney() {
		return backMoney;
	}
	public void setBackMoney(BigDecimal backMoney) {
		this.backMoney = backMoney;
	}
	public BigDecimal getCollectCapital() {
		return collectCapital;
	}
	public void setCollectCapital(BigDecimal collectCapital) {
		this.collectCapital = collectCapital;
	}
	public BigDecimal getCollectInterest() {
		return collectInterest;
	}
	public void setCollectInterest(BigDecimal collectInterest) {
		this.collectInterest = collectInterest;
	}
	public IncomePlanState getIncomePlanState() {
		return incomePlanState;
	}
	public void setIncomePlanState(IncomePlanState incomePlanState) {
		this.incomePlanState = incomePlanState;
	}
    
}
