package com.autoserve.abc.web.module.screen.webnotify;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.result.BaseResult;

public class OpenAcctBgRetUrl {
	
	private final static Logger logger = LoggerFactory.getLogger(OpenAcctRetUrl.class);
	@Resource
	private AccountInfoService   accountInfoService;
	@Resource
	private HttpServletResponse resp;
	@Resource
	private HttpServletRequest  request;
	@Resource
	HuifuPayService huifuPayService;
	
	public void execute(Context context, Navigator nav, ParameterParser params){
		HuiFuData resultData = new HuiFuData();
		BaseResult result = new BaseResult();
		AccountInfoDO account = new AccountInfoDO();
		
		resultData = huifuPayService.openAccountResult(params);
		String recvOrdId = "RECV_ORD_ID_"+resultData.getTrxId();
		String trxId = resultData.getTrxId();
		String ordId = resultData.getRecvOrdId();
		context.put("recvOrdId", recvOrdId);
		context.put("trxId",trxId);
		context.put("ordId",ordId);
    	if(resultData.getRespCode().equals("000")){
			System.out.println(result.getMessage());
    	}else{
    		System.out.println("开户失败！");
    		logger.error("[OpenAccount] error: ", "开户失败");
    	}
    	nav.forwardTo("/receive");
		
	}
}
