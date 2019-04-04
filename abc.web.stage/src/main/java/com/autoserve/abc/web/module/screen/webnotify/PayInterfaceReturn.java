/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.webnotify;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;

/**
 * 将支付结果展示给用户
 * 
 * @author J.YL 2014年12月12日 下午5:31:31
 */
public class PayInterfaceReturn {
    @Resource
    private HttpServletResponse resp;

    public void execute(Context context, ParameterParser params) {

    }
}
