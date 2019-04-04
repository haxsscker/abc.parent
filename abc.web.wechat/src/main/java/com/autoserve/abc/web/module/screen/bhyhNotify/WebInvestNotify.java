package com.autoserve.abc.web.module.screen.bhyhNotify;

import java.math.BigDecimal;
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
import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class WebInvestNotify {
	private final static Logger logger = LoggerFactory
			.getLogger(OpenAccountNotify.class);
	@Resource
	private AccountInfoService accountInfoService;
	@Resource
	private HttpServletResponse resp;
	@Resource
	private HttpServletRequest resq;
	@Resource
	private DoubleDryService doubleDtyService;
	@Resource
    private UserService  userService;
	@Resource
	private InvestOrderService investOrderService;
	@Resource
	private CashRecordService cashRecordService;
	@Resource
	private DealRecordService dealRecordService;
	
	public void execute(Context context, Navigator nav, ParameterParser params) {
		logger.info("===================投资异步通知===================");
		Map returnMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
		logger.info(returnMap.toString());
		String partner_id = FormatHelper.GBK2Chinese(params.getString("partner_id"));// 
		String version_no = FormatHelper.GBK2Chinese(params.getString("version_no"));//
		String biz_type = FormatHelper.GBK2Chinese(params.getString("biz_type"));// 
		String sign_type = FormatHelper.GBK2Chinese(params.getString("sign_type"));// 
		String MerBillNo = FormatHelper.GBK2Chinese(params.getString("MerBillNo"));// 
		String RespCode = FormatHelper.GBK2Chinese(params.getString("RespCode"));// 
		String RespDesc = FormatHelper.GBK2Chinese(params.getString("RespDesc"));// 
		String TransAmt = FormatHelper.GBK2Chinese(params.getString("TransAmt"));
		String TransId = FormatHelper.GBK2Chinese(params.getString("TransId"));//账户存管平台流水号
		String FeeAmt = FormatHelper.GBK2Chinese(params.getString("FeeAmt"));//手续费
		String FreezeId = FormatHelper.GBK2Chinese(params.getString("FreezeId"));//冻结编号
		String mac = params.getString("mac");
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
		paramsMap.put("FeeAmt", FeeAmt);
		paramsMap.put("FreezeId", FreezeId);
		try {
			//boolean res=CryptHelper.verifySignature(paramsMap, mac, "GBK");
			//if(res){
				if("000000".equals(RespCode)){
						InvestOrderDO order = new InvestOrderDO();
						order.setIoInnerSeqNo(MerBillNo);
						order.setIoOutSeqNo(TransId);
						order.setIoFreezeId(FreezeId);
						BaseResult baseResult = investOrderService.updateBySeqNo(order);
						if (!baseResult.isSuccess()) {
							resp.getWriter().print("fail");
						}
					
					CashRecordDO cashRecord = new CashRecordDO();
					cashRecord.setCrSeqNo(MerBillNo.substring(0, MerBillNo.length() - 1));
					// 保存回调
					cashRecord.setCrResponse(RespCode);
					cashRecord.setCrResponseState(200);
					cashRecordService.modifyCashRecordState(cashRecord);
					BigDecimal mount = BigDecimal.ZERO;
					mount = mount.add(new BigDecimal(TransAmt+""));
					String status= "TRADE_FAILURE";
					BaseResult modifyResult = dealRecordService.modifyDealRecordState(
							MerBillNo.substring(0, MerBillNo.length() - 1), status, mount);
					if (!modifyResult.isSuccess()) {
						resp.getWriter().print("fail");
					} else {
						resp.getWriter().print("SUCCESS");
					}
					/*if (result.isSuccess()) {
						resp.getWriter().print("SUCCESS");
					} else {
						resp.getWriter().print("fail");
					}*/
				}else{
					logger.info("投资失败====="+RespDesc);
				}
			//}else{
			//	logger.info("验签失败");
			//}
		} catch (Exception e) {
			logger.error("[OpenAccount] error: ", e);
		}
	}


}
