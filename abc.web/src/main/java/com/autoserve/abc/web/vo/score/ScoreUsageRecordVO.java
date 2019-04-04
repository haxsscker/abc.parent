package com.autoserve.abc.web.vo.score;

/**
 * @author RJQ 2014/12/16 21:44.
 */
public class ScoreUsageRecordVO {
    /**
     * abc_score_usage_record.sur_id
     */
    private Integer sco_score_id;

    /**
     * 用户ID 关联user表
     * abc_score_usage_record.sur_user_id
     */
    private String cst_user_name;

    /**
     * 兑换积分
     * abc_score_usage_record.sur_exchange_score
     */
    private Integer sco_to_score;

    /**
     * 兑换金额
     * abc_score_usage_record.sur_exchange_cash
     */
    private Long sco_to_cash;

    /**
     * 兑现日期
     * abc_score_usage_record.sur_exchange_date
     */
    private String sco_to_cash_date;

    /**
     * 审核日期
     * abc_score_usage_record.sur_review_date
     */
    private String sco_check_date;

    /**
     * 审核意见
     * abc_score_usage_record.sur_review_note
     */
    private String sco_check_idear;

    /**
     * 审核状态 0：待审核 1：审核已通过 2：审核未通过
     * abc_score_usage_record.sur_review_state
     */
    private String sco_check_state;

    public Integer getSco_score_id() {
        return sco_score_id;
    }

    public void setSco_score_id(Integer sco_score_id) {
        this.sco_score_id = sco_score_id;
    }

    public String getCst_user_name() {
        return cst_user_name;
    }

    public void setCst_user_name(String cst_user_name) {
        this.cst_user_name = cst_user_name;
    }

    public Integer getSco_to_score() {
        return sco_to_score;
    }

    public void setSco_to_score(Integer sco_to_score) {
        this.sco_to_score = sco_to_score;
    }

    public Long getSco_to_cash() {
        return sco_to_cash;
    }

    public void setSco_to_cash(Long sco_to_cash) {
        this.sco_to_cash = sco_to_cash;
    }

    public String getSco_to_cash_date() {
        return sco_to_cash_date;
    }

    public void setSco_to_cash_date(String sco_to_cash_date) {
        this.sco_to_cash_date = sco_to_cash_date;
    }

    public String getSco_check_date() {
        return sco_check_date;
    }

    public void setSco_check_date(String sco_check_date) {
        this.sco_check_date = sco_check_date;
    }

    public String getSco_check_idear() {
        return sco_check_idear;
    }

    public void setSco_check_idear(String sco_check_idear) {
        this.sco_check_idear = sco_check_idear;
    }

    public String getSco_check_state() {
        return sco_check_state;
    }

    public void setSco_check_state(String sco_check_state) {
        this.sco_check_state = sco_check_state;
    }
}
