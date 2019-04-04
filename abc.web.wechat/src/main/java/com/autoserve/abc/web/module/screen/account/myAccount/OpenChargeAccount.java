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
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.ExchangeDataUtils;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class OpenChargeAccount {
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

    public void execute(Context context, ParameterParser params, Navigator nav) {
        User user = (User) session.getAttribute("user");
        PlainResult<User> result = userService.findEntityById(user.getUserId());
        Map<String,String> param= new LinkedHashMap<String,String>();
    	param.put("TxnTyp", params.getString("TxnTyp"));
		param.put("AccountNo", params.getString("AccountNo"));
		param.put("AccountName", params.getString("AccountName"));
		param.put("AccountBk", params.getString("AccountBk"));
		param.put("userId", String.valueOf(user.getUserId()));
		param.put("Agent", "wx");
    	PlainResult<Map> paramMap = doubledryservice.openChargeAccent(param);
	    paramMap.getData().remove("MerBillNo");
    	//微信端通过页面get方式请求会把参数再次编码，所以这边先解码，避免2次编码，app端就不用解码了
    	String NetLoanInfo = URLDecoder.decode((String) paramMap.getData().get("NetLoanInfo"));
//    	logger.info(NetLoanInfo);
    	paramMap.getData().put("NetLoanInfo",NetLoanInfo);
    	context.put("SubmitURL", paramMap.getData().remove("requestUrl"));
    	context.put("paramMap", paramMap);
    }
}
