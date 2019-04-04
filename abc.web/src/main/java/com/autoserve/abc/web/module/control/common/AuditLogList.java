package com.autoserve.abc.web.module.control.common;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
/**
 * 审核历史
 * @author zhangkang
 *
 */
public class AuditLogList {
	public void execute(Context context, @Param("loanId") String loanId) {
		context.put("loanId", loanId);
	}
}
