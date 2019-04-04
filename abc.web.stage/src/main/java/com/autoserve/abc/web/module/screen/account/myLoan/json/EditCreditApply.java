package com.autoserve.abc.web.module.screen.account.myLoan.json;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.CreditApply;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.CreditType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.intf.credit.CreditService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;


public class EditCreditApply {
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

    public BaseResult execute(Context context,Navigator nav,ParameterParser params,
    									@Params CreditApply creditapply) {
    	BaseResult  result = new BaseResult();
    	User user = (User) session.getAttribute("user");
    	if(user==null){
    		nav.redirectToLocation(deployConfigService.getLoginUrl(request));
    	}

    	PlainResult<UserDO> userresult = userService.findById(user.getUserId());
    	UserDO userdo = new UserDO();
    	userdo = userresult.getData();
    	
	     if(creditapply==null){
	     creditapply = new CreditApply();
	     }
	     creditapply.setCreditUserId(userdo.getUserId());
	     creditapply.setCreditType(CreditType.LOAN_CREDIT);
	     creditapply.setCreditOldAmount(userdo.getUserLoanCredit());
	     creditapply.setCreditReviewState(ReviewState.PENDING_REVIEW);
	     creditapply.setCreditApplyDate(new Date());
	     result = creditservice.createCreditApply(creditapply);
    	 return result;
    }
}
