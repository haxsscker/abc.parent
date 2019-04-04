package com.autoserve.abc.web.module.screen.reportAnalysis;

import java.util.List;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.RedenvelopeType;
import com.google.common.collect.Lists;

public class RedReportView {
	public void execute(Context context){
		//不显示项目奖励红包
		RedenvelopeType[] types = RedenvelopeType.values();
		List<RedenvelopeType> types2 = Lists.newArrayList();
		for(int i=0; i<types.length; i++){
			if(types[i]!=RedenvelopeType.PROJECT_RED){
				types2.add(types[i]);
			}
		}
		context.put("types", types2);
		//context.put("redStates", RedState.values());
	}
}
