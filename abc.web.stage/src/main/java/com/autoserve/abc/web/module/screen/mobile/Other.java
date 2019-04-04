package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.AreaDO;
import com.autoserve.abc.dao.dataobject.CardCityBaseDO;
import com.autoserve.abc.service.biz.intf.cash.CardCityBaseService;
import com.autoserve.abc.service.biz.intf.common.AreaService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.util.Md5Encrypt;

public class Other {
	
	@Resource
	private AreaService areaService;
	@Resource
	private CardCityBaseService cardcitybaseservice;

	public JsonMobileVO execute(Context context, ParameterParser params) throws IOException {
		JsonMobileVO result = new JsonMobileVO();
		
		try {
			String catalog = params.getString("catalog");
			
			if ("1".equals(catalog)) {
				result.setResultCode("200");
			} 
//			else if ("2".equals(catalog)) {
//				//获取地区
//				String superAreaCode = params.getString("superAreaCode");
//				
//				List<AreaDO> list;
//				if(superAreaCode == null || "".equals(superAreaCode)) {
//					list = areaService.queryFirstArea();
//				} else {
//					list = areaService.querySecondArea(superAreaCode);
//				}
//				result.setResultCode("200");
//				result.setResult(JSON.toJSON(list));
//			} 
			else if ("2".equals(catalog)) {
				String md5Flag = params.getString("md5Flag");
				
				List<AreaDO> list = areaService.queryAllArea();
				String md5 = Md5Encrypt.md5(JSON.toJSONString(list));
				if(!md5.equals(md5Flag)) {
					Map<String, Object> objMap = new HashMap<String, Object>();
					objMap.put("md5Flag", md5);
					objMap.put("areaList", JSON.toJSON(list));
					
					result.setResult(JSON.toJSON(objMap));
				}
				result.setResultCode("200");
			} 
			//查询银行卡省市
			else if("3".equals(catalog)){
				String province = params.getString("province");
		   	 	ListResult<CardCityBaseDO>  citys = cardcitybaseservice.queryCardByprovCode(Integer.valueOf(province));
		    	result.setResult(citys.getData());
			}
			else {
				result.setResultCode("201");
				result.setResultMessage("catalog not found");
			}
		} catch (Exception e) {
			result.setResultCode("201");
			result.setResultMessage("error");
		}

		return result;
	}

}
