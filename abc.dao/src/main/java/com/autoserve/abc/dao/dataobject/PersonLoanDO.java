package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * PersonLoan abc_loan_person
 */
public class PersonLoanDO {
    /**
     * abc_loan_person.lp_id
     */
    private Integer           lpId;

    /**
     * 项目id abc_loan_person.lp_loan_id
     */
    private Integer           lpLoanId;

    /**
     * 证件类型 abc_loan_person.lp_card_type
     */
    private String            lpCardType;

    /**
     * 证件号码 abc_loan_person.lp_card_no
     */
    private String            lpCardNo;

    /**
     * 婚姻状况 abc_loan_person.lp_is_marry
     */
    private Integer           lpIsMarry;

    /**
     * 联系人 abc_loan_person.lp_contact_person
     */
    private String            lpContactPerson;

    /**
     * 联系人电话 abc_loan_person.lp_contact_phone
     */
    private String            lpContactPhone;

    /**
     * 月收入 abc_loan_person.lp_month_salary
     */
    private BigDecimal        lpMonthSalary;

    /**
     * 单位名称 abc_loan_person.lp_work_name
     */
    private String            lpWorkName;

    /**
     * 单位性质 abc_loan_person.lp_work_type
     */
    private String            lpWorkType;

    /**
     * 工作年限 abc_loan_person.lp_work_year
     */
    private Byte              lpWorkYear;

    /**
     * 所属地区 abc_loan_person.lp_area_id
     */
    private String            lpAreaId;

    /**
     * 详细地址 abc_loan_person.lp_person_address
     */
    private String            lpPersonAddress;

    public Integer getLpId() {
        return lpId;
    }

    public void setLpId(Integer lpId) {
        this.lpId = lpId;
    }

    public Integer getLpLoanId() {
        return lpLoanId;
    }

    public void setLpLoanId(Integer lpLoanId) {
        this.lpLoanId = lpLoanId;
    }

    public String getLpCardType() {
        return lpCardType;
    }

    public void setLpCardType(String lpCardType) {
        this.lpCardType = lpCardType;
    }

    public String getLpCardNo() {
        return lpCardNo;
    }

    public void setLpCardNo(String lpCardNo) {
        this.lpCardNo = lpCardNo;
    }

    public Integer getLpIsMarry() {
        return lpIsMarry;
    }

    public void setLpIsMarry(Integer lpIsMarry) {
        this.lpIsMarry = lpIsMarry;
    }

    public String getLpContactPerson() {
        return lpContactPerson;
    }

    public void setLpContactPerson(String lpContactPerson) {
        this.lpContactPerson = lpContactPerson;
    }

    public String getLpContactPhone() {
        return lpContactPhone;
    }

    public void setLpContactPhone(String lpContactPhone) {
        this.lpContactPhone = lpContactPhone;
    }

    public BigDecimal getLpMonthSalary() {
        return lpMonthSalary;
    }

    public void setLpMonthSalary(BigDecimal lpMonthSalary) {
        this.lpMonthSalary = lpMonthSalary;
    }

    public String getLpWorkName() {
        return lpWorkName;
    }

    public void setLpWorkName(String lpWorkName) {
        this.lpWorkName = lpWorkName;
    }

    public String getLpWorkType() {
        return lpWorkType;
    }

    public void setLpWorkType(String lpWorkType) {
        this.lpWorkType = lpWorkType;
    }

    public Byte getLpWorkYear() {
        return lpWorkYear;
    }

    public void setLpWorkYear(Byte lpWorkYear) {
        this.lpWorkYear = lpWorkYear;
    }

    public String getLpAreaId() {
        return lpAreaId;
    }

    public void setLpAreaId(String lpAreaId) {
        this.lpAreaId = lpAreaId;
    }

    public String getLpPersonAddress() {
        return lpPersonAddress;
    }

    public void setLpPersonAddress(String lpPersonAddress) {
        this.lpPersonAddress = lpPersonAddress;
    }
}
