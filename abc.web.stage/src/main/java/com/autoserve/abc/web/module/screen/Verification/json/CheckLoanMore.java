package com.autoserve.abc.web.module.screen.Verification.json;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.AuthorizeUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonBaseVO;
/**
 * 借款校验是否实名认证、开户、授权
 * @author Administrator
 *
 */
public class CheckLoanMore {
	@Resource
	private HttpSession session;
	@Resource
	private UserService userService;
	@Resource
    private AccountInfoService  accountInfoService;
	@Resource
	private CompanyCustomerService companyCustomerService;
	public JsonBaseVO execute(Context context, ParameterParser params) throws ParseException{
		 JsonBaseVO result = new JsonBaseVO();
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 Date currentTime=formatter.parse(formatter.format(new Date())); //这是获取当前时间
		 User user=(User)session.getAttribute("user");
		 String id = params.getString("id");
		 BigDecimal loanMoney = BigDecimal.ZERO;//借款金额
		 if(id==null){
			 id="";
		 }
		 if(StringUtils.isNotEmpty(params.getString("loanMoney"))){
			 loanMoney = new BigDecimal(params.getString("loanMoney"));//借款金额
		 }
		if(user!=null){
			PlainResult<UserDO> userDoResult=userService.findById(user.getUserId());
			//实名认证标志
//			Integer UserRealnameIsproven= userDoResult.getData().getUserRealnameIsproven();
			//缴费授权
			Date authorizeFeeStartDate=userDoResult.getData().getAuthorizeFeeStartDate();
			Date authorizeFeeEndDate=userDoResult.getData().getAuthorizeFeeEndDate();
			BigDecimal authorizeFeeAmount=null != userDoResult.getData().getAuthorizeFeeAmount()?userDoResult.getData().getAuthorizeFeeAmount():BigDecimal.ZERO;
			//还款授权
			Date authorizeRepayStartDate=userDoResult.getData().getAuthorizeRepayStartDate();
			Date authorizeRepayEndDate=userDoResult.getData().getAuthorizeRepayEndDate();
			BigDecimal authorizeRepayAmount=null != userDoResult.getData().getAuthorizeRepayAmount()?userDoResult.getData().getAuthorizeRepayAmount():BigDecimal.ZERO;
			
			//真实姓名
			String userRealName=userDoResult.getData().getUserRealName();
			//身份证号
			String userDocNo=userDoResult.getData().getUserDocNo();
			//判断是否是企业用户
			Integer usrtype=userDoResult.getData().getUserType();    //用户类型
			String ccCompanyName="";   //企业名
			String ccLicenseNo="";  //营业执照号
			if(userDoResult.getData()!=null && usrtype!=null &&  usrtype==2){
				PlainResult<CompanyCustomerDO> companyCustomer=companyCustomerService.findByUserId(userDoResult.getData().getUserId());
				ccCompanyName=companyCustomer.getData().getCcCompanyName();
				ccLicenseNo=companyCustomer.getData().getCcLicenseNo();
			}
				if(id.equals("3")||id.equals("19")){
						if((usrtype==2 && (ccCompanyName==null || "".equals(ccCompanyName) || ccLicenseNo==null || "".equals(ccLicenseNo)))){
							result.setSuccess(false);
							result.setMessage("您还没有录入实名认证信息,请先录入！");
							result.setRedirectUrl("/account/myAccount/basicInformation");
						}else{
							result.setSuccess(true);
							result.setMessage("验证通过！");
						}
				}else if(id.equals("4")||id.equals("5")||id.equals("6")||id.equals("7")||id.equals("8")
						||id.equals("9")||id.equals("10")||id.equals("16")||id.equals("20")
						||id.equals("12")||id.equals("13")||id.equals("14")||id.equals("18")){
					if((usrtype==2 && (ccCompanyName==null || "".equals(ccCompanyName) || ccLicenseNo==null || "".equals(ccLicenseNo)))){
							result.setSuccess(false);
							result.setMessage("您还没有录入实名认证信息,请先录入！");
							result.setRedirectUrl("/account/myAccount/basicInformation");
						}else 
							//if(userDoResult.getData().getUserBusinessState()==null ||userDoResult.getData().getUserBusinessState()<2){
						if(id.equals("7")||id.equals("8")||id.equals("9")||id.equals("10")){//我的投资
							userDoResult.getData().setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
							BaseResult res=accountInfoService.isOpenAccount(userDoResult.getData());
							if(!res.isSuccess()){
								result.setSuccess(false);
								result.setMessage(res.getMessage());
								result.setRedirectUrl("/account/myAccount/bindAccount");
							}
						}else if(id.equals("12")||id.equals("13")||id.equals("14")||id.equals("18")){//我的借款
							userDoResult.getData().setAccountCategory(AccountCategory.LOANACCOUNT.getType());
							BaseResult res=accountInfoService.isOpenAccount(userDoResult.getData());
							if(!res.isSuccess()){
								result.setSuccess(false);
								result.setMessage(res.getMessage());
								result.setRedirectUrl("/account/myAccount/bindAccount");
							}							
						}else if(id.equals("4")||id.equals("5")||id.equals("6")||id.equals("16")){//充值 提现 资金流水 绑卡
							if(id.equals("6")&&usrtype==2){
								userDoResult.getData().setAccountCategory(AccountCategory.LOANACCOUNT.getType());
								BaseResult res=accountInfoService.isOpenAccount(userDoResult.getData());
								if(!res.isSuccess() && CommonResultCode.NO_LOAN_ACCOUNT.message.equals(res.getMessage())){
									result.setSuccess(false);
									result.setMessage(res.getMessage());
									result.setRedirectUrl("/account/myAccount/bindAccount");
								}	
							}else{
								BaseResult res=accountInfoService.isOpenAccount(userDoResult.getData());
								if(!res.isSuccess() && CommonResultCode.NO_INVEST_LOAN_ACCOUNT.message.equals(res.getMessage())){
									result.setSuccess(false);
									result.setMessage(res.getMessage());
									result.setRedirectUrl("/account/myAccount/bindAccount");
								}	
							}
													
						}else if(id.equals("20")){//融资账户转账
							BaseResult res=accountInfoService.isOpenAccount(userDoResult.getData());
							if(!res.isSuccess()){
								result.setSuccess(false);
								result.setMessage(res.getMessage());
								result.setRedirectUrl("/account/myAccount/bindAccount");
							}							
						}else{
							result.setSuccess(true);
							result.setMessage("验证通过！");
						}
				}else{
					userDoResult.getData().setAccountCategory(AccountCategory.LOANACCOUNT.getType());
					BaseResult res=accountInfoService.isOpenAccount(userDoResult.getData());
					if((usrtype==2 && (ccCompanyName==null || "".equals(ccCompanyName) || ccLicenseNo==null || "".equals(ccLicenseNo)))){
							result.setSuccess(false);
							result.setMessage("您还没有录入实名认证信息,请先录入！");
							result.setRedirectUrl("/account/myAccount/basicInformation");
						}else 
							//if(userDoResult.getData().getUserBusinessState()==null ||userDoResult.getData().getUserBusinessState()<2){
						if(!res.isSuccess()){
							result.setSuccess(false);
							result.setMessage(res.getMessage());
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}else if(!"59".equals(userDoResult.getData().getAuthorizeFeeType())){
							result.setSuccess(false);
							result.setMessage("您还未开启缴费授权，请先去授权！");
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}else if(!"有效".equals(AuthorizeUtil.isAuthorize(authorizeFeeStartDate,authorizeFeeEndDate))){
							result.setSuccess(false);
							result.setMessage("缴费授权"+AuthorizeUtil.isAuthorize(authorizeFeeStartDate,authorizeFeeEndDate)+",请去修改！");
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}else if(!"60".equals(userDoResult.getData().getAuthorizeRepayType())){
							result.setSuccess(false);
							result.setMessage("您还未开启还款授权，请先去授权！");
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}else if(!"有效".equals(AuthorizeUtil.isAuthorize(authorizeRepayStartDate,authorizeRepayEndDate))){
							result.setSuccess(false);
							result.setMessage("还款授权"+AuthorizeUtil.isAuthorize(authorizeRepayStartDate,authorizeRepayEndDate)+",请去修改！");
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}else if(loanMoney.doubleValue()>authorizeRepayAmount.doubleValue()){
							result.setSuccess(false);
							result.setMessage("借款金额超过您的还款授权金额,请去修改！");
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}else{
							result.setSuccess(true);
							result.setMessage("验证通过！");
						}
				}
		}else{
			result.setSuccess(false);
			result.setMessage("您还没有登录,请先登录");
			result.setRedirectUrl("/login/login");
		}
		 return result;
		 
	 }
}


