package com.autoserve.abc.web.module.screen.reportAnalysis;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;

/**
 * 用户资金明细
 * 
 */
public class CustomerAgenciesLookUpView {
    
    public void execute(@Param("userId") Integer userId,@Param("accountNo") String accountNo, ParameterParser params,Context context) {

		
   		context.put("userId", userId);
   		context.put("accountNo", accountNo);
    }
}
