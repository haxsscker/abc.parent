package com.autoserve.abc.web.module.control.common;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-28,21:55
 */
public class IntentInfoView {
    private static final Logger logger = LoggerFactory.getLogger(IntentInfoView.class);

    @Autowired
    private LoanIntentApplyService intentService;

    public void execute(Context context) {
        Integer intentId = Integer.valueOf(context.get("intentId").toString());
        if (intentId == null || intentId <= 0) {
            logger.error("参数校验出错，intentID必须大于0");
            return;
        }

        PlainResult<LoanIntentApply> intentRes = intentService.queryById(intentId);
        if (intentRes.isSuccess()) {
            context.put("intent", intentRes.getData());
        }
    }
}
