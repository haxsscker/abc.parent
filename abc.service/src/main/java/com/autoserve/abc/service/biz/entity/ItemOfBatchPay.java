package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;

import com.autoserve.abc.service.biz.enums.EasyPayConfig;

public class ItemOfBatchPay {

    private static String DEFAULT_AMOUNT_TYE = "CNY";
    private static String DEFAULT_REMARK     = "OK";
    private Integer       tradeNum;                  //必须     批次内的排序字段
    private String        tradeCardnum;              //必须   对方银行卡号
    private String        tradeCardname;             //必须   账户名称
    private String        tradeAccountName;          //必须  开户行
    private String        tradeBranchBank;           //必须  分行
    private String        tradeSubbranchBank;        //必须 支行
    private Integer       tradeAccountType;          //必须    对公对私标志   参考BatchPayConfig的TradeAccountType
    private BigDecimal    tradeAmount;               //必须    金额
    private String        tradeAmountType;           //非必须   
    private String        tradeProvince;             //非必须  省份
    private String        tradeCity;                 //非必须  市
    private String        tradeMobile;               //必须   手机号  交易成功后  会发送短信
    private Integer       certificateType;           //必须   证件类型      参考EasyPayConfig的CertificateType 默认身份证
    private String        certificateNum;            //必须   证件号
    private String        contractUsercode;          //非必须  用户协议号
    private String        tradeCustorder;            //非必须 商户订单号
    private String        tradeRemark;               //非必须

    public ItemOfBatchPay() {
        this.tradeAmountType = DEFAULT_AMOUNT_TYE;
        this.tradeRemark = DEFAULT_REMARK;
        this.certificateType = EasyPayConfig.CertificateType.ID_CARD.value;
    }

    public Integer getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(Integer tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getTradeCardnum() {
        return tradeCardnum;
    }

    public void setTradeCardnum(String tradeCardnum) {
        this.tradeCardnum = tradeCardnum;
    }

    public String getTradeCardname() {
        return tradeCardname;
    }

    public void setTradeCardname(String tradeCardname) {
        this.tradeCardname = tradeCardname;
    }

    public String getTradeAccountName() {
        return tradeAccountName;
    }

    public void setTradeAccountName(String tradeAccountName) {
        this.tradeAccountName = tradeAccountName;
    }

    public String getTradeBranchBank() {
        return tradeBranchBank;
    }

    public void setTradeBranchBank(String tradeBranchBank) {
        this.tradeBranchBank = tradeBranchBank;
    }

    public String getTradeSubbranchBank() {
        return tradeSubbranchBank;
    }

    public void setTradeSubbranchBank(String tradeSubbranchBank) {
        this.tradeSubbranchBank = tradeSubbranchBank;
    }

    public Integer getTradeAccountType() {
        return tradeAccountType;
    }

    public void setTradeAccountType(Integer tradeAccountType) {
        this.tradeAccountType = tradeAccountType;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getTradeAmountType() {
        return tradeAmountType;
    }

    public String getTradeProvince() {
        return tradeProvince;
    }

    public void setTradeProvince(String tradeProvince) {
        this.tradeProvince = tradeProvince;
    }

    public String getTradeCity() {
        return tradeCity;
    }

    public void setTradeCity(String tradeCity) {
        this.tradeCity = tradeCity;
    }

    public String getTradeMobile() {
        return tradeMobile;
    }

    public void setTradeMobile(String tradeMobile) {
        this.tradeMobile = tradeMobile;
    }

    public Integer getCertificateType() {
        return certificateType;
    }

    public String getCertificateNum() {
        return certificateNum;
    }

    public void setCertificateNum(String certificateNum) {
        this.certificateNum = certificateNum;
    }

    public String getContractUsercode() {
        return contractUsercode;
    }

    public String getTradeCustorder() {
        return tradeCustorder;
    }

    public String getTradeRemark() {
        return tradeRemark;
    }

}
