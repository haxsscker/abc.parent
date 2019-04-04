package com.autoserve.abc.web.module.screen.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.intf.user.UserService;

public class Getpassword3
{
	@Autowired
    private HttpSession session;
 	@Resource
 	private UserService userService;
    public void execute(Context context,Navigator nav, ParameterParser params) {
    	String emailOrPhone =  (String) params.get("emailOrPhone");
    	String type = params.getString("type");
    	String flag=params.getString("flag");
    	context.put("emailOrPhone", emailOrPhone);
    	context.put("type", type);
    	context.put("flag", flag);
    }
}
