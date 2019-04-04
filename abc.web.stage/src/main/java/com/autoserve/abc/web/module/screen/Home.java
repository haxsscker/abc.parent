package com.autoserve.abc.web.module.screen;

import com.alibaba.citrus.turbine.Context;

/**
 * abc 默认首页
 */
public class Home {

    public void execute(Context context) {
        String crmUrl = "http://localhost:8020";

        context.put("crmUrl", crmUrl);
    }

}
