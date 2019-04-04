package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.result.CommonResultCode;

public class Authorize {
	@Autowired
    private HttpSession session;
	@Autowired
    private AccountInfoService accountInfoService;
	@Resource
    private DoubleDryService doubleDryService;

    public void execute(Context context, Navigator nav,ParameterParser params) {
    	User user=(User)session.getAttribute("user");
    	/*UserIdentity userIdentity =new UserIdentity();
    	userIdentity.setUserId(user.getUserId());
    	if(user.getUserType()==null || user.getUserType().getType()==1){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());	
    	PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);*/
    	String PlaCustId="";
    	UserDO userDO = new UserDO();
    	userDO.setUserId(user.getUserId());
    	userDO.setUserType(null==user.getUserType()?UserType.PERSONAL.getType():user.getUserType().getType());
    	userDO.setAccountCategory(AccountCategory.INVESTACCOUNT.type);
    	AccountInfoDO accountInfoDO=accountInfoService.getAccountByCategory(userDO);
    	userDO.setAccountCategory(AccountCategory.LOANACCOUNT.type);
    	AccountInfoDO accountInfoDO1=accountInfoService.getAccountByCategory(userDO);
    	if (null!=accountInfoDO && StringUtils.isNotEmpty(accountInfoDO.getAccountNo())) {
    		PlaCustId=accountInfoDO.getAccountNo();
    	}else if(null!=accountInfoDO1 && StringUtils.isNotEmpty(accountInfoDO1.getAccountNo())) {
    		PlaCustId=accountInfoDO1.getAccountNo();
    	}else{
    		context.put("ResultCode", "fail");
         	context.put("Message", CommonResultCode.NO_INVEST_LOAN_ACCOUNT.message);
         	nav.forwardTo("/error").end(); 
         	return;
    	}
    	 Map<String, String> map = new LinkedHashMap<String, String>();
    	 map.put("PlaCustId", PlaCustId);
    	 map.put("TxnTyp",params.getString("TxnTyp"));//1、授权 2、解授权
    	 map.put("userId",String.valueOf(user.getUserId()));
    	 map.put("TransTyp",params.getString("TransTyp"));//1、投资户 2、融资户
    	 map.put("source", params.getString("source"));//授权 操作的来源
    	 Map<String, String> paramMap =  doubleDryService.authorize(map);
    	 context.put("SubmitURL", paramMap.remove("requestUrl"));
    	 context.put("paramMap", paramMap);
    }
}