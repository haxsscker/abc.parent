package com.autoserve.abc.web.module.screen.account.myAccount.json;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.CardCityBaseDO;
import com.autoserve.abc.service.biz.intf.cash.CardCityBaseService;
import com.autoserve.abc.service.biz.result.ListResult;
/**
 * 获取一级区域
 * @author XR
 *
 */
public class CardCityBaseList {
	@Resource
	private CardCityBaseService cardcitybaseservice;
	
	 public  ListResult<CardCityBaseDO> execute(Context context, ParameterParser params) {
	 	String province = params.getString("province");
   	 	ListResult<CardCityBaseDO>  cardresult = cardcitybaseservice.queryCardByprovCode(Integer.valueOf(province));
    	return cardresult;
	 }

}
