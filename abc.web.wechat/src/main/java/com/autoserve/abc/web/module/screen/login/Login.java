package com.autoserve.abc.web.module.screen.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;

/**
 * 登录
 */
public class Login {

    @Autowired
    private HttpSession session;

    public void execute(Context context, ParameterParser params) {
        context.put("redirectUrl", params.getString("redirectUrl"));

    }

}
