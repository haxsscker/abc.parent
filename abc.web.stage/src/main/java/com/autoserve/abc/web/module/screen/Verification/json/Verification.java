package com.autoserve.abc.web.module.screen.Verification.json;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.RealnameAuthService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.CryptUtils;
import com.autoserve.abc.service.util.Md5Encrypt;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 验证用户名，手机号码...
 * @author DS
 *
 */
public class Verification {
	@Resource
	private UserService userService;
	@Resource
	private HttpSession session;
	@Autowired
	private DeployConfigService deployConfigService;
	@Resource
	private HttpServletRequest request;
	@Resource
	private RealnameAuthService realnameAuthService;
	@Resource
	private DoubleDryService doubleDryService;
	@Autowired
    private AccountInfoService    accountInfoService;
	public JsonBaseVO execute(Context context, ParameterParser params,Navigator nav){	
		 JsonBaseVO result = new JsonBaseVO();
		 UserDO userDO=new UserDO();
		 //标志
		 String flag=params.getString("flag");
		//用户名flag为1
		 if(flag!=null && "1".equals(flag)){			
			String userName=params.getString("userName");
			userDO.setUserName(userName);
			ListResult<UserDO> listResult= userService.queryList(userDO);
			if(listResult.getData().size()!=0){
				result.setSuccess(false);
				result.setMessage("用户名不能重复!");
			}else{
				result.setSuccess(true);
			}
			
		 }
		 if(flag!=null && "2".equals(flag)){
			//手机号码
			 String userPhone=params.getString("userPhone");
			 userDO.setUserPhone(userPhone);
				PageResult<UserDO> pageResult= userService.queryList(userDO, new PageCondition());
				if(pageResult.getData().size()!=0){
					result.setSuccess(false);
					result.setMessage("手机号码不能重复!");
				}else{
					result.setSuccess(true);
				}
		 }
		 
		//邮箱flag为3
		 if(flag!=null && "3".equals(flag)){
			//邮箱
			 String userEmail=params.getString("userEmail");
			 userDO.setUserEmail(userEmail);
				PageResult<UserDO> pageResult= userService.queryList(userDO, new PageCondition());
				if(pageResult.getData().size()!=0){
					result.setSuccess(false);
					result.setMessage("邮箱不能重复!");
				}else{
					result.setSuccess(true);
				}
		 }
		 
		//身份证号flag为4
		 if(flag!=null && "4".equals(flag)){
			//身份证号
			 String userDocNo=params.getString("userDocNo");
			 userDO.setUserDocNo(userDocNo);
			 userDO.setUserType(1);
				PageResult<UserDO> pageResult= userService.queryList(userDO, new PageCondition()); 
				if(pageResult.getData().size()!=0){
					result.setSuccess(false);
					result.setMessage("身份证不能重复!");
				}else{
					result.setSuccess(true);
				}
		 }
		 
		 //交易密码flag为5
		 if(flag!=null && "5".equals(flag)){
			 String userDealPwd=params.getString("userDealPwd");
			 User user=(User)session.getAttribute("user");
			 if(user==null){
		    		nav.redirectToLocation(deployConfigService.getLoginUrl(request));
		    		return null;
		    	}  
			 userDO.setUserId(user.getUserId());
			 userDO.setUserDealPwd(CryptUtils.md5(userDealPwd));
			 PageResult<UserDO> pageResult= userService.queryList(userDO, new PageCondition()); 
				if(pageResult.getData().size()==0){
					result.setSuccess(false);
					result.setMessage("交易密码错误!");
				}else{
					result.setSuccess(true);
				}
		 }
		 //登录密码flag为6
		 if(flag!=null && "6".equals(flag)){
			 String userPwd=params.getString("userPwd");
			 User user=(User)session.getAttribute("user");
			 if(user==null){
				 nav.redirectToLocation(deployConfigService.getLoginUrl(request));
				 return null;
			 }  
			 userDO.setUserId(user.getUserId());
			 userDO.setUserPwd(CryptUtils.md5(userPwd));
			 PageResult<UserDO> pageResult= userService.queryList(userDO, new PageCondition()); 
			 if(pageResult.getData().size()==0){
				 result.setSuccess(false);
				 result.setMessage("登录密码错误!");
			 }else{
				 result.setSuccess(true);
			 }
		 }
		 //真实姓名flag为7
		 if(flag!=null && "7".equals(flag)){
			 String userRealName=params.getString("userRealName");
			 userDO.setUserRealName(userRealName);
			 PageResult<UserDO> pageResult= userService.queryList(userDO, new PageCondition());
			 if(pageResult.getData().size()!=0){
					result.setSuccess(false);
					result.setMessage("真实姓名不能重复!");
				}else{
					result.setSuccess(true);
				}
			 
		 }
		 
		//实名认证flag为8
		 if(flag!=null && "8".equals(flag)){
			 User user=(User)session.getAttribute("user");
			 if(user==null){
				 nav.redirectToLocation(deployConfigService.getLoginUrl(request));
				 return null;
			 	}
			BaseResult baseResult=realnameAuthService.realNameAudit(user.getUserId());
			result.setSuccess(baseResult.isSuccess());
			result.setMessage(baseResult.getMessage());
		    result.setRedirectUrl("/account/myAccount/openAccountForm");
		 	}
		 
		//对公账号flag为9
		 if(flag!=null && "9".equals(flag)){
			 String accountNo=params.getString("accountNo");
			 AccountInfoDO account = new AccountInfoDO();
			 account.setAccountUserAccount(accountNo);
			 //PageResult<UserDO> pageResult= userService.queryList(userDO, new PageCondition());
			 PageResult<AccountInfoDO> pageResult = accountInfoService.queryByAccount(account, new PageCondition());
			 if(pageResult.getData().size()!=0){
				 AccountInfoDO account1 = pageResult.getData().get(0);
				 if(null!=account1.getAccountNo()&&""!=account1.getAccountNo()){
					 result.setSuccess(false);
					 result.setMessage("对公账号不能重复!");
				 }else{
					 String accountUserAccount =account1.getAccountUserAccount();
        		     String mark=account1.getAccountMark();
        		     Map<String, String> chargeAccountMap =this.doubleDryService.queryChargeAccountResult(accountUserAccount,mark);
        		     String accountNo1=chargeAccountMap.get("accountNo");
        		     if(null!=accountNo1){
						 result.setSuccess(false);
						 result.setMessage("该账户已开户!");
					 }
				 }	
				}else{
					result.setSuccess(true);
				}
			 
		 }else if(flag!=null && "10".equals(flag)){//普通图形验证码及手机验证码
			 	String securityCode = (String) session.getAttribute("securityCode");
				String Verification = params.getString("Verification");
				if (securityCode == null || "".equals(securityCode)) {
					result.setSuccess(false);
					result.setMessage("验证码已失效，请重新获取！");
				} else if (null == Verification
						|| !securityCode.equals(Md5Encrypt.md5(Verification))) {
					result.setSuccess(false);
					result.setMessage("验证码错误！");
				}
		 }else if(flag!=null && "11".equals(flag)){//发送手机验证码的图形验证码
			 	String securityCode = (String) session.getAttribute("smsSecurityCode");
				String Verification = params.getString("Verification");
				if (securityCode == null || "".equals(securityCode)) {
					result.setSuccess(false);
					result.setMessage("验证码已失效，请重新获取！");
				} else if (null == Verification
						|| !securityCode.equals(Md5Encrypt.md5(Verification))) {
					result.setSuccess(false);
					result.setMessage("验证码错误！");
				}
		 }
		 return result;
	 	}

}
