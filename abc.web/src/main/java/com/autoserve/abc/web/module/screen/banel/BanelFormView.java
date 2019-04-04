package com.autoserve.abc.web.module.screen.banel;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.BanelDO;
import com.autoserve.abc.service.biz.intf.banel.BanelService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class BanelFormView {

	@Resource
	private BanelService banelService;
	
	public void execute(Context context, ParameterParser params) {
		int id = params.getInt("id");
    	if(id != 0){
	        PlainResult<BanelDO> result = this.banelService.queryById(id);
	        context.put("banel", result.getData());
        }
	}

}
