package com.autoserve.abc.web.module.screen.apply;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.User;

/**
 * 担保标
 */
public class SecuredApply {

	@Resource
	private HttpSession session;
    public void execute(Context context, ParameterParser params) {
    	User user=(User)session.getAttribute("user");
    	context.put("user", user);

    }

}
