package com.autoserve.abc.web.module.screen.bhyhNotify;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.service.requestcontext.parser.ValueParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.service.biz.impl.cash.Common;
import com.autoserve.abc.service.biz.impl.cash.LoanJsonList;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class InvestNotify {
    private final static Logger logger = LoggerFactory.getLogger(PayInterfaceNotify.class);
    @Resource
    private LoanService         loanService;
    @Resource
    private InvestService       investService;
    @Resource
    private CashRecordService   cashRecordService;
    @Resource
    private DealRecordService   dealRecordService;
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  resq;
   

    public void execute(Context context, ParameterParser params) {
    	
         Map notifyMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
         logger.info("[InvestNotify][execute] parameters:{}", JSON.toJSON(notifyMap));
         
            String LoanJsonList;
            BaseResult result = new BaseResult();
			/*try {
				 LoanJsonList = URLDecoder.decode(notifyMap.get("LoanJsonList").toString(), "utf-8");
				 JSONArray list=JSON.parseArray(LoanJsonList);
   	             JSONObject LoanJsonListMap=(JSONObject)list.get(0);
				 String OrderNo =LoanJsonListMap.getString("OrderNo");
				 PlainResult<CashRecordDO> plainResult = cashRecordService.queryCashRecordBySeqNo(OrderNo);
				 String response = plainResult.getData().getCrResponse();
				 if(response==null||"".equals(response)){
					 notifyMap.put("LoanJsonList", LoanJsonList);
					// result = dealRecordService.doublePayMentNotify(notifyMap);
					
				 }
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			*/
         
            result.setSuccess(true);
         try {
            /* if (result.isSuccess()) {*/
                 resp.getWriter().print("SUCCESS");
             /*} else {
                 resp.getWriter().print("fail");
             }*/
         } catch (IOException e) {
             logger.error("[InvestNotify] error: ", e);
         }
         }
       
}
