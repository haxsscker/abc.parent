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
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class RechargeNotify {
	private final static Logger logger = LoggerFactory.getLogger(RechargeNotify.class);
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
	private RechargeService rechargeservice;
	
	   public void execute(Context context, Navigator nav, ParameterParser params) {
            Map notifyMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
            String ResultCode =  (String)notifyMap.get("ResultCode");
            String OrderNo = (String)notifyMap.get("OrderNo");
            String LoanNo = (String)notifyMap.get("LoanNo");
            
	        try {
	        	BaseResult result = dealRecord.modifyDealRecordStateWithDouble(notifyMap);
		        System.out.println("修改资金交易记录："+result.isSuccess()+result.getMessage());
		        
		        PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo((String)notifyMap.get("OrderNo"));
		        CashRecordDO cashrecord = cashrecorddo.getData(); 
		        cashrecord.setCrResponse(notifyMap.toString());
		        cashrecord.setCrResponseState(Integer.valueOf(ResultCode));
		        BaseResult cashresult = cashrecordservice.modifyCashRecordState(cashrecord);
		        System.out.println("修改资金交易记录："+cashresult.isSuccess()+cashresult.getMessage());
		        
		        RechargeRecordDO rechargeDo = new RechargeRecordDO();
		        rechargeDo.setRechargeSeqNo(OrderNo);
		        rechargeDo.setRechargeOutSeqNo(LoanNo);
		        if(ResultCode.equals("88") || ResultCode.equals("90")){
		        	rechargeDo.setRechargeState(1);}
		        else{
		        	rechargeDo.setRechargeState(2);
		        }
		        BaseResult rechargeresult = rechargeservice.updateBackStatus(rechargeDo);
		        System.out.println("修改充值记录："+rechargeresult.isSuccess()+rechargeresult.getMessage());
	        	
	            if (result.isSuccess()&&cashresult.isSuccess()&&rechargeresult.isSuccess()) {
	                resp.getWriter().print("SUCCESS");
	            } else {
	                resp.getWriter().print("fail");
	            }
	        } catch (Exception e) {
	            logger.error("[recharge] error: ", e);
	        }
	    }
}
