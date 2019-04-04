package com.autoserve.abc.web.module.screen.webnotify;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.util.EasyPayUtils;

public class AuthorizeReturn {
    private final static Logger logger = LoggerFactory.getLogger(AuthorizeReturn.class);
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  resq;
    @Resource
    private UserService         userService;
    @Resource
    private HttpSession         session;

    public void execute(Context context, Navigator nav, ParameterParser params) {
        Map paramterMap = resq.getParameterMap();
        Map returnMap = EasyPayUtils.transformRequestMap(paramterMap);
        String ResultCode = (String) returnMap.get("ResultCode");
        BaseResult result = new BaseResult();
        if (ResultCode.equals("88")) {
            nav.redirectToLocation("/moneyReturnManage/rechangeReturn?Message=88&status=sq");
        }

    }
}
