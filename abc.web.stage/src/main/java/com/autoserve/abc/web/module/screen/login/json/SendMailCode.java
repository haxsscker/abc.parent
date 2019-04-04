package com.autoserve.abc.web.module.screen.login.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.message.mail.SendMailService;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.autoserve.abc.web.util.GenerateUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class SendMailCode
{
	@Autowired
    private HttpSession session;
	@Resource
	private SendMailService sendMailService;
	 public JsonBaseVO execute(Context context, ParameterParser params) {
		    JsonBaseVO result = new JsonBaseVO();
		    String validCode = GenerateUtil.generateValidCode();
		    String emailOrPhone = params.getString("emailOrPhone");
	    	String content = "您的邮箱验证码："+validCode+",感谢使用新华久久贷";
	    	
	    	boolean isSend = sendMailService.sendYzm2Mail(emailOrPhone, content,"新华久久贷邮箱验证");
	    	if(!isSend)
	    	{
	    		context.put("msgError", "邮件发送失败");
	    	}
	    	
	    	session.setAttribute("securityCode", Md5Encrypt.md5(validCode));
	    	
	    	return result;
	 }
}
