package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CustLoan abc_loan_cust
 */
public class LoanCustDO {
    /**
     * 主键id abc_loan_cust.lcu_id
     */
    private Integer           lcuId;

    /**
     * 贷款id abc_loan_cust.lcu_loan_id
     */
    private String            lcuLoanId;

    /**
     * 公司名称 abc_loan_cust.lcu_cust_name
     */
    private String            lcuCustName;

    /**
     * 公司类别 abc_loan_cust.lcu_cust_type
     */
    private Integer            lcuCustType;

    /**
     * 所属行业 abc_loan_cust.lcu_cust_industry
     */
    private Integer            lcuCustIndustry;

    /**
     * 组织机构代码 abc_loan_cust.lcu_cust_no
     */
    private String            lcuCustNo;

    /**
     * 税务登记号 abc_loan_cust.lcu_tax_no
     */
    private String            lcuTaxNo;

    /**
     * 营业执照 abc_loan_cust.lcu_biz_no
     */
    private String            lcuBizNo;

    /**
     * 资产总额 abc_loan_cust.lcu_total_capital
     */
    private BigDecimal        lcuTotalCapital;

    /**
     * 公司规模 0:50人以下 1:50-500人 2:500人以上 abc_loan_cust.lcu_cust_scale
     */
    private Integer           lcuCustScale;

    /**
     * 法人姓名 abc_loan_cust.lcu_legal_person
     */
    private String            lcuLegalPerson;

    /**
     * 证件类型 abc_loan_cust.lcu_card_type
     */
    private String            lcuCardType;

    /**
     * 证件号码 abc_loan_cust.lcu_card_no
     */
    private String            lcuCardNo;

    /**
     * 联系人 abc_loan_cust.lcu_contact_person
     */
    private String            lcuContactPerson;

    /**
     * 联系电话 abc_loan_cust.lcu_contact_phone
     */
    private String            lcuContactPhone;

    /**
     * 月收入 abc_loan_cust.lcu_month_salary
     */
    private Long              lcuMonthSalary;

    /**
     * 注册资本 abc_loan_cust.lcu_regist_money
     */
    private BigDecimal        lcuRegistMoney;

    /**
     * 注册日期 abc_loan_cust.lcu_regist_time
     */
    private Date              lcuRegistTime;

    /**
     * 注册登记号 abc_loan_cust.lcu_regist_no
     */
    private String            lcuRegistNo;

    /**
     * 注册地址 abc_loan_cust.lcu_regist_address
     */
    private String            lcuRegistAddress;

    /**
     * 所属地区 abc_loan_cust.lcu_area_id
     */
    private String            lcuAreaId;

    /**
     * 详细地址 abc_loan_cust.lcu_area_address
     */
    private String            lcuAreaAddress;

    public Integer getLcuId() {
        return lcuId;
    }

    public void setLcuId(Integer lcuId) {
        this.lcuId = lcuId;
    }

    public String getLcuLoanId() {
        return lcuLoanId;
    }

    public void setLcuLoanId(String lcuLoanId) {
        this.lcuLoanId = lcuLoanId;
    }

    public String getLcuCustName() {
        return lcuCustName;
    }

    public void setLcuCustName(String lcuCustName) {
        this.lcuCustName = lcuCustName;
    }

    public Integer getLcuCustType() {
        return lcuCustType;
    }

    public void setLcuCustType(Integer lcuCustType) {
        this.lcuCustType = lcuCustType;
    }

    public Integer getLcuCustIndustry() {
        return lcuCustIndustry;
    }

    public void setLcuCustIndustry(Integer lcuCustIndustry) {
        this.lcuCustIndustry = lcuCustIndustry;
    }

    public String getLcuCustNo() {
        return lcuCustNo;
    }

    public void setLcuCustNo(String lcuCustNo) {
        this.lcuCustNo = lcuCustNo;
    }

    public String getLcuTaxNo() {
        return lcuTaxNo;
    }

    public void setLcuTaxNo(String lcuTaxNo) {
        this.lcuTaxNo = lcuTaxNo;
    }

    public String getLcuBizNo() {
        return lcuBizNo;
    }

    public void setLcuBizNo(String lcuBizNo) {
        this.lcuBizNo = lcuBizNo;
    }

    public BigDecimal getLcuTotalCapital() {
        return lcuTotalCapital;
    }

    public void setLcuTotalCapital(BigDecimal lcuTotalCapital) {
        this.lcuTotalCapital = lcuTotalCapital;
    }

    public Integer getLcuCustScale() {
        return lcuCustScale;
    }

    public void setLcuCustScale(Integer lcuCustScale) {
        this.lcuCustScale = lcuCustScale;
    }

    public String getLcuLegalPerson() {
        return lcuLegalPerson;
    }

    public void setLcuLegalPerson(String lcuLegalPerson) {
        this.lcuLegalPerson = lcuLegalPerson;
    }

    public String getLcuCardType() {
        return lcuCardType;
    }

    public void setLcuCardType(String lcuCardType) {
        this.lcuCardType = lcuCardType;
    }

    public String getLcuCardNo() {
        return lcuCardNo;
    }

    public void setLcuCardNo(String lcuCardNo) {
        this.lcuCardNo = lcuCardNo;
    }

    public String getLcuContactPerson() {
        return lcuContactPerson;
    }

    public void setLcuContactPerson(String lcuContactPerson) {
        this.lcuContactPerson = lcuContactPerson;
    }

    public String getLcuContactPhone() {
        return lcuContactPhone;
    }

    public void setLcuContactPhone(String lcuContactPhone) {
        this.lcuContactPhone = lcuContactPhone;
    }

    public Long getLcuMonthSalary() {
        return lcuMonthSalary;
    }

    public void setLcuMonthSalary(Long lcuMonthSalary) {
        this.lcuMonthSalary = lcuMonthSalary;
    }

    public BigDecimal getLcuRegistMoney() {
        return lcuRegistMoney;
    }

    public void setLcuRegistMoney(BigDecimal lcuRegistMoney) {
        this.lcuRegistMoney = lcuRegistMoney;
    }

    public Date getLcuRegistTime() {
        return lcuRegistTime;
    }

    public void setLcuRegistTime(Date lcuRegistTime) {
        this.lcuRegistTime = lcuRegistTime;
    }

    public String getLcuRegistNo() {
        return lcuRegistNo;
    }

    public void setLcuRegistNo(String lcuRegistNo) {
        this.lcuRegistNo = lcuRegistNo;
    }

    public String getLcuRegistAddress() {
        return lcuRegistAddress;
    }

    public void setLcuRegistAddress(String lcuRegistAddress) {
        this.lcuRegistAddress = lcuRegistAddress;
    }

    public String getLcuAreaId() {
        return lcuAreaId;
    }

    public void setLcuAreaId(String lcuAreaId) {
        this.lcuAreaId = lcuAreaId;
    }

    public String getLcuAreaAddress() {
        return lcuAreaAddress;
    }

    public void setLcuAreaAddress(String lcuAreaAddress) {
        this.lcuAreaAddress = lcuAreaAddress;
    }
}
