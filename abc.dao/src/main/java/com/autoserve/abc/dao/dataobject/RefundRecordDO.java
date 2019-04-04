package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * RefundRecordDO abc_refund_record
 */
public class RefundRecordDO {
    /**
     * 主键id abc_refund_record.refund_id
     */
    private Integer    refundId;

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

    /**
     * 退费日期 abc_refund_record.refund_date
     */
    private Date       refundDate;

    /**
     * 交易流水号 abc_refund_record.refund_seq_no
     */
    private String     refundSeqNo;

    /**
     * 退费状态(0退款中，1退款成功，2退款失败) abc_refund_record.refund_state
     */
    private Integer    refundState;

    /**
     * 退费申请人id abc_refund_record.refund_applicant
     */
    private Integer    refundApplicant;

    /**
     * 申请日期 abc_refund_record.refund_apply_date
     */
    private Date       refundApplyDate;
    /**
     * 审核意见 abc_refund_record.refund_apply_opinion
     */
    private String     refundApplyOpinion;

    /**
     * 申请状态(0待审核 1 通过 2 未通过) abc_refund_record.refund_apply_state
     */
    private Integer    refundApplyState;

    private Date       refundCheckDate;

    public Integer getRefundId() {
        return refundId;
    }

    public void setRefundId(Integer refundId) {
        this.refundId = refundId;
    }

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
        this.refundAccountNo = refundAccountNo == null ? null : refundAccountNo.trim();
    }

    public String getRefundUserPhone() {
        return refundUserPhone;
    }

    public void setRefundUserPhone(String refundUserPhone) {
        this.refundUserPhone = refundUserPhone == null ? null : refundUserPhone.trim();
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason == null ? null : refundReason.trim();
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

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public String getRefundSeqNo() {
        return refundSeqNo;
    }

    public void setRefundSeqNo(String refundSeqNo) {
        this.refundSeqNo = refundSeqNo == null ? null : refundSeqNo.trim();
    }

    public Integer getRefundState() {
        return refundState;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }

    public Integer getRefundApplicant() {
        return refundApplicant;
    }

    public void setRefundApplicant(Integer refundApplicant) {
        this.refundApplicant = refundApplicant;
    }

    public Date getRefundApplyDate() {
        return refundApplyDate;
    }

    public void setRefundApplyDate(Date refundApplyDate) {
        this.refundApplyDate = refundApplyDate;
    }

    public Integer getRefundApplyState() {
        return refundApplyState;
    }

    public void setRefundApplyState(Integer refundApplyState) {
        this.refundApplyState = refundApplyState;
    }

    /**
     * @return the refundApplyOpinion
     */
    public String getRefundApplyOpinion() {
        return refundApplyOpinion;
    }

    /**
     * @param refundApplyOpinion the refundApplyOpinion to set
     */
    public void setRefundApplyOpinion(String refundApplyOpinion) {
        this.refundApplyOpinion = refundApplyOpinion;
    }

    /**
     * @return the refundCheckDate
     */
    public Date getRefundCheckDate() {
        return refundCheckDate;
    }

    /**
     * @param refundCheckDate the refundCheckDate to set
     */
    public void setRefundCheckDate(Date refundCheckDate) {
        this.refundCheckDate = refundCheckDate;
    }
}
