package com.autoserve.abc.dao.dataobject.stage.myborrow;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的借款列表 （投标中）
 * 
 * @author liuwei 2015年1月26日 下午4:42:13
 */
public class MyBorrowTender {

    /**
     * 项目ID
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
     * 项目金额
     */
    private BigDecimal loanMoney;

    /**
     * 期限
     */
    private int        loanPeriod;
    /**
     * 期限单位
     */
    private int        loanPeriodType;
    /**
     * 申请时间
     */
    private Date       applyDate;
    /**
     * 投标成交时间
     */
    private Date       successDate;
    /**
     * 投资金额
     */
    private BigDecimal investMoney;
    /**
     * 到期日
     */
    private Date       endDate;

    /**
     * 状态
     */
    private int        state;
    /**
     * 投资进度
     */
    private Integer investProgress;

    public Integer getInvestProgress() {
		return investProgress;
	}

	public void setInvestProgress(Integer investProgress) {
		this.investProgress = investProgress;
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

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getSuccessDate() {
        return successDate;
    }

    public void setSuccessDate(Date successDate) {
        this.successDate = successDate;
    }

    public BigDecimal getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(BigDecimal investMoney) {
        this.investMoney = investMoney;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

}
