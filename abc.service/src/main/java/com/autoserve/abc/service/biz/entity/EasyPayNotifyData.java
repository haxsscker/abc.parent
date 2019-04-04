/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 易生支付通知返回的数据(暂时用于充值)
 * 
 * @author J.YL 2014年11月24日 上午10:54:35
 */
public class EasyPayNotifyData extends NotifyData {
    /**
     * 通知类型 trade_status_sync
     */
    private String     notifyType;
    /**
     * 通知id 易生支付通知流水号，合作伙伴可以用这个流水号询问易生支付该条通知的合法性
     */
    private String     notifyId;
    /**
     * 通知事件
     */
    private Date       notifyTime;
    /**
     * 签名
     */
    private String     sign;
    /**
     * 签名方式
     */
    private String     signType;
    /**
     * 易生支付交易号
     */
    private String     tradeNo;
    /**
     * 商户系统内部交易流水号
     */
    private String     outTradeNo;
    /**
     * 交易类型 ：1 商品购买
     */
    private String     paymentType;
    /**
     * 商品名称
     */
    private String     subject;
    /**
     * 商品描述
     */
    private String     body;
    /**
     * 交易总金额 单位：RMB Yuan 0.01～1000000.00
     */
    private BigDecimal totalFee;
    /**
     * 交易状态
     */
    private String     tradeStatus;
    /**
     * 卖家email
     */
    private String     sellerEmail;
    /**
     * 卖家Id
     */
    private String     sellerId;
    /**
     * 买家Id
     */
    private String     buyerId;
    /**
     * 买家Email
     */
    private String     buyerEmail;
    /**
     * 交易创建时间
     */
    private Date       gmtCreate;
    /**
     * 交易付款时间
     */
    private Date       gmtPayment;

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtPayment() {
        return gmtPayment;
    }

    public void setGmtPayment(Date gmtPayment) {
        this.gmtPayment = gmtPayment;
    }

}
