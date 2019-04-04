package com.autoserve.abc.web.module.screen.loanpay.json;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class SendMessageAction {
	@Autowired
	private PaymentPlanService paymentPlanService;

	public JsonBaseVO execute(Context context, ParameterParser params) {
		JsonBaseVO jsonBaseVO = new JsonBaseVO();
		int planPaymentId = params.getInt("planPaymentId");
		BaseResult result = paymentPlanService.repaymentSmsNotify(planPaymentId);
		jsonBaseVO.setMessage(result.getMessage());
		return jsonBaseVO;
	}

}
