package com.autoserve.abc.web.module.control.loan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.LoanCust;
import com.autoserve.abc.service.biz.intf.loan.LoanCustService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-12-25,15:34
 */
public class CustAddView {
    private static final Logger logger = LoggerFactory.getLogger(CustAddView.class);

    @Autowired
    private LoanCustService     loanCustService;

    public void execute(Context context) {
        Integer loanId = Integer.valueOf(context.get("loanId").toString());
        if (loanId == null || loanId <= 0) {
            return;
        }

        PlainResult<LoanCust> custRes = loanCustService.queryByLoanId(loanId);
        if (custRes.isSuccess()) {
            context.put("cust", custRes.getData());
        }
    }
}
