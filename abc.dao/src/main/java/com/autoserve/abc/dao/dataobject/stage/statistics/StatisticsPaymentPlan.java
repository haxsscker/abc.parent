package com.autoserve.abc.dao.dataobject.stage.statistics;

import java.math.BigDecimal;

/**
 *	回款计划
 * 
 * @author wuqiang.du 2015-2-7 11:46:59
 */
public class StatisticsPaymentPlan {
	 /**
     * 日期
     */
	private String dateUnit;
	 /**
     * 投资笔数
     */
	private Integer investNum;
	
	 /**
     * 金额
     */
	private BigDecimal investMoney;

	
	public String getDateUnit() {
		return dateUnit;
	}

	public void setDateUnit(String dateUnit) {
		this.dateUnit = dateUnit;
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
