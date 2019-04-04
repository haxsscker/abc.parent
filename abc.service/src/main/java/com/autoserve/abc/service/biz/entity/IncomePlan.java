/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.IncomePlanState;

/**
 * 收益计划
 *
 * @author segen189 2014年11月26日 上午10:49:04
 */
public class IncomePlan {
    /**
     * 主键id
     */
    private Integer         id;

    /**
     * 满标资金划转记录id
     */
    private Integer         fullTransRecordId;

    /**
     * 借款人还款计划表id
     */
    private Integer         paymentPlanId;

    /**
     * 投资记录id
     */
    private Integer         investId;

    /**
     * 普通标id
     */
    private Integer         loanId;

    /**
     * 应还本金
     */
    private BigDecimal      payCapital;

    /**
     * 应还利息
     */
    private BigDecimal      payInterest;

    /**
     * 应还罚金
     */
    private BigDecimal      payFine;

    /**
     * 应还总额
     */
    private BigDecimal      payTotalMoney;

    /**
     * 实还本金
     */
    private BigDecimal      collectCapital;

    /**
     * 实还利息
     */
    private BigDecimal      collectInterest;

    /**
     * 实还罚金
     */
    private BigDecimal      collectFine;

    /**
     * 实还总额
     */
    private BigDecimal      collectTotal;

    /**
     * 应还日期
     */
    private Date            paytime;

    /**
     * 实还日期
     */
    private Date            collecttime;

    /**
     * 剩余罚金 因罚金是时时计算的，当第一次罚金未还清时，保留剩余未还罚金
     */
    private Long            remainFine;

    /**
     * 期数
     */
    private Integer         loanPeriod;

    /**
     * 收益计划状态
     */
    private IncomePlanState incomePlanState;

    /**
     * 收益人
     */
    private Integer         beneficiary;

    /**
     * 内部交易流水号
     */
    private String          innerSeqNo;
    
    /**
     * 项目名称
     */
    private String          loanNo;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 是否是转让人最后一期收益
     */
    private Boolean transferLast;
    
    

    public Boolean getTransferLast() {
		return transferLast;
	}

	public void setTransferLast(Boolean transferLast) {
		this.transferLast = transferLast;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFullTransRecordId() {
        return fullTransRecordId;
    }

    public void setFullTransRecordId(Integer fullTransRecordId) {
        this.fullTransRecordId = fullTransRecordId;
    }

    public Integer getPaymentPlanId() {
        return paymentPlanId;
    }

    public void setPaymentPlanId(Integer paymentPlanId) {
        this.paymentPlanId = paymentPlanId;
    }

    public Integer getInvestId() {
        return investId;
    }

    public void setInvestId(Integer investId) {
        this.investId = investId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getPayCapital() {
        return payCapital;
    }

    public void setPayCapital(BigDecimal payCapital) {
        this.payCapital = payCapital;
    }

    public BigDecimal getPayInterest() {
        return payInterest;
    }

    public void setPayInterest(BigDecimal payInterest) {
        this.payInterest = payInterest;
    }

    public BigDecimal getPayFine() {
        return payFine;
    }

    public void setPayFine(BigDecimal payFine) {
        this.payFine = payFine;
    }

    public BigDecimal getPayTotalMoney() {
        return payTotalMoney;
    }

    public void setPayTotalMoney(BigDecimal payTotalMoney) {
        this.payTotalMoney = payTotalMoney;
    }

    public BigDecimal getCollectCapital() {
        return collectCapital;
    }

    public void setCollectCapital(BigDecimal collectCapital) {
        this.collectCapital = collectCapital;
    }

    public BigDecimal getCollectInterest() {
        return collectInterest;
    }

    public void setCollectInterest(BigDecimal collectInterest) {
        this.collectInterest = collectInterest;
    }

    public BigDecimal getCollectFine() {
        return collectFine;
    }

    public void setCollectFine(BigDecimal collectFine) {
        this.collectFine = collectFine;
    }

    public BigDecimal getCollectTotal() {
        return collectTotal;
    }

    public void setCollectTotal(BigDecimal collectTotal) {
        this.collectTotal = collectTotal;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

    public Date getCollecttime() {
        return collecttime;
    }

    public void setCollecttime(Date collecttime) {
        this.collecttime = collecttime;
    }

    public Long getRemainFine() {
        return remainFine;
    }

    public void setRemainFine(Long remainFine) {
        this.remainFine = remainFine;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public IncomePlanState getIncomePlanState() {
        return incomePlanState;
    }

    public void setIncomePlanState(IncomePlanState incomePlanState) {
        this.incomePlanState = incomePlanState;
    }

    public Integer getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Integer beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getInnerSeqNo() {
        return innerSeqNo;
    }

    public void setInnerSeqNo(String innerSeqNo) {
        this.innerSeqNo = innerSeqNo;
    }

	/**
	 * @return the loanNo
	 */
	public String getLoanNo() {
		return loanNo;
	}

	/**
	 * @param loanNo the loanNo to set
	 */
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

}
