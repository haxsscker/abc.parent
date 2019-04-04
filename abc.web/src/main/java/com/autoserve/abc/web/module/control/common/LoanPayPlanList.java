package com.autoserve.abc.web.module.control.common;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;

/**
 * 类LoanPayPlanList.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月19日 下午12:05:29
 */
public class LoanPayPlanList {

    public void execute(Context context, @Param("loanId") int loanId) {
    	if(loanId!=0){
    		context.put("loanId", loanId);
    	}
        
    }
}
