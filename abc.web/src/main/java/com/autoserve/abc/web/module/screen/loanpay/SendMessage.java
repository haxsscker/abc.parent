package com.autoserve.abc.web.module.screen.loanpay;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.SmsNotifyDO;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;

public class SendMessage {

	@Autowired
	private PaymentPlanService paymentPlanService;

	public void execute(Context context, ParameterParser params) {
		int planPaymentId = params.getInt("planPaymentId");
		SmsNotifyDO sms = paymentPlanService.createSmsNotify(planPaymentId);
		context.put("sms", sms);
	}
}
