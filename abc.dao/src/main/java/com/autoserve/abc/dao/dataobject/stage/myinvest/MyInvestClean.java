package com.autoserve.abc.dao.dataobject.stage.myinvest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的投资列表 已回款
 * 
 * @author liuwei 2015年1月26日 下午12:04:41
 */
public class MyInvestClean {
    /**
     * 项目ID
     */
    private int        loanId;
    /**
     * 投资ID
     */
    private int        investId;
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
    private BigDecimal        investMoney;
    /**
     * 投资时间
     */
    private Date        investDate;
    /**
     * 回款金额
     */
    private BigDecimal   receivedMoney;
    /**
     * 回款日期
     */
    private Date       receivedDate;
    /**
     * 状态
     */
    private int        state;

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
    
	public BigDecimal getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(BigDecimal investMoney) {
		this.investMoney = investMoney;
	}

	public Date getInvestDate() {
		return investDate;
	}

	public void setInvestDate(Date investDate) {
		this.investDate = investDate;
	}

    public BigDecimal getReceivedMoney() {
		return receivedMoney;
	}

	public void setReceivedMoney(BigDecimal receivedMoney) {
		this.receivedMoney = receivedMoney;
	}

	public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
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

	public int getInvestId() {
		return investId;
	}

	public void setInvestId(int investId) {
		this.investId = investId;
	}
    
}
