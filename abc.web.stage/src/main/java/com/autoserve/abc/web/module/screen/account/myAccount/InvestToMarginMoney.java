package com.autoserve.abc.web.module.screen.account.myAccount;


import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.PayMentService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class InvestToMarginMoney {
	@Autowired
    private HttpSession session;
	@Autowired
    private ToCashService tocashservice;
	@Autowired
    private RechargeService rechargeservice;
	@Resource
    private DealRecordService dealrecordservice;
	@Resource
    private AccountInfoService  accountinfoservice;
	@Resource
    private PayMentService  payMentService;

    public void execute(Context context, ParameterParser params,Navigator nav) {
    	User user=(User)session.getAttribute("user");
    	UserIdentity userIdentity = new UserIdentity();
    	if(user.getUserType()==null||user.getUserType()==UserType.PERSONAL){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());
    	userIdentity.setUserId(user.getUserId());
    	PlainResult<Account> account =  accountinfoservice.queryByUserId(userIdentity);
    	String accountNo = account.getData().getAccountNo();
    	String money = FormatHelper.changeY2F(params.getString("money"));
    	Map<String, String> paramsMap = new LinkedHashMap<String, String>();  
    	PlainResult<Map> returnData = new PlainResult<Map>();
        paramsMap.put("biz_type", "FinanceTransfer");
        paramsMap.put("AccountNo", accountNo);
        paramsMap.put("TransAmt", money);
        paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("investToMarginReturnUrl"));
        paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("investToMarginNotifyUrl"));
    	Map<String, String> resultMap = ExchangeDataUtils.getSubmitData(paramsMap);
    	returnData.setSuccess(true);
        returnData.setData(resultMap);
    	context.put("SubmitURL", returnData.getData().remove("requestUrl"));
    	context.put("paramMap", returnData);
    }
}
