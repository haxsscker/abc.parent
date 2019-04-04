package com.autoserve.abc.web.module.screen.mobileIos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.BanelDO;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.intf.banel.BanelService;
import com.autoserve.abc.service.biz.result.ListResult;

public class Index {
	
	@Autowired
    private HttpServletResponse response;
	@Resource
	private BanelService banelService;	
	@Autowired
    private HttpSession session;
	public JsonMobileVO execute(Context context) throws IOException {
		JsonMobileVO result = new JsonMobileVO();
		System.out.println("=============session:"+session.getId());
		try {
			Map<String, Object> bannerMap = new HashMap<String, Object>();
			Map<String, Object> prodMap = new HashMap<String, Object>();
			Map<String, Object> objMap = new HashMap<String, Object>();
			
			List<Map<String, Object>> bannerList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> prodList = new ArrayList<Map<String, Object>>();
			
			
			ListResult<BanelDO> banelResult = banelService.queryListByGroup("app");
			

	    	if(banelResult.isSuccess()) {
	    		List<BanelDO> list = banelResult.getData();
	    		for(BanelDO banelDao:list){
	    		bannerMap = new HashMap<String, Object>();
				bannerMap.put("id", banelDao.getId());
				bannerMap.put("url", banelDao.getBanelUrl());
				bannerMap.put("linkurl", banelDao.getLinkUrl());//banner图跳转链接
				bannerList.add(bannerMap);
	    		}
	    	}    	
//			bannerMap.put("id", "1");
//			bannerMap.put("url", "/Images/banner.png");
//			bannerList.add(bannerMap);
//			
//			bannerMap = new HashMap<String, Object>();
//			bannerMap.put("id", "2");
//			bannerMap.put("url", "/Images/banner.png");
//			bannerList.add(bannerMap);
//			
//			bannerMap = new HashMap<String, Object>();
//			bannerMap.put("id", "3");
//			bannerMap.put("url", "/Images/banner.png");
//			bannerList.add(bannerMap);

			prodMap = new HashMap<String, Object>();
			
			prodMap.put("prodId", 1);
			prodMap.put("prodName", "信用贷");
			prodMap.put("prodDesc", "100%本息保障，出现逾期，新华久久贷先行垫付");
			prodMap.put("prodState", "项目可投");
			prodMap.put("prodImg", "/Images/c-jkxqy-icon.png");
			prodList.add(prodMap);
			
			prodMap = new HashMap<String, Object>();
			prodMap.put("prodId", 2);
			prodMap.put("prodName", "抵押贷");
			prodMap.put("prodDesc", "100%本息保障，出现逾期，新华久久贷先行垫付");
			prodMap.put("prodState", "项目可投");
			prodMap.put("prodImg", "/Images/c-jkxqy-icon.png");
			prodList.add(prodMap);
			
			prodMap = new HashMap<String, Object>();
			prodMap.put("prodId", 3);
			prodMap.put("prodName", "担保贷");
			prodMap.put("prodDesc", "100%本息保障，出现逾期，新华久久贷先行垫付");
			prodMap.put("prodState", "项目可投");
			prodMap.put("prodImg", "/Images/c-jkxqy-icon.png");
			prodList.add(prodMap);
			
			prodMap = new HashMap<String, Object>();
			prodMap.put("prodId", 4);
			prodMap.put("prodName", "综合贷");
			prodMap.put("prodDesc", "100%本息保障，出现逾期，新华久久贷先行垫付");
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
