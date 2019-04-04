/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.PayType;

/**
 * 还款计划
 *
 * @author segen189 2014年11月26日 上午11:09:44
 */
public class PaymentPlan {
    /**
     * 主键id
     */
    private Integer    id;

    /**
     * 满标资金划转记录id
     */
    private Integer    fullTransRecordId;

    /**
     * 原始借款标id
     */
    private Integer    loanId;

    /**
     * 应还本金
     */
    private BigDecimal payCapital;

    /**
     * 应还利息
     */
    private BigDecimal payInterest;

    /**
     * 应还逾期罚金
     */
    private BigDecimal payFine;

    /**
     * 应还平台服务费
     */
    private BigDecimal payServiceFee;

    /**
     * 应还平台担保费
     */
    private BigDecimal payGuarFee;

    /**
     * 应还总额
     */
    private BigDecimal payTotalMoney;

    /**
     * 实还本金
     */
    private BigDecimal payCollectCapital;

    /**
     * 实还利息
     */
    private BigDecimal payCollectInterest;

    /**
     * 实还逾期罚金
     */
    private BigDecimal payCollectFine;

    /**
     * 实还平台服务费
     */
    private BigDecimal collectServiceFee;

    /**
     * 实还担保服务费
     */
    private BigDecimal collectGuarFee;

    /**
     * 实还总额
     */
    private BigDecimal collectTotal;

    /**
     * 剩余逾期罚金 因罚金是时时计算的，当第一次罚金未还清时，保留剩余未还罚金
     */
    private BigDecimal remainFine;

    /**
     * 期数
     */
    private Integer    loanPeriod;

    /**
     * 是否还清 true:是 false:否
     */
    private Boolean    isClear;

    /**
     * 应还日期
     */
    private Date       paytime;

    /**
     * 实还日期
     */
    private Date       collecttime;

    /**
     * 还款状态 -1:未激活 1:未还清 2:付款中 3:已还清
     */
    private PayState   payState;

    /**
     * 还款类型 1:正常还清 2:平台代还清 3:强制还清 4：提前还清  5:担保代还清
     */
    private PayType    payType;

    /**
     * 是否已经代还 true:是 false:否
     */
    private Boolean    replaceState;

    /**
     * 借款人
     */
    private Integer    loanee;

    /**
     * 内部交易流水号
     */
    private String     innerSeqNo;
    
    /**
     * @content:新增违约罚金属性:payBreachFine、
     * 		payCollectBreachFine、remainBreachFine
     * @author:夏同同
     * @date:2016年4月10日 上午10:33:54
     */
    /** 应还违约罚金*/
    private BigDecimal payBreachFine;
    /** 实还违约罚金*/
    private BigDecimal payCollectBreachFine;
    /** 剩余违约罚金*/
    private BigDecimal remainBreachFine;
    
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

    public BigDecimal getPayServiceFee() {
        return payServiceFee;
    }

    public void setPayServiceFee(BigDecimal payServiceFee) {
        this.payServiceFee = payServiceFee;
    }

    public BigDecimal getPayGuarFee() {
        return payGuarFee;
    }

    public void setPayGuarFee(BigDecimal payGuarFee) {
        this.payGuarFee = payGuarFee;
    }

    public BigDecimal getPayTotalMoney() {
        return payTotalMoney;
    }

    public void setPayTotalMoney(BigDecimal payTotalMoney) {
        this.payTotalMoney = payTotalMoney;
    }

    public BigDecimal getPayCollectCapital() {
        return payCollectCapital;
    }

    public void setPayCollectCapital(BigDecimal payCollectCapital) {
        this.payCollectCapital = payCollectCapital;
    }

    public BigDecimal getPayCollectInterest() {
        return payCollectInterest;
    }

    public void setPayCollectInterest(BigDecimal payCollectInterest) {
        this.payCollectInterest = payCollectInterest;
    }

    public BigDecimal getPayCollectFine() {
        return payCollectFine;
    }

    public void setPayCollectFine(BigDecimal payCollectFine) {
        this.payCollectFine = payCollectFine;
    }

    public BigDecimal getCollectServiceFee() {
        return collectServiceFee;
    }

    public void setCollectServiceFee(BigDecimal collectServiceFee) {
        this.collectServiceFee = collectServiceFee;
    }

    public BigDecimal getCollectGuarFee() {
        return collectGuarFee;
    }

    public void setCollectGuarFee(BigDecimal collectGuarFee) {
        this.collectGuarFee = collectGuarFee;
    }

    public BigDecimal getCollectTotal() {
        return collectTotal;
    }

    public void setCollectTotal(BigDecimal collectTotal) {
        this.collectTotal = collectTotal;
    }

    public BigDecimal getRemainFine() {
        return remainFine;
    }

    public void setRemainFine(BigDecimal remainFine) {
        this.remainFine = remainFine;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public Boolean getIsClear() {
        return isClear;
    }

    public void setIsClear(Boolean isClear) {
        this.isClear = isClear;
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

    public PayState getPayState() {
        return payState;
    }

    public void setPayState(PayState payState) {
        this.payState = payState;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public Boolean getReplaceState() {
        return replaceState;
    }

    public void setReplaceState(Boolean replaceState) {
        this.replaceState = replaceState;
    }

    public Integer getLoanee() {
        return loanee;
    }

    public void setLoanee(Integer loanee) {
        this.loanee = loanee;
    }

    public String getInnerSeqNo() {
        return innerSeqNo;
    }

    public void setInnerSeqNo(String innerSeqNo) {
        this.innerSeqNo = innerSeqNo;
    }

	public BigDecimal getPayBreachFine() {
		return payBreachFine;
	}

	public void setPayBreachFine(BigDecimal payBreachFine) {
		this.payBreachFine = payBreachFine;
	}

	public BigDecimal getPayCollectBreachFine() {
		return payCollectBreachFine;
	}

	public void setPayCollectBreachFine(BigDecimal payCollectBreachFine) {
		this.payCollectBreachFine = payCollectBreachFine;
	}

	public BigDecimal getRemainBreachFine() {
		return remainBreachFine;
	}

	public void setRemainBreachFine(BigDecimal remainBreachFine) {
		this.remainBreachFine = remainBreachFine;
	}

}
