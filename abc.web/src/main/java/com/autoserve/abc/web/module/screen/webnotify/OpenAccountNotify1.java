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

public class OpenAccountNotify1 {
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
        /*
         * AccountInfoDO account = doubleDtyService.formatAccount(notifyMap);
         * BaseResult result = new BaseResult(); result =
         * accountInfoService.openAccount(account);
         */
        try {
            /* if (result.isSuccess()) { */
            resp.getWriter().print("SUCCESS");
            /*
             * } else { resp.getWriter().print("fail"); }
             */
        } catch (IOException e) {
            logger.error("[OpenAccountNotify] error: ", e);
        }
    }
}
