package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 信用额度申请
 */
public class CreditApplyDO {
    private Integer creditApplyId;

    /**
     * 申请人
     */
    private Integer creditUserId;

    /**
     * 申请额度类型  1：借款信用额度 2：投资担保额度 3：借款担保额度
     */
    private Integer creditType;

    /**
     * 原来的信用额度
     */
    private BigDecimal creditOldAmount;

    /**
     * 申请额度
     */
    private BigDecimal creditApplyAmount;

    /**
     * 审核额度
     */
    private BigDecimal creditReviewAmount;

    /**
     * 申请日期
     */
    private Date creditApplyDate;

    /**
     * 审核状态	0：待审核 1：审核已通过 2：审核未通过
     */
    private Integer creditReviewState;

    /**
     * 详细说明
     */
    private String creditApplyNote;

    private Date creditLastModifyTime;

    public Integer getCreditApplyId() {
        return creditApplyId;
    }

    public void setCreditApplyId(Integer creditApplyId) {
        this.creditApplyId = creditApplyId;
    }

    public Integer getCreditUserId() {
        return creditUserId;
    }

    public void setCreditUserId(Integer creditUserId) {
        this.creditUserId = creditUserId;
    }

    public Integer getCreditType() {
        return creditType;
    }

    public void setCreditType(Integer creditType) {
        this.creditType = creditType;
    }

    public BigDecimal getCreditOldAmount() {
        return creditOldAmount;
    }

    public void setCreditOldAmount(BigDecimal creditOldAmount) {
        this.creditOldAmount = creditOldAmount;
    }

    public BigDecimal getCreditApplyAmount() {
        return creditApplyAmount;
    }

    public void setCreditApplyAmount(BigDecimal creditApplyAmount) {
        this.creditApplyAmount = creditApplyAmount;
    }

    public BigDecimal getCreditReviewAmount() {
        return creditReviewAmount;
    }

    public void setCreditReviewAmount(BigDecimal creditReviewAmount) {
        this.creditReviewAmount = creditReviewAmount;
    }

    public Date getCreditApplyDate() {
        return creditApplyDate;
    }

    public void setCreditApplyDate(Date creditApplyDate) {
        this.creditApplyDate = creditApplyDate;
    }

    public Integer getCreditReviewState() {
        return creditReviewState;
    }

    public void setCreditReviewState(Integer creditReviewState) {
        this.creditReviewState = creditReviewState;
    }

    public String getCreditApplyNote() {
        return creditApplyNote;
    }

    public void setCreditApplyNote(String creditApplyNote) {
        this.creditApplyNote = creditApplyNote;
    }

    public Date getCreditLastModifyTime() {
        return creditLastModifyTime;
    }

    public void setCreditLastModifyTime(Date creditLastModifyTime) {
        this.creditLastModifyTime = creditLastModifyTime;
    }
}