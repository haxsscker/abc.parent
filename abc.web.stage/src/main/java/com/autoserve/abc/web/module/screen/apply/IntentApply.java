package com.autoserve.abc.web.module.screen.apply;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.web.helper.DeployConfigService;

/**
 * 借款意向
 * @author DS
 *
 * 2016年5月6日下午2:40:10
 */
public class IntentApply {

	@Resource
	private HttpSession session;
	@Resource
	private HttpServletRequest request;
	@Autowired
	private DeployConfigService deployConfigService;
    public void execute(Context context, ParameterParser params,Navigator nav) {
    	//登录URL
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		nav.redirectToLocation(deployConfigService.getLoginUrl(request));
    		return;
    	}
    	int catalog=params.getInt("catalog");
    	context.put("user", user);
    	context.put("catalog", catalog);
    }

}
