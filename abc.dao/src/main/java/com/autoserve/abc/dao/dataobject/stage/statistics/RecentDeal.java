package com.autoserve.abc.dao.dataobject.stage.statistics;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 最近交易
 * 
 * @author wuqiang.du 2015-2-7 11:46:59
 */
public class RecentDeal {
	 /**
     * 时间
     */
	private Date dealDate;
	 /**
     * 类型
     */
	private Integer dealType;
	 /**
     * 金额
     */
	private BigDecimal dealMoney;
	/**
	 * 付款人账户
	 */
	private String drPayAccount;
	/**
	 * 收款人账户
	 */
	private String drReceiveAccount;
	/**
	 * 交易细则类型
	 */
	private Integer detailType;
	/**
	 * 外部流水号
	 */
	private String outSeqNo;
	
	
	public String getOutSeqNo() {
		return outSeqNo;
	}
	public void setOutSeqNo(String outSeqNo) {
		this.outSeqNo = outSeqNo;
	}
	public Integer getDetailType() {
		return detailType;
	}
	public void setDetailType(Integer detailType) {
		this.detailType = detailType;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public Integer getDealType() {
		return dealType;
	}
	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}
	public BigDecimal getDealMoney() {
		return dealMoney;
	}
	public void setDealMoney(BigDecimal dealMoney) {
		this.dealMoney = dealMoney;
	}
	public String getDrPayAccount() {
		return drPayAccount;
	}
	public void setDrPayAccount(String drPayAccount) {
		this.drPayAccount = drPayAccount;
	}
	public String getDrReceiveAccount() {
		return drReceiveAccount;
	}
	public void setDrReceiveAccount(String drReceiveAccount) {
		this.drReceiveAccount = drReceiveAccount;
	}	
}
