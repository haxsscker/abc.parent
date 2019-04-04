package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * LevelDO 等级信息 abc_level
 */
public class LevelDO {
    /**
     * abc_level.lev_id
     */
    private Integer    levId;

    /**
     * 等级名称 abc_level.lev_name
     */
    private String     levName;

    /**
     * 等级最小积分值 abc_level.lev_max_score
     */
    private BigDecimal levMinScore;

    /**
     * 等级最大积分值 abc_level.lev_max_score
     */
    private BigDecimal levMaxScore;

    /**
     * 等级图片路径 abc_level.lev_icon
     */
    private String     levIcon;

    public Integer getLevId() {
        return levId;
    }

    public void setLevId(Integer levId) {
        this.levId = levId;
    }

    public String getLevName() {
        return levName;
    }

    public void setLevName(String levName) {
        this.levName = levName;
    }

    public BigDecimal getLevMaxScore() {
        return levMaxScore;
    }

    public void setLevMaxScore(BigDecimal levMaxScore) {
        this.levMaxScore = levMaxScore;
    }

    public String getLevIcon() {
        return levIcon;
    }

    public void setLevIcon(String levIcon) {
        this.levIcon = levIcon;
    }

    public BigDecimal getLevMinScore() {
        return levMinScore;
    }

    public void setLevMinScore(BigDecimal levMinScore) {
        this.levMinScore = levMinScore;
    }
}
