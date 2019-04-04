package com.autoserve.abc.dao.dataobject.stage.myinvest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的投资列表 投标中
 * 
 * @author liuwei 2015年1月26日 下午12:04:41
 */
public class MyInvestTender {
    /**
     * 项目ID
     */
    private String     loanId;
    /**
     * 项目名称
     */
    private String     loanNo;
    /**
     * 年利率
     */
    private BigDecimal loanRate;
    /**
     * 项目金额
     */
    private BigDecimal loanMoney;
    /**
     * 投资金额
     */
    private BigDecimal investMoney;
    /**
     * 期限
     */
    private int        loanPeriod;
    /**
     * 期限单位
     */
    private int        loanPeriodType;
    /**
     * 还款方式
     */
    private int        loanPayType;
    /**
     * 投标时间
     */
    private Date       tenderDate;
    /**
     * 已投资金额
     */
    private BigDecimal validInvest;
    /**
     * 标状态
     */
    private int loanState;
    
    /**
     * 投资id
     */
    private int        investId;

    public int getLoanState() {
		return loanState;
	}

	public void setLoanState(int loanState) {
		this.loanState = loanState;
	}

	public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public int getLoanPeriodType() {
        return loanPeriodType;
    }

    public void setLoanPeriodType(int loanPeriodType) {
        this.loanPeriodType = loanPeriodType;
    }

    public int getLoanPayType() {
        return loanPayType;
    }

    public void setLoanPayType(int loanPayType) {
        this.loanPayType = loanPayType;
    }

    public Date getTenderDate() {
        return tenderDate;
    }

    public void setTenderDate(Date tenderDate) {
        this.tenderDate = tenderDate;
    }

    public BigDecimal getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(BigDecimal investMoney) {
        this.investMoney = investMoney;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getValidInvest() {
        return validInvest;
    }

    public void setValidInvest(BigDecimal validInvest) {
        this.validInvest = validInvest;
    }

	/**
	 * @return the investId
	 */
	public int getInvestId() {
		return investId;
	}

	/**
	 * @param investId the investId to set
	 */
	public void setInvestId(int investId) {
		this.investId = investId;
	}

}
