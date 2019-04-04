/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 登录日志
 *
 * @author segen189 2015年1月5日 下午1:33:29
 */
public class LoginLogJDO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 登录人ID
     */
    private Integer employeeId;

    /**
     * 登录名
     */
    private String  employeeName;

    /**
     * 登录IP
     */
    private String  ip;

    /**
     * 登录系统时间
     */
    private Date    loginTime;

    /**
     * 退出系统时间
     */
    private Date    logoutTime;

    /**
     * 登录状态 -1：已删除 0：用户登录失败 1：用户登录成功 2：用户主动登出
     */
    private Integer loginState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Integer getLoginState() {
        return loginState;
    }

    public void setLoginState(Integer loginState) {
        this.loginState = loginState;
    }

}
