package com.autoserve.abc.web.module.screen.account.myAward;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;

public class UsageScore {
	@Resource
	HttpServletRequest request;
	public void execute(Context context,ParameterParser params){
//		Integer usageScore = params.getInt("useableScore");
		String useableScore = request.getParameter("useableScore");
//		System.out.println(usageScore);
		System.out.println(useableScore);
		context.put("useableScore", useableScore);
	}
}
