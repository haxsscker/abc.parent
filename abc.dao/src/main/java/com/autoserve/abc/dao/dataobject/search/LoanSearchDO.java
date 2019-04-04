/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject.search;

import java.util.Date;
import java.util.List;

/**
 * 普通标查询条件
 *
 * @author segen189 2014年12月24日 下午9:45:15
 */
public class LoanSearchDO {
    /**
     * 项目编号
     */
    private String        loanNo;

    /**
     * 借款人id
     */
    private Integer       loaneeId;

    /**
     * 借款人类型
     */
    private Integer       loaneeType;

    /**
     * 借款人姓名
     */
    private String        loaneeName;

    /**
     * 担保机构名称
     */
    private String        guarGovName;

    /**
     * 担保机构id
     */
    private Integer       guarGovId;

    /**
     * 项目分类
     */
    private Integer       loanCategory;

    /**
     * 申请日期区间开始
     */
    private Date          applyDateStart;

    /**
     * 申请日期区间结束
     */
    private Date          applyDateEnd;

    /**
     * 借款金额区间开始
     */
    private Double        loanMoneyStart;

    /**
     * 借款金额区间结束
     */
    private Double        loanMoneyEnd;

    /**
     * 借款状态
     */
    private List<Integer> loanState;

    /**
     * 不包括的借款状态
     */
    private List<Integer> excludeLoanState;

    /**
     * 是否来源前台意向
     */
    private Boolean       isFromIntent;
    /**
     * 投资开始时间
     */
    private Date           loanInvestStarttime;

    /**
     * 投资结束时间
     */
    private Date           loanInvestEndtime;
    /**
     * 0或null表示前台、1表示后台（区分前后台的排序条件）
     */
    private Integer       flag;
    
    //查询是否定向标
    private Integer 	isShow;

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public Integer getLoaneeId() {
        return loaneeId;
    }

    public void setLoaneeId(Integer loaneeId) {
        this.loaneeId = loaneeId;
    }

    public Integer getLoaneeType() {
        return loaneeType;
    }

    public void setLoaneeType(Integer loaneeType) {
        this.loaneeType = loaneeType;
    }

    public String getLoaneeName() {
        return loaneeName;
    }

    public void setLoaneeName(String loaneeName) {
        this.loaneeName = loaneeName;
    }

    public String getGuarGovName() {
        return guarGovName;
    }

    public void setGuarGovName(String guarGovName) {
        this.guarGovName = guarGovName;
    }

    public Integer getGuarGovId() {
        return guarGovId;
    }

    public void setGuarGovId(Integer guarGovId) {
        this.guarGovId = guarGovId;
    }

    public Integer getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(Integer loanCategory) {
        this.loanCategory = loanCategory;
    }

    public Date getApplyDateStart() {
        return applyDateStart;
    }

    public void setApplyDateStart(Date applyDateStart) {
        this.applyDateStart = applyDateStart;
    }

    public Date getApplyDateEnd() {
        return applyDateEnd;
    }

    public void setApplyDateEnd(Date applyDateEnd) {
        this.applyDateEnd = applyDateEnd;
    }

    public Double getLoanMoneyStart() {
        return loanMoneyStart;
    }

    public void setLoanMoneyStart(Double loanMoneyStart) {
        this.loanMoneyStart = loanMoneyStart;
    }

    public Double getLoanMoneyEnd() {
        return loanMoneyEnd;
    }

    public void setLoanMoneyEnd(Double loanMoneyEnd) {
        this.loanMoneyEnd = loanMoneyEnd;
    }

    public List<Integer> getLoanState() {
        return loanState;
    }

    public List<Integer> getExcludeLoanState() {
        return excludeLoanState;
    }

    public void setExcludeLoanState(List<Integer> excludeLoanState) {
        this.excludeLoanState = excludeLoanState;
    }

    public void setLoanState(List<Integer> loanState) {
        this.loanState = loanState;
    }

    public Boolean getIsFromIntent() {
        return isFromIntent;
    }

    public void setIsFromIntent(Boolean isFromIntent) {
        this.isFromIntent = isFromIntent;
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
}
