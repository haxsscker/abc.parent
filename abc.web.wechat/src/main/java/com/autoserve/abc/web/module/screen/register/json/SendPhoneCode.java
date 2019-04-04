package com.autoserve.abc.web.module.screen.register.json;

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
	    	String validCode = GenerateUtil.generateValidCode();
	    	String telephone  = params.getString("telephone");	    	
	    	String personName = params.getString("username");
	    	String securityCode = params.getString("imgCode");//图形验证码，防恶意发短信
	    	String securityfromSession = (String) session
					.getAttribute("securityCode");
			boolean flag = securityfromSession == null || securityCode == null
					|| !securityfromSession.equalsIgnoreCase(securityCode);
			if (flag) {
				if (securityfromSession == null) {
					result.setMessage("验证码已失效，请重新获取");
				}else if(null ==securityCode){
					result.setMessage("请输入图形验证码");
				} else {
					result.setMessage("图形验证码错误");
				}
				result.setSuccess(false);
				return result;
			}
	    	
	    	
	    	String content = personName+",您的手机验证码："+validCode+",有效时间5分钟，感谢使用新华久久贷";
	    	boolean isSend = sendMsgService.sendMsg(telephone, content, personName,"2");
	    	if(isSend)
	    	{
	    		result.setMessage("短信发送成功");
                result.setSuccess(true);
	    	}
	    	else{
	    		result.setMessage("短信发送失败");
                result.setSuccess(false);
	    	}
	        session.setAttribute("securityCode", validCode);
	        return result;
	        
	    }
}
