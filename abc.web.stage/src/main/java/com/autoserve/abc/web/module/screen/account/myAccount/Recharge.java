package com.autoserve.abc.web.module.screen.account.myAccount;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;

public class Recharge {
	@Autowired
    private HttpSession session;
	@Autowired
	private DeployConfigService deployConfigService;
	@Resource
	private HttpServletRequest request;
	@Autowired
    private UserService         userservice;
	@Autowired
    private AccountInfoService accountInfoService;
	@Resource
    private DoubleDryService doubleDryService;

    public void execute(Context context, ParameterParser params,Navigator nav) {
    	String tab_name = params.getString("tab_name", "KJCZ");
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.redirectToLocation(deployConfigService.getLoginUrl(request));
    		return;
    	}  
    	PlainResult<User> result= userservice.findEntityById(user.getUserId());
    	user = result.getData();
    	session.setAttribute("user", user);
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
   		Double[] accountBacance = { 0.00, 0.00, 0.00, 0.00, 0.00, 0.00};
	    //网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
	    if (accountNo != null && !"".equals(accountNo)) {
            accountBacance = this.doubleDryService.queryBalanceDetail(accountNo);
        }		
	    context.put("accountBacance", accountBacance);
	    context.put("user", user);
	    context.put("tab_name", tab_name);
	    context.put("accountNo1", accountNo1);
	    context.put("accountNo2", accountNo2);
    }
}
