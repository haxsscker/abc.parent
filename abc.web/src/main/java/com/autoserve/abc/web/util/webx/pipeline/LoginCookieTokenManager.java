/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.util.webx.pipeline;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import com.alibaba.citrus.service.requestcontext.parser.CookieParser;
import com.alibaba.citrus.util.StringUtil;
import com.alibaba.citrus.util.UUID;
import com.autoserve.abc.service.util.CryptUtils;

/**
 * 登录用户cookie管理
 *
 * @author segen189 2014年12月1日 下午7:29:54
 */
public class LoginCookieTokenManager {
    /**
     * cookie中标志用户ID的关键字
     */
    private static final String KEY_LOGIN_COOKIE_USER_ID         = "abcUserId";

    /**
     * cookie中标志用户登录状态实效时间戳的关键字
     */
    private static final String KEY_LOGIN_COOKIE_EXPIRE_TIMESTAP = "abcUserExpire";

    /**
     * cookie中标志用户登录状态的签名关键字
     */
    private static final String KEY_LOGIN_COOKIE_SIGN_TOKEN      = "abcUserToken";

    private static final String salt                             = new UUID(false).nextID();

    /**
     * cookie 默认最长有效时间
     */
    public static final int     defaultCookieMaxValidSeconds     = 3600;

    /**
     * 从cookie中的用户登录标志中检出userId<br>
     * 校验成功返回用户id，校验不成功返回null
     */
    public static Integer checkLoginTokens(CookieParser cookieParser) {
        Integer userIdToken = cookieParser.getInt(KEY_LOGIN_COOKIE_USER_ID);
        String expireToken = cookieParser.getString(KEY_LOGIN_COOKIE_EXPIRE_TIMESTAP);
        String signToken = cookieParser.getString(KEY_LOGIN_COOKIE_SIGN_TOKEN);

        if (userIdToken <= 0 || StringUtil.isBlank(signToken) || StringUtil.isBlank(signToken)) {
            return null;
        } else if (signToken.equals(generateLoginSignToken(userIdToken, expireToken))) {
            return userIdToken;
        } else {
            return null;
        }
    }

    /**
     * 设置用户登录的cookie
     */
    public static void addLoginUserCookies(final int userId, final int maxAgeMills, final HttpServletRequest request,
                                           final HttpServletResponse response) {

        final String expireTimeStamp = String.valueOf(new DateTime().plusMillis(maxAgeMills).getMillis());
        final String cookieDomain = getCookieDomain(request);

        Cookie userIdCookie = new Cookie(KEY_LOGIN_COOKIE_USER_ID, String.valueOf(userId));
        userIdCookie.setPath("/");
        userIdCookie.setMaxAge(maxAgeMills);
        if (cookieDomain != null) {
            userIdCookie.setDomain(cookieDomain);
        }

        Cookie expireTimeCookie = new Cookie(KEY_LOGIN_COOKIE_EXPIRE_TIMESTAP, String.valueOf(expireTimeStamp));
        expireTimeCookie.setPath("/");
        expireTimeCookie.setMaxAge(maxAgeMills);
        if (cookieDomain != null) {
            expireTimeCookie.setDomain(cookieDomain);
        }

        Cookie signCookie = new Cookie(KEY_LOGIN_COOKIE_SIGN_TOKEN, generateLoginSignToken(userId, expireTimeStamp));
        signCookie.setPath("/");
        signCookie.setMaxAge(maxAgeMills);
        if (cookieDomain != null) {
            signCookie.setDomain(cookieDomain);
        }

        response.addCookie(userIdCookie);
        response.addCookie(expireTimeCookie);
        response.addCookie(signCookie);
    }

    /**
     * 设置用户登录的cookie
     */
    public static void removeLoginUserCookies(final HttpServletRequest request, final HttpServletResponse response) {
        addLoginUserCookies(0, 0, request, response);
    }

    /**
     * 生成cookie中的用户成功登录标志
     */
    private static String generateLoginSignToken(int userId, String expireTimeStamp) {
        return CryptUtils.md5(userId + salt + expireTimeStamp);
    }

    /**
     * 获取cookie域
     */
    private static String getCookieDomain(HttpServletRequest request) {
        String curServerName = request.getServerName();

        if (StringUtil.indexOf(curServerName, "zyzx.com") > -1) {
            return "zyzx.com";
        } else if (StringUtil.indexOf(curServerName, "localhost") > -1) {
            return null;
        }

        // return curServerName; TODO
        return null;
    }
}
