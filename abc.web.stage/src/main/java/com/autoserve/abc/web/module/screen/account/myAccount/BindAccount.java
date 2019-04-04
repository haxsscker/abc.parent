package com.autoserve.abc.web.module.screen.account.myAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.UserAuthorizeFlag;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.AuthorizeUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.FormatHelper;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.helper.DeployConfigService;

public class BindAccount {
   
	@Autowired
    private UserService         userservice;
	@Resource
	private UserDao userDao;
	@Autowired
    private HttpSession session;
	
	@Autowired
    private AccountInfoService accountInfoService;
	
	@Autowired
	private DeployConfigService deployConfigService;
	
	@Resource
	private CompanyCustomerService companyCustomerService;
	
	@Resource
	private HttpServletRequest request;
	
	@Resource
	private DoubleDryService doubleDryService;

    public void execute(Context context, ParameterParser params,Navigator nav) {
    	String tab_name = params.getString("tab_name", "PTXX");
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.redirectToLocation(deployConfigService.getLoginUrl(request));
    		return;
    	}  
    	user = userservice.findEntityById(user.getUserId()).getData();
    	
    	UserIdentity userIdentity =new UserIdentity();
    	userIdentity.setUserId(user.getUserId());
    	if(user.getUserType()==null || user.getUserType().getType()==1){
    		user.setUserType(UserType.PERSONAL);
    	}else{
    		user.setUserType(UserType.ENTERPRISE);
    		PlainResult<CompanyCustomerDO> companyDO = companyCustomerService.findByUserId(user.getUserId());
    		context.put("company", companyDO.getData());
    	}
    	userIdentity.setUserType(user.getUserType());
    	//投资账户
    	userIdentity.setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
    	PlainResult<Account> account1 = accountInfoService.queryByUserId(userIdentity);
    	//String accountNo1 = account1.getData().getAccountNo();
    	//融资账户
    	userIdentity.setAccountCategory(AccountCategory.LOANACCOUNT.getType());
    	PlainResult<Account> account2 = accountInfoService.queryByUserId(userIdentity);
    	//String accountNo2 = account2.getData().getAccountNo();
    	//绑定银行卡
    	List<BankInfoDO> banklist = new ArrayList<BankInfoDO>();
    	List<BankInfoDO> investBanklist = new ArrayList<BankInfoDO>();
    	List<BankInfoDO> loanBanklist = new ArrayList<BankInfoDO>();
    	if(UserType.PERSONAL.type==user.getUserType().getType()){
    		if(null != account1 && StringUtils.isNotEmpty(account1.getData().getAccountNo())){
    			banklist = doubleDryService.queryCard(account1.getData().getAccountNo(), "");
    		}else if(null != account2 && StringUtils.isNotEmpty(account2.getData().getAccountNo())){
    			banklist = doubleDryService.queryCard(account2.getData().getAccountNo(), "");
    		}
    	}
    	//投资户绑定手机
    	String investBindMobile = user.getUserPhone();
    	//融资户绑定手机
    	String loanBindMobile = user.getUserPhone();
    	if(null != banklist && banklist.size()>0){
    		for(BankInfoDO bd :banklist){
    			if(1==bd.getBankAccountType()){//投资户
    				investBanklist.add(bd);
    				investBindMobile = bd.getBindMobile();
    			}else if(2==bd.getBankAccountType()){//融资户
    				loanBanklist.add(bd);
    				loanBindMobile = bd.getBindMobile();
    			}
    		}
    	}
    	context.put("investBanklist", investBanklist);
    	context.put("loanBanklist", loanBanklist);
    	context.put("investBindMobile", investBindMobile);
    	context.put("loanBindMobile", loanBindMobile);
    	String RealNameFlg = null;
    	String ChargeAmt = null;
        if(account2.isSuccess()&&user.getUserType().type==2){
        	String accountUserAccount2 =account2.getData().getAccountUserAccount();
        	String mark=account2.getData().getAccountMark();
        	Map<String, String> chargeAccountMap =this.doubleDryService.queryChargeAccountResult(accountUserAccount2,mark);
        	//accountNo2=chargeAccountMap.get("accountNo");
        	RealNameFlg=chargeAccountMap.get("RealNameFlg");
        	ChargeAmt=chargeAccountMap.get("ChargeAmt");
        }
    	String chargeAccount1 ="";
    	String accountName1 ="";
    	String chargeAccount2 ="";
    	String accountName2 ="";
    	if(null != account1 && StringUtils.isNotEmpty(account1.getData().getAccountNo())){
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("PlaCustId", account1.getData().getAccountNo());
    		/*Map<String, String> authInfMap = doubleDryService.queryAuthorizeInfo(map);
    		String authorizeInvestType=authInfMap.get("authorizeInvestType");
            String authorizeInvestStartDate=authInfMap.get("authorizeInvestStartDate");
            String authorizeInvestEndDate=authInfMap.get("authorizeInvestEndDate");
    		String authorizeFeeType=authInfMap.get("authorizeFeeType");
    		String authorizeFeeStartDate=authInfMap.get("authorizeFeeStartDate");
    		String authorizeFeeEndDate=authInfMap.get("authorizeFeeEndDate");
    		String authorizeRepayType=authInfMap.get("authorizeRepayType");
    		String authorizeRepayStartDate=authInfMap.get("authorizeRepayStartDate");
    		String authorizeRepayEndDate=authInfMap.get("authorizeRepayEndDate");
    		if(StringUtils.isNotEmpty(authorizeInvestType) || StringUtils.isNotEmpty(authorizeFeeType) 
    				|| StringUtils.isNotEmpty(authorizeRepayType)){
    			UserDO userDo = new UserDO();
    			userDo.setUserId(user.getUserId());
    			userDo.setAuthorizeInvestType(authorizeInvestType);
        		userDo.setAuthorizeInvestStartDate(FormatHelper.formatStr2Date(authorizeInvestStartDate, "yyyyMMdd"));
        		userDo.setAuthorizeInvestEndDate(FormatHelper.formatStr2Date(authorizeInvestEndDate, "yyyyMMdd"));
        		userDo.setAuthorizeFeeType(authorizeFeeType);
        		userDo.setAuthorizeFeeStartDate(FormatHelper.formatStr2Date(authorizeFeeStartDate, "yyyyMMdd"));
        		userDo.setAuthorizeFeeEndDate(FormatHelper.formatStr2Date(authorizeFeeEndDate, "yyyyMMdd"));
        		userDo.setAuthorizeRepayType(authorizeRepayType);
        		userDo.setAuthorizeRepayStartDate(FormatHelper.formatStr2Date(authorizeRepayStartDate, "yyyyMMdd"));
        		userDo.setAuthorizeRepayEndDate(FormatHelper.formatStr2Date(authorizeRepayEndDate, "yyyyMMdd"));
        		userDo.setUserAuthorizeFlag(UserAuthorizeFlag.ENABLE.getState());
        		userDao.updateAuthorizeByUserId(userDo);
        		user.setAuthorizeInvestType(authorizeInvestType);
        		user.setAuthorizeInvestStartDate(FormatHelper.formatStr2Date(authorizeInvestStartDate, "yyyyMMdd"));
        		user.setAuthorizeInvestEndDate(FormatHelper.formatStr2Date(authorizeInvestEndDate, "yyyyMMdd"));
        		user.setAuthorizeFeeType(authorizeFeeType);
        		user.setAuthorizeFeeStartDate(FormatHelper.formatStr2Date(authorizeFeeStartDate, "yyyyMMdd"));
        		user.setAuthorizeFeeEndDate(FormatHelper.formatStr2Date(authorizeFeeEndDate, "yyyyMMdd"));
        		user.setAuthorizeRepayType(authorizeRepayType);
        		user.setAuthorizeRepayStartDate(FormatHelper.formatStr2Date(authorizeRepayStartDate, "yyyyMMdd"));
        		user.setAuthorizeRepayEndDate(FormatHelper.formatStr2Date(authorizeRepayEndDate, "yyyyMMdd"));
        		user.setUserAuthorizeFlag(UserAuthorizeFlag.ENABLE);
    		}*/
    	    
    		Map<String, String> chargeAccountMap = doubleDryService.queryChargeAccount(map);
        	chargeAccount1 =chargeAccountMap.get("ChargeAccount");
        	accountName1 =chargeAccountMap.get("AccountName");
    	    
    	}
    	
    	if(null != account2 && StringUtils.isNotEmpty(account2.getData().getAccountNo())){
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("PlaCustId", account2.getData().getAccountNo());
    		/*Map<String, String> authInfMap = doubleDryService.queryAuthorizeInfo(map);
    		String authorizeInvestType=authInfMap.get("authorizeInvestType");
            String authorizeInvestStartDate=authInfMap.get("authorizeInvestStartDate");
            String authorizeInvestEndDate=authInfMap.get("authorizeInvestEndDate");
    		String authorizeFeeType=authInfMap.get("authorizeFeeType");
    		String authorizeFeeStartDate=authInfMap.get("authorizeFeeStartDate");
    		String authorizeFeeEndDate=authInfMap.get("authorizeFeeEndDate");
    		String authorizeRepayType=authInfMap.get("authorizeRepayType");
    		String authorizeRepayStartDate=authInfMap.get("authorizeRepayStartDate");
    		String authorizeRepayEndDate=authInfMap.get("authorizeRepayEndDate");
    		if(StringUtils.isNotEmpty(authorizeInvestType) || StringUtils.isNotEmpty(authorizeFeeType) 
    				|| StringUtils.isNotEmpty(authorizeRepayType)){
    			UserDO userDo = new UserDO();
    			userDo.setUserId(user.getUserId());
    			userDo.setAuthorizeInvestType(authorizeInvestType);
        		userDo.setAuthorizeInvestStartDate(FormatHelper.formatStr2Date(authorizeInvestStartDate, "yyyyMMdd"));
        		userDo.setAuthorizeInvestEndDate(FormatHelper.formatStr2Date(authorizeInvestEndDate, "yyyyMMdd"));
        		userDo.setAuthorizeFeeType(authorizeFeeType);
        		userDo.setAuthorizeFeeStartDate(FormatHelper.formatStr2Date(authorizeFeeStartDate, "yyyyMMdd"));
        		userDo.setAuthorizeFeeEndDate(FormatHelper.formatStr2Date(authorizeFeeEndDate, "yyyyMMdd"));
        		userDo.setAuthorizeRepayType(authorizeRepayType);
        		userDo.setAuthorizeRepayStartDate(FormatHelper.formatStr2Date(authorizeRepayStartDate, "yyyyMMdd"));
        		userDo.setAuthorizeRepayEndDate(FormatHelper.formatStr2Date(authorizeRepayEndDate, "yyyyMMdd"));
        		userDo.setUserAuthorizeFlag(UserAuthorizeFlag.ENABLE.getState());
        		userDao.updateAuthorizeByUserId(userDo);
        		user.setAuthorizeInvestType(authorizeInvestType);
        		user.setAuthorizeInvestStartDate(FormatHelper.formatStr2Date(authorizeInvestStartDate, "yyyyMMdd"));
        		user.setAuthorizeInvestEndDate(FormatHelper.formatStr2Date(authorizeInvestEndDate, "yyyyMMdd"));
        		user.setAuthorizeFeeType(authorizeFeeType);
        		user.setAuthorizeFeeStartDate(FormatHelper.formatStr2Date(authorizeFeeStartDate, "yyyyMMdd"));
        		user.setAuthorizeFeeEndDate(FormatHelper.formatStr2Date(authorizeFeeEndDate, "yyyyMMdd"));
        		user.setAuthorizeRepayType(authorizeRepayType);
        		user.setAuthorizeRepayStartDate(FormatHelper.formatStr2Date(authorizeRepayStartDate, "yyyyMMdd"));
        		user.setAuthorizeRepayEndDate(FormatHelper.formatStr2Date(authorizeRepayEndDate, "yyyyMMdd"));
        		user.setUserAuthorizeFlag(UserAuthorizeFlag.ENABLE);
    		}*/
    	    if(user.getUserType().type==1){
        		Map<String, String> chargeAccountMap = doubleDryService.queryChargeAccount(map);
            	chargeAccount2 =chargeAccountMap.get("ChargeAccount");
            	accountName2 =chargeAccountMap.get("AccountName");
    	    }else{
    	    	chargeAccount2 =account2.getData().getAccountUserChargeAccount();
            	accountName2 =account2.getData().getAccountUserChargeName();
    	    }
    	}
    	String chargeAccount="";
    	String accountName="";
    	if(chargeAccount1!=null && !chargeAccount1.equals("")){
    		chargeAccount = chargeAccount1;
    		accountName = accountName1;
    	}else if(chargeAccount2!=null && !chargeAccount2.equals("")){
    		chargeAccount = chargeAccount2;
    		accountName = accountName2;
    	}
    	
    	//投标授权类型 11、投标 59、缴费 60、还款
    	String authorizeInvest="";
    	String authorizeFee="";
    	String authorizeRepay="";
    	if("11".equals(user.getAuthorizeInvestType())){
    		authorizeInvest=AuthorizeUtil.isAuthorize(user.getAuthorizeInvestStartDate(),user.getAuthorizeInvestEndDate());
    	}
    	if("59".equals(user.getAuthorizeFeeType())){
    		authorizeFee=AuthorizeUtil.isAuthorize(user.getAuthorizeFeeStartDate(),user.getAuthorizeFeeEndDate());
    	}
    	if("60".equals(user.getAuthorizeRepayType())){
    		authorizeRepay=AuthorizeUtil.isAuthorize(user.getAuthorizeRepayStartDate(),user.getAuthorizeRepayEndDate());
    	}
    	
//    	String MoneyMoreMoreUrl = SystemGetPropeties.getStrString("submiturlprefix");
//    	context.put("MoneyMoreMoreUrl", MoneyMoreMoreUrl);
    	context.put("authorizeInvest",authorizeInvest);
    	context.put("authorizeFee", authorizeFee);
    	context.put("authorizeRepay",authorizeRepay);
    	context.put("user", user);
    	context.put("account1", account1.getData());
    	context.put("chargeAccount1",chargeAccount1);
    	context.put("accountName1",accountName1);
    	context.put("account2", account2.getData());
    	context.put("chargeAccount",chargeAccount);
    	context.put("accountName",accountName);
    	context.put("RealNameFlg",RealNameFlg);
    	context.put("ChargeAmt",ChargeAmt);
    	
    	context.put("tab_name",tab_name);
    }
}
