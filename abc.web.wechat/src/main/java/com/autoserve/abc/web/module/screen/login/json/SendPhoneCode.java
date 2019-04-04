package com.autoserve.abc.web.module.screen.login.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.message.sms.SendMsgService;
import com.autoserve.abc.web.util.GenerateUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class SendPhoneCode
{
	@Autowired
    private HttpSession session;
 	@Resource
 	private SendMsgService sendMsgService;
	  public JsonBaseVO execute(Context context, ParameterParser params) {
		  
		  JsonBaseVO result = new JsonBaseVO();
		  String userphone = params.getString("userphone"); 
		  String username = params.getString("username");
		  String code = GenerateUtil.generateValidCode();
		  String content = "您的手机验证码："+code+",有效时间5分钟，感谢使用新华久久贷";
		 boolean isSend =  sendMsgService.sendMsg(userphone, content, username,"2");
		 if(!isSend) {
			 result.setMessage("短信发送失败");
			 result.setSuccess(false);
		 }
		 else{
			 result.setSuccess(true);
		 }
		 session.setAttribute("securityCode", code);
		  return result;
	  }
}
