/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.sysset;

/**
 * 登录日志
 * 
 * @author segen189 2015年1月6日 下午4:39:12
 */
public class LoginLogVO {
    /**
     * 主键
     */
    private Integer    id;

    /**
     * 登录人ID
     */
    private Integer    employeeId;

    /**
     * 登录名
     */
    private String     employeeName;

    /**
     * 登录IP
     */
    private String     ip;

    /**
     * 登录系统时间
     */
    private String       loginTime;

    /**
     * 退出系统时间
     */
    private String       logoutTime;

    /**
     * 登录状态
     */
    private String loginState;

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

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getLoginState() {
        return loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }
}
