package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 积分兑换投资券记录
 * abc_score_usage_record
 */
public class ScoreUsageRecordWithNameDO {
    /**
     * abc_score_usage_record.sur_id
     */
    private Integer surId;

    /**
     * 用户ID 关联user表
     * abc_score_usage_record.sur_user_id
     */
    private Integer surUserId;

    private String userName;

    /**
     * 兑换积分
     * abc_score_usage_record.sur_exchange_score
     */
    private Integer surExchangeScore;

    /**
     * 兑换金额
     * abc_score_usage_record.sur_exchange_cash
     */
    private Long surExchangeCash;

    /**
     * 兑现日期
     * abc_score_usage_record.sur_exchange_date
     */
    private Date surExchangeDate;

    /**
     * 有效开始日期
     * abc_score_usage_record.sur_start_date
     */
    private Date surStartDate;

    /**
     * 有效结束日期
     * abc_score_usage_record.sur_end_date
     */
    private Date surEndDate;

    /**
     * 划转金额 即已使用金额
     * abc_score_usage_record.sur_use_money
     */
    private Long surUseMoney;

    /**
     * 审核状态 0：待审核 1：审核已通过 2：审核未通过
     * abc_score_usage_record.sur_review_state
     */
    private Integer surReviewState;

    /**
     * 投资券状态 0：未使用 1：已使用 2：已过期
     * abc_score_usage_record.sur_score_state
     */
    private Integer surScoreState;

    public Integer getSurId() {
        return surId;
    }

    public void setSurId(Integer surId) {
        this.surId = surId;
    }

    public Integer getSurUserId() {
        return surUserId;
    }

    public void setSurUserId(Integer surUserId) {
        this.surUserId = surUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getSurReviewState() {
        return surReviewState;
    }

    public void setSurReviewState(Integer surReviewState) {
        this.surReviewState = surReviewState;
    }

    public Integer getSurScoreState() {
        return surScoreState;
    }

    public void setSurScoreState(Integer surScoreState) {
        this.surScoreState = surScoreState;
    }
}
