/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.loan.repay;

import java.math.BigDecimal;

import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.enums.PayType;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 还款服务
 *
 * @author segen189 2014年11月29日 下午2:28:34
 */
public interface RepayService {
    /**
     * 还款
     *
     * @param loanId 借款id，必选
     * @param repayPlanId 还款计划id，必选
     * @param payType 资金划转类型，必选
     * @param operatorId 操作人id，必选
     * @return BaseResult
     */
    BaseResult repay(int loanId, int repayPlanId, PayType payType, int operatorId);

    /**
     * 根据还款计划表id查询应还罚息
     */
    PlainResult<BigDecimal> queryPulishMoney(int repayPlanId);

	BaseResult repay(boolean flag, IncomePlan incomePlan,
			PlainResult<PaymentPlan> repayPlanResult, int loanId,
			int repayPlanId, PayType payType, int operatorId);

}
