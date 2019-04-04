package com.autoserve.abc.web.module.screen.account;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class DryOpenAccount {
    @Autowired
    private UserService        userservice;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DoubleDryService   doubleDryService;

    public void execute(@Param("empId") int empId, Context context, Navigator nav, ParameterParser params) {
    	Map notifyMap = EasyPayUtils.transformRequestMap(request.getParameterMap());
    	Map<String,String> param= new LinkedHashMap<String,String>();
    	if("1".equals((String)notifyMap.get("AccountMark"))){
    		param.put("TxnTyp", "2");
    	}else if("2".equals((String)notifyMap.get("AccountMark"))){
    		param.put("TxnTyp", "1");
    	}
    	param.put("AccountTyp", "2");
		param.put("AccountNo", (String)notifyMap.get("AccountNo"));
		param.put("AccountName", (String)notifyMap.get("AccountName"));
		param.put("AccountBk", (String)notifyMap.get("AccountBk"));
		param.put("userId", (String)notifyMap.get("empId"));
    	PlainResult<Map> paramMap = doubleDryService.openChargeAccent(param);
        context.put("paramMap", paramMap);
    	context.put("SubmitURL", paramMap.getData().remove("requestUrl"));
    }
}
