package com.autoserve.abc.web.module.screen.account;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.web.helper.LoginUserUtil;

public class Authorize {
    @Autowired
    private UserService        userservice;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DoubleDryService   doubleDryService;
    @Autowired
    private AccountInfoService accountInfoService;
    
    public void execute(Context context, Navigator nav, ParameterParser params) {
    	 Integer empId = LoginUserUtil.getEmpId();
    	 String PlaCustId="";
    	 AccountInfoDO account =  accountInfoService.queryByAccountMark(empId, UserType.PARTNER.type);
    	 PlaCustId=account.getAccountNo();
    	
    	 Map<String, String> map = new LinkedHashMap<String, String>();
    	 map.put("PlaCustId", PlaCustId);
    	 map.put("TxnTyp",params.getString("TxnTyp"));//1、授权 2、解授权
    	 map.put("empId",String.valueOf(empId));
    	 map.put("TransTyp",params.getString("TransTyp"));//1、投资户 2、融资户
    	 
    	 Map<String, String> paramMap =  doubleDryService.guarAuthorize(map);
    	 context.put("SubmitURL", paramMap.remove("requestUrl"));
    	 context.put("paramMap", paramMap);

    }
}
