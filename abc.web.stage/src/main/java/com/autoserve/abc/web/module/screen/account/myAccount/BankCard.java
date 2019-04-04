package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;

public class BankCard {
	@Autowired
	private HttpSession session;
	@Autowired
	private UserService userservice;
	@Autowired
	private AccountInfoService accountInfoService;
	@Resource
	private BankInfoService bankinfoservice;
	@Resource
    private DoubleDryService      doubleDryService;

	public void execute(Context context, Navigator nav) {

		User user = (User) session.getAttribute("user");
		PlainResult<User> result = userservice.findEntityById(user.getUserId());
		session.setAttribute("user", result.getData());
//		UserIdentity userIdentity = new UserIdentity();
//		userIdentity.setUserId(user.getUserId());
//		if (user.getUserType() == null || user.getUserType().getType() == 1) {
//			user.setUserType(UserType.PERSONAL);
//		} else {
//			user.setUserType(UserType.ENTERPRISE);
//		}
//		userIdentity.setUserType(user.getUserType());

		// 查询用户提现卡号（后期完善）
//		BankInfo bankInfo = new BankInfo();
//		bankInfo.setBankUserId(user.getUserId());
//		bankInfo.setBankUserType(user.getUserType().getType());
//		bankInfo.setCardStatus(CardStatus.STATE_ENABLE);
//		List<BankInfoDO> banklist = bankinfoservice.findBankInfo(bankInfo);
		String userPhone=user.getUserPhone();
		//投资户
//		userIdentity.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
//		PlainResult<Account> account1 = accountInfoService.queryByUserId(userIdentity);		
//		String accountNo1 = account1.getData().getAccountNo(); 
//		
//		//融资户
//		userIdentity.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
//		PlainResult<Account> account2 = accountInfoService.queryByUserId(userIdentity);
//		String accountNo2 = account2.getData().getAccountNo(); 
		List<BankInfoDO> banklist = new ArrayList<BankInfoDO>();
//		List<BankInfoDO> banklist2 = new ArrayList<BankInfoDO>();
		//投资户与融资户账号一样
		AccountInfoDO account = accountInfoService.qureyAccountByUserIdAndUserType(user.getUserId(),user.getUserType().getType());
		if(account!=null&&account.getAccountNo()!=null){
			banklist = doubleDryService.queryCard(account.getAccountNo(), userPhone);
		}
//		if(account2!=null&&account2.getData().getAccountNo()!=null){
//			banklist2 = doubleDryService.queryCard(accountNo2, userPhone);
//		}
//		banklist.addAll(banklist2);
		context.put("banklist", banklist);
		context.put("banksize", banklist.size());
		context.put("user", user);
//
//		String MoneyMoreMoreUrl = SystemGetPropeties
//				.getStrString("submiturlprefix");
//		context.put("MoneyMoreMoreUrl", MoneyMoreMoreUrl);
	
	}
}
