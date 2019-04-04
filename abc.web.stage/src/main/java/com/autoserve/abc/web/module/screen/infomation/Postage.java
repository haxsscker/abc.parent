package com.autoserve.abc.web.module.screen.infomation;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;

public class Postage {
	@Autowired
	private HttpSession 		session;
	@Resource
	private LoanQueryService    loanQueryService;

	public void execute(Context context, ParameterParser params) {
		
		//提前还款概况
		context.put("aheadPaySummary", loanQueryService.queryAheadPay());
	}
}
