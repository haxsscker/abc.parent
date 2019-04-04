package com.autoserve.abc.dao.dataobject.search;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-02,21:14
 */
public class IntentReviewSearchDO {
    /**
     * 意向编号
     */
    private String     intentNo;

    /**
     * 意向标题
     */
    private String     intentTitle;

    /**
     * 意向类型
     */
    private Integer    intentCategory;

    /**
     * 融资金额
     */
    private BigDecimal intentMoneyFrom;

    /**
     * 融资金额
     */
    private BigDecimal intentMoneyTo;

    /**
     * 还款方式
     */
    private Integer    intentPayType;

    /**
     * 借款人类型
     */
    private Integer    intenteeType;

    /**
     * 借款人
     */
    private String     userName;

    /**
     * 申请时间
     */
    private Date       intentTimeFrom;

    /**
     * 申请时间
     */
    private Date       intentTimeTo;

    /**
     * 审核类型
     */
    private Integer    reviewType;

    /**
     * 审核状态
     */
    private Integer    reviewState;

    public String getIntentNo() {
        return intentNo;
    }

    public void setIntentNo(String intentNo) {
        this.intentNo = intentNo;
    }

    public String getIntentTitle() {
        return intentTitle;
    }

    public void setIntentTitle(String intentTitle) {
        this.intentTitle = intentTitle;
    }

    public Integer getIntentCategory() {
        return intentCategory;
    }

    public void setIntentCategory(Integer intentCategory) {
        this.intentCategory = intentCategory;
    }

    public BigDecimal getIntentMoneyFrom() {
        return intentMoneyFrom;
    }

    public void setIntentMoneyFrom(BigDecimal intentMoneyFrom) {
        this.intentMoneyFrom = intentMoneyFrom;
    }

    public BigDecimal getIntentMoneyTo() {
        return intentMoneyTo;
    }

    public void setIntentMoneyTo(BigDecimal intentMoneyTo) {
        this.intentMoneyTo = intentMoneyTo;
    }

    public Integer getIntentPayType() {
        return intentPayType;
    }

    public void setIntentPayType(Integer intentPayType) {
        this.intentPayType = intentPayType;
    }

    public Integer getIntenteeType() {
        return intenteeType;
    }

    public void setIntenteeType(Integer intenteeType) {
        this.intenteeType = intenteeType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getIntentTimeFrom() {
        return intentTimeFrom;
    }

    public void setIntentTimeFrom(Date intentTimeFrom) {
        this.intentTimeFrom = intentTimeFrom;
    }

    public Date getIntentTimeTo() {
        return intentTimeTo;
    }

    public void setIntentTimeTo(Date intentTimeTo) {
        this.intentTimeTo = intentTimeTo;
    }

    public Integer getReviewType() {
        return reviewType;
    }

    public void setReviewType(Integer reviewType) {
        this.reviewType = reviewType;
    }

    public Integer getReviewState() {
        return reviewState;
    }

    public void setReviewState(Integer reviewState) {
        this.reviewState = reviewState;
    }
}
