package com.autoserve.abc.web.module.screen.account.seCenter;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.User;

public class ModifyPsw {

    @Autowired
    private HttpSession session;

    public void execute(Context context, Navigator nav, ParameterParser params) {
        User user = (User) session.getAttribute("user");
        context.put("user", user);
    }
}
