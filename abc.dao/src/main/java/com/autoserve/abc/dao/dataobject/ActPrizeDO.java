package com.autoserve.abc.dao.dataobject;

import java.io.Serializable;

/**
 * 活动类abc_xh_act_prize.java的实现描述： 类实现描述
 * 
 * @author sxd
 */
public class ActPrizeDO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8599360711534910847L;

	/**
     * ap_id
     */
    private Integer           apId;
    
    /**
     * 奖项名称 ap_name
     */
    private String            apName;

    /**
     * 奖项比例 ap_percentage
     */
    private Integer           apPercentage;

    /**
     * 奖项个数 ap_count
     */
    private Integer           apCount;

    /**
     * 奖项描述 ap_desc
     */
    private String            apDesc;

    /**
     * 活动ID act_id
     */
    private Integer           actId;

	public Integer getApId() {
		return apId;
	}

	public void setApId(Integer apId) {
		this.apId = apId;
	}

	public String getApName() {
		return apName;
	}

	public void setApName(String apName) {
		this.apName = apName;
	}

	public Integer getApPercentage() {
		return apPercentage;
	}

	public void setApPercentage(Integer apPercentage) {
		this.apPercentage = apPercentage;
	}

	public Integer getApCount() {
		return apCount;
	}

	public void setApCount(Integer apCount) {
		this.apCount = apCount;
	}

	public String getApDesc() {
		return apDesc;
	}

	public void setApDesc(String apDesc) {
		this.apDesc = apDesc;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

    
}
