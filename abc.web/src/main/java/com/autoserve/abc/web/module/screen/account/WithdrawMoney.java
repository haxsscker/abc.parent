package com.autoserve.abc.web.module.screen.account;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.BankInfo;
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.enums.CardType;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;

public class WithdrawMoney {
	@Autowired
	private HttpSession session;
	@Autowired
	private AccountInfoService accountInfoService;
	@Autowired
	private DealRecordService dealrecordservice;
	@Resource
	private DoubleDryService doubleDryService;
	@Resource
	private BankInfoService bankInfoService;
	@Resource
	private ToCashService tocashservice;
	@Resource
	private HttpServletRequest request;
	@Resource
	private UserService userService;

	public void execute(Context context, ParameterParser params, Navigator nav) {
		String CardNo = params.getString("CardNo");
		String Cardtype = params.getString("CardType");
		String BankCode = params.getString("BankCode");
		String BranchBankName = params.getString("BranchBankName");
		String Province = params.getString("Province");
		String City = params.getString("City");
		String mey = params.getString("money");

		Integer empId = LoginUserUtil.getEmpId();
		Integer orgId = LoginUserUtil.getEmpOrgId();

		UserType userType = null;
		if (orgId == null) {
			userType = UserType.PLATFORM;
		} else {
			userType = UserType.PARTNER;
		}

		InnerSeqNo innerseqno = InnerSeqNo.getInstance();
		Map<String, Object> map = new HashMap<String, Object>();

		// 绑定银行卡，用于快速提现
		BankInfo bankInfo = new BankInfo();
		bankInfo.setBankUserId(empId);
		bankInfo.setBankUserType(userType.type);
		bankInfo.setCardType(CardType.valueOf(Integer.parseInt(Cardtype)));
		bankInfo.setBankNo(CardNo);
		bankInfo.setBankName(BranchBankName);
		bankInfo.setBankCode(BankCode);
		bankInfo.setAreaCode(City);
		PlainResult<BankInfoDO> bankResult = bankInfoService
				.saveBankInfo(bankInfo);

		map.put("OrderNo", innerseqno.toString());
		map.put("Amount", mey);
		map.put("FeePercent", "0");
		map.put("CardNo", CardNo);
		map.put("CardType", Cardtype);
		map.put("BankCode", BankCode);
		map.put("BranchBankName", BranchBankName);
		map.put("Province", Province);
		map.put("City", City);
		map.put("Remark1", bankResult.getData().getBankId());// 用于returnUrl回调，将银行卡enable
		
		//平台提现优先使用免费提现额度
		if(userType.equals(UserType.PLATFORM)){
			map.put("FeePercent", "100");
		}

		PlainResult<DealReturn> paramMap = tocashservice.toCashOther(empId,
				userType, new BigDecimal(mey), map);

		JSONObject jo = JSON.parseObject(paramMap.getData().getParams());

		context.put("jo", jo);
	}
}
