/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;

/**
 * 退费entity
 * 
 * @author J.YL 2014年11月18日 下午6:37:22
 */
public class Refund {
    /**
     * 用户id abc_refund_record.refund_user_id
     */
    private Integer    refundUserId;

    /**
     * 退回开户账户 取账户开户表中的：开户账户 abc_refund_record.refund_account_no
     */
    private String     refundAccountNo;

    /**
     * 退回用户手机：网友基本信息表中user_phone，如果上述字段中没有值，取 账户开户表user_phone
     * abc_refund_record.refund_user_phone
     */
    private String     refundUserPhone;

    /**
     * 退费原因 abc_refund_record.refund_reason
     */
    private String     refundReason;

    /**
     * 退费金额 abc_refund_record.refund_amount
     */
    private BigDecimal refundAmount;

    /**
     * 操作人id：employee abc_refund_record.refund_operator
     */
    private Integer    refundOperator;

    public Integer getRefundUserId() {
        return refundUserId;
    }

    public void setRefundUserId(Integer refundUserId) {
        this.refundUserId = refundUserId;
    }

    public String getRefundAccountNo() {
        return refundAccountNo;
    }

    public void setRefundAccountNo(String refundAccountNo) {
        this.refundAccountNo = refundAccountNo;
    }

    public String getRefundUserPhone() {
        return refundUserPhone;
    }

    public void setRefundUserPhone(String refundUserPhone) {
        this.refundUserPhone = refundUserPhone;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Integer getRefundOperator() {
        return refundOperator;
    }

    public void setRefundOperator(Integer refundOperator) {
        this.refundOperator = refundOperator;
    }

}
