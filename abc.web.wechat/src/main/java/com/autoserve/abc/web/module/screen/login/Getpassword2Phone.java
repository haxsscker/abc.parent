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
import com.autoserve.abc.service.message.sms.SendMsgService;
import com.autoserve.abc.web.util.GenerateUtil;
import com.octo.captcha.service.image.ImageCaptchaService;


public class Getpassword2Phone
{
	@Autowired
    private ImageCaptchaService imageCaptchaService;
    @Autowired
    private HttpServletRequest  request;
    @Resource
 	private UserService userService;
    @Resource
 	private SendMsgService sendMsgService;
    @Autowired
    private HttpSession session;
	 public void execute(Context context,Navigator nav,ParameterParser params) {
		 	String emailOrPhone =  (String) params.get("emailOrPhone");
	    	context.put("emailOrPhone", emailOrPhone);
	    	String securityCode = params.getString("securityCode");
	    	String flag=params.getString("flag");
	    	context.put("flag", flag);
	    	
	    	
	    	//校验图片验证码
	    	try{
	    		boolean isResponseCorrect = imageCaptchaService.validateResponseForID(request.getSession().getId(), securityCode);
	    		if(!isResponseCorrect)
		    	{
		    		context.put("msgImageCode", "验证码不正确");
		    		context.put("type", 2);
		    		nav.forwardTo("/login/getpassword1").end();
		    		return;
		    	}
	    	}catch(Exception e)
	    	{
	    		context.put("type", 2);
	    		nav.forwardTo("/login/getpassword1").end();
	    	}
	    	//校验手机号
	    	UserDO  userdo = new UserDO();
	    	userdo.setUserPhone(emailOrPhone);
	    	PageResult<UserDO> result=  userService.queryListMobile(userdo, null, null, new PageCondition());
	    	List<UserDO> userDoList = result.getData();
	    	if(userDoList.size()<=0)
	    	{
	    		context.put("msgPhone", "账号不存在");
	    		context.put("type", 2);
	    		nav.forwardTo("/login/getpassword1").end();
	    		return;
	    	}
	    	
	    	//发送短信
	    	String validCode = GenerateUtil.generateValidCode();
	    	String content = "您的手机验证码："+validCode+",有效时间5分钟，感谢使用新华久久贷";
	    	if(userDoList.size()>0)
	        userdo = userDoList.get(0);
	    	boolean isSend = sendMsgService.sendMsg(userdo.getUserPhone(), content, userdo.getUserName(),"2");
	    	if(!isSend)
	    	{
	    		context.put("msgError", "短信发送失败");
	    		context.put("type", 2);
	    		nav.forwardTo("/login/getpassword1").end();
	    		return;
	    	}
	    	context.put("userphone",userdo.getUserPhone());
	    	context.put("username",userdo.getUserName());
	    	session.setAttribute("securityCode", validCode);
	    	
	    	
	   }
}
