/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.helper;

import java.util.List;

import com.autoserve.abc.dao.dataobject.RoleDO;

/**
 * 登录用户工具类，用来取得当前用户信息
 *
 * @author segen189 2014年12月4日 下午8:13:51
 */
public class LoginUserUtil {

    public static Integer getEmpId() {
        return LoginUserInfoHelper.getLoginUserInfo().getEmpId();
    }

    public static String getEmpName() {
        return LoginUserInfoHelper.getLoginUserInfo().getEmpName();
    }

    public static String getEmpNo() {
        return LoginUserInfoHelper.getLoginUserInfo().getEmpNo();
    }

    public static String getEmpEmail() {
        return LoginUserInfoHelper.getLoginUserInfo().getEmpEmail();
    }

    public static String getEmpMobile() {
        return LoginUserInfoHelper.getLoginUserInfo().getEmpMobile();
    }

    public static String getEmpHeadImg() {
        return LoginUserInfoHelper.getLoginUserInfo().getEmpHeadImg();
    }

    public static Integer getEmpOrgId() {
        return LoginUserInfoHelper.getLoginUserInfo().getEmpOrgId();
    }

    public static List<RoleDO> getEmpRoles() {
        return LoginUserInfoHelper.getLoginUserInfo().getEmpRoles();
    }

    public static String getLoginIp() {
        return LoginUserInfoHelper.getLoginUserInfo().getLoginIp();
    }

}
