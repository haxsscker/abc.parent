package com.autoserve.abc.dao.dataobject.stage.myinvest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的债权回款中
 * 
 * @author liuwei 2015年1月26日 下午2:15:25
 */
public class MyCreditReceived {
    /**
     * 转让项目Id
     */
    private int        transferloanId;
    /**
     * 投资记录Id
     */
    private int        investId;
    /**
     * 项目Id
     */
    private int        loanId;

    /**
     * 项目名称
     */
    private String     loanNo;
    /**
     * 年利率
     */
    private BigDecimal loanRate;
    /**
     * 转让金额
     */
    private BigDecimal transferLoanMoney;
    /**
     * 成交日期
     */
    private Date       investDate;
    /**
     * 到期日
     */
    private Date       endDate;
    /**
     * 已回款
     */
    private BigDecimal cleanMoney;
    /**
     * 待回款
     */
    private BigDecimal receivedMoney;
    /**
     * 已转让
     */
    private BigDecimal transferMoney;
    /**
     * 
     * 转让状态
     */
    private Integer transferState;
    /**
     * 转让项目编码
     */
    private String            transferLoanNo;
    /**
     * 投标金额
     */
    private BigDecimal investMoney; //add by xiatt 20160704 前台-我的债券-回款中页面增加投标金额

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public BigDecimal getTransferLoanMoney() {
        return transferLoanMoney;
    }

    public void setTransferLoanMoney(BigDecimal transferLoanMoney) {
        this.transferLoanMoney = transferLoanMoney;
    }

    public Date getInvestDate() {
        return investDate;
    }

    public void setInvestDate(Date investDate) {
        this.investDate = investDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getCleanMoney() {
        return cleanMoney;
    }

    public void setCleanMoney(BigDecimal cleanMoney) {
        this.cleanMoney = cleanMoney;
    }

    public BigDecimal getReceivedMoney() {
        return receivedMoney;
    }

    public void setReceivedMoney(BigDecimal receivedMoney) {
        this.receivedMoney = receivedMoney;
    }

    public BigDecimal getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(BigDecimal transferMoney) {
        this.transferMoney = transferMoney;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public int getTransferloanId() {
        return transferloanId;
    }

    public void setTransferloanId(int transferloanId) {
        this.transferloanId = transferloanId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getInvestId() {
        return investId;
    }

    public void setInvestId(int investId) {
        this.investId = investId;
    }

	public Integer getTransferState() {
		return transferState;
	}

	public void setTransferState(Integer transferState) {
		this.transferState = transferState;
	}

	public String getTransferLoanNo() {
		return transferLoanNo;
	}

	public void setTransferLoanNo(String transferLoanNo) {
		this.transferLoanNo = transferLoanNo;
	}

	public BigDecimal getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(BigDecimal investMoney) {
		this.investMoney = investMoney;
	}
	
    
}
