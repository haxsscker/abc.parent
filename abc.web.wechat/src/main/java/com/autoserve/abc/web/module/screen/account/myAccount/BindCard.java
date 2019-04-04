package com.autoserve.abc.web.module.screen.account.myAccount;

import java.net.URLDecoder;
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
    	PlainResult<User> result = userservice.findEntityById(user.getUserId());
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
    	param.put("Agent", "wx");
    	param.put("MerPriv", result.getData().getUserId().toString());
    	param.put("TransTyp", params.getString("TransTyp"));
    	PlainResult<Map> paramMap = doubledryservice.bindCard(param);
    	//微信端通过页面get方式请求会把参数再次编码，所以这边先解码，避免2次编码，app端就不用解码了
    	String NetLoanInfo = URLDecoder.decode((String) paramMap.getData().get("NetLoanInfo"));
//    	logger.info(NetLoanInfo);
    	paramMap.getData().put("NetLoanInfo",NetLoanInfo);
    	System.out.println("发送参数:======"+paramMap.getData());
    	context.put("SubmitURL", paramMap.getData().remove("requestUrl"));
    	context.put("paramMap", paramMap);
	}

}
