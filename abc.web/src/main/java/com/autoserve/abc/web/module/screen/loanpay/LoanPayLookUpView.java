package com.autoserve.abc.web.module.screen.loanpay;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;

/**
 * 类实现描述
 * 
 * @author liuwei 2015年1月12日 下午8:36:09
 */
public class LoanPayLookUpView {
    public void execute(Context context, ParameterParser params, @Param("loanId") int loanId,
                        @Param("planId") int planId) {
        context.put("loanId", loanId);
        context.put("planId", planId);
    }

}
