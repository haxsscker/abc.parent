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
import com.autoserve.abc.service.biz.impl.cash.StringUtil;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.bhyh.util.AuthorizeUtil;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonBaseVO;


/**
 * 投资校验是否实名认证、开户、授权
 * @author DS
 *
 * 2015年1月30日上午10:40:59
 */
public class CheckMoneyMoreMore {
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
		 String transTyp = params.getString("transTyp");//1 投资户,2 融资户
		 BigDecimal investMoney = BigDecimal.ZERO;//投资金额
		 if(id==null){
			 id="";
		 }
		 if(StringUtils.isNotEmpty(params.getString("investMoney"))){
			 investMoney = new BigDecimal(params.getString("investMoney"));//投资金额
		 }
		if(user!=null){
			PlainResult<UserDO> userDoResult=userService.findById(user.getUserId());
			//实名认证标志
//			Integer UserRealnameIsproven= userDoResult.getData().getUserRealnameIsproven();
			//投标授权有效开始日
			Date investStartDate=userDoResult.getData().getAuthorizeInvestStartDate();
			//投标授权有效结束日
			Date investEndDate=userDoResult.getData().getAuthorizeInvestEndDate();
			//投标授权金额
			BigDecimal authorizeInvestAmount=null != userDoResult.getData().getAuthorizeInvestAmount()?userDoResult.getData().getAuthorizeInvestAmount():BigDecimal.ZERO;
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
				if(id.equals("3")){
						if((usrtype==1 && (userRealName==null ||"".equals(userRealName) || userDocNo==null || "".equals(userDocNo))) ||
						(usrtype==2 && (ccCompanyName==null || "".equals(ccCompanyName) || ccLicenseNo==null || "".equals(ccLicenseNo)))){
							result.setSuccess(false);
							result.setMessage("您还没有录入实名认证信息,请先录入！");
							result.setRedirectUrl("/account/myAccount/basicInformation");
						}else{
							result.setSuccess(true);
							result.setMessage("验证通过！");
						}
				}else if(id.equals("4")||id.equals("5")||id.equals("6")||id.equals("16")||id.equals("20")){
					if(usrtype==2 && (ccCompanyName==null || "".equals(ccCompanyName) || ccLicenseNo==null || "".equals(ccLicenseNo))){
							result.setSuccess(false);
							result.setMessage("您还没有录入实名认证信息,请先录入！");
							result.setRedirectUrl("/account/myAccount/basicInformation");
					}else 
						//if(userDoResult.getData().getUserBusinessState()==null ||userDoResult.getData().getUserBusinessState()<2){
					if(id.equals("4")||id.equals("5")||id.equals("6")||id.equals("16")){//充值 提现 资金流水 绑卡
						if(!StringUtil.isEmpty(transTyp)){
							if("1".equals(transTyp)){
								userDoResult.getData().setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
							}else if("2".equals(transTyp)){
								userDoResult.getData().setAccountCategory(AccountCategory.LOANACCOUNT.getType());
							}
						}
						if(usrtype==2){
							userDoResult.getData().setAccountCategory(AccountCategory.LOANACCOUNT.getType());
						}
						BaseResult res=accountInfoService.isOpenAccount(userDoResult.getData());
						if(!res.isSuccess()){
							result.setSuccess(false);
							result.setMessage(res.getMessage());
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}
						/*if(usrtype==1 && !res.isSuccess() && CommonResultCode.NO_INVEST_ACCOUNT.message.equals(res.getMessage())){
							result.setSuccess(false);
							result.setMessage(res.getMessage());
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}
						if(usrtype==1 && !res.isSuccess() && CommonResultCode.NO_INVEST_LOAN_ACCOUNT.message.equals(res.getMessage())){
							result.setSuccess(false);
							result.setMessage(res.getMessage());
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}
						if(usrtype==2 && !res.isSuccess() && CommonResultCode.NO_LOAN_ACCOUNT.message.equals(res.getMessage())){
							result.setSuccess(false);
							result.setMessage(res.getMessage());
							result.setRedirectUrl("/account/myAccount/bindAccount");
						}*/
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
					userDoResult.getData().setAccountCategory(AccountCategory.INVESTACCOUNT.getType());
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
					}/*else if(userDoResult.getData().getUserAuthorizeFlag()==null || userDoResult.getData().getUserAuthorizeFlag()==0){
						result.setSuccess(false);
						result.setMessage("您还未开启自动转账授权，请先去授权！");
						result.setRedirectUrl("/account/myAccount/bindAccount");
					}*/else if(!"11".equals(userDoResult.getData().getAuthorizeInvestType())){
						result.setSuccess(false);
						result.setMessage("您还未开启出借授权，请先去授权！");
						result.setRedirectUrl("/account/myAccount/bindAccount");
					}else if(!"有效".equals(AuthorizeUtil.isAuthorize(investStartDate,investEndDate))){
						result.setSuccess(false);
						result.setMessage("出借授权"+AuthorizeUtil.isAuthorize(investStartDate,investEndDate)+",请去修改！");
						result.setRedirectUrl("/account/myAccount/bindAccount");
					}else if(investMoney.doubleValue()>authorizeInvestAmount.doubleValue()){
						result.setSuccess(false);
						result.setMessage("投资金额超过您的出借授权金额,请去修改！");
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
