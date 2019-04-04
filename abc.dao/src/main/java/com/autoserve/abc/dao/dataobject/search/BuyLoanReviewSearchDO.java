/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject.search;

import java.math.BigDecimal;
import java.util.List;

/**
 * 收购初审和收购满标审核查询DO
 * 
 * @author J.YL 2014年12月27日 上午11:03:37
 */
public class BuyLoanReviewSearchDO {
    /**
     * 审核类型，对应枚举类ReviewType abc_review.review_type
     */
    private Integer       reviewType;

    /**
     * 当前待审核角色index abc_review.review_curr_role_id
     */
    private List<Integer> reviewCurrRoleIdxList;

    /**
     * 当前待审核人id abc_review.review_curr_emp_id
     */
    private Integer       reviewCurrEmpId;

    /**
     * 审核是否挂起 abc_review.review_suspend
     */
    private Boolean       reviewSuspend;

    /**
     * 审核的流程是否已结束 abc_review.review_end
     */
    private Boolean       reviewEnd;

    /**
     * 项目编号
     */
    private String        loanNo;

    /**
     * 项目类型
     */
    private Integer       loanCategory;

    /**
     * 收购人
     */
    private String        userName;

    /**
     * 起始收购份额
     */
    private BigDecimal    buyTotalFrom;

    /**
     * 结束收购份额
     */
    private BigDecimal    buyTotalTo;

    /**
     * 申请查询起始时间 无则默认为1970-01-01
     */
    private String        applyDateFrom;

    /**
     * 申请查询结束时间
     */
    private String        applyDateTo;
    /**
     * 申请审核状态
     */
    private Integer       applyState;
    /**
     * 满标时间 起始
     */
    private String        buyLoanFullTimeFrom;
    /**
     * 满标时间 结束
     */
    private String        buyLoanFullTimeTo;

    /**
     * 收购满标状态
     */
    private Integer       fullState;

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public Integer getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(Integer loanCategory) {
        this.loanCategory = loanCategory;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getBuyTotalFrom() {
        return buyTotalFrom;
    }

    public void setBuyTotalFrom(BigDecimal buyTotalFrom) {
        this.buyTotalFrom = buyTotalFrom;
    }

    public BigDecimal getBuyTotalTo() {
        return buyTotalTo;
    }

    public void setBuyTotalTo(BigDecimal buyTotalTo) {
        this.buyTotalTo = buyTotalTo;
    }

    public String getApplyDateFrom() {
        return applyDateFrom;
    }

    public void setApplyDateFrom(String applyDateFrom) {
        this.applyDateFrom = applyDateFrom;
    }

    public String getApplyDateTo() {
        return applyDateTo;
    }

    public void setApplyDateTo(String applyDateTo) {
        this.applyDateTo = applyDateTo;
    }

    public Integer getApplyState() {
        return applyState;
    }

    public void setApplyState(Integer applyState) {
        this.applyState = applyState;
    }

    public Integer getReviewType() {
        return reviewType;
    }

    public void setReviewType(Integer reviewType) {
        this.reviewType = reviewType;
    }

    public List<Integer> getReviewCurrRoleIdxList() {
        return reviewCurrRoleIdxList;
    }

    public void setReviewCurrRoleIdxList(List<Integer> reviewCurrRoleIdxList) {
        this.reviewCurrRoleIdxList = reviewCurrRoleIdxList;
    }

    public Integer getReviewCurrEmpId() {
        return reviewCurrEmpId;
    }

    public void setReviewCurrEmpId(Integer reviewCurrEmpId) {
        this.reviewCurrEmpId = reviewCurrEmpId;
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

    public String getBuyLoanFullTimeTo() {
        return buyLoanFullTimeTo;
    }

    public void setBuyLoanFullTimeTo(String buyLoanFullTimeTo) {
        this.buyLoanFullTimeTo = buyLoanFullTimeTo;
    }

    public String getBuyLoanFullTimeFrom() {
        return buyLoanFullTimeFrom;
    }

    public void setBuyLoanFullTimeFrom(String buyLoanFullTimeFrom) {
        this.buyLoanFullTimeFrom = buyLoanFullTimeFrom;
    }

    public Integer getFullState() {
        return fullState;
    }

    public void setFullState(Integer fullState) {
        this.fullState = fullState;
    }
}
