package com.autoserve.abc.web.module.control.loan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.LoanHouse;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.intf.loan.LoanHouseService;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-12-25,15:36
 */
public class HouseAddView {
    private static final Logger     logger = LoggerFactory.getLogger(HouseAddView.class);

    @Autowired
    private LoanIntentApplyService loanIntentApplyService;

    @Autowired
    private LoanHouseService        loanHouseService;

    public void execute(Context context) {
        boolean isIntent = "true".equals(context.get("isIntent").toString());
        Integer loanId;

        // 来自前台意向，根据intentId获取loanId
        if (isIntent) {
            Integer intentId = Integer.valueOf(context.get("intentId").toString());
            if (intentId == null || intentId <= 0) {
                return;
            }

            PlainResult<LoanIntentApply> intentResult = loanIntentApplyService.queryById(intentId);
            if (!intentResult.isSuccess() || intentResult.getData() == null
                    || intentResult.getData().getLoanId() == null) {
                return;
            }

            loanId = intentResult.getData().getLoanId();
        }
        // 来自后台申请，获取loanId
        else {
            loanId = Integer.valueOf(context.get("loanId").toString());
            if (loanId == null || loanId <= 0) {
                return;
            }
        }

        // 根据loanId获取houseList
        ListResult<LoanHouse> houseRes = loanHouseService.queryByLoanId(loanId);
        if (houseRes.isSuccess()) {
            context.put("houseList", houseRes.getData());
        }
    }
}
