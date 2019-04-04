package com.autoserve.abc.web.module.screen.account.myAccount;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.autoserve.abc.service.biz.result.PlainResult;

public class Authorize {
	@Autowired
    private HttpSession session;
	@Autowired
    private AccountInfoService accountInfoService;
	@Resource
    private DoubleDryService doubleDryService;

    public void execute(Context context, Navigator nav, ParameterParser params) {
    	User user=(User)session.getAttribute("user");
    	UserIdentity userIdentity =new UserIdentity();
    	userIdentity.setUserId(user.getUserId());
    	if(user.getUserType()==null || user.getUserType().getType()==1){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());	
    	 PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
    	 if(null == account || StringUtils.isEmpty(account.getData().getAccountNo())){
    		context.put("ResultCode", "fail");
         	context.put("Message", "该用户未开户！");
         	nav.forwardTo("/error").end(); 
         	return;
    	 }
    	 Map<String, String> map = new LinkedHashMap<String, String>();
    	 map.put("Agent", "wx");
    	 map.put("PlaCustId", account.getData().getAccountNo());
    	 map.put("TxnTyp",params.getString("TxnTyp"));//1、授权 2、解授权
    	 map.put("TransTyp",params.getString("TransTyp"));//1、投资账户 2、融资账户
    	 map.put("userId",String.valueOf(user.getUserId()));
    	 Map<String, String> paramMap =  doubleDryService.authorize(map);
    	 //微信端通过页面get方式请求会把参数再次编码，所以这边先解码，避免2次编码，app端就不用解码了
     	 String NetLoanInfo = URLDecoder.decode(paramMap.get("NetLoanInfo"));
     	 paramMap.put("NetLoanInfo",NetLoanInfo);
    	 context.put("SubmitURL", paramMap.remove("requestUrl"));
    	 context.put("paramMap", paramMap);
    }
}