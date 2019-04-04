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

public class OpenAccount {
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
        
        //个人用户开户前实名认证
//        if(result.getData().getUserType().equals(UserType.PERSONAL)){
//	   	 	BaseResult baseResult = realnameAuthService.realNameAudit(user.getUserId());
//	   	 	if(!baseResult.isSuccess()){
//	   	 		context.put("Message", baseResult.getMessage());
//	   	 		nav.forwardTo("/error.vm");
//	   	 	}
//        }
        
        session.setAttribute("user", result.getData());
        Map<String,String> param= new HashMap<String,String>();
//    	param.put("RegisterType", "2");
    	if(result.getData().getUserType()==null||"".equals(result.getData().getUserType())||result.getData().getUserType()==UserType.PERSONAL){
//    		param.put("AccountType", "");
//    		param.put("IdentificationNo", result.getData().getUserDocNo());
//    		param.put("RealName", result.getData().getUserRealName());
    		param.put("MobileNo", result.getData().getUserPhone());
//        	param.put("Email", result.getData().getUserEmail());
    	}else{
    		CompanyCustomerDO company = companyCustomerService.findByUserId(user.getUserId()).getData();
//    		param.put("AccountType","1");
//    		param.put("IdentificationNo", company.getCcLicenseNo());
//    		param.put("RealName", company.getCcCompanyName());
    		param.put("MobileNo", company.getCcContactPhone());
//        	param.put("Email", company.getCcContactEmail());
    	}   	
//    	param.put("LoanPlatformAccount", result.getData().getUserName());
//    	param.put("Remark1", result.getData().getUserId().toString());
		param.put("Agent", "wx");
    	param.put("MerPriv", result.getData().getUserId().toString());
    	param.put("TransTyp", params.getString("TransTyp"));
    	PlainResult<Map> paramMap = doubledryservice.openAccent(param);
    	//微信端通过页面get方式请求会把参数再次编码，所以这边先解码，避免2次编码，app端就不用解码了
    	String NetLoanInfo = URLDecoder.decode((String) paramMap.getData().get("NetLoanInfo"));
//    	logger.info(NetLoanInfo);
    	paramMap.getData().put("NetLoanInfo",NetLoanInfo);
    	context.put("SubmitURL", paramMap.getData().remove("requestUrl"));
    	context.put("paramMap", paramMap);
    }
}
