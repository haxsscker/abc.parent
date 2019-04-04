package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class PhoneEditInformation {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private UserService userservice;
	@Autowired
	private AccountInfoService accountInfoService;
	@Autowired
    private DoubleDryService doubledryservice;
	
	
	
	public void execute(Context context, ParameterParser params) {
    	User user=(User)session.getAttribute("user");
    	user = userservice.findEntityById(user.getUserId()).getData();
    	
    	UserIdentity userIdentity = new UserIdentity();
    	if(user.getUserType()==null||user.getUserType()==UserType.PERSONAL){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());
    	userIdentity.setUserId(user.getUserId());
    	PlainResult<Account> account =  accountInfoService.queryByUserId(userIdentity) ;
    	
    	String phone = params.getString("userPhone");
    	if(phone!=null&&!"".equals(phone)&&account!=null){
    		String accountNo = account.getData().getAccountNo();
	        Map<String, String> param = new LinkedHashMap <String, String> ();
	        param.put("PlaCustId", accountNo);
	        param.put("MobileNo", phone);
	        param.put("TransTyp", params.getString("TransTyp"));
	        param.put("MerPriv", String.valueOf(user.getUserId()));
	        PlainResult<Map> paramMap =doubledryservice.changPhone(param);
	    	context.put("SubmitURL", paramMap.getData().remove("requestUrl"));
	    	context.put("paramMap", paramMap);
    	
    	}
    }

}
