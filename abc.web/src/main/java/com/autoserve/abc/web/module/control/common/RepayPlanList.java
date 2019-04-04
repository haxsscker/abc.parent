package com.autoserve.abc.web.module.control.common;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilderByDayRate;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类实现描述 还款计划（利随本清）判断
 * 
 * @author liuwei 2015年3月1日 下午3:44:02
 */
public class RepayPlanList {

    @Autowired
    private LoanQueryService LoanQueryService;

    public void execute(Context context, @Param("loanId") Integer loanId) {

        PlainResult<Loan> result = this.LoanQueryService.queryById(loanId);

        DateTime fullDaytime = DateTime.now();

        PlanBuilderByDayRate planBuilderByDayRate = PlanBuilderByDayRate.getInstance();

        int totalMonths = planBuilderByDayRate.buildTotalMonths(result.getData(), fullDaytime);

        context.put("totalMonths", totalMonths);
        context.put("loanPayType", result.getData().getLoanPayType().type);

    }

}
