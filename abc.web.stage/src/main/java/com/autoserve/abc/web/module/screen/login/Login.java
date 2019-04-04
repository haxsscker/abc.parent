package com.autoserve.abc.web.module.screen.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.web.helper.DeployConfigService;

/**
 * 登录
 */
public class Login {

    @Autowired
    private HttpSession session;
    @Resource
	private HttpServletRequest request;
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	User user=(User)session.getAttribute("user");
    	if(user!=null){
    		nav.forwardTo("/account/myAccount/accountOverview").end();
    		return;
    	}
        context.put("redirectUrl", params.getString("redirectUrl"));
    }
}
