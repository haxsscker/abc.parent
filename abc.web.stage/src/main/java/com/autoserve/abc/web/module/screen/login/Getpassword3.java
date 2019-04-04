package com.autoserve.abc.web.module.screen.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.util.Md5Encrypt;

public class Getpassword3
{
	@Autowired
    private HttpSession session;
 	@Resource
 	private UserService userService;
    public void execute(Context context,Navigator nav, ParameterParser params) {
    	//校验验证码
    	 String phoneCode = params.getString("securityCode");
		 String sessionPhoneCode = (String) session.getAttribute("securityCode");
		 if(phoneCode==null || sessionPhoneCode==null || !sessionPhoneCode.equals(Md5Encrypt.md5(phoneCode))){
			 context.put("flag", params.getString("flag"));
             context.put("emailOrPhone", params.getString("emailOrPhone"));
             int type=params.getInt("type");
             if(type==1){
            	 nav.forwardTo("/login/getpassword2Email").end();
             }else if(type==2){
            	 nav.forwardTo("/login/getpassword2Phone").end();
             }else{
            	context.put("ResultCode", "405");
	            context.put("Message", "传递参数错误");
	            nav.forwardTo("/error").end();
             }
		 }
    	String emailOrPhone =  (String) params.get("emailOrPhone");
    	String type = params.getString("type");
    	String flag=params.getString("flag");
    	context.put("emailOrPhone", emailOrPhone);
    	context.put("type", type);
    	context.put("flag", flag);
    }
}
