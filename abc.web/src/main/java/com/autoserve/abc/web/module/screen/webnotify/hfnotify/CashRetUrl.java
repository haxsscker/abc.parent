package com.autoserve.abc.web.module.screen.webnotify.hfnotify;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.service.biz.enums.ToCashState;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class CashRetUrl {
	@Resource
	private HttpServletRequest req;
	@Resource HttpServletResponse resp;
	@Resource 
	private HuifuPayService payService;
	@Resource
 	private DealRecordService dealRecordSer;
	@Resource
 	private CashRecordService cashRecordSer;
	@Resource
	private ToCashService tocashservice;
	public void execute(Context context,ParameterParser params,Navigator nav){
		HuiFuData data=payService.cashResult(req);
		
		TocashRecordDO toCashDo = new TocashRecordDO();
		toCashDo.setTocashSeqNo(data.getMerPriv());
		toCashDo.setTocashOutSeqNo(data.getOrdId());
		if (!data.getRespCode().equals("000")) {
			toCashDo.setTocashState(2);//提现记录表 0提现中 (前台页面展示时显示为提现审核中)，1提现成功，2提现失败',
			context.put("resultCode", data.getRespCode());
			context.put("resultDesc", data.getRespDesc());
			nav.forwardTo("/error");
			System.out.println("异步提现失败,失败原因："+data.getRespDesc());
		}else{
		    System.out.println("异步成功，成功提现了："+data.getTransAmt()+"元");
		    //更新提现记录表
		    toCashDo.setTocashState(ToCashState.PROCESSING.getState());
		    BaseResult tocashresult = tocashservice.updateBySeqNo(toCashDo);
			System.out.println(tocashresult.getMessage());
			
			//内部交易流水号
		    String merPriv = data.getMerPriv();
		    
		    //方法是公共的所以只能跟着方法写
		    Map dataMap = new HashMap();
		    dataMap.put("data", data);
		    BaseResult result = dealRecordSer.modifyDealRecordStateWithDouble(dataMap);
		    //end
		    System.out.println("支付回调接口："+result.isSuccess()+result.getMessage()); 
		    
	        PlainResult<CashRecordDO> cashrecorddo = cashRecordSer.queryCashRecordBySeqNo(merPriv);
	        CashRecordDO cashrecord = cashrecorddo.getData(); 
	        cashrecord.setCrResponse(JSON.toJSONString(data));
	        cashrecord.setCrResponseState(Integer.valueOf(data.getRespCode().toString()));
	        BaseResult cashresult = cashRecordSer.modifyCashRecordState(cashrecord);
	        System.out.println("修改资金交易记录："+cashresult.isSuccess()+cashresult.getMessage());
	        
		    //add by jh.gong
		    String cashFlag = data.getTransAmt();
			context.put("cashFlag", cashFlag);
			nav.redirectToLocation("/account/myAccount/capital?cashType=TXJL");
		}
		
	  }
}
