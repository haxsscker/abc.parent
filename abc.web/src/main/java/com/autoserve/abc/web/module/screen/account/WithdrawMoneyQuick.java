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
import com.autoserve.abc.service.biz.entity.DealReturn;
import com.autoserve.abc.service.biz.entity.InnerSeqNo;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DealRecordService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.ToCashService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;

public class WithdrawMoneyQuick {
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
		String mey = params.getString("money");

		Integer empId = LoginUserUtil.getEmpId();
		Integer orgId = LoginUserUtil.getEmpOrgId();

		

		InnerSeqNo innerseqno = InnerSeqNo.getInstance();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("OrderNo", innerseqno.toString());
		map.put("Amount", mey);
		map.put("FeePercent", "0");
		map.put("CardNo", CardNo);
		
		UserType userType = null;
		if (orgId == null) {
			userType = UserType.PLATFORM;
			map.put("FeePercent", 100);
		} else {
			userType = UserType.PARTNER;
		}

		PlainResult<DealReturn> paramMap = tocashservice.toCashOther(empId,
				userType, new BigDecimal(mey), map);

		JSONObject jo = JSON.parseObject(paramMap.getData().getParams());

		context.put("jo", jo);
	}
}
