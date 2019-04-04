package com.autoserve.abc.dao.dataobject.summary;

import java.math.BigDecimal;

public class PostageSummaryDO {
	
	private int aheadPayLoan;
	private BigDecimal aheadPayMoney;
	
	public int getAheadPayLoan() {
		return aheadPayLoan;
	}
	public void setAheadPayLoan(int aheadPayLoan) {
		this.aheadPayLoan = aheadPayLoan;
	}
	public BigDecimal getAheadPayMoney() {
		return aheadPayMoney;
	}
	public void setAheadPayMoney(BigDecimal aheadPayMoney) {
		this.aheadPayMoney = aheadPayMoney;
	}
}
