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

    public static Integer getUserId() {
        return LoginUserInfoHelper.getLoginUserInfo().getUserId();
    }

    public static String getUserName() {
        return LoginUserInfoHelper.getLoginUserInfo().getUserName();
    }

    public static String getUserNo() {
        return LoginUserInfoHelper.getLoginUserInfo().getUserNo();
    }

    public static String getUserEmail() {
        return LoginUserInfoHelper.getLoginUserInfo().getUserEmail();
    }

    public static String getUserPhone() {
        return LoginUserInfoHelper.getLoginUserInfo().getUserPhone();
    }

    public static String getUserHeadImg() {
        return LoginUserInfoHelper.getLoginUserInfo().getUserHeadImg();
    }

    public static Integer getUserOrgId() {
        return LoginUserInfoHelper.getLoginUserInfo().getUserOrgId();
    }

    public static List<RoleDO> getUserRoles() {
        return LoginUserInfoHelper.getLoginUserInfo().getUserRoles();
    }

    public static String getLoginIp() {
        return LoginUserInfoHelper.getLoginUserInfo().getLoginIp();
    }

}
