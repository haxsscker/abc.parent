package com.autoserve.abc.dao.dataobject.stage.myinvest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的债权列表 已结清
 * 
 * @author liuwei 2015年1月26日 下午12:04:41
 */
public class MyCreditClean {
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
     * 债权总价
     */
    private BigDecimal creditTotalMoney;
    /**
     * 转让价格
     */
    private BigDecimal transferMoney;
    /**
     * 转让时间
     */
    private Date       transferDate;
    /**
     * 到期日
     */
    private Date       endDate;
    /**
     * 转让项目编码
     */
    private String            transferLoanNo;
    /**
     * 投资状态
     */
    private Integer investState;

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public BigDecimal getCreditTotalMoney() {
        return creditTotalMoney;
    }

    public void setCreditTotalMoney(BigDecimal creditTotalMoney) {
        this.creditTotalMoney = creditTotalMoney;
    }

    public BigDecimal getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(BigDecimal transferMoney) {
        this.transferMoney = transferMoney;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
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

	public String getTransferLoanNo() {
		return transferLoanNo;
	}

	public void setTransferLoanNo(String transferLoanNo) {
		this.transferLoanNo = transferLoanNo;
	}

	public Integer getInvestState() {
		return investState;
	}

	public void setInvestState(Integer investState) {
		this.investState = investState;
	}
    
}
