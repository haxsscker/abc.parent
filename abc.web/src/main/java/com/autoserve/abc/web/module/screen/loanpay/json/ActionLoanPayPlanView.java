package com.autoserve.abc.web.module.screen.loanpay.json;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.PaymentPlanVOConverter;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.plan.PaymentPlanVO;

public class ActionLoanPayPlanView {

    @Resource
    private PaymentPlanService paymentPlanService;

    public JsonPageVO<PaymentPlanVO> execute(Context context, @Param("rows") int rows, @Param("page") int page,
            @Param("planId") int planId, @Param("loanId") int loanId) {

        PageCondition pageCondition = new PageCondition(page, rows);
        PaymentPlan paymentPlan = new PaymentPlan();
        if(planId!=0){
        	 paymentPlan.setId(planId);
        }
        if(loanId!=0){
        	 paymentPlan.setLoanId(loanId);
        }

        PageResult<PaymentPlan> pageResult = paymentPlanService.queryPaymentPlanList(paymentPlan, pageCondition);
        return ResultMapper.toPageVO(pageResult, PaymentPlanVOConverter.toPaymentPlanVOList(pageResult.getData()));
    }

}
