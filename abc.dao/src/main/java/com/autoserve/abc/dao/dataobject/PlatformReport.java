package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台运营总报表
 * 
 * @author zhangkang
 *
 */
public class PlatformReport {
	/** 查询条件 */
	private Date beginDate;
	private Date endDate;

	/** 统计字段 */
	private Integer registerCount;// 注册人数
	private Integer investCount;//新增投资人数
	private Integer openAccountCount;// 开户人数
	private Integer month1;// 一月标
	private BigDecimal month1Money;// 一月标金额
	private Integer month3;// 三月标
	private BigDecimal month3Money;// 三月标金额
	private Integer month6;// 六月标
	private BigDecimal month6Money;// 六月标金额
	private Integer month12;// 十二月标
	private BigDecimal month12Money;// 十二月标金额
	private Integer year1;// 年标
	private BigDecimal year1Money;// 年标金额
	private Integer loanCount;// 项目合计
	private BigDecimal loanMoneySum;// 项目合计金额
	private BigDecimal investMoney; //投标金额
	private BigDecimal fullMoney; //满标金额
	private BigDecimal waitCapital;
	private BigDecimal waitInterest;
	private Integer transferCount;
	private BigDecimal transferMoney;
	private BigDecimal payedInterest;
	private Integer completeLoanCount;
	private BigDecimal completeLoanMoney;
	private Integer rechargeCount;
	private BigDecimal rechargeMoney;
	private Integer withdrawCount;
	private BigDecimal withdrawMoney;
	private Integer handFeeCount;
	private BigDecimal handFeeMoney;
	private Integer guarFeeCount;
	private BigDecimal guarFeeMoney;
	private Integer transferFeeCount;
	private BigDecimal transferFeeMoney;
	private BigDecimal registerRedMoney;
	private BigDecimal investRedMoney;
	private BigDecimal activityRedMoney;
	private BigDecimal inviteRedMoney;
	private BigDecimal scoreRedMoney;
	private BigDecimal useRedMoney;
	private BigDecimal undueRedMoney; 
	
	public Integer getInvestCount() {
		return investCount;
	}

	public void setInvestCount(Integer investCount) {
		this.investCount = investCount;
	}

	public BigDecimal getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(BigDecimal investMoney) {
		this.investMoney = investMoney;
	}

	public BigDecimal getFullMoney() {
		return fullMoney;
	}

	public void setFullMoney(BigDecimal fullMoney) {
		this.fullMoney = fullMoney;
	}

	public BigDecimal getWaitCapital() {
		return waitCapital;
	}

	public void setWaitCapital(BigDecimal waitCapital) {
		this.waitCapital = waitCapital;
	}

	public BigDecimal getWaitInterest() {
		return waitInterest;
	}

	public void setWaitInterest(BigDecimal waitInterest) {
		this.waitInterest = waitInterest;
	}

	public Integer getTransferCount() {
		return transferCount;
	}

	public void setTransferCount(Integer transferCount) {
		this.transferCount = transferCount;
	}

	public BigDecimal getTransferMoney() {
		return transferMoney;
	}

	public void setTransferMoney(BigDecimal transferMoney) {
		this.transferMoney = transferMoney;
	}

	public BigDecimal getPayedInterest() {
		return payedInterest;
	}

	public void setPayedInterest(BigDecimal payedInterest) {
		this.payedInterest = payedInterest;
	}

	public Integer getCompleteLoanCount() {
		return completeLoanCount;
	}

	public void setCompleteLoanCount(Integer completeLoanCount) {
		this.completeLoanCount = completeLoanCount;
	}

	public BigDecimal getCompleteLoanMoney() {
		return completeLoanMoney;
	}

	public void setCompleteLoanMoney(BigDecimal completeLoanMoney) {
		this.completeLoanMoney = completeLoanMoney;
	}

	public Integer getRechargeCount() {
		return rechargeCount;
	}

	public void setRechargeCount(Integer rechargeCount) {
		this.rechargeCount = rechargeCount;
	}

	public BigDecimal getRechargeMoney() {
		return rechargeMoney;
	}

	public void setRechargeMoney(BigDecimal rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}

	public Integer getWithdrawCount() {
		return withdrawCount;
	}

	public void setWithdrawCount(Integer withdrawCount) {
		this.withdrawCount = withdrawCount;
	}

	public BigDecimal getWithdrawMoney() {
		return withdrawMoney;
	}

	public void setWithdrawMoney(BigDecimal withdrawMoney) {
		this.withdrawMoney = withdrawMoney;
	}

	public Integer getHandFeeCount() {
		return handFeeCount;
	}

	public void setHandFeeCount(Integer handFeeCount) {
		this.handFeeCount = handFeeCount;
	}

	public BigDecimal getHandFeeMoney() {
		return handFeeMoney;
	}

	public void setHandFeeMoney(BigDecimal handFeeMoney) {
		this.handFeeMoney = handFeeMoney;
	}

	public Integer getGuarFeeCount() {
		return guarFeeCount;
	}

	public void setGuarFeeCount(Integer guarFeeCount) {
		this.guarFeeCount = guarFeeCount;
	}

	public BigDecimal getGuarFeeMoney() {
		return guarFeeMoney;
	}

	public void setGuarFeeMoney(BigDecimal guarFeeMoney) {
		this.guarFeeMoney = guarFeeMoney;
	}

	public Integer getTransferFeeCount() {
		return transferFeeCount;
	}

	public void setTransferFeeCount(Integer transferFeeCount) {
		this.transferFeeCount = transferFeeCount;
	}

	public BigDecimal getTransferFeeMoney() {
		return transferFeeMoney;
	}

	public void setTransferFeeMoney(BigDecimal transferFeeMoney) {
		this.transferFeeMoney = transferFeeMoney;
	}

	public BigDecimal getRegisterRedMoney() {
		return registerRedMoney;
	}

	public void setRegisterRedMoney(BigDecimal registerRedMoney) {
		this.registerRedMoney = registerRedMoney;
	}

	public BigDecimal getInvestRedMoney() {
		return investRedMoney;
	}

	public void setInvestRedMoney(BigDecimal investRedMoney) {
		this.investRedMoney = investRedMoney;
	}

	public BigDecimal getActivityRedMoney() {
		return activityRedMoney;
	}

	public void setActivityRedMoney(BigDecimal activityRedMoney) {
		this.activityRedMoney = activityRedMoney;
	}

	public BigDecimal getInviteRedMoney() {
		return inviteRedMoney;
	}

	public void setInviteRedMoney(BigDecimal inviteRedMoney) {
		this.inviteRedMoney = inviteRedMoney;
	}

	public BigDecimal getScoreRedMoney() {
		return scoreRedMoney;
	}

	public void setScoreRedMoney(BigDecimal scoreRedMoney) {
		this.scoreRedMoney = scoreRedMoney;
	}

	public BigDecimal getUseRedMoney() {
		return useRedMoney;
	}

	public void setUseRedMoney(BigDecimal useRedMoney) {
		this.useRedMoney = useRedMoney;
	}

	public BigDecimal getUndueRedMoney() {
		return undueRedMoney;
	}

	public void setUndueRedMoney(BigDecimal undueRedMoney) {
		this.undueRedMoney = undueRedMoney;
	}

	public Integer getMonth1() {
		return month1;
	}

	public void setMonth1(Integer month1) {
		this.month1 = month1;
	}

	public BigDecimal getMonth1Money() {
		return month1Money;
	}

	public void setMonth1Money(BigDecimal month1Money) {
		this.month1Money = month1Money;
	}

	public Integer getMonth3() {
		return month3;
	}

	public void setMonth3(Integer month3) {
		this.month3 = month3;
	}

	public BigDecimal getMonth3Money() {
		return month3Money;
	}

	public void setMonth3Money(BigDecimal month3Money) {
		this.month3Money = month3Money;
	}

	public Integer getMonth6() {
		return month6;
	}

	public void setMonth6(Integer month6) {
		this.month6 = month6;
	}

	public BigDecimal getMonth6Money() {
		return month6Money;
	}

	public void setMonth6Money(BigDecimal month6Money) {
		this.month6Money = month6Money;
	}

	public Integer getMonth12() {
		return month12;
	}

	public void setMonth12(Integer month12) {
		this.month12 = month12;
	}

	public BigDecimal getMonth12Money() {
		return month12Money;
	}

	public void setMonth12Money(BigDecimal month12Money) {
		this.month12Money = month12Money;
	}

	public Integer getYear1() {
		return year1;
	}

	public void setYear1(Integer year1) {
		this.year1 = year1;
	}

	public BigDecimal getYear1Money() {
		return year1Money;
	}

	public void setYear1Money(BigDecimal year1Money) {
		this.year1Money = year1Money;
	}

	public Integer getLoanCount() {
		return loanCount;
	}

	public void setLoanCount(Integer loanCount) {
		this.loanCount = loanCount;
	}

	public BigDecimal getLoanMoneySum() {
		return loanMoneySum;
	}

	public void setLoanMoneySum(BigDecimal loanMoneySum) {
		this.loanMoneySum = loanMoneySum;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(Integer registerCount) {
		this.registerCount = registerCount;
	}

	public Integer getOpenAccountCount() {
		return openAccountCount;
	}

	public void setOpenAccountCount(Integer openAccountCount) {
		this.openAccountCount = openAccountCount;
	}

}
