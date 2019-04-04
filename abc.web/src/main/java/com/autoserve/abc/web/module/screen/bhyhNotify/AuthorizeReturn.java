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
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;

public class AuthorizeReturn {
	private final static Logger logger = LoggerFactory.getLogger(AuthorizeReturn.class);
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
		   logger.info("===================授权同步返回===================");
		   String ResultCode = "";
		   String Message = "";
		   ResultCode = params.get("RespCode") == null ? "" : (String)params.get("RespCode");
		   try {
	            if (null != ResultCode && ResultCode.equals("000000")) {
	            	nav.redirectToLocation("/moneyReturnManage/rechangeReturn?Message=000000&status=sq");
	            } else {
	            	Message = "授权失败！";
	            	context.put("ResultCode", ResultCode);
	            	context.put("Message", Message);
	            	nav.forwardTo("/moneyReturnManage/error").end();
	            }
	        } catch (Exception e) {
	            logger.error("[authorize] error: ", e);
	        }
	    }
}
