package com.autoserve.abc.service.biz.impl.loan.plan;

import java.math.BigDecimal;

/**
 * Description:万分之五计息工具类
 * 一万元一天的利息就是：10000×0.05%=5;因此叫万分之五。
 * @author sunlu
 *
 */
public class TenthousandFiveUtils {
	
	private static final double DAYRATE = 0.0005;//日利率为0.05%
	/**
	 * 万分之五算法获取利息
	 * @param invest 总借款额（贷款本金）
	 * @param days 借款天数
	 * @return
	 */
	public static BigDecimal getInterest(double invest,int days) {
        BigDecimal interest = new BigDecimal(invest).multiply(new BigDecimal(DAYRATE).multiply(new BigDecimal(days)));
        interest = interest.setScale(2, BigDecimal.ROUND_HALF_UP);
        return interest;
    }
	public static void main(String[] args) {
		double invest = 10000; // 本金
        int month = 12;
        int days=month*30;
        BigDecimal interest = getInterest(invest,days);
        System.out.println("万分之五算法---总利息："+interest);
	}
}
