package com.autoserve.abc.web.module.control.common;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
/**
 * 业务流程
 *
 */
public class ProjectTraceRecord {
	public void execute(Context context, @Param("loanId") String loanId) {
		context.put("loanId", loanId);
	}
}
