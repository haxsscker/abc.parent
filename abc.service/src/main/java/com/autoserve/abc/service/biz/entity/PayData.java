/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

/**
 * 支付接口元数据信息
 *
 * @author segen189 2014年11月19日 下午5:06:26
 */
public class PayData {

    /**
     * 通知URL NOT　NULL 针对该交易的交易状态同步通知接收URL。（URL里不要带参数）
     */
    private String notifyUrl;

    /**
     * 返回URL NOT　NULL 结果返回URL，仅适用于立即返回处理结果的接口。易生支付处理完请求后，立即将处理结果返回给这个URL。
     * （URL里不要带参数）
     */
    private String returnUrl;

    /**
     * 商品名称 NOT　NULL 商品的标题
     */
    private String subject;
    /**
     * 商品描述 NOT　NULL 商品的具体描述
     */
    private String body;

    /**
     * 外部交易号 NOT NULL 合作伙伴交易号（确保在合作伙伴系统中唯一），譬如：bhe2011051189234
     */
    private String outTradeNo;

    /**
     * 交易金额 NOT NULL totalFee:单位为RMB Yuan
     */
    private Number totalFee;

    /**
     * 卖家 userId
     */
    private String sellerUserId;

    /**
     * 买家 userId
     */
    private String buyerUserId;

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
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

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Number getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Number totalFee) {
        this.totalFee = totalFee;
    }

    public String getSellerUserId() {
        return sellerUserId;
    }

    public void setSellerUserId(String sellerUserId) {
        this.sellerUserId = sellerUserId;
    }

    public String getBuyerUserId() {
        return buyerUserId;
    }

    public void setBuyerUserId(String buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

}
