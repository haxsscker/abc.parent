package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * 积分兑换投资券设置 abc_score_config
 */
public class ScoreConfigDO {
    /**
     * abc_score_config.sc_id
     */
    private Integer    scId;

    /**
     * 兑换积分 abc_score_config.sc_max_score
     */
    private BigDecimal scMaxScore;

    /**
     * 积分兑换图片 abc_score_config.sc_score_pic
     */
    private String     scScorePic;

    /**
     * 积分兑换金额 abc_score_config.sc_score_cash
     */
    private BigDecimal scScoreCash;
    
    
    /**
     * 积分兑换的红包的使用范围abc_score_config.sc_red_use_scope
     */
    private String     useScope;

    /**
     * 积分兑换的红包的有效期abc_score_config.sc_red_closetime
     */
    private Integer    redCloseTime;
    

    public Integer getScId() {
        return scId;
    }

    public void setScId(Integer scId) {
        this.scId = scId;
    }

    public BigDecimal getScMaxScore() {
        return scMaxScore;
    }

    public void setScMaxScore(BigDecimal scMaxScore) {
        this.scMaxScore = scMaxScore;
    }

    public String getScScorePic() {
        return scScorePic;
    }

    public void setScScorePic(String scScorePic) {
        this.scScorePic = scScorePic;
    }

    public BigDecimal getScScoreCash() {
        return scScoreCash;
    }

    public void setScScoreCash(BigDecimal scScoreCash) {
        this.scScoreCash = scScoreCash;
    }

	/**
	 * @return the useScope
	 */
	public String getUseScope() {
		return useScope;
	}

	/**
	 * @param useScope the useScope to set
	 */
	public void setUseScope(String useScope) {
		this.useScope = useScope;
	}

	/**
	 * @return the redCloseTime
	 */
	public Integer getRedCloseTime() {
		return redCloseTime;
	}

	/**
	 * @param redCloseTime the redCloseTime to set
	 */
	public void setRedCloseTime(Integer redCloseTime) {
		this.redCloseTime = redCloseTime;
	}
    
    
}
