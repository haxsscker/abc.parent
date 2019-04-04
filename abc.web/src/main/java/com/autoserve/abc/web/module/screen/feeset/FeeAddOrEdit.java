package com.autoserve.abc.web.module.screen.feeset;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.LoanCategory;

public class FeeAddOrEdit {
	public void execute(Context context, ParameterParser params) {
	context.put("loanCatogories", LoanCategory.values());
	}

}
