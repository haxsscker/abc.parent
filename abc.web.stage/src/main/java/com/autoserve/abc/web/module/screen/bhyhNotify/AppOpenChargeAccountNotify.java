package com.autoserve.abc.web.module.screen.bhyhNotify;

import java.io.UnsupportedEncodingException;
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
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.AccountCategory;
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

public class AppOpenChargeAccountNotify {
	private final static Logger logger = LoggerFactory
			.getLogger(AppOpenChargeAccountNotify.class);
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
		logger.info("===================企业开户异步通知===================");
		Map returnMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
		logger.info(returnMap.toString());
		String partner_id = FormatHelper.GBKDecodeStr(params.getString("PartnerId"));// 
		String sign_type = FormatHelper.GBKDecodeStr(params.getString("Sign_Type"));// 
		String MerBillNo = FormatHelper.GBKDecodeStr(params.getString("MerBillNo"));//  
		String RespCode = FormatHelper.GBKDecodeStr(params.getString("RpCode"));// 
		String RespDesc = FormatHelper.GBKDecodeStr(params.getString("RpDesc"));// 
		String ChargeAccount = FormatHelper.GBKDecodeStr(params.getString("ChargeAccount"));
		String AccountName = FormatHelper.GBKDecodeStr(params.getString("AccountName"));
		String AccountBk = FormatHelper.GBKDecodeStr(params.getString("AccountBk"));
		String ChargeAmt = FormatHelper.GBKDecodeStr(params.getString("ChargeAmt"));
		String PlaCustId = FormatHelper.GBKDecodeStr(params.getString("PlaCustId"));
		String mac = params.getString("Mac");
		BaseResult result = new BaseResult();
		//验签
		Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
		paramsMap.put("partner_id", partner_id);
		paramsMap.put("sign_type", sign_type);
		paramsMap.put("MerBillNo", MerBillNo);
		paramsMap.put("RespCode", RespCode);
		paramsMap.put("RespDesc", RespDesc);
		paramsMap.put("ChargeAccount", ChargeAccount);
		paramsMap.put("AccountName", AccountName);
		paramsMap.put("AccountBk", AccountBk);
		paramsMap.put("ChargeAmt", ChargeAmt);
		paramsMap.put("PlaCustId", PlaCustId);
		try {
			//boolean res=CryptHelper.verifySignature(paramsMap, mac, "GBK");
			//if(res){
				if("000000".equals(RespCode)){
					//插入账户
					AccountInfoDO account = new AccountInfoDO();
					account.setAccountMark(MerBillNo);
					account= accountInfoService.queryByAccountMark(MerBillNo);
					Integer accountState = account.getAccountState();
					account.setAccountUserChargeAccount(ChargeAccount);
					account.setAccountUserChargeName(AccountName);
					account.setAccountUserAccountName(account.getAccountUpdateName());
					account.setAccountUserAccountBk(AccountBk);
					account.setAccountUserType(2);
					account.setAccountCategory(AccountCategory.LOANACCOUNT.type);
					account.setAccountNo(PlaCustId);
					account.setAccountState(1);
					result = accountInfoService.updateByUserId(account);
					
					User user= userService.findEntityById(account.getAccountUserId()).getData();
					int userType = user.getUserType().getType();
					//将用户状态改为已开户
					userService.modifyUserBusinessState(account.getAccountUserId(), user.getUserType(), UserBusinessState.ACCOUNT_OPENED);
					
					/**if(accountState == 0){
						//开户送积分
		                userService.modifyUserScore(user.getUserId(), ScoreType.REALNAME_SCORE, null);
						
					}**/
					
	                if (result.isSuccess()) {
						resp.getWriter().print("SUCCESS");
					} else {
						resp.getWriter().print("fail");
					}
				}else{
					logger.info("开户失败====="+RespDesc);
				}
			//}else{
			//	logger.info("验签失败");
			//}
		} catch (Exception e) {
			logger.error("[OpenAccount] error: ", e);
		}
	}
}
