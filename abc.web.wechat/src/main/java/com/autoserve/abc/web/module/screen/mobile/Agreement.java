package com.autoserve.abc.web.module.screen.mobile;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;

public class Agreement {
    public void execute(Context context, Navigator nav, ParameterParser params) {
        Integer loanId = params.getInt("loanId");
        Integer flagLoan = params.getInt("flagLoan");
        String fromKind = params.getString("fromKind");
        context.put("loanId", loanId);
        context.put("flagLoan", flagLoan);
        context.put("fromKind", fromKind);
    }
}
