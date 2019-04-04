package com.autoserve.abc.web.module.screen.government;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;

/**
 * @author RJQ 2014/12/19 18:04.
 */
public class GovCheckIdearView {
    public void execute(@Param("govId") Integer govId, Context context) {
        context.put("govId", govId);
    }
}
