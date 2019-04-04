package com.autoserve.abc.web.module.screen.account.myAward;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;

public class ExchangeScore {
	@Autowired
	private HttpSession session;
	@Autowired
	private DeployConfigService deployConfigService;
	@Resource
	private HttpServletRequest request;
	@Resource
	private UserService userService;

	public void execute(Context context, ParameterParser params, Navigator nav) {
		User user = (User) session.getAttribute("user");

		if (user == null) {
			nav.redirectToLocation(deployConfigService.getLoginUrl(request));
			return;
		}
		PlainResult<User> userResult = userService.findEntityById(user
				.getUserId());
		context.put("userPojo", userResult.getData());
	}
}
