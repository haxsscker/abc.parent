package com.autoserve.abc.dao.dataobject;

import java.io.Serializable;

/**
 * 活动类abc_xh_act_holiday.java的实现描述： 类实现描述
 * 
 * @author sxd
 */
public class ActHolidayDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -794547636209723578L;
	
	/**
     * ah_id
     */
    private Integer           ahId;
    
    /**
     * 活动ID act_id
     */
    private Integer           actId;
    
    /**
     *  节日ah_day
     */
    private String            ahDay;
    
    /**
     * 抽奖次数ah_count
     */
    private Integer           ahCount;

	public Integer getAhId() {
		return ahId;
	}

	public void setAhId(Integer ahId) {
		this.ahId = ahId;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	public String getAhDay() {
		return ahDay;
	}

	public void setAhDay(String ahDay) {
		this.ahDay = ahDay;
	}

	public Integer getAhCount() {
		return ahCount;
	}

	public void setAhCount(Integer ahCount) {
		this.ahCount = ahCount;
	}
    
}
