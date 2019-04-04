package com.autoserve.abc.dao.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动类abc_xh_act_user.java的实现描述： 类实现描述
 * 
 * @author sxd
 */
public class ActUserDO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5505592611495733857L;
	/**
     * au_id
     */
    private Integer           auId;
    
    /**
     * 活动ID act_id
     */
    private Integer           actId;
    
    /**
     * 奖项ID ap_id
     */
    private Integer           apId;

    /**
     * 用户ID au_userid
     */
    private Integer           auUserId;

    /**
     * 发布时间 au_createtime
     */
    private Date              auCreatetime;

    /**
     * 获奖号码 au_phone
     */
    private String            auPhone;
    
    /**
     * 描述
     */
    private String 			  auNote;

	public Integer getAuId() {
		return auId;
	}

	public void setAuId(Integer auId) {
		this.auId = auId;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	public Integer getApId() {
		return apId;
	}

	public void setApId(Integer apId) {
		this.apId = apId;
	}

	public Integer getAuUserId() {
		return auUserId;
	}

	public void setAuUserId(Integer auUserId) {
		this.auUserId = auUserId;
	}

	public Date getAuCreatetime() {
		return auCreatetime;
	}

	public void setAuCreatetime(Date auCreatetime) {
		this.auCreatetime = auCreatetime;
	}

	public String getAuPhone() {
		return auPhone;
	}

	public void setAuPhone(String auPhone) {
		this.auPhone = auPhone;
	}

	public String getAuNote() {
		return auNote;
	}

	public void setAuNote(String auNote) {
		this.auNote = auNote;
	}
    
    
}
