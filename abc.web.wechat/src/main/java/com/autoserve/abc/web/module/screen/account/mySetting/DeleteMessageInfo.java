package com.autoserve.abc.web.module.screen.account.mySetting;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;

public class DeleteMessageInfo
{
	@Resource
	private SysMessageInfoService messageInfoService;
	public void execute(Context context,Navigator nav, ParameterParser params) {
		
		String ids =  params.getString("ids");
		if(ids.contains(","))
		{
			String [] messageInfoids =  ids.split(",");
			for(int i=0;i<messageInfoids.length;i++)
			{
				int messageId = Integer.parseInt(messageInfoids[i]);
				messageInfoService.removeById(messageId);
			}
		}
		nav.forwardTo("/account/mySetting/platformLetter").end();
	}
}
