package com.autoserve.abc.web.module.screen.account.myAccount;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.AssessLevelDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserAssessService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;

public class UserAssessLevel {
	
	@Autowired
	private DeployConfigService deployConfigService;
	
	@Autowired
    private HttpSession session;
	
	@Resource
	private UserService userService;

	@Resource
	private UserAssessService userAssessService;
	
	@Resource
	private HttpServletRequest request;
  
	public void execute(Context context, ParameterParser params,Navigator nav) {
    	User user=(User) session.getAttribute("user");
    	if(user==null){
    		nav.forwardTo(deployConfigService.getLoginUrl(request));
    		return;
    	}
    	
    	PlainResult<UserDO> userDO = this.userService.findById(user.getUserId());
    	context.put("user", userDO.getData());
    	
    	AssessLevelDO assLevelDO = null;
    	if (0 != userDO.getData().getAssId())
    	{
    		assLevelDO = userAssessService.findById(userDO.getData().getAssId());
    	}
    	context.put("assLevel", assLevelDO);
    }
}
