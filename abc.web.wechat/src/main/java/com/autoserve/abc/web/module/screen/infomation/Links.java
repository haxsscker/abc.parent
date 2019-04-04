package com.autoserve.abc.web.module.screen.infomation;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.SysLinkInfoDO;
import com.autoserve.abc.service.biz.intf.sys.SysLinkInfoService;
import com.autoserve.abc.service.biz.result.ListResult;

public class Links
{

	@Resource
	private SysLinkInfoService sysLinkInfoService;
	
    public void execute(Context context, ParameterParser params) {
    	
    	ListResult<SysLinkInfoDO> linkResult = sysLinkInfoService.queryAllList();
    	if(linkResult.isSuccess()) {
    		context.put("links", linkResult.getData());
    	}
    	
    }
}
