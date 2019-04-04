package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data;

public class OrdDetail {
	/*借款人在汇付的唯一标示  对应的是usrCustId*/
	private String borrowerCustId;
	/*借款额度，小数点后保留2位*/
	private String borrowerAmt;
	/*借款手续费率*/
	private String borrowerRate;
	/*项目ID proId*/
	private String proId;
	/*已还款金额*/
	private String prinAmt;
	
	public String getBorrowerCustId() {
		return borrowerCustId;
	}
	public void setBorrowerCustId(String borrowerCustId) {
		this.borrowerCustId = borrowerCustId;
	}
	public String getBorrowerAmt() {
		return borrowerAmt;
	}
	public void setBorrowerAmt(String borrowerAmt) {
		this.borrowerAmt = borrowerAmt;
	}
	public String getBorrowerRate() {
		return borrowerRate;
	}
	public void setBorrowerRate(String borrowerRate) {
		this.borrowerRate = borrowerRate;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getPrinAmt() {
		return prinAmt;
	}
	public void setPrinAmt(String prinAmt) {
		this.prinAmt = prinAmt;
	}
	
	
}
