package com.autoserve.abc.dao.dataobject.stage.myinvest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的债权投标中
 * 
 * @author liuwei 2015年1月26日 下午2:15:25
 */
public class MyCreditTender {
    /**
     * 转让项目Id
     */
    private int        transferloanId;
    /**
     * 项目Id
     */
    private int        loanId;
    /**
     * 项目名称
     */
    private String     LoanNo;
    /**
     * 年利率
     */
    private BigDecimal loanRate;
    /**
     * 转让金额
     */
    private BigDecimal transferLoanMoney;
    /**
     * 已转让金额
     */
    private BigDecimal transferMoney;
    /**
     * 到期日
     */
    private Date       endDate;
    /**
     * 投资日期
     * @return
     */
    private Date       createDate;
    /**
     * 当前有效投标总额
     * @return
     */
    private BigDecimal currentMoney;
    /**
     * 转让项目编码
     */
    private String            transferLoanNo;
    /**
     * 投资进度百分比
     */
    private BigDecimal percent;
    public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public String getLoanNo() {
        return LoanNo;
    }

    public void setLoanNo(String loanNo) {
        LoanNo = loanNo;
    }

    public BigDecimal getTransferLoanMoney() {
        return transferLoanMoney;
    }

    public void setTransferLoanMoney(BigDecimal transferLoanMoney) {
        this.transferLoanMoney = transferLoanMoney;
    }

    public BigDecimal getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(BigDecimal transferMoney) {
        this.transferMoney = transferMoney;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getCurrentMoney() {
		return currentMoney;
	}

	public void setCurrentMoney(BigDecimal currentMoney) {
		this.currentMoney = currentMoney;
	}

	public String getTransferLoanNo() {
		return transferLoanNo;
	}

	public void setTransferLoanNo(String transferLoanNo) {
		this.transferLoanNo = transferLoanNo;
	}
    
}
