package com.autoserve.abc.web.module.screen.register.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.message.sms.SendMsgService;
import com.autoserve.abc.web.util.GenerateUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class SendPhoneNotify {
	@Autowired
	private HttpSession session;

	@Resource
	private SendMsgService sendMsgService;

	public JsonBaseVO execute(Context context, ParameterParser params) {
		JsonBaseVO result = new JsonBaseVO();
		String validCode = GenerateUtil.generateValidCode();
		String telephone = params.getString("telephone");
		String personName = params.getString("username");
		String msg = params.getString("msg");
		String content = personName + ",您的手机验证码：" + validCode
				+ ",有效时间5分钟，新华久久贷平台告警：" + msg;
		boolean isSend = sendMsgService.sendMsg(telephone, content, personName,
				"2");
		if (isSend) {
			result.setMessage("短信发送成功");
			result.setSuccess(true);
		} else {
			result.setMessage("短信发送失败");
			result.setSuccess(false);
		}
		session.setAttribute("securityCode", validCode);
		return result;
	}
}
