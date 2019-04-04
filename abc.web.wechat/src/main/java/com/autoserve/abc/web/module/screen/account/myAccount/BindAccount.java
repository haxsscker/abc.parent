package com.autoserve.abc.web.module.screen.account.myAccount;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.helper.DeployConfigService;

public class BindAccount {
   
	@Autowired
    private UserService         userservice;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
    private AccountInfoService accountInfoService;
	
	@Autowired
	private DeployConfigService deployConfigService;
	
	@Resource
	private CompanyCustomerService companyCustomerService;
	
	@Resource
	private HttpServletRequest request;

    public void execute(Context context, ParameterParser params,Navigator nav) {
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.redirectToLocation(deployConfigService.getLoginUrl(request));
    		return;
    	}  
    	user = userservice.findEntityById(user.getUserId()).getData();
    	
    	UserIdentity userIdentity =new UserIdentity();
    	userIdentity.setUserId(user.getUserId());
    	if(user.getUserType()==null || user.getUserType().getType()==1){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    		PlainResult<CompanyCustomerDO> companyDO = companyCustomerService.findByUserId(user.getUserId());
    		context.put("company", companyDO.getData());
    	}
    	userIdentity.setUserType(user.getUserType());	
    	PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
    	
    	String MoneyMoreMoreUrl = SystemGetPropeties.getStrString("submiturlprefix");
    	context.put("MoneyMoreMoreUrl", MoneyMoreMoreUrl);
    	
    	context.put("user", user);
    	context.put("account", account.getData());
    }
}
