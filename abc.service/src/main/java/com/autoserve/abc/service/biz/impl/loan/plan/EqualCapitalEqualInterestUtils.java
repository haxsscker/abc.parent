package com.autoserve.abc.service.biz.impl.loan.plan;

import java.math.BigDecimal;

/**
 * 等本等息（客户采用的是信用卡的分期方式）
 * 分期业务最长可以分24期，每月手续费是消费金额的0.6%,借款金额5400

　　每月还款本金5400/24=225

　　每月手续费5400*0.6%=32.4

　　合计每月要还257.4
 * @author sl
 *
 */
public class EqualCapitalEqualInterestUtils {
	/**
	 * 获取每月还款本金
	 * @param invest 总借款额（贷款本金）
	 * @param totalmonth 还款总月数
	 * @return 每月还款本金
	 */
	public static BigDecimal getPerMonthPrincipal(double invest, int totalmonth){
		return new BigDecimal(invest).divide(new BigDecimal(totalmonth), 2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 获取每月还款利息
	 * @param invest 总借款额（贷款本金）
	 * @param yearRate 年利率
	 * @return
	 */
	public static BigDecimal getPerMonthInterest(double invest, double yearRate){
		double monthRate = yearRate/12;
		return new BigDecimal(invest).multiply(new BigDecimal(monthRate)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
	}
}
