package com.autoserve.abc.web.module.screen.mobileIos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.BanelDO;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.banel.BanelService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class Banner {
	@Autowired
    private HttpServletResponse response;
	@Autowired
    private HttpServletRequest request;
	@Resource
	private BanelService banelService;	
	@Autowired
    private HttpSession session;
	public JsonMobileVO execute(Context context,ParameterParser params) throws IOException {
		JsonMobileVO result = new JsonMobileVO();
		try {
			Integer id = params.getInt("id");
			Map<String, Object> bannerMap = new HashMap<String, Object>();
			Map<String, Object> objMap = new HashMap<String, Object>();
			
			PlainResult<BanelDO> banelResult=banelService.queryById(id);
			List<Map<String, Object>> bannerList = new ArrayList<Map<String, Object>>();

	    	if(banelResult.isSuccess()) {
	    		BanelDO banel = banelResult.getData();
	    		bannerMap = new HashMap<String, Object>();
	    		String url = banel.getLinkUrl();
	    		if(!StringUtil.isEmpty(url)){
	    			if(url.indexOf("http") < 0){
	    				url = "http://"+request.getLocalAddr()+":"+request.getLocalPort()+banel.getLinkUrl();
	    			}
	    		}
	    		bannerMap.put("linkurl", url);//banner图跳转链接
				bannerList.add(bannerMap);
				
				objMap.put("bannerList", JSON.toJSON(bannerList));
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
	    	} 
		} catch (Exception e) {
			result.setResultCode("201");
			result.setResultMessage("请求异常");
		}
		return result;
	}

}
