package com.autoserve.abc.web.module.screen.webnotify.hfnotify;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;

public class CreditAssignBgRetUrl {

	@Resource
	HuifuPayService huifuPayService;
	
	@Resource 
	HttpServletRequest request;
	
	public void execute(ParameterParser params,Navigator nav,Context context ){
		
		HuiFuData data = huifuPayService.CreditAssignResult(request);
		
		String respCode = data.getRespCode();
		String respDesc = data.getRespDesc();
		String ordId = data.getOrdId();
		String recvOrdId = data.getRecvOrdId();
		if(!respCode.equals("000")){
			System.out.println("债权转让投资失败，失败原因："+respDesc);
			
		}
		context.put("ordId", ordId);
		context.put("recvOrdId", recvOrdId);
		nav.forwardTo("/receive");
		
	}
}
