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
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.EasyPayUtils;
import com.autoserve.abc.web.helper.DeployConfigService;

public class OpenAccount {
	
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
	private RealnameAuthService realnameAuthService;
    
	@Resource
	private CompanyCustomerService companyCustomerService;
	
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
    	Map<String,String> param= new LinkedHashMap<String,String>();
    	param.put("source", (String)notifyMap.get("source"));//开户操作的来源，区分注册与普通开户
		//param.put("UsrName", (String)notifyMap.get("UsrName"));
		//param.put("IdentNo", (String)notifyMap.get("IdentNo"));
		param.put("MobileNo", (String)notifyMap.get("MobileNo"));
		param.put("TransTyp", (String)notifyMap.get("TransTyp"));
		//param.put("OpenBankId", (String)notifyMap.get("OpenBankId"));
    	//param.put("OpenAcctId", (String)notifyMap.get("OpenAcctId"));    	
    	param.put("MerPriv", result.getData().getUserId().toString());
    	PlainResult<Map> paramMap = doubledryservice.openAccent(param);
//    	System.out.println("发送参数:======"+paramMap.getData());
    	context.put("SubmitURL", paramMap.getData().remove("requestUrl"));
    	context.put("paramMap", paramMap);
    }
}
