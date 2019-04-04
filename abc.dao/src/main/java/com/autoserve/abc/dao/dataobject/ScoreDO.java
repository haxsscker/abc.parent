package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 *  ScoreDO 积分类型
 *  abc_score
 */
public class ScoreDO {
    /**
     * 
     * abc_score.score_id
     */
    private Integer scoreId;

    /**
     * 积分名称
     * abc_score.score_name
     */
    private String scoreName;

    /**
     * 积分代码
     * abc_score.score_code
     */
    private String scoreCode;

    /**
     * 积分值
     * abc_score.score_value
     */
    private Integer scoreValue;

    /**
     * 积分创建时间
     * abc_score.score_createtime
     */
    private Date scoreCreatetime;

    /**
     * 积分状态 0：禁用 1：启用 2：已删除
     * abc_score.score_state
     */
    private Integer scoreState;

    public Integer getScoreId() {
        return scoreId;
    }

    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    public String getScoreCode() {
        return scoreCode;
    }

    public void setScoreCode(String scoreCode) {
        this.scoreCode = scoreCode;
    }

    public Integer getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Integer scoreValue) {
        this.scoreValue = scoreValue;
    }

    public Date getScoreCreatetime() {
        return scoreCreatetime;
    }

    public void setScoreCreatetime(Date scoreCreatetime) {
        this.scoreCreatetime = scoreCreatetime;
    }

    public Integer getScoreState() {
        return scoreState;
    }

    public void setScoreState(Integer scoreState) {
        this.scoreState = scoreState;
    }
}
