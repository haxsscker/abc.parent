package com.autoserve.abc.dao.dataobject.stage.myinvest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的投资列表 回款中
 * 
 * @author liuwei 2015年1月26日 下午12:04:41
 */
public class MyInvestReceived {
	/**
	 *投资ID
	 */
	 private int  investId;
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
     * 投资金额
     */
    private BigDecimal investMoney;
    /**
     * 待收本金
     */
    private BigDecimal DsMoney;
    /**
     * 投资时间
     */
    private Date       investDate;
    /**
     * 下期回款日
     */
    private Date       receivedDate;
    /**
     * 下期回款金额
     */
    private BigDecimal receivedMoney;
    /**
     * 到期日
     */
    private Date       endDate;
    /**
     * 
     * 转让状态
     */
    private Integer transferState;
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

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public BigDecimal getReceivedMoney() {
        return receivedMoney;
    }

    public void setReceivedMoney(BigDecimal receivedMoney) {
        this.receivedMoney = receivedMoney;
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

	public int getInvestId() {
		return investId;
	}

	public void setInvestId(int investId) {
		this.investId = investId;
	}

	public BigDecimal getDsMoney() {
		return DsMoney;
	}

	public void setDsMoney(BigDecimal dsMoney) {
		DsMoney = dsMoney;
	}

	public Integer getTransferState() {
		return transferState;
	}

	public void setTransferState(Integer transferState) {
		this.transferState = transferState;
	}
    
}
