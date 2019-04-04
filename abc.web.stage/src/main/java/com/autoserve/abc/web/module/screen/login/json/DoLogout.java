package com.autoserve.abc.web.module.screen.login.json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.webx.pipeline.LoginCookieTokenManager;

/**
 * 退出
 */
public class DoLogout {

    @Autowired
    private DeployConfigService deployConfigService;
    
	@Resource
	private HttpSession session;
	@Resource
	private HttpServletRequest request;
    public void execute(Context context, Navigator nav, ParameterParser params, TurbineRunData rundata) {

        // remove cookie
        LoginCookieTokenManager.removeLoginUserCookies((TurbineRunDataInternal) rundata);
        session.removeAttribute("user");

        // 重定向
        String loginUrl = deployConfigService.getHomeUrl(request);
        //        String redirectUrl = params.getString("redirectUrl");
        //        if (redirectUrl == null
        //                || redirectUrl.indexOf("redirectUrl", redirectUrl.indexOf("redirectUrl") + "redirectUrl".length()) > 0) {
        //            nav.redirectToLocation(loginUrl);
        //        } else {
        //            nav.redirectToLocation(redirectUrl);
        //        }
        nav.redirectToLocation(loginUrl);
    }

}
