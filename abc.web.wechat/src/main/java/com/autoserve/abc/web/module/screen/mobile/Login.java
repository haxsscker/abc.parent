package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.util.IPUtil;
import com.autoserve.abc.web.util.SafeUtil;

/**
 * 手机端用户登录
 * 
 * @author Bo.Zhang
 *
 */
public class Login {
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private HttpServletRequest request;

	public JsonMobileVO execute(Context context, ParameterParser params) throws IOException {
		JsonMobileVO result = new JsonMobileVO();
		
		try {
			String username = params.getString("username");
			String password = params.getString("password");
			
			if (StringUtil.isBlank(username) || StringUtil.isBlank(password)) {
				result.setResultCode("201");
				result.setResultMessage("登录名或密码错误");
				return result;
	        }
			
			PlainResult<User> findResult = userService.login(username, CryptUtils.md5(password),
                    IPUtil.getUserIpAddr(request),"Wechat");
			
			if (findResult.isSuccess()) {
				User user = findResult.getData();
				Map<String, Object> objMap = new HashMap<String, Object>();
				objMap.put("userId", user.getUserId());
				objMap.put("userName", user.getUserName());
				if(user.getUserType() != null) {
					objMap.put("userType", user.getUserType().getType());
				}
				objMap.put("userEmail", SafeUtil.hideEmail(user.getUserEmail()));
				objMap.put("userPhone", user.getUserPhone());
				objMap.put("phoneValidFlag", user.getUserMobileIsbinded().getState());
				objMap.put("emailValidFlag", user.getUserEmailIsbinded().getState());
//				objMap.put("userHeadImg", "");
				result.setResultCode("200");
				result.setResultMessage("登录成功");
				result.setResult(JSON.toJSON(objMap));
			} else {
				result.setResultCode("201");
				result.setResultMessage(findResult.getMessage());
			}
		} catch (Exception e) {
			result.setResultCode("201");
			result.setResultMessage("请求异常");
		}
		
		return result;
	}

}
