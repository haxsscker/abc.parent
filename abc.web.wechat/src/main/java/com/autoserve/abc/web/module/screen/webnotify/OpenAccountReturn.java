package com.autoserve.abc.web.module.screen.webnotify;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.util.EasyPayUtils;
import com.autoserve.abc.web.util.HttpRequestDeviceUtils;

public class OpenAccountReturn {
	private final static Logger logger = LoggerFactory.getLogger(OpenAccountReturn.class);
    @Resource
    private AccountInfoService   accountInfoService;
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  resq;
    @Resource
    private InvestService        investService;
	@Resource
	private DealRecordService dealRecord;
	@Resource
	private CashRecordService cashrecordservice;
	@Resource
	private BankInfoService	bankinfoservice;
	
	   public void execute(Context context, Navigator nav, ParameterParser params) {
		    Map paramterMap = resq.getParameterMap();
            Map notifyMap = EasyPayUtils.transformRequestMap(paramterMap);
	        String ResultCode = (String)notifyMap.get("ResultCode");
	        String Message = params.getString("Message");
	        try {
	        	//判断请求是否来自手机
//	        	if(HttpRequestDeviceUtils.isMobileDevice(resq)) {
//	        		context.put("message", Message);
//	        		nav.forwardTo("/mobile/default").end();
////	            	nav.redirectTo("abcUri").withTarget("mobile/default").withParameter("message", Message);
//	        		return;
//	        	}
	            if (ResultCode.equals("88")) {
	            	nav.redirectToLocation("/account/myAccount/bindAccount");
	            } else {
	            	context.put("ResultCode", ResultCode);
	            	context.put("Message", Message);
	            	nav.forwardTo("/error").end();
//	            	nav.redirectTo("abcUri").withTarget("error").withParameter("ResultCode", ResultCode).withParameter("Message", Message);
	            }
	        } catch (Exception e) {
	            logger.error("[openAccount] error: ", e);
	        }
	    }
}
