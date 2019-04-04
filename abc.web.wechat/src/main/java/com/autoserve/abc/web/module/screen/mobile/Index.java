package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;

public class Index {
	
	@Autowired
    private HttpServletResponse response;

	public JsonMobileVO execute(Context context) throws IOException {
		JsonMobileVO result = new JsonMobileVO();
		
		try {
			Map<String, Object> bannerMap = new HashMap<String, Object>();
			Map<String, Object> prodMap = new HashMap<String, Object>();
			Map<String, Object> objMap = new HashMap<String, Object>();
			
			List<Map<String, Object>> bannerList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> prodList = new ArrayList<Map<String, Object>>();
			
			bannerMap.put("id", "1");
			bannerMap.put("url", "/Images/banner.png");
			bannerList.add(bannerMap);
			
			bannerMap = new HashMap<String, Object>();
			bannerMap.put("id", "2");
			bannerMap.put("url", "/Images/banner.png");
			bannerList.add(bannerMap);
			
			bannerMap = new HashMap<String, Object>();
			bannerMap.put("id", "3");
			bannerMap.put("url", "/Images/banner.png");
			bannerList.add(bannerMap);
			
			prodMap = new HashMap<String, Object>();
			prodMap.put("prodId", 1);
			prodMap.put("prodName", "抵押标");
			prodMap.put("prodDesc", "借款期限：12、18、24个月");
			prodMap.put("prodState", "项目可投");
			prodMap.put("prodImg", "/Images/c-jkxqy-icon.png");
			prodList.add(prodMap);
			
			prodMap = new HashMap<String, Object>();
			prodMap.put("prodId", 2);
			prodMap.put("prodName", "担保标");
			prodMap.put("prodDesc", "借款期限：12、18、24个月");
			prodMap.put("prodState", "项目可投");
			prodMap.put("prodImg", "/Images/c-jkxqy-icon.png");
			prodList.add(prodMap);
			
			objMap.put("bannerList", JSON.toJSON(bannerList));
			objMap.put("prodList", JSON.toJSON(prodList));
			
			result.setResultCode("200");
			result.setResult(JSON.toJSON(objMap));
		} catch (Exception e) {
			result.setResultCode("201");
			result.setResultMessage("请求异常");
		}
		
		return result;
	}

}
