package com.autoserve.abc.dao.dataobject.search;

import java.math.BigDecimal;

/**
 * @author wei.huimin
 *         Created on 2014-12-25,12:36
 */
public class TransferReviewSearchDO {

    /**
     * 审核类型，对应枚举类ReviewType
     * abc_review.review_type
     */
    private Integer reviewType;

    /**
     * 审核是否挂起
     * abc_review.review_suspend
     */
    private Boolean reviewSuspend;

    /**
     * 审核的流程是否已结束
     * abc_review.review_end
     */
    private Boolean reviewEnd;

    /**
     * 审核状态
     * abc_review.review_state
     */
    private Integer reviewState;

    /**
     * 当前审核人角色
     * abc_review.review_curr_role_idx
     */
    private Integer currRole;

    /**
     * 当前审核人ID
     * abc_review.review_curr_emp_id
     */
    private Integer currEmp;

    /**
     * 项目编号 abc_loan.loan_no
     */
    private String loanNo;

    /**
     * 项目分类
     */
    private Integer loanCategory;

    /**
     * 借款人姓名 abc_loan.loan_user_id
     */
    private String loanUser;

    /**
     * 转让金额 abc_transfer_loan.tl_transfer_money
     */
    private BigDecimal transferMoneyFrom;

    /**
     * 转让金额 abc_transfer_loan.tl_transfer_money
     */
    private BigDecimal transferMoneyTo;

    /**
     * 申请日期 abc_transfer_loan.tl_createtime
     */
    private String createTimeFrom;

    /**
     * 申请日期 abc_transfer_loan.tl_createtime
     */
    private String createTimeTo;

    /**
     * 满标日期 abc_transfer_loan.tl_fulltime
     */
    private String fullTimeFrom;

    /**
     * 满标日期 abc_transfer_loan.tl_fulltime
     */
    private String fullTimeTo;

    public Integer getReviewType() {
        return reviewType;
    }

    public void setReviewType(Integer reviewType) {
        this.reviewType = reviewType;
    }

    public Boolean getReviewSuspend() {
        return reviewSuspend;
    }

    public void setReviewSuspend(Boolean reviewSuspend) {
        this.reviewSuspend = reviewSuspend;
    }

    public Boolean getReviewEnd() {
        return reviewEnd;
    }

    public void setReviewEnd(Boolean reviewEnd) {
        this.reviewEnd = reviewEnd;
    }

    public Integer getCurrRole() {
        return currRole;
    }

    public void setCurrRole(Integer currRole) {
        this.currRole = currRole;
    }

    public Integer getCurrEmp() {
        return currEmp;
    }

    public void setCurrEmp(Integer currEmp) {
        this.currEmp = currEmp;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getLoanUser() {
        return loanUser;
    }

    public void setLoanUser(String loanUser) {
        this.loanUser = loanUser;
    }

    public Integer getReviewState() {
        return reviewState;
    }

    public void setReviewState(Integer reviewState) {
        this.reviewState = reviewState;
    }

    public Integer getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(Integer loanCategory) {
        this.loanCategory = loanCategory;
    }

    public BigDecimal getTransferMoneyFrom() {
        return transferMoneyFrom;
    }

    public void setTransferMoneyFrom(BigDecimal transferMoneyFrom) {
        this.transferMoneyFrom = transferMoneyFrom;
    }

    public BigDecimal getTransferMoneyTo() {
        return transferMoneyTo;
    }

    public void setTransferMoneyTo(BigDecimal transferMoneyTo) {
        this.transferMoneyTo = transferMoneyTo;
    }

    public String getCreateTimeFrom() {
        return createTimeFrom;
    }

    public void setCreateTimeFrom(String createTimeFrom) {
        this.createTimeFrom = createTimeFrom;
    }

    public String getCreateTimeTo() {
        return createTimeTo;
    }

    public void setCreateTimeTo(String createTimeTo) {
        this.createTimeTo = createTimeTo;
    }

    public String getFullTimeFrom() {
        return fullTimeFrom;
    }

    public void setFullTimeFrom(String fullTimeFrom) {
        this.fullTimeFrom = fullTimeFrom;
    }

    public String getFullTimeTo() {
        return fullTimeTo;
    }

    public void setFullTimeTo(String fullTimeTo) {
        this.fullTimeTo = fullTimeTo;
    }
}
