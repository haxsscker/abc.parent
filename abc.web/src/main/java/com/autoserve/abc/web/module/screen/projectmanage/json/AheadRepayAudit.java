package com.autoserve.abc.web.module.screen.projectmanage.json;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.loan.repay.AheadRepayService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.LoginUserUtil;

public class AheadRepayAudit {
	
	@Autowired
	private AheadRepayService aheadRepayService;
	
	public BaseResult execute(ParameterParser params) {
		int id = params.getInt("id");
		boolean pass = params.getBoolean("pass");
		String auditOpinion = params.getString("auditOpinion");
		int empId = LoginUserUtil.getEmpId();
		BaseResult result = aheadRepayService.audit(id, empId, pass, auditOpinion);
		return result;
	}
}
