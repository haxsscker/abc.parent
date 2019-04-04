package com.autoserve.abc.web.module.screen.account.seCenter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.web.helper.DeployConfigService;

public class PhoneVerify {
    @Autowired
    private HttpServletRequest  request;

    @Autowired
    private UserService         userService;

    @Autowired
    private HttpSession         session;

    @Autowired
    private DeployConfigService deployConfigService;

    public void execute(Context context, Navigator nav , ParameterParser params) {
        User user = (User) session.getAttribute("user");
        String isOpen = params.getString("isOpen");
        if (user == null) {
            nav.redirectToLocation(deployConfigService.getLoginUrl(request));
            return;
        } else {
            context.put("user", user);
            context.put("isOpen", isOpen);
        }
    }

}
