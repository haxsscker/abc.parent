package com.autoserve.abc.dao.dataobject.stage.myborrow;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的借款列表（已结清）
 * 
 * @author liuwei 2015年1月26日 下午4:42:13
 */
public class MyBorrowClean {
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
     * 还款本金
     */
    private BigDecimal        payCapital;

    /**
     * 还款利息
     */
    private BigDecimal       payInterest;
    /**
     * 还款罚息
     */
    private BigDecimal       payFine;
    /**
     * 结清日期
     */
    private Date       endDate;

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

	public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
