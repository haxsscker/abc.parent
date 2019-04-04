package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;
import com.autoserve.abc.dao.dataobject.IncomePlanDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.CashInvesterService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.plan.IncomePlanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.helper.DeployConfigService;

public class Treasure {
	private final static Logger logger = LoggerFactory
			.getLogger(Treasure.class); // 加入日志

	@Autowired
	private HttpSession session;
	@Resource
	private UserService userService;
	@Autowired
	private AccountInfoService accountInfoService;
	@Resource
	private HttpServletRequest request;
	@Resource
	private DeployConfigService deployConfigService;
	@Resource
	private HuifuPayService huiFuPayServcice;
	@Resource
	private IncomePlanService incomePlanService;

	@Resource
	private InvestQueryService investQueryService;
	@Resource
	private DoubleDryService doubleDryService;
	@Resource
    private BankInfoService bankInfoService;
	@Autowired
    private CashInvesterService cashInvesterService;
	
	public void execute(Context context, ParameterParser params, Navigator nav) {

		User user = (User) session.getAttribute("user");

		if (user == null) {
			nav.redirectToLocation(deployConfigService.getLoginUrl(request));
			return;
		}
		IncomePlanDO incomePlanDO = new IncomePlanDO();
		// 待收益
		incomePlanDO.setPiIncomePlanState(0);
		incomePlanDO.setPiBeneficiary(user.getUserId());
		PlainResult<IncomePlanDO> daResult = incomePlanService
				.findSumPlanIncome(incomePlanDO);
		if (daResult.getData() == null) {
			context.put("dueMoney", 0); // 待收总金额
			context.put("dueInterest", 0); // 待收利息
			context.put("dueCapital", 0); // 待收本金
			context.put("dueFine", 0); // 待收本金
		} else {
			context.put("dueMoney", daResult.getData().getPiPayTotalMoney()); // 待收总金额
			context.put("dueInterest", daResult.getData().getPiPayInterest()); // 待收利息
			context.put("dueCapital", daResult.getData().getPiPayCapital()); // 待收本金
			context.put("dueFine", daResult.getData().getPiPayFine()); // 待收本金
		}

		// 已收益
		incomePlanDO = new IncomePlanDO();
		incomePlanDO.setPiIncomePlanState(2);
		incomePlanDO.setPiBeneficiary(user.getUserId());
		PlainResult<IncomePlanDO> daResult1 = incomePlanService
				.findSumPlanIncome(incomePlanDO);
		if (daResult1.getData() == null) {
			context.put("alCapital", 0); // 已收本金
			context.put("alMoney", 0); // 已收本金
			context.put("alInterest", 0); // 已收利息
			context.put("allEarnings", 0); // 已收利息
		} else {
			context.put("alCapital", daResult1.getData().getPiCollectCapital()); // 已收本金
			context.put("alMoney", daResult1.getData().getPiCollectTotal()
					.subtract(daResult1.getData().getPiCollectCapital())); // 已收本金
			context.put("alInterest", daResult1.getData()
					.getPiCollectInterest()); // 已收利息
			context.put("allEarnings", daResult1.getData().getPiCollectTotal()
					.subtract(daResult1.getData().getPiCollectCapital())); // 已收利息
		}
		incomePlanDO = new IncomePlanDO();
		incomePlanDO.setPiBeneficiary(user.getUserId());
		Date dt = new Date();
		incomePlanDO.setPiPaytime(dt);
		// 今日
		PlainResult<IncomePlanDO> daResult2 = incomePlanService
				.findSumPlanIncome(incomePlanDO);
		if (daResult2.getData() == null) {
			context.put("dayMoney", 0); // 待收总金额
		} else {
			context.put("dayMoney", daResult2.getData().getPiPayTotalMoney());// 今日待收
		}
		UserDO userDO = userService.findById(user.getUserId()).getData();
		context.put("userDO", userDO);
		if (userDO.getUserType() == null || userDO.getUserType() == 1) {
			userDO.setUserType(UserType.PERSONAL.getType());
			//投资账户
			UserIdentity userIdentity = new UserIdentity();
	        userIdentity.setUserId(user.getUserId());
	        userIdentity.setUserType(user.getUserType());
	    	userIdentity.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
	    	PlainResult<Account> account1 = accountInfoService.queryByUserId(userIdentity);
	    	if(account1.isSuccess()){
	    		PlainResult<CashInvesterViewDO> plainResult = cashInvesterService.queryCashInvester(user.getUserId());
				context.put("investMoney", plainResult.getData().getInValidInvestMoney());
	    	}
			
		} else {
			userDO.setUserType(UserType.ENTERPRISE.getType());
		}
		// 帐号金额信息
		UserIdentity userIdentity = new UserIdentity();
		userIdentity.setUserId(userDO.getUserId());
		userIdentity.setUserType(UserType.valueOf(userDO.getUserType()));

		if (null != userDO.getUserDocNo() && null != userDO.getUserRealName()) {
			context.put("isRealName", "1");
		} else {
			context.put("isRealName", "0");
			// return result;
		}

		PlainResult<Account> accountResult = accountInfoService
				.queryByUserId(userIdentity);
		if (!accountResult.isSuccess()) {
			/*
			 * result.setResultCode("201");
			 * result.setResultMessage("开户账号查询失败，请先开户"); return result;
			 */
		}

		String accountNo = accountResult.getData().getAccountNo();
		Double[] accountBalance = { 0.00, 0.00, 0.00 };
		if (StringUtils.isNotEmpty(accountNo)) {
			accountBalance = this.doubleDryService.queryBalance(accountNo,
					"1");
		}
		context.put("avlMoney", accountBalance[1]);
		Invest invest = new Invest();
		invest.setUserId(user.getUserId());
		PlainResult<Invest> resultInvest = investQueryService
				.queryByInvest(invest);
		
		//投资统计、待收汇总
        
		context.put("investCount", resultInvest.getData().getUserId());
		
		
		ListResult<BankInfoDO> banklist = bankInfoService.queryListBankInfo(user.getUserId());
		context.put("bankSize", banklist.getData().size());
		context.put("menu", "treasure");

	}
}
