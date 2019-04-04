package com.autoserve.abc.web.module.screen.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.service.resource.ResourceLoadingService;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.util.CryptUtils;

/**
 * 登录
 */
public class Login {

    @Autowired
    private HttpSession            session;

    @Autowired
    private ResourceLoadingService resourceLoadingService;

    public void execute(Context context, ParameterParser params) {
        // Resource resource = resourceLoadingService.getResource("/my/virtual/c817de10-51df-4b5e-8d36-1df80c82cc60.jpg");
        session.setMaxInactiveInterval(30);
        String loginState = params.getString("loginState");
        context.put("redirectUrl", params.getString("redirectUrl"));
        if (loginState == null) {
            return;
        }
        if (loginState.equals(CryptUtils.md5("securityCodeError"))) {
            loginState = "1";
        } else if (loginState.equals(CryptUtils.md5("userNameOrPasswordError"))) {
            loginState = "2";
        } else if(loginState.equals(CryptUtils.md5("notEnable"))) {
            loginState = "3";
        } else if(loginState.equals(CryptUtils.md5("delete"))){
        	loginState = "4";
        }
        context.put("loginState", loginState);

    }

}
