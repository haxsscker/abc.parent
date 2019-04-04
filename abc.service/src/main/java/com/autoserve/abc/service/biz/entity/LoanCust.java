package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.CompanyType;
import com.autoserve.abc.service.biz.enums.IndustryType;
import com.autoserve.abc.service.biz.enums.ScaleType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-13,21:10
 */
public class LoanCust {
    /**
     * 主键id
     */
    private Integer           id;

    /**
     * 贷款id
     */
    private String            loanId;

    /**
     * 公司名称
     */
    private String            custName;

    /**
     * 公司类别
     */
    private CompanyType       custType;

    /**
     * 所属行业
     */
    private IndustryType      custIndustry;

    /**
     * 组织机构代码
     */
    private String            custNo;

    /**
     * 税务登记号
     */
    private String            taxNo;

    /**
     * 营业执照
     */
    private String            bizNo;

    /**
     * 资产总额
     */
    private BigDecimal        totalCapital;

    /**
     * 公司规模
     */
    private ScaleType         custScale;

    /**
     * 法人姓名
     */
    private String            legalPerson;

    /**
     * 证件类型
     */
    private String            cardType;

    /**
     * 证件号码
     */
    private String            cardNo;

    /**
     * 联系人
     */
    private String            contactPerson;

    /**
     * 联系电话
     */
    private String            contactPhone;

    /**
     * 月收入
     */
    private Long              monthSalary;

    /**
     * 注册资本
     */
    private BigDecimal        registMoney;

    /**
     * 注册日期
     */
    private Date              registTime;

    /**
     * 注册登记号
     */
    private String            registNo;

    /**
     * 注册地址
     */
    private String            registAddress;

    /**
     * 所属地区
     */
    private String            areaId;

    /**
     * 详细地址
     */
    private String            areaAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public CompanyType getCustType() {
        return custType;
    }

    public void setCustType(CompanyType custType) {
        this.custType = custType;
    }

    public IndustryType getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(IndustryType custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public BigDecimal getTotalCapital() {
        return totalCapital;
    }

    public void setTotalCapital(BigDecimal totalCapital) {
        this.totalCapital = totalCapital;
    }

    public ScaleType getCustScale() {
        return custScale;
    }

    public void setCustScale(ScaleType custScale) {
        this.custScale = custScale;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Long getMonthSalary() {
        return monthSalary;
    }

    public void setMonthSalary(Long monthSalary) {
        this.monthSalary = monthSalary;
    }

    public BigDecimal getRegistMoney() {
        return registMoney;
    }

    public void setRegistMoney(BigDecimal registMoney) {
        this.registMoney = registMoney;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public String getRegistNo() {
        return registNo;
    }

    public void setRegistNo(String registNo) {
        this.registNo = registNo;
    }

    public String getRegistAddress() {
        return registAddress;
    }

    public void setRegistAddress(String registAddress) {
        this.registAddress = registAddress;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaAddress() {
        return areaAddress;
    }

    public void setAreaAddress(String areaAddress) {
        this.areaAddress = areaAddress;
    }
}
