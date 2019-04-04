package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

public class InvestJDO {
	
	/**
	 * 项目编号
	 */
	private String loanNo;
	/**
	 * 转让金额
	 */
	private BigDecimal transferMoney;
	
	/**
	 * 成交日期
	 */
	private Date strikeDate;
	
	/**
	 * 到期日
	 */
	private Date endDate;
	
	/**
	 * 已回款
	 */
	private BigDecimal received;
	
	/**
	 * 待回款
	 */
	private BigDecimal wait;

	public String getLoanNo() {
		return loanNo;
	}

	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public BigDecimal getTransferMoney() {
		return transferMoney;
	}

	public void setTransferMoney(BigDecimal transferMoney) {
		this.transferMoney = transferMoney;
	}

	public Date getStrikeDate() {
		return strikeDate;
	}

	public void setStrikeDate(Date strikeDate) {
		this.strikeDate = strikeDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getReceived() {
		return received;
	}

	public void setReceived(BigDecimal received) {
		this.received = received;
	}

	public BigDecimal getWait() {
		return wait;
	}

	public void setWait(BigDecimal wait) {
		this.wait = wait;
	}

}
