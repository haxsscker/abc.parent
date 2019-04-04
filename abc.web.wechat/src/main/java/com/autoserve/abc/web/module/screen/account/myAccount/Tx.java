package com.autoserve.abc.web.module.screen.account.myAccount;

import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;

public class Tx {
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

	public void execute(Context context, ParameterParser params, Navigator nav) {
		User user = (User) session.getAttribute("user");
		PlainResult<User> result = userservice.findEntityById(user.getUserId());
		session.setAttribute("user", result.getData());
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
		String accountMark = account.getData().getAccountMark();

		// 网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
		Double[] accountBacance = this.doubleDryService.queryBalance(
				accountMark, "1");

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
			context.put("Message", "免费提现次数查询失败");
			nav.forwardTo("/error").end();
		}

		// 查询用户本月的提现次数
		PlainResult<Integer> resultx = toCashService
				.countTocashCurrentMonth(user.getUserId());
		if (!resultx.isSuccess()) {
			context.put("Message", resultx.getMessage());
			nav.forwardTo("/error").end();
		}
		// 用户的待还本金
		PlainResult<BigDecimal> waitPayCapital = paymentPlanService
				.queryWaitPayCapital(user.getUserId());
		PlainResult<SysConfig> payCapitalResult = sysConfigService.querySysConfig(SysConfigEntry.WAIT_PAY_CAPITAL);
        if (!payCapitalResult.isSuccess()) {
           context.put("Message","待还本金的上限查询失败");
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

		context.put("accountBacance", accountBacance);
		context.put("cashQuota", cashQuota);
		context.put("monthtimes", monthtimes);
		context.put("user", user);

		String MoneyMoreMoreUrl = SystemGetPropeties
				.getStrString("submiturlprefix");
		context.put("MoneyMoreMoreUrl", MoneyMoreMoreUrl);
	}
}
