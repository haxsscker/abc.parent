package com.autoserve.abc.web.module.screen.mobileIos;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.CashRecordDO;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.DealRecordDO;
import com.autoserve.abc.dao.dataobject.RechargeRecordDO;
import com.autoserve.abc.dao.dataobject.TocashRecordDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.dataobject.search.RedSearchDO;
import com.autoserve.abc.dao.intf.CompanyCustomerDao;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.entity.RedsendJ;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.CardType;
import com.autoserve.abc.service.biz.enums.DealState;
import com.autoserve.abc.service.biz.enums.DealType;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.ToCashState;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.AuthorizeUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CardCityBaseService;
import com.autoserve.abc.service.biz.intf.cash.CashRecordService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.PayMentService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedsendService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 帐号管理
 * 
 * @author Bo.Zhang
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
	@Resource
	private BankInfoService bankinfoservice;
	@Resource
	private InvestService investservice;
	@Resource
	private RealnameAuthService realnameAuthService;
	@Resource
	private CompanyCustomerService companyCustomerService;
	@Autowired
	private DoubleDryService doubledryservice;
	@Resource
	private SysConfigService sysConfigService;
	@Autowired
	private PaymentPlanService paymentPlanService;
	@Resource
	private RedsendService redSendService;
	@Autowired
	private HttpSession session;
	@Resource
    private PayMentService  payMentService;
	@Resource
	private CashRecordService cashrecordservice;
	@Resource
	private ToCashService tocashservice;
	@Autowired
	private CompanyCustomerDao companyCustomerDao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
				// 账户金额
				UserDO userDO = userService.findById(userId).getData();
				if (userDO == null) {
					result.setResultCode("200");
					result.setResultMessage("不存在的用户");
					return result;
				}
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(userDO.getUserId());
				if (userDO.getUserType() == 1) {
					userIdentity.setUserType(UserType.PERSONAL);
				} else {
					userIdentity.setUserType(UserType.ENTERPRISE);
				}
				PlainResult<Account> account = accountInfoService
						.queryByUserId(userIdentity);
				String accountNo = account.getData().getAccountNo();
		    	Map<String, Object> resultMap = Maps.newHashMap();
		        if(account.isSuccess()&&userDO.getUserType()==2){
		        	String accountUserAccount =account.getData().getAccountUserAccount();
		        	String mark=account.getData().getAccountMark();
		        	Map<String, String> chargeAccountMap =this.doubleDryService.queryChargeAccountResult(accountUserAccount,mark);
		        	resultMap.put("RealNameFlg", null!=chargeAccountMap.get("RealNameFlg")?chargeAccountMap.get("RealNameFlg"):"");
			    	resultMap.put("ChargeAmt", null!=chargeAccountMap.get("ChargeAmt")?chargeAccountMap.get("ChargeAmt"):"");
		        }
				Double[] accountBalance = { 0.00, 0.00, 0.00 };
				if (accountNo != null && !"".equals(accountNo)) {
					resultMap.put("AccountUserChargeAccount", account.getData().getAccountUserChargeAccount());
					resultMap.put("AccountUserChargeName", account.getData().getAccountUserChargeName());
					resultMap.put("accountUserAccount", account.getData().getAccountUserAccount());
					resultMap.put("AccountName", account.getData().getAccountUserAccountName());
					resultMap.put("AccountBk", account.getData().getAccountUserAccountBk());
					accountBalance = this.doubleDryService.queryBalance(
							accountNo, "1");
				}
				
				result.setResult(resultMap);
				resultMap.put("userType", userDO.getUserType());
				// 可用金额
				BigDecimal avaiMoney = new BigDecimal(
						Double.toString(accountBalance[1]));
				BigDecimal blockMoney = new BigDecimal(
						Double.toString(accountBalance[2]));
				resultMap.put("avaiMoney", avaiMoney);
				// 冻结金额
				resultMap.put("blockMoney", blockMoney);
				// 待收待还金额
				PlainResult<Map<String, BigDecimal>> planinResult = investservice
						.findTotalIncomeAndPayMoneyByUserId(userDO.getUserId());
				BigDecimal incomeMoney = planinResult.getData().get(
						"incomeMoney");
				BigDecimal payMoney = planinResult.getData().get("payMoney");
				resultMap.put("incomeMoney", incomeMoney);
				resultMap.put("payMoney", payMoney);
				// 资产总额
				BigDecimal allMoney = avaiMoney.add(blockMoney)
						.add(incomeMoney).subtract(payMoney);
				resultMap.put("allMoney", allMoney);
				resultMap.put("userRealName",
						MobileHelper.nullToEmpty(userDO.getUserRealName()));
				resultMap.put("userEmail",
						MobileHelper.nullToEmpty(userDO.getUserEmail()));
				
				if (userDO.getUserType() == 1) {
					resultMap.put("userPhone", MobileHelper.nullToEmpty(userDO.getUserPhone()));
				} else {
					resultMap.put("userPhone", MobileHelper.nullToEmpty(companyCustomerDao.findByUserId(userId).getCcContactPhone()));
				}
				// 获取用户信息
				if(UserType.PERSONAL.type==userDO.getUserType()){
					userDO.setAccountCategory(AccountCategory.INVESTACCOUNT.type);
					AccountInfoDO investAccount = accountInfoService.getAccountByCategory(userDO);
					if (null == investAccount || StringUtils.isEmpty(investAccount.getAccountNo())) {
						resultMap.put("invest_isOpenAccount", "0");
					} else {
						resultMap.put("invest_isOpenAccount", "1");
					}
				}
				userDO.setAccountCategory(AccountCategory.LOANACCOUNT.type);
				AccountInfoDO loanAccount = accountInfoService.getAccountByCategory(userDO);
				if (null == loanAccount || StringUtils.isEmpty(loanAccount.getAccountNo())) {
					resultMap.put("loan_isOpenAccount", "0");
				} else {
					resultMap.put("loan_isOpenAccount", "1");
				}
//				if (userDO.getUserBusinessState() == null
//						|| userDO.getUserBusinessState() < 2) {
//					resultMap.put("isOpenAccount", "0");
//				} else {
//					resultMap.put("isOpenAccount", "1");
//				}
				if (userDO.getUserAuthorizeFlag() == null
						|| userDO.getUserAuthorizeFlag() == 0) {
					resultMap.put("isAuth", "0");
				} else {
					resultMap.put("isAuth", "1");
				}
				if(StringUtils.isNotEmpty(userDO.getUserRealName()) && StringUtils.isNotEmpty(userDO.getUserDocNo()) &&StringUtils.isNotEmpty(userDO.getUserEmail()) ){
					resultMap.put("isCompleteInfo", "1");
				} else {
					resultMap.put("isCompleteInfo", "0");
				}
				return result;
			}else if ("011".equals(catalog)) {
				// 账户金额明细（分为投资账户和融资账户）
				User user = userService.findEntityById(userId).getData();
				if (user == null) {
					result.setResultCode("201");
					result.setResultMessage("不存在的用户");
					return result;
				}
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(user.getUserId());
				if (user.getUserType() == null || user.getUserType().getType() == 1) {
					userIdentity.setUserType(UserType.PERSONAL);
				} else {
					userIdentity.setUserType(UserType.ENTERPRISE);
				}
				PlainResult<Account> account = accountInfoService
						.queryByUserId(userIdentity);
				String accountNo = account.getData().getAccountNo();
				Double[] accountBalance = { 0.00, 0.00, 0.00 , 0.00, 0.00, 0.00};
				if (accountNo != null && !"".equals(accountNo)) {
					accountBalance = this.doubleDryService.queryBalanceDetail(accountNo);
				}
				
				// 免费提现额度
				BigDecimal cashQuota = user.getUserCashQuota();
				if (cashQuota == null) {
					cashQuota = new BigDecimal(0);
				}
				
				// 本月的免费提现机会剩余次数
				int monthtimes = 0;
				PlainResult<SysConfig> payCapitalResult = sysConfigService
						.querySysConfig(SysConfigEntry.WAIT_PAY_CAPITAL);
				if (!payCapitalResult.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage("借款人使用免费提现次数的限制，待还本金的上限查询失败");
					return result;
				}
				PlainResult<SysConfig> monthfreeTocashTimesResult = sysConfigService
						.querySysConfig(SysConfigEntry.MONTHFREE_TOCASH_TIMES);
				if (!monthfreeTocashTimesResult.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage("每个用户每月免费提现次数查询失败");
					return result;
				}
				// 用户的待还本金
				PlainResult<BigDecimal> waitPayCapital = paymentPlanService
						.queryWaitPayCapital(user.getUserId());
				
				// 查询用户本月的提现次数
				PlainResult<Integer> resultx = toCashService
						.countTocashCurrentMonth(user.getUserId());
				if (!resultx.isSuccess()) {
					result.setResultCode("201");
					result.setResultMessage("查询用户本月的提现次数失败");
					return result;
				}
				if (resultx.getData() < Integer.parseInt(monthfreeTocashTimesResult
						.getData().getConfValue())
						&& waitPayCapital.getData().compareTo(
								new BigDecimal(payCapitalResult.getData()
										.getConfValue())) < 0) {
					monthtimes = Integer.parseInt(monthfreeTocashTimesResult.getData()
							.getConfValue()) - resultx.getData();
				}
				Map<String, Object> resultMap = Maps.newHashMap();


				resultMap.put("userRealName", user.getUserRealName());
				resultMap.put("cashQuota", cashQuota);
				resultMap.put("monthtimes", monthtimes);
				//每個用戶每月免費提現次數
				resultMap.put("usermonthtimes", monthfreeTocashTimesResult.getData()
						.getConfValue());
				// 投资账户可用金额
				BigDecimal invAvaiMoney = new BigDecimal(
						Double.toString(accountBalance[0]));
				// 融资账户可用金额
				BigDecimal loanAvaiMoney = new BigDecimal(
						Double.toString(accountBalance[3]));
				resultMap.put("invAvaiMoney", invAvaiMoney);
				resultMap.put("loanAvaiMoney", loanAvaiMoney);
				result.setResultCode("200");
				result.setResult(resultMap);
				return result;
			} else if ("2".equals(catalog)) {
				// 修改用户信息
				// /mobile/accountManage.json?catalog=2&userId=235&param={"userEmail":"asdf","userRealName":"张康2","userDocNo":"340821"}
				String param = params.getString("param");
	        	UserDO userDO = JSON.parseObject(param, UserDO.class);
	        	userDO.setUserId(userId);
	        	
	        	//实名认证后，不允许修改用户真实姓名和身份证号
	        	UserDO oldUser = userService.findById(userDO.getUserId()).getData();
//	        	if(oldUser!=null && new Integer(1).equals(oldUser.getUserRealnameIsproven())){
//	        		if(!StringUtils.isEmpty(userDO.getUserDocNo()) || !StringUtils.isEmpty(userDO.getUserRealName())){
//	        			result.setResultCode("201");
//	        			result.setResultMessage("实名认证后不允许修改");
//	        			return result;
//	        		}
//	        	}
	        	//校验证件号码唯一性 xiatt 20160625 
	        	if(!StringUtil.isBlank(userDO.getUserDocNo())){
	        		UserDO queryUser = new UserDO();
	        		queryUser.setUserDocNo(userDO.getUserDocNo());
	        		queryUser.setUserId(userId);
	        		//个人用户校验身份证重复，公司用户不需要
	        		queryUser.setUserType(1);
	        		int docNoCount = userService.countDocNoByParam(queryUser);
	        		if(docNoCount>0){
	        			result.setResultCode("201");
	        			result.setResultMessage("证件号码已存在");
	        			return result;
	        		}
	        	}
	        	BaseResult baseResult = userService.modifyUserSelective(userDO);
	        	if (baseResult.isSuccess()) {
	        		result.setResultCode("200");
	        		result.setResultMessage("保存信息成功");
	        	} else {
	        		result.setResultCode("201");
	        		result.setResultMessage(baseResult.getMessage());
	        	}
			}else if ("201".equals(catalog)) {
				// 本地修改手机
				String Verification = params.getString("verifyCode");
				String userPhone = params.getString("userPhone");
		        String validCode = Constants.mobileCodeMap.remove(userPhone);
		        if ( Verification.equals(validCode)) {
		        	UserDO userDO = new  UserDO();
		        	userDO.setUserId(userId);
		        	userDO.setUserPhone(userPhone);
		        	if(!StringUtil.isBlank(userDO.getUserDocNo())){
		        		int count = userService.countPhoneByParam(userDO);
		        		if(count>0){
		        			result.setResultCode("201");
		        			result.setResultMessage("手机号码已存在");
		        			return result;
		        		}
		        	}
		        	BaseResult baseResult = userService.modifyUserSelective(userDO);
		        	if (baseResult.isSuccess()) {
		        		result.setResultCode("200");
		        		result.setResultMessage("手机修改成功");
		        	} else {
		        		result.setResultCode("201");
		        		result.setResultMessage(baseResult.getMessage());
		        	}
		        }else {
	        		result.setResultCode("201");
	        		result.setResultMessage("验证码错误");
	        	}
			} else if ("3".equals(catalog)) {
				// 银行卡列表
				User user = userService.findEntityById(userId).getData();
				if (user == null) {
					result.setResultCode("201");
					result.setResultMessage("不存在的用户");
					return result;
				}
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(userId);
				if (user.getUserType().getType() == 1) {
					userIdentity.setUserType(UserType.PERSONAL);
				} else {
					userIdentity.setUserType(UserType.ENTERPRISE);
				}
				PlainResult<Account> account = accountInfoService
						.queryByUserId(userIdentity);
				Map<String, Object> objMap = new HashMap<String, Object>();
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				if(account.isSuccess() && StringUtils.isNotEmpty(account.getData().getAccountNo())){
					String accountNo = account.getData().getAccountNo();
					String userPhone=user.getUserPhone();
					List<BankInfoDO> list = doubleDryService.queryCard(accountNo, userPhone);
					Map<String, Object> loanMap = new HashMap<String, Object>();
					for (BankInfoDO bankInfoDO : list) {
						loanMap = new HashMap<String, Object>();
						//loanMap.put("bankId", bankInfoDO.getBankId());
						loanMap.put("bankNumber", bankInfoDO.getBankNo());
						loanMap.put("bankAccountType", bankInfoDO.getBankAccountType());//1投资户，2融资户
						//loanMap.put("bankName", bankInfoDO.getBankName());
						loanMap.put("BankCode", bankInfoDO.getBankCode());
						loanMap.put("BankIcon", "/Images/bank-"+bankInfoDO.getBankCode()+".png");
						loanList.add(loanMap);
					}
				}
				objMap.put("pageCount", 1);
				objMap.put("list", JSON.toJSON(loanList));
				objMap.put("isAddCard", "1");

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("4".equals(catalog)) {
				/*// 添加银行卡
				String param = params.getString("param");
				String md5Flag = params.getString("md5Flag");

				if (param == null || "".equals(param)) {
					List<CardCityBaseDO> list = cardCityBaseService
							.queryAllCity().getData();
					String md5 = Md5Encrypt.md5(JSON.toJSONString(list));
					if (!md5.equals(md5Flag)) {
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
					BaseResult baseResult = bankInfoService
							.createBankInfo(bankInfo);
					if (baseResult.isSuccess()) {
						result.setResultCode("200");
						result.setResultMessage("添加银行卡成功");
					} else {
						result.setResultCode("201");
						result.setResultMessage(baseResult.getMessage());
					}
				}*/
			} else if ("5".equals(catalog)) {
				// 账户充值
				// /mobile/accountManage.json?catalog=5&userId=131&money=100
				String money = FormatHelper.changeY2F(params.getString("money"));

				// if (!MobileHelper.check(userService, userId, result)) {
				// return result;
				// }

				User user = userService.findEntityById(userId).getData();
				UserIdentity userIdentity = new UserIdentity();
				if (user.getUserType() == null || "".equals(user.getUserType())
						|| user.getUserType() == UserType.PERSONAL) {
					user.setUserType(UserType.PERSONAL);
				} else {
					user.setUserType(UserType.ENTERPRISE);
				}
				userIdentity.setUserType(user.getUserType());
				userIdentity.setUserId(user.getUserId());
				Account account = accountInfoService
						.queryByUserId(userIdentity).getData();

				if (money != null && !"".equals(money) && account != null
						&& !"".equals(account)) {
					Map<String,String> map = new HashMap<String,String>();
					PlainResult<DealReturn> paramMap = rechargeService
							.recharge(user.getUserId(), user.getUserType(),
									new BigDecimal(params.getString("money")), map);
					Map<String, String> param = new HashMap<String, String>();
					param.put("Transid", "CBHBNetLoanRecharge");
					param.put("MerBillNo", paramMap.getData().getDrInnerSeqNo());
					param.put("PlaCustId", account.getAccountNo());
					param.put("FeeType", "0");
					param.put("MerFeeAmt", "0");
					param.put("FrontUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("rechargeReturnUrl"));
					param.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("appRechargeNotifyUrl"));    	
					param.put("TransAmt", money);
					param.put("MerPriv", String.valueOf(user.getUserId()));
					param.put("TransTyp", params.getString("TransTyp"));//1投资户 2融资户 (必送)
					Map<String, String> resultMap = ExchangeDataUtils.getMobileSubmitData(param);
			        resultMap.remove("MerBillNo");
					StringBuffer sb = new StringBuffer();
					sb.append("Transid=").append(resultMap.get("Transid"));
					sb.append("&NetLoanInfo=").append(resultMap.get("NetLoanInfo"));
					Map<String, Object> returnMap = new HashMap<String, Object>();
					returnMap.put("submitUrl", resultMap.get("requestUrl"));
					returnMap.put("postData", sb.toString());
					result.setResult(returnMap);
					result.setResultCode("200");
					return result;
				} else {
					result.setResultCode("201");
					result.setResultMessage("充值失败");
				}
			} else if ("6".equals(catalog)) {
				// 账户提现
				// /mobile/accountManage.json?catalog=6&money=100&userId=156&bankId=36
				Double money = params.getDouble("money");
				String mey = params.getString("money");
				int monthtimes = Integer.valueOf(params.getString("monthtimes"));

				User user = userService.findEntityById(userId).getData();
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(user.getUserId());
				if (user.getUserType() == null
						|| user.getUserType().getType() == 1) {
					user.setUserType(UserType.PERSONAL);
				} else {
					user.setUserType(UserType.ENTERPRISE);
				}
				userIdentity.setUserType(user.getUserType());
				Account account = accountInfoService
						.queryByUserId(userIdentity).getData();

				if (money != null && !"".equals(money) && account != null
						&& !"".equals(account)) {
					// 生成订单号
					//InnerSeqNo innerseqno = InnerSeqNo.getInstance();
			     	Map<String,Object> map = new HashMap<String,Object>();
			 		//快速提现
					//String CardNo = params.getString("CardNo");
			     	//map.put("OrderNo", innerseqno.toString());
			     	map.put("Amount", mey);
			     	//map.put("FeeMax", "");
			     	//map.put("FeeRate", "");
			     	map.put("monthtimes", monthtimes);
					// 计算手续费比例,存入map
					BaseResult resultx = toCashService.calculationPlatformFee(
							user.getUserId(), new BigDecimal(mey), map);
					if (!resultx.isSuccess()) {
						result.setResultCode("500");
						result.setResultMessage(resultx.getMessage());
						return result;
					}

					PlainResult<DealReturn> paramMap = toCashService
							.toCashOther(user.getUserId(), user.getUserType(),
									new BigDecimal(money), map);
					System.out.println("发送参数:======"
							+ paramMap.getData().getCashRecords().get(0)
									.getCrRequestParameter());

					//在此处构造接口参数
			        PlainResult<Map> returnData = new PlainResult<Map>();
			        Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
			        paramsMap.put("Transid", "CBHBNetLoanWithdraw");
			        paramsMap.put("MerBillNo", paramMap.getData().getDrInnerSeqNo());
			        paramsMap.put("PlaCustId", account.getAccountNo());
			        PlainResult<SysConfig> withdrawrate = sysConfigService
							.querySysConfig(SysConfigEntry.WITHDRAW_RATE);
					if (!withdrawrate.isSuccess()) {
						result.setResultCode("500");
						result.setResultMessage(withdrawrate.getMessage());
						return result;
					}
			        if(monthtimes>0){
			        	paramsMap.put("FeeType", "0");
				        paramsMap.put("MerFeeAmt", "0");
			        }else if(monthtimes==0){
			        	paramsMap.put("FeeType", "1");
				        paramsMap.put("MerFeeAmt", FormatHelper.changeY2F(new BigDecimal(mey).multiply(new BigDecimal(withdrawrate.getData().getConfValue())).divide(new BigDecimal("100"))));
			        }
			        paramsMap.put("BackUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("appWithdrawsNotifyUrl"));    	
			        paramsMap.put("FrontUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("withdrawReturnUrl"));
			        paramsMap.put("TransAmt", FormatHelper.changeY2F(params.getString("money")));

			        paramsMap.put("MerPriv", String.valueOf(user.getUserId()));
			        paramsMap.put("TransTyp", params.getString("TransTyp"));
			        Map<String, String> resultMap = ExchangeDataUtils.getMobileSubmitData(paramsMap);
			    	resultMap.put("NetLoanInfo",resultMap.get("NetLoanInfo"));
			    	resultMap.remove("MerBillNo");
					StringBuffer sb = new StringBuffer();
					sb.append("Transid=").append(resultMap.get("Transid"));
					sb.append("&NetLoanInfo=").append(resultMap.get("NetLoanInfo"));
					Map<String, Object> returnMap = new HashMap<String, Object>();
					returnMap.put("submitUrl", resultMap.get("requestUrl"));
					returnMap.put("postData", sb.toString());
					result.setResult(returnMap);
					result.setResultCode("200");
					return result;
				} else {
					result.setResultCode("201");
					result.setResultMessage("提现失败");
				}
			} else if ("61".equals(catalog)) {
				// 账户提现(绑卡)
				// /mobile/accountManage.json?catalog=61&money=345&cardNo=6217788302900174767&cardType=0&branchBankName=zhihang001&Province=18&city=1183&userId=156&bankCode=1
				Double money = params.getDouble("money");
				String mey = params.getString("money");

				String CardNo = params.getString("cardNo");
				String CardType1 = params.getString("cardType");
				String BankCode = params.getString("bankCode");
				String BranchBankName = params.getString("branchBankName");
				String Province = params.getString("Province");
				String City = params.getString("city");
				User user = userService.findEntityById(userId).getData();
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(user.getUserId());
				if (user.getUserType() == null
						|| user.getUserType().getType() == 1) {
					user.setUserType(UserType.PERSONAL);
				} else {
					user.setUserType(UserType.ENTERPRISE);
				}
				userIdentity.setUserType(user.getUserType());
				Account account = accountInfoService
						.queryByUserId(userIdentity).getData();

				if (money != null && !"".equals(money) && account != null
						&& !"".equals(account)) {
					// 生成订单号
					InnerSeqNo innerseqno = InnerSeqNo.getInstance();
					//保存银行卡，用于快速提现
					BankInfo bankInfo = new BankInfo();
					bankInfo.setBankUserId(user.getUserId());
					bankInfo.setBankUserType(user.getUserType().getType());
					bankInfo.setCardType(CardType.valueOf(Integer.parseInt(CardType1)));
					bankInfo.setBankNo(CardNo);
					bankInfo.setBankName(BranchBankName);
					bankInfo.setBankCode(BankCode);
					bankInfo.setAreaCode(City);
					PlainResult<BankInfoDO> bankResult = bankInfoService
							.saveBankInfo(bankInfo);

					Map<String, Object> param = new HashMap<String, Object>();
					param.put("WithdrawMoneymoremore", account.getAccountNo());
					param.put("PlatformMoneymoremore", "");
					param.put("OrderNo", innerseqno.toString());
					param.put("Amount", mey);
					// param.put("FeePercent", "0");
					param.put("FeeMax", "");
					param.put("FeeRate", "");
					param.put("CardNo", CardNo);
					param.put("CardType", CardType1);
					param.put("BankCode", BankCode);
					param.put("BranchBankName", BranchBankName);
					// param.put("Province",
					// cardCityBaseService.queryCardByCode(Integer.valueOf(bankInfoDO.getAreaCode())).getData().getProvCode().toString());
					param.put("Province", Province);
					param.put("City", City);
					param.put("Remark1", bankResult.getData().getBankId());
					param.put("Remark2", "");
					param.put("Remark3", "");
					param.put("ReturnURL", "");
					param.put("NotifyURL", "");
					param.put("SignInfo", "");

					// 计算手续费比例,存入map
					BaseResult resultx = toCashService.calculationPlatformFee(
							user.getUserId(), new BigDecimal(mey), param);
					if (!resultx.isSuccess()) {
						result.setResultCode("500");
						result.setResultMessage(resultx.getMessage());
						return result;
					}

					PlainResult<DealReturn> paramMap = toCashService
							.toCashOther(user.getUserId(), user.getUserType(),
									new BigDecimal(money), param);
					System.out.println("发送参数:======"
							+ paramMap.getData().getCashRecords().get(0)
									.getCrRequestParameter());

					JSONObject jo = JSON.parseObject(paramMap.getData()
							.getCashRecords().get(0).getCrRequestParameter());
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
							+ URLEncoder.encode(jo.get("CardNo").toString(),
									"UTF-8")
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
							+ MobileHelper.nullToEmpty(jo
									.get("RandomTimeStamp"))
							+ "&ReturnURL="
							+ jo.get("ReturnURL")
							+ "&NotifyURL="
							+ jo.get("NotifyURL")
							+ "&SignInfo="
							+ URLEncoder.encode(jo.get("SignInfo").toString(),
									"UTF-8");
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
			} else if ("7".equals(catalog)) {
				// 交易记录
				Integer pageSize = params.getInt("pageSize");
				Integer showPage = params.getInt("showPage");

				UserDO userDO = userService.findById(userId).getData();
				UserIdentity userIdentity = new UserIdentity();
				if (userDO.getUserType() == null
						|| "".equals(userDO.getUserType())
						|| userDO.getUserType() == UserType.PERSONAL.getType()) {
					userDO.setUserType(UserType.PERSONAL.getType());
				} else {
					userDO.setUserType(UserType.ENTERPRISE.getType());
				}
				userIdentity
						.setUserType(UserType.valueOf(userDO.getUserType()));
				userIdentity.setUserId(userDO.getUserId());
				PlainResult<Account> account = accountInfoService
						.queryByUserId(userIdentity);
				Map<String, Object> objMap = new HashMap<String, Object>();
				if (account.getData().getAccountNo() == null) {
					objMap.put("pageCount", 0);
					objMap.put("list", new ArrayList(0));
					result.setResult(objMap);
					return result;
				}

				DealRecordDO dealrecorddo = new DealRecordDO();
				dealrecorddo
						.setDrPayAccount(account.getData().getAccountNo());
				PageResult<DealRecordDO> pageResult = dealRecordService
						.queryDealByParams(dealrecorddo, new PageCondition(
								showPage, pageSize), null, null);
				List<DealRecordDO> list = pageResult.getData();

				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();

				Map<String, Object> loanMap = new HashMap<String, Object>();

				for (DealRecordDO dealRecordDO : list) {
					loanMap = new HashMap<String, Object>();
					loanMap.put("tradeType",
							DealType.valueOf(dealRecordDO.getDrType()).getDes()); // 交易类型
					loanMap.put("traderStatus",
							DealState.valueOf(dealRecordDO.getDrState())
									.getDes()); // 交易状态
					loanMap.put("tradeTime", new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").format(dealRecordDO
							.getDrOperateDate())); // 交易时间
					loanMap.put("transMoney", dealRecordDO.getDrMoneyAmount()); // 交易金额
					loanMap.put("transCharges", "0.00"); // 交易手续费
					loanMap.put("transNum", dealRecordDO.getDrInnerSeqNo()
							.substring(0, 15) + "..."); // 交易流水号
					// loanMap.put("transNum", dealRecordDO.getDrInnerSeqNo());
					// //交易流水号
					loanMap.put("receiveAccount",
							dealRecordDO.getDrReceiveAccount()); // 交易对方
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
					BaseResult baseResult = userService
							.modifyUserSelective(userDO);
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

				Map<String, Object> objMap = new MobileMap<String, Object>();

				// 获取用户绑定的银行卡信息
//				List<BankInfoDO> list = bankInfoService.queryListBankInfo(
//						userId).getData();
//				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
//				Map<String, Object> loanMap = new HashMap<String, Object>();
//				for (BankInfoDO bankInfoDO : list) {
//					loanMap = new HashMap<String, Object>();
//					loanMap.put("bankId", bankInfoDO.getBankId());
//					loanMap.put("bankNumber", bankInfoDO.getBankNo());
//					loanMap.put("bankName", bankInfoDO.getBankName());
//					loanMap.put("bankCode", bankInfoDO.getBankCode());
//					loanMap.put("bankIcon", "");
//					loanList.add(loanMap);
//				}
				if (userDO.getUserEmail() != null) {
					objMap.put("userEmail",
							userDO.getUserEmail());
				}
				objMap.put("userRealName",
						userDO.getUserRealName());
				UserIdentity userIdentity = new UserIdentity();
				userIdentity.setUserId(userDO.getUserId());
				if (userDO.getUserType() == null || userDO.getUserType() == 1) {
					userDO.setUserType(UserType.PERSONAL.getType());
				} else {
					userDO.setUserType(UserType.ENTERPRISE.getType());
				}
				userIdentity
						.setUserType(UserType.valueOf(userDO.getUserType()));
				Account account = accountInfoService
						.queryByUserId(userIdentity).getData();
				String accountNo = null != account?account.getAccountNo():"";
				Double[] accountBacance = { 0.00, 0.00, 0.00, 0.00, 0.00, 0.00};
				if (accountNo != null && !"".equals(accountNo)) {
					// 网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
					accountBacance = doubleDryService.queryBalanceDetail(accountNo);
				}
				//投资户的余额
				objMap.put("invest_allMoney", accountBacance[2]);//账面总余额
				objMap.put("invest_avlMoney", accountBacance[0]);//可用余额
				objMap.put("invest_fraMoney", accountBacance[1]);//冻结金额
				//融资户的余额
				objMap.put("loan_allMoney", accountBacance[5]);//账面总余额
				objMap.put("loan_avlMoney", accountBacance[3]);//可用余额
				objMap.put("loan_fraMoney", accountBacance[4]);//冻结金额
				//投资户与融资户总的余额
				objMap.put("allMoney", accountBacance[2]+accountBacance[5]);//账面总余额
				objMap.put("avlMoney", accountBacance[0]+accountBacance[3]);//可用余额
				objMap.put("fraMoney", accountBacance[1]+accountBacance[4]);//冻结金额
//				objMap.put("bankList", JSON.toJSON(loanList));

				result.setResultCode("200");
				result.setResult(JSON.toJSON(objMap));
			} else if ("10".equals(catalog)) {
				// 用户信息
				UserDO userDO = userService.findById(userId).getData();
				Map<String, Object> objMap = new MobileMap<String, Object>();
				if(UserType.PERSONAL.type==userDO.getUserType()){
					userDO.setAccountCategory(AccountCategory.INVESTACCOUNT.type);
					AccountInfoDO investAccount = accountInfoService.getAccountByCategory(userDO);
					if (null == investAccount || StringUtils.isEmpty(investAccount.getAccountNo())) {
						objMap.put("invest_isOpenAccount", "0");
					} else {
						objMap.put("invest_isOpenAccount", "1");
					}
				}
				userDO.setAccountCategory(AccountCategory.LOANACCOUNT.type);
				AccountInfoDO loanAccount = accountInfoService.getAccountByCategory(userDO);
				if (null == loanAccount || StringUtils.isEmpty(loanAccount.getAccountNo())) {
					objMap.put("loan_isOpenAccount", "0");
				} else {
					objMap.put("loan_isOpenAccount", "1");
				}
				
				objMap.put("realName", userDO.getUserRealName());
				objMap.put("userName", userDO.getUserName());
				objMap.put("email", userDO.getUserEmail());
				objMap.put("userDocNo", userDO.getUserDocNo());
				if (userDO.getUserType() == 1) {
					objMap.put("userPhone", MobileHelper.nullToEmpty(userDO.getUserPhone()));
				} else {
					objMap.put("userPhone", MobileHelper.nullToEmpty(companyCustomerDao.findByUserId(userId).getCcContactPhone()));
				}
				result.setResult(objMap);
			}
			// 资金流水
			else if ("20".equals(catalog)) {
				result = cashSerial(params);
			}
			// 资金流水详细
			else if ("21".equals(catalog)) {
				result = orderDetail(params);
			}
			// 银行卡解绑
			else if ("22".equals(catalog)) {
//				result = unBindBankCard(params);
				result = modifyBindBankCard(params);
			}
			// 开户
			else if ("23".equals(catalog)) {
				result = openAccount(params);
			}
			// 授权/解授权
			else if ("24".equals(catalog)) {
				result = auth(params);
			}
			// 授权信息
			else if ("240".equals(catalog)) {
				result = authInformation(params);
			}
			// 帐号状态检查
			else if ("25".equals(catalog)) {
				result = check(params);
			}
			// 提现前显示
			else if ("26".equals(catalog)) {
				result = toCashBefore(params);
			}
			// 修改交易密码
			else if ("27".equals(catalog)) {
				result = modifyDealPsw(params);
			} 
			else if("28".equals(catalog)){
				// accountManage.json?catalog=28&userId=133
				result = redList(params);
			}else if("29".equals(catalog)){
				result = modifyPlatPsw(params);
			}else if("30".equals(catalog)){
				// accountManage.json?catalog=28&userId=133
				result = modifyPhoneNo(params);
			}else if("31".equals(catalog)){
				// accountManage.json?catalog=28&userId=133
				result = investToMarginMoney(params);
			}// 机构用户开户
			else if ("32".equals(catalog)) {
				result = openChargeAccount(params);
			}// 销户
			else if ("33".equals(catalog)) {
				result = closeAccount(params);
			}// 查询交易状态
			else if ("34".equals(catalog)) {
				result = queryOrderStatus(params);
			}
			else {
				result.setResultCode("201");
				result.setResultMessage("catalog notFound");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode("201");
			result.setResultMessage("error");
		}

		return result;
	}
	
	/**
	 * 查询交易状态
	 * @param params
	 * @return
	 */
	public JsonMobileVO queryOrderStatus(ParameterParser params) {
		JsonMobileVO result = new JsonMobileVO();
		String id = params.getString("id");
		 String type = params.getString("type");
		 if(id==null){
			 id="";
		 }
    	Map<String,String> resultMap = accountInfoService.queryTransStatus(id, type);
		String RespCode = resultMap.get("RespCode");
		String TransStat = resultMap.get("TransStat");
		//更新交易、资金操作记录
		if(null!=TransStat&&TransStat.equals("S1")){
			dealRecordService.modifyDealRecordStateWithDouble(resultMap);
			PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo((String)resultMap.get("MerBillNo"));
	        CashRecordDO cashrecord = cashrecorddo.getData(); 
	        cashrecord.setCrResponseState(Integer.valueOf(RespCode));
	        cashrecordservice.modifyCashRecordState(cashrecord);
		}else if(null==TransStat||TransStat.equals("F1")){//交易失败
			RespCode = "111111";//自定义失败状态码为111111
			resultMap.put("RespCode", RespCode);
			dealRecordService.modifyDealRecordStateWithDouble(resultMap);
			PlainResult<CashRecordDO> cashrecorddo = cashrecordservice.queryCashRecordBySeqNo((String)resultMap.get("MerBillNo"));
	        CashRecordDO cashrecord = cashrecorddo.getData(); 
	        cashrecord.setCrResponseState(Integer.valueOf(RespCode));
	        cashrecordservice.modifyCashRecordState(cashrecord);
		}
		
        if("CZJL".equals(type)){
        	RechargeRecordDO rechargeDo = new RechargeRecordDO();
	        rechargeDo.setRechargeSeqNo(resultMap.get("MerBillNo"));
	        if(null!=TransStat&&TransStat.equals("S1")){
	        	rechargeDo.setRechargeState(1);}
	        else if(null==TransStat||TransStat.equals("F1")){
	        	rechargeDo.setRechargeState(2);
	        }
	        BaseResult rechargeresult = rechargeService.updateBackStatus(rechargeDo);
	        System.out.println("修改充值记录："+rechargeresult.isSuccess()+rechargeresult.getMessage());
        }else if("TXJL".equals(type)){
        	//更新提现记录
	        PlainResult<TocashRecordDO> resultRecord=tocashservice.queryBySeqNo(resultMap.get("MerBillNo"));
	        TocashRecordDO toCashDo = new TocashRecordDO();
	        toCashDo.setTocashSeqNo(resultMap.get("MerBillNo"));
	        if(null!=TransStat&&"S1".equals(TransStat)){
	        	toCashDo.setTocashState(ToCashState.SUCCESS.getState());
				toCashDo.setTocashValidquota(resultRecord.getData().getTocashQuota());
				//更新免费提现额度
				if(resultRecord.getData()!=null && resultRecord.getData().getTocashQuota()!=null){
					userService.reduceCashQuota(resultRecord.getData().getTocashUserId(), resultRecord.getData().getTocashQuota());
				}			
	        }else if(null==TransStat||"F1".equals(TransStat)){
	        	toCashDo.setTocashState(ToCashState.FAILURE.getState());
				toCashDo.setTocashValidquota(new BigDecimal(0));
	        }
	        BaseResult tocashresult = tocashservice.updateBySeqNo(toCashDo);	      
	        System.out.println("修改提现记录："+tocashresult.isSuccess()+tocashresult.getMessage());
        }
		if(null!=TransStat&&"S1".equals(TransStat)){
			result.setResultCode("200");
			result.setResultMessage("交易成功");
		}else if(null==TransStat||"F1".equals(TransStat)){
			result.setResultCode("201");
			result.setResultMessage("交易失败");
		}else if("W2".equals(TransStat)){
			result.setResultCode("201");
			result.setResultMessage("请求处理中");
		}else if("W3".equals(TransStat)){
			result.setResultCode("201");
			result.setResultMessage("系统受理中");
		}else if("W4".equals(TransStat)){
			result.setResultCode("201");
			result.setResultMessage("银行受理中");
		}
    	return result;
    }
	
	
	/**
	 * 销户
	 * @param params
	 * @return
	 */
	public JsonMobileVO closeAccount(ParameterParser params) {
		JsonMobileVO result = new JsonMobileVO();
    	PlainResult<User> userResult= userService.findEntityById(params.getInt("userId"));
    	User user=userResult.getData();
    	UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(params.getInt("userId"));
        if (user.getUserType() == null || user.getUserType().getType() == 1) {
            user.setUserType(UserType.PERSONAL);
        } else {
            user.setUserType(UserType.ENTERPRISE);
        }
        userIdentity.setUserType(user.getUserType());
        PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
        String accountNo = account.getData().getAccountNo();
    	Map<String,String> param= new LinkedHashMap<String,String>();
    	param.put("PlaCustId", accountNo);   
    	param.put("Agent", "app");
    	param.put("TransTyp", params.getString("TransTyp"));//1投资户 2融资户 (必送)
    	PlainResult<Map> paramMap = doubledryservice.closeAccount(param);
    	paramMap.getData().remove("MerBillNo");
		StringBuffer sb = new StringBuffer();
		sb.append("Transid=").append(paramMap.getData().get("Transid"));
		sb.append("&NetLoanInfo=").append(paramMap.getData().get("NetLoanInfo"));
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("submitUrl", paramMap.getData().get("requestUrl"));
		returnMap.put("postData", sb.toString());
		result.setResult(returnMap);
		result.setResultCode("200");
    	return result;
    }
	
	/**
	 * 机构账户开户
	 * @param params
	 * @return
	 */
	public JsonMobileVO openChargeAccount(ParameterParser params) {
		JsonMobileVO result = new JsonMobileVO();
    	User user=(User)session.getAttribute("user");
    	Map<String,String> param= new LinkedHashMap<String,String>();
    	param.put("TxnTyp", params.getString("TxnTyp"));
		param.put("AccountNo", params.getString("AccountNo"));
		param.put("AccountName", params.getString("AccountName"));
		param.put("AccountBk", params.getString("AccountBk"));
		param.put("userId", params.getString("userId"));
		param.put("Agent", "app");
		param.put("TransTyp", String.valueOf(AccountCategory.LOANACCOUNT.type));//1投资户 2融资户 (必送)
    	PlainResult<Map> paramMap = doubledryservice.openChargeAccent(param);
	    paramMap.getData().remove("MerBillNo");
		StringBuffer sb = new StringBuffer();
		sb.append("Transid=").append(paramMap.getData().get("Transid"));
		sb.append("&NetLoanInfo=").append(paramMap.getData().get("NetLoanInfo"));
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("submitUrl", paramMap.getData().get("requestUrl"));
		returnMap.put("postData", sb.toString());
		result.setResult(returnMap);
		result.setResultCode("200");
    	return result;
    }
	
	/**
	 * 融资账户转账
	 * @param params
	 * @return
	 */
	public JsonMobileVO investToMarginMoney(ParameterParser params) {
		JsonMobileVO result = new JsonMobileVO();
		PlainResult<User> userResult= userService.findEntityById(params.getInt("userId"));
    	User user=userResult.getData();
    	UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(params.getInt("userId"));
        if (user.getUserType() == null || user.getUserType().getType() == 1) {
            user.setUserType(UserType.PERSONAL);
        } else {
            user.setUserType(UserType.ENTERPRISE);
        }
        userIdentity.setUserType(user.getUserType());
        PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
    	String accountNo = account.getData().getAccountNo();
    	String money = FormatHelper.changeY2F(params.getString("money"));
    	Map<String, String> resultMap = payMentService.investToMargin(money, accountNo);
    	String  RespCode = resultMap.get("RespCode");
    	String  RespDesc = resultMap.get("RespDesc");
    	if("000000".equals(RespCode)){
    		result.setResultCode("200");
			result.setResultMessage("转账成功");
		}else{
			result.setResultCode("201");
			result.setResultMessage(RespDesc);
		}
    	return result;
    }
	
	/**
	 * 红包列表
	 * @param params
	 * @return
	 */
	private JsonMobileVO redList(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		Integer userId = params.getInt("userId");
		int currentPage = params.getInt("showPage");
		int pageSize = params.getInt("pageSize");

		PageCondition pageCondition = new PageCondition(currentPage, pageSize);
		RedSearchDO redSearchDO = new RedSearchDO();
		redSearchDO.setUserId(userId);
		redSearchDO.setOrder("FIELD(rs_state, 1, 2, 0), rs_closetime ASC");// 排序按未使用、已使用、已过期
																			// 红包过期时间升序
		PageResult<RedsendJ> RedsendJPageResult = redSendService.queryListJ(
				redSearchDO, pageCondition);
		List<RedsendJ> redsendJList = RedsendJPageResult.getData();
		List<Map<String, Object>> redList = Lists.newArrayList();
		for(RedsendJ red:redsendJList){
			Map<String, Object> map = Maps.newHashMap();
			map.put("validAmount", red.getRsValidAmount());
			map.put("redSource", red.getRsType().getDes());
			map.put("useScope", red.getRsUseScope());
			map.put("closeTime", DateUtil.formatDate(red.getRsClosetime(), DateUtil.DEFAULT_DAY_STYLE));
			map.put("state", red.getRsState().des);
			redList.add(map);
		}
		resultMap.put("list", redList);
		resultMap.put("pageCount", RedsendJPageResult.getTotalCount());
		return vo;
	}

	/**
	 * 修改交易密码
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO modifyDealPsw(ParameterParser params) {
		// /mobile/accountManage.json?catalog=27&userId=219&oldDealPsw=123456&newDealPsw=111111
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		String oldDealPsw = params.getString("oldDealPsw");
		int userId = params.getInt("userId");
		String newDealPsw = params.getString("newDealPsw");
		UserDO userDO = userService.findById(userId).getData();
		// 检验原交易密码是否相同
		if (!userDO.getUserDealPwd().equals(CryptUtils.md5(oldDealPsw))) {
			vo.setResultCode("201");
			vo.setResultMessage("原交易密码错误！");
			return vo;
		}
		// 修改交易密码
		UserDO userDO2 = new UserDO();
		userDO2.setUserId(userId);
		userDO2.setUserDealPwd(CryptUtils.md5(newDealPsw));
		BaseResult result = this.userService.modifyUserSelective(userDO2);
		if (!result.isSuccess()) {
			vo.setResultCode("500");
			vo.setMessage(result.getMessage());
			return vo;
		} else {
			vo.setMessage("修改成功！");
		}
		return vo;
	}

	
	/**
	 * 修改存管密码
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO modifyPlatPsw(ParameterParser params) {
		// /mobile/accountManage.json?catalog=27&userId=219&oldDealPsw=123456&newDealPsw=111111
		JsonMobileVO vo = new JsonMobileVO();		
		int userId = params.getInt("userId");
		// 获取用户信息
		PlainResult<User> result = userService.findEntityById(userId);
		User user = result.getData();
        String type = params.getString("type");//1:找回存管密码   2:修改存管密码  3：机构用户找回密码  4：机构用户修改密码
    	UserIdentity userIdentity = new UserIdentity();
    	if(user.getUserType()==null||user.getUserType()==UserType.PERSONAL){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());
    	userIdentity.setUserId(user.getUserId());
    	PlainResult<Account> account =  accountInfoService.queryByUserId(userIdentity) ;
    	
    	if(account!=null){
    		String accountNo = account.getData().getAccountNo();
	        Map<String, String> param = new LinkedHashMap <String, String> ();
	        param.put("PlaCustId", accountNo);
	        param.put("type", type);
	        param.put("Agent", "app");
	        param.put("MobileNo", user.getUserPhone());
	        param.put("TransTyp", params.getString("TransTyp"));//1投资户 2融资户 (必送)
	        PlainResult<Map> paramMap =doubledryservice.changPwd(param);
	        paramMap.getData().remove("MerBillNo");
			StringBuffer sb = new StringBuffer();
			sb.append("Transid=").append(paramMap.getData().get("Transid"));
			sb.append("&NetLoanInfo=").append(paramMap.getData().get("NetLoanInfo"));
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("submitUrl", paramMap.getData().get("requestUrl"));
			returnMap.put("postData", sb.toString());
			vo.setResult(returnMap);
			vo.setResultCode("200");
    	}else{
    		vo.setResultCode("500");
			vo.setResultMessage("请先开户！");
			return vo;
    	}
		return vo;
	}
	
	/**
	 * 修改手机号码
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO modifyPhoneNo(ParameterParser params) {
		// /mobile/accountManage.json?catalog=27&userId=219&oldDealPsw=123456&newDealPsw=111111
		JsonMobileVO vo = new JsonMobileVO();
		String Verification = params.getString("verifyCode");
		String newPhone = params.getString("userPhone");
        String validCode = Constants.mobileCodeMap.remove(newPhone);
        if (Verification.equals(validCode)) {
        	int userId = params.getInt("userId");
    		// 获取用户信息
    		PlainResult<User> result = userService.findEntityById(userId);
    		User user = result.getData();
    		UserIdentity userIdentity = new UserIdentity();
        	if(user.getUserType()==null||user.getUserType()==UserType.PERSONAL){
        		user.setUserType(UserType.PERSONAL);
        	}else{
        		user.setUserType(UserType.ENTERPRISE);
        	}
        	userIdentity.setUserType(user.getUserType());
        	userIdentity.setUserId(user.getUserId());
        	PlainResult<Account> account =  accountInfoService.queryByUserId(userIdentity) ;
        	
        	String phone = user.getUserPhone();
        	if(phone!=null&&!"".equals(phone)&&account!=null){
        		String accountNo = account.getData().getAccountNo();
    	        Map<String, String> param = new LinkedHashMap <String, String> ();
    	        param.put("PlaCustId", accountNo);
    	        param.put("MobileNo", phone);
    	        param.put("NewMobileNo", newPhone);
    	        param.put("UsrName", user.getUserRealName());
    	        param.put("IdentType", "00");
    	        param.put("IdentNo", user.getUserDocNo());
    	        param.put("Agent", "app");
    	        param.put("MerPriv", String.valueOf(user.getUserId()));
    	        param.put("TransTyp", String.valueOf(account.getData().getAccountCategory()));//1投资户 2融资户 (必送)
    	        PlainResult<Map> paramMap =doubledryservice.changPhone(param);
    	        paramMap.getData().remove("MerBillNo");
    			StringBuffer sb = new StringBuffer();
    			sb.append("Transid=").append(paramMap.getData().get("Transid"));
    			sb.append("&NetLoanInfo=").append(paramMap.getData().get("NetLoanInfo"));
    			Map<String, Object> returnMap = new HashMap<String, Object>();
    			returnMap.put("submitUrl", paramMap.getData().get("requestUrl"));
    			returnMap.put("postData", sb.toString());
    			vo.setResult(returnMap);
    			vo.setResultCode("200");
        	}else{
        		vo.setResultCode("500");
    			vo.setResultMessage("请先开户！");
    			return vo;
        	}
        } else {
        	vo.setResultCode("201");
			vo.setResultMessage("验证码错误！");
        }
		return vo;
	}
	
	/**
	 * 提现前信息展示
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO toCashBefore(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		Integer userId = params.getInt("userId");
		PlainResult<User> result = userService.findEntityById(userId);
		// 免费提现额度
		BigDecimal cashQuota = result.getData().getUserCashQuota();
		if (cashQuota == null) {
			cashQuota = new BigDecimal(0);
		}
		// 本月的免费提现机会剩余次数
		int monthtimes = 0;
		PlainResult<SysConfig> monthfreeTocashTimesResult = sysConfigService
				.querySysConfig(SysConfigEntry.MONTHFREE_TOCASH_TIMES);
		if (!monthfreeTocashTimesResult.isSuccess()) {
			vo.setResultCode("500");
			vo.setResultMessage("免费提现次数查询失败");
			return vo;
		}

		// 查询用户本月的提现次数
		PlainResult<Integer> resultx = toCashService
				.countTocashCurrentMonth(userId);
		if (!resultx.isSuccess()) {
			vo.setResultCode("500");
			vo.setResultMessage(resultx.getMessage());
			return vo;
		}
		// 用户的待还本金
		PlainResult<BigDecimal> waitPayCapital = paymentPlanService
				.queryWaitPayCapital(userId);
		PlainResult<SysConfig> payCapitalResult = sysConfigService
				.querySysConfig(SysConfigEntry.WAIT_PAY_CAPITAL);
		if (!payCapitalResult.isSuccess()) {
			vo.setResultCode("500");
			vo.setResultMessage("待还本金的上限查询失败");
			return vo;
		}
		// 用户本月提现次数小于系统设置 && 用户待还本金没有超过系统设置
		if (resultx.getData() < Integer.parseInt(monthfreeTocashTimesResult
				.getData().getConfValue())
				&& waitPayCapital.getData().compareTo(
						new BigDecimal(payCapitalResult.getData()
								.getConfValue())) < 0) {
			monthtimes = Integer.parseInt(monthfreeTocashTimesResult.getData()
					.getConfValue()) - resultx.getData();
		}
		resultMap.put("cashQuota", cashQuota);
		resultMap.put("monthTimes", monthtimes);
		return vo;
	}

	/**
	 * 检查有无开户，授权
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO check(ParameterParser params) {
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		Integer userId = params.getInt("userId");
		// 获取用户信息
		PlainResult<User> result = userService.findEntityById(userId);
		User user = result.getData();
		if (user == null) {
			vo.setResultCode("404");
			vo.setResultMessage("不存在的用户");
			return vo;
		}
		if(2==user.getUserType().getType()){
			UserIdentity userIdentity = new UserIdentity();
	    	if(user.getUserType()==null||user.getUserType()==UserType.PERSONAL){
	    		user.setUserType(UserType.PERSONAL);
	    	}else{
	    		user.setUserType(UserType.ENTERPRISE);
	    	}
	    	userIdentity.setUserType(user.getUserType());
	    	userIdentity.setUserId(user.getUserId());
	    	PlainResult<Account> account =  accountInfoService.queryByUserId(userIdentity);
	    	String RealNameFlg = null;
	    	String ChargeAmt = null;
	        if(account.isSuccess()&&user.getUserType().getType()==2){
	        	String accountUserAccount =account.getData().getAccountUserAccount();
	        	String mark=account.getData().getAccountMark();
	        	Map<String, String> chargeAccountMap =this.doubleDryService.queryChargeAccountResult(accountUserAccount,mark);
	        	RealNameFlg=chargeAccountMap.get("RealNameFlg");
	        	ChargeAmt=chargeAccountMap.get("ChargeAmt");
	        }
	        resultMap.put("RealNameFlg", RealNameFlg);
	    	resultMap.put("ChargeAmt", ChargeAmt);
	    	resultMap.put("AccountUserChargeAccount", account.getData().getAccountUserChargeAccount());
	    	resultMap.put("AccountUserChargeName", account.getData().getAccountUserChargeName());
	    	resultMap.put("AccountNo", account.getData().getAccountUserAccount());
	    	resultMap.put("AccountName", account.getData().getAccountUserAccountName());
	    	resultMap.put("AccountBk", account.getData().getAccountUserAccountBk());
		}
		resultMap.put("userType", user.getUserType().getType());
		resultMap.put("email", user.getUserEmail());
		UserDO userDO = new UserDO();
		userDO.setUserId(userId);
		userDO.setUserType(user.getUserType().getType());
		if(UserType.PERSONAL.type==userDO.getUserType()){
			userDO.setAccountCategory(AccountCategory.INVESTACCOUNT.type);
			AccountInfoDO investAccount = accountInfoService.getAccountByCategory(userDO);
			if (null == investAccount || StringUtils.isEmpty(investAccount.getAccountNo())) {
				resultMap.put("invest_isOpenAccount", "0");
			} else {
				resultMap.put("invest_isOpenAccount", "1");
			}
		}
		userDO.setAccountCategory(AccountCategory.LOANACCOUNT.type);
		AccountInfoDO loanAccount = accountInfoService.getAccountByCategory(userDO);
		if (null == loanAccount || StringUtils.isEmpty(loanAccount.getAccountNo())) {
			resultMap.put("loan_isOpenAccount", "0");
		} else {
			resultMap.put("loan_isOpenAccount", "1");
		}
//		if (user.getUserBusinessState() == null
//				|| user.getUserBusinessState().getState() < 2) {
//			resultMap.put("isOpenAccount", "0");
//		} else {
//			resultMap.put("isOpenAccount", "1");
//		}
		if (user.getUserAuthorizeFlag() == null
				|| user.getUserAuthorizeFlag().getState() == 0) {
			resultMap.put("isAuth", "0");
		} else {
			resultMap.put("isAuth", "1");
		}
		if(StringUtils.isNotEmpty(user.getUserRealName()) && StringUtils.isNotEmpty(user.getUserDocNo()) &&StringUtils.isNotEmpty(user.getUserEmail()) ){
			resultMap.put("isCompleteInfo", "1");
		} else {
			resultMap.put("isCompleteInfo", "0");
		}
		return vo;
	}

	/**
	 * 授权/解授权
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private JsonMobileVO auth(ParameterParser params)
			throws UnsupportedEncodingException {
		// /mobile/accountManage.json?catalog=24&userId=227
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		Integer userId = params.getInt("userId");
		String txnTyp = params.getString("txnTyp");//1、授权 2、解授权

		// 获取用户信息
		PlainResult<User> result = userService.findEntityById(userId);
		User user = result.getData();
		if (user == null) {
			vo.setResultCode("404");
			vo.setResultMessage("不存在的用户");
			return vo;
		}

		// 获取开户信息
		UserIdentity userIdentity = new UserIdentity();
		userIdentity.setUserId(user.getUserId());
		if (user.getUserType() == null || user.getUserType().getType() == 1) {
			user.setUserType(UserType.PERSONAL);
		} else {
			user.setUserType(UserType.ENTERPRISE);
		}
		userIdentity.setUserType(user.getUserType());
		PlainResult<Account> account = accountInfoService
				.queryByUserId(userIdentity);
		if (!account.isSuccess()) {
			vo.setResultCode("405");
			vo.setResultMessage("你还没有开户，请先开户");
			return vo;
		}

		// 授权参数
		Map<String, String> map = new HashMap<String, String>();
//		map.put("MoneymoremoreId", account.getData().getAccountNo());
//		map.put("Remark1", account.getData().getAccountUserId().toString());
//		map.put("AuthorizeTypeOpen", "1,2,3");
		map.put("Agent", "app");
		map.put("PlaCustId", account.getData().getAccountNo());
		map.put("TxnTyp", txnTyp);
		map.put("userId", String.valueOf(userId));
		map.put("TransTyp", params.getString("TransTyp"));//1投资户 2融资户 (必送)
		Map<String, String> paramsMap = doubleDryService.authorize(map);
		// 手机端参数拼接
		StringBuffer sb = new StringBuffer();
		/*sb.append("MoneymoremoreId=" + paramsMap.get("MoneymoremoreId"));
		sb.append("&PlatformMoneymoremore="
				+ paramsMap.get("PlatformMoneymoremore"));
		sb.append("&AuthorizeTypeOpen=" + paramsMap.get("AuthorizeTypeOpen"));
		sb.append("&Remark1=" + paramsMap.get("Remark1"));
		sb.append("&ReturnURL=" + paramsMap.get("ReturnURL"));
		sb.append("&NotifyURL=" + paramsMap.get("NotifyURL"));
		sb.append("&SignInfo="
				+ URLEncoder.encode(paramsMap.get("SignInfo"), "UTF-8"));*/
		sb.append("Transid=").append(paramsMap.get("Transid"));
		sb.append("&NetLoanInfo=").append(paramsMap.get("NetLoanInfo"));
		resultMap.put("submitUrl", paramsMap.get("requestUrl"));
		resultMap.put("postData", sb.toString());
		return vo;
	}
	/**
	 * 授权信息
	 * @param params
	 * @return
	 */
	private JsonMobileVO authInformation(ParameterParser params){
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		Integer userId = params.getInt("userId");
		// 获取用户信息
		PlainResult<User> result = userService.findEntityById(userId);
		User user = result.getData();
		if (user == null) {
			vo.setResultCode("404");
			vo.setResultMessage("不存在的用户");
			return vo;
		}
		//授权类型 11、投标 59、缴费 60、还款
    	String authorizeInvestState="";
    	BigDecimal authorizeInvestAmount = null!=user.getAuthorizeInvestAmount()?user.getAuthorizeInvestAmount():BigDecimal.ZERO;
    	String authorizeFeeState="";
    	BigDecimal authorizeFeeAmount = null!=user.getAuthorizeFeeAmount()?user.getAuthorizeFeeAmount():BigDecimal.ZERO;
    	String authorizeRepayState="";
    	BigDecimal authorizeRepayAmount = null!=user.getAuthorizeRepayAmount()?user.getAuthorizeRepayAmount():BigDecimal.ZERO;
    	if("11".equals(user.getAuthorizeInvestType())){
    		authorizeInvestState=AuthorizeUtil.isAuthorize(user.getAuthorizeInvestStartDate(),user.getAuthorizeInvestEndDate());
    	}else{
    		authorizeInvestState="未授权";
    	}
    	if("59".equals(user.getAuthorizeFeeType())){
    		authorizeFeeState=AuthorizeUtil.isAuthorize(user.getAuthorizeFeeStartDate(),user.getAuthorizeFeeEndDate());
    	}else{
    		authorizeFeeState="未授权";
    	}
    	if("60".equals(user.getAuthorizeRepayType())){
    		authorizeRepayState=AuthorizeUtil.isAuthorize(user.getAuthorizeRepayStartDate(),user.getAuthorizeRepayEndDate());
    	}else{
    		authorizeRepayState="未授权";
    	}
    	if (user.getUserType() == null || user.getUserType().getType() == 1) {
			resultMap.put("authorizeInvestState", authorizeInvestState);
			resultMap.put("authorizeInvestAmount", authorizeInvestAmount.doubleValue());
		}
    	resultMap.put("authorizeFeeState", authorizeFeeState);
    	resultMap.put("authorizeFeeAmount", authorizeFeeAmount.doubleValue());
    	resultMap.put("authorizeRepayState", authorizeRepayState);
    	resultMap.put("authorizeRepayAmount", authorizeRepayAmount.doubleValue());
		return vo;
	}
	/**
	 * 开户
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private JsonMobileVO openAccount(ParameterParser params)
			throws UnsupportedEncodingException {
		// /mobile/accountManage.json?catalog=23&userId=233
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		Integer userId = params.getInt("userId");
		// 实名认证
//		BaseResult realNameResult = realnameAuthService.realNameAudit(userId);
//		if (!realNameResult.isSuccess()) {
//			vo.setResultCode("404");
//			vo.setResultMessage(realNameResult.getMessage());
//			return vo;
//		}
		// 开户
		PlainResult<User> result = userService.findEntityById(userId);
		Map<String, String> param = new HashMap<String, String>();
//		param.put("RegisterType", "2");
		if (result.getData().getUserType() == null
				|| result.getData().getUserType() == UserType.PERSONAL) {
//			param.put("AccountType", "");
//			param.put("IdentificationNo", result.getData().getUserDocNo());
//			param.put("RealName", result.getData().getUserRealName());
			param.put("MobileNo", result.getData().getUserPhone());
//			param.put("Email", result.getData().getUserEmail());
		} else {
			CompanyCustomerDO company = companyCustomerService.findByUserId(
					userId).getData();
//			param.put("AccountType", "1");
//			param.put("IdentificationNo", company.getCcLicenseNo());
//			param.put("RealName", company.getCcCompanyName());
			param.put("MobileNo", company.getCcContactPhone());
//			param.put("Email", company.getCcContactEmail());
		}
//		param.put("LoanPlatformAccount", result.getData().getUserName());
//		param.put("Remark1", result.getData().getUserId().toString());
		param.put("Agent", "app");
    	param.put("MerPriv", result.getData().getUserId().toString());
    	param.put("TransTyp", params.getString("TransTyp"));//1投资户 2融资户 (必送)
		PlainResult<Map> paramMap = doubledryservice.openAccent(param);
//		System.out.println("发送参数:======" + paramMap.getData());
		Map data = paramMap.getData();
		StringBuffer sb = new StringBuffer();
		sb.append("Transid=").append(data.get("Transid"));
		sb.append("&NetLoanInfo=").append(data.get("NetLoanInfo"));
		/*sb.append("RegisterType=" + data.get("RegisterType"));
		sb.append("&AccountType=" + data.get("AccountType"));
		sb.append("&Mobile=" + data.get("Mobile"));
		sb.append("&Email=" + data.get("Email"));
		sb.append("&RealName=" + data.get("RealName"));
		sb.append("&IdentificationNo=" + data.get("IdentificationNo"));
		sb.append("&Image1=" + MobileHelper.nullToEmpty(data.get("Image1")));
		sb.append("&Image2=" + MobileHelper.nullToEmpty(data.get("Image2")));
		sb.append("&LoanPlatformAccount=" + data.get("LoanPlatformAccount"));
		sb.append("&PlatformMoneymoremore=" + data.get("PlatformMoneymoremore"));
		sb.append("&RandomTimeStamp="
				+ MobileHelper.nullToEmpty(data.get("RandomTimeStamp")));
		sb.append("&Remark1=" + data.get("Remark1"));
		sb.append("&Remark2=" + MobileHelper.nullToEmpty(data.get("Remark2")));
		sb.append("&Remark3=" + MobileHelper.nullToEmpty(data.get("Remark3")));
		sb.append("&ReturnURL=" + data.get("ReturnURL"));
		sb.append("&NotifyURL=" + data.get("NotifyURL"));
		sb.append("&SignInfo="
				+ URLEncoder.encode(data.get("SignInfo").toString(), "UTF-8"));*/
		resultMap.put("submitUrl", data.get("requestUrl"));
		resultMap.put("postData", sb.toString());
		return vo;

	}
	/**
	 * 修改绑定银行卡
	 * @param params
	 * @return
	 */
	private JsonMobileVO modifyBindBankCard(ParameterParser params){
		JsonMobileVO vo = new JsonMobileVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		vo.setResult(resultMap);
		int userId = params.getInt("userId");
		UserDO userDO = userService.findById(userId).getData();
		if (userDO == null) {
			vo.setResultCode("200");
			vo.setResultMessage("不存在的用户");
			return vo;
		}
        AccountInfoDO account = accountInfoService.qureyAccountByUserIdAndUserType(userId,userDO.getUserType());
        if (null == account || StringUtils.isEmpty(account.getAccountNo())) {
			vo.setResultCode("404");
			vo.setResultMessage("您还未开户！");
			return vo;
		}
		Map<String,String> param= new LinkedHashMap<String,String>();
		param.put("Agent", "app");
    	param.put("PlaCustId", account.getAccountNo());   	
    	param.put("MerPriv", String.valueOf(userId));
    	param.put("TransTyp", params.getString("TransTyp"));//1投资户 2融资户 (必送)
    	PlainResult<Map> paramMap = doubledryservice.bindCard(param);
    	Map data = paramMap.getData();
		StringBuffer sb = new StringBuffer();
		sb.append("Transid=").append(data.get("Transid"));
		sb.append("&NetLoanInfo=").append(data.get("NetLoanInfo"));
		resultMap.put("submitUrl", data.get("requestUrl"));
		resultMap.put("postData", sb.toString());
		return vo;
	}
	/**
	 * 银行卡解绑
	 * 
	 * @param params
	 * @return
	 */
	private JsonMobileVO unBindBankCard(ParameterParser params) {
		// /mobile/accountManage.json?catalog=22&userId=224&bankId=114
		JsonMobileVO vo = new JsonMobileVO();
		int userId = params.getInt("userId");
		String bankid = params.getString("bankId");
		BaseResult cardresult = new BaseResult();
		cardresult.setSuccess(false);

		/* 验证删除的卡号是否为该用户的 */
		Boolean checkresult = false;
		if (bankid != null && !"".equals(bankid)) {
			PlainResult<BankInfoDO> checkUserResult = bankinfoservice
					.queryListBankInfoById(Integer.valueOf(bankid));
			if (checkUserResult.isSuccess()
					&& checkUserResult.getData() != null) {
				Integer carduserid = checkUserResult.getData().getBankUserId();
				if (carduserid != null) {
					if (carduserid.intValue() == userId)
						checkresult = true;
				}
			}
		}

		if (checkresult) {
			BankInfo bankinfo = new BankInfo();
			bankinfo.setBankId(Integer.valueOf(bankid));
			bankinfo.setCardStatus(CardStatus.STATE_DISABLE);
			cardresult = bankinfoservice.modifyBankInfo(bankinfo);
		} else {
			vo.setResultCode("201");
			vo.setResultMessage("解绑失败");
		}
		return vo;
	}

	/**
	 * 资金流水详细
	 * 
	 * @param params
	 * @return
	 * @throws ParseException
	 */
	private JsonMobileVO orderDetail(ParameterParser params)
			throws ParseException {
		JsonMobileVO mobileVo = new JsonMobileVO();
		Map<String, Object> objMap = new HashMap<String, Object>();
		mobileVo.setResult(objMap);
		String orderNo = (String) params.get("orderNo");
		String cashType = (String) params.get("cashType");

		// 双乾接口参数
		Map<String, String> map = new HashMap<String, String>();
		if (orderNo != null && !"".equals(orderNo)) {
			map.put("OrderNo", orderNo);
		}
		if ("SZMX".equalsIgnoreCase(cashType)) {
			map.put("Action", "");
		} else if ("CZJL".equalsIgnoreCase(cashType)) {
			map.put("Action", "1");
		} else if ("CZJL".equalsIgnoreCase(cashType)) {
			map.put("Action", "2");
		}
		@SuppressWarnings("rawtypes")
		PlainResult<Map> result = doubleDryService.balanceAccount(map);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (result.getData() != null && "SZMX".equals(cashType)) {
			String TransferTime = sdf2.format(sdf.parse((String) result
					.getData().get("TransferTime")));
			String ActTime = sdf2.format(sdf.parse((String) result.getData()
					.get("ActTime")));

			String SecondaryTime = "";
			if (result.getData().get("SecondaryTime") != null
					&& !result.getData().get("SecondaryTime").equals("")) {
				SecondaryTime = sdf2.format(sdf.parse((String) result.getData()
						.get("SecondaryTime")));
			}
			objMap.put("TransferTime", TransferTime);
			objMap.put("ActTime", ActTime);
			objMap.put("SecondaryTime", SecondaryTime);
		} else if (result.getData() != null && "CZJL".equals(cashType)) {
			Date rechargetime = sdf.parse((String) result.getData().get(
					"RechargeTime"));
			String RechargeTime = sdf2.format(rechargetime);
			objMap.put("RechargeTime", RechargeTime);
		} else if (result.getData() != null && "TXJL".equals(cashType)) {
			String WithdrawsTime = "";
			String PlatformAuditTime = "";
			String WithdrawsBackTime = "";
			WithdrawsTime = (String) result.getData().get("WithdrawsTime");
			PlatformAuditTime = (String) result.getData().get(
					"PlatformAuditTime");
			WithdrawsBackTime = (String) result.getData().get(
					"WithdrawsBackTime");
			if (WithdrawsTime != null && !"".equals(WithdrawsTime)) {
				WithdrawsTime = sdf2.format(sdf.parse(WithdrawsTime));
			}
			if (PlatformAuditTime != null && !"".equals(PlatformAuditTime)) {
				PlatformAuditTime = sdf2.format(sdf.parse(PlatformAuditTime));
			}
			if (WithdrawsBackTime != null && !"".equals(WithdrawsBackTime)) {
				WithdrawsBackTime = sdf2.format(sdf.parse(WithdrawsBackTime));
			}
			objMap.put("WithdrawsTime", WithdrawsTime);
			objMap.put("PlatformAuditTime", PlatformAuditTime);
			objMap.put("WithdrawsBackTime", WithdrawsBackTime);
		}
		objMap.put("data", result.getData());
		return mobileVo;
	}

	/**
	 * 资金流水 /mobile/accountManage.json?catalog=20&userId=156&cashType=czjl
	 * 
	 * @param context
	 * @param params
	 */
	private JsonMobileVO cashSerial(ParameterParser params) {
		JsonMobileVO mobileVo = new JsonMobileVO();
		Map<String, Object> objMap = new HashMap<String, Object>();
		mobileVo.setResult(objMap);
		String cashType = params.getString("cashType");// 必传字段
		Integer userId = params.getInt("userId");// 必传字段
		UserIdentity userIdentity = new UserIdentity();
		PlainResult<UserDO> uResult = userService.findById(userId);
		if (uResult.isSuccess()) {
			int userType = uResult.getData().getUserType();
			userIdentity.setUserType(UserType.valueOf(userType));
		}
		userIdentity.setUserId(userId);

		PlainResult<Account> account = accountInfoService
				.queryByUserId(userIdentity);

		int currentPage = params.getInt("showPage");
		int pageSize = params.getInt("pageSize");

		PageCondition pageCondition = new PageCondition(currentPage, pageSize);

		String accountNo = account.getData().getAccountNo();
		if (StringUtils.isEmpty(accountNo)) {
			objMap.put("pageCount", 0);
			objMap.put("list", new ArrayList(0));
			return mobileVo;
		}
		// 收支明细
		if (cashType.equalsIgnoreCase("SZMX")) {
			DealRecordDO dealrecorddo = new DealRecordDO();
			dealrecorddo.setDrPayAccount(accountNo);
			PageResult<DealRecordDO> result = dealRecordService
					.queryDealByParams(dealrecorddo, pageCondition, null, null);
			objMap.put("pageCount", result.getTotalCount());
			objMap.put("list", szmx(result.getData(), accountNo));
		}
		// 充值记录
		else if (cashType.equalsIgnoreCase("CZJL")) {
			RechargeRecordDO rechargerecorddo = new RechargeRecordDO();
			rechargerecorddo.setRechargeUserId(userId);
			//rechargerecorddo.setRechargeState(1); // 成功
			PageResult<RechargeRecordDO> result = rechargeService
					.queryRechargeRecordByparam(rechargerecorddo,
							pageCondition, null, null);
			objMap.put("pageCount", result.getTotalCount());
			objMap.put("list", czjl(result.getData()));
		}
		// 提现记录
		else if (cashType.equalsIgnoreCase("TXJL")) {
			TocashRecordDO tocashrecorddo = new TocashRecordDO();
			tocashrecorddo.setTocashUserId(userId);
	//		tocashrecorddo.setTocashState(1); // 提现成功
			PageResult<TocashRecordDO> result = toCashService
					.queryListByUserId(tocashrecorddo, pageCondition, null,
							null);
			objMap.put("pageCount", result.getTotalCount());
			objMap.put("list", txjl(result.getData()));
		} else {
			mobileVo.setSuccess(false);
			mobileVo.setMessage("没有找到cashType");
		}
		return mobileVo;
	}

	/**
	 * 提现记录
	 * 
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> txjl(List<TocashRecordDO> list) {
		List<Map<String, Object>> resultList = Lists.newArrayList();
		for (TocashRecordDO tocashrecord : list) {
			Map<String, Object> tempVo = Maps.newHashMap();
			tempVo.put("toCashDate",
					DateUtil.formatDate(tocashrecord.getTocashDate()));
			tempVo.put("toCashSeqNo", tocashrecord.getTocashSeqNo());
			tempVo.put("money", tocashrecord.getTocashAmount().toString());
			tempVo.put("type", "提现");
			switch (tocashrecord.getTocashState()) {
			case 0:
				tempVo.put("state", "进行中");
				break;
			case 1:
				tempVo.put("state", "成功");
				break;
			default:
				tempVo.put("state", "失败");
			}
			resultList.add(tempVo);
		}
		return resultList;
	}

	/**
	 * 充值记录
	 * 
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> czjl(List<RechargeRecordDO> list) {
		List<Map<String, Object>> resultList = Lists.newArrayList();
		for (RechargeRecordDO rechargerecord : list) {
			Map<String, Object> tempVo = Maps.newHashMap();
			tempVo.put("rechargeDate",
					DateUtil.formatDate(rechargerecord.getRechargeDate()));
			tempVo.put("orderNo", rechargerecord.getRechargeSeqNo());
			tempVo.put("money", rechargerecord.getRechargeAmount().toString());
			tempVo.put("type", "充值");
			switch (rechargerecord.getRechargeState()) {
			case 0:
				tempVo.put("state", "进行中");
				break;
			case 1:
				tempVo.put("state", "成功");
				break;
			default:
				tempVo.put("state", "失败");
			}
			resultList.add(tempVo);
		}
		return resultList;
	}

	/**
	 * 收支明细
	 * 
	 * @param list
	 * @param accountNo
	 * @return
	 */
	private List<Map<String, Object>> szmx(List<DealRecordDO> list,
			String accountNo) {
//		for (DealRecordDO dealRecordDO : list) {
//			if (dealRecordDO.getDrDetailType() == 11) { // 平台手续费
//				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo()
//						+ "333");
//			} else if (dealRecordDO.getDrDetailType() == 12) { // 担保服务费
//				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo()
//						+ "111");
//			} else if (dealRecordDO.getDrDetailType() == 14) { // 转让手续费
//				dealRecordDO.setDrInnerSeqNo(dealRecordDO.getDrInnerSeqNo()
//						+ "222");
//			}
//		}
		List<Map<String, Object>> resultList = Lists.newArrayList();
		for (DealRecordDO dealrecord : list) {
			Map<String, Object> tempVo = Maps.newHashMap();
			tempVo.put("operateDate",
					DateUtil.formatDate(dealrecord.getDrOperateDate()));
			// 交易订单号
			tempVo.put("orderNo", dealrecord.getDrInnerSeqNo());
			
			if(dealrecord.getDrPayAccount().equals(accountNo)){
				tempVo.put("money", "-"
						+ dealrecord.getDrMoneyAmount().toString());
			} else {
				tempVo.put("money", "+"+dealrecord.getDrMoneyAmount()
						.toString());
			}
			
			// 交易对方
			if ((dealrecord.getDrType() == 2 && accountNo.equals(dealrecord.getDrReceiveAccount()))
					|| (dealrecord.getDrType() == 3 && accountNo.equals(dealrecord.getDrReceiveAccount()))
					|| (dealrecord.getDrType()==10 && accountNo.equals(dealrecord.getDrReceiveAccount()))) {
				tempVo.put("receiveAccount", dealrecord.getDrPayAccount());
			} else if(dealrecord.getDrType()==9){
				//自主转账
				if(accountNo.equals(dealrecord.getDrPayAccount())){
					tempVo.put("receiveAccount", dealrecord.getDrReceiveAccount());
				} else{
             		tempVo.put("receiveAccount", dealrecord.getDrPayAccount());
             	}
			} else {
				tempVo.put("receiveAccount", dealrecord.getDrReceiveAccount());
			}
			// 交易类型
			dealrecord.createDrTypeStr(accountNo);
			tempVo.put("type", dealrecord.getDrTypeStr());
			// 状态
			switch (dealrecord.getDrState()) {
			case 0:
				tempVo.put("state", "进行中");
				break;
			case 1:
				tempVo.put("state", "成功");
				break;
			default:
				tempVo.put("state", "失败");
			}
			resultList.add(tempVo);
		}
		return resultList;
	}
}
