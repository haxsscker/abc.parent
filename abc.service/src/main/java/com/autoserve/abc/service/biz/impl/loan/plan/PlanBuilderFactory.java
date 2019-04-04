/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.plan;

import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;

/**
 * 还款计划和收益计划生成器工厂<br>
 * 目前仅支持按月利率的方式生成计划
 * 
 * @author segen189 2014年11月28日 上午12:28:29
 */
public class PlanBuilderFactory {
    public static PlanBuilder createPlanBuilder(LoanPeriodUnit periodUnit) {
        switch (periodUnit) {
            case MONTH:
                return PlanBuilderByMonthRate.getInstance();
            case DAY:
                return PlanBuilderByDayRate.getInstance();
            default:
                return null;
        }
    }
}
