package com.autoserve.abc.web.module.screen.user;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;

/**
 * @author RJQ 2014/12/11 15:05.
 */
public class UserListView {
    public void execute(Context context, ParameterParser params) {
        context.put("duty", params.getString("duty"));
        context.put("loanType", params.getString("loanType"));
    }
}
