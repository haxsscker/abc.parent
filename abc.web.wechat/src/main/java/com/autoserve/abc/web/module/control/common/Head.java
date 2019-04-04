package com.autoserve.abc.web.module.control.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;


/**
 * 头部
 */
public class Head {

	@Resource
	private HttpSession session;
	 @Autowired
	private HttpServletResponse response;
	 @Autowired
	 private HttpServletRequest  request;
	 @Resource
	 private UserService userService;
    public void execute(Context context, ParameterParser params) {
//    	String userId=CookieUtils.getCookieValue("abcUserId",request);
//    	if(userId!=null  && !"".equals(userId)){
//    		PlainResult<User> plainResult=userService.findEntityById(Integer.parseInt(userId));
//    		if(plainResult!=null && plainResult.getData()!=null){
//    			context.put("user", plainResult.getData());
//    		}
//    	}
    	User user=(User)session.getAttribute("user");
    	if(user!=null){
    		context.put("user",user);
    	}

    }

}
