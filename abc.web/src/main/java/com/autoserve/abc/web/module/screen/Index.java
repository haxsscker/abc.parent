package com.autoserve.abc.web.module.screen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;

/**
 * abc 默认框架首页
 */
public class Index {

    private static final Log log = LogFactory.getLog(Index.class);

    public void execute(Context context, Navigator nav, HttpServletRequest req, HttpServletResponse response,
                        TurbineRunData rundata) {
        // log.info("index");

        // 跳转到应用管理页面
        nav.redirectTo("abcUri").withTarget("Home/index.vm").end();
    }

}
