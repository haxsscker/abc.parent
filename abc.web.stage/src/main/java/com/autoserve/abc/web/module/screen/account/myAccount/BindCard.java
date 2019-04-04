package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.EasyPayUtils;
import com.autoserve.abc.web.helper.DeployConfigService;

public class BindCard {
	@Autowired
    private UserService         userservice;
	@Autowired
    private HttpSession session;
	@Autowired
    private DoubleDryService doubledryservice;
	@Resource
	private HttpServletRequest request;
	@Autowired
	private DeployConfigService deployConfigService;
	@Autowired
    private AccountInfoService    accountInfoService;
	
	public void execute(Context context, ParameterParser params,Navigator nav) {
		Map notifyMap = EasyPayUtils.transformRequestMap(request.getParameterMap());
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request));
    		return;
    	}
    	PlainResult<User> result= userservice.findEntityById(user.getUserId());
    	User userResult=result.getData();
    	
    	UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(user.getUserId());
        if (user.getUserType() == null || user.getUserType().getType() == 1) {
            user.setUserType(UserType.PERSONAL);
        } else {
            user.setUserType(UserType.ENTERPRISE);
        }
        userIdentity.setUserType(user.getUserType());
        PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
        String accountNo = account.getData().getAccountNo();
   
    	session.setAttribute("user", userResult);
    	Map<String,String> param= new LinkedHashMap<String,String>();
    	param.put("PlaCustId", accountNo);  
    	param.put("TransTyp", params.getString("TransTyp"));  
    	param.put("MerPriv", result.getData().getUserId().toString());
    	param.put("Agent", "pc"); 
    	PlainResult<Map> paramMap = doubledryservice.bindCard(param);
    	System.out.println("发送参数:======"+paramMap.getData());
    	context.put("SubmitURL", paramMap.getData().remove("requestUrl"));
    	context.put("paramMap", paramMap);
	}

}
