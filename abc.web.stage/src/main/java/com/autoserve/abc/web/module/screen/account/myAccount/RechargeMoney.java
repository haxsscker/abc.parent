package com.autoserve.abc.web.module.screen.account.myAccount;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ConfigHelper;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class RechargeMoney {
	@Autowired
    private UserService         userservice;
	@Autowired
    private HttpSession session;
	@Autowired
    private DealRecordService dealrecordservice;
	@Autowired
    private AccountInfoService  accountinfoservice;
	@Autowired
    private RechargeService rechargeservice;

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
    	PlainResult<Account> account =  accountinfoservice.queryByUserId(userIdentity) ;
    	
    	String money = FormatHelper.changeY2F(params.getString("money"));

    	if(money!=null&&!"".equals(money)&&account!=null){
    	
    	Map<String,String> map = new HashMap<String,String>();
    	BigDecimal bigdecimal = new BigDecimal(params.getString("money"));
    	PlainResult<DealReturn> DealReturn = rechargeservice.recharge(user.getUserId(), user.getUserType(), bigdecimal, map);
    	    	
    	//在此处构造接口参数（之前是在rechargeservice.recharge()里构造，现在废弃）
        PlainResult<Map> returnData = new PlainResult<Map>();
        Map<String, String> paramsMap = new LinkedHashMap <String, String> ();
        String source = params.getString("source");//充值操作的来源
        paramsMap.put("MerBillNo", DealReturn.getData().getDrInnerSeqNo());
        paramsMap.put("biz_type", "WebRecharge");
        paramsMap.put("PlaCustId", account.getData().getAccountNo());
        paramsMap.put("TransAmt", money);
        paramsMap.put("FeeType", "0");
        paramsMap.put("MerFeeAmt", "0");
        if("register".equals(source)){//注册流程充值
        	paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("regRechargeReturnUrl"));
        }else{
        	paramsMap.put("PageReturnUrl",ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("rechargeReturnUrl"));
        }
        paramsMap.put("BgRetUrl", ConfigHelper.getConfig("notifyurlprefix") + ConfigHelper.getConfig("rechargeNotifyUrl"));
        paramsMap.put("TransTyp", params.getString("TransTyp"));
        paramsMap.put("MerPriv", String.valueOf(user.getUserId()));
        Map<String, String> resultMap = ExchangeDataUtils.getSubmitData(paramsMap);
        returnData.setSuccess(true);
        returnData.setData(resultMap);
    	context.put("SubmitURL", returnData.getData().remove("requestUrl"));
    	context.put("paramMap", returnData);
    	
    	}
    }
}
