package com.autoserve.abc.web.module.screen.account.seCenter;


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
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.PayMentService;
import com.autoserve.abc.service.biz.intf.cash.RechargeService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

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

    public JsonBaseVO execute(Context context, ParameterParser params) {
    	JsonBaseVO result = new JsonBaseVO();
    	User user=(User)session.getAttribute("user");
    	UserIdentity userIdentity = new UserIdentity();
    	if(user.getUserType()==null||user.getUserType()==UserType.PERSONAL){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    	}
    	userIdentity.setUserType(user.getUserType());
    	userIdentity.setUserId(user.getUserId());
    	PlainResult<Account> account =  accountinfoservice.queryByUserId(userIdentity) ;
    	String accountNo = account.getData().getAccountNo();
    	String money = FormatHelper.changeY2F(params.getString("money"));
    	Map<String, String> resultMap = payMentService.investToMargin(money, accountNo);
    	String  RespCode = resultMap.get("RespCode");
    	String  RespDesc = resultMap.get("RespDesc");
    	if("000000".equals(RespCode)){
			result.setSuccess(true);
			result.setMessage("转账成功");
		}else{
			result.setSuccess(false);
			result.setMessage(RespDesc);
		}
    	return result;
    }
}
