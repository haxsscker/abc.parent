/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue Dec 02 13:24:46 CST 2014
 * Description:
 */
package com.autoserve.abc.dao.dataobject.search;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  FundApplyDO
 *  abc_fund_apply
 */
public class FundApplySearchDO {
    /**
     * 主键ID
     * abc_fund_apply.fa_fund_id
     */
    private Integer faFundId;

    /**
     * 目项编号
     * abc_fund_apply.fa_fund_no
     */
    private String faFundNo;

    /**
     * 基金名称
     * abc_fund_apply.fa_fund_name
     */
    private String faFundName;

    /**
     * 基金公司
     * abc_fund_apply.fa_fund_comp
     */
    private String faFundComp;

    /**
     * 预计发行规模
     * abc_fund_apply.fa_fund_money
     */
    private BigDecimal faFundMoney;

    /**
     * 存续期
     * abc_fund_apply.fa_fund_period
     */
    private BigDecimal faFundPeriod;

    /**
     * 预期年收益率
     * abc_fund_apply.fa_fund_rate
     */
    private BigDecimal faFundRate;

    /**
     * 最低认购金额
     * abc_fund_apply.fa_min_money
     */
    private BigDecimal faMinMoney;

    /**
     * 投资行业
     * abc_fund_apply.fa_fund_industry
     */
    private String faFundIndustry;

    /**
     * 还款方式(1：等额本息 2：按月还息到期还本 3：等额本金)
     * abc_fund_apply.fa_pay_type
     */
    private Integer faPayType;

    /**
     * 基金类型
     * abc_fund_apply.fa_fund_type
     */
    private String faFundType;

    /**
     * 抵押率
     * abc_fund_apply.fa_martgage_rate
     */
    private BigDecimal faMartgageRate;

    /**
     * 抵押物
     * abc_fund_apply.fa_martgage_introl
     */
    private String faMartgageIntrol;

    /**
     * 产品说明
     * abc_fund_apply.fa_fund_introl
     */
    private String faFundIntrol;

    /**
     * 资金用途
     * abc_fund_apply.fa_fund_use
     */
    private String faFundUse;

    /**
     * 风险控制
     * abc_fund_apply.fa_fund_risk
     */
    private String faFundRisk;

    /**
     * 还款来源
     * abc_fund_apply.fa_pay_resource
     */
    private String faPayResource;

    /**
     * 项目简介
     * abc_fund_apply.fa_project_introl
     */
    private String faProjectIntrol;

    /**
     * 基金公司简介
     * abc_fund_apply.fa_comp_introl
     */
    private String faCompIntrol;

    /**
     * 是否发布
     * abc_fund_apply.fa_fund_state
     */
    private Integer faFundState;

    /**
     * 发售日期
     * abc_fund_apply.fa_add_date
     */
    private Date faAddDate;

    /**
     * 预计发行规模起始值
     * abc_fund_apply.fa_fund_money
     */
    private BigDecimal faFundMoneyFrom;
    
    /**
     * 预计发行规模终了值
     * abc_fund_apply.fa_fund_money
     */
    private BigDecimal faFundMoneyTo;
    
    /**
     * 发售日期开始日
     * abc_fund_apply.fa_add_date
     */
    private Date faAddDateFrom;
    
    /**
     * 发售日期终了日
     * abc_fund_apply.fa_add_date
     */
    private Date faAddDateTo;

    public Integer getFaFundId() {
        return faFundId;
    }

    public void setFaFundId(Integer faFundId) {
        this.faFundId = faFundId;
    }

    public String getFaFundNo() {
        return faFundNo;
    }

    public void setFaFundNo(String faFundNo) {
        this.faFundNo = faFundNo;
    }

    public String getFaFundName() {
        return faFundName;
    }

    public void setFaFundName(String faFundName) {
        this.faFundName = faFundName;
    }

    public String getFaFundComp() {
        return faFundComp;
    }

    public void setFaFundComp(String faFundComp) {
        this.faFundComp = faFundComp;
    }

    public BigDecimal getFaFundMoney() {
        return faFundMoney;
    }

    public void setFaFundMoney(BigDecimal faFundMoney) {
        this.faFundMoney = faFundMoney;
    }

    public BigDecimal getFaFundPeriod() {
        return faFundPeriod;
    }

    public void setFaFundPeriod(BigDecimal faFundPeriod) {
        this.faFundPeriod = faFundPeriod;
    }

    public BigDecimal getFaFundRate() {
        return faFundRate;
    }

    public void setFaFundRate(BigDecimal faFundRate) {
        this.faFundRate = faFundRate;
    }

    public BigDecimal getFaMinMoney() {
        return faMinMoney;
    }

    public void setFaMinMoney(BigDecimal faMinMoney) {
        this.faMinMoney = faMinMoney;
    }

    public String getFaFundIndustry() {
        return faFundIndustry;
    }

    public void setFaFundIndustry(String faFundIndustry) {
        this.faFundIndustry = faFundIndustry;
    }

    public Integer getFaPayType() {
        return faPayType;
    }

    public void setFaPayType(Integer faPayType) {
        this.faPayType = faPayType;
    }

    public String getFaFundType() {
        return faFundType;
    }

    public void setFaFundType(String faFundType) {
        this.faFundType = faFundType;
    }

    public BigDecimal getFaMartgageRate() {
        return faMartgageRate;
    }

    public void setFaMartgageRate(BigDecimal faMartgageRate) {
        this.faMartgageRate = faMartgageRate;
    }

    public String getFaMartgageIntrol() {
        return faMartgageIntrol;
    }

    public void setFaMartgageIntrol(String faMartgageIntrol) {
        this.faMartgageIntrol = faMartgageIntrol;
    }

    public String getFaFundIntrol() {
        return faFundIntrol;
    }

    public void setFaFundIntrol(String faFundIntrol) {
        this.faFundIntrol = faFundIntrol;
    }

    public String getFaFundUse() {
        return faFundUse;
    }

    public void setFaFundUse(String faFundUse) {
        this.faFundUse = faFundUse;
    }

    public String getFaFundRisk() {
        return faFundRisk;
    }

    public void setFaFundRisk(String faFundRisk) {
        this.faFundRisk = faFundRisk;
    }

    public String getFaPayResource() {
        return faPayResource;
    }

    public void setFaPayResource(String faPayResource) {
        this.faPayResource = faPayResource;
    }

    public String getFaProjectIntrol() {
        return faProjectIntrol;
    }

    public void setFaProjectIntrol(String faProjectIntrol) {
        this.faProjectIntrol = faProjectIntrol;
    }

    public String getFaCompIntrol() {
        return faCompIntrol;
    }

    public void setFaCompIntrol(String faCompIntrol) {
        this.faCompIntrol = faCompIntrol;
    }

    public Integer getFaFundState() {
        return faFundState;
    }

    public void setFaFundState(Integer faFundState) {
        this.faFundState = faFundState;
    }

    public Date getFaAddDate() {
        return faAddDate;
    }

    public void setFaAddDate(Date faAddDate) {
        this.faAddDate = faAddDate;
    }

	public BigDecimal getFaFundMoneyFrom() {
		return faFundMoneyFrom;
	}

	public void setFaFundMoneyFrom(BigDecimal faFundMoneyFrom) {
		this.faFundMoneyFrom = faFundMoneyFrom;
	}

	public BigDecimal getFaFundMoneyTo() {
		return faFundMoneyTo;
	}

	public void setFaFundMoneyTo(BigDecimal faFundMoneyTo) {
		this.faFundMoneyTo = faFundMoneyTo;
	}

	public Date getFaAddDateFrom() {
		return faAddDateFrom;
	}

	public void setFaAddDateFrom(Date faAddDateFrom) {
		this.faAddDateFrom = faAddDateFrom;
	}

	public Date getFaAddDateTo() {
		return faAddDateTo;
	}

	public void setFaAddDateTo(Date faAddDateTo) {
		this.faAddDateTo = faAddDateTo;
	}
}
