/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.loan.plan;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.util.Assert;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.IncomePlan;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.enums.IncomePlanState;
import com.autoserve.abc.service.biz.enums.LoanClearType;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.service.util.DateUtil;

/**
 * 月利率计算方式下，还款计划和收益计划生成服务
 * 
 * @author segen189 2014年11月28日 上午12:37:15
 */
public class PlanBuilderByDayRate implements PlanBuilder {
	private static final PlanBuilderByDayRate singleton = new PlanBuilderByDayRate();
	private static final Logger logger = LoggerFactory.getLogger(PlanBuilderByDayRate.class);

	private PlanBuilderByDayRate() {
	}

	public static PlanBuilderByDayRate getInstance() {
		return singleton;
	}

	/**
	 * 计算月利率<br>
	 * 月利率 ＝ 年化收益率 * / 12 / 100
	 * 
	 * @param annualYield
	 *            年化收益率 如 10.10D 代表 10.10%
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
	 * @param annualYield
	 *            年化收益率 如 10.10D 代表 10.10%
	 * @param annualDays
	 *            一年按多少天计算
	 * @return double 日利率
	 */
	private double getDayRate(double annualRate, int annualDays) {
		// 日利率 ＝ 年化收益率 / 年化天数 / 100
		double dayRate = annualRate / annualDays / 100;
		return dayRate;
	}

	@Override
	public List<PaymentPlan> buildPaymentPlanList(Loan pojo, double serveFee,
			int fullTransRecordId, int resultPeriod) {
		DateTime fullDaytime = DateTime.now();

		int totalMonths = this.buildTotalMonths(pojo, fullDaytime);

		if (totalMonths % resultPeriod != 0) {
			return null;
		}
		Assert.assertTrue(totalMonths % resultPeriod == 0, "还款总期数必须要能被借款总月数整除");

		List<PaymentPlan> planList = buildSequentialPaymentPlanList(pojo,
				serveFee, fullTransRecordId, resultPeriod, fullDaytime);

		return planList;
	}
	@Override
	public List<PaymentPlan> buildPaymentPlanListIncludeHandFee(Loan pojo, double handFee,
			int fullTransRecordId, int resultPeriod) {
		DateTime fullDaytime = DateTime.now();

		int totalMonths = this.buildTotalMonths(pojo, fullDaytime);

		if (totalMonths % resultPeriod != 0) {
			return null;
		}
		Assert.assertTrue(totalMonths % resultPeriod == 0, "还款总期数必须要能被借款总月数整除");

		List<PaymentPlan> planList = this.buildSequentialPaymentPlanListIncludeHandFee(pojo,handFee, fullTransRecordId, resultPeriod, fullDaytime);

		return planList;
	}
	
	/**
	 * 计算期数
	 * 
	 * @param pojo
	 * @param fullDaytime
	 * @return
	 */
	@Override
	public int buildTotalMonths(Loan pojo, DateTime fullDaytime) {

		DateTime expire = new DateTime(pojo.getLoanExpireDate());

		int totalMonths = getMonthSpace(fullDaytime.toDate(),
				pojo.getLoanExpireDate());

		if (expire.getDayOfMonth() > pojo.getLoanPayDate()
				&& pojo.getLoanPayDate() > fullDaytime.getDayOfMonth()) {
			totalMonths = totalMonths + 1;
		}
		// ??
		if (expire.getDayOfMonth() < pojo.getLoanPayDate()
				&& pojo.getLoanPayDate() < fullDaytime.getDayOfMonth()) {
			return totalMonths;
		}
		if (expire.getDayOfMonth() != pojo.getLoanPayDate()
				&& expire.getDayOfMonth() <= fullDaytime.getDayOfMonth()
				&& pojo.getLoanPayDate() != fullDaytime.getDayOfMonth()) {
			totalMonths = totalMonths + 1;
		}

		return totalMonths;
	}
	public static class buildPaymentPlanListTask implements Runnable
	{
		private String name;
        
		buildPaymentPlanListTask(String name)
        {
            this.name = name;
        }
		@Override
		public void run() {
			 System.out.println("线程" + name + "启动");
			 PlanBuilderByDayRate planBuilderByDayRate=new PlanBuilderByDayRate();
				Loan pojo =new Loan();
				try {
//					pojo.setLoanExpireDate(DateUtil.parseDate("2019-07-28","yyyy-MM-dd"));//指定到期日
					pojo.setLoanExpireDate(DateUtil.parseDate("2019-08-23","yyyy-MM-dd"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pojo.setLoanPeriod(12);//借款期限
				pojo.setLoanPayDate(3);//还款日期
				pojo.setLoanClearType(LoanClearType.FIXED_DAY);//结算方式
				pojo.setLoanPayType(LoanPayType.DEBX);//还款方式
				pojo.setLoanCurrentValidInvest(new BigDecimal(1827));//项目金额
				pojo.setLoanRate(new BigDecimal(11));//年化率
//				DateTime transferDate = new DateTime("2018-07-30");//划转日
				DateTime transferDate = new DateTime("2018-08-28");
//				DateTime transferDate = DateTime.now();
				//计算期数
				int totalMonths=planBuilderByDayRate.buildTotalMonths(pojo,transferDate);
				System.out.println("项目总期数=========="+totalMonths);
//				Assert.assertTrue(totalMonths % 12 == 0, "还款总期数必须要能被借款总月数整除");
			
				List<PaymentPlan> planList = planBuilderByDayRate.buildSequentialPaymentPlanListIncludeHandFee(pojo,0, 1,totalMonths, transferDate);
				System.out.println("=====================生成还款计划====================");
				for(PaymentPlan p : planList){
					System.out.print("period======="+p.getLoanPeriod());
					System.out.print(",paytime======="+DateUtil.formatDate(p.getPaytime(),"yyyy-MM-dd"));
					System.out.print(",PayCapital======="+p.getPayCapital());
					System.out.print(",PayInterest======="+p.getPayInterest());
					System.out.print(",payService======="+p.getPayServiceFee());
					System.out.println(",TotalMoney======="+p.getPayTotalMoney());
				}
				List<Invest> investList = new ArrayList<Invest>();
				Invest invest = new Invest();
				invest.setId(1);
				invest.setValidInvestMoney(new BigDecimal(1000));
				investList.add(invest);
				invest = new Invest();
				invest.setId(2);
				invest.setValidInvestMoney(new BigDecimal(827));
				investList.add(invest);
				List<IncomePlan> incomePlanList = planBuilderByDayRate.buildIncomePlanList(pojo,investList,planList,1);
				System.out.println("=====================生成收益计划====================");
				BigDecimal sumInterest=BigDecimal.ZERO;
				for(IncomePlan p : incomePlanList){
					System.out.print("period======="+p.getLoanPeriod());
					System.out.print(",paytime======="+DateUtil.formatDate(p.getPaytime(),"yyyy-MM-dd"));
					System.out.print(",PayCapital======="+p.getPayCapital());
					System.out.print(",PayInterest======="+p.getPayInterest());
					System.out.println(",TotalMoney======="+p.getPayTotalMoney());
					sumInterest=sumInterest.add(p.getPayInterest());
				}
				System.out.println("sumInterest======="+sumInterest);
		}
		
	}
	public static class buildPaymentPlanListTask1 implements Runnable
	{
		private String name;
        
		buildPaymentPlanListTask1(String name)
        {
            this.name = name;
        }
		@Override
		public void run() {
			 System.out.println("线程" + name + "启动");
			 try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 PlanBuilderByDayRate planBuilderByDayRate=new PlanBuilderByDayRate();
				Loan pojo =new Loan();
				try {
					pojo.setLoanExpireDate(DateUtil.parseDate("2019-08-23","yyyy-MM-dd"));//指定到期日
//					pojo.setLoanExpireDate(DateUtil.parseDate("2019-07-28","yyyy-MM-dd"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pojo.setLoanPeriod(6);//借款期限
				pojo.setLoanPayDate(20);//还款日期
				pojo.setLoanClearType(LoanClearType.FIXED_DAY);//结算方式
				pojo.setLoanPayType(LoanPayType.AYHX_DQHB);//还款方式
				pojo.setLoanCurrentValidInvest(new BigDecimal(1000));//项目金额
				pojo.setLoanRate(new BigDecimal(11));//年化率
				pojo.setFsRate(5d);
//				DateTime transferDate = new DateTime("2018-07-30");//划转日
				DateTime transferDate = new DateTime("2019-02-16");
//				DateTime transferDate = DateTime.now();
				//计算期数
				int totalMonths=planBuilderByDayRate.buildTotalMonths(pojo,transferDate);
				System.out.println("项目总期数=========="+totalMonths);
//				Assert.assertTrue(totalMonths % 12 == 0, "还款总期数必须要能被借款总月数整除");
			
				List<PaymentPlan> planList = planBuilderByDayRate.buildSequentialPaymentPlanListIncludeHandFee(pojo,0,1, totalMonths, transferDate);
				System.out.println("=====================生成还款计划====================");
				for(PaymentPlan p : planList){
					System.out.print("period======="+p.getLoanPeriod());
					System.out.print(",paytime======="+DateUtil.formatDate(p.getPaytime(),"yyyy-MM-dd"));
					System.out.print(",PayCapital======="+p.getPayCapital());
					System.out.print(",PayInterest======="+p.getPayInterest());
					System.out.print(",payService======="+p.getPayServiceFee());
					System.out.println(",TotalMoney======="+p.getPayTotalMoney());
				}
				List<Invest> investList = new ArrayList<Invest>();
				Invest invest = new Invest();
				invest.setId(1);
				invest.setValidInvestMoney(new BigDecimal(1000));
				investList.add(invest);
				invest = new Invest();
				invest.setId(2);
				invest.setValidInvestMoney(new BigDecimal(827));
				investList.add(invest);
				List<IncomePlan> incomePlanList = planBuilderByDayRate.buildIncomePlanList(pojo,investList,planList,1);
				System.out.println("=====================生成收益计划====================");
				BigDecimal sumInterest=BigDecimal.ZERO;
				for(IncomePlan p : incomePlanList){
					System.out.print("period======="+p.getLoanPeriod());
					System.out.print(",paytime======="+DateUtil.formatDate(p.getPaytime(),"yyyy-MM-dd"));
					System.out.print(",PayCapital======="+p.getPayCapital());
					System.out.print(",PayInterest======="+p.getPayInterest());
					System.out.println(",TotalMoney======="+p.getPayTotalMoney());
					sumInterest=sumInterest.add(p.getPayInterest());
				}
				System.out.println("sumInterest======="+sumInterest);
		}
		
	}
	/**
	 * 测试计划表生成
	 * @param args
	 */
	public static void main(String[] args) {
//		new Thread(new buildPaymentPlanListTask("buildPaymentPlanListTask")).start();
		new Thread(new buildPaymentPlanListTask1("buildPaymentPlanListTask1")).start();
	}
	// 生成还款计划列表（包含手续费，用于手续费分期）
		private List<PaymentPlan> buildSequentialPaymentPlanListIncludeHandFee(Loan pojo,double servFee, int fullTransRecordId, int totalMonths,
				DateTime fullDaytime) {
			List<PaymentPlan> planList = new ArrayList<PaymentPlan>();
			// 本金
			double loanCapital = pojo.getLoanCurrentValidInvest().doubleValue();
			// 日利率按360天计算
			double dayRate = getDayRate(pojo.getLoanRate().doubleValue(), 360);
			//服务费年化率
			double fyRate = getDayRate(pojo.getFsRate(), 360);
			// 每期顺延日期
			Date flagDay = fullDaytime.toDate();

			// 计算应还的总利息
			int daycounts = this.getDifferenceDays(flagDay,
					pojo.getLoanExpireDate());
			BigDecimal PayInterest = new BigDecimal(loanCapital * dayRate
					* daycounts).setScale(2, BigDecimal.ROUND_HALF_UP);
			//计算应还的总服务费
			BigDecimal fee = new BigDecimal(loanCapital * fyRate
					* daycounts).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal Capitalcount = BigDecimal.ZERO; // 除最后一期还款的本金之和
			BigDecimal Interestcount = BigDecimal.ZERO; // 除最后一期还款的利息之和
			BigDecimal serFeeCount = BigDecimal.ZERO; // 除最后一期还款的服务费之和
			logger.info("还款方式==================="+pojo.getLoanPayType().prompt);
			//System.out.println("还款方式==================="+pojo.getLoanPayType().prompt);
			Map<Integer, BigDecimal> CapitalMap = new HashMap<Integer, BigDecimal>();
			Map<Integer, BigDecimal> InterestMap = new HashMap<Integer, BigDecimal>();
			switch (pojo.getLoanPayType()) {
				case DEBX: {
					CapitalMap = AverageCapitalPlusInterestUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths);
					InterestMap = AverageCapitalPlusInterestUtils.getPerMonthInterest(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths);
				}
				break;
				case DEBJ: {
					CapitalMap = AverageCapitalUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths);
					InterestMap = AverageCapitalUtils.getPerMonthInterest(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths);
				}
				break;
				default:
			}
			for (int period = 1; period <= totalMonths; period++) {
				PaymentPlan plan = new PaymentPlan();

				plan.setLoanPeriod(period);
				plan.setLoanId(pojo.getLoanId());
				plan.setPayState(PayState.INACTIVED);
				plan.setFullTransRecordId(fullTransRecordId);
				plan.setLoanee(pojo.getLoanUserId());
				plan.setIsClear(false);
				plan.setReplaceState(false);

				// 计算还款日
				switch (pojo.getLoanClearType()) {
				case FIXED_DAY: {
					// 指定还款日（划转日>=还款日，第一期还款日期的月份往后加1，否则取划转日当月的月份。
					//列如：划转日2018-8-20号，还款日15号，则第一期还款日期是2018-9-15；
					//划转日2018-8-10号，还款日15号，则第一期还款日期是2018-8-15；）
					if (fullDaytime.getDayOfMonth() >= pojo.getLoanPayDate()) {
						if (period == totalMonths) {
							plan.setPaytime(pojo.getLoanExpireDate());
						} else {
							plan.setPaytime(fullDaytime
									.plusMonths(period)
									.withField(DateTimeFieldType.dayOfMonth(),
											pojo.getLoanPayDate()).toDate());
						}
					} else if (fullDaytime.getDayOfMonth() < pojo.getLoanPayDate()) {
						if (period == totalMonths) {
							plan.setPaytime(pojo.getLoanExpireDate());
						} else {
							plan.setPaytime(fullDaytime
									.plusMonths(period - 1)
									.withField(DateTimeFieldType.dayOfMonth(),
											pojo.getLoanPayDate()).toDate());
						}
					}
				}
					break;
				// case UN_FIXED_DAY: {
				// // 非固定还款日，下一期还款日重新计算
				// // 最后一期限在满标日顺延续n期
				// // 满标日期在1号－15号，下一期还款日在下一期的1号
				// if (period == totalMonths) {
				// plan.setPaytime(fullDaytime.plusMonths(period).toDate());
				// } else if (fullDaytime.getDayOfMonth() <= 15) {
				// plan.setPaytime(fullDaytime.plusMonths(period).withField(DateTimeFieldType.dayOfMonth(),
				// 1)
				// .toDate());
				// }
				// // 满标日期在16号及以后，下一期还款日在下一期的16号
				// else {
				// plan.setPaytime(fullDaytime.plusMonths(period).withField(DateTimeFieldType.dayOfMonth(),
				// 16)
				// .toDate());
				// }
				// }
				// break;
				default:
					throw new IllegalArgumentException("Illegal LoanClearType");
				}

				// 计算每期还款总额、每期所还本金、每期所还利息
				switch (pojo.getLoanPayType()) {
				case AYHX_DQHB: {
					/**
					 * 按月还息到期还本算法
					 */
					// 计算最每期要还利息天数
					int days = this.getDifferenceDays(flagDay, plan.getPaytime());
					flagDay = plan.getPaytime();
					// 利息
					BigDecimal everyPayInterest = new BigDecimal(loanCapital
							* dayRate * days).setScale(2, BigDecimal.ROUND_HALF_UP);
					//每期的服务费
					BigDecimal everyPayServFee = new BigDecimal(loanCapital * fyRate
							* days).setScale(2, BigDecimal.ROUND_HALF_UP);
					// 最后一期还本金+利息
					if (period == totalMonths) {
						plan.setPayTotalMoney(new BigDecimal(loanCapital)
								.add(PayInterest.subtract(Interestcount)).add(fee.subtract(serFeeCount)));
						plan.setPayInterest(PayInterest.subtract(Interestcount));
						plan.setPayCapital(new BigDecimal(loanCapital));
						plan.setPayServiceFee(fee.subtract(serFeeCount));
						planList.add(plan);
					} else {
						plan.setPayTotalMoney(everyPayInterest.add(everyPayServFee));
						plan.setPayInterest(everyPayInterest);
						plan.setPayCapital(BigDecimal.valueOf(0));
						plan.setPayServiceFee(everyPayServFee);
						planList.add(plan);
						Interestcount = Interestcount.add(everyPayInterest); // 利息累计
						serFeeCount = serFeeCount.add(everyPayServFee);//服务费累计
					}
				}
					break;
				case DEBX: {
					/**
					 * 等额本息算法
					 */
					BigDecimal monthCapital = BigDecimal.ZERO;
					BigDecimal monthInterest = BigDecimal.ZERO;
					// 计算最每期要还利息天数
					int days = this.getDifferenceDays(flagDay, plan.getPaytime());
					flagDay = plan.getPaytime();
					//每期的服务费
					BigDecimal everyPayServFee = new BigDecimal(loanCapital * fyRate
							* days).setScale(2, BigDecimal.ROUND_HALF_UP);
					if (period == totalMonths) {
						plan.setPayServiceFee(fee.subtract(serFeeCount));
					}else{
						plan.setPayServiceFee(everyPayServFee);
						serFeeCount = serFeeCount.add(everyPayServFee);//服务费累计
					}
					if(period==1 && fullDaytime.getDayOfMonth() != pojo.getLoanPayDate()){//第一期不足月(放款日不等于还款日)
						logger.info("第一期不足月");
						// 每期实际天数利息
						monthInterest = new BigDecimal(loanCapital
								* dayRate * days).setScale(2, BigDecimal.ROUND_HALF_UP);
					}else if(period == totalMonths && new DateTime(pojo.getLoanExpireDate()).getDayOfMonth() != pojo.getLoanPayDate()){//最后一期不足月
						logger.info("最后一期不足月");
						monthInterest = pojo.getLoanCurrentValidInvest().subtract(Capitalcount);//拿剩余本金算利息
						monthInterest = monthInterest.multiply(new BigDecimal(dayRate * days)).setScale(2, BigDecimal.ROUND_HALF_UP);
					}else{
						monthInterest = InterestMap.get(period);
					}
					monthCapital = CapitalMap.get(period);
					Capitalcount=Capitalcount.add(monthCapital);//累加已分配的本金
					plan.setPayTotalMoney(monthCapital.add(monthInterest).add(plan.getPayServiceFee()));
					plan.setPayInterest(monthInterest);
					plan.setPayCapital(monthCapital);
					planList.add(plan);
				}
					break;
				case DEBJ: {
					/**
					 * 等额本金算法
					 */
					BigDecimal monthCapital = BigDecimal.ZERO;
					BigDecimal monthInterest = BigDecimal.ZERO;
					// 计算每期要还利息天数
					int days = this.getDifferenceDays(flagDay, plan.getPaytime());
					flagDay = plan.getPaytime();
					//每期的服务费
					BigDecimal everyPayServFee = new BigDecimal(loanCapital * fyRate
							* days).setScale(2, BigDecimal.ROUND_HALF_UP);
					if (period == totalMonths) {
						plan.setPayServiceFee(fee.subtract(serFeeCount));
					}else{
						plan.setPayServiceFee(everyPayServFee);
						serFeeCount = serFeeCount.add(everyPayServFee);//服务费累计
					}
					if(period==1 && fullDaytime.getDayOfMonth() != pojo.getLoanPayDate()){//第一期不足月(放款日不等于还款日)
						logger.info("第一期不足月");
						monthInterest = new BigDecimal(loanCapital
								* dayRate * days).setScale(2, BigDecimal.ROUND_HALF_UP);
					}else if(period == totalMonths && new DateTime(pojo.getLoanExpireDate()).getDayOfMonth() != pojo.getLoanPayDate()){//最后一期不足月
						logger.info("最后一期不足月");
						monthInterest = pojo.getLoanCurrentValidInvest().subtract(Capitalcount);//拿剩余本金算利息
						monthInterest = monthInterest.multiply(new BigDecimal(dayRate * days)).setScale(2, BigDecimal.ROUND_HALF_UP);
					}else{
						monthInterest = InterestMap.get(period);
					}
					monthCapital = CapitalMap.get(period);
					Capitalcount=Capitalcount.add(monthCapital);//累加已分配的本金
					plan.setPayTotalMoney(monthCapital.add(monthInterest).add(plan.getPayServiceFee()));
					plan.setPayInterest(monthInterest);
					plan.setPayCapital(monthCapital);
					planList.add(plan);
				}
					break;
				case DBDX: {
					/**
					 * 等本等息算法
					 */
					BigDecimal monthCapital = EqualCapitalEqualInterestUtils.getPerMonthPrincipal(loanCapital,totalMonths);
					BigDecimal monthInterest = BigDecimal.ZERO;
					// 计算每期要还利息天数
					int days = this.getDifferenceDays(flagDay, plan.getPaytime());
					flagDay = plan.getPaytime();
					//每期的服务费
					BigDecimal everyPayServFee = new BigDecimal(loanCapital * fyRate
							* days).setScale(2, BigDecimal.ROUND_HALF_UP);
					if (period == totalMonths) {
						monthCapital = new BigDecimal(loanCapital).subtract(Capitalcount);
						plan.setPayServiceFee(fee.subtract(serFeeCount));
					}else{
						plan.setPayServiceFee(everyPayServFee);
						serFeeCount = serFeeCount.add(everyPayServFee);//服务费累计
					}
					if(period==1 && fullDaytime.getDayOfMonth() != pojo.getLoanPayDate()){//第一期不足月(放款日不等于还款日)
						logger.info("第一期不足月");
						monthInterest = new BigDecimal(loanCapital
								* dayRate * days).setScale(2, BigDecimal.ROUND_HALF_UP);
					}else if(period == totalMonths && new DateTime(pojo.getLoanExpireDate()).getDayOfMonth() != pojo.getLoanPayDate()){//最后一期不足月
						logger.info("最后一期不足月");
						monthInterest = pojo.getLoanCurrentValidInvest().subtract(Capitalcount);//拿剩余本金算利息
						monthInterest = monthInterest.multiply(new BigDecimal(dayRate * days)).setScale(2, BigDecimal.ROUND_HALF_UP);
					}else{
						monthInterest = EqualCapitalEqualInterestUtils.getPerMonthInterest(loanCapital,pojo.getLoanRate().doubleValue());
					}
					Capitalcount=Capitalcount.add(monthCapital);//累加已分配的本金
					plan.setPayCapital(monthCapital);
					plan.setPayInterest(monthInterest);
					plan.setPayTotalMoney(plan.getPayInterest().add(plan.getPayCapital()).add(plan.getPayServiceFee()));
					planList.add(plan);
				}
					break;
				case DQBX: {
					// 到期本息算法
					if (period == totalMonths) {
						// 利随本清只有一期
						plan.setLoanPeriod(1);
						int days = this.getDifferenceDays(flagDay,
								pojo.getLoanExpireDate());
						plan.setPayCapital(pojo.getLoanCurrentValidInvest());
						plan.setPayInterest(new BigDecimal(loanCapital * dayRate
								* days).setScale(2, BigDecimal.ROUND_HALF_UP));
						plan.setPayTotalMoney(plan.getPayInterest().add(
								plan.getPayCapital()).add(fee));
						plan.setPayServiceFee(fee);
						planList.add(plan);
					}
				}
					break;
				default:
					throw new IllegalArgumentException("Illegal LoanPayType");
				}

			}
			logger.info("=====================生成还款计划====================");
			for(PaymentPlan p : planList){
				logger.info("period======={},paytime======={},"
						+ "PayCapital======={},PayInterest======={},PayServiceFee======={},TotalMoney======={}"
						,p.getLoanPeriod(),DateUtil.formatDate(p.getPaytime(),"yyyy-MM-dd"),
						p.getPayCapital(),p.getPayInterest(),p.getPayServiceFee(),p.getPayTotalMoney());
			}
			return planList;
		}
	
	// 生成还款计划列表（目前没有使用这个方法）
	private List<PaymentPlan> buildSequentialPaymentPlanList(Loan pojo,
			double serveFee, int fullTransRecordId, int totalMonths,
			DateTime fullDaytime) {
		List<PaymentPlan> planList = new ArrayList<PaymentPlan>();
		// 本金
		double loanCapital = pojo.getLoanCurrentValidInvest().doubleValue();
		// 日利率按360天计算
		double dayRate = getDayRate(pojo.getLoanRate().doubleValue(), 360);
		// 每期顺延日期
		Date flagDay = fullDaytime.toDate();

		// 计算应还的总利息
		int daycounts = this.getDifferenceDays(flagDay,
				pojo.getLoanExpireDate());
		BigDecimal PayInterest = new BigDecimal(loanCapital * dayRate
				* daycounts).setScale(2, BigDecimal.ROUND_HALF_UP);
//		logger.info("按月付息到期还本/一次性还本付息应还的总利息======="+PayInterest);
		BigDecimal Interestcount = BigDecimal.ZERO; // 除最后一期还款的利息之和
		logger.info("还款方式==================="+pojo.getLoanPayType().prompt);
		System.out.println("还款方式==================="+pojo.getLoanPayType().prompt);
		for (int period = 1; period <= totalMonths; period++) {
			PaymentPlan plan = new PaymentPlan();

			plan.setLoanPeriod(period);
			plan.setLoanId(pojo.getLoanId());
//			plan.setPayServiceFee(new BigDecimal(serveFee));
			plan.setPayState(PayState.INACTIVED);
			plan.setFullTransRecordId(fullTransRecordId);
			plan.setLoanee(pojo.getLoanUserId());
			plan.setIsClear(false);
			plan.setReplaceState(false);

			// 计算还款日
			switch (pojo.getLoanClearType()) {
			case FIXED_DAY: {
				// 指定还款日（划转日>=还款日，第一期还款日期的月份往后加1，否则取划转日当月的月份。
				//列如：划转日2018-8-20号，还款日15号，则第一期还款日期是2018-9-15；
				//划转日2018-8-10号，还款日15号，则第一期还款日期是2018-8-15；）
				if (fullDaytime.getDayOfMonth() >= pojo.getLoanPayDate()) {
					if (period == totalMonths) {
						plan.setPaytime(pojo.getLoanExpireDate());
					} else {
						plan.setPaytime(fullDaytime
								.plusMonths(period)
								.withField(DateTimeFieldType.dayOfMonth(),
										pojo.getLoanPayDate()).toDate());
					}
				} else if (fullDaytime.getDayOfMonth() < pojo.getLoanPayDate()) {
					if (period == totalMonths) {
						plan.setPaytime(pojo.getLoanExpireDate());
					} else {
						plan.setPaytime(fullDaytime
								.plusMonths(period - 1)
								.withField(DateTimeFieldType.dayOfMonth(),
										pojo.getLoanPayDate()).toDate());
					}
				}
			}
				break;
			// case UN_FIXED_DAY: {
			// // 非固定还款日，下一期还款日重新计算
			// // 最后一期限在满标日顺延续n期
			// // 满标日期在1号－15号，下一期还款日在下一期的1号
			// if (period == totalMonths) {
			// plan.setPaytime(fullDaytime.plusMonths(period).toDate());
			// } else if (fullDaytime.getDayOfMonth() <= 15) {
			// plan.setPaytime(fullDaytime.plusMonths(period).withField(DateTimeFieldType.dayOfMonth(),
			// 1)
			// .toDate());
			// }
			// // 满标日期在16号及以后，下一期还款日在下一期的16号
			// else {
			// plan.setPaytime(fullDaytime.plusMonths(period).withField(DateTimeFieldType.dayOfMonth(),
			// 16)
			// .toDate());
			// }
			// }
			// break;
			default:
				throw new IllegalArgumentException("Illegal LoanClearType");
			}

			// 计算每期还款总额、每期所还本金、每期所还利息
			switch (pojo.getLoanPayType()) {
			case AYHX_DQHB: {
				/**
				 * 按月还息到期还本算法
				 */
				// 计算最每期要还利息天数
				int days = this.getDifferenceDays(flagDay, plan.getPaytime());
				flagDay = plan.getPaytime();
				// 利息
				BigDecimal everyPayInterest = new BigDecimal(loanCapital
						* dayRate * days).setScale(2, BigDecimal.ROUND_HALF_UP);

				// 最后一期还本金+利息
				if (period == totalMonths) {
					plan.setPayTotalMoney(new BigDecimal(loanCapital)
							.add(PayInterest.subtract(Interestcount)));
					plan.setPayInterest(PayInterest.subtract(Interestcount));
					plan.setPayCapital(new BigDecimal(loanCapital));
					planList.add(plan);
				} else {
					plan.setPayTotalMoney(everyPayInterest);
					plan.setPayInterest(everyPayInterest);
					plan.setPayCapital(BigDecimal.valueOf(0));
					planList.add(plan);
					Interestcount = Interestcount.add(everyPayInterest); // 利息累计
				}
			}
				break;
			case DEBX: {
				/**
				 * 等额本息算法
				 * 按借款期数生成计划表；
				 * 一、当生成期数大于借款期数时，判断第一期与最后一期是否足月；
				 * 1.第一期不足月，最后一期足月。第一期利息按实际天数算，本金为0，后面对应的期数的本金和利息按对应的期数减一算；
				 * 2.第一期足月，最后一期不足月。最后一期利息按实际天数算，本金为0，前面对应的期数的本金和利息按对应的期数算；
				 * 3.第一期不足月，最后一期不足月。第一期利息按实际天数算，本金为0，后面对应的期数的本金和利息按对应的期数减一算； 最后一期利息按实际天数算，本金按对应的期数减一算；
				 * 二、当生成期数等于借款期数时，判断第一期与最后一期是否足月；
				 * 1.第一期不足月，最后一期足月。第一期利息按实际天数算，本金按对应的期数算，后面对应的期数的本金和利息按对应的期数算；
				 * 2.第一期足月，最后一期不足月。最后一期利息按实际天数算，本金按对应的期数算，前面对应的期数的本金和利息按对应的期数算；
				 * 3.第一期不足月，最后一期不足月。第一期利息按实际天数算，本金按对应的期数算，后面对应的期数的本金和利息按对应的期数算； 最后一期利息按实际天数算，本金按对应的期数减一算；
				 */
				BigDecimal monthCapital = BigDecimal.ZERO;
				BigDecimal monthInterest = BigDecimal.ZERO;
				// 计算每期要还利息天数
				int days = this.getDifferenceDays(flagDay, plan.getPaytime());
				flagDay = plan.getPaytime();
				// 每期实际天数利息
				BigDecimal realDaysInterest = new BigDecimal(loanCapital
						* dayRate * days).setScale(2, BigDecimal.ROUND_HALF_UP);
				if(period==1 && fullDaytime.getDayOfMonth() != pojo.getLoanPayDate()){//第一期不足月(放款日不等于还款日)
					System.out.println("第一期不足月");
					monthInterest = realDaysInterest;
					if(totalMonths==pojo.getLoanPeriod()){
						monthCapital = AverageCapitalPlusInterestUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths, period);
					}
				}else if(period == totalMonths && new DateTime(pojo.getLoanExpireDate()).getDayOfMonth() != pojo.getLoanPayDate()){//最后一期不足月
					System.out.println("最后一期不足月");
					monthInterest = realDaysInterest;
					if(fullDaytime.getDayOfMonth() != pojo.getLoanPayDate() && totalMonths-1==pojo.getLoanPeriod()){
						monthCapital = AverageCapitalPlusInterestUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths-1, period-1);
					}else if(totalMonths==pojo.getLoanPeriod()){
						monthCapital = AverageCapitalPlusInterestUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths, period);
					}
				}else{
					if(totalMonths-1==pojo.getLoanPeriod()){
						if(fullDaytime.getDayOfMonth() != pojo.getLoanPayDate()){//第一期不足月(放款日不等于还款日)
							monthCapital = AverageCapitalPlusInterestUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths-1, period-1);
							monthInterest = AverageCapitalPlusInterestUtils.getPerMonthInterest(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths-1, period-1);
						}else{
							monthCapital = AverageCapitalPlusInterestUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths-1, period);
							monthInterest = AverageCapitalPlusInterestUtils.getPerMonthInterest(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths-1, period);
						}
					}else{
						monthCapital = AverageCapitalPlusInterestUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths, period);
						monthInterest = AverageCapitalPlusInterestUtils.getPerMonthInterest(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths, period);
					}
				}
				plan.setPayTotalMoney(monthCapital.add(monthInterest));
				plan.setPayInterest(monthInterest);
				plan.setPayCapital(monthCapital);
				planList.add(plan);
			}
				break;
			case DEBJ: {
				/**
				 * 等额本金算法
				 * 按借款期数生成计划表；
				 * 一、当生成期数大于借款期数时，判断第一期与最后一期是否足月；
				 * 1.第一期不足月，最后一期足月。第一期利息按实际天数算，本金为0，后面对应的期数的本金和利息按对应的期数减一算；
				 * 2.第一期足月，最后一期不足月。最后一期利息按实际天数算，本金为0，前面对应的期数的本金和利息按对应的期数算；
				 * 3.第一期不足月，最后一期不足月。第一期利息按实际天数算，本金为0，后面对应的期数的本金和利息按对应的期数减一算； 最后一期利息按实际天数算，本金按对应的期数减一算；
				 * 二、当生成期数等于借款期数时，判断第一期与最后一期是否足月；
				 * 1.第一期不足月，最后一期足月。第一期利息按实际天数算，本金按对应的期数算，后面对应的期数的本金和利息按对应的期数算；
				 * 2.第一期足月，最后一期不足月。最后一期利息按实际天数算，本金按对应的期数算，前面对应的期数的本金和利息按对应的期数算；
				 * 3.第一期不足月，最后一期不足月。第一期利息按实际天数算，本金按对应的期数算，后面对应的期数的本金和利息按对应的期数算； 最后一期利息按实际天数算，本金按对应的期数减一算；
				 */
				BigDecimal monthCapital = BigDecimal.ZERO;
				BigDecimal monthInterest = BigDecimal.ZERO;
				// 计算每期要还利息天数
				int days = this.getDifferenceDays(flagDay, plan.getPaytime());
				flagDay = plan.getPaytime();
				// 每期实际天数利息
				BigDecimal realDaysInterest = new BigDecimal(loanCapital
						* dayRate * days).setScale(2, BigDecimal.ROUND_HALF_UP);
				if(period==1 && fullDaytime.getDayOfMonth() != pojo.getLoanPayDate()){//第一期不足月(放款日不等于还款日)
					System.out.println("第一期不足月");
					monthInterest = realDaysInterest;
					if(totalMonths==pojo.getLoanPeriod()){
						monthCapital = AverageCapitalUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths, period);
					}
				}else if(period == totalMonths && new DateTime(pojo.getLoanExpireDate()).getDayOfMonth() != pojo.getLoanPayDate()){//最后一期不足月
					System.out.println("最后一期不足月");
					monthInterest = realDaysInterest;
					if(fullDaytime.getDayOfMonth() != pojo.getLoanPayDate() && totalMonths-1==pojo.getLoanPeriod()){
						monthCapital = AverageCapitalUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths-1, period-1);
					}else if(totalMonths==pojo.getLoanPeriod()){
						monthCapital = AverageCapitalUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths, period);
					}
				}else{
					if(totalMonths-1==pojo.getLoanPeriod()){
						if(fullDaytime.getDayOfMonth() != pojo.getLoanPayDate()){//第一期不足月(放款日不等于还款日)
							monthCapital = AverageCapitalUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths-1, period-1);
							monthInterest = AverageCapitalUtils.getPerMonthInterest(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths-1, period-1);
						}else{
							monthCapital = AverageCapitalUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths-1, period);
							monthInterest = AverageCapitalUtils.getPerMonthInterest(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths-1, period);
						}
					}else{
						monthCapital = AverageCapitalUtils.getPerMonthPrincipal(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths, period);
						monthInterest = AverageCapitalUtils.getPerMonthInterest(loanCapital, pojo.getLoanRate().doubleValue(), totalMonths, period);
					}
				}
				plan.setPayTotalMoney(monthCapital.add(monthInterest));
				plan.setPayInterest(monthInterest);
				plan.setPayCapital(monthCapital);
				planList.add(plan);
			}
				break;
			case DBDX: {
				/**
				 * 万分之五算法
				 */
				if (period == totalMonths) {
					plan.setLoanPeriod(1);
					int days = this.getDifferenceDays(flagDay,
							pojo.getLoanExpireDate());
					plan.setPayCapital(pojo.getLoanCurrentValidInvest());
					plan.setPayInterest(TenthousandFiveUtils.getInterest(loanCapital, days));
					plan.setPayTotalMoney(plan.getPayInterest().add(
							plan.getPayCapital()));
					planList.add(plan);
				}
			}
				break;
			case DQBX: {
				// 到期本息算法
				if (period == totalMonths) {
					// 利随本清只有一期
					plan.setLoanPeriod(1);
					int days = this.getDifferenceDays(flagDay,
							pojo.getLoanExpireDate());
					plan.setPayCapital(pojo.getLoanCurrentValidInvest());
					plan.setPayInterest(new BigDecimal(loanCapital * dayRate
							* days).setScale(2, BigDecimal.ROUND_HALF_UP));
					plan.setPayTotalMoney(plan.getPayInterest().add(
							plan.getPayCapital()));
					planList.add(plan);
				}
			}
				break;
			default:
				throw new IllegalArgumentException("Illegal LoanPayType");
			}

		}
		logger.info("=====================生成还款计划====================");
		for(PaymentPlan p : planList){
			logger.info("period======={},paytime======={},"
					+ "PayCapital======={},PayInterest======={},TotalMoney======={}"
					,p.getLoanPeriod(),DateUtil.formatDate(p.getPaytime(),"yyyy-MM-dd"),
					p.getPayCapital(),p.getPayInterest(),p.getPayTotalMoney());
		}
		return planList;
	}
	
	@Override
	public List<IncomePlan> buildIncomePlanList(Loan pojo,
			List<Invest> investList, List<PaymentPlan> paymentList,
			int fullTransRecordId) {
		List<IncomePlan> planList = new LinkedList<IncomePlan>();
		int numOfPeriods = paymentList.size(); // 期数

		// 用map存放每个投资者除最后一期所还的本金之和
		Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
		for (Invest invest : investList) {
			map.put(invest.getId(), BigDecimal.ZERO);
		}
		// 说明:（1.每一期的最后一个投资者 2.每个投资者的最后一期）
		// 1.防止出现每期收益计划的总钱数和每期还款计划的钱数不是完全相等的情况，最后一个投资者的收益不能按百分比计算，应该是还款的钱数减去前几个投资人的收益和。
		// 2.对于每个投资者来说，投资金额应该等于各期所还本金之和。所以对于投资者的最后一期收益所获的本金=投资者有效投资金额-前几期所还的本金之和。
		for (int i = 0; i < paymentList.size(); i++) {
			PaymentPlan paymentPlan = paymentList.get(i);
			BigDecimal capitalSum = BigDecimal.ZERO; // 除最后一个投资人所分发的本金之和
			BigDecimal interstSum = BigDecimal.ZERO; // 除最后一个投资人所分发的利息之和
			for (int j = 0; j < investList.size(); j++) {
				IncomePlan incomePlan = new IncomePlan();
				Invest invest = investList.get(j);
				incomePlan.setFullTransRecordId(fullTransRecordId);
				incomePlan.setPaymentPlanId(paymentPlan.getId());
				incomePlan.setInvestId(invest.getId());
				incomePlan.setLoanId(invest.getOriginId());
				incomePlan.setBeneficiary(invest.getUserId());
				incomePlan.setPaytime(paymentPlan.getPaytime());
				incomePlan.setLoanPeriod(paymentPlan.getLoanPeriod());
				incomePlan.setIncomePlanState(IncomePlanState.INACTIVED);
				if (j == investList.size() - 1) {
					if (i+1 == numOfPeriods) { // 每个投资者的最后一期(本金计算)
						incomePlan.setPayCapital(invest.getValidInvestMoney()
								.subtract(map.get(invest.getId())));
					} else {
						incomePlan.setPayCapital(paymentPlan.getPayCapital()
								.subtract(capitalSum));
						map.put(invest.getId(),
								map.get(invest.getId()).add(
										incomePlan.getPayCapital()));
					}
					incomePlan.setPayInterest(paymentPlan.getPayInterest()
							.subtract(interstSum));
					incomePlan.setPayTotalMoney(incomePlan.getPayCapital().add(
							incomePlan.getPayInterest()));
				} else {
					double percent = Arith.div(invest.getValidInvestMoney(),
							pojo.getLoanCurrentValidInvest()).doubleValue();
					if (i+1 == numOfPeriods) { // 每个投资者的最后一期(本金计算)
						incomePlan.setPayCapital(invest.getValidInvestMoney()
								.subtract(map.get(invest.getId())));
					} else {
						incomePlan.setPayCapital(new BigDecimal(paymentPlan
								.getPayCapital().doubleValue() * percent)
								.setScale(2, BigDecimal.ROUND_HALF_UP));
						map.put(invest.getId(),
								map.get(invest.getId()).add(
										incomePlan.getPayCapital()));
					}
					incomePlan.setPayInterest(new BigDecimal(paymentPlan
							.getPayInterest().doubleValue() * percent)
							.setScale(2, BigDecimal.ROUND_HALF_UP));
					incomePlan.setPayTotalMoney(incomePlan.getPayCapital().add(
							incomePlan.getPayInterest()));
					capitalSum = capitalSum.add(incomePlan.getPayCapital()); // 本金累计
					interstSum = interstSum.add(incomePlan.getPayInterest()); // 利息累计
				}
				planList.add(incomePlan);
			}
		}
		logger.info("=====================生成收益计划====================");
		for(IncomePlan p : planList){
			logger.info("period======={},paytime======={},"
					+ "PayCapital======={},PayInterest======={},TotalMoney======={}"
					,p.getLoanPeriod(),DateUtil.formatDate(p.getPaytime(),"yyyy-MM-dd"),
					p.getPayCapital(),p.getPayInterest(),p.getPayTotalMoney());
		}
		return planList;
	}

	@Override
	public List<IncomePlan> buildTransferIncomePlanList(TransferLoan pojo,
			List<IncomePlan> transferIncomeList, List<Invest> investList,
			int fullTransRecordId,Date prePayTime) {
		List<IncomePlan> planList = new LinkedList<IncomePlan>();

		// 第一期待回款计划利息分为两部分：前面一部分归转让人所有，后面一部分受让人瓜分
		// 前面一部分： 收益计划生成日期 or上期应还日期->当前日期
		// 后面一部分：当前日期->还款日期
		BigDecimal afterInterest = null;
		
		IncomePlan firstIncome = transferIncomeList.get(0);// 第一期待回款计划
		BigDecimal allDay = new BigDecimal(DateUtil.substractDay(
				firstIncome.getPaytime(), prePayTime));
		BigDecimal afterDay = new BigDecimal(DateUtil.substractDay(
				firstIncome.getPaytime(), new Date()));
		if(allDay.equals(afterDay)){
			 throw new BusinessException("当日划转计算得转让人的利息为0，请一天后再操作");
		}
		// 计算第一期待回款计划 后面部分利息
		BigDecimal firstAfterInterest = firstIncome.getPayInterest()
				.multiply(afterDay).divide(allDay, 2, BigDecimal.ROUND_HALF_UP);

		for (int i = 0; i < transferIncomeList.size(); i++) {

			IncomePlan oldPlan = transferIncomeList.get(i);
			if (i == 0) {
				afterInterest = firstAfterInterest;
			} else {
				afterInterest = oldPlan.getPayInterest();
			}

			BigDecimal capitalSum = BigDecimal.ZERO; // 除最后一个投资人所分发的本金之和
			BigDecimal interstSum = BigDecimal.ZERO; // 除最后一个投资人所分发的利息之和
			for (int j = 0; j < investList.size(); j++) {
				Invest invest = investList.get(j);
				IncomePlan newPlan = new IncomePlan();
				newPlan.setFullTransRecordId(fullTransRecordId);
				newPlan.setPaymentPlanId(oldPlan.getPaymentPlanId());
				newPlan.setInvestId(invest.getId());
				newPlan.setLoanId(invest.getOriginId());
				newPlan.setBeneficiary(invest.getUserId());
				if (j == investList.size() - 1) {
					newPlan.setPayCapital(oldPlan.getPayCapital().subtract(
							capitalSum));
					newPlan.setPayInterest(afterInterest.subtract(interstSum));
					newPlan.setPayTotalMoney(newPlan.getPayCapital().add(
							newPlan.getPayInterest()));
				} else {
					// 利息*(投资金额/债权价值)
					newPlan.setPayCapital(oldPlan
							.getPayCapital()
							.multiply(invest.getValidInvestMoney())
							.divide(pojo.getTransferMoney(), 2,
									BigDecimal.ROUND_HALF_UP));
					newPlan.setPayInterest(afterInterest.multiply(
							invest.getValidInvestMoney()).divide(
							pojo.getTransferMoney(), 2,
							BigDecimal.ROUND_HALF_UP));
					newPlan.setPayTotalMoney(newPlan.getPayCapital().add(
							newPlan.getPayInterest()));
					capitalSum = capitalSum.add(newPlan.getPayCapital()); // 本金累计，用于最后一个人减去
					interstSum = interstSum.add(newPlan.getPayInterest()); // 利息累计
				}
				newPlan.setPaytime(oldPlan.getPaytime());
				newPlan.setLoanPeriod(oldPlan.getLoanPeriod());
				newPlan.setIncomePlanState(IncomePlanState.INACTIVED);
				planList.add(newPlan);
			}

		}

		// 新增一条转让人的收益计划，回调中将原来的收益计划删掉
		firstIncome.setPayCapital(BigDecimal.ZERO);
		firstIncome.setPayInterest(firstIncome.getPayInterest().subtract(
				firstAfterInterest));
		firstIncome.setPayTotalMoney(firstIncome.getPayInterest());
		firstIncome.setIncomePlanState(IncomePlanState.INACTIVED);
		firstIncome.setFullTransRecordId(fullTransRecordId);
		firstIncome.setTransferLast(Boolean.TRUE);
		planList.add(firstIncome);
		return planList;
	}

	
	
	@Override
	public List<IncomePlan> buildTransferIncomePlanListForTrans(TransferLoan pojo,
			List<IncomePlan> transferIncomeList, List<Invest> investList,
			int fullTransRecordId,Date prePayTime) {
		List<IncomePlan> planList = new LinkedList<IncomePlan>();

		// 第一期待回款计划利息分为两部分：前面一部分归转让人所有，后面一部分受让人瓜分
		// 前面一部分： 收益计划生成日期 or上期应还日期->当前日期
		// 后面一部分：当前日期->还款日期	
		
		 // 新建一个map对象，其键为投资记录的ID，存储的值为该投资人每次应得还款总金额的累加和（供计算最后一期应得还款金额）
	      Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
	      for (int k = 0; k < investList.size(); k++) {
	          map.put(investList.get(k).getId(), BigDecimal.ZERO);// 初始化map对象
	      }
	      int numOfPeriods = transferIncomeList.size(); // 期数
	      
		for (int i = 0; i < transferIncomeList.size(); i++) {

			IncomePlan oldPlan = transferIncomeList.get(i);
			
			BigDecimal capitalSum = BigDecimal.ZERO; // 除最后一个投资人所分发的本金之和
			BigDecimal interstSum = BigDecimal.ZERO; // 除最后一个投资人所分发的利息之和
			for (int j = 0; j < investList.size(); j++) {
				Invest invest = investList.get(j);
				IncomePlan newPlan = new IncomePlan();
				newPlan.setFullTransRecordId(fullTransRecordId);
				newPlan.setPaymentPlanId(oldPlan.getPaymentPlanId());
				newPlan.setInvestId(invest.getId());
				newPlan.setLoanId(invest.getOriginId());
				newPlan.setBeneficiary(invest.getUserId());
				if (j == investList.size() - 1) {
					if(i+1 == numOfPeriods){
						//最后一期应得本金=投资的本金-除最后一期所分发的本金之和，这样会造成投资人的本金不一致的情况 bug 56219
						newPlan.setPayCapital(invest.getValidInvestMoney().subtract(map.get(invest.getId())));
					}else{
						newPlan.setPayCapital(oldPlan.getPayCapital().subtract(
								capitalSum));
						map.put(invest.getId(), map.get(invest.getId()).add(newPlan.getPayCapital()));// 累加已获得本金
					}	
					newPlan.setPayInterest(oldPlan.getPayInterest().subtract(interstSum));
					newPlan.setPayTotalMoney(newPlan.getPayCapital().add(
							newPlan.getPayInterest()));
				} else {
					if(i+1 == numOfPeriods){
						//最后一期应得本金=投资的本金-除最后一期所分发的本金之和，这样会造成投资人的本金不一致的情况 bug 56219
						newPlan.setPayCapital(invest.getValidInvestMoney().subtract(map.get(invest.getId())));
					}else{
						// 本金*(投资金额/债权价值)
						newPlan.setPayCapital(oldPlan
								.getPayCapital()
								.multiply(invest.getValidInvestMoney())
								.divide(pojo.getTransferMoney().subtract(pojo.getCurrentValidInvest()), 2,
										BigDecimal.ROUND_HALF_UP));
						map.put(invest.getId(), map.get(invest.getId()).add(newPlan.getPayCapital()));// 累加已获得本金
					}
					// 利息*(投资金额/债权价值)
					newPlan.setPayInterest(oldPlan.getPayInterest().multiply(
							invest.getValidInvestMoney()).divide(
							pojo.getTransferMoney().subtract(pojo.getCurrentValidInvest()), 2,
							BigDecimal.ROUND_HALF_UP));
					newPlan.setPayTotalMoney(newPlan.getPayCapital().add(
							newPlan.getPayInterest()));
					capitalSum = capitalSum.add(newPlan.getPayCapital()); // 本金累计，用于最后一个人减去
					interstSum = interstSum.add(newPlan.getPayInterest()); // 利息累计
				}
				newPlan.setPaytime(oldPlan.getPaytime());
				newPlan.setLoanPeriod(oldPlan.getLoanPeriod());
				newPlan.setIncomePlanState(IncomePlanState.GOING);
				planList.add(newPlan);
			}

		}
		return planList;
	}
	
	@Override
	public List<IncomePlan> buildBuyIncomePlanList(BuyLoan pojo,
			List<IncomePlan> buyIncomeList, List<Invest> investList,
			int fullTransRecordId) {
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
			newPlan.setInvestId(userIdToInvestIdMap.get(oldPlan
					.getBeneficiary()));
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

	/**
	 * 计算两个时间相差月份
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public int getMonthSpace(Date date1, Date date2) {

		int result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		try {
			c1.setTime(date1);
			c2.setTime(date2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int c1Day1 = c1.get(Calendar.DAY_OF_MONTH);
		while (c1.before(c2)) {
			result++;
			int c1Day2 = c1.get(Calendar.DAY_OF_MONTH);
			if(c1Day2<c1Day1){
				c1.add(Calendar.MONTH, 1);
				int c1Day3 = c1.getActualMaximum(Calendar.DATE);;
				if(c1Day3>c1Day1){
					c1.add(Calendar.DATE, c1Day1-c1Day2);
				}else{
					c1.add(Calendar.DATE, c1Day3-c1Day2);
				}
			}else{
				c1.add(Calendar.MONTH, 1);
			}
		}

		return result == 0 ? 1 : Math.abs(result);

	}

	/**
	 * 计算两个时间相差天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public int getDifferenceDays(Date date1, Date date2) {

		int loanDays = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		try {
			c1.setTime(date1);
			c2.setTime(date2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (c1.before(c2)) {
			loanDays++;
			c1.add(Calendar.DAY_OF_YEAR, 1);
		}

		return loanDays;
	}

	@Override
	public List<IncomePlan> distributionPenalty(Loan loan,
			List<Invest> invests, List<IncomePlan> incomePlans,
			BigDecimal pulishMoney) {
		BigDecimal sumPulishMoney = new BigDecimal(0); // 除最后一个投资人所分配的罚金总额
		
		/*
		 * edit by 夏同同，此处试用(本金+利息)来计算分配，因为本金和利息都有可能是0，此时会出错
		 * 改成使用本金来计算分配
		 */
		// 总利息
		//BigDecimal sumInterest = BigDecimal.ZERO;
		//for (IncomePlan income : incomePlans) {
			//sumInterest = sumInterest.add(income.getPayInterest());
		//}
		// 总本息
		BigDecimal sumCapitalInterest = BigDecimal.ZERO;
		for (IncomePlan income : incomePlans) {
			sumCapitalInterest = sumCapitalInterest.add(income.getPayCapital())
					.add(income.getPayInterest());
		}
		
		//瓜分罚金，按利息比例分
		//for (int i = 0; i < incomePlans.size(); i++) {
			//IncomePlan incomePlan = incomePlans.get(i);
			//if (i == incomePlans.size() - 1) {
				//incomePlan.setPayFine(pulishMoney.subtract(sumPulishMoney));
				//incomePlan.setPayTotalMoney(incomePlan.getPayTotalMoney().add(
						//incomePlan.getPayFine()));
			//} else {
				// 罚金：总罚金*(当前人的利息/所有人总利息)
				//BigDecimal payFine = pulishMoney.multiply(
						//incomePlan.getPayInterest()).divide(sumInterest, 2,
						//BigDecimal.ROUND_HALF_UP);
				//incomePlan.setPayFine(payFine);
				//incomePlan.setPayTotalMoney(incomePlan.getPayTotalMoney().add(
						//payFine));
				//sumPulishMoney = sumPulishMoney.add(payFine);
			//}
		//}
		
		//瓜分罚金，按本金比例分
		for (int i = 0; i < incomePlans.size(); i++) {
			IncomePlan incomePlan = incomePlans.get(i);
			if (i == incomePlans.size() - 1) {
				incomePlan.setPayFine(pulishMoney.subtract(sumPulishMoney));
				incomePlan.setPayTotalMoney(incomePlan.getPayTotalMoney().add(
						incomePlan.getPayFine()));
			} else {
				// 罚金：总罚金*(当前人的利息/所有人总利息)
				BigDecimal payFine = pulishMoney.multiply(
							incomePlan.getPayCapital().add(incomePlan.getPayInterest())
						).divide(sumCapitalInterest, 2,
						BigDecimal.ROUND_HALF_UP);
				incomePlan.setPayFine(payFine);
				incomePlan.setPayTotalMoney(incomePlan.getPayTotalMoney().add(
						payFine));
				sumPulishMoney = sumPulishMoney.add(payFine);
			}
		}
		return incomePlans;
	}
}
