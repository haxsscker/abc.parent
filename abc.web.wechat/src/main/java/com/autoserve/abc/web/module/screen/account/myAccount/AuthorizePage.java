package com.autoserve.abc.web.module.screen.account.myAccount;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.AuthorizeUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class AuthorizePage {
    private final static Logger logger = LoggerFactory.getLogger(AuthorizePage.class); //加入日志
    @Autowired
    private HttpSession         session;
    @Autowired
    private UserService         userservice;
    @Autowired
    private AccountInfoService  accountInfoService;

    public void execute(Context context, ParameterParser params) {

        logger.info("into Authorize execute");

        User user = (User) session.getAttribute("user");

        PlainResult<User> result = userservice.findEntityById(user.getUserId());
        user = result.getData();
        session.setAttribute("user", result.getData());
        UserIdentity userIdentity =new UserIdentity();
    	userIdentity.setUserId(user.getUserId());
    	if(user.getUserType()==null || user.getUserType().getType()==1){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());	

    	
    	//投资账户
    	userIdentity.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
    	PlainResult<Account> account1 = accountInfoService.queryByUserId(userIdentity);
    	//String accountNo1 = account1.getData().getAccountNo();
    	//融资账户
    	userIdentity.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
    	PlainResult<Account> account2 = accountInfoService.queryByUserId(userIdentity);
    	context.put("account1", account1.getData());
    	context.put("account2", account2.getData());
      //投标授权类型 11、投标 59、缴费 60、还款
    	String authorizeInvest="";
    	String authorizeFee="";
    	String authorizeRepay="";
    	if("11".equals(user.getAuthorizeInvestType())){
    		authorizeInvest=AuthorizeUtil.isAuthorize(user.getAuthorizeInvestStartDate(),user.getAuthorizeInvestEndDate());
    	}
    	if("59".equals(user.getAuthorizeFeeType())){
    		authorizeFee=AuthorizeUtil.isAuthorize(user.getAuthorizeFeeStartDate(),user.getAuthorizeFeeEndDate());
    	}
    	if("60".equals(user.getAuthorizeRepayType())){
    		authorizeRepay=AuthorizeUtil.isAuthorize(user.getAuthorizeRepayStartDate(),user.getAuthorizeRepayEndDate());
    	}
    	context.put("authorizeInvest",authorizeInvest);
    	context.put("authorizeFee", authorizeFee);
    	context.put("authorizeRepay",authorizeRepay);
    	context.put("user", user);
    }

}
