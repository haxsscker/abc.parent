package com.autoserve.abc.web.module.screen.bhyhNotify;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.CardType;
import com.autoserve.abc.service.biz.enums.ScoreType;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankMappingService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class AppCloseAccountNotify {
	private final static Logger logger = LoggerFactory
			.getLogger(AppCloseAccountNotify.class);
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
		logger.info("===================销户异步通知===================");
		Map returnMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
		logger.info(returnMap.toString());
		String partner_id = FormatHelper.GBKDecodeStr(params.getString("PartnerId"));// 
		String version_no = FormatHelper.GBKDecodeStr(params.getString("Version_No"));//
		String biz_type = FormatHelper.GBKDecodeStr(params.getString("Biz_Type"));// 
		String MerBillNo = FormatHelper.GBKDecodeStr(params.getString("MerBillNo"));//  
		String RespCode = FormatHelper.GBKDecodeStr(params.getString("RpCode"));// 
		String RespDesc = FormatHelper.GBKDecodeStr(params.getString("RpDesc"));// 
		String PlaCustId = FormatHelper.GBKDecodeStr(params.getString("PlaCustId"));
		Integer TransTyp = params.getInt("TransTyp");
		String mac = params.getString("Mac");
		BaseResult result = new BaseResult();
		//验签
		Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
		paramsMap.put("partner_id", partner_id);
		paramsMap.put("version_no", version_no);
		paramsMap.put("biz_type", biz_type);
		paramsMap.put("MerBillNo", MerBillNo);
		paramsMap.put("RespCode", RespCode);
		paramsMap.put("RespDesc", RespDesc);
		paramsMap.put("PlaCustId", PlaCustId);
		try {
			//boolean res=CryptHelper.verifySignature(paramsMap, mac, "GBK");
			//if(res){
				if("000000".equals(RespCode)){
					AccountInfoDO account = accountInfoService.queryByAccountNoAndType(PlaCustId,TransTyp);
					//删除账户
					result = accountInfoService.deleteAccountById(account.getAccountId());
					User user= userService.findEntityById(account.getAccountUserId()).getData();
					//将用户状态改为已注册
					userService.modifyUserBusinessState(account.getAccountUserId(), user.getUserType(), UserBusinessState.REGISTERED);
					//清除授权信息
					UserDO userDO = new UserDO();
					userDO.setUserId(account.getAccountUserId());
					userService.cleanAuthorizeByUserId(userDO,String.valueOf(TransTyp));
					if (result.isSuccess()) {
						resp.getWriter().print("SUCCESS");
					} else {
						resp.getWriter().print("fail");
					}
				}else{
					logger.info("销户失败====="+RespDesc);
				}
			//}else{
			//	logger.info("验签失败");
			//}
		} catch (Exception e) {
			logger.error("[OpenAccount] error: ", e);
		}
	}
}
