package com.autoserve.abc.web.module.screen.account.myAccount;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.SafeUtil;

public class BasicInformation {
	
	@Autowired
	private DeployConfigService deployConfigService;
	
	@Autowired
    private HttpSession session;
	
	@Resource
	private UserService userService;
	
	@Resource
	private CompanyCustomerService companyCustomerService;
	
	@Resource
	private BankInfoService bankInfoService;
	@Autowired
    private AccountInfoService    accountInfoService;
	
	@Resource
	private HttpServletRequest request;
  
	public void execute(Context context, ParameterParser params,Navigator nav) {
    	User user=(User) session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request));
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
         //投资户
         userIdentity.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
    	 PlainResult<Account> account1 = accountInfoService.queryByUserId(userIdentity);
    	 if(account1!=null){
    		 String accountNo1 = account1.getData().getAccountNo();
    		 context.put("accountNo1", accountNo1);
    	 }
    	 //融资户
    	 userIdentity.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
    	 PlainResult<Account> account2 = accountInfoService.queryByUserId(userIdentity);
    	 if(account2!=null){
    		 String accountNo2 = account2.getData().getAccountNo();
    		 context.put("accountNo2", accountNo2);
    	 }
    	if(user.getUserType() == UserType.PERSONAL){ // 如果是个人账户
    		
    		Integer completed = 0;      //统计资料完善度
			PlainResult<UserDO>  userDO = this.userService.findById(user.getUserId());
			ListResult<BankInfoDO> banks = bankInfoService.queryListBankInfo(user.getUserId());
			userDO.getData().setUserBankcardIsbinded(banks.getData().size()>0 ? 1:0);
			context.put("user", userDO.getData());
			if(userDO.getData().getUserPhone()!=null && !"".equals(userDO.getData().getUserPhone())){
				String userPhone  = SafeUtil.hideMobile(userDO.getData().getUserPhone());    //格式化电话号码
				context.put("userPhone", userPhone);
				completed+=15;
			}
			if(userDO.getData().getUserDocNo()!=null && !"".equals(userDO.getData().getUserDocNo())){
				//String userDocNo = SafeUtil.hideIDNumber(userDO.getData().getUserDocNo());   //格式化证件号码
				String userDocNo = userDO.getData().getUserDocNo();   //格式化证件号码
				context.put("userDocNo", userDocNo);
				completed+=15;
			}
			if(userDO.getData().getUserRealName()!=null && !"".equals(userDO.getData().getUserRealName())){
				//String userRealName =SafeUtil.hideName(userDO.getData().getUserRealName());//格式化真实姓名
				String userRealName =userDO.getData().getUserRealName();//格式化真实姓名
				context.put("userRealName", userRealName);
				completed+=10;
			}
			
			if(userDO.getData().getUserName()!=null && !"".equals(userDO.getData().getUserName())){
				completed+=10;
			}
			if(userDO.getData().getUserEmail()!=null && !"".equals(userDO.getData().getUserEmail())){
				completed+=15;
			}
			if(userDO.getData().getUserPwd()!=null && !"".equals(userDO.getData().getUserPwd())){
				completed+=15;
			}
			if(userDO.getData().getUserDocType()!=null && !"".equals(userDO.getData().getUserDocType())){
				completed+=10;
			}
			if(userDO.getData().getUserHeadImg()!=null && !"".equals(userDO.getData().getUserHeadImg())){
				completed+=10;
			}
			context.put("completed", completed);
			
		} else if(user.getUserType() == UserType.ENTERPRISE) { // 如果是企业用户
			
			Integer userId = user.getUserId();
			Integer completed = 100;      //统计资料完善度(当作有头像处理，有证件类型)
			PlainResult<UserDO>  userDO = this.userService.findById(userId);
			ListResult<BankInfoDO> banks = bankInfoService.queryListBankInfo(userId);
			userDO.getData().setUserBankcardIsbinded(banks.getData().size()>0 ? 1:0);
			PlainResult<CompanyCustomerDO> companyDO = companyCustomerService.findByUserId(userId);
			context.put("user", userDO.getData());
			context.put("company", companyDO.getData());
			
			context.put("completed", completed);
		}
    
    }
}
