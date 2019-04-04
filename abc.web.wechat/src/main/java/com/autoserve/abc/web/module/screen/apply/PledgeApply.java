package com.autoserve.abc.web.module.screen.apply;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.User;

/**
 * 抵押标
 */
public class PledgeApply {

	@Resource
	private HttpSession session;
    public void execute(Context context, ParameterParser params) {
    	User user=(User)session.getAttribute("user");
    	context.put("user", user);

    }

}
