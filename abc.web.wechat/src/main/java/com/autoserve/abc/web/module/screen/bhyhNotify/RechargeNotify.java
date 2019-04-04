package com.autoserve.abc.web.module.screen.bhyhNotify;

import java.util.LinkedHashMap;
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
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
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
		   logger.info("===================充值异步通知===================");
			Map notifyMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
			logger.info(notifyMap.toString());
			String partner_id = FormatHelper.GBK2Chinese(params.getString("PartnerId"));// 
			String MerBillNo = FormatHelper.GBK2Chinese(params.getString("MerBillNo"));//
			String TransAmt = FormatHelper.GBK2Chinese(params.getString("TransAmt"));
			String TransId = FormatHelper.GBK2Chinese(params.getString("TransId"));
			String MerFeeAmt = FormatHelper.GBK2Chinese(params.getString("MerFeeAmt"));
			String FeeAmt = FormatHelper.GBK2Chinese(params.getString("FeeAmt"));
			String MerPriv = FormatHelper.GBK2Chinese(params.getString("MerPriv"));
			String version_no = FormatHelper.GBK2Chinese(params.getString("Version_No"));
			String biz_type = FormatHelper.GBK2Chinese(params.getString("Biz_Type"));// 
			String RespCode = FormatHelper.GBK2Chinese(params.getString("RpCode"));// 
			String RespDesc = FormatHelper.GBK2Chinese(params.getString("RpDesc"));//
			String sign_type = FormatHelper.GBK2Chinese(params.getString("Sign_Type"));//
			String mac = params.getString("Mac");
			BaseResult result = new BaseResult();
			//验签
			Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
			paramsMap.put("partner_id", partner_id);
			paramsMap.put("version_no", version_no);
			paramsMap.put("biz_type", biz_type);
			paramsMap.put("sign_type", sign_type);
			paramsMap.put("MerBillNo", MerBillNo);
			paramsMap.put("RespCode", RespCode);
			paramsMap.put("RespDesc", RespDesc);
			paramsMap.put("TransAmt", TransAmt);
			paramsMap.put("TransId", TransId);
			paramsMap.put("MerFeeAmt", MerFeeAmt);
			paramsMap.put("FeeAmt", FeeAmt);
			paramsMap.put("MerPriv", MerPriv);
		   
		   
	        try {
	        	result = dealRecord.modifyDealRecordStateWithDouble(notifyMap);
		        System.out.println("修改资金交易记录："+result.isSuccess()+result.getMessage());
		        
		        PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo((String)notifyMap.get("MerBillNo"));
		        CashRecordDO cashrecord = cashrecorddo.getData(); 
		        cashrecord.setCrResponse(notifyMap.toString());
		        cashrecord.setCrResponseState(Integer.valueOf(RespCode));
		        BaseResult cashresult = cashrecordservice.modifyCashRecordState(cashrecord);
		        System.out.println("修改资金交易记录："+cashresult.isSuccess()+cashresult.getMessage());
		        
		        RechargeRecordDO rechargeDo = new RechargeRecordDO();
		        rechargeDo.setRechargeSeqNo(MerBillNo);
		        rechargeDo.setRechargeOutSeqNo(TransId);
		        if(RespCode.equals("000000")){
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
