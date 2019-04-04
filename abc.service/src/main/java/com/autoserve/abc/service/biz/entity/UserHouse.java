package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UserHouse {
    /**
     * 主键id
     * abc_user_house.uh_id
     */
    private Integer uhId;

    /**
     * 用户id
     * abc_user_house.uh_user_id
     */
    private Integer uhUserId;

    /**
     * 房产地址
     * abc_user_house.uh_address
     */
    private String uhAddress;

    /**
     * 建筑面积
     * abc_user_house.uh_area
     */
    private BigDecimal uhArea;

    /**
     * 供款状况
     * abc_user_house.uh_pay_state
     */
    private String uhPayState;

    /**
     * 建筑日期
     * abc_user_house.uh_date
     */
    private Date uhDate;

    /**
     * 所有权1
     * abc_user_house.uh_owner_one
     */
    private String uhOwnerOne;

    /**
     * 所有权1的份额
     * abc_user_house.uh_share_one
     */
    private BigDecimal uhShareOne;

    /**
     * 所有权2
     * abc_user_house.uh_owner_two
     */
    private String uhOwnerTwo;

    /**
     * 所有权2的份额
     * abc_user_house.uh_share_two
     */
    private BigDecimal uhShareTwo;

    /**
     * 贷款年限
     * abc_user_house.uh_loan_period
     */
    private Integer uhLoanPeriod;

    /**
     * 每月供款
     * abc_user_house.uh_month_pay
     */
    private BigDecimal uhMonthPay;

    /**
     * 尚欠供款余额
     * abc_user_house.uh_sett_pay
     */
    private BigDecimal uhSettPay;

    /**
     * 按揭银行
     * abc_user_house.uh_pay_bank
     */
    private String uhPayBank;

    public Integer getUhId() {
        return uhId;
    }

    public void setUhId(Integer uhId) {
        this.uhId = uhId;
    }

    public Integer getUhUserId() {
        return uhUserId;
    }

    public void setUhUserId(Integer uhUserId) {
        this.uhUserId = uhUserId;
    }

    public String getUhAddress() {
        return uhAddress;
    }

    public void setUhAddress(String uhAddress) {
        this.uhAddress = uhAddress;
    }

    public BigDecimal getUhArea() {
        return uhArea;
    }

    public void setUhArea(BigDecimal uhArea) {
        this.uhArea = uhArea;
    }

    public String getUhPayState() {
        return uhPayState;
    }

    public void setUhPayState(String uhPayState) {
        this.uhPayState = uhPayState;
    }

    public Date getUhDate() {
        return uhDate;
    }

    public void setUhDate(Date uhDate) {
        this.uhDate = uhDate;
    }

    public String getUhOwnerOne() {
        return uhOwnerOne;
    }

    public void setUhOwnerOne(String uhOwnerOne) {
        this.uhOwnerOne = uhOwnerOne;
    }

    public BigDecimal getUhShareOne() {
        return uhShareOne;
    }

    public void setUhShareOne(BigDecimal uhShareOne) {
        this.uhShareOne = uhShareOne;
    }

    public String getUhOwnerTwo() {
        return uhOwnerTwo;
    }

    public void setUhOwnerTwo(String uhOwnerTwo) {
        this.uhOwnerTwo = uhOwnerTwo;
    }

    public BigDecimal getUhShareTwo() {
        return uhShareTwo;
    }

    public void setUhShareTwo(BigDecimal uhShareTwo) {
        this.uhShareTwo = uhShareTwo;
    }

    public Integer getUhLoanPeriod() {
        return uhLoanPeriod;
    }

    public void setUhLoanPeriod(Integer uhLoanPeriod) {
        this.uhLoanPeriod = uhLoanPeriod;
    }

    public BigDecimal getUhMonthPay() {
        return uhMonthPay;
    }

    public void setUhMonthPay(BigDecimal uhMonthPay) {
        this.uhMonthPay = uhMonthPay;
    }

    public BigDecimal getUhSettPay() {
        return uhSettPay;
    }

    public void setUhSettPay(BigDecimal uhSettPay) {
        this.uhSettPay = uhSettPay;
    }

    public String getUhPayBank() {
        return uhPayBank;
    }

    public void setUhPayBank(String uhPayBank) {
        this.uhPayBank = uhPayBank;
    }
}
