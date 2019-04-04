package com.autoserve.abc.web.module.screen.account.mySetting;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.dataobject.SysMessageReplyDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.sys.SysMessageReplyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.helper.DeployConfigService;

public class ReplyMessage {
	@Resource
	private SysMessageReplyService sysMessageReplyService;
	@Autowired
	private HttpSession session;
	 @Resource
	    private HttpServletRequest    request;
	 @Autowired
	    private DeployConfigService   deployConfigService;

	public BaseResult execute(@Params SysMessageReplyDO reply,Navigator nav) {
		User user = (User) session.getAttribute("user");
		reply.setSysUserId(user.getUserId());
		BaseResult result = this.sysMessageReplyService.createMessage(reply);
		return result;
	}
}
