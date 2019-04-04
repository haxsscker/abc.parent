package com.autoserve.abc.web.module.screen.test;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.message.mail.SendMailService;

public class ContractTest1 {
	 @Resource
	 private SendMailService    sendMailService;
	 
	 public void execute(Context context, ParameterParser params) {
		 int loanId = params.getInt("loanId");
		 String loanName = params.getString("loanName");
		 sendMailService.sendMailToCreditoUser(loanId, loanName);

	 }
}
