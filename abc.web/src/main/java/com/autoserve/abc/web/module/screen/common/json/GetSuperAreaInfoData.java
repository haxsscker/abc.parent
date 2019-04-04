/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.common.json;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.service.biz.intf.common.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类GetSuperAreaInfoData.java的实现描述：TODO 类实现描述
 * 
 * @author J.YL 2014年12月4日 下午5:55:17
 */
public class GetSuperAreaInfoData {
    private final Logger        Logger = LoggerFactory.getLogger(GetSuperAreaInfoData.class);
    @Autowired
    private HttpServletResponse resp;
    @Resource
    private AreaService         areaService;

    public void execute(ParameterParser params) {
        String modelAction = params.getString("modelAction");
        String superCode = areaService.querySuperByAreaCode(modelAction);
        if (null == superCode) {
            superCode = "0";
        }
        try {
            resp.getWriter().write(superCode.toString());
        } catch (IOException e) {
            Logger.error("[GetSuperAreaInfoData]", e);
        }
    }
}
