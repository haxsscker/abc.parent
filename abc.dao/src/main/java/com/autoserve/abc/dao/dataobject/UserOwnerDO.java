package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  UserOwner
 *  abc_user_owner
 */
public class UserOwnerDO {
    /**
     * 主键id
     * abc_user_owner.uo_id
     */
    private Integer uoId;

    /**
     * 用户id
     * abc_user_owner.uo_user_id
     */
    private Integer uoUserId;

    /**
     * 私营企业类型
     * abc_user_owner.uo_type
     */
    private String uoType;

    /**
     * 经营场所地址
     * abc_user_owner.uo_address
     */
    private String uoAddress;

    /**
     * 成立日期
     * abc_user_owner.uo_date
     */
    private Date uoDate;

    /**
     * 租期
     * abc_user_owner.uo_rent_period
     */
    private BigDecimal uoRentPeriod;

    /**
     * 租金
     * abc_user_owner.uo_rent_money
     */
    private BigDecimal uoRentMoney;

    /**
     * 税务编号
     * abc_user_owner.uo_tax_no
     */
    private String uoTaxNo;

    /**
     * 工商登记号
     * abc_user_owner.uo_registrat_no
     */
    private String uoRegistratNo;

    /**
     * 全年盈利/亏损额
     * abc_user_owner.uo_profit_money
     */
    private BigDecimal uoProfitMoney;

    /**
     * 雇佣人数
     * abc_user_owner.uo_employ_num
     */
    private Integer uoEmployNum;

    /**
     * 每月供款?
     * abc_user_owner.uo_month_pay
     */
    private BigDecimal uoMonthPay;

    /**
     * 尚欠供款余额?
     * abc_user_owner.uo_sett_pay
     */
    private BigDecimal uoSettPay;

    /**
     * 按揭银行?
     * abc_user_owner.uo_bank_pay
     */
    private String uoBankPay;

    public Integer getUoId() {
        return uoId;
    }

    public void setUoId(Integer uoId) {
        this.uoId = uoId;
    }

    public Integer getUoUserId() {
        return uoUserId;
    }

    public void setUoUserId(Integer uoUserId) {
        this.uoUserId = uoUserId;
    }

    public String getUoType() {
        return uoType;
    }

    public void setUoType(String uoType) {
        this.uoType = uoType;
    }

    public String getUoAddress() {
        return uoAddress;
    }

    public void setUoAddress(String uoAddress) {
        this.uoAddress = uoAddress;
    }

    public Date getUoDate() {
        return uoDate;
    }

    public void setUoDate(Date uoDate) {
        this.uoDate = uoDate;
    }

    public BigDecimal getUoRentPeriod() {
        return uoRentPeriod;
    }

    public void setUoRentPeriod(BigDecimal uoRentPeriod) {
        this.uoRentPeriod = uoRentPeriod;
    }

    public BigDecimal getUoRentMoney() {
        return uoRentMoney;
    }

    public void setUoRentMoney(BigDecimal uoRentMoney) {
        this.uoRentMoney = uoRentMoney;
    }

    public String getUoTaxNo() {
        return uoTaxNo;
    }

    public void setUoTaxNo(String uoTaxNo) {
        this.uoTaxNo = uoTaxNo;
    }

    public String getUoRegistratNo() {
        return uoRegistratNo;
    }

    public void setUoRegistratNo(String uoRegistratNo) {
        this.uoRegistratNo = uoRegistratNo;
    }

    public BigDecimal getUoProfitMoney() {
        return uoProfitMoney;
    }

    public void setUoProfitMoney(BigDecimal uoProfitMoney) {
        this.uoProfitMoney = uoProfitMoney;
    }

    public Integer getUoEmployNum() {
        return uoEmployNum;
    }

    public void setUoEmployNum(Integer uoEmployNum) {
        this.uoEmployNum = uoEmployNum;
    }

    public BigDecimal getUoMonthPay() {
        return uoMonthPay;
    }

    public void setUoMonthPay(BigDecimal uoMonthPay) {
        this.uoMonthPay = uoMonthPay;
    }

    public BigDecimal getUoSettPay() {
        return uoSettPay;
    }

    public void setUoSettPay(BigDecimal uoSettPay) {
        this.uoSettPay = uoSettPay;
    }

    public String getUoBankPay() {
        return uoBankPay;
    }

    public void setUoBankPay(String uoBankPay) {
        this.uoBankPay = uoBankPay;
    }
}
