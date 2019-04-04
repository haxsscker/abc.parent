package com.autoserve.abc.web.module.screen.mobile;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.util.SystemGetPropeties;

public class QianDaoYe {

    public void execute(Context context, Navigator nav, ParameterParser params) {
    	String androidDownloadUrl = SystemGetPropeties.getBossString("androidDownloadUrl");
    	String iosDownloadUrl = SystemGetPropeties.getBossString("iosDownloadUrl");
    	context.put("androidDownloadUrl", androidDownloadUrl);
    	context.put("iosDownloadUrl", iosDownloadUrl);
    }

}
