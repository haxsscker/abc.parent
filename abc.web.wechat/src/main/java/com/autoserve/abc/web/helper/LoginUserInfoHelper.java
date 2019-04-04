/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.helper;


/**
 * 登录用户帮助类，用来取得当前用户信息
 *
 * @author segen189 2014年12月1日 上午10:42:52
 */
public class LoginUserInfoHelper {
    /**
     * 登录用户的基本信息
     */
    private static final ThreadLocal<LoginUserInfo> loginUserInfo = new ThreadLocal<LoginUserInfo>();

    private LoginUserInfoHelper() {
    }

    public static LoginUserInfo getLoginUserInfo() {
        return loginUserInfo.get();
    }

    public static void setLoginUserInfo(LoginUserInfo info) {
        loginUserInfo.set(info);
    }

}
