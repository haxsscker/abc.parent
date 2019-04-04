package com.autoserve.abc.web.module.screen.register.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.message.sms.SendMsgService;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.autoserve.abc.web.module.screen.invest.json.CreateInvest;
import com.autoserve.abc.web.util.GenerateUtil;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;
public class SendPhoneCode
{
	private static final Logger log = LoggerFactory.getLogger(SendPhoneCode.class);
		@Resource
		private HttpServletRequest request;
	 	@Autowired
		private HttpSession session;
	 	@Resource
	 	private SendMsgService sendMsgService;
		public JsonBaseVO execute(Context context, ParameterParser params) {
			String ip = request.getRemoteHost();
			log.info("发送手机验证码请求IP=============="+ip);
			String realIp = IPUtil.getUserIpAddr(request);
			log.info("发送手机验证码请求真实的IP=============="+realIp);
			JsonBaseVO result = new JsonBaseVO();
			String telephone  = params.getString("telephone");			
			String personName = params.getString("username");
			String securityCode = params.getString("imgCode");//图形验证码，防恶意发短信
			String securityfromSession = (String) session
					.getAttribute("smsSecurityCode");
			log.info("用户输入图形验证码=============="+securityCode);
			String md5SecurityCode = "";
			if(!StringUtil.isEmpty(securityCode)){
				md5SecurityCode = Md5Encrypt.md5(securityCode);
				log.info("用户输入图形验证码加密=============="+md5SecurityCode);
			}
			log.info("会话中保存图形验证码=============="+securityfromSession);
			boolean flag = securityfromSession == null || securityCode == null
					|| !securityfromSession.equalsIgnoreCase(md5SecurityCode);
			log.info("验证码比对是否成功=============="+!flag);
			if (flag) {
				if (securityfromSession == null) {
					result.setMessage("图形验证码已失效，请重新获取");
				}else if(null ==securityCode){
					result.setMessage("请输入图形验证码");
				} else {
					result.setMessage("图形验证码错误");
				}
				result.setSuccess(false);
				return result;
			}
			
			String validCode = GenerateUtil.generateValidCode();
//			String validCode = "111111";
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
			session.setAttribute("securityCode", Md5Encrypt.md5(validCode));
			return result;
			
		}
}
