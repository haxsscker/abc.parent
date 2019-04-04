package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.ReviewState;

import java.util.Date;

/**
 * 积分兑换投资券记录
 */
public class ScoreUsageRecord {
    private Integer surId;

    /**
     * 用户ID 关联user表
     */
    private Integer surUserId;

    /**
     * 兑换用户名
     */
    private String userName;

    /**
     * 兑换积分
     */
    private Integer surExchangeScore;

    /**
     * 兑换金额
     */
    private Long surExchangeCash;

    /**
     * 兑现日期
     */
    private Date surExchangeDate;

    /**
     * 有效开始日期
     */
    private Date surStartDate;

    /**
     * 有效结束日期
     */
    private Date surEndDate;

    /**
     * 划转金额 即已使用金额
     */
    private Long surUseMoney;

    /**
     * 审核日期
     */
    private Date reviewDate;

    /**
     * 审核意见
     */
    private String reviewNote;

    /**
     * 审核状态 0：待审核 1：审核已通过 2：审核未通过
     */
    private ReviewState surReviewState;

    /**
     * 投资券状态 0：未使用 1：已使用 2：已过期
     */
    private Integer surScoreState;

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewNote() {
        return reviewNote;
    }

    public void setReviewNote(String reviewNote) {
        this.reviewNote = reviewNote;
    }

    public void setSurReviewState(ReviewState surReviewState) {
        this.surReviewState = surReviewState;
    }

    public Integer getSurUserId() {
        return surUserId;
    }

    public void setSurUserId(Integer surUserId) {
        this.surUserId = surUserId;
    }

    public Integer getSurExchangeScore() {
        return surExchangeScore;
    }

    public void setSurExchangeScore(Integer surExchangeScore) {
        this.surExchangeScore = surExchangeScore;
    }

    public Long getSurExchangeCash() {
        return surExchangeCash;
    }

    public void setSurExchangeCash(Long surExchangeCash) {
        this.surExchangeCash = surExchangeCash;
    }

    public Date getSurExchangeDate() {
        return surExchangeDate;
    }

    public void setSurExchangeDate(Date surExchangeDate) {
        this.surExchangeDate = surExchangeDate;
    }

    public Date getSurStartDate() {
        return surStartDate;
    }

    public void setSurStartDate(Date surStartDate) {
        this.surStartDate = surStartDate;
    }

    public Date getSurEndDate() {
        return surEndDate;
    }

    public void setSurEndDate(Date surEndDate) {
        this.surEndDate = surEndDate;
    }

    public Long getSurUseMoney() {
        return surUseMoney;
    }

    public void setSurUseMoney(Long surUseMoney) {
        this.surUseMoney = surUseMoney;
    }

    public ReviewState getSurReviewState() {
        return surReviewState;
    }

    public Integer getSurScoreState() {
        return surScoreState;
    }

    public void setSurScoreState(Integer surScoreState) {
        this.surScoreState = surScoreState;
    }

    public Integer getSurId() {
        return surId;
    }

    public void setSurId(Integer surId) {
        this.surId = surId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
