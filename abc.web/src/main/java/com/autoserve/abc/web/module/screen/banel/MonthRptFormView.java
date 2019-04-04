package com.autoserve.abc.web.module.screen.banel;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.MonthReportDO;
import com.autoserve.abc.service.biz.intf.banel.MonthRptService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class MonthRptFormView {

	@Resource
	private MonthRptService monthRptService;
	
	public void execute(Context context, ParameterParser params) {
		int id = params.getInt("id");
		int type = params.getInt("type");
    	if(id != 0){
	        PlainResult<MonthReportDO> result = this.monthRptService.queryById(id);
	        context.put("monthRpt", result.getData());
	        context.put("type", type);
        }
	}

}
