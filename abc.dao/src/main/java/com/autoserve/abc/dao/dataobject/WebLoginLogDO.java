package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * LoginLog abc_login_log
 */
public class WebLoginLogDO {
    /**
     * abc_login_log.ll_id
     */
    private Integer llId;

    /**
     * 登录人 员工ID 关联员工表 abc_login_log.ll_emp_id
     */
    private Integer llUserId;

    /**
     * 登录IP abc_login_log.ll_ip
     */
    private String  llIp;
    
    /**
     * 登录地址 abc_login_log.ll_area
     */
    private String  llArea;
    /**
     * 登录类型（PC、APP、Wechat） abc_login_log.llLoginType
     */
    private String llLoginType;
    /**
     * 登录系统时间 abc_login_log.ll_login_time
     */
    private Date    llLoginTime;

    /**
     * 退出系统时间 abc_login_log.ll_logout_time
     */
    private Date    llLogoutTime;

    /**
     * 登录状态 -1：已删除 0：用户登录失败 1：用户登录成功 2：用户主动登出 abc_login_log.ll_login_state
     */
    private Integer llLoginState;

    public Integer getLlId() {
        return llId;
    }

    public void setLlId(Integer llId) {
        this.llId = llId;
    }

    public Integer getLlUserId() {
        return llUserId;
    }

    public void setLlUserId(Integer llUserId) {
        this.llUserId = llUserId;
    }

    public String getLlIp() {
        return llIp;
    }

    public void setLlIp(String llIp) {
        this.llIp = llIp;
    }

    public Date getLlLoginTime() {
        return llLoginTime;
    }

    public void setLlLoginTime(Date llLoginTime) {
        this.llLoginTime = llLoginTime;
    }

    public Date getLlLogoutTime() {
        return llLogoutTime;
    }

    public void setLlLogoutTime(Date llLogoutTime) {
        this.llLogoutTime = llLogoutTime;
    }

    public Integer getLlLoginState() {
        return llLoginState;
    }

    public void setLlLoginState(Integer llLoginState) {
        this.llLoginState = llLoginState;
    }

	public String getLlArea() {
		return llArea;
	}

	public void setLlArea(String llArea) {
		this.llArea = llArea;
	}

	public String getLlLoginType() {
		return llLoginType;
	}

	public void setLlLoginType(String llLoginType) {
		this.llLoginType = llLoginType;
	}
}
