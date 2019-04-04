package com.autoserve.abc.web.module.screen.webnotify.hfnotify;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class RechargeRetUrl {
	private final static Logger logger = LoggerFactory.getLogger(RechargeRetUrl.class);
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
	@Resource
	HuifuPayService huifuPayService;
	
	public void execute(Context context,Navigator nav,ParameterParser params){
		HuiFuData data = new HuiFuData();
		data = huifuPayService.rechargeResult(resq);
		String resultCode = data.getRespCode();
		String resultDesc = data.getRespDesc();
        String orderNo = data.getMerPriv();
        String ordId = data.getOrdId();
        String trxId = data.getTrxId();
        String recvOrdId = "RECV_ORD_ID_" + trxId;
        Map notifyMap = new HashMap();
        notifyMap.put("data", data);
        System.out.println(notifyMap.get("data"));
        String crResponse = JSON.toJSONString(data);
        System.out.println(crResponse);
        
	        BaseResult result = dealRecord.modifyDealRecordStateWithDouble(notifyMap);
	        System.out.println("修改资金交易记录："+result.isSuccess()+result.getMessage());
	        
	        PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo(orderNo);
	        CashRecordDO cashrecord = cashrecorddo.getData(); 
	        cashrecord.setCrResponse(crResponse);
	        cashrecord.setCrResponseState(Integer.valueOf(resultCode));
	        BaseResult cashresult = cashrecordservice.modifyCashRecordState(cashrecord);
	        System.out.println("修改资金交易记录："+cashresult.isSuccess()+cashresult.getMessage());
	        
	        RechargeRecordDO rechargeDo = new RechargeRecordDO();
	        rechargeDo.setRechargeSeqNo(orderNo);
	        rechargeDo.setRechargeOutSeqNo(ordId);
	        if(resultCode.equals("000")){
	        	rechargeDo.setRechargeState(1);
//	        	context.put("ordId", ordId);
//	        	context.put("trxId", trxId);
//	        	context.put("recvOrdId", recvOrdId);
//	        	nav.forwardTo("/receive");
//	        	nav.forwardTo("/account/myAccount/capital").end();
	        	nav.redirectToLocation("/account/myAccount/accountOverview");
	        }else{
	        	rechargeDo.setRechargeState(2);
	        	context.put("resultCode", resultCode);
	        	context.put("resultDesc", resultDesc);
	        	nav.forwardTo("/error");
	        }
	        	BaseResult rechargeresult = rechargeservice.updateBackStatus(rechargeDo);
	        	System.out.println("修改充值记录："+rechargeresult.isSuccess()+rechargeresult.getMessage());
	        	
	        
	    }
	}
	
