package com.autoserve.abc.web.module.screen.account.myLoan;

import java.math.BigDecimal;
import java.util.Date;

public class LoanVO {
    /**
     * 主键id
     */
    private Integer    loanId;
    /**
     * 项目名称
     */
    private String     loanNo;
    /**
     * 借款金额
     */
    private BigDecimal loanMoney;
    /**
     * 创建时间
     */
    private Date       loanCreatetime;
    /**
     * 年化收益率
     */
    private BigDecimal loanRate;
    /**
     * 下次还款日
     */
    private Date       nextRepaytime;
    /**
     * 还款总期数
     */
    private Integer    repayConuts;
    /**
     * 应还总额
     */
    private BigDecimal repayMoney;
    /**
     * 到期日期
     */
    private Date       endTime;
    /**
     * 满标时间
     */
    private Date       loanInvestFulltime;

    public Date getLoanInvestFulltime() {
        return loanInvestFulltime;
    }

    public void setLoanInvestFulltime(Date loanInvestFulltime) {
        this.loanInvestFulltime = loanInvestFulltime;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
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

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public Date getLoanCreatetime() {
        return loanCreatetime;
    }

    public void setLoanCreatetime(Date loanCreatetime) {
        this.loanCreatetime = loanCreatetime;
    }

    public Date getNextRepaytime() {
        return nextRepaytime;
    }

    public void setNextRepaytime(Date nextRepaytime) {
        this.nextRepaytime = nextRepaytime;
    }

    public Integer getRepayConuts() {
        return repayConuts;
    }

    public void setRepayConuts(Integer repayConuts) {
        this.repayConuts = repayConuts;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getRepayMoney() {
        return repayMoney;
    }

    public void setRepayMoney(BigDecimal repayMoney) {
        this.repayMoney = repayMoney;
    }

}
