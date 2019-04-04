package com.autoserve.abc.web.module.screen.wxproxy;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.WxTokenDO;
import com.autoserve.abc.service.biz.intf.wxproxy.WxProxyService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class GenerateToken {
	@Resource
	private WxProxyService wxProxyService;
	 private final static Logger logger = LoggerFactory.getLogger(GenerateToken.class);
	 public void execute(Context context, ParameterParser params) {
		  // PlainResult<Map<String, String>> result = new PlainResult<Map<String, String>>();
/*	       Map<String, String> param = new HashMap<String, String>();
	       String mainJsonStr = params.getString("tokenId");
           JSONObject mainJson = JSON.parseObject(mainJsonStr);
*/	       String AppID = params.getString("AppID");
	       String AppSecret = params.getString("AppSecret");
	       //result = wxProxyService.generateToken(AppID, AppSecret);
	       PlainResult<WxTokenDO> result =  wxProxyService.CreatToken(AppID, AppSecret);
	       WxTokenDO wxTokenDO = result.getData();
	       context.put("accessToken", wxTokenDO.getWxAccessToken());
	       context.put("token", wxTokenDO.getWxToken());
	       context.put("state", wxTokenDO.getWxState());
	       context.put("appId", wxTokenDO.getWxOpenId());
	       
	  }
	       
}
