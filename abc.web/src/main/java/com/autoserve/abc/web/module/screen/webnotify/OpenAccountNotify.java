package com.autoserve.abc.web.module.screen.webnotify;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.util.EasyPayUtils;

public class OpenAccountNotify {
    private final static Logger logger = LoggerFactory.getLogger(PayInterfaceNotify.class);
    @Resource
    private AccountInfoService  accountInfoService;
    @Resource
    private HttpServletResponse resp;
    @Resource
    private HttpServletRequest  resq;
    @Resource
    private DoubleDryService    doubleDtyService;

    public void execute(Context context, ParameterParser params) {
        Map paramterMap = resq.getParameterMap();
        Map notifyMap = EasyPayUtils.transformRequestMap(paramterMap);
        try {
            resp.getWriter().print("SUCCESS");
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }
}
