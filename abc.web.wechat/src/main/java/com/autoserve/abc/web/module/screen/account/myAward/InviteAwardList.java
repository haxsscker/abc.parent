package com.autoserve.abc.web.module.screen.account.myAward;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.InviteJDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.invite.InviteService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;

public class InviteAwardList {
	@Autowired
    private HttpSession session;
	@Autowired
	private DeployConfigService deployConfigService;
	@Resource
	private UserService userService;
	@Resource
	private HttpServletRequest  request;
	@Resource
	private InviteService inviteService;
	
	
	public void execute(Context context, ParameterParser params, Navigator nav) {
		User user=(User) session.getAttribute("user");
		
		//推荐展示
		int currentPage = params.getInt("currentPage");
		if(currentPage==0)
			currentPage=1;
		int pageSize=10;
		PageCondition pageCondition = new PageCondition(currentPage,pageSize);
		InviteJDO inviteJDO=new InviteJDO();
		inviteJDO.setInviteUserId(user.getUserId());
		PageResult<InviteJDO>  result=inviteService.queryList(inviteJDO, pageCondition);
		Pagebean<InviteJDO>  pagebean = new Pagebean<InviteJDO>(currentPage,pageSize,result.getData(),result.getTotalCount());
		context.put("pagebean", pagebean);
	}
}
