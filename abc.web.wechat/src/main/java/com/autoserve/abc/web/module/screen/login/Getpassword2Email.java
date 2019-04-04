package com.autoserve.abc.web.module.screen.login;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.message.mail.SendMailService;
import com.autoserve.abc.web.util.GenerateUtil;
import com.octo.captcha.service.image.ImageCaptchaService;

public class Getpassword2Email
{
	@Autowired
    private HttpSession session;
 	@Resource
 	private UserService userService;
 	@Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private HttpServletRequest  request;
    @Resource
    private SendMailService sendMailService;
    public void execute(Context context,Navigator nav, ParameterParser params) {
    	String emailOrPhone =  (String) params.get("emailOrPhone");
    	context.put("emailOrPhone", emailOrPhone);
    	String securityCode = params.getString("securityCode");
    	String flag=params.getString("flag");
    	context.put("flag", flag);
    	
    	try{
//	    	boolean isResponseCorrect = imageCaptchaService.validateResponseForID(request.getSession().getId(), securityCode);
    		String securityfromSession=(String)session.getAttribute("securityCode");
	    	if(securityfromSession==null || securityCode==null || !securityfromSession.equalsIgnoreCase(securityCode)){
	    		if(securityfromSession==null){
	    			context.put("msgImageCode","验证码已失效，请重新获取");
            	}else{
            		context.put("msgImageCode","验证码错误");
            	}
	    		context.put("type", 1);
	    		nav.forwardTo("/login/getpassword1").end();
	    		return;
	    	} 
    	}catch(Exception e)
    	{
    		context.put("type",1);
    		nav.forwardTo("/login/getpassword1").end();
    	}
    	
    	UserDO  userdo = new UserDO();
    	userdo.setUserEmail(emailOrPhone);
    	PageResult<UserDO> result=  userService.queryListEmail(userdo, null, null, new PageCondition());
    	List<UserDO> userDoList = result.getData();
    	if(userDoList.size()<=0)
    	{
    		context.put("msgEmail", "账号不存在");
    		context.put("type", 1);
    		nav.forwardTo("/login/getpassword1").end();
    		return;
    	}
    	

    	//发送邮件
    	String validCode = GenerateUtil.generateValidCode();
    	if(userDoList.size()>0)
    	userdo = userDoList.get(0);
	    try{
	    	sendMailService.sendYzm2Mail(emailOrPhone, userdo.getUserName()+":验证码为："+validCode, "新华久久贷验证信息");
	    	session.setAttribute("securityCode", validCode);   
	    }
	    catch(Exception e)
	    {
	    	context.put("type", 1);
	    	nav.forwardTo("/login/getpassword1").end();
	    	System.out.println("邮件发送有问题");
	    }
    
    	
    }
}
