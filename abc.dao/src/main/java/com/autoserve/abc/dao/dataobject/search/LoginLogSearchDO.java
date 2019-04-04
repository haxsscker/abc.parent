/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject.search;

/**
 * 用户登录日志信息查询条件
 *
 * @author segen189 2015年1月5日 上午10:24:20
 */
public class LoginLogSearchDO {
    /**
     * 用户名
     */
    private String  employeeName;

    /**
     * 登录状态
     */
    private Integer loginState;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getLoginState() {
        return loginState;
    }

    public void setLoginState(int loginState) {
        this.loginState = loginState;
    }

}
