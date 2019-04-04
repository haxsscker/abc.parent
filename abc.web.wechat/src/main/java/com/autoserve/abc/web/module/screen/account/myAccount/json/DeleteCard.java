package com.autoserve.abc.web.module.screen.account.myAccount.json;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.CardStatus;
import com.autoserve.abc.service.biz.impl.cash.thirdparty.chinaPnr.data.HuiFuData;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;
import com.autoserve.abc.web.helper.DeployConfigService;

public class DeleteCard {
	@Autowired
	private DeployConfigService deployConfigService;
	@Autowired
	private HttpSession session;
	@Resource
	private BankInfoService bankinfoservice;
	@Resource
	private HttpServletRequest request;
	@Resource
	private AccountInfoService accountInfo;

	public BaseResult execute(Context context, ParameterParser params,
			Navigator nav) {
		// 登录URL
		User user = (User) session.getAttribute("user");
		if (user == null) {
			nav.forwardTo(deployConfigService.getLoginUrl(request));
			return null;
		}
		String bankid = params.getString("bankid");
		BaseResult cardresult = new BaseResult();
		cardresult.setSuccess(false);
		/* 验证删除的卡号是否为该用户的 */
		Boolean checkresult = false;
		if (bankid != null && !"".equals(bankid)) {
			PlainResult<BankInfoDO> checkUserResult = bankinfoservice
					.queryListBankInfoById(Integer.valueOf(bankid));
			if (checkUserResult.isSuccess()
					&& checkUserResult.getData() != null) {
				Integer carduserid = checkUserResult.getData().getBankUserId();
				if (carduserid != null) {
					if (carduserid.intValue() == user.getUserId().intValue())
						checkresult = true;
				}
			}
		}
		if (checkresult) {
			BankInfo bankinfo = new BankInfo();
			bankinfo.setBankId(Integer.valueOf(bankid));
			bankinfo.setCardStatus(CardStatus.STATE_DISABLE);
			cardresult = bankinfoservice.modifyBankInfo(bankinfo);
		}
		return cardresult;
	}
}
