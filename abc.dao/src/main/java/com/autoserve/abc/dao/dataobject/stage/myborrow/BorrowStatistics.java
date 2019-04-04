package com.autoserve.abc.dao.dataobject.stage.myborrow;

import java.math.BigDecimal;

/**
 * 借款统计
 * 
 * @author liuwei 2015年1月26日 下午7:38:40
 */
public class BorrowStatistics {
	/**
	 * 项目编号
	 */
	private Integer     loanId;
    /**
     * 项目名称
     */
    private String     loanNo;
    /**
     * 借款金额
     */
    private BigDecimal loanMoney;
    /**
     * 借款期限
     */
    private BigDecimal loanPeriod;
    /**
     * 期限类型
     */
    private Integer    loanPeriodType;
    /**
     * 已还本息
     */
    private BigDecimal alreadyMoney;
    /**
     * 待还本息
     */
    private BigDecimal waitMoney;
    /**
     * 未还期数
     */
    private BigDecimal waitPeriod;

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

    public BigDecimal getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(BigDecimal loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public BigDecimal getAlreadyMoney() {
        return alreadyMoney;
    }

    public void setAlreadyMoney(BigDecimal alreadyMoney) {
        this.alreadyMoney = alreadyMoney;
    }

    public BigDecimal getWaitMoney() {
        return waitMoney;
    }

    public void setWaitMoney(BigDecimal waitMoney) {
        this.waitMoney = waitMoney;
    }

    public BigDecimal getWaitPeriod() {
        return waitPeriod;
    }

    public void setWaitPeriod(BigDecimal waitPeriod) {
        this.waitPeriod = waitPeriod;
    }

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getLoanPeriodType() {
		return loanPeriodType;
	}

	public void setLoanPeriodType(Integer loanPeriodType) {
		this.loanPeriodType = loanPeriodType;
	}
    
}
