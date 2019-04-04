package com.autoserve.abc.dao.dataobject.search;

import java.math.BigDecimal;

/**
 * 项目初审与满标审核的搜索DO
 *
 * @author yuqing.zheng
 *         Created on 2014-12-22,15:35
 */
public class LoanReviewSearchDO {
    /**
     * 审核id
     * abc_review.review_id
     */
    private Integer reviewId;

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
     * 借款人姓名 abc_loan.loan_user_id
     */
    private String loanUser;

    /**
     * 借款机构id 外键 abc_loan.loan_gov
     */
//    private Integer    loanGov;

    /**
     * 担保机构id 外键 abc_loan.loan_guar_gov
     */
//    private Integer    loanGuarGov;

    /**
     * 借款金额 abc_loan.loan_money
     */
    private BigDecimal loanMoneyFrom;

    /**
     * 借款金额 abc_loan.loan_money
     */
    private BigDecimal loanMoneyTo;

    /**
     * 投资满标时间 abc_loan.loan_invest_fulltime
     */
    private String loanInvestFulltimeFrom;

    /**
     * 投资满标时间 abc_loan.loan_invest_fulltime
     */
    private String loanInvestFulltimeTo;

    /**
     * 项目分类
     */
    private Integer loanCategory;

    /**
     * 创建时间(申请时间) abc_loan.loan_createtime
     */
    private String loanCreatetimeFrom;

    /**
     * 创建时间(申请时间) abc_loan.loan_createtime
     */
    private String loanCreatetimeTo;

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

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

    public BigDecimal getLoanMoneyFrom() {
        return loanMoneyFrom;
    }

    public void setLoanMoneyFrom(BigDecimal loanMoneyFrom) {
        this.loanMoneyFrom = loanMoneyFrom;
    }

    public BigDecimal getLoanMoneyTo() {
        return loanMoneyTo;
    }

    public void setLoanMoneyTo(BigDecimal loanMoneyTo) {
        this.loanMoneyTo = loanMoneyTo;
    }

    public String getLoanInvestFulltimeFrom() {
        return loanInvestFulltimeFrom;
    }

    public void setLoanInvestFulltimeFrom(String loanInvestFulltimeFrom) {
        this.loanInvestFulltimeFrom = loanInvestFulltimeFrom;
    }

    public String getLoanInvestFulltimeTo() {
        return loanInvestFulltimeTo;
    }

    public void setLoanInvestFulltimeTo(String loanInvestFulltimeTo) {
        this.loanInvestFulltimeTo = loanInvestFulltimeTo;
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

    public String getLoanCreatetimeFrom() {
        return loanCreatetimeFrom;
    }

    public void setLoanCreatetimeFrom(String loanCreatetimeFrom) {
        this.loanCreatetimeFrom = loanCreatetimeFrom;
    }

    public String getLoanCreatetimeTo() {
        return loanCreatetimeTo;
    }

    public void setLoanCreatetimeTo(String loanCreatetimeTo) {
        this.loanCreatetimeTo = loanCreatetimeTo;
    }
}
