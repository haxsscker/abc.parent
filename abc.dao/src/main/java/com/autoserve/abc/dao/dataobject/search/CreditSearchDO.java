package com.autoserve.abc.dao.dataobject.search;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author RJQ 2015/1/6 15:54.
 */
public class CreditSearchDO {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 申请开始日期
     */
    private Date creditApplyStartDate;

    /**
     * 申请结束日期
     */
    private Date creditApplyEndDate;

    /**
     * 审核开始额度
     */
    private BigDecimal creditReviewStartAmount;

    /**
     * 审核结束额度
     */
    private BigDecimal creditReviewEndAmount;

    /**
     * 申请额度类型  1：借款信用额度 2：投资担保额度 3：借款担保额度
     */
    private Integer creditType;

    /**
     * 审核状态	0：待审核 1：审核已通过 2：审核未通过
     */
    private Integer creditReviewState;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreditApplyStartDate() {
        return creditApplyStartDate;
    }

    public void setCreditApplyStartDate(Date creditApplyStartDate) {
        this.creditApplyStartDate = creditApplyStartDate;
    }

    public Date getCreditApplyEndDate() {
        return creditApplyEndDate;
    }

    public void setCreditApplyEndDate(Date creditApplyEndDate) {
        this.creditApplyEndDate = creditApplyEndDate;
    }

    public BigDecimal getCreditReviewStartAmount() {
        return creditReviewStartAmount;
    }

    public void setCreditReviewStartAmount(BigDecimal creditReviewStartAmount) {
        this.creditReviewStartAmount = creditReviewStartAmount;
    }

    public BigDecimal getCreditReviewEndAmount() {
        return creditReviewEndAmount;
    }

    public void setCreditReviewEndAmount(BigDecimal creditReviewEndAmount) {
        this.creditReviewEndAmount = creditReviewEndAmount;
    }

    public Integer getCreditType() {
        return creditType;
    }

    public void setCreditType(Integer creditType) {
        this.creditType = creditType;
    }

    public Integer getCreditReviewState() {
        return creditReviewState;
    }

    public void setCreditReviewState(Integer creditReviewState) {
        this.creditReviewState = creditReviewState;
    }
}
