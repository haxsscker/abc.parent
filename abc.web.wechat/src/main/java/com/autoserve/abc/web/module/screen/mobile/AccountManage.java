package com.autoserve.abc.web.module.screen.mobile;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.CardCityBaseDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.CardType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CardCityBaseService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.module.screen.webnotify.WithdrawReturn;
import com.autoserve.abc.web.util.SafeUtil;

/**
 * 帐号管理
 * 
 * @author Bo.Zhang
 * 
 */
public class AccountManage {

	@Resource
	private UserService userService;

	@Resource
	private AccountInfoService accountInfoService;

	@Resource
	private BankInfoService bankInfoService;
	
	@Resource
    private DoubleDryService doubleDryService;
	
	@Resource
    private CardCityBaseService cardCityBaseService;
	
	@Resource
    private RechargeService rechargeService;
	
	@Resource
	private ToCashService toCashService;
	
	@Resource
    private DealRecordService dealRecordService;

	public JsonMobileVO execute(Context context, ParameterParser params)
			throws IOException {
		JsonMobileVO result = new JsonMobileVO();

		try {
			String catalog = params.getString("catalog");
			Integer userId = params.getInt("userId");

			if (userId == null || "".equals(userId)) {
				result.setResultCode("201");
				result.setResultMessage("请求用户id不能为空");
				return result;
			}

			if ("1".equals(catalog)) {
				// 我的账户
				UserDO userDO = userService.findById(userId).getData();

				if (userDO.getUserRealnameIsproven() == null || userDO.getUserRealnameIsproven() == 0) {
					result.setResultCode("200");
					result.setResultMessage("请先实名认证");
					result.setResult("shimingrenzheng");
					return result;
				}
				if (userDO.getUserBusinessState() == null ||userDO.getUserBusinessState() < 2) {
					result.setResultCode("200");
					result.setResultMessage("您还没有开户，点击开户");
					Map<String, String> param = new HashMap<String, String>();
					param.put("RegisterType", "2");
					if (userDO.getUserType() == null || "".equals(userDO.getUserType()) || userDO.getUserType() == UserType.PERSONAL.getType()) {
						param.put("AccountType", "");
					} else {
						param.put("AccountType", "1");
					}
					param.put("Mobile", userDO.getUserPhone());
			    	param.put("Email", userDO.getUserEmail());
			    	param.put("RealName", userDO.getUserRealName());
			    	param.put("IdentificationNo", userDO.getUserDocNo());
			    	param.put("LoanPlatformAccount", userDO.getUserName());
			    	param.put("Remark1", userDO.getUserId().toString());
					Map paramMap = doubleDryService.openAccent(param).getData();
					String postData = "RegisterType="
							+ paramMap.get("RegisterType")
							+ "&AccountType="
							+ paramMap.get("AccountType")
							+ "&Mobile="
							+ paramMap.get("Mobile")
							+ "&Email="
							+ paramMap.get("Email")
							+ "&RealName="
							+ paramMap.get("RealName")
							+ "&IdentificationNo="
							+ paramMap.get("IdentificationNo")
							+ "&Image1="
							+ MobileHelper.nullToEmpty(paramMap.get("Image1"))
							+ "&Image2="
							+ MobileHelper.nullToEmpty(paramMap.get("Image2"))
							+ "&LoanPlatformAccount="
							+ paramMap.get("LoanPlatformAccount")
							+ "&PlatformMoneymoremore="
							+ paramMap.get("PlatformMoneymoremore")
							+ "&RandomTimeStamp="
							+ MobileHelper.nullToEmpty(paramMap.get("RandomTimeStamp"))
							+ "&Remark1="
							+ MobileHelper.nullToEmpty(paramMap.get("Remark1"))
							+ "&Remark2="
							+ MobileHelper.nullToEmpty(paramMap.get("Remark2"))
							+ "&Remark3="
							+ MobileHelper.nullToEmpty(paramMap.get("Remark3"))
							+ "&ReturnURL="
							+ paramMap.get("ReturnURL")
							+ "&NotifyURL="
							+ paramMap.get("NotifyURL")
							+ "&SignInfo="
							+ URLEncoder.encode(paramMap.get("SignInfo").toString(), "UTF-8");
					Map<String, Object> objMap = new HashMap<String, Object>();
					objMap.put("submitUrl", paramMap.get("SubmitURL"));
					objMap.put("postData", postData);
					result.setResult(JSON.toJSON(objMap));
					return result;
				}
				if(userDO.getUserAuthorizeFlag() == null || userDO.getUserAuthorizeFlag() == 0){
					result.setResultCode("200");
					result.setResultMessage("您还没有授权，点击授权");
					UserIdentity userIdentity =new UserIdentity();
			    	userIdentity.setUserId(userDO.getUserId());
			    	if(userDO.getUserType() == null || userDO.getUserType() == 1){
			    		userDO.setUserType(UserType.PERSONAL.getType());
			    	}else{
			    		userDO.setUserType(UserType.ENTERPRISE.getType());
			    	}
			    	userIdentity.setUserType(UserType.valueOf(userDO.getUserType()));	
			    	PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
			    	
			    	Map<String, String> param = new HashMap<String, String>();
			    	param.put("MoneymoremoreId", account.getData().getAccountMark());
			    	param.put("Remark1", account.getData().getAccountUserId().toString());
			    	param.put("AuthorizeTypeOpen","1,2,3");
			    	Map<String, String> paramMap = doubleDryService.authorize(param);
			    	String postData = "MoneymoremoreId="
							+ paramMap.get("MoneymoremoreId")
							+ "&AuthorizeTypeOpen="
							+ paramMap.get("AuthorizeTypeOpen")
							+ "&PlatformMoneymoremore="
							+ paramMap.get("PlatformMoneymoremore")
							+ "&Remark1="
							+ MobileHelper.nullToEmpty(paramMap.get("Remark1"))
							+ "&ReturnURL="
							+ paramMap.get("ReturnURL")
							+ "&NotifyURL="
							+ paramMap.get("NotifyURL")
							+ "&SignInfo="
							+ URLEncoder.encode(paramMap.get("SignInfo").toString(), "UTF-8");
			    	Map<String, Object> objMap = new HashMap<String, Object>();
					objMap.put("submitUrl", paramMap.get("SubmitURL"));
					objMap.put("postData", postData);
					result.setResult(JSON.toJSON(objMap));
					return result;
				}
				
				//帐号金额信息
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(userDO.getUserId());
				if (userDO.getUserType() == null || userDO.getUserType() == 1) {
					userDO.setUserType(UserType.PERSONAL.getType());
				} else {
					userDO.setUserType(UserType.ENTERPRISE.getType());
				}
				userIdentity.setUserType(UserType.valueOf(userDO.getUserType()));
				Account account = accountInfoService.queryByUserId(userIdentity).getData();
				String accountMark = account.getAccountMark();

				// 网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
				Double[] accountBacance = doubleDryService.queryBalance(accountMark, "1");

				Map<String, Object> objMap = new HashMap<String, Object>();
				objMap.put("allMoney", accountBacance[0]);
				objMap.put("avlMoney", accountBacance[1]);
				objMap.put("fraMoney", accountBacance[2]);
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("2".equals(catalog)) {
				// 基本信息
				String param = params.getString("param");
				UserDO userDO = JSON.parseObject(param, UserDO.class);
				//校验证件号码唯一性 xiatt 20160625 
		        if(!StringUtil.isBlank(userDO.getUserDocNo())){
		        	UserDO queryUser = new UserDO();
		        	queryUser.setUserDocNo(userDO.getUserDocNo());
		        	queryUser.setUserId(userId);
		            int docNoCount = userService.countDocNoByParam(queryUser);
		            if(docNoCount>0){
						result.setResultCode("201");
						result.setResultMessage("证件号码已存在");
						return result;
		            }
		        }
				
				userDO.setUserId(userId);
				userDO.setUserRealnameIsproven(1);
				BaseResult baseResult = userService.modifyUserSelective(userDO);
				if (baseResult.isSuccess()) {
					result.setResultCode("200");
					result.setResultMessage("保存信息成功");
				} else {
					result.setResultCode("201");
					result.setResultMessage(baseResult.getMessage());
				}
			} else if ("3".equals(catalog)) {
				// 银行卡列表
				ListResult<BankInfoDO> listResult = bankInfoService.queryListBankInfo(userId);
				List<BankInfoDO> list = listResult.getData();

				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				Map<String, Object> loanMap = new HashMap<String, Object>();

				for (BankInfoDO bankInfoDO : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("bankId", bankInfoDO.getBankId());
					loanMap.put("bankNumber", bankInfoDO.getBankNo());
					loanMap.put("bankName", bankInfoDO.getBankName());
					loanMap.put("BankCode", bankInfoDO.getBankCode());
					loanMap.put("BankIcon", "");
					loanList.add(loanMap);
				}

				objMap.put("pageCount", 1);
				objMap.put("list", JSON.toJSON(loanList));
				objMap.put("isAddCard", "1");

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("4".equals(catalog)) {
				// 添加银行卡
				String param = params.getString("param");
				String md5Flag = params.getString("md5Flag");
				
				if(param == null || "".equals(param)) {
					 List<CardCityBaseDO>  list = cardCityBaseService.queryAllCity().getData();
					 String md5 = Md5Encrypt.md5(JSON.toJSONString(list));
						if(!md5.equals(md5Flag)) {
							Map<String, Object> objMap = new HashMap<String, Object>();
							objMap.put("md5Flag", md5);
							objMap.put("cardCityList", JSON.toJSON(list));
							
							result.setResult(JSON.toJSON(objMap));
						}
					 result.setResultCode("200");
				} else {
					User user = userService.findEntityById(userId).getData();

					JSONObject jObject = JSON.parseObject(param);
					BankInfo bankInfo = new BankInfo();
					bankInfo.setBankUserId(user.getUserId());
					bankInfo.setBankLawer(user.getUserRealName());
					bankInfo.setBankUserType(user.getUserType().getType());
					if (jObject.getInteger("cardType") == 0) {
						bankInfo.setCardType(CardType.DEBIT_CARD);
					} else if (jObject.getInteger("cardType") == 1) {
						bankInfo.setCardType(CardType.CREDIT_CARD);
					}
					bankInfo.setCardStatus(CardStatus.STATE_ENABLE);
					bankInfo.setBankNo(jObject.getString("cardNo"));
					bankInfo.setBankName(jObject.getString("branchBankName"));
					bankInfo.setBankCode(jObject.getString("bankCode"));
					bankInfo.setAreaCode(jObject.getString("city"));
					BaseResult baseResult = bankInfoService.createBankInfo(bankInfo);
					if (baseResult.isSuccess()) {
						result.setResultCode("200");
						result.setResultMessage("添加银行卡成功");
					} else {
						result.setResultCode("201");
						result.setResultMessage(baseResult.getMessage());
					}
				}
			} else if ("5".equals(catalog)) {
				// 账户充值
				Double money = params.getDouble("money");
				String mey = params.getString("money");

//				if (!MobileHelper.check(userService, userId, result)) {
//					return result;
//				}

				User user = userService.findEntityById(userId).getData();
				UserIdentity userIdentity = new UserIdentity();
				if (user.getUserType() == null || "".equals(user.getUserType()) || user.getUserType() == UserType.PERSONAL) {
					user.setUserType(UserType.PERSONAL);
				} else {
					user.setUserType(UserType.ENTERPRISE);
				}
				userIdentity.setUserType(user.getUserType());
				userIdentity.setUserId(user.getUserId());
				Account account = accountInfoService.queryByUserId(userIdentity).getData();

				if (money != null && !"".equals(money) && account != null && !"".equals(account)) {
//					Deal deal = new Deal();
//					InnerSeqNo innerseqno = InnerSeqNo.getInstance();
//					deal.setInnerSeqNo(innerseqno);
//					deal.setBusinessType(DealType.RECHARGE);
//					deal.setOperator(user.getUserId());
//
//					List<DealDetail> list = new ArrayList<DealDetail>();
//					DealDetail dealdetail = new DealDetail();
					Map<String, String> param = new HashMap<String, String>();
					param.put("RechargeMoneymoremore", account.getAccountMark());
					param.put("PlatformMoneymoremore", "");
//					param.put("OrderNo", innerseqno.toString());
					param.put("Amount", mey);
					param.put("RechargeType", "2");
					param.put("FeeType", "1");
					param.put("CardNo", "");
					param.put("RandomTimeStamp", "");
					param.put("ReturnURL", "");
					param.put("NotifyURL", "");

//					dealdetail.setData(param);
//					BigDecimal bigdecimal = new BigDecimal(money);
//					dealdetail.setMoneyAmount(bigdecimal);
//					dealdetail.setDealDetailType(DealDetailType.RECHARGE_MONEY);
//					list.add(dealdetail);
//
//					deal.setDealDetail(list);

//					PlainResult<DealReturn> paramMap = dealRecordService.createBusinessRecord(deal, null);
					PlainResult<DealReturn> paramMap = rechargeService.recharge(user.getUserId(), user.getUserType(), new BigDecimal(money), param);
					JSONObject jo = JSON.parseObject(paramMap.getData().getCashRecords().get(0).getCrRequestParameter());
					String postData = "RechargeType="
							+ jo.get("RechargeType")
							+ "&RechargeMoneymoremore="
							+ jo.get("RechargeMoneymoremore")
							+ "&OrderNo="
							+ jo.get("OrderNo")
							+ "&Amount="
							+ jo.get("Amount")
							+ "&FeeType="
							+ jo.get("FeeType")
							+ "&CardNo="
							+ jo.get("CardNo")
							+ "&PlatformMoneymoremore="
							+ jo.get("PlatformMoneymoremore")
							+ "&RandomTimeStamp="
							+ MobileHelper.nullToEmpty(jo.get("RandomTimeStamp"))
							+ "&ReturnURL="
							+ jo.get("ReturnURL")
							+ "&NotifyURL="
							+ jo.get("NotifyURL")
							+ "&SignInfo="
							+ URLEncoder.encode(jo.get("SignInfo").toString(), "UTF-8");
					Map<String, Object> objMap = new HashMap<String, Object>();
					objMap.put("submitUrl", jo.get("submitUrl"));
					objMap.put("postData", postData);
					result.setResultCode("200");
					result.setResult(JSON.toJSON(objMap));
					return result;
				} else {
					result.setResultCode("201");
					result.setResultMessage("充值失败");
				}
			} else if ("6".equals(catalog)) {
				// 账户提现
				Double money = params.getDouble("money");
				String mey = params.getString("money");
				Integer bankId = params.getInt("bankId");

//				if (!MobileHelper.check(userService, userId, result)) {
//					return result;
//				}

				User user = userService.findEntityById(userId).getData();
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(user.getUserId());
				if (user.getUserType() == null || user.getUserType().getType() == 1) {
					user.setUserType(UserType.PERSONAL);
				} else {
					user.setUserType(UserType.ENTERPRISE);
				}
				userIdentity.setUserType(user.getUserType());
				Account account = accountInfoService.queryByUserId(userIdentity).getData();

				if (money != null && !"".equals(money) && account != null && !"".equals(account)) {
					// 生成订单号
					InnerSeqNo innerseqno = InnerSeqNo.getInstance();

					BankInfoDO bankInfoDO = bankInfoService.queryListBankInfoById(bankId).getData();

					Map<String, String> param = new HashMap<String, String>();
					param.put("WithdrawMoneymoremore", account.getAccountMark());
					param.put("PlatformMoneymoremore", "");
					param.put("OrderNo", innerseqno.toString());
					param.put("Amount", mey);
					param.put("FeePercent", "0");
					param.put("FeeMax", "");
					param.put("FeeRate", "");
					param.put("CardNo", bankInfoDO.getBankNo());
					param.put("CardType", bankInfoDO.getCardType() + "");
					param.put("BankCode", bankInfoDO.getBankCode());
					param.put("BranchBankName", bankInfoDO.getBankName());
					param.put("Province", cardCityBaseService.queryCardByCode(Integer.valueOf(bankInfoDO.getAreaCode())).getData().getProvCode().toString());
//					param.put("Province", "1");
					param.put("City", bankInfoDO.getAreaCode());
					param.put("Remark1", "");
					param.put("Remark2", "");
					param.put("Remark3", "");
					param.put("ReturnURL", "");
					param.put("NotifyURL", "");
					param.put("SignInfo", "");

					PlainResult<DealReturn> paramMap = toCashService.toCashOther(user.getUserId(), user.getUserType(), new BigDecimal(money), param);
					System.out.println("发送参数:======" + paramMap.getData().getCashRecords().get(0).getCrRequestParameter());

					JSONObject jo = JSON.parseObject(paramMap.getData().getCashRecords().get(0).getCrRequestParameter());
					String postData = "WithdrawMoneymoremore="
							+ jo.get("WithdrawMoneymoremore")
							+ "&OrderNo="
							+ jo.get("OrderNo")
							+ "&Amount="
							+ jo.get("Amount")
							+ "&FeePercent="
							+ jo.get("FeePercent")
							+ "&FeeMax="
							+ jo.get("FeeMax")
							+ "&FeeRate="
							+ jo.get("FeeRate")
							+ "&CardNo="
							+ URLEncoder.encode(jo.get("CardNo").toString(), "UTF-8")
							+ "&CardType="
							+ jo.get("CardType")
							+ "&BankCode="
							+ jo.get("BankCode")
							+ "&BranchBankName="
							+ jo.get("BranchBankName")
							+ "&Province="
							+ jo.get("Province")
							+ "&City="
							+ jo.get("City")
							+ "&Remark1="
							+ jo.get("Remark1")
							+ "&Remark2="
							+ jo.get("Remark2")
							+ "&Remark3="
							+ jo.get("Remark3")
							+ "&PlatformMoneymoremore="
							+ jo.get("PlatformMoneymoremore")
							+ "&RandomTimeStamp="
							+ MobileHelper.nullToEmpty(jo.get("RandomTimeStamp"))
							+ "&ReturnURL="
							+ jo.get("ReturnURL")
							+ "&NotifyURL="
							+ jo.get("NotifyURL")
							+ "&SignInfo="
							+ URLEncoder.encode(jo.get("SignInfo").toString(), "UTF-8");
					Map<String, Object> objMap = new HashMap<String, Object>();
					objMap.put("submitUrl", jo.get("submitUrl"));
					objMap.put("postData", postData);
					result.setResultCode("200");
					result.setResult(JSON.toJSON(objMap));
					return result;
				} else {
					 result.setResultCode("201");
					 result.setResultMessage("提现失败");
				}
			} 
			else if ("61".equals(catalog)) {
				// 账户提现
				Double money = params.getDouble("money");
				String mey = params.getString("money");
				Integer bankId = params.getInt("bankId");
				
				String CardNo = params.getString("cardNo");
				String CardType1 = params.getString("cardType");
				String BankCode = params.getString("bankCode");
				String BranchBankName = params.getString("branchBankName");
				String Province = params.getString("Province");
				String City = params.getString("city");
				
//				if (!MobileHelper.check(userService, userId, result)) {
//					return result;
//				}

				User user = userService.findEntityById(userId).getData();
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(user.getUserId());
				if (user.getUserType() == null || user.getUserType().getType() == 1) {
					user.setUserType(UserType.PERSONAL);
				} else {
					user.setUserType(UserType.ENTERPRISE);
				}
				userIdentity.setUserType(user.getUserType());
				Account account = accountInfoService.queryByUserId(userIdentity).getData();

				if (money != null && !"".equals(money) && account != null && !"".equals(account)) {
					// 生成订单号
					InnerSeqNo innerseqno = InnerSeqNo.getInstance();
					// 添加银行卡
					BankInfo bankInfo = new BankInfo();
					bankInfo.setBankUserId(user.getUserId());
					bankInfo.setBankLawer(user.getUserRealName());
					bankInfo.setBankUserType(user.getUserType().getType());
					if (CardType1.equals("0")) {
						bankInfo.setCardType(CardType.DEBIT_CARD);
					} else if (CardType1.equals("1")) {
						bankInfo.setCardType(CardType.CREDIT_CARD);
					}
					bankInfo.setCardStatus(CardStatus.STATE_DISABLE);
					bankInfo.setBankNo(CardNo);
					bankInfo.setBankName(BranchBankName);
					bankInfo.setBankCode(BankCode);
					bankInfo.setAreaCode(City);
					BaseResult baseResult = bankInfoService.createBankInfo(bankInfo);
					if (baseResult.isSuccess()) {
					BankInfoDO bankInfoDO1 = bankInfoService.queryBankInfo(userId.toString(), CardNo).getData();
					
					String BankId=bankInfoDO1.getBankId().toString();
						Map<String, String> param = new HashMap<String, String>();
						param.put("WithdrawMoneymoremore", account.getAccountMark());
						param.put("PlatformMoneymoremore", "");
						param.put("OrderNo", innerseqno.toString());
						param.put("Amount", mey);
						param.put("FeePercent", "0");
						param.put("FeeMax", "");
						param.put("FeeRate", "");
						param.put("CardNo", CardNo);
						param.put("CardType", CardType1);
						param.put("BankCode", BankCode);
						param.put("BranchBankName", BranchBankName);
//						param.put("Province", cardCityBaseService.queryCardByCode(Integer.valueOf(bankInfoDO.getAreaCode())).getData().getProvCode().toString());
						param.put("Province", Province);
						param.put("City", City);
						param.put("Remark1", BankId);
						param.put("Remark2", "");
						param.put("Remark3", "");
						param.put("ReturnURL", "");
						param.put("NotifyURL", "");
						param.put("SignInfo", "");

						PlainResult<DealReturn> paramMap = toCashService.toCashOther(user.getUserId(), user.getUserType(), new BigDecimal(money), param);
						System.out.println("发送参数:======" + paramMap.getData().getCashRecords().get(0).getCrRequestParameter());

						JSONObject jo = JSON.parseObject(paramMap.getData().getCashRecords().get(0).getCrRequestParameter());
						String postData = "WithdrawMoneymoremore="
								+ jo.get("WithdrawMoneymoremore")
								+ "&OrderNo="
								+ jo.get("OrderNo")
								+ "&Amount="
								+ jo.get("Amount")
								+ "&FeePercent="
								+ jo.get("FeePercent")
								+ "&FeeMax="
								+ jo.get("FeeMax")
								+ "&FeeRate="
								+ jo.get("FeeRate")
								+ "&CardNo="
								+ URLEncoder.encode(jo.get("CardNo").toString(), "UTF-8")
								+ "&CardType="
								+ jo.get("CardType")
								+ "&BankCode="
								+ jo.get("BankCode")
								+ "&BranchBankName="
								+ jo.get("BranchBankName")
								+ "&Province="
								+ jo.get("Province")
								+ "&City="
								+ jo.get("City")
								+ "&Remark1="
								+ jo.get("Remark1")
								+ "&Remark2="
								+ jo.get("Remark2")
								+ "&Remark3="
								+ jo.get("Remark3")
								+ "&PlatformMoneymoremore="
								+ jo.get("PlatformMoneymoremore")
								+ "&RandomTimeStamp="
								+ MobileHelper.nullToEmpty(jo.get("RandomTimeStamp"))
								+ "&ReturnURL="
								+ jo.get("ReturnURL")
								+ "&NotifyURL="
								+ jo.get("NotifyURL")
								+ "&SignInfo="
								+ URLEncoder.encode(jo.get("SignInfo").toString(), "UTF-8");
						Map<String, Object> objMap = new HashMap<String, Object>();
						objMap.put("submitUrl", jo.get("submitUrl"));
						objMap.put("postData", postData);
						result.setResultCode("200");
						result.setResult(JSON.toJSON(objMap));
//						
					} else {
						result.setResultCode("201");
						result.setResultMessage(baseResult.getMessage());
					}
			
						
						return result;
				} else {
					 result.setResultCode("201");
					 result.setResultMessage("提现失败");
				}
			}else if ("7".equals(catalog)) {
				// 交易记录
				Integer pageSize = params.getInt("pageSize");
				Integer showPage = params.getInt("showPage");
				
				UserDO userDO = userService.findById(userId).getData();
				UserIdentity userIdentity = new UserIdentity();
		    	if(userDO.getUserType()==null||"".equals(userDO.getUserType())||userDO.getUserType()==UserType.PERSONAL.getType()){
		    		userDO.setUserType(UserType.PERSONAL.getType());
		    	}else{
		    		userDO.setUserType(UserType.ENTERPRISE.getType());
		    	}
		    	userIdentity.setUserType(UserType.valueOf(userDO.getUserType()));
		    	userIdentity.setUserId(userDO.getUserId());
		    	PlainResult<Account> account =  accountInfoService.queryByUserId(userIdentity) ;
		    	
				DealRecordDO dealrecorddo = new DealRecordDO();
	    		dealrecorddo.setDrPayAccount(account.getData().getAccountNo());
	    		//String platformAcount = SystemGetPropeties.getStrString("MerCustId");
	    		PageResult<DealRecordDO> pageResult = dealRecordService.queryDealByParams(dealrecorddo, new PageCondition(showPage, pageSize), null, null);
	    		List<DealRecordDO> list = pageResult.getData();
	    		
	    		Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> loanMap = new HashMap<String, Object>();
				
	    		for (DealRecordDO dealRecordDO : list) {
	    			loanMap = new HashMap<String, Object>();
	    			loanMap.put("tradeType", DealType.valueOf(dealRecordDO.getDrType()).getDes()); //交易类型
	    			loanMap.put("traderStatus", DealState.valueOf(dealRecordDO.getDrState()).getDes()); //交易状态
	    			loanMap.put("tradeTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dealRecordDO.getDrOperateDate())); //交易时间
	    			loanMap.put("transMoney", dealRecordDO.getDrMoneyAmount()); //交易金额
	    			loanMap.put("transCharges", "0.00"); //交易手续费
	    			loanMap.put("transNum", dealRecordDO.getDrInnerSeqNo().substring(0, 15) + "..."); //交易流水号
//	    			loanMap.put("transNum", dealRecordDO.getDrInnerSeqNo()); //交易流水号
	    			loanMap.put("receiveAccount", dealRecordDO.getDrReceiveAccount()); //交易对方
	    			loanList.add(loanMap);
				}
	    		
	    		objMap.put("pageCount", pageResult.getTotalCount());
				objMap.put("list", JSON.toJSON(loanList));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("8".equals(catalog)) {
				// 修改密码
				String oldPwd = params.getString("oldPwd");
				String newPwd = params.getString("newPwd");

				PlainResult<UserDO> plainResult = userService.findById(userId);
				UserDO userDO = plainResult.getData();
				if (!userDO.getUserPwd().equals(CryptUtils.md5(oldPwd))) {
					result.setResultCode("201");
					result.setResultMessage("输入的旧密码不正确");
				} else {
					userDO.setUserPwd(CryptUtils.md5(newPwd));
					BaseResult baseResult = userService.modifyUserSelective(userDO);
					if (baseResult.isSuccess()) {
						result.setResultCode("200");
						result.setResultMessage("修改密码成功");
					} else {
						result.setResultCode("201");
						result.setResultMessage("修改密码失败");
					}
				}
			} else if ("9".equals(catalog)) {
				// 账户余额
				UserDO userDO = userService.findById(userId).getData();
				
//				if (!MobileHelper.check(userService, userId, result)) {
//					return result;
//				}

				Map<String, Object> objMap = new HashMap<String, Object>();
				
				// 获取用户绑定的银行卡信息
				List<BankInfoDO> list = bankInfoService.queryListBankInfo(userId).getData();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				Map<String, Object> loanMap = new HashMap<String, Object>();
				
//				if(list.size() == 0) {
//					result.setResultCode("200");
//					result.setResultMessage("您还没有绑卡，请先绑卡");
//					result.setResult("bangka");
//					return result;
//				}

				for (BankInfoDO bankInfoDO : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("bankId", bankInfoDO.getBankId());
					loanMap.put("bankNumber", bankInfoDO.getBankNo());
					loanMap.put("bankName", bankInfoDO.getBankName());
					loanMap.put("bankCode", bankInfoDO.getBankCode());
					loanMap.put("bankIcon", "");
					loanList.add(loanMap);
				}
				
//				objMap.put("userId", userDO.getUserId());
//				objMap.put("userName", userDO.getUserName());
//				objMap.put("userType", userDO.getUserType());
				if (userDO.getUserEmail() != null) {
					objMap.put("userEmail", SafeUtil.hideEmail(userDO.getUserEmail()));
				}
//				objMap.put("userPhone", SafeUtil.hideMobile(userDO.getUserPhone()));
				objMap.put("userRealName", SafeUtil.hideName(userDO.getUserRealName()));
//				objMap.put("phoneValidFlag", userDO.getUserMobileIsbinded());
//				objMap.put("emailValidFlag", userDO.getUserEmailIsbinded());
//				objMap.put("userDocType", userDO.getUserDocType());
//				if (userDO.getUserDocNo() != null) {
//					objMap.put("userDocNo", SafeUtil.hideIDNumber(userDO.getUserDocNo()));
//				}

				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(userDO.getUserId());
				if (userDO.getUserType() == null || userDO.getUserType() == 1) {
					userDO.setUserType(UserType.PERSONAL.getType());
				} else {
					userDO.setUserType(UserType.ENTERPRISE.getType());
				}
				userIdentity.setUserType(UserType.valueOf(userDO.getUserType()));
				Account account = accountInfoService.queryByUserId(userIdentity).getData();
				String accountMark = account.getAccountMark();

				// 网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
				Double[] accountBacance = doubleDryService.queryBalance(accountMark, "1");

				objMap.put("allMoney", accountBacance[0]);
				objMap.put("avlMoney", accountBacance[1]);
				objMap.put("fraMoney", accountBacance[2]);
				objMap.put("bankList", JSON.toJSON(loanList));
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if("10".equals(catalog)) {
				//账户信息
				UserDO userDO = userService.findById(userId).getData();
				
				Map<String, Object> objMap = new HashMap<String, Object>();
				objMap.put("userId", userDO.getUserId());
				objMap.put("userName", userDO.getUserName());
				objMap.put("userType", userDO.getUserType());
				if (userDO.getUserEmail() != null) {
					objMap.put("userEmail", SafeUtil.hideEmail(userDO.getUserEmail()));
				}
				objMap.put("userPhone", SafeUtil.hideMobile(userDO.getUserPhone()));
				if (userDO.getUserRealName() != null) {
					objMap.put("userRealName", SafeUtil.hideName(userDO.getUserRealName()));
				}
				objMap.put("phoneValidFlag", userDO.getUserMobileIsbinded());
				objMap.put("emailValidFlag", userDO.getUserEmailIsbinded());
				objMap.put("userDocType", userDO.getUserDocType());
				if (userDO.getUserDocNo() != null) {
					objMap.put("userDocNo", SafeUtil.hideIDNumber(userDO.getUserDocNo()));
				}
				
				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else {
				result.setResultCode("201");
				result.setResultMessage("请求参数异常");
			}
		} catch (Exception e) {
			result.setResultCode("201");
			result.setResultMessage("请求异常");
		}

		return result;
	}
}
