package com.autoserve.abc.web.vo.user;

import java.math.BigDecimal;

/**
 * 类UserOwnerVO.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月18日 下午1:17:13
 */
/** 私营业主资料 */
public class UserOwnerVO {

    /**
     * 主键id abc_user_owner.uo_id
     */
    private Integer    uoId;

    /**
     * 用户id abc_user_owner.uo_user_id
     */
    private Integer    uoUserId;

    /**
     * 私营企业类型 abc_user_owner.uo_type
     */
    private String     uoType;

    /**
     * 经营场所地址 abc_user_owner.uo_address
     */
    private String     uoAddress;

    /**
     * 成立日期 abc_user_owner.uo_date
     */
    private String     uoDate;

    /**
     * 租期 abc_user_owner.uo_rent_period
     */
    private BigDecimal uoRentPeriod;

    /**
     * 租金 abc_user_owner.uo_rent_money
     */
    private BigDecimal uoRentMoney;

    /**
     * 税务编号 abc_user_owner.uo_tax_no
     */
    private String     uoTaxNo;

    /**
     * 工商登记号 abc_user_owner.uo_registrat_no
     */
    private String     uoRegistratNo;

    /**
     * 全年盈利/亏损额 abc_user_owner.uo_profit_money
     */
    private BigDecimal uoProfitMoney;

    /**
     * 雇佣人数 abc_user_owner.uo_employ_num
     */
    private Integer    uoEmployNum;

    /**
     * 每月供款? abc_user_owner.uo_month_pay
     */
    private BigDecimal uoMonthPay;

    /**
     * 尚欠供款余额? abc_user_owner.uo_sett_pay
     */
    private BigDecimal uoSettPay;

    /**
     * 按揭银行? abc_user_owner.uo_bank_pay
     */
    private String     uoBankPay;

    /**
     * @return abc_user_owner.uo_id
     */
    public Integer getUoId() {
        return uoId;
    }

    /**
     * @param Integer uoId (abc_user_owner.uo_id )
     */
    public void setUoId(Integer uoId) {
        this.uoId = uoId;
    }

    /**
     * @return abc_user_owner.uo_user_id
     */
    public Integer getUoUserId() {
        return uoUserId;
    }

    /**
     * @param Integer uoUserId (abc_user_owner.uo_user_id )
     */
    public void setUoUserId(Integer uoUserId) {
        this.uoUserId = uoUserId;
    }

    /**
     * @return abc_user_owner.uo_type
     */
    public String getUoType() {
        return uoType;
    }

    /**
     * @param String uoType (abc_user_owner.uo_type )
     */
    public void setUoType(String uoType) {
        this.uoType = uoType == null ? "" : uoType.trim();
    }

    /**
     * @return abc_user_owner.uo_address
     */
    public String getUoAddress() {
        return uoAddress;
    }

    /**
     * @param String uoAddress (abc_user_owner.uo_address )
     */
    public void setUoAddress(String uoAddress) {
        this.uoAddress = uoAddress == null ? "" : uoAddress.trim();
    }

    /**
     * @return abc_user_owner.uo_date
     */
    public String getUoDate() {
        return uoDate;
    }

    /**
     * @param Date uoDate (abc_user_owner.uo_date )
     */
    public void setUoDate(String string) {
        this.uoDate = string;
    }

    /**
     * @return abc_user_owner.uo_rent_period
     */
    public BigDecimal getUoRentPeriod() {
        return uoRentPeriod;
    }

    /**
     * @param BigDecimal uoRentPeriod (abc_user_owner.uo_rent_period )
     */
    public void setUoRentPeriod(BigDecimal uoRentPeriod) {
        this.uoRentPeriod = uoRentPeriod;
    }

    /**
     * @return abc_user_owner.uo_rent_money
     */
    public BigDecimal getUoRentMoney() {
        return uoRentMoney;
    }

    /**
     * @param BigDecimal uoRentMoney (abc_user_owner.uo_rent_money )
     */
    public void setUoRentMoney(BigDecimal uoRentMoney) {
        this.uoRentMoney = uoRentMoney;
    }

    /**
     * @return abc_user_owner.uo_tax_no
     */
    public String getUoTaxNo() {
        return uoTaxNo;
    }

    /**
     * @param String uoTaxNo (abc_user_owner.uo_tax_no )
     */
    public void setUoTaxNo(String uoTaxNo) {
        this.uoTaxNo = uoTaxNo == null ? "" : uoTaxNo.trim();
    }

    /**
     * @return abc_user_owner.uo_registrat_no
     */
    public String getUoRegistratNo() {
        return uoRegistratNo;
    }

    /**
     * @param String uoRegistratNo (abc_user_owner.uo_registrat_no )
     */
    public void setUoRegistratNo(String uoRegistratNo) {
        this.uoRegistratNo = uoRegistratNo == null ? "" : uoRegistratNo.trim();
    }

    /**
     * @return abc_user_owner.uo_profit_money
     */
    public BigDecimal getUoProfitMoney() {
        return uoProfitMoney;
    }

    /**
     * @param BigDecimal uoProfitMoney (abc_user_owner.uo_profit_money )
     */
    public void setUoProfitMoney(BigDecimal uoProfitMoney) {
        this.uoProfitMoney = uoProfitMoney;
    }

    /**
     * @return abc_user_owner.uo_employ_num
     */
    public Integer getUoEmployNum() {
        return uoEmployNum;
    }

    /**
     * @param Integer uoEmployNum (abc_user_owner.uo_employ_num )
     */
    public void setUoEmployNum(Integer uoEmployNum) {
        this.uoEmployNum = uoEmployNum;
    }

    /**
     * @return abc_user_owner.uo_month_pay
     */
    public BigDecimal getUoMonthPay() {
        return uoMonthPay;
    }

    /**
     * @param BigDecimal uoMonthPay (abc_user_owner.uo_month_pay )
     */
    public void setUoMonthPay(BigDecimal uoMonthPay) {
        this.uoMonthPay = uoMonthPay;
    }

    /**
     * @return abc_user_owner.uo_sett_pay
     */
    public BigDecimal getUoSettPay() {
        return uoSettPay;
    }

    /**
     * @param BigDecimal uoSettPay (abc_user_owner.uo_sett_pay )
     */
    public void setUoSettPay(BigDecimal uoSettPay) {
        this.uoSettPay = uoSettPay;
    }

    /**
     * @return abc_user_owner.uo_bank_pay
     */
    public String getUoBankPay() {
        return uoBankPay;
    }

    /**
     * @param String uoBankPay (abc_user_owner.uo_bank_pay )
     */
    public void setUoBankPay(String uoBankPay) {
        this.uoBankPay = uoBankPay == null ? "" : uoBankPay.trim();
    }
}
