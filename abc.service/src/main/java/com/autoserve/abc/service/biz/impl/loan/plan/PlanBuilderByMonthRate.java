/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.plan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;

import com.alibaba.citrus.util.Assert;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.util.Arith;

/**
 * 月利率计算方式下，还款计划和收益计划生成服务
 * 
 * @author segen189 2014年11月28日 上午12:37:15
 */
public class PlanBuilderByMonthRate implements PlanBuilder {
    private static final PlanBuilderByMonthRate singleton = new PlanBuilderByMonthRate();

    private PlanBuilderByMonthRate() {
    }

    public static PlanBuilderByMonthRate getInstance() {
        return singleton;
    }

    /**
     * 计算月利率<br>
     * 月利率 ＝ 年化收益率 * / 12 / 100
     * 
     * @param annualYield 年化收益率 如 10.10D 代表 10.10%
     * @return double 月利率
     */
    private double getMonthRate(double annualRate) {
        // 月利率 ＝ 年化收益率 / 12 / 100
        return annualRate / 12 / 100;
    }

    /**
     * 计算日利率<br>
     * 日利率 ＝ 年化收益率 / 年化天数 / 100
     * 
     * @param annualYield 年化收益率 如 10.10D 代表 10.10%
     * @param annualDays 一年按多少天计算
     * @return double 日利率
     */
    private double getDayRate(double annualRate, int annualDays) {
        // 日利率 ＝ 年化收益率 / 年化天数 / 100
        double dayRate = annualRate / annualDays / 100;
        return dayRate;
    }

    @Override
    public List<PaymentPlan> buildPaymentPlanList(Loan pojo, double serveFee, int fullTransRecordId, int resultPeriod) {

        int totalMonths;
        if (LoanPeriodUnit.YEAR.equals(pojo.getLoanPeriodUnit())) {
            totalMonths = pojo.getLoanPeriod() * 12;
        } else {
            totalMonths = pojo.getLoanPeriod();
        }

        Assert.assertTrue(totalMonths % resultPeriod == 0, "还款总期数必须要能被借款总月数整除");

        List<PaymentPlan> planList = buildSequentialPaymentPlanList(pojo, serveFee, fullTransRecordId, totalMonths);

        // 总月数等于总期数
        if (totalMonths == resultPeriod) {
            return planList;
        }

        List<PaymentPlan> newPlanList = new ArrayList<PaymentPlan>(resultPeriod);
        int step = totalMonths / resultPeriod;

        //如果是到期本息（利随本清）只有一期计划。不用累加
        if (pojo.getLoanPayType().equals(LoanPayType.DQBX)) {

            PaymentPlan curPlan = planList.get(0);

            newPlanList.add(curPlan);

        } else {

            for (int i = 1; i <= resultPeriod; i++) {
                // 叠加还款计划
                int curPlanIndex = i * step - 1;
                PaymentPlan curPlan = planList.get(curPlanIndex);

                for (int j = 1; j < step; j++) {
                    PaymentPlan iPlan = planList.get(curPlanIndex - j);
                    curPlan.setLoanPeriod(i);
                    curPlan.setPayCapital(curPlan.getPayCapital().add(iPlan.getPayCapital()));
                    curPlan.setPayInterest(curPlan.getPayInterest().add(iPlan.getPayInterest()));
                    curPlan.setPayTotalMoney(curPlan.getPayTotalMoney().add(iPlan.getPayTotalMoney()));
                }

                newPlanList.add(curPlan);
            }
        }

        return newPlanList;
    }

    private List<PaymentPlan> buildSequentialPaymentPlanList(Loan pojo, double serveFee, int fullTransRecordId,
                                                             int totalMonths) {
        List<PaymentPlan> planList = new ArrayList<PaymentPlan>(pojo.getLoanPeriod());

        // 满标日期
        DateTime fullDaytime = DateTime.now();
        // 本金
        double loanCapital = pojo.getLoanMoney().doubleValue();
        // 每月剩余本金
        double remainLoanCapital = loanCapital;
        // 月利率
        double monthRate = getMonthRate(pojo.getLoanRate().doubleValue());

        for (int period = 1; period <= totalMonths; period++) {
            PaymentPlan plan = new PaymentPlan();

            plan.setLoanPeriod(period);
            plan.setLoanId(pojo.getLoanId());
            plan.setPayServiceFee(new BigDecimal(serveFee));
            plan.setPayState(PayState.INACTIVED);
            plan.setFullTransRecordId(fullTransRecordId);
            plan.setLoanee(pojo.getLoanUserId());
            plan.setIsClear(false);
            plan.setReplaceState(false);

            // 计算还款日
            switch (pojo.getLoanClearType()) {
                case FIXED_DAY: {
                    // 固定还款日，下一期还款日在满标日期所在日顺延一期
                    //plan.setPaytime(fullDaytime.plusMonths(period).toDate());

                    //指定还款日
                    plan.setPaytime(fullDaytime.plusMonths(period)
                            .withField(DateTimeFieldType.dayOfMonth(), pojo.getLoanPayDate()).toDate());
                }
                    break;
                case UN_FIXED_DAY: {
                    // 非固定还款日，下一期还款日重新计算
                    // 最后一期限在满标日顺延续n期
                    // 满标日期在1号－15号，下一期还款日在下一期的1号
                    if (period == totalMonths) {
                        plan.setPaytime(fullDaytime.plusMonths(period).toDate());
                    } else if (fullDaytime.getDayOfMonth() <= 15) {
                        plan.setPaytime(fullDaytime.plusMonths(period).withField(DateTimeFieldType.dayOfMonth(), 1)
                                .toDate());
                    }
                    // 满标日期在16号及以后，下一期还款日在下一期的16号
                    else {
                        plan.setPaytime(fullDaytime.plusMonths(period).withField(DateTimeFieldType.dayOfMonth(), 16)
                                .toDate());
                    }
                }
                    break;
                default:
                    throw new IllegalArgumentException("Illegal LoanClearType");
            }

            // 计算每期还款总额、每期所还本金、每期所还利息
            switch (pojo.getLoanPayType()) {
            //                case DEBX: {
            //                    /**
            //                     * 等额本息算法<br>
            //                     * 每月还本付息金额 =[ 本金 * 月利率 *(1+月利率)^贷款月数 ] / [(1+月利率)^贷款月数 - 1]<br>
            //                     * 每月利息 = 剩余本金*贷款月利率<br>
            //                     * 还款总利息=贷款额*贷款月数*月利率*（1+月利率）贷款月数/【（1+月利率）还款月数 - 1】-贷款额<br>
            //                     * 还款总额=还款月数*贷款额*月利率*（1+月利率）贷款月数/【（1+月利率）还款月数 - 1】<br>
            //                     */
            //
            //                    // 每月还本付息金额 =[ 本金 * 月利率 *(1+月利率)贷款月数 ] / [(1+月利率)贷款月数 - 1]
            //                    double perPayMoney = loanCapital * monthRate * Math.pow(1 + monthRate, totalMonths)
            //                            / (Math.pow(1 + monthRate, totalMonths) - 1);
            //
            //                    // 每月还本付息金额
            //                    plan.setPayTotalMoney(new BigDecimal(perPayMoney));
            //
            //                    // 每月所还利息 = 剩余本金*贷款月利率
            //                    double curPayInterest = remainLoanCapital * monthRate;
            //                    plan.setPayInterest(new BigDecimal(curPayInterest));
            //
            //                    // 每月所还本金 = 每月还本付息金额 - 每月所还利息
            //                    double curPayCapital = perPayMoney - curPayInterest;
            //                    plan.setPayCapital(new BigDecimal(curPayCapital));
            //
            //                    // 当前剩余本金
            //                    remainLoanCapital = remainLoanCapital - curPayCapital;
            //                    planList.add(plan);
            //                }
            //                    break;
                case AYHX_DQHB: {
                    /**
                     * 按月还息到期还本算法
                     */

                    // 每期利息
                    double everyPayInterest = loanCapital * monthRate;

                    // 最后一期还本金+利息
                    if (period == totalMonths) {
                        plan.setPayTotalMoney(new BigDecimal(loanCapital + everyPayInterest));
                        plan.setPayInterest(new BigDecimal(everyPayInterest));
                        plan.setPayCapital(new BigDecimal(loanCapital));
                        planList.add(plan);
                    }
                    //                    // 非最后一期只还利息 第一期的利息不一定是整月，需重新计算利息
                    //                    else if (period == 1) {
                    //                        int firstInteralDays = Days.daysBetween(fullDaytime, new DateTime(plan.getPaytime())).getDays();
                    //                        BigDecimal firstPayInterest = new BigDecimal(everyPayInterest / firstInteralDays);
                    //
                    //                        plan.setPayTotalMoney(firstPayInterest);
                    //                        plan.setPayInterest(firstPayInterest);
                    //                        plan.setPayCapital(BigDecimal.valueOf(0));
                    //                    }
                    // 非最后一期只还利息

                    else {
                        plan.setPayTotalMoney(new BigDecimal(everyPayInterest));
                        plan.setPayInterest(new BigDecimal(everyPayInterest));
                        plan.setPayCapital(BigDecimal.valueOf(0));
                        planList.add(plan);
                    }
                }
                    break;
                //                case DEBJ: {
                //                    /**
                //                     * 等额本金算法<br>
                //                     * 每月还本付息金额=(本金/还款月数)+(本金-累计已还本金)*月利率<br>
                //                     * 每月本金=总本金/还款月数<br>
                //                     * 每月利息=(本金-累计已还本金)*月利率<br>
                //                     * 还款总利息=（还款月数+1）*贷款额*月利率/2<br>
                //                     * 还款总额=(还款月数+1)*贷款额*月利率/2+贷款额<br>
                //                     */
                //
                //                    // 每月本金=总本金/还款月数
                //                    double curPayCapital = loanCapital / pojo.getLoanPeriod();
                //                    plan.setPayCapital(new BigDecimal(curPayCapital));
                //
                //                    // 每月利息=当前剩余本金*月利率
                //                    double curPayInterest = remainLoanCapital * monthRate;
                //                    plan.setPayInterest(new BigDecimal(curPayInterest));
                //
                //                    // 每月还本付息金额＝每月本金＋每月利息
                //                    plan.setPayTotalMoney(new BigDecimal(curPayCapital + curPayInterest));
                //
                //                    // 当前剩余本金
                //                    remainLoanCapital = remainLoanCapital - curPayCapital;
                //                    planList.add(plan);
                //                }
                //                    break;
                case DQBX: {
                    // 到期本息算法
                    if (period == pojo.getLoanPeriod()) {
                        plan.setPayCapital(pojo.getLoanMoney());
                        plan.setPayInterest(new BigDecimal(pojo.getLoanMoney().doubleValue() * monthRate));
                        plan.setPayTotalMoney(plan.getPayInterest().add(plan.getPayCapital()));
                        planList.add(plan);
                    } else {
                        plan.setPayCapital(BigDecimal.ZERO);
                        plan.setPayInterest(BigDecimal.ZERO);
                        plan.setPayTotalMoney(BigDecimal.ZERO);
                    }
                }
                    break;
                default:
                    throw new IllegalArgumentException("Illegal LoanPayType");
            }

        }

        return planList;
    }

    @Override
    public List<IncomePlan> buildIncomePlanList(Loan pojo, List<Invest> investList, List<PaymentPlan> paymentList,
                                                int fullTransRecordId) {
        List<IncomePlan> planList = new LinkedList<IncomePlan>();

        for (PaymentPlan paymentPlan : paymentList) {
            for (Invest invest : investList) {
                IncomePlan incomePlan = new IncomePlan();

                incomePlan.setFullTransRecordId(fullTransRecordId);
                incomePlan.setPaymentPlanId(paymentPlan.getId());
                incomePlan.setInvestId(invest.getId());
                incomePlan.setLoanId(invest.getOriginId());
                incomePlan.setBeneficiary(invest.getUserId());

                double percent = Arith.div(invest.getValidInvestMoney(), pojo.getLoanMoney()).doubleValue();

                incomePlan.setPayCapital(new BigDecimal(paymentPlan.getPayCapital().doubleValue() * percent));
                incomePlan.setPayInterest(new BigDecimal(paymentPlan.getPayInterest().doubleValue() * percent));
                incomePlan.setPayTotalMoney(incomePlan.getPayCapital().add(incomePlan.getPayInterest()));
                incomePlan.setPaytime(paymentPlan.getPaytime());
                incomePlan.setLoanPeriod(paymentPlan.getLoanPeriod());
                incomePlan.setIncomePlanState(IncomePlanState.INACTIVED);

                planList.add(incomePlan);
            }
        }

        return planList;
    }

    @Override
    public List<IncomePlan> buildTransferIncomePlanList(TransferLoan pojo, List<IncomePlan> transferIncomeList,
                                                        List<Invest> investList, int fullTransRecordId, Date prePayTime) {
        List<IncomePlan> planList = new LinkedList<IncomePlan>();

        // 增加受让人的未收益的收益计划
        for (Invest invest : investList) {
            double percent = Arith.div(invest.getValidInvestMoney(), pojo.getTransferMoney()).doubleValue();

            for (IncomePlan oldPlan : transferIncomeList) {
                IncomePlan newPlan = new IncomePlan();

                newPlan.setFullTransRecordId(fullTransRecordId);
                newPlan.setPaymentPlanId(oldPlan.getPaymentPlanId());
                newPlan.setInvestId(invest.getId());
                newPlan.setLoanId(invest.getOriginId());
                newPlan.setBeneficiary(invest.getUserId());

                newPlan.setPayCapital(new BigDecimal(oldPlan.getPayCapital().doubleValue() * percent));
                newPlan.setPayInterest(new BigDecimal(oldPlan.getPayInterest().doubleValue() * percent));
                newPlan.setPayTotalMoney(newPlan.getPayCapital().add(newPlan.getPayInterest()));
                newPlan.setPaytime(oldPlan.getPaytime());
                newPlan.setLoanPeriod(oldPlan.getLoanPeriod());
                newPlan.setIncomePlanState(IncomePlanState.INACTIVED);

                planList.add(newPlan);
            }
        }

        return planList;
    }

    @Override
    public List<IncomePlan> buildBuyIncomePlanList(BuyLoan pojo, List<IncomePlan> buyIncomeList,
                                                   List<Invest> investList, int fullTransRecordId) {
        List<IncomePlan> planList = new LinkedList<IncomePlan>();

        Map<Integer, Integer> userIdToInvestIdMap = new HashMap<Integer, Integer>();
        for (Invest invest : investList) {
            userIdToInvestIdMap.put(invest.getUserId(), invest.getId());
        }

        // 增加收购人的未收益的收益计划
        for (IncomePlan oldPlan : buyIncomeList) {
            IncomePlan newPlan = new IncomePlan();

            newPlan.setFullTransRecordId(fullTransRecordId);
            newPlan.setPaymentPlanId(oldPlan.getPaymentPlanId());
            newPlan.setInvestId(userIdToInvestIdMap.get(oldPlan.getBeneficiary()));
            newPlan.setLoanId(oldPlan.getLoanId());
            newPlan.setBeneficiary(pojo.getUserId());
            newPlan.setPayCapital(oldPlan.getPayCapital());
            newPlan.setPayInterest(oldPlan.getPayInterest());
            newPlan.setPayFine(oldPlan.getPayFine());
            newPlan.setPayTotalMoney(oldPlan.getPayTotalMoney());
            newPlan.setPaytime(oldPlan.getPaytime());
            newPlan.setLoanPeriod(oldPlan.getLoanPeriod());
            newPlan.setRemainFine(oldPlan.getRemainFine());
            newPlan.setIncomePlanState(IncomePlanState.INACTIVED);

            planList.add(newPlan);
        }

        return planList;
    }

    @Override
    public int buildTotalMonths(Loan pojo, DateTime fullDaytime) {
        // TODO Auto-generated method stub
        return 0;
    }

	@Override
	public List<IncomePlan> distributionPenalty(Loan loan,
			List<Invest> invests, List<IncomePlan> incomePlans,
			BigDecimal pulishMoney) {		
		return null;
	}

	@Override
	public List<IncomePlan> buildTransferIncomePlanListForTrans(
			TransferLoan pojo, List<IncomePlan> transferIncomeList,
			List<Invest> investList, int fullTransRecordId, Date prePayTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaymentPlan> buildPaymentPlanListIncludeHandFee(Loan pojo,double handFee, int fullTransRecordId,
			int resultPeriod) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
