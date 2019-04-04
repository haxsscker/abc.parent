package com.autoserve.abc.web.module.screen.webnotify;

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
import com.alibaba.fastjson.JSONObject;
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
import com.autoserve.abc.service.util.EasyPayUtils;


/**
 * 
 * @author andy
 *
 *充值返回接口
 */
public class RechargeBgRetUrl {
	
	private final static Logger logger = LoggerFactory.getLogger(RechargeBgRetUrl.class);
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
	private HuifuPayService huifuPayService;
	
	public void execute(Context context, Navigator nav) {
		
        
		HuiFuData data = new HuiFuData();
		data = huifuPayService.rechargeResult(resq);
		String resultCode = data.getRespCode();
        String orderNo = data.getMerPriv();
        String ordId = data.getOrdId();
//		Map notifyMap = new HashMap();
//		notifyMap.put("params", data);
//		String crResponse = JSON.toJSONString(data);
//		
//		 BaseResult result = dealRecord.modifyDealRecordStateWithDouble(notifyMap);
//	        System.out.println("修改资金交易记录："+result.isSuccess()+result.getMessage());
//	        
//	        PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo(orderNo);
//	        CashRecordDO cashrecord = cashrecorddo.getData(); 
//	        cashrecord.setCrResponse(crResponse);
//	        cashrecord.setCrResponseState(Integer.valueOf(resultCode));
//	        BaseResult cashresult = cashrecordservice.modifyCashRecordState(cashrecord);
//	        System.out.println("修改资金交易记录："+cashresult.isSuccess()+cashresult.getMessage());
//	        
//	        RechargeRecordDO rechargeDo = new RechargeRecordDO();
//	        rechargeDo.setRechargeSeqNo(orderNo);
//	        rechargeDo.setRechargeOutSeqNo(ordId);
	        if(resultCode.equals("000")){
//	        	rechargeDo.setRechargeState(1);
	        	context.put("ordId", ordId);
//	        	String trxId = notifyMap.get("trxId").toString();
	        	String trxId = data.getTrxId();
	        	String recvOrdId = "RECV_ORD_ID_" + trxId;
	        	System.out.println(recvOrdId);
	        	context.put("trxId", trxId);
	        	context.put("recvOrdId", recvOrdId);
	        	nav.forwardTo("/receive");
	        }else{
//	        	rechargeDo.setRechargeState(2);
	        	System.out.println("充值失败，失败原因："+data.getRespDesc());
	        	}
//	        BaseResult rechargeresult = rechargeservice.updateBackStatus(rechargeDo);
//	        System.out.println("修改充值记录："+rechargeresult.isSuccess()+rechargeresult.getMessage());
	        
//	        try {
//	            if (result.isSuccess()&&cashresult.isSuccess()&&rechargeresult.isSuccess()) {
//	                resp.getWriter().print("SUCCESS");
//	            } else {
//	                resp.getWriter().print("fail");
//	            }
//	        } catch (Exception e) {
//	            logger.error("[recharge] error: ", e);
//	        }
		
	}
	
}
