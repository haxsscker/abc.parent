package com.autoserve.abc.web.module.screen.invest;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.invest.InvestService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserAssessService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.EasyPayUtils;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.module.screen.invest.json.CreateInvest;

/**
 * 创建投资
 *
 * @author zhouyu 2018年4月3日 
 */
public class WebInvest {

	@Resource
	private HttpSession session;
	@Resource
	private UserService userService;
	@Autowired
	private DeployConfigService deployConfigService;
	@Resource
	private HttpServletRequest request;
	@Autowired
    private AccountInfoService    accountInfoService;
	@Resource
    private DoubleDryService      doubleDryService;

	public void execute(Context context, ParameterParser params,Navigator nav) {
		Map notifyMap = EasyPayUtils.transformRequestMap(request.getParameterMap());
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request));
    		return;
    	}
    	PlainResult<User> result= userService.findEntityById(user.getUserId());
    	User userResult=result.getData();
    	
    	UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(user.getUserId());
        if (user.getUserType() == null || user.getUserType().getType() == 1) {
            user.setUserType(UserType.PERSONAL);
        } else {
            user.setUserType(UserType.ENTERPRISE);
        }
        userIdentity.setUserType(user.getUserType());
        PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
        String accountNo = account.getData().getAccountNo();
    	session.setAttribute("user", userResult);
    	
    	Integer loanId = params.getInt("loanId");//标的 ID
    	Double transAmt = params.getDouble("investedMoney");//总投资金额
    	Double marketAmt = params.getDouble("MarketAmt");//营销金额
    	String MerBillNo = params.getString("MerBillNo");//
    	Map<String,String> param= new LinkedHashMap<String,String>();
    	param.put("PlaCustId", accountNo);  
    	param.put("TransAmt", transAmt.toString()); 
    	param.put("MarketAmt", marketAmt.toString()); 
    	param.put("BorrowId", loanId.toString()); 
    	param.put("MerBillNo", MerBillNo); 
    	//param.put("MerPriv", result.getData().getUserId().toString());
    	PlainResult<Map> paramMap = doubleDryService.webInvest(param);
    	System.out.println("发送参数:======"+paramMap.getData());
    	context.put("SubmitURL", paramMap.getData().remove("requestUrl"));
    	context.put("paramMap", paramMap);
    	
    	
	}

}
