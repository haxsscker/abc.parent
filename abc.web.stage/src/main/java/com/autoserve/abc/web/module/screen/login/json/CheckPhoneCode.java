package com.autoserve.abc.web.module.screen.login.json;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class CheckPhoneCode
{
	 @Autowired
     private HttpSession session;
	 public JsonBaseVO execute(Context context, ParameterParser params)
	 {
		 
		 JsonBaseVO result = new JsonBaseVO();
		 String phoneCode = params.getString("securityCode");
		 String sessionPhoneCode = (String) session.getAttribute("securityCode");
		 if(null == phoneCode || null == sessionPhoneCode 
				 || !sessionPhoneCode.equals(Md5Encrypt.md5(phoneCode)))
		 {
			 result.setSuccess(false);
		 }
		 else{
			 result.setSuccess(true);
		 }
		 
		 return result;
	 }
	 
}
