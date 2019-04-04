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
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankMappingService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class AppChangePhoneNotify {
	
	private final static Logger logger = LoggerFactory
			.getLogger(AppChangePhoneNotify.class);
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
		logger.info("===================更换手机号异步通知===================");
		Map returnMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
		logger.info(returnMap.toString());
		String RespCode = FormatHelper.GBKDecodeStr(params.getString("RpCode"));// 
		String RespDesc = FormatHelper.GBKDecodeStr(params.getString("RpDesc"));// 
		String UsrName = FormatHelper.GBKDecodeStr(params.getString("UsrName"));
		String MobileNo = FormatHelper.GBKDecodeStr(params.getString("MobileNo"));
		String PlaCustId = FormatHelper.GBKDecodeStr(params.getString("PlaCustId"));
		//验签
		Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
		paramsMap.put("PlaCustId", PlaCustId);
		paramsMap.put("RespCode", RespCode);
		paramsMap.put("RespDesc", RespDesc);
		paramsMap.put("UsrName", UsrName);
		paramsMap.put("MobileNo", MobileNo);
		try {
			if("000000".equals(RespCode)){
				AccountInfoDO accountInfo = accountInfoService.queryByAccountNo(PlaCustId);
				UserDO userDO = new UserDO();
				userDO.setUserPhone(MobileNo); // 修改绑定的手机
				userDO.setUserNation(UsrName);
				//userDO.setUserId(Integer.parseInt(MerPriv));
				userDO.setUserId(accountInfo.getAccountUserId());
				BaseResult resu = this.userService.modifyUserSelective(userDO);
				if (resu.isSuccess()) {
					resp.getWriter().print("SUCCESS");
				} else {
					resp.getWriter().print("fail");
				}
			}else{
				resp.getWriter().print("fail");
				logger.info("更换手机号失败====="+RespDesc);
			}
		} catch (Exception e) {
			logger.error("[OpenAccount] error: ", e);
		}
	}

}
