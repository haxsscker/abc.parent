package com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data;

import java.util.List;

public class BidDetail {
	private String bidOrdId;
	private String bidOrdDate;
	private String bidCreditAmt;
	private List<BorrowerDetail> borrowerDetails;
	public String getBidOrdId() {
		return bidOrdId;
	}
	public String getBidOrdDate() {
		return bidOrdDate;
	}
	public void setBidOrdDate(String bidOrdDate) {
		this.bidOrdDate = bidOrdDate;
	}
	public String getBidCreditAmt() {
		return bidCreditAmt;
	}
	public void setBidCreditAmt(String bidCreditAmt) {
		this.bidCreditAmt = bidCreditAmt;
	}
	public List<BorrowerDetail> getBorrowerDetails() {
		return borrowerDetails;
	}
	public void setBorrowerDetails(List<BorrowerDetail> borrowerDetails) {
		this.borrowerDetails = borrowerDetails;
	}
	public void setBidOrdId(String bidOrdId) {
		this.bidOrdId = bidOrdId;
	}
}
