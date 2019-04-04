/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.plan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.TransferLoan;

/**
 * 还款计划和收益计划生成服务
 * 
 * @author segen189 2014年11月27日 下午11:44:41
 */
public interface PlanBuilder {
    /**
     * 生成借款人的还款计划表
     * 
     * @param pojo 借款标, 必选
     * @param serveFee 服务费
     * @param fullTransRecordId 满标资金划转记录id
     * @param resultPeriod 还款计划期数
     * @return List<PaymentPlan> 还款计划表
     */
    List<PaymentPlan> buildPaymentPlanList(Loan pojo, double serveFee, int fullTransRecordId, int resultPeriod);

    /**
     * 生成投资人的收益计划表
     * 
     * @param pojo 借款标, 必选
     * @param investList 借款标的投资记录, 必选
     * @param paymentList 借款标的还款计划表, 必选
     * @param fullTransRecordId 满标资金划转记录id
     * @return List<IncomePlan> 收益计划表
     */
    List<IncomePlan> buildIncomePlanList(Loan pojo, List<Invest> investList, List<PaymentPlan> paymentList,
                                         int fullTransRecordId);

    /**
     * 生成受让人和转让人的收益明细表
     * @param pojo 转让标, 必选
     * @param transferIncomeList 转让人的全部待收益列表, 必选
     * @param investList 转让标的投资记录, 必选
     * @param fullTransRecordId 满标资金划转记录id
     * @param prePayTime 上期还款计划应还日期，第一期传满标划转日期
     * @return List<IncomePlan> 受让人每期的待收益明细
     */
    List<IncomePlan> buildTransferIncomePlanList(TransferLoan pojo, List<IncomePlan> transferIncomeList,
                                                 List<Invest> investList, int fullTransRecordId,Date prePayTime);

    /**
     * 生成收购者的收益明细表
     * 
     * @param pojo 收购标, 必选
     * @param buyIncomeList 认购人的待收益列表, 必选
     * @param investList 收购标的投资记录, 必选
     * @param fullTransRecordId 满标资金划转记录id, 必选
     * @return List<IncomePlan> 收购人每期的收益明细
     */
    List<IncomePlan> buildBuyIncomePlanList(BuyLoan pojo, List<IncomePlan> buyIncomeList, List<Invest> investList,
                                            int fullTransRecordId);

    /**
     * 期数计算
     * 
     * @param pojo
     * @param fullDaytime
     * @return
     */
    int buildTotalMonths(Loan pojo, DateTime fullDaytime);
    
    /**
     * 分配罚金
     * @param loan   原始标
     * @param invests  投资列表
     * @param paymentPlan 本期收益计划列表
     * @param pulishMoney 应还罚金
     * @return
     */
    List<IncomePlan> distributionPenalty(Loan loan,List<Invest> invests,List<IncomePlan> incomePlans ,BigDecimal pulishMoney);

	List<IncomePlan> buildTransferIncomePlanListForTrans(TransferLoan pojo,
			List<IncomePlan> transferIncomeList, List<Invest> investList,
			int fullTransRecordId, Date prePayTime);

	List<PaymentPlan> buildPaymentPlanListIncludeHandFee(Loan pojo,double handFee,int fullTransRecordId, int resultPeriod);
}
