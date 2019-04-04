package com.autoserve.abc.web.module.screen.message;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysMessageReplyDO;
import com.autoserve.abc.service.biz.intf.sys.SysMessageReplyService;
import com.autoserve.abc.service.biz.result.PageResult;

public class SeeReply {
	@Resource
	private SysMessageReplyService msgReplyService;

	public void execute(Context context, ParameterParser params, Navigator nav) {
		Integer msgInfoId = params.getInt("msgInfoId");
		PageResult<SysMessageReplyDO> replyResult = msgReplyService
				.queryByMessageId(msgInfoId, new PageCondition(1, 1));
		List<SysMessageReplyDO> replyList = replyResult.getData();
		if (replyList != null && replyList.size() >= 1) {
			context.put("reply", replyList.get(0));
		}
	}
}
