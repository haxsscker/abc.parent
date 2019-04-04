package com.autoserve.abc.dao.dataobject;

import java.util.Date;

public class OnlineDO {
	
	private Integer id;
	
	private Integer userId;
	
	private Date dayDate;
	
	private Date heartTime;
	
	private Integer onlineLength;
	
	private Integer scoreNum;

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

	public Date getDayDate() {
		return dayDate;
	}

	public void setDayDate(Date dayDate) {
		this.dayDate = dayDate;
	}
	
	public Date getHeartTime() {
		return heartTime;
	}

	public void setHeartTime(Date heartTime) {
		this.heartTime = heartTime;
	}

	public Integer getOnlineLength() {
		return onlineLength;
	}

	public void setOnlineLength(Integer onlineLength) {
		this.onlineLength = onlineLength;
	}

	public Integer getScoreNum() {
		return scoreNum;
	}

	public void setScoreNum(Integer scoreNum) {
		this.scoreNum = scoreNum;
	}
	
}
