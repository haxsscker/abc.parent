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
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankMappingService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.util.EasyPayUtils;

public class ModifyPwdNotify {
	
	private final static Logger logger = LoggerFactory
			.getLogger(ModifyPwdNotify.class);
	@Resource
	private AccountInfoService accountInfoService;
	@Resource
	private BankInfoService bankInfoService;
	@Resource
	private HttpServletResponse resp;
	@Resource
	private HttpServletRequest resq;
	@Resource
	private DoubleDryService doubleDtyService;
	@Resource
    private UserService  userService;
	@Resource
	private BankMappingService bankMappingService;
	
	public void execute(Context context, Navigator nav, ParameterParser params) {
		logger.info("===================更换存管密码异步通知===================");
		Map returnMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
		logger.info(returnMap.toString());
		String char_set = FormatHelper.GBK2Chinese(params.getString("Char_Set"));//
		String partner_id = FormatHelper.GBK2Chinese(params.getString("PartnerId"));// 
		String version_no = FormatHelper.GBK2Chinese(params.getString("Version_No"));//
		String biz_type = FormatHelper.GBK2Chinese(params.getString("Biz_Type"));// 
		String sign_type = FormatHelper.GBK2Chinese(params.getString("Sign_Type"));// 
		String MerBillNo = FormatHelper.GBK2Chinese(params.getString("MerBillNo"));// 
		String RespCode = FormatHelper.GBK2Chinese(params.getString("RpCode"));// 
		String RespDesc = FormatHelper.GBK2Chinese(params.getString("RpDesc"));// 
		String PlaCustId = FormatHelper.GBK2Chinese(params.getString("PlaCustId"));// 
		String mac = params.getString("Mac");
		//验签
		Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
		paramsMap.put("char_set", char_set);
		paramsMap.put("partner_id", partner_id);
		paramsMap.put("version_no", version_no);
		paramsMap.put("biz_type", biz_type);
		paramsMap.put("sign_type", sign_type);
		paramsMap.put("MerBillNo", MerBillNo);
		paramsMap.put("RespCode", RespCode);
		paramsMap.put("RespDesc", RespDesc);
		paramsMap.put("PlaCustId", PlaCustId);
		paramsMap.put("mac", mac);
	}

}
