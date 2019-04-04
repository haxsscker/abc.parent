package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * 个人财务状况资料
 * @author RJQ 2014/11/13 18:19.
 */
public class UserFinanceDO {
    /**
     * 主键
     * abc_user_finance.uf_id
     */
    private Integer ufId;

    /**
     * 用户ID
     * abc_user_finance.uf_userid
     */
    private Integer ufUserid;

    /**
     * 每月无抵押贷款还款额
     * abc_user_finance.uf_unsecured_pay
     */
    private BigDecimal ufUnsecuredPay;

    /**
     * 每月信用卡还款金额
     * abc_user_finance.uf_credit_pay
     */
    private BigDecimal ufCreditPay;

    /**
     * 每月房屋按揭金额
     * abc_user_finance.uf_house_pay
     */
    private BigDecimal ufHousePay;

    /**
     * 每月汽车按揭金额
     * abc_user_finance.uf_car_pay
     */
    private BigDecimal ufCarPay;

    /**
     * 自有房产	1：是 0：否
     * abc_user_finance.uf_own_house
     */
    private Integer ufOwnHouse;

    /**
     * 自有汽车	1：是 0：否
     * abc_user_finance.uf_own_car
     */
    private Integer ufOwnCar;

    public Integer getUfId() {
        return ufId;
    }

    public void setUfId(Integer ufId) {
        this.ufId = ufId;
    }

    public Integer getUfUserid() {
        return ufUserid;
    }

    public void setUfUserid(Integer ufUserid) {
        this.ufUserid = ufUserid;
    }

    public BigDecimal getUfUnsecuredPay() {
        return ufUnsecuredPay;
    }

    public void setUfUnsecuredPay(BigDecimal ufUnsecuredPay) {
        this.ufUnsecuredPay = ufUnsecuredPay;
    }

    public BigDecimal getUfCreditPay() {
        return ufCreditPay;
    }

    public void setUfCreditPay(BigDecimal ufCreditPay) {
        this.ufCreditPay = ufCreditPay;
    }

    public BigDecimal getUfHousePay() {
        return ufHousePay;
    }

    public void setUfHousePay(BigDecimal ufHousePay) {
        this.ufHousePay = ufHousePay;
    }

    public BigDecimal getUfCarPay() {
        return ufCarPay;
    }

    public void setUfCarPay(BigDecimal ufCarPay) {
        this.ufCarPay = ufCarPay;
    }

    public Integer getUfOwnHouse() {
        return ufOwnHouse;
    }

    public void setUfOwnHouse(Integer ufOwnHouse) {
        this.ufOwnHouse = ufOwnHouse;
    }

    public Integer getUfOwnCar() {
        return ufOwnCar;
    }

    public void setUfOwnCar(Integer ufOwnCar) {
        this.ufOwnCar = ufOwnCar;
    }
}
