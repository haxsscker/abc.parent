package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * LoginLog abc_login_log
 */
public class LoginLogDO {
    /**
     * abc_login_log.ll_id
     */
    private Integer llId;

    /**
     * 登录人 员工ID 关联员工表 abc_login_log.ll_emp_id
     */
    private Integer llEmpId;

    /**
     * 登录IP abc_login_log.ll_ip
     */
    private String  llIp;

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

    public Integer getLlEmpId() {
        return llEmpId;
    }

    public void setLlEmpId(Integer llEmpId) {
        this.llEmpId = llEmpId;
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
}
