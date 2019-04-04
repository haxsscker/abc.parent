package com.autoserve.abc.web.module.screen.invest.json;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.web.vo.JsonBaseVO;
public class SendPhoneCode
{
	 	@Autowired
		private HttpSession session;
	 	@Resource
	 	private AccountInfoService accountInfoService;
		public JsonBaseVO execute(Context context, ParameterParser params) {
			JsonBaseVO result = new JsonBaseVO();
			User user = (User) session.getAttribute("user");
			String telephone  = user.getUserPhone();						
			
			Map<String, String> resultMap = accountInfoService.querySmsCode(telephone);
			if("000000".equals(resultMap.get("RespCode")))
			{
				result.setMessage("短信发送成功");
				result.setSuccess(true);
			}
			else{
				result.setMessage("短信发送失败");
				result.setSuccess(false);
			}
			return result;
			
		}
}
