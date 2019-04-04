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

public class Getpassword1
{	
	@Resource
	private HttpServletRequest request;
	@Autowired
	private DeployConfigService deployConfigService;
	@Resource
	private HttpSession session;
    public void execute(Context context,Navigator nav, ParameterParser params) {
    	String flag=params.getString("flag");
    	if("2".equals(flag)){//找回交易密码
    		User user=(User) session.getAttribute("user");
    		if(user == null){
        		nav.forwardTo("login/login");
        		return;	
        	}else{
        		context.put("custPhone", user.getUserPhone());
        		context.put("custEmail", user.getUserEmail());
        	}
    	}
    	context.put("flag", flag);
    }
}
