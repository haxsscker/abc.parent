package com.autoserve.abc.web.module.screen.register;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.AssessLevelDO;
import com.autoserve.abc.dao.dataobject.BankMappingDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankMappingService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserAssessService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;

/**
 * 注册
 */
public class Toregister {
    @Resource
    private UserService userService;
    @Autowired
    private AccountInfoService accountInfoService;
    @Resource
    private HttpSession session;
    @Resource
	private BankMappingService bankMappingService;
    @Resource
	private UserAssessService userAssessService;
	@Resource
	private HttpServletRequest request;
	@Autowired
	private DeployConfigService deployConfigService;
	@Resource
    private DoubleDryService doubleDryService;
	
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	//新的注册流程
    	String step = params.getString("step");
        if(null==step || "1".equals(step)){//注册页面
        	String InvitationId = params.getString("InvitationId");
        	if (InvitationId != null && !"".equals(InvitationId)) {
                context.put("InvitationId", InvitationId);
            }
        	nav.forwardTo("/register/toregister_step1").end();
        	return;
        }else if("2".equals(step)){//开通存管账户页面
        	User user=(User)session.getAttribute("user");
	    	if(user==null){
	    		nav.forwardTo(deployConfigService.getLoginUrl(request)).end();
	    		return;
	    	}
	    	PlainResult<User> result= userService.findEntityById(user.getUserId());
	    	User userResult=result.getData();
	    	context.put("user", userResult);
        	nav.forwardTo("/register/toregister_step2").end();
//        	nav.redirectToLocation("/account/myAccount/AccountOverview");
        	return;
        }else if("3".equals(step)){//授权页面
	    	User user=(User)session.getAttribute("user");
	    	if(user==null){
	    		nav.forwardTo(deployConfigService.getLoginUrl(request));
	    		return;
	    	}
	    	PlainResult<User> result= userService.findEntityById(user.getUserId());
	    	User userResult=result.getData();
	    	context.put("user", userResult);
        	nav.forwardTo("/register/toregister_step3").end();
        	return;
        }else if("4".equals(step)){//风险承受能力评估页面
	    	User user=(User)session.getAttribute("user");
	    	if(user==null){
	    		nav.forwardTo(deployConfigService.getLoginUrl(request));
	    		return;
	    	}
	    	PlainResult<User> result= userService.findEntityById(user.getUserId());
	    	User userResult=result.getData();
	    	context.put("user", userResult);
        	nav.forwardTo("/register/toregister_step4").end();
        	return;
        }else if("5".equals(step)){//账户充值页面
	    	User user=(User)session.getAttribute("user");
	    	if(user==null){
	    		nav.forwardTo(deployConfigService.getLoginUrl(request));
	    		return;
	    	}
	    	PlainResult<User> result= userService.findEntityById(user.getUserId());
	    	User userResult=result.getData();
	    	context.put("user", userResult);
	    	//投资户与融资户账号一样
			AccountInfoDO account = accountInfoService.qureyAccountByUserIdAndUserType(user.getUserId(),user.getUserType().getType());
	   		String accountNo = account.getAccountNo();
	   		Double[] accountBacance = { 0.00, 0.00, 0.00, 0.00, 0.00, 0.00};
		    //网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
		    if (accountNo != null && !"".equals(accountNo)) {
	            accountBacance = this.doubleDryService.queryBalanceDetail(accountNo);
	        }		
		    context.put("accountBacance", accountBacance);
        	nav.forwardTo("/register/toregister_step5").end();
        	return;
        }else if("6".equals(step)){//投资页面
	    	User user=(User)session.getAttribute("user");
	    	if(user==null){
	    		nav.forwardTo(deployConfigService.getLoginUrl(request));
	    		return;
	    	}
	    	PlainResult<User> result= userService.findEntityById(user.getUserId());
	    	User userResult=result.getData();
	    	context.put("user", userResult);
        	nav.forwardTo("/register/toregister_step6").end();
        	return;
        }
    
        /*String InvitationId = params.getString("InvitationId");
        if("3".equals(params.getString("step"))){
	        List<BankMappingDO> paramList = bankMappingService.findBankMapping();
	    	context.put("paramList", paramList);
	    	User user=(User)session.getAttribute("user");
	    	if(user==null){
	    		nav.forwardTo(deployConfigService.getLoginUrl(request));
	    		return;
	    	}
	    	PlainResult<User> result= userService.findEntityById(user.getUserId());
	    	User userResult=result.getData();
	    	context.put("userResult", userResult);
	    	context.put("userRealName", params.getString("userRealName"));
	    	context.put("userDocNo", params.getString("userDocNo"));
	    	
        }
        if (InvitationId != null && !"".equals(InvitationId)) {
            context.put("InvitationId", InvitationId);
        }*/

    }

}
