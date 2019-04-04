/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.autoserve.abc.service.biz.enums.EasyPayConfig;
import com.autoserve.abc.service.util.Md5Encrypt;

/**
 * 易生支付data
 * 
 * @author J.YL 2014年11月18日 下午3:30:21
 */
public class EasyPayData {

    public EasyPayData() {
        this.service = EasyPayConfig.EASY_PAY_SERVICE;
        this.partner = EasyPayConfig.PARTNER;
        this.sign_type = EasyPayConfig.SIGN_TYPE;
        this._input_charset = EasyPayConfig.CHART_SET;
        this.payment_type = EasyPayConfig.EASY_PAYMENT_TYPE;
        this.paymethod = EasyPayConfig.EasyPaymentMethod.BANKPAY.value;
        this.sign = this.buildSign();
        this.subject = EasyPayConfig.EASY_PAY_SUBJECT;
        this.body = EasyPayConfig.EASY_BODY;
        this.paymethod = EasyPayConfig.EasyPaymentMethod.BANKPAY.value;

    }

    @SuppressWarnings("unchecked")
    private String buildSign() {
        Map<String, String> map = JSON.parseObject(JSON.toJSONString(this), Map.class);
        @SuppressWarnings("rawtypes")
        List keys = new ArrayList(map.keySet());
        Collections.sort(keys);
        StringBuilder prestr = new StringBuilder();
        String key = "";
        String value = "";
        for (int i = 0; i < keys.size(); i++) {
            key = (String) keys.get(i);
            value = map.get(key);
            if ("".equals(value) || value == null || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            prestr.append(key).append("=").append(value).append("&");
        }
        prestr.deleteCharAt(prestr.length() - 1);
        prestr.append(EasyPayConfig.KEY);
        String sign = Md5Encrypt.md5(prestr.toString());
        return sign;
    }

    /**
     * 默认值表示网上支付 NOT NULL
     */
    private final String service;
    /**
     * 版本号 NULL 预付费卡支付时，必须填写，其他情况不允许填写。 PRECARD_1.0，表示预付费卡支付 目前不用设置。set方法已经删除
     */
    private String       version;

    /**
     * 合作伙伴ID NOT　NULL 合作伙伴在易生支付的用户ID
     */
    private final String partner;

    /**
     * 通知URL NOT　NULL 针对该交易的交易状态同步通知接收URL。（URL里不要带参数）
     */
    private URL          notify_url;

    /**
     * 返回URL NOT　NULL 结果返回URL，仅适用于立即返回处理结果的接口。易生支付处理完请求后，立即将处理结果返回给这个URL。
     * （URL里不要带参数）
     */
    private URL          return_url;

    /**
     * 签名 NOT　NULL
     */
    private final String sign;

    /**
     * 签名方式 NOT　NULL 目前只支持MD5
     */
    private final String sign_type;

    /**
     * 参数编码字符集 （默认为GBK） NOT　NULL 合作伙伴系统与易生支付系统之间交互信息时使用的编码字符集。
     * 合作伙伴可以通过该参数指定使用何种字符集对传递参数进行编码。 同时，易生支付系统也会使用该字符集对返回参数或通知参数进行编码。
     * 注：该参数在POST方式发送请求时要附在action后。
     * 如：http://cachier.bhecard.com/portal?_input_charset=gbk
     */
    private final String _input_charset;

    /**
     * 商品名称 NOT　NULL 商品的标题
     */
    private final String subject;
    /**
     * 商品描述 NOT　NULL 商品的具体描述
     */
    private final String body;

    /**
     * 外部交易号 NOT NULL 合作伙伴交易号（确保在合作伙伴系统中唯一），譬如：bhe2011051189234
     */
    private String       out_trade_no;

    /**
     * 交易金额 NOT NULL total_fee:单位为RMB Yuan
     */
    private Number       total_fee;

    /**
     * 支付类型 NOT NULL 1，目前支持值为“1”
     */
    private String       payment_type = "1";

    /**
     * 支付方式 NOT NULL 固定值 1. bankPay，网银支付，默认值； 2. directPay，账户余额支付 3.
     * bankDirect，银行直连时使用
     */
    private String       paymethod;

    /**
     * 网银代码 NULL 网银代码 在此为空 set方法已经删除
     */
    private String       defaultbank;

    /**
     * 卖家 Email NOT NULL 卖家在易生支付的注册Email.
     */
    private String       seller_email;

    /**
     * 买家Email NULL 买家在易生支付的注册Email。
     */
    private String       buyer_email;

    /**
     * 买家真实姓名 NULL
     */
    private String       buyer_realname;

    /**
     * 买家联系方式 NULL
     */
    private String       buyer_contact;

    public String getService() {
        return service;
    }

    public String getVersion() {
        return version;
    }

    public String getPartner() {
        return partner;
    }

    public URL getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(URL notify_url) {
        this.notify_url = notify_url;
    }

    public URL getReturn_url() {
        return return_url;
    }

    public void setReturn_url(URL return_url) {
        this.return_url = return_url;
    }

    public String getSign() {
        return sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public String get_input_charset() {
        return _input_charset;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Number getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Number total_fee) {
        this.total_fee = total_fee;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public String getDefaultbank() {
        return defaultbank;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public String getBuyer_realname() {
        return buyer_realname;
    }

    public void setBuyer_realname(String buyer_realname) {
        this.buyer_realname = buyer_realname;
    }

    public String getBuyer_contact() {
        return buyer_contact;
    }

    public void setBuyer_contact(String buyer_contact) {
        this.buyer_contact = buyer_contact;
    }

}
