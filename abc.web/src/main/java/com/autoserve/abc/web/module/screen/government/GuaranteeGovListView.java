package com.autoserve.abc.web.module.screen.government;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;

/**
 * @author RJQ 2014/12/11 15:05.
 */
public class GuaranteeGovListView {
    public void execute(Context context, ParameterParser params) {
        String duty = params.getString("duty");
        String govId = params.getString("govId");
        context.put("duty", duty);
        context.put("govId", govId);
    }
}
