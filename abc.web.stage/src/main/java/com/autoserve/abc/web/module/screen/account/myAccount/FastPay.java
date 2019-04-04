package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;

public class FastPay {
	@Autowired
	private HttpSession session;
	@Autowired
	private UserService userservice;
	@Autowired
	private AccountInfoService accountInfoService;
	@Resource
	private DoubleDryService doubleDryService;

	public void execute(Context context, ParameterParser params) {
		User user = (User) session.getAttribute("user");
		PlainResult<User> result = userservice.findEntityById(user.getUserId());
		session.setAttribute("user", result.getData());
		UserIdentity userIdentity = new UserIdentity();
		userIdentity.setUserId(user.getUserId());
		if (user.getUserType() == null || user.getUserType().getType() == 1) {
			user.setUserType(UserType.PERSONAL);
		} else {
			user.setUserType(UserType.ENTERPRISE);
		}
		userIdentity.setUserType(user.getUserType());
		PlainResult<Account> account = accountInfoService
				.queryByUserId(userIdentity);
		String accountMark = account.getData().getAccountMark();
		
		String actionType = params.getString("actionType");

		// 发起接口
		Map<String,String> map = new HashMap<String,String>();
    	map.put("MoneymoremoreId", accountMark);
    	map.put("PlatformMoneymoremore", "");
    	map.put("Action", actionType);
    	map.put("CardNo", "");
    	map.put("WithholdBeginDate", "");
    	map.put("WithholdEndDate", "");
    	map.put("SingleWithholdLimit", "");
    	map.put("TotalWithholdLimit", "");
    	map.put("RandomTimeStamp", "");
    	map.put("ReturnURL", "");
    	map.put("NotifyURL", "");
    	
    	PlainResult<Map> maps = this.doubleDryService.fastPay(map);
    	JSONObject jo  = JSON.parseObject(JSON.toJSONString(maps.getData()));
    	
    	context.put("jo", jo);
	}
}
