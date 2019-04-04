package com.autoserve.abc.web.module.screen.webnotify;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;

public class AuthorizeNotify1 {
    private final static Logger logger = LoggerFactory.getLogger(AuthorizeNotify1.class);
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  resq;
    @Resource
    private UserService         userService;
    @Resource
    private HttpSession         session;

    public void execute(Context context, ParameterParser params) {
        Map paramterMap = resq.getParameterMap();
        BaseResult result = new BaseResult();
        result.setSuccess(true);
        try {
            if (result.isSuccess()) {
                resp.getWriter().print("success");
            } else {
                resp.getWriter().print("fail");
            }
        } catch (IOException e) {
            logger.error("[AuthorizeNotify] error: ", e);
        }
    }
}
