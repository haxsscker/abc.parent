package com.autoserve.abc.web.module.screen.review;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-16,00:13
 */
public class IntentCheckView {
    private static final Logger logger = LoggerFactory.getLogger(IntentCheckView.class);

    @Autowired
    private LoanQueryService loanQueryService;

    public void execute(Context context, @Param("intentId") Integer intentId) {
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
