package com.autoserve.abc.web.module.screen.webnotify;

import java.util.Map;
import java.util.NavigableMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.service.biz.enums.ToCashState;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.EasyPayUtils;

/*
 * @author gongiuhui
 * 回付同步发回的地址
 * */
public class CashBgRetUrl {
	@Resource
	private HttpServletRequest  resq;
	@Resource
	private HttpServletResponse resp;
	@Resource 
	private HuifuPayService payService;
	@Resource
	private ToCashService tocashservice;
	public void execute(Context context,ParameterParser params,Navigator nav){
		System.out.println(resq.getParameter("CmdId").trim()+"sss");
		HuiFuData data=
		payService.cashResult(resq);
		TocashRecordDO toCashDo = new TocashRecordDO();
		toCashDo.setTocashSeqNo(data.getMerPriv());
		toCashDo.setTocashOutSeqNo(data.getOrdId());
		if (!data.getRespCode().equals("000")) {
			toCashDo.setTocashState(ToCashState.FAILURE.getState());
			BaseResult tocashresult = tocashservice.updateBySeqNo(toCashDo);
			System.out.println("提现失败,失败原因："+data.getRespDesc());
			context.put("resultDesc", data.getRespDesc());
			nav.forwardTo("/error");
		}else{
			
			toCashDo.setTocashState(ToCashState.SUCCESS.getState());
		    BaseResult tocashresult = tocashservice.updateBySeqNo(toCashDo);
			System.out.println(tocashresult.getMessage());
			System.out.println("提现成功，成功提现了："+data.getTransAmt()+"元");
			Map paramterMap = resq.getParameterMap();
	        Map notifyMap = EasyPayUtils.transformRequestMap(paramterMap);
	        String ResultCode = (String) notifyMap.get("RespCode");
	        String OrderNo = (String) notifyMap.get("RespDesc");
	        
	       
		String trxId=data.getTrxId();
		String  recvOrdId=data.getRecvOrdId();
		String ordId = data.getOrdId();
		
		context.put("trxId", trxId);
		context.put("recvOrdId", recvOrdId);
		context.put("ordId", ordId);
		
		nav.forwardTo("/receive");
		}
		    
	  }
}
