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
import com.autoserve.abc.service.util.EasyPayUtils;
import com.autoserve.abc.web.util.HttpRequestDeviceUtils;

public class WebInvestReturn {
	private final static Logger logger = LoggerFactory.getLogger(OpenAccountReturn.class);
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  resq;
	
	   public void execute(Context context, Navigator nav, ParameterParser params) {
		   logger.info("===================投资同步返回===================");
            Map notifyMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
            logger.info(notifyMap.toString());
	        String ResultCode = notifyMap.get("RespCode") == null ? null : (String)notifyMap.get("RespCode");
	        String Message = params.getString("RespDesc");
	        try {
	        	//判断请求是否来自手机
	        	if(HttpRequestDeviceUtils.isMobileDevice(resq)) {
	        		context.put("message", Message);
	        		nav.forwardTo("/mobile/default").end();
	        	}
	            if (null != ResultCode && ResultCode.equals("000000")) {
	            	nav.redirectToLocation("/invest/investList");
	            } else {
	            	context.put("ResultCode", ResultCode);
	            	context.put("Message", Message);
	            	nav.forwardTo("/error").end();
	            }
	        } catch (Exception e) {
	            logger.error("[bindCard] error: ", e);
	        }
	    }


}
