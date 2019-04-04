package com.autoserve.abc.web.module.screen.account.myAccount;

import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
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
    	String phone = user.getUserPhone();
    	String newPhone = params.getString("userPhone");
    	if(phone!=null&&!"".equals(phone)&&account!=null){
    		String accountNo = account.getData().getAccountNo();
	        Map<String, String> param = new LinkedHashMap <String, String> ();
	        param.put("PlaCustId", accountNo);
	        param.put("MobileNo", phone);
	        param.put("NewMobileNo", newPhone);
	        param.put("UsrName", user.getUserRealName());
	        param.put("IdentType", "00");
	        param.put("IdentNo", user.getUserDocNo());
	        param.put("Agent", "wx");
	        param.put("MerPriv", String.valueOf(user.getUserId()));
	        param.put("TransTyp", String.valueOf(account.getData().getAccountCategory()));
	        PlainResult<Map> paramMap =doubledryservice.changPhone(param);
	      //微信端通过页面get方式请求会把参数再次编码，所以这边先解码，避免2次编码，app端就不用解码了
	    	String NetLoanInfo = URLDecoder.decode((String) paramMap.getData().get("NetLoanInfo"));
//	    	logger.info(NetLoanInfo);
	    	paramMap.getData().put("NetLoanInfo",NetLoanInfo);
	    	context.put("SubmitURL", paramMap.getData().remove("requestUrl"));
	    	context.put("paramMap", paramMap);
    	
    	}
    }

}
