package com.autoserve.abc.web.module.screen.mobileIos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.intf.invite.AppInviteService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class erweima {
	@Autowired
    private HttpSession session;
	@Resource
	private HttpServletRequest  request;
	@Resource
	private AppInviteService appInviteService;
	
	
	public JsonMobileVO execute(Context context,ParameterParser params) throws IOException {
			JsonMobileVO vo = new JsonMobileVO();
			Map<String, Object> bannerMap = new HashMap<String, Object>();
			Map<String, Object> objMap = new HashMap<String, Object>();
		try {
			Integer userId = params.getInt("userId");
			InviteUserType userType = InviteUserType.PERSONAL;
			List<Map<String, Object>> bannerList = new ArrayList<Map<String, Object>>();
			
			PlainResult<String> inviteString = appInviteService.generateAppInviteUrl(userId, userType);
			 
			 bannerMap.put("inviteurl", inviteString);
			 bannerList.add(bannerMap);
				
			 objMap.put("url", JSON.toJSON(bannerList));
			 vo.setResultCode("200");
			 vo.setResult(JSON.toJSON(objMap));
		} catch (Exception e) {
			vo.setResultCode("201");
			vo.setResultMessage("请求异常");
		}
			return vo;
		}

}
