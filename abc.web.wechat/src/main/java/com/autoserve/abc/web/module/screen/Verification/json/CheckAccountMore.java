package com.autoserve.abc.web.module.screen.Verification.json;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonBaseVO;
/**
 * 借款校验是否实名认证、开户、授权
 * @author Administrator
 *
 */
public class CheckAccountMore {
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
		 User user=(User)session.getAttribute("user");
		 String id = params.getString("id");
		 Integer type = params.getInt("type");
		 if(user!=null){
			PlainResult<UserDO> userDoResult=userService.findById(user.getUserId());
			if(id.equals("1")){
				userDoResult.getData().setAccountCategory(AccountCategory.valueOf(type).getType());
				BaseResult res=accountInfoService.isOpenAccount(userDoResult.getData());
				if(!res.isSuccess()){
					result.setSuccess(false);
					result.setMessage(res.getMessage());
					result.setRedirectUrl("/account/myAccount/accountOverview");
				}
			}else{
				result.setSuccess(true);
				result.setMessage("验证通过！");
			}
		}else{
			result.setSuccess(false);
			result.setMessage("您还没有登录,请先登录");
			result.setRedirectUrl("/login/login");
		}		
		
		 return result;
		 
	 }
}


