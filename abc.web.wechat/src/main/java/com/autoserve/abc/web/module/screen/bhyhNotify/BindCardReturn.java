package com.autoserve.abc.web.module.screen.bhyhNotify;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.util.EasyPayUtils;
import com.autoserve.abc.web.util.HttpRequestDeviceUtils;

public class BindCardReturn {
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
		   logger.info("===================微信绑卡同步返回===================");
            Map notifyMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
            logger.info(notifyMap.toString());
	        String ResultCode = FormatHelper.GBKDecodeStr(params.getString("RpCode"));// 
			String Message = FormatHelper.GBKDecodeStr(params.getString("RpDesc"));//
	        try {
	        	//判断请求是否来自手机
	        	if(HttpRequestDeviceUtils.isMobileDevice(resq)) {
	        		if (null != ResultCode && ResultCode.equals("000000")) {
	        			Message="恭喜您，修改成功！";
	        		}
	        		context.put("ResultCode", ResultCode);
	        		context.put("message", Message);
	        		context.put("jumpUrl", "/account/myAccount/withdrawCash");
	        		nav.forwardTo("/mobile/message").end();
//	            	nav.redirectTo("abcUri").withTarget("mobile/default").withParameter("message", Message);
	        		return;
	        	}
	            if (null != ResultCode && ResultCode.equals("000000")) {
//	            	nav.redirectToLocation("/account/myAccount/bindAccount");
	            	
	            	nav.redirectToLocation("/account/myAccount/bankCard");
	            	//注册新流程
//	            	nav.redirectToLocation("/register/toregister?step=4");
//	            	nav.redirectTo("abcUri").withTarget("account/myAccount/bindAccount");
	            } else {
	            	context.put("ResultCode", ResultCode);
	            	context.put("Message", Message);
	            	nav.forwardTo("/error").end();
//	            	nav.redirectTo("abcUri").withTarget("error").withParameter("ResultCode", ResultCode).withParameter("Message", Message);
	            }
	        } catch (Exception e) {
	            logger.error("[bindCard] error: ", e);
	        }
	    }
}
