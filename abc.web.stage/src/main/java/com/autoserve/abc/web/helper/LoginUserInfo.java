/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.helper;

import java.util.List;

import com.autoserve.abc.dao.dataobject.RoleDO;

/**
 * 登录用户的基本信息
 * 
 * @author segen189 2014年12月1日 上午10:32:07
 */
public class LoginUserInfo {
    /**
     * 主键
     */
    private Integer      userId;

    /**
     * 用户名
     */
    private String       userName;

    /**
     * 员工工号
     */
    private String       userNo;

    /**
     * 电子邮箱
     */
    private String       userEmail;

    /**
     * 手机号码
     */
    private String       userPhone;

    /**
     * 头像url
     */
    private String       userHeadImg;

    /**
     * 机构id
     */
    private Integer      userOrgId;

    /**
     * 角色
     */
    private List<RoleDO> userRoles;

    /**
     * 登录ip
     */
    private String       loginIp;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public Integer getUserOrgId() {
        return userOrgId;
    }

    public void setUserOrgId(Integer userOrgId) {
        this.userOrgId = userOrgId;
    }

    public List<RoleDO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<RoleDO> userRoles) {
        this.userRoles = userRoles;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

}
