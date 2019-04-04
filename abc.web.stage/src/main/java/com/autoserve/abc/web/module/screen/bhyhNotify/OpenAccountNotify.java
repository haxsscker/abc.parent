package com.autoserve.abc.web.module.screen.bhyhNotify;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.ScoreDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryWithValueDO;
import com.autoserve.abc.service.biz.convert.UserConverter;
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
import com.autoserve.abc.service.biz.intf.score.ScoreHistoryService;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
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
	@Resource
	private ScoreService scoreService;
	@Resource
	private ScoreHistoryService scoreHistoryService;
	
	public void execute(Context context, Navigator nav, ParameterParser params) {
		logger.info("===================开户异步通知===================");
		Map returnMap = EasyPayUtils.transformRequestMap(resq.getParameterMap());
		logger.info(returnMap.toString());
//		String partner_id = FormatHelper.GBKDecodeStr(params.getString("partner_id"));// 
//		String version_no = FormatHelper.GBKDecodeStr(params.getString("version_no"));//
//		String biz_type = FormatHelper.GBKDecodeStr(params.getString("biz_type"));// 
//		String sign_type = FormatHelper.GBKDecodeStr(params.getString("sign_type"));// 
		String MerBillNo = FormatHelper.GBKDecodeStr(params.getString("MerBillNo"));// 
		String PlaCustId = FormatHelper.GBKDecodeStr(params.getString("PlaCustId"));// 
		String RespCode = FormatHelper.GBKDecodeStr(params.getString("RespCode"));// 
		String RespDesc = FormatHelper.GBKDecodeStr(params.getString("RespDesc"));// 
		String OpenBankId = FormatHelper.GBKDecodeStr(params.getString("OpenBankId"));
		String OpenAcctId = FormatHelper.GBKDecodeStr(params.getString("OpenAcctId"));
//		String IdentType = FormatHelper.GBKDecodeStr(params.getString("IdentType"));
		String IdentNo = FormatHelper.GBKDecodeStr(params.getString("IdentNo"));
		String UsrName = FormatHelper.GBKDecodeStr(params.getString("UsrName"));
		String MobileNo = FormatHelper.GBKDecodeStr(params.getString("MobileNo"));
		String TransTyp = FormatHelper.GBKDecodeStr(params.getString("TransTyp"));
//		String FEE_AMT = FormatHelper.GBKDecodeStr(params.getString("FEE_AMT"));
		String MerPriv = FormatHelper.GBKDecodeStr(params.getString("MerPriv"));
//		String mac = params.getString("mac");
		BaseResult result = new BaseResult();
		//验签
//		Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
//		paramsMap.put("partner_id", partner_id);
//		paramsMap.put("version_no", version_no);
//		paramsMap.put("biz_type", biz_type);
//		paramsMap.put("sign_type", sign_type);
//		paramsMap.put("MerBillNo", MerBillNo);
//		paramsMap.put("PlaCustId", PlaCustId);
//		paramsMap.put("RespCode", RespCode);
//		paramsMap.put("RespDesc", RespDesc);
//		paramsMap.put("OpenBankId", OpenBankId);
//		paramsMap.put("OpenAcctId", OpenAcctId);
//		paramsMap.put("IdentType", IdentType);
//		paramsMap.put("IdentNo", IdentNo);
//		paramsMap.put("UsrName", UsrName);
//		paramsMap.put("MobileNo", MobileNo);
//		paramsMap.put("FEE_AMT", FEE_AMT);
//		paramsMap.put("MerPriv", MerPriv);
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
					account.setAccountKind(AccountInfoDO.KIND_BOHAI);
					account.setAccountCategory(Integer.valueOf(TransTyp));
					result = accountInfoService.openAccount(account);
					//插入银行卡
					
					User user= userService.findEntityById(Integer.valueOf(MerPriv)).getData();
					String realName = user.getUserRealName();
					
					int userType = user.getUserType().getType();
					//将用户状态改为已开户
					userService.modifyUserBusinessState(Integer.valueOf(MerPriv), user.getUserType(), UserBusinessState.ACCOUNT_OPENED);
					//回填实名认证信息
					user.setUserDocType("身份证");
					user.setUserRealName(UsrName);
					user.setUserDocNo(IdentNo);
					user.setUserRealnameIsproven(1);
					userService.modifyInfo(UserConverter.toUserDO(user));

	                //只有投资开户送积分
					if("1".equals(TransTyp)){
						PlainResult<ScoreDO> scoreResult = scoreService
								.findByScoreCode(ScoreType.REALNAME_SCORE.getCode());
						if (!scoreResult.isSuccess()) {
							throw new BusinessException("未找到指定积分类型！");
						}
						ScoreDO scoreDO = scoreResult.getData();
						ScoreHistoryDO scoreHistoryDO = new ScoreHistoryDO();
						scoreHistoryDO.setShUserId(Integer.valueOf(MerPriv));
						scoreHistoryDO.setShScoreId(scoreDO.getScoreId());
						PageCondition pageCondition = new PageCondition(1,10);
						PageResult<ScoreHistoryWithValueDO> scoreRs = scoreHistoryService.queryScoreHistoryList(scoreHistoryDO,pageCondition);
						if(scoreRs.getTotalCount()==0){
							userService.modifyUserScore(user.getUserId(), ScoreType.REALNAME_SCORE, null);
						}
					}
	                
					BankInfo bankInfo = new BankInfo();
					String bankName = bankMappingService.findBankMappingByCode(OpenBankId).getBankName();
					bankInfo.setBankUserId(Integer.valueOf(MerPriv));
					bankInfo.setBankCode(OpenBankId);
					bankInfo.setBankLawer(realName);
					bankInfo.setBankNo(OpenAcctId);
					bankInfo.setBankName(bankName);
					bankInfo.setBankUserType(userType);
					bankInfo.setCardType(CardType.DEBIT_CARD);
					bankInfo.setBankAccountType(Integer.valueOf(TransTyp));
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
