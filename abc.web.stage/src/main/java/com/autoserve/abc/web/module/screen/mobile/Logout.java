package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;

/**
 * 用户登出
 * 
 * @author tabor
 *
 */
public class Logout {

	@Autowired
    private HttpServletResponse response;

	public JsonMobileVO execute(Context context, ParameterParser params) throws IOException {
		JsonMobileVO result = new JsonMobileVO();
		
		try {
			String userId = params.getString("userId");
			
			if(userId == null || "".equals(userId)) {
				result.setResultCode("201");
				result.setResultMessage("请求用户id不能为空");
				return result;
			}
			result.setResultCode("200");
		} catch (Exception e) {
			result.setResultCode("201");
			result.setResultMessage("请求异常");
		}

		return result;
	}

}
