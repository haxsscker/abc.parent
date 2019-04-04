package com.autoserve.abc.web.module.screen.selfprove;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.CompanyCustomerDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.convert.CompanyCustomerConverter;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.CompanyCustomer;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.user.CompanyCustomerService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.CompanyCustomerVOConverter;
import com.autoserve.abc.web.vo.company.CompanyCustomerVO;

public class OpenAccountForm {

	@Resource
	private CompanyCustomerService companyCustomerService;
	@Resource
	private AccountInfoService accountInfoService;
	@Resource
	private UserService userService;
	
	public void execute(Context context, ParameterParser params) {
		Integer cinId=params.getInt("cinId");
		if (cinId != 0) {
			PlainResult<CompanyCustomerDO> plainResult = companyCustomerService
					.findById(cinId);
			if (plainResult.isSuccess()) {
				CompanyCustomer companyCustomer = CompanyCustomerConverter
						.toCompanyCustomer(plainResult.getData());
				CompanyCustomerVO companyCustomerVO = CompanyCustomerVOConverter
						.convertToCompanyCustomerVO(companyCustomer);
				Integer userId = companyCustomerVO.getCcUserid();
				User user = userService.findEntityById(userId).getData();	
				if (user.getUserBusinessState() != null
						&& user.getUserBusinessState().getState() == 2) {
					UserIdentity userIdentity = new UserIdentity();
			        userIdentity.setUserId(userId);
			        if (user.getUserType() == null || user.getUserType().getType() == 1) {
			            user.setUserType(UserType.PERSONAL);
			        } else {
			            user.setUserType(UserType.ENTERPRISE);
			        }
			        userIdentity.setUserType(user.getUserType());
			        PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
			        context.put("account", account);
				}
				context.put("companyCustomerVO", companyCustomerVO);
			}
		}
	}
}
