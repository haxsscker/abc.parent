package com.autoserve.abc.web.module.screen.webnotify;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class AuditNotify {
	 private final static Logger logger = LoggerFactory.getLogger(AuditNotify.class);
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
	         logger.info("[AuditNotify][execute] parameters:{}", JSON.toJSON(notifyMap));
	         
			try {
				     BaseResult result =new BaseResult();
//					/* String LoanJsonList;
//					 LoanJsonList = URLDecoder.decode(notifyMap.get("LoanJsonList").toString(), "utf-8");
//					 JSONArray list=JSON.parseArray(LoanJsonList);
//			         JSONObject LoanJsonListMap=(JSONObject)list.get(0);
//			         String orderList = LoanJsonListMap.getString("OrderNo");
//			         String OrderNo =orderList.substring(0, orderList.length()-3);*/
//			         /*String resultCode =notifyMap.get("Remark2").toString();
//			         String money =notifyMap.get("Remark1").toString();
//				     CashRecordDO cashRecord = new CashRecordDO();
//					 cashRecord.setCrSeqNo(OrderNo);
//					 cashRecord.setCrResponse(JSON.toJSONString(notifyMap));
//					 cashRecord.setCrResponseState(200);
//					 cashRecordService.modifyCashRecordState(cashRecord);
//					 Map<String, String> notifyData =new HashMap();
//					 notifyData.put("OrderNo",OrderNo);
//					 notifyData.put("resultCode", resultCode);
//					 notifyData.put("money", money);*/
//					/* PlainResult<CashRecordDO> plainResult = cashRecordService.queryCashRecordBySeqNo(OrderNo);
//					 String response = plainResult.getData().getCrResponse();
//					 if(response==null||"".equals(response)){
//						 notifyMap.put("LoanJsonList","LoanJsonList");
//					    // result = dealRecordService.auditPayMentNotify(notifyMap);
//						 result.setSuccess(true);
//					 }*/
				     result.setSuccess(true);
					  if (result.isSuccess()) {
			                 resp.getWriter().print("SUCCESS");
			             } else {
			                 resp.getWriter().print("fail");
			             }
				
		 	 	 
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				 logger.error("[AuditNotify] error: ",e1);
			} catch (IOException e) {
	             logger.error("[AuditNotify] error: ", e);
	         }
	         }
	       
}
