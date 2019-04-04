package com.autoserve.abc.web.module.screen.account.mySetting;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.intf.sys.SysMessageReplyService;

public class ReplyMessage
{
	@Resource 
	private SysMessageReplyService sysMessageReplyService;
	public void execute(Context context,Navigator nav, ParameterParser params) {
		String replyids = params.getString("replyids");
		String [] ids ;
		if(replyids.contains(","))
		{
			ids = replyids.split(",");
			for(int i=0;i<ids.length;i++)
			{
				//sysMessageReplyService
			}
		}
	}
}
