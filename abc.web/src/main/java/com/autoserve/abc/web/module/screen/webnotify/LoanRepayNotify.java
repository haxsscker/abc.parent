package com.autoserve.abc.web.module.screen.webnotify;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.util.EasyPayUtils;

public class LoanRepayNotify {
	 private final static Logger logger = LoggerFactory.getLogger(LoanRepayNotify.class);
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
	    	 Map paramterMap = resq.getParameterMap();
	         Map notifyMap = EasyPayUtils.transformRequestMap(paramterMap);
	       
	         logger.info("[LoanRepayNotify][execute] parameters:{}", JSON.toJSON(notifyMap));
	         
	        /*  String LoanJsonList;
	    	 try {
	    	   String action =notifyMap.get("Action").toString();
			  if(!"1".equals(action)){
					 resp.getWriter().print("SUCCESS");	
				}else{
				 LoanJsonList = URLDecoder.decode(notifyMap.get("LoanJsonList").toString(), "utf-8");
				 JSONArray list= JSON.parseArray(LoanJsonList);
	             JSONObject LoanJsonListMap=(JSONObject)list.get(0);
			     String inOrderNo =LoanJsonListMap.getString("OrderNo");
			     String OrderNo =inOrderNo.substring(0, inOrderNo.length()-1);
	             CashRecordDO cashRecord = new CashRecordDO();
	             cashRecord.setCrSeqNo(OrderNo);
	         //保存回调
	         cashRecord.setCrResponse(JSON.toJSONString(notifyMap));
	         cashRecord.setCrResponseState(200);
	         cashRecordService.modifyCashRecordState(cashRecord);
	        BaseResult result = new BaseResult();//dealRecordService.PayBackNotify(notifyMap);
	         if (result.isSuccess()) {*/
                 try {
					resp.getWriter().print("SUCCESS");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            /* } else {
                 resp.getWriter().print("fail");
             }
					}
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 catch (IOException e) {
	             logger.error("[PayInterfaceNotify] error: ", e);
	         }
	         */}
	       
}
