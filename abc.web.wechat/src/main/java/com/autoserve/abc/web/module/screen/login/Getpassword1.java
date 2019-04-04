package com.autoserve.abc.web.module.screen.login;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;

public class Getpassword1
{
    public void execute(Context context,Navigator nav, ParameterParser params) {
    	String flag=params.getString("flag");
    	context.put("flag", flag);
    }
}
