package com.autoserve.abc.dao.dataobject.search;

import java.math.BigDecimal;
import java.util.Date;

public class GovReviewSearchDO {
    private Integer govId;

    /**
     * 被担保机构id，也就是govId
     * 只是用来查询用，如果此字段不为空，就只查询为此机构做担保的担保公司
     */
    private Integer guareeGovId;

    /**
     * 担保机构id，也就是govId
     * 只是用来查询用，如果此字段不为空，就只查询此担保公司向哪些小贷公司提供了服务，将这些机构查询出来
     */
    private Integer guarGovId;

    /**
     * 排除的机构id
     */
    private Integer excludeGovId;

    /**
     * 机构名称
     */
    private String govName;

    /**
     * 机构用户名
     */
    private String govUserName;

    /**
     * 客户经理名
     */
    private String govCustomerManagerName;

    /**
     * 所属地区
     */
    private String govArea;

    /**
     * 父级地区
     */
    private String superArea;

    /**
     * 最大贷款额度起始值
     */
    private BigDecimal maxLoanMoneyStart;

    /**
     * 最大贷款额度结束值
     */
    private BigDecimal maxLoanMoneyEnd;

    /**
     * 是否提供担保 0：否 1：是
     */
    private Integer isOfferGuar;

    /**
     * 是否启用	1：启用 0：停用 2 : 已删除
     */
    private Integer govIsEnable;

    /**
     * 机构状态	0：待审核 1：审核已通过 2：审核未通过
     */
    private Integer govState;

    /**
     * 修改开始日期
     */
    private Date updateStartDate;

    /**
     * 修改结束日期
     */
    private Date updateEndDate;

    /**
     * 更新审核搜索标识
     */
    private Boolean updateSearchFlag;

    public Boolean getUpdateSearchFlag() {
        return updateSearchFlag;
    }

    public void setUpdateSearchFlag(Boolean updateSearchFlag) {
        this.updateSearchFlag = updateSearchFlag;
    }

    public Integer getGovId() {
        return govId;
    }

    public void setGovId(Integer govId) {
        this.govId = govId;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getGovUserName() {
        return govUserName;
    }

    public void setGovUserName(String govUserName) {
        this.govUserName = govUserName;
    }

    public String getGovCustomerManagerName() {
        return govCustomerManagerName;
    }

    public void setGovCustomerManagerName(String govCustomerManagerName) {
        this.govCustomerManagerName = govCustomerManagerName;
    }

    public String getGovArea() {
        return govArea;
    }

    public void setGovArea(String govArea) {
        this.govArea = govArea;
    }

    public String getSuperArea() {
        return superArea;
    }

    public void setSuperArea(String superArea) {
        this.superArea = superArea;
    }

    public BigDecimal getMaxLoanMoneyStart() {
        return maxLoanMoneyStart;
    }

    public void setMaxLoanMoneyStart(BigDecimal maxLoanMoneyStart) {
        this.maxLoanMoneyStart = maxLoanMoneyStart;
    }

    public BigDecimal getMaxLoanMoneyEnd() {
        return maxLoanMoneyEnd;
    }

    public void setMaxLoanMoneyEnd(BigDecimal maxLoanMoneyEnd) {
        this.maxLoanMoneyEnd = maxLoanMoneyEnd;
    }

    public Integer getGovIsEnable() {
        return govIsEnable;
    }

    public void setGovIsEnable(Integer govIsEnable) {
        this.govIsEnable = govIsEnable;
    }

    public Integer getGovState() {
        return govState;
    }

    public void setGovState(Integer govState) {
        this.govState = govState;
    }

    public Date getUpdateStartDate() {
        return updateStartDate;
    }

    public void setUpdateStartDate(Date updateStartDate) {
        this.updateStartDate = updateStartDate;
    }

    public Date getUpdateEndDate() {
        return updateEndDate;
    }

    public void setUpdateEndDate(Date updateEndDate) {
        this.updateEndDate = updateEndDate;
    }

    public Integer getGuareeGovId() {
        return guareeGovId;
    }

    public void setGuareeGovId(Integer guareeGovId) {
        this.guareeGovId = guareeGovId;
    }

    public Integer getGuarGovId() {
        return guarGovId;
    }

    public void setGuarGovId(Integer guarGovId) {
        this.guarGovId = guarGovId;
    }

    public Integer getIsOfferGuar() {
        return isOfferGuar;
    }

    public void setIsOfferGuar(Integer isOfferGuar) {
        this.isOfferGuar = isOfferGuar;
    }

    public Integer getExcludeGovId() {
        return excludeGovId;
    }

    public void setExcludeGovId(Integer excludeGovId) {
        this.excludeGovId = excludeGovId;
    }
}


