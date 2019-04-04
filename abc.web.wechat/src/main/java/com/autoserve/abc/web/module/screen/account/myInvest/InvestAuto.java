package com.autoserve.abc.web.module.screen.account.myInvest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.InvestSet;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.invest.InvestSetService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
/**
 * 
 * @author DS
 *
 * 2015上午9:49:26
 */
public class InvestAuto {
	 @Autowired
	 private HttpSession           session;
	 @Resource
	 private HttpServletRequest    request;
	 @Autowired
	 private DeployConfigService   deployConfigService;
	 @Autowired
	 private AccountInfoService    accountInfoService;
	 @Resource
	 private DoubleDryService      doubleDryService;
	 @Autowired
	 private InvestSetService     investSetService;
	 public void execute(Context context, ParameterParser params, Navigator nav) {
		 User user = (User) session.getAttribute("user");
	        if (user == null) {
	            nav.redirectToLocation(deployConfigService.getLoginUrl(request));
	            return;
	        }
	        UserIdentity userIdentity = new UserIdentity();
	        userIdentity.setUserId(user.getUserId());
	        if (user.getUserType() == null || user.getUserType().getType() == 1) {
	            user.setUserType(UserType.PERSONAL);
	        } else {
	            user.setUserType(UserType.ENTERPRISE);
	        }
	        userIdentity.setUserType(user.getUserType());
	        PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
	       //网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
	        String accountMark = account.getData().getAccountMark(); //多多号id
	        Double[] accountBacance = { 0.00, 0.00, 0.00 };
	        if (accountMark != null || !"".equals(accountMark)) {
	            accountBacance = this.doubleDryService.queryBalance(accountMark, "1");
	        }
	        context.put("accountBacance", accountBacance);
	        //标类型
	   	 	context.put("loanCategoryList",LoanCategory.values());
	   	 	//还款方式
	   	 	context.put("loanPayTypeList", LoanPayType.values());
	   	 	
	   	 	//查询设置自动投标记录
	   	 	InvestSet investSet=new InvestSet();
	   	 	investSet.setUserId(user.getUserId());
	   	 	ListResult<InvestSet> listResult=investSetService.queryInvest(investSet);
	   	 	context.put("listInvestSet", listResult);
	 }
	
	 
}
