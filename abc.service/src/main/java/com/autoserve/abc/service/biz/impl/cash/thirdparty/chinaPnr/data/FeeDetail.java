package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data;

public class FeeDetail {
	/*手续费分账客户号，汇付生成的usrCustID,有配置文件读取*/
	private  String divCustId;
	/*商户的子账号*/
	private String divAcctId;
	/*手续费金额,小数点保留2位*/
	private String divAmt;
	/*手续费分账客户号，汇付生成的usrCustID,有配置文件读取*/
	public String getDivCustId() {
		return divCustId;
	}
	public void setDivCustId(String divCustId) {
		this.divCustId = divCustId;
	}
	/*商户的子账号,配置文件读取*/
	public String getDivAcctId() {
		return divAcctId;
	}
	public void setDivAcctId(String divAcctId) {
		this.divAcctId = divAcctId;
	}
	/**
	 * 手续费金额,小数点保留2位
	 */
	public String getDivAmt() {
		return divAmt;
	}
	/**
	 * 手续费金额,小数点保留2位
	 */
	public void setDivAmt(String divAmt) {
		this.divAmt = divAmt;
	}
}
