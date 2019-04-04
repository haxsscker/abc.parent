package com.autoserve.abc.web.module.screen.bhyhNotify;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.web.util.HttpRequestDeviceUtils;

public class InvestToMarginReturn {
	private final static Logger logger = LoggerFactory.getLogger(InvestToMarginReturn.class);
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  resq;
	
	   public void execute(Context context, Navigator nav, ParameterParser params) {
		   logger.info("===================融资账户转账同步返回===================");
		   String ResultCode = "";
		   String Message = "";
		   boolean isMobileDevice=false;
		   //判断请求是否来自手机
	       	if(HttpRequestDeviceUtils.isMobileDevice(resq)) {
	       		logger.info("===================来自手机===================");
	       		isMobileDevice=true;
	       		ResultCode = params.getString("RpCode");
	       		Message = FormatHelper.GBKDecodeStr(params.getString("RpDesc"));
	       	}else{
	       		ResultCode = params.get("RespCode") == null ? "" : (String)params.get("RespCode");
	       		Message = FormatHelper.GBKDecodeStr(params.getString("RespDesc"));
	       	}
	        try {
	        	//判断请求是否来自手机
	        	if(isMobileDevice) {
	        		if (null != ResultCode && ResultCode.equals("000000")) {
	        			Message="恭喜您！转账成功！";
	        		}
	        		context.put("operation", "openAccount");
	        		context.put("message", Message);
	        		nav.forwardTo("/mobile/message").end();
	        		return;
	        	}
	            if (null != ResultCode && ResultCode.equals("000000")) {
	            	nav.redirectToLocation("/account/myAccount/AccountOverview");
	            } else {
	            	context.put("ResultCode", ResultCode);
	            	context.put("Message", Message);
	            	nav.forwardTo("/error").end();
	            }
	        } catch (Exception e) {
	            logger.error("[openAccount] error: ", e);
	        }
	    }
}
