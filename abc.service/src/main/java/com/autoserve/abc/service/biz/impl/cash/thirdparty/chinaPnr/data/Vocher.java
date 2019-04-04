package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data;

public class Vocher {
	/**
	 * 代金券出账子账户
	 */
	private String acctId;
	/**
	 * 代金券出账金额
	 */
	private String vocherAmt;
	
	/*商户的子账号,配置文件读取*/
	public String getAcctId() {
		return acctId;
	}
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	public String getVocherAmt() {
		return vocherAmt;
	}
	public void setVocherAmt(String vocherAmt) {
		this.vocherAmt = vocherAmt;
	}
	
	
}
