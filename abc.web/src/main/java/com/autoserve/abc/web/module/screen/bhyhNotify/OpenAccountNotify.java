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
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.CardType;
import com.autoserve.abc.service.biz.enums.UserBusinessState;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankMappingService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class OpenAccountNotify {
	private final static Logger logger = LoggerFactory
			.getLogger(OpenAccountNotify.class);
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
		logger.info("===================开户异步通知===================");
		Map returnMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
		logger.info(returnMap.toString());
		String partner_id = FormatHelper.GBK2Chinese(params.getString("partner_id"));// 
		String version_no = FormatHelper.GBK2Chinese(params.getString("version_no"));//
		String biz_type = FormatHelper.GBK2Chinese(params.getString("biz_type"));// 
		String sign_type = FormatHelper.GBK2Chinese(params.getString("sign_type"));// 
		String MerBillNo = FormatHelper.GBK2Chinese(params.getString("MerBillNo"));// 
		String PlaCustId = FormatHelper.GBK2Chinese(params.getString("PlaCustId"));// 
		String RespCode = FormatHelper.GBK2Chinese(params.getString("RespCode"));// 
		String RespDesc = FormatHelper.GBK2Chinese(params.getString("RespDesc"));// 
		String OpenBankId = FormatHelper.GBK2Chinese(params.getString("OpenBankId"));
		String OpenAcctId = FormatHelper.GBK2Chinese(params.getString("OpenAcctId"));
		String IdentType = FormatHelper.GBK2Chinese(params.getString("IdentType"));
		String IdentNo = FormatHelper.GBK2Chinese(params.getString("IdentNo"));
		String UsrName = FormatHelper.GBK2Chinese(params.getString("UsrName"));
		String MobileNo = FormatHelper.GBK2Chinese(params.getString("MobileNo"));
		String FEE_AMT = FormatHelper.GBK2Chinese(params.getString("FEE_AMT"));
		String MerPriv = FormatHelper.GBK2Chinese(params.getString("MerPriv"));
		String mac = params.getString("mac");
		BaseResult result = new BaseResult();
		//验签
		Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
		paramsMap.put("partner_id", partner_id);
		paramsMap.put("version_no", version_no);
		paramsMap.put("biz_type", biz_type);
		paramsMap.put("sign_type", sign_type);
		paramsMap.put("MerBillNo", MerBillNo);
		paramsMap.put("PlaCustId", PlaCustId);
		paramsMap.put("RespCode", RespCode);
		paramsMap.put("RespDesc", RespDesc);
		paramsMap.put("OpenBankId", OpenBankId);
		paramsMap.put("OpenAcctId", OpenAcctId);
		paramsMap.put("IdentType", IdentType);
		paramsMap.put("IdentNo", IdentNo);
		paramsMap.put("UsrName", UsrName);
		paramsMap.put("MobileNo", MobileNo);
		paramsMap.put("FEE_AMT", FEE_AMT);
		paramsMap.put("MerPriv", MerPriv);
		try {
			//boolean res=CryptHelper.verifySignature(paramsMap, mac, "GBK");
			//if(res){
				if("000000".equals(RespCode)){
					//插入账户
					AccountInfoDO account = new AccountInfoDO();
					account.setAccountUserId(Integer.valueOf(MerPriv));
					account.setAccountUserName(UsrName);
					account.setAccountLegalName("");
					account.setAccountNo(PlaCustId);
					account.setAccountUserCard(IdentNo);
					account.setAccountBankName(OpenBankId);
					account.setAccountUserAccount(OpenAcctId);
					account.setAccountUserEmail("");
					account.setAccountUserPhone(MobileNo);
					account.setAccountMark(MerBillNo);
					account.setAccountUserType(1);
					result = accountInfoService.openAccount(account);
					//插入银行卡
					
					User user= userService.findEntityById(Integer.valueOf(MerPriv)).getData();
					String realName = user.getUserRealName();
					int userType = user.getUserType().getType();
					//将用户状态改为已开户
					userService.modifyUserBusinessState(Integer.valueOf(MerPriv), user.getUserType(), UserBusinessState.ACCOUNT_OPENED);
					
					BankInfo bankInfo = new BankInfo();
					String bankName = bankMappingService.findBankMappingByCode(OpenBankId).getBankName();
					bankInfo.setBankUserId(Integer.valueOf(MerPriv));
					bankInfo.setBankCode(OpenBankId);
					bankInfo.setBankLawer(realName);
					bankInfo.setBankNo(OpenAcctId);
					bankInfo.setBankName(bankName);
					bankInfo.setBankUserType(userType);
					bankInfo.setCardType(CardType.DEBIT_CARD);
					bankInfoService.createBankInfo(bankInfo);
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
