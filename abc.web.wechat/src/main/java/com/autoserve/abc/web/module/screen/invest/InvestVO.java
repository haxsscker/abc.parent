package com.autoserve.abc.web.module.screen.invest;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;

public class InvestVO {
	 /**
     * 主键id
     */
    private Integer     id;

    /**
     * 投资人
     */
    private Integer     userId;
    /**
     * 投资人姓名
     */
    private String      userName;
    /**
     * 投资人姓名
     */
    private String      userPhone;
    /**
     * 投资日期
     */
    private String       createtime;

    /**
     * 更新日期
     */
    private Date        modifytime;

    /**
     * 投资金额
     */
    private BigDecimal  investMoney;

    /**
     * 有效投资金额
     */
    private BigDecimal  validInvestMoney;

    /**
     * 投资状态
     */
    private InvestState investState;

    /**
     * 内部交易流水号
     */
    private String      innerSeqNo;

    /**
     * 撤投交易流水号
     */
    private String      withdrawSeqNo;

    /**
     * 原始贷款id
     */
    private Integer     originId;

    /**
     * 标类型
     */
    private BidType     bidType;

    /**
     * 标外键表id
     */
    private Integer     bidId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public BigDecimal getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(BigDecimal investMoney) {
		this.investMoney = investMoney;
	}

	public BigDecimal getValidInvestMoney() {
		return validInvestMoney;
	}

	public void setValidInvestMoney(BigDecimal validInvestMoney) {
		this.validInvestMoney = validInvestMoney;
	}

	public InvestState getInvestState() {
		return investState;
	}

	public void setInvestState(InvestState investState) {
		this.investState = investState;
	}

	public String getInnerSeqNo() {
		return innerSeqNo;
	}

	public void setInnerSeqNo(String innerSeqNo) {
		this.innerSeqNo = innerSeqNo;
	}

	public String getWithdrawSeqNo() {
		return withdrawSeqNo;
	}

	public void setWithdrawSeqNo(String withdrawSeqNo) {
		this.withdrawSeqNo = withdrawSeqNo;
	}

	public Integer getOriginId() {
		return originId;
	}

	public void setOriginId(Integer originId) {
		this.originId = originId;
	}

	public BidType getBidType() {
		return bidType;
	}

	public void setBidType(BidType bidType) {
		this.bidType = bidType;
	}

	public Integer getBidId() {
		return bidId;
	}

	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}



}
