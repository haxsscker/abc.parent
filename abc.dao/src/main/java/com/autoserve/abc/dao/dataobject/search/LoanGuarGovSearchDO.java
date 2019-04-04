/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject.search;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 担保审核搜索DO
 * 
 * @author J.YL 2015年1月7日 下午2:57:07
 */
public class LoanGuarGovSearchDO {

    /**
     * 项目编号
     */
    private String loanNo;

    /**
     * 借款人
     */
    private String userName;

    /**
     * 审核状态
     */
    private Integer reviewState;

    /**
     * 项目类型
     */
    private Integer loanCategory;

    /**
     * 申请日期开始
     */
    private Date applyTimeFrom;

    /**
     * 申请日期结束
     */
    private Date applyTimeTo;

    /**
     * 借款金额开始
     */
    private BigDecimal loanMoneyFrom;

    /**
     * 借款金额结束
     */
    private BigDecimal loanMoneyTo;

    /**
     * 当前待审角色
     * 此字段用于区分是信贷审核还是担保审核
     */
    private Integer currRoleIdx;

    /**
     * 注意：此字段是当前执行搜索的登录员工ID，不是审核的待审人ID
     */
    private Integer currEmp;

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Date getApplyTimeFrom() {
        return applyTimeFrom;
    }

    public void setApplyTimeFrom(Date applyTimeFrom) {
        this.applyTimeFrom = applyTimeFrom;
    }

    public Date getApplyTimeTo() {
        return applyTimeTo;
    }

    public void setApplyTimeTo(Date applyTimeTo) {
        this.applyTimeTo = applyTimeTo;
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

    public Integer getCurrRoleIdx() {
        return currRoleIdx;
    }

    public void setCurrRoleIdx(Integer currRoleIdx) {
        this.currRoleIdx = currRoleIdx;
    }

    public Integer getCurrEmp() {
        return currEmp;
    }

    public void setCurrEmp(Integer currEmp) {
        this.currEmp = currEmp;
    }
}
