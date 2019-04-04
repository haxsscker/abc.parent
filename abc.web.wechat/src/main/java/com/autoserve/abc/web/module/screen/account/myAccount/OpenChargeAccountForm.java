package com.autoserve.abc.web.module.screen.account.myAccount;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class OpenChargeAccountForm {
//	private static Logger logger = LoggerFactory.getLogger(OpenAccount.class);
	
    @Autowired
    private UserService            userService;
    @Autowired
    private HttpSession            session;
    @Resource
    private HuifuPayService        huifuPayService;
    @Resource
    private CompanyCustomerService companyCustomerService;
    @Autowired
    private DoubleDryService doubledryservice;
    @Autowired
    private RealnameAuthService realnameAuthService;
	@Resource
	private AccountInfoService accountInfoService;
	@Resource
    private DoubleDryService doubleDryService;
	
    public void execute(Context context, ParameterParser params, Navigator nav) {
        User user = (User) session.getAttribute("user");
        PlainResult<User> result = userService.findEntityById(user.getUserId());
     // 获取用户信息
		if (result.getData().getUserBusinessState() == null
				|| result.getData().getUserBusinessState().getState() < 2) {
			context.put("txnTyp", "1");
		} else {
			UserIdentity userIdentity = new UserIdentity();
	    	if(user.getUserType()==null||user.getUserType()==UserType.PERSONAL){
	    		user.setUserType(UserType.PERSONAL);
	    	}else{
	    		user.setUserType(UserType.ENTERPRISE);
	    	}
	    	userIdentity.setUserType(user.getUserType());
	    	userIdentity.setUserId(user.getUserId());
	    	PlainResult<Account> account =  accountInfoService.queryByUserId(userIdentity);
	    	String RealNameFlg = null;
	    	String ChargeAmt = null;
	        if(account.isSuccess()&&user.getUserType().getType()==2){
	        	String accountUserAccount =account.getData().getAccountUserAccount();
	        	String mark=account.getData().getAccountMark();
	        	Map<String, String> chargeAccountMap =this.doubleDryService.queryChargeAccountResult(accountUserAccount,mark);
	        	RealNameFlg=chargeAccountMap.get("RealNameFlg");
	        	ChargeAmt=chargeAccountMap.get("ChargeAmt");
	        }
	        context.put("RealNameFlg",RealNameFlg);
	    	context.put("ChargeAmt",ChargeAmt);
	    	context.put("account", account.getData());
			context.put("txnTyp", "2");
		}
		    	
    	context.put("user", result.getData());
    }
}
