/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 转让标连表信息
 *
 * @author segen189 2014年12月23日 下午3:44:18
 */
public class TransferLoanJDO extends TransferLoanDO {
    //----------------------------------------------------------------//
    //----------------loan 表字段信息开始--------------------------------//
    //----------------------------------------------------------------//
	
    /**
     * 项目编号 abc_loan.loan_no
     */
    private String     loanNo;

    /**
     * 借款人类型 1:个人 2:企业 3:借款结构 4:平台 abc_loan.loan_emp_type
     */
    private Integer    loanEmpType;

    /**
     * 借款人id abc_loan.loan_user_id
     */
    private Integer    loanUserId;

    /**
     * 借款机构id 外键 abc_loan.loan_gov
     */
    private Integer    loanGov;

    /**
     * 担保机构id 外键 abc_loan.loan_guar_gov
     */
    private Integer    loanGuarGov;

    /**
     * 借款金额 abc_loan.loan_money
     */
    private BigDecimal loanMoney;

    /**
     * 年化收益率 abc_loan.loan_rate
     */
    private BigDecimal loanRate;

    /**
     * 借款期限 abc_loan.loan_period
     */
    private Integer    loanPeriod;

    /**
     * 期限类型 1:年 2:月 3:日 abc_loan.loan_period_type
     */
    private Integer    loanPeriodType;

    /**
     * 还款方式 1:等额本息 2:按月还息到月还本 3:等额本金 abc_loan.loan_pay_type
     */
    private Integer    loanPayType;

    /**
     * 投资开始时间 abc_loan.loan_invest_starttime
     */
    private Date       loanInvestStarttime;

    /**
     * 投资结束时间 abc_loan.loan_invest_endtime
     */
    private Date       loanInvestEndtime;

    /**
     * 投资满标时间 abc_loan.loan_invest_fulltime
     */
    private Date       loanInvestFulltime;

    /**
     * 结算方式 1:固定还款日 2:非固定还款日 abc_loan.loan_clear_type
     */
    private Integer    loanClearType;

    /**
     * 项目分类 1:企业经营贷 2:房屋抵押贷 3:汽车抵押贷 4:个人轻松贷 abc_loan.loan_category
     */
    private Integer    loanCategory;

    /**
     * 项目分类 外键 abc_loan.loan_category_id
     */
    private Integer    loanCategoryId;

    /**
     * 附件url
     */
    private String     loanFileUrl;

    /**
     * 期限
     */
    private Integer		timelimit;
    /**
     * 项目到期日
     */
    private Date loanExpireDate;
    /**
     * 原始项目状态
     */
    private Integer loanState;
    
   

	public Integer getLoanState() {
		return loanState;
	}

	public void setLoanState(Integer loanState) {
		this.loanState = loanState;
	}

	public Date getLoanExpireDate() {
		return loanExpireDate;
	}

	public void setLoanExpireDate(Date loanExpireDate) {
		this.loanExpireDate = loanExpireDate;
	}

	public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public Integer getLoanEmpType() {
        return loanEmpType;
    }

    public void setLoanEmpType(Integer loanEmpType) {
        this.loanEmpType = loanEmpType;
    }

    public Integer getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(Integer loanUserId) {
        this.loanUserId = loanUserId;
    }

    public Integer getLoanGov() {
        return loanGov;
    }

    public void setLoanGov(Integer loanGov) {
        this.loanGov = loanGov;
    }

    public Integer getLoanGuarGov() {
        return loanGuarGov;
    }

    public void setLoanGuarGov(Integer loanGuarGov) {
        this.loanGuarGov = loanGuarGov;
    }

    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public Integer getLoanPeriodType() {
        return loanPeriodType;
    }

    public void setLoanPeriodType(Integer loanPeriodType) {
        this.loanPeriodType = loanPeriodType;
    }

    public Integer getLoanPayType() {
        return loanPayType;
    }

    public void setLoanPayType(Integer loanPayType) {
        this.loanPayType = loanPayType;
    }

    public Date getLoanInvestStarttime() {
        return loanInvestStarttime;
    }

    public void setLoanInvestStarttime(Date loanInvestStarttime) {
        this.loanInvestStarttime = loanInvestStarttime;
    }

    public Date getLoanInvestEndtime() {
        return loanInvestEndtime;
    }

    public void setLoanInvestEndtime(Date loanInvestEndtime) {
        this.loanInvestEndtime = loanInvestEndtime;
    }

    public Date getLoanInvestFulltime() {
        return loanInvestFulltime;
    }

    public void setLoanInvestFulltime(Date loanInvestFulltime) {
        this.loanInvestFulltime = loanInvestFulltime;
    }

    public Integer getLoanClearType() {
        return loanClearType;
    }

    public void setLoanClearType(Integer loanClearType) {
        this.loanClearType = loanClearType;
    }

    public Integer getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(Integer loanCategory) {
        this.loanCategory = loanCategory;
    }

    public Integer getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(Integer loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
    }

    public String getLoanFileUrl() {
        return loanFileUrl;
    }

    public void setLoanFileUrl(String loanFileUrl) {
        this.loanFileUrl = loanFileUrl;
    }

	public Integer getTimelimit() {
		return timelimit;
	}

	public void setTimelimit(Integer timelimit) {
		this.timelimit = timelimit;
	}

}
