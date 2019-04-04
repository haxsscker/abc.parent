package com.autoserve.abc.web.module.screen.account.myLoan;

import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CreditJDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.credit.CreditService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;


public class CreditApply {
	@Autowired
    private HttpSession session;
	@Autowired
	private DeployConfigService deployConfigService;
	@Resource
	private UserService userService;
	@Resource
	private CreditService creditservice;
	@Resource
	private HttpServletRequest  request;

    public void execute(Context context,Navigator nav,ParameterParser params) {
    	 User user = (User) session.getAttribute("user");
	     if(user==null){
	     nav.redirectToLocation(deployConfigService.getLoginUrl(request));
	     	return;
	     }
	     int currentPage = params.getInt("currentPage");
    	 if(currentPage==0)currentPage=1;
    	 int pageSize=10;
    	 PageCondition pageCondition = new PageCondition(currentPage,pageSize);
    	 
	     PlainResult<UserDO> userresult = userService.findById(user.getUserId());
	     context.put("UserDO", userresult.getData());
	     
	     CreditJDO creditJDO = new CreditJDO();
	     creditJDO.setCreditUserId(user.getUserId());
	     PageResult<CreditJDO> result = creditservice.queryList(creditJDO, pageCondition);
	     Pagebean<CreditJDO>  pagebean = new Pagebean<CreditJDO>(currentPage,pageSize,result.getData(),result.getTotalCount());
 		context.put("pagebean", pagebean);
    }
   
}
