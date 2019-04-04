package com.autoserve.abc.web.module.screen.account.myAccount;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.helper.DeployConfigService;

public class WithdrawCash {
	@Autowired
	private HttpSession session;
	@Autowired
	private UserService userservice;
	@Autowired
	private AccountInfoService accountInfoService;
	@Resource
	private DoubleDryService doubleDryService;
	@Resource
	private BankInfoService bankinfoservice;
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private ToCashService toCashService;
	@Autowired
	private PaymentPlanService paymentPlanService;
	@Autowired
	private DeployConfigService deployConfigService;
	@Resource
	private HttpServletRequest request;

	public void execute(Context context, ParameterParser params, Navigator nav) {
		//String Type = params.getString("Type");
		User user = (User) session.getAttribute("user");
		if(user==null){
			nav.redirectToLocation(deployConfigService.getLoginUrl(request));
			return;
		}  
		PlainResult<User> result = userservice.findEntityById(user.getUserId());
		session.setAttribute("user", result.getData());
		String accountNo1 = "";//投资账户
    	String accountNo2 = "";//融资账户
    	UserIdentity userIdentity =new UserIdentity();
    	userIdentity.setUserId(user.getUserId());
    	userIdentity.setUserType(user.getUserType());
    	if(UserType.PERSONAL.type == user.getUserType().getType()){
    		user.setUserType(UserType.PERSONAL);
    		userIdentity.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
    		PlainResult<Account> account1 = accountInfoService.queryByUserId(userIdentity);
    		if(account1.isSuccess()){
    			accountNo1 = account1.getData().getAccountNo();
    		}
    	}
    	userIdentity.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
    	PlainResult<Account> account2 = accountInfoService.queryByUserId(userIdentity);
    	if(account2.isSuccess()){
			accountNo2 = account2.getData().getAccountNo();
		}
	    //投资户与融资户账号一样
   		String accountNo = StringUtil.isEmpty(accountNo1)?accountNo2:accountNo1;

		// 网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
   		Double[] accountBacance = { 0.00, 0.00, 0.00, 0.00, 0.00, 0.00 };
   		if (accountNo != null && !"".equals(accountNo)) {
   			accountBacance = this.doubleDryService.queryBalanceDetail(accountNo);
        }
   		
		// 查询用户提现卡号（后期完善）
		BankInfo bankInfo = new BankInfo();
		bankInfo.setBankUserId(user.getUserId());
		bankInfo.setBankUserType(user.getUserType().getType());
		bankInfo.setCardStatus(CardStatus.STATE_ENABLE);
		//List<BankInfoDO> banklist = bankinfoservice.findBankInfo(bankInfo);
		// 免费提现额度
		BigDecimal cashQuota = result.getData().getUserCashQuota();
		if (cashQuota == null) {
			cashQuota = new BigDecimal(0);
		}
		// 本月的免费提现机会剩余次数
		int monthtimes = 0;
		PlainResult<SysConfig> payCapitalResult = sysConfigService
				.querySysConfig(SysConfigEntry.WAIT_PAY_CAPITAL);
		if (!payCapitalResult.isSuccess()) {
			context.put("Message", "借款人使用免费提现次数的限制，待还本金的上限查询失败");
			nav.forwardTo("/error").end();
		}
		PlainResult<SysConfig> monthfreeTocashTimesResult = sysConfigService
				.querySysConfig(SysConfigEntry.MONTHFREE_TOCASH_TIMES);
		if (!monthfreeTocashTimesResult.isSuccess()) {
			context.put("Message", "每个用户每月免费提现次数查询失败");
			nav.forwardTo("/error").end();
		}
	 	PlainResult<SysConfig> withdrawrate = sysConfigService
					.querySysConfig(SysConfigEntry.WITHDRAW_RATE);
		if (!withdrawrate.isSuccess()) {
			context.put("Message", "用户提现手续费费率查询失败");
			nav.forwardTo("/error").end();
		}
		// 用户的待还本金
		PlainResult<BigDecimal> waitPayCapital = paymentPlanService
				.queryWaitPayCapital(user.getUserId());

		// 查询用户本月的提现次数
		PlainResult<Integer> resultx = toCashService
				.countTocashCurrentMonth(user.getUserId());
		if (!resultx.isSuccess()) {
			context.put("Message", resultx.getMessage());
			nav.forwardTo("/error").end();
		}
		if (resultx.getData() < Integer.parseInt(monthfreeTocashTimesResult
				.getData().getConfValue())
				&& waitPayCapital.getData().compareTo(
						new BigDecimal(payCapitalResult.getData()
								.getConfValue())) < 0) {
			monthtimes = Integer.parseInt(monthfreeTocashTimesResult.getData()
					.getConfValue()) - resultx.getData();
		}
		//context.put("Type", Type);
		//context.put("banklist", banklist);
		//context.put("banksize", banklist.size());
		//context.put("bank", banklist.get(0));
		context.put("accountBacance", accountBacance);
		context.put("cashQuota", cashQuota);
		context.put("monthtimes", monthtimes);
		//每個用戶每月免費提現次數
		context.put("usermonthtimes", monthfreeTocashTimesResult.getData()
				.getConfValue());
		context.put("user", user);
		context.put("accountNo1", accountNo1);
	    context.put("accountNo2", accountNo2);
	    context.put("withdrawFeeRate", withdrawrate.getData().getConfValue());
	}

}
