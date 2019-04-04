package com.autoserve.abc.web.module.screen.webnotify;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class OpenAccountNotify {
	private final static Logger logger = LoggerFactory.getLogger(OpenAccountNotify.class);
	@Resource
    private AccountInfoService   accountInfoService;
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  resq;
    @Resource
    private DoubleDryService doubleDtyService;

    public void execute(Context context, Navigator nav, ParameterParser params) {
    	Map paramterMap = resq.getParameterMap();
        Map returnMap = EasyPayUtils.transformRequestMap(paramterMap);
        System.out.println("接收参数：======="+returnMap);
    	String AccountNumber =params.getString("AccountNumber");//多多号
    	String MoneymoremoreId =params.getString("MoneymoremoreId");//多多号
        String AccountType = params.getString("AccountType");//账户类型
        String Mobile = params.getString("Mobile");//手机号
        String Email = params.getString("Email");//邮箱
        String RealName = params.getString("RealName");//真实姓名
        String IdentificationNo = params.getString("IdentificationNo");//身份证
        String LoanPlatformAccount = params.getString("LoanPlatformAccount");//用户在网贷平台的账号
        String ResultCode = params.getString("ResultCode");
        String Remark1 = params.getString("Remark1");
        
        BaseResult result = new BaseResult();
        if(ResultCode.equals("88")){
        	AccountInfoDO account = new AccountInfoDO();
        	account.setAccountUserId(Integer.valueOf(Remark1));
        	account.setAccountUserName(LoanPlatformAccount);
        	account.setAccountLegalName(RealName);
        	account.setAccountNo(AccountNumber);
        	account.setAccountUserCard(IdentificationNo);
        	account.setAccountUserEmail(Email);
        	account.setAccountUserPhone(Mobile);
        	account.setAccountMark(MoneymoremoreId);
        	if("".equals(AccountType)||AccountType==null){
        		account.setAccountUserType(1);
        	}  else{
        		account.setAccountUserType(2);
        	}
        	result = accountInfoService.openAccount(account);
        	System.out.println(result.getMessage());
        	}
        
        try {
        		if(result.isSuccess()){
        			resp.getWriter().print("SUCCESS");
                }else {
                	resp.getWriter().print("fail");
                }
        } catch (Exception e) {
            logger.error("[OpenAccount] error: ", e);
        }
        
    }
}
