/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue Mar 10 16:45:23 CST 2015
 * Description:
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.InvestSetOpenState;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPayType;

/**
 * InvestSet abc_invest_set
 */
public class InvestSet {
    /**
     * abc_invest_set.is_id
     */
    private Integer            id;

    /**
     * 用户ID abc_invest_set.is_user_id
     */
    private Integer            userId;

    /**
     * 最小借款金额 abc_invest_set.is_min_money
     */
    private BigDecimal         minMoney;

    /**
     * 最大借款金额 abc_invest_set.is_max_money
     */
    private BigDecimal         maxMoney;

    /**
     * 最小年化利率 abc_invest_set.is_min_rate
     */
    private BigDecimal         minRate;

    /**
     * 最大年化利率 abc_invest_set.is_max_rate
     */
    private BigDecimal         maxRate;

    /**
     * 借款期限 abc_invest_set.is_loan_period
     */
    private Integer            maxLoanPeriod;
    /**
     * 借款期限 abc_invest_set.is_loan_period
     */
    private Integer            minLoanPeriod;

    /**
     * 还款方式 0：所有 1：等额本息 2：按月还息，到期还本 abc_invest_set.is_loan_type
     */
    private LoanPayType        loanType;

    /**
     * 每笔投资金额 abc_invest_set.is_invest_money
     */
    private BigDecimal         investMoney;

    /**
     * 账户保留余额 abc_invest_set.is_sett_money
     */
    private BigDecimal         settMoney;

    /**
     * 是否开启 1：已开启 0：已关闭 abc_invest_set.is_is_open
     */
    private InvestSetOpenState isOpen;

    /**
     * 项目分类
     */
    private LoanCategory       loanCategory;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(BigDecimal minMoney) {
        this.minMoney = minMoney;
    }

    public BigDecimal getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(BigDecimal maxMoney) {
        this.maxMoney = maxMoney;
    }

    public BigDecimal getMinRate() {
        return minRate;
    }

    public void setMinRate(BigDecimal minRate) {
        this.minRate = minRate;
    }

    public BigDecimal getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(BigDecimal maxRate) {
        this.maxRate = maxRate;
    }

    public Integer getMaxLoanPeriod() {
        return maxLoanPeriod;
    }

    public void setMaxLoanPeriod(Integer maxLoanPeriod) {
        this.maxLoanPeriod = maxLoanPeriod;
    }

    public Integer getMinLoanPeriod() {
        return minLoanPeriod;
    }

    public void setMinLoanPeriod(Integer minLoanPeriod) {
        this.minLoanPeriod = minLoanPeriod;
    }

    public LoanPayType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanPayType loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(BigDecimal investMoney) {
        this.investMoney = investMoney;
    }

    public BigDecimal getSettMoney() {
        return settMoney;
    }

    public void setSettMoney(BigDecimal settMoney) {
        this.settMoney = settMoney;
    }

    public InvestSetOpenState getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(InvestSetOpenState isOpen) {
        this.isOpen = isOpen;
    }

    public LoanCategory getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(LoanCategory loanCategory) {
        this.loanCategory = loanCategory;
    }

}
