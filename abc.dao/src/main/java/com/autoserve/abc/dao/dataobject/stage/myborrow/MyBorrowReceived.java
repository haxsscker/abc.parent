package com.autoserve.abc.dao.dataobject.stage.myborrow;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的借款列表（还款中）
 * 
 * @author liuwei 2015年1月26日 下午4:42:13
 */
public class MyBorrowReceived {

	/**
	 * 项目ID
	 */
	private String loanId;
	/**
	 * 项目名称
	 */
	private String loanNo;
	/**
	 * 年利率
	 */
	private BigDecimal loanRate;
	/**
	 * 借款金额
	 */
	private BigDecimal loanMoney;
	/**
	 * 借款日期
	 */
	private Date loantime;
	/**
	 * 下次还款日
	 */
	private Date paytime;
	/**
	 * 期数
	 */
	private int loanPeriod;

	/**
	 * 应还金额
	 */
	private BigDecimal payMoney;
	/**
	 * 到期日
	 */
	private Date endDate;

	/**
	 * 状态
	 */
	private int state;
	
	/**
	 * 提前还款状态，用于前台判断是否已经提前还款
	 * add by 夏同同  20160506
	 */
	private String aheadState;
	

	public String getLoanNo() {
		return loanNo;
	}

	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public BigDecimal getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}

	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	public int getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(int loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getLoantime() {
		return loantime;
	}

	public void setLoantime(Date loantime) {
		this.loantime = loantime;
	}

	public BigDecimal getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getAheadState() {
		return aheadState;
	}

	public void setAheadState(String aheadState) {
		this.aheadState = aheadState;
	}

}
