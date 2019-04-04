package com.autoserve.abc.web.module.control.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.web.helper.DeployConfigService;

public class AccountLeft
{
	@Resource
	private HttpSession session;
	@Autowired
	private DeployConfigService deployConfigService;
	@Resource
	private HttpServletRequest request;
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.redirectToLocation(deployConfigService.getLoginUrl(request));
    		return;
    	}  
    	context.put("user", user);   	
    }
}
