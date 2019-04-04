package com.autoserve.abc.web.module.screen.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-12-28,21:42
 */
public class IntentLookUpView {
    private static final Logger logger = LoggerFactory.getLogger(IntentLookUpView.class);

    @Autowired
    private LoanQueryService    loanQueryService;

    public void execute(@Param("intentId") Integer intentId, Context context) {
        if (intentId == null || intentId <= 0) {
            logger.error("参数校验出错，intentId不能为空并且必须大于0");
            return;
        }

        PlainResult<Loan> loanRes = loanQueryService.queryByIntentApplyId(intentId);
        if (loanRes.isSuccess() && loanRes.getData() != null) {
            context.put("loanId", loanRes.getData().getLoanId());
            context.put("loanExist", true);
        } else {
            context.put("intentId", intentId);
            context.put("loanExist", false);
        }
    }
}
