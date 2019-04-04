package com.autoserve.abc.web.module.screen.loanpay;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.loan.repay.RepayService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.util.NumberUtil;

/**
 * 类RepaymentView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年1月10日 下午9:52:23
 */
public class RepaymentView {

	@Resource
	private PaymentPlanService paymentPlanService;
	@Resource
	private RepayService repayService;
	@Resource
	private SysConfigService sysConfigService;

	public void execute(Context context, ParameterParser params) {
		int loanId = params.getInt("loanId");

		int planId = params.getInt("planId");
		PlainResult<BigDecimal> overResult = repayService.queryPulishMoney(planId);
		PlainResult<PaymentPlan> result = this.paymentPlanService.queryById(planId);
		// 计算罚金
		PaymentPlan paymentPlan2 = result.getData();
		Map<String, BigDecimal> pulishMoneys = computePulishMoney(paymentPlan2);
		BigDecimal pulishMoney = pulishMoneys.get("pulishMoney");
		BigDecimal payFine = paymentPlan2.getPayFine().subtract(paymentPlan2.getPayCollectFine()).add(pulishMoney);
		
		BigDecimal pulishBreachMoney = pulishMoneys.get("pulishBreachMoney");
		BigDecimal payBreachFine = paymentPlan2.getPayBreachFine().subtract(paymentPlan2.getPayCollectBreachFine()).add(pulishBreachMoney);
		
		context.put("loanId", loanId);
		context.put("plan", result.getData());
		context.put("planId", planId);
		context.put("overMonery", NumberUtil.moneyFormat(overResult.getData().doubleValue()));
		context.put("payFine", payFine);
		context.put("payBreachFine", payBreachFine);
		context.put("payServiceFee", paymentPlan2.getPayServiceFee());
	}

	/**
	 * @content:计算逾期罚金合违约罚金
	 * @author:夏同同
	 * @date:2016年4月10日 上午11:01:23
	 */
	private Map<String, BigDecimal> computePulishMoney(PaymentPlan repayPlan) {
		Map<String, BigDecimal> pulishMoneys = new HashMap<String, BigDecimal>();
		BigDecimal pulishMoney; // 逾期罚金
		BigDecimal pulishBreachMoney; // 违约罚金

        //edit by 夏同同 20160421 计算天数时不应该考虑时分秒
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        // 判断是否逾期
        DateTime nowTime = new DateTime(sf.format(new Date()));
        DateTime planPayTime = new DateTime(sf.format(repayPlan.getPaytime()));

		// 如果逾期则查询罚息利率并计算罚息
		if (nowTime.isAfter(planPayTime)
				&& !nowTime.toString("MM/dd/yyyy").equals(planPayTime.toString("MM/dd/yyyy"))) {
			// 逾期罚息利率
			PlainResult<SysConfig> punishRateResult = sysConfigService
					.querySysConfig(SysConfigEntry.PUNISH_INTEREST_RATE);
			if (!punishRateResult.isSuccess()) {
				throw new BusinessException("罚息利率查询失败");
			}
			// 违约罚息利率
			PlainResult<SysConfig> punishBreachRateResult = sysConfigService
					.querySysConfig(SysConfigEntry.PUNISH_BREACH_INTEREST_RATE);
			if(!punishBreachRateResult.isSuccess()){
				throw new BusinessException("违约罚息利率查询失败");
			}

			// 天逾期罚息利率
			BigDecimal rate = new BigDecimal(punishRateResult.getData().getConfValue())
					.divide(new BigDecimal(100 * 360), 10, BigDecimal.ROUND_HALF_UP);
			double punishRate = rate.doubleValue();
			
			// 天违约罚息利率
			BigDecimal breachRate = new BigDecimal(punishBreachRateResult.getData().getConfValue())
					.divide(new BigDecimal(100 * 360), 10, BigDecimal.ROUND_HALF_UP);
			double punishBreachRate = breachRate.doubleValue();

			// 天数
			int expiryDays = Days.daysBetween(planPayTime, nowTime).getDays();
			// 本标本期未还款的本金
			PlainResult<BigDecimal> remainingPrincipalResult = paymentPlanService
					.queryRemainPrincipalByLoanidAndPeriod(repayPlan.getLoanId(), repayPlan.getLoanPeriod());
			BigDecimal remainingPrincipal = remainingPrincipalResult.getData();
			// 本标本期未还款的利息
			PlainResult<BigDecimal> remainingInterestResult = paymentPlanService
					.queryRemainInterestByLoanidAndPeriod(repayPlan.getLoanId(), repayPlan.getLoanPeriod());
			BigDecimal remainingInterest = remainingInterestResult.getData();
			/**
			 * 修改罚息计算公式,夏同同,2016年4月10日 上午11:01:23 罚息 = 剩余本金 * 罚息利率 * 逾期天数 +
			 * 剩余罚金(作废) 罚息 = (本期剩余本金+本期剩余利息） * 罚息利率 * 逾期天数 + 剩余罚金 剩余本金 = 应还本金 -
			 * 实还本金(作废) 本期剩余本金 = 本期应还本金 - 本期实还本金 本期剩余利息 = 本期应还利息 - 本期实还利息
			 * 罚息利率=罚息月利率/100/30 逾期天数 = 当前日期 - （实还日期（如果借款人还过部分款） 或
			 * 应还日期（如果借款人没有还过款））
			 */
			//计算逾期罚金
			pulishMoney = (remainingPrincipal.add(remainingInterest)).multiply(new BigDecimal(punishRate * expiryDays))
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			//计算违约罚金
			pulishBreachMoney = (remainingPrincipal.add(remainingInterest)).multiply(new BigDecimal(punishBreachRate * expiryDays))
					.setScale(2, BigDecimal.ROUND_HALF_UP);
		} else {
			pulishMoney = BigDecimal.ZERO;
			pulishBreachMoney = BigDecimal.ZERO;
		}
		pulishMoneys.put("pulishMoney", pulishMoney);
		pulishMoneys.put("pulishBreachMoney", pulishBreachMoney);
		return pulishMoneys;
	}

	// /**
	// * 查询罚息利率并计算罚息
	// * 注释 夏同同
	// */
	// private BigDecimal computePulishMoney(PaymentPlan repayPlan) {
	// BigDecimal pulishMoney;
	//
	// // 判断是否逾期
	// DateTime nowTime = DateTime.now();
	// DateTime planPayTime = new DateTime(repayPlan.getPaytime());
	//
	// // 如果逾期则查询罚息利率并计算罚息
	// if (nowTime.isAfter(planPayTime) &&
	// !nowTime.toString("MM/dd/yyyy").equals(planPayTime.toString("MM/dd/yyyy")))
	// {
	// //罚息利率
	// PlainResult<SysConfig> punishRateResult = sysConfigService
	// .querySysConfig(SysConfigEntry.PUNISH_INTEREST_RATE);
	// if (!punishRateResult.isSuccess()) {
	// throw new BusinessException("罚息利率查询失败");
	// }
	//// MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
	//
	// //天罚息利率
	// BigDecimal rate = new
	// BigDecimal(punishRateResult.getData().getConfValue()).divide(
	// new BigDecimal(100 * 360), 10,BigDecimal.ROUND_HALF_UP);
	// double punishRate = rate.doubleValue();
	//
	// //天数
	// int expiryDays = Days.daysBetween(planPayTime, nowTime).getDays();
	// //未还本金
	// PlainResult<BigDecimal>
	// remainingPrincipalResult=paymentPlanService.queryRemainPrincipalByLoanidAndPeriod(repayPlan.getLoanId(),
	// repayPlan.getLoanPeriod());
	// BigDecimal remainingPrincipal=remainingPrincipalResult.getData();
	// /**
	// * 罚息 = 剩余本金 * 罚息利率 * 逾期天数 + 剩余罚金<br>
	// * 剩余本金 = 应还本金 - 实还本金<br>
	// * 罚息利率=罚息月利率/100/30<br>
	// * 逾期天数 = 当前日期 - （实还日期（如果借款人还过部分款） 或 应还日期（如果借款人没有还过款））<br>
	// */
	//
	// /**
	// * 当前为 罚金 = 未还本金* 天罚息利率* 逾期天数<br>
	// */
	// pulishMoney = remainingPrincipal.multiply(new BigDecimal(punishRate *
	// expiryDays)).setScale(2,BigDecimal.ROUND_HALF_UP);
	//
	//// pulishMoney=pulishMoney.abs(mc);
	// } else {
	// pulishMoney = BigDecimal.ZERO;
	// }
	//
	// return pulishMoney;
	// }
}
