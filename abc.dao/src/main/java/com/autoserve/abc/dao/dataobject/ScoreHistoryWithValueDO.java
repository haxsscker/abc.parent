package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 积分记录，带连表查询到的积分类型名字，积分值，代码信息
 */
public class ScoreHistoryWithValueDO {

    private Integer id;

    private Integer userId;

    private Integer scoreId;

    private String scoreName;

    private String scoreValue;

    private String scoreCode;

    private Date createTime;

    private String note;
    
    private Integer flag;       //记录标记(1:表示获取积分,2:表示使用积分)
    
    private Integer shValue;
    
    
    public Integer getShValue() {
		return shValue;
	}

	public void setShValue(Integer shValue) {
		this.shValue = shValue;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public String getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(String scoreValue) {
        this.scoreValue = scoreValue;
    }

    public String getScoreCode() {
        return scoreCode;
    }

    public void setScoreCode(String scoreCode) {
        this.scoreCode = scoreCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

	/**
	 * @return the flag
	 */
	public Integer getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
    
}