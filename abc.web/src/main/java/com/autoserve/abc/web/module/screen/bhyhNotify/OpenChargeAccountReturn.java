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

public class OpenChargeAccountReturn {
	private final static Logger logger = LoggerFactory.getLogger(OpenChargeAccountReturn.class);
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
		   logger.info("===================开户同步返回===================");
           Map notifyMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
           logger.info(notifyMap.toString());
           String ResultCode = "";
 		   String Message = "";
 		   
 	       ResultCode = params.get("RespCode") == null ? "" : (String)params.get("RespCode");
 	       
	        try {
	            if (null != ResultCode && ResultCode.equals("000000")) {
	            	nav.redirectToLocation("/moneyReturnManage/rechangeReturn?Message=000000&status=kh");
	            } else {
	            	Message = "开户失败！";
	            	context.put("ResultCode", ResultCode);
	            	context.put("Message", Message);
	            	nav.forwardTo("/moneyReturnManage/error").end();
	            }
	        } catch (Exception e) {
	            logger.error("[openAccount] error: ", e);
	        }
	    }
}
