package com.autoserve.abc.web.module.screen.account.myLoan.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.intf.loan.repay.RepayService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.web.helper.DeployConfigService;
/**
 * 还款
 *
 * 2015年5月4日下午4:55:11
 */
public class Repay{
	 @Autowired
	 private DeployConfigService deployConfigService;
	 @Resource
	 private RepayService rapayService;
	 @Resource
	 private HttpSession session;
	 @Resource
	 private HttpServletRequest request;
	 @Resource
	 private UserService userService;
	 public BaseResult execute(Context context,Navigator nav, ParameterParser params){
		 BaseResult baseResult = new BaseResult();
		 User user = (User) session.getAttribute("user");
		 if(user==null){
			 nav.redirectToLocation(deployConfigService.getLoginUrl(request));
	    	 return null;
		 }
		//交易密码
		 String payPs = params.getString("payPs");
		 //验证码
		 String imageCode = params.getString("imageCode");
		 Integer loanId = params.getInt("loanId");
		 Integer repayPlanId = params.getInt("repayPlanId");
		 //验证
		 PlainResult<UserDO> resultUserDO = userService.findById(user.getUserId());
		 UserDO userDO=resultUserDO.getData();
		 if(!userDO.getUserDealPwd().equals(CryptUtils.md5(payPs))){
			 baseResult.setSuccess(false);
			 baseResult.setMessage("交易密码错误");
			 return baseResult;
		 }
		 String securityfromSession=(String)session.getAttribute("securityCode");
		 if (securityfromSession==null || imageCode==null || !securityfromSession.equalsIgnoreCase(imageCode)){
			if(securityfromSession==null){
				baseResult.setMessage("验证码已失效，请重新获取");
	        }else{
	            baseResult.setMessage("验证码错误");
	         }                           
		    baseResult.setSuccess(false);
			return baseResult;
			}
		 //调用还款接口
		 baseResult=  rapayService.repay(loanId, repayPlanId, PayType.COMMON_CLEAR, user.getUserId());
		 return baseResult;		
	 }
}