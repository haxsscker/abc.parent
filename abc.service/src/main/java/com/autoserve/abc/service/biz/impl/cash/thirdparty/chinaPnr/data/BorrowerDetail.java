package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data;

public class BorrowerDetail {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*借款人的userCustId*/
	private String borrowerCustId;
	/*转出原标借款金额（从原投标订单借款人转出的已放款金额）*/
	private String borrowerCreditAmt;
	/*  已还本金  (借款人还款金额中所占本金的部分) */
	private String prinAmt;
	/*项目id*/
	private String proId;
	/**
	 * 借款人的userCustId
	 * @return
	 */
	public String getBorrowerCustId() {
		return borrowerCustId;
	}
	/**
	 * 借款人的userCustId
	 * @param borrowerCustId
	 */
	public void setBorrowerCustId(String borrowerCustId) {
		this.borrowerCustId = borrowerCustId;
	}
	/**
	 * 转出原标借款金额（从原投标订单借款人转出的已放款金额）
	 * @return
	 */
	public String getBorrowerCreditAmt() {
		return borrowerCreditAmt;
	}
	/**
	 * 转出原标借款金额（从原投标订单借款人转出的已放款金额）
	 * @param borrowerCreditAmt
	 */
	public void setBorrowerCreditAmt(String borrowerCreditAmt) {
		this.borrowerCreditAmt = borrowerCreditAmt;
	}
	/**
	 * 借款人还款金额中所占本金的部分
	 * @return
	 */
	public String getPrinAmt() {
		return prinAmt;
	}
	/**
	 * 借款人还款金额中所占本金的部分
	 * @param printAmt
	 */
	public void setPrinAmt(String prinAmt) {
		this.prinAmt = prinAmt;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	
	
}
