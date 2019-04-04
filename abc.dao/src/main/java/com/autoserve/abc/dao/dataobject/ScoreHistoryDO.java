package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  ScoreHistoryDO 积分记录
 *  abc_score_history
 */
public class ScoreHistoryDO {
    /**
     * 
     * abc_score_history.sh_id
     */
    private Integer shId;

    /**
     * 用户ID
     * abc_score_history.sh_user_id
     */
    private Integer shUserId;

    /**
     * 积分ID
     * abc_score_history.sh_score_id
     */
    private Integer shScoreId;

    /**
     * 添加时间
     * abc_score_history.sh_createtime
     */
    private Date shCreatetime;

    /**
     * 备注
     * abc_score_history.sh_note
     */
    private String shNote;

    /**
     * 积分值
     * abc_score_history.sh_value
     * */
    private BigDecimal shValue;
    
    public Integer getShId() {
        return shId;
    }

    public void setShId(Integer shId) {
        this.shId = shId;
    }

    public Integer getShUserId() {
        return shUserId;
    }

    public void setShUserId(Integer shUserId) {
        this.shUserId = shUserId;
    }

    public Integer getShScoreId() {
        return shScoreId;
    }

    public void setShScoreId(Integer shScoreId) {
        this.shScoreId = shScoreId;
    }

    public Date getShCreatetime() {
        return shCreatetime;
    }

    public void setShCreatetime(Date shCreatetime) {
        this.shCreatetime = shCreatetime;
    }

    public String getShNote() {
        return shNote;
    }

    public void setShNote(String shNote) {
        this.shNote = shNote;
    }

	public BigDecimal getShValue() {
		return shValue;
	}

	public void setShValue(BigDecimal shValue) {
		this.shValue = shValue;
	}

}
