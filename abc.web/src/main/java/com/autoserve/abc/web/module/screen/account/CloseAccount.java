package com.autoserve.abc.web.module.screen.account;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.EasyPayUtils;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;

public class CloseAccount {
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
		LoginUserInfo user = LoginUserInfoHelper.getLoginUserInfo();
        if (user == null) {
        	nav.forwardTo(deployConfigService.getLoginUrl(request));
    		return;
        }
        UserIdentity ui = new UserIdentity();
        ui.setUserId(user.getEmpId());
        ui.setUserType(UserType.PARTNER);
        AccountInfoDO account = accountInfoService.queryByUserIdentity(ui).getData();
        if (account == null) {
            account = new AccountInfoDO();
        }
        String accountNo = account.getAccountNo();
    	Map<String,String> param= new LinkedHashMap<String,String>();
    	param.put("PlaCustId", accountNo);   	
    	param.put("MerPriv", String.valueOf(user.getEmpId()));
    	param.put("TransTyp", String.valueOf("2"));
    	PlainResult<Map> paramMap = doubledryservice.closeAccount(param);
    	System.out.println("发送参数:======"+paramMap.getData());
    	context.put("SubmitURL", paramMap.getData().remove("requestUrl"));
    	context.put("paramMap", paramMap);
	}

}
