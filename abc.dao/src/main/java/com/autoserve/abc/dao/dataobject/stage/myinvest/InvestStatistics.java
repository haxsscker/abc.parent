package com.autoserve.abc.dao.dataobject.stage.myinvest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投资统计
 * 
 * @author liuwei 2015年1月26日 下午7:38:17
 */
public class InvestStatistics {
	 /**
     * 项目ID
     */
    private int        loanId;
    /**
     * 项目名称
     */
    private String     loanNo;
    
    /**
     * 指定到期日 abc_loan.loan_expire_date
     */
    private Date       loanExpireDate;
    
    /**
     * 已回款
     */
    private BigDecimal alreadyTotalMoney;
    /**
     * 已回利息
     */
    private BigDecimal alreadyInterest;
    /**
     * 已回本金
     */
    private BigDecimal alreadyCapital;
    /**
     * 待回款
     */
    private BigDecimal waitTotalMoney;
    /**
     * 待回利息
     */
    private BigDecimal waitInterest;
    /**
     * 待回本金
     */
    private BigDecimal waitCapital;
    /**
     * 总额
     */
    private BigDecimal totalMoney;

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public BigDecimal getAlreadyTotalMoney() {
        return alreadyTotalMoney;
    }

    public void setAlreadyTotalMoney(BigDecimal alreadyTotalMoney) {
        this.alreadyTotalMoney = alreadyTotalMoney;
    }

    public BigDecimal getAlreadyInterest() {
        return alreadyInterest;
    }

    public void setAlreadyInterest(BigDecimal alreadyInterest) {
        this.alreadyInterest = alreadyInterest;
    }

    public BigDecimal getAlreadyCapital() {
        return alreadyCapital;
    }

    public void setAlreadyCapital(BigDecimal alreadyCapital) {
        this.alreadyCapital = alreadyCapital;
    }

    public BigDecimal getWaitTotalMoney() {
        return waitTotalMoney;
    }

    public void setWaitTotalMoney(BigDecimal waitTotalMoney) {
        this.waitTotalMoney = waitTotalMoney;
    }

    public BigDecimal getWaitInterest() {
        return waitInterest;
    }

    public void setWaitInterest(BigDecimal waitInterest) {
        this.waitInterest = waitInterest;
    }

    public BigDecimal getWaitCapital() {
        return waitCapital;
    }

    public void setWaitCapital(BigDecimal waitCapital) {
        this.waitCapital = waitCapital;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
    
	public Date getLoanExpireDate() {
        return loanExpireDate;
    }

    public void setLoanExpireDate(Date loanExpireDate) {
        this.loanExpireDate = loanExpireDate;
    }
}
