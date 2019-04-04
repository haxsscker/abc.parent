package com.autoserve.abc.web.module.screen.account.mySetting;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.dataobject.SysMessageReplyDO;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.intf.sys.SysMessageReplyService;
import com.autoserve.abc.service.biz.result.PageResult;
/**
 * 信箱详细
 * @author Administrator
 *
 */
public class LetterDetail {
	
	@Resource
	private SysMessageInfoService msgInfoService;
	@Resource
	private SysMessageReplyService msgReplyService;
	
	public void execute(Context context, ParameterParser params, Navigator nav) {
		Integer msgInfoId = params.getInt("messageInfoId");
		
		// 更新该信息已读
		SysMessageInfoDO msgInfoUpdate = new SysMessageInfoDO();
		msgInfoUpdate.setSysMessageId(msgInfoId);
		msgInfoUpdate.setSysRead("1"); 
		msgInfoService.updateMessage(msgInfoUpdate);
		
		//查询
		SysMessageInfoDO messageInfo = msgInfoService.queryMesageById(msgInfoId);
		PageResult<SysMessageReplyDO> replyResult = msgReplyService.queryByMessageId(msgInfoId, new PageCondition(1, 1));
		List<SysMessageReplyDO> replyList = replyResult.getData();
		if(replyList!=null && replyList.size()>=1){
			context.put("reply", replyList.get(0));
		}
		context.put("msgInfo", messageInfo);
	}
}
