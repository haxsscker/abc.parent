package com.autoserve.abc.web.module.screen.account.myLoan.json;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.service.biz.intf.loan.repay.AheadRepayService;
import com.google.common.collect.Maps;

public class GetAheadRepayData {
	
	@Autowired
	private AheadRepayService aheadRepayService;
	@Autowired
	private PaymentPlanDao paymentPlanDao;
	
	public Map<String, Object> execute(Context context, ParameterParser params,@Param("loanId") Integer loanId) {
		Map<String, Object> map = Maps.newHashMap();
		List<PaymentPlanDO> paymentList = paymentPlanDao
				.findByLoanId(loanId);
		BigDecimal shouldCapital = aheadRepayService.calcShouldPayCapital(paymentList);
		BigDecimal shouldInterest = aheadRepayService.calcShouldPayInterest(paymentList);
		BigDecimal shouldServeFee = aheadRepayService.calcShouldPayServeFee(paymentList);
		map.put("shouldCapital", shouldCapital);
		map.put("shouldInterest", shouldInterest);
		map.put("shouldServeFee", shouldServeFee);
		map.put("shouldAll", shouldCapital.add(shouldInterest).add(shouldServeFee));
		return map;
	}
}
