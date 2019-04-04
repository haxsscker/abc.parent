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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.CryptHelper;
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

public class AppOpenAccountNotify {
	private final static Logger logger = LoggerFactory
			.getLogger(AppOpenAccountNotify.class);
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
		logger.info("===================app开户异步通知===================");
		JSONObject rspJsonObject = null;
		try {
			resq.setCharacterEncoding("UTF-8");
			String mac = resq.getParameter("Mac");
			String rspData = CryptHelper.AES_Decrypt(mac);
			rspJsonObject = JSONObject.parseObject(rspData);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		/*String MerBillNo = FormatHelper.GBKDecodeStr(null!=rspJsonObject?rspJsonObject.getString("MerBillNo"):params.getString("MerBillNo"));// 
		String PlaCustId = FormatHelper.GBKDecodeStr(null!=rspJsonObject?rspJsonObject.getString("PlaCustId"):params.getString("PlaCustId"));// 
		String RespCode = FormatHelper.GBKDecodeStr(null!=rspJsonObject?rspJsonObject.getString("RpCode"):params.getString("RpCode"));// 
		String RespDesc = FormatHelper.GBKDecodeStr(null!=rspJsonObject?rspJsonObject.getString("RpDesc"):params.getString("RpDesc"));// 
		String OpenBankId = FormatHelper.GBKDecodeStr(null!=rspJsonObject?rspJsonObject.getString("OpenBankId"):params.getString("OpenBankId"));
		String OpenAcctId = FormatHelper.GBKDecodeStr(null!=rspJsonObject?rspJsonObject.getString("OpenAcctId"):params.getString("OpenAcctId"));
		String IdentNo = FormatHelper.GBKDecodeStr(null!=rspJsonObject?rspJsonObject.getString("IdentNo"):params.getString("IdentNo"));
		String UsrName = FormatHelper.GBKDecodeStr(null!=rspJsonObject?rspJsonObject.getString("UsrName"):params.getString("UsrName"));
		String MobileNo = FormatHelper.GBKDecodeStr(null!=rspJsonObject?rspJsonObject.getString("MobileNo"):params.getString("MobileNo"));
		String MerPriv = FormatHelper.GBKDecodeStr(null!=rspJsonObject?rspJsonObject.getString("MerPriv"):params.getString("MerPriv"));*/
		String MerBillNo = FormatHelper.GBKDecodeStr(params.getString("MerBillNo"));// 
		String PlaCustId = FormatHelper.GBKDecodeStr(params.getString("PlaCustId"));// 
		String RespCode = FormatHelper.GBKDecodeStr(params.getString("RpCode"));// 
		String RespDesc = FormatHelper.GBKDecodeStr(params.getString("RpDesc"));// 
		String OpenBankId = FormatHelper.GBKDecodeStr(params.getString("OpenBankId"));
		String OpenAcctId = FormatHelper.GBKDecodeStr(params.getString("OpenAcctId"));
		String IdentNo = FormatHelper.GBKDecodeStr(params.getString("IdentNo"));
		String UsrName = FormatHelper.GBKDecodeStr(params.getString("UsrName"));
		String MobileNo = FormatHelper.GBKDecodeStr(params.getString("MobileNo"));
		String TransTyp = FormatHelper.GBKDecodeStr(params.getString("TransTyp"));
		String MerPriv = FormatHelper.GBKDecodeStr(params.getString("MerPriv"));
		BaseResult result = new BaseResult();
		try {
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
		} catch (Exception e) {
			logger.error("[OpenAccount] error: ", e);
		}
	}
}
