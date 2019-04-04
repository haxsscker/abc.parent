/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.autoserve.abc.service.biz.enums.TransferLoanState;

/**
 * 转让标
 * 
 * @author segen189 2014年11月22日 上午9:41:51
 */
public class TransferLoan {
    /**
     * 主键id
     */
    private Integer           id;

    /**
     * 原始贷款id
     */
    private Integer           originId;

    /**
     * 投资id
     */
    private Integer           investId;

    /**
     * 转让人
     */
    private Integer           userId;

    /**
     * 转让债权总额
     */
    private BigDecimal        transferTotal;

    /**
     * 转让金额
     */
    private BigDecimal        transferMoney;

    /**
     * 转让后利率
     */
    private BigDecimal        transferRate;

    /**
     * 转让手续费
     */
    private BigDecimal        transferFee;

    /**
     * 转让折让率
     */
    private BigDecimal        transferDiscountRate;

    /**
     * 转让折让费
     */
    private BigDecimal        transferDiscountFee;

    /**
     * 转让期数 比如：12期的收回计划表，还了3期，第4期转让，那么转让期数=12-3 = 9（期）
     */
    private Integer           transferPeriod;

    /**
     * 转让状态
     */
    private TransferLoanState transferLoanState;
    private List<TransferLoanState> transferLoanStates;

    /**
     * 转让申请日期 1、当转让人部分转让时，存在剩余金额，且同一笔贷款时，若第一笔债权未满标时，不允许转让第二次；\n2、当未放款前，借款人还钱了，
     * 那么该转让申请作废，投资人钱重新进行返还，借款人把钱还给原债权人
     */
    private Date              createtime;

    /**
     * 修改日期
     */
    private Date              modifytime;

    /**
     * 投资开始时间
     */
    private Date              investStarttime;

    /**
     * 投资结束时间
     */
    private Date              investEndtime;

    /**
     * 满标日期
     */
    private Date              fulltime;

    /**
     * 放款成功时间
     */
    private Date              fullTransferedtime;

    /**
     * 当前投标总额
     */
    private BigDecimal        currentInvest;

    /**
     * 当前有效投标总额
     */
    private BigDecimal        currentValidInvest;

    /**
     * 下一次还款计划id
     */
    private Integer           nextPaymentId;
    /**
     * 转让项目编码
     */
    private String            transferLoanNo;
    /**
     * 转让标发布时间
     */
    private Date releaseDate;

    public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public Integer getInvestId() {
        return investId;
    }

    public void setInvestId(Integer investId) {
        this.investId = investId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTransferTotal() {
        return transferTotal;
    }

    public void setTransferTotal(BigDecimal transferTotal) {
        this.transferTotal = transferTotal;
    }

    public BigDecimal getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(BigDecimal transferMoney) {
        this.transferMoney = transferMoney;
    }

    public BigDecimal getTransferRate() {
        return transferRate;
    }

    public void setTransferRate(BigDecimal transferRate) {
        this.transferRate = transferRate;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public BigDecimal getTransferDiscountRate() {
        return transferDiscountRate;
    }

    public void setTransferDiscountRate(BigDecimal transferDiscountRate) {
        this.transferDiscountRate = transferDiscountRate;
    }

    public BigDecimal getTransferDiscountFee() {
        return transferDiscountFee;
    }

    public void setTransferDiscountFee(BigDecimal transferDiscountFee) {
        this.transferDiscountFee = transferDiscountFee;
    }

    public Integer getTransferPeriod() {
        return transferPeriod;
    }

    public void setTransferPeriod(Integer transferPeriod) {
        this.transferPeriod = transferPeriod;
    }

    public TransferLoanState getTransferLoanState() {
        return transferLoanState;
    }

    public void setTransferLoanState(TransferLoanState transferLoanState) {
        this.transferLoanState = transferLoanState;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public Date getInvestStarttime() {
        return investStarttime;
    }

    public void setInvestStarttime(Date investStarttime) {
        this.investStarttime = investStarttime;
    }

    public Date getInvestEndtime() {
        return investEndtime;
    }

    public void setInvestEndtime(Date investEndtime) {
        this.investEndtime = investEndtime;
    }

    public Date getFulltime() {
        return fulltime;
    }

    public void setFulltime(Date fulltime) {
        this.fulltime = fulltime;
    }

    public Date getFullTransferedtime() {
        return fullTransferedtime;
    }

    public void setFullTransferedtime(Date fullTransferedtime) {
        this.fullTransferedtime = fullTransferedtime;
    }

    public BigDecimal getCurrentInvest() {
        return currentInvest;
    }

    public void setCurrentInvest(BigDecimal currentInvest) {
        this.currentInvest = currentInvest;
    }

    public BigDecimal getCurrentValidInvest() {
        return currentValidInvest;
    }

    public void setCurrentValidInvest(BigDecimal currentValidInvest) {
        this.currentValidInvest = currentValidInvest;
    }

    public Integer getNextPaymentId() {
        return nextPaymentId;
    }

    public void setNextPaymentId(Integer nextPaymentId) {
        this.nextPaymentId = nextPaymentId;
    }

    public String getTransferLoanNo() {
        return transferLoanNo;
    }

    public void setTransferLoanNo(String transferLoanNo) {
        this.transferLoanNo = transferLoanNo;
    }

	public List<TransferLoanState> getTransferLoanStates() {
		return transferLoanStates;
	}

	public void setTransferLoanStates(List<TransferLoanState> transferLoanStates) {
		this.transferLoanStates = transferLoanStates;
	}

}
