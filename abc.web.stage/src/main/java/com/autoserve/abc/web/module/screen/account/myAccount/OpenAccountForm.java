package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.BankMappingDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankMappingService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.EasyPayUtils;
import com.autoserve.abc.web.helper.DeployConfigService;

public class OpenAccountForm {
	
	@Autowired
	private DeployConfigService deployConfigService;
	
	@Autowired
    private UserService         userservice;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
    private DoubleDryService doubledryservice;
	
	@Resource
	private HttpServletRequest request;
	
	@Resource
	private BankMappingService bankMappingService;
	@Autowired
    private AccountInfoService accountInfoService;
    
	
	public void execute(Context context, ParameterParser params,Navigator nav) {
		Map notifyMap = EasyPayUtils.transformRequestMap(request.getParameterMap());
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request));
    		return;
    	}
    	PlainResult<User> result= userservice.findEntityById(user.getUserId());
    	User userResult=result.getData();
    	
    	session.setAttribute("user", userResult);
    	
        List<BankMappingDO> paramList = bankMappingService.findBankMapping();
        if(userResult.getUserType().getType()==1){
        	String MobileNo=request.getParameter("MobileNo");
        	if(StringUtil.isEmpty(MobileNo)){
        		MobileNo=userResult.getUserPhone();
        	}
        	nav.redirectToLocation("/account/myAccount/openAccount?MobileNo="+MobileNo+"&TransTyp="+notifyMap.get("TransTyp"));
        }
        Account account = new  Account();
        String TxnTyp = (String)notifyMap.get("TxnTyp");// 1-新开 2-修改（修改户名和清算行号）
        if("2".equals(TxnTyp)){
        	UserIdentity userIdentity =new UserIdentity();
        	userIdentity.setUserId(user.getUserId());
        	userIdentity.setUserType(user.getUserType());	
        	PlainResult<Account> accountResult = accountInfoService.queryByUserId(userIdentity);
        	account = accountResult.getData();
        }
        
    	context.put("paramList", paramList);
    	context.put("user", userResult);
    	context.put("TxnTyp", TxnTyp);
    	context.put("account", account);
    }
}
