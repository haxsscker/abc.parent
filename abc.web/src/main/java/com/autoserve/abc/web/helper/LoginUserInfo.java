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
    private Integer      empId;

    /**
     * 用户名
     */
    private String       empName;

    /**
     * 员工工号
     */
    private String       empNo;

    /**
     * 电子邮箱
     */
    private String       empEmail;

    /**
     * 手机号码
     */
    private String       empMobile;

    /**
     * 头像url
     */
    private String       empHeadImg;

    /**
     * 机构id
     */
    private Integer      empOrgId;

    /**
     * 角色
     */
    private List<RoleDO> empRoles;

    /**
     * 登录ip
     */
    private String       loginIp;

    public List<RoleDO> getEmpRoles() {
        return empRoles;
    }

    public void setEmpRoles(List<RoleDO> empRoles) {
        this.empRoles = empRoles;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public String getEmpHeadImg() {
        return empHeadImg;
    }

    public void setEmpHeadImg(String empHeadImg) {
        this.empHeadImg = empHeadImg;
    }

    public Integer getEmpOrgId() {
        return empOrgId;
    }

    public void setEmpOrgId(Integer empOrgId) {
        this.empOrgId = empOrgId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

}
