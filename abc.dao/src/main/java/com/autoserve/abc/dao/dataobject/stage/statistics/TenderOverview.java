package com.autoserve.abc.dao.dataobject.stage.statistics;

import java.math.BigDecimal;

/**
 *	投标概况
 * 
 * @author wuqiang.du 2015-2-7 11:46:59
 */
public class TenderOverview {
	 /**
     * 项目状态
     */
	private Integer loanstatus;
	 /**
     * 投资笔数
     */
	private Integer investNum;
	
	 /**
     * 金额
     */
	private BigDecimal investMoney;

	public Integer getLoanstatus() {
		return loanstatus;
	}

	public void setLoanstatus(Integer loanstatus) {
		this.loanstatus = loanstatus;
	}

	public Integer getInvestNum() {
		return investNum;
	}

	public void setInvestNum(Integer investNum) {
		this.investNum = investNum;
	}

	public BigDecimal getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(BigDecimal investMoney) {
		this.investMoney = investMoney;
	}
	
	
	
}
