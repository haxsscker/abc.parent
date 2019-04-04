package com.autoserve.abc.web.module.screen.review;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-29,12:19
 */
public class AuditOpinionListView {
    private static final Logger logger = LoggerFactory.getLogger(AuditOpinionListView.class);

    public void execute(@Param("reviewType") int reviewType, @Param("applyId") int applyId, Context context) {
        context.put("reviewType", reviewType);
        context.put("applyId", applyId);
    }
}
