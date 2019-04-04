package com.autoserve.abc.web.module.screen.account;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.web.helper.LoginUserUtil;

/**
 * 提现绑卡
 * 
 * @author Administrator
 *
 */
public class WithdrawCash {

	@Autowired
	private AccountInfoService accountInfoService;
	@Resource
	private DoubleDryService doubleDryService;
	@Resource
	private SysConfigService sysConfigService;
	@Autowired
	private BankInfoService bankInfoService;

	public void execute(Context context, ParameterParser params, Navigator nav) {
		Integer empId = LoginUserUtil.getEmpId();
		Integer orgId = LoginUserUtil.getEmpOrgId();
		Double[] moneys = null;
		String platform = sysConfigService
				.querySysConfig(SysConfigEntry.PLATFORM_ACCOUNT).getData()
				.getConfValue();
		UserType userType = null;
		if (orgId == null) {
			// 平台
			moneys = doubleDryService.queryBalance(platform, "2");
			userType = UserType.PLATFORM;
		} else {
			// 担保
			UserIdentity userIdentity = new UserIdentity();
			userIdentity.setUserId(empId);
			userIdentity.setUserType(UserType.PARTNER);
			String accMark = accountInfoService.queryByUserId(userIdentity)
					.getData().getAccountMark();
			moneys = doubleDryService.queryBalance(accMark, "1");
			userType = UserType.PARTNER;
		}
		context.put("avaiMoney", new BigDecimal(moneys[1] + ""));
		
		//银行卡
		BankInfo bankInfo = new BankInfo();
		bankInfo.setBankUserId(empId);
		bankInfo.setBankUserType(userType.getType());
		bankInfo.setCardStatus(CardStatus.STATE_ENABLE);
		List<BankInfoDO> bankList = bankInfoService.findBankInfo(bankInfo);
		context.put("banklist", bankList);
		
//		String MoneyMoreMoreUrl = SystemGetPropeties
//				.getStrString("submiturlprefix");
//		context.put("MoneyMoreMoreUrl", MoneyMoreMoreUrl);
	}

}
