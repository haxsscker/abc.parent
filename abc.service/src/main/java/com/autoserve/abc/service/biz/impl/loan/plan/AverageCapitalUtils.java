package com.autoserve.abc.service.biz.impl.loan.plan;

/**
 * Description:等额本金工具类
 * @author: sunlu
 */
 
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
 
/**
 * 等额本金是指一种贷款的还款方式，是在还款期内把贷款数总额等分，每月偿还同等数额的本金和剩余贷款在该月所产生的利息，这样由于每月的还款本金额固定，
 * 而利息越来越少，借款人起初还款压力较大，但是随时间的推移每月还款数也越来越少。
 */
public class AverageCapitalUtils {
	
	private static ThreadLocal<Map<Integer, BigDecimal>>  principalThreadLocal = new ThreadLocal<Map<Integer, BigDecimal>>();
	
	private static ThreadLocal<Map<Integer, BigDecimal>> interestThreadLocal = new ThreadLocal<Map<Integer, BigDecimal>>();
    /**
     * 等额本金计算获取还款方式为等额本金的每月偿还本金和利息
     * 
     * 公式：每月偿还本金和利息=(贷款本金÷还款月数)+(贷款本金-已归还本金累计额)×月利率
     * 
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 每月偿还本金和利息
     */
    public static Map<Integer, BigDecimal> getPerMonthPrincipalInterest(double invest, double yearRate, int totalMonth) {
        Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
        // 每月本金
        BigDecimal monthPri = getPerMonthPrincipal(invest, totalMonth);
        // 获取月利率
        double monthRate = yearRate / 12/100;
        BigDecimal totalPrincipalAmount = BigDecimal.ZERO; //已归还本金累计额
        BigDecimal monthInterest;//每月利息
        BigDecimal monthRes;//每月偿还本金和利息
        for (int i = 1; i <= totalMonth; i++) {
        	if(i==totalMonth) monthPri = new BigDecimal(invest).subtract(totalPrincipalAmount);
            monthInterest = new BigDecimal(invest).subtract(totalPrincipalAmount).multiply(new BigDecimal(monthRate));
            monthRes = monthPri.add(monthInterest).setScale(2, BigDecimal.ROUND_HALF_UP);
            map.put(i, monthRes);
            totalPrincipalAmount=totalPrincipalAmount.add(monthPri);
        }
        return map;
    }
    /**
     * 等额本金计算获取还款方式为等额本金的每月偿还利息
     * 
     * 公式：每月应还利息=剩余本金×月利率=(贷款本金-已归还本金累计额)×月利率
     * 
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 每月偿还利息
     */
    public static Map<Integer, BigDecimal> getPerMonthInterest(double invest, double yearRate, int totalMonth) {
        Map<Integer, BigDecimal> inMap = new HashMap<Integer, BigDecimal>();
        Map<Integer, BigDecimal> mapPerMonthPrincipal = getPerMonthPrincipal(invest,yearRate,totalMonth);
        Map<Integer, BigDecimal> mapPerMonthPrincipalInterest = getPerMonthPrincipalInterest(invest, yearRate, totalMonth);
        for (Map.Entry<Integer, BigDecimal> entry : mapPerMonthPrincipalInterest.entrySet()) {
        	BigDecimal interestBigDecimal = entry.getValue().subtract(mapPerMonthPrincipal.get(entry.getKey()));
        	inMap.put(entry.getKey(), interestBigDecimal);	
        }
        // 获取月利率
        /*BigDecimal monthPri = getPerMonthPrincipal(invest, totalMonth);
         double monthRate = yearRate / 12/100;
        BigDecimal totalPrincipalAmount = BigDecimal.ZERO; //已归还本金累计额
        BigDecimal monthInterest;//每月利息
        for (int i = 1; i <= totalMonth; i++) {
        	if(i==totalMonth) monthPri = new BigDecimal(invest).subtract(totalPrincipalAmount);
            monthInterest = new BigDecimal(invest).subtract(totalPrincipalAmount).multiply(new BigDecimal(monthRate));
            monthInterest = monthInterest.setScale(2, BigDecimal.ROUND_DOWN);
            inMap.put(i, monthInterest);
            totalPrincipalAmount=totalPrincipalAmount.add(monthPri);
        }*/
        return inMap;
    }
    /**
     * 等额本金计算获取还款方式为等额本金的每月偿还本金
     * 
     * 公式：每月应还本金=贷款本金÷还款月数
     * 
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 每月偿还本金
     */
    public static Map<Integer, BigDecimal> getPerMonthPrincipal(double invest, double yearRate, int totalMonth) {
        Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
        BigDecimal monthPri = getPerMonthPrincipal(invest, totalMonth);
        BigDecimal totalPrincipalAmount = BigDecimal.ZERO; //已归还本金累计额
        for (int i = 1; i <= totalMonth; i++) {
        	if(i==totalMonth) monthPri = new BigDecimal(invest).subtract(totalPrincipalAmount);
            map.put(i, monthPri);
            totalPrincipalAmount=totalPrincipalAmount.add(monthPri);
        }
        return map;
    }
    /**
     * 等额本金计算获取还款方式为等额本金的每月偿还本金
     * 
     * 公式：每月应还本金=贷款本金÷还款月数
     * 
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 每月偿还本金
     */
    public static BigDecimal getPerMonthPrincipal(double invest, int totalMonth) {
        BigDecimal monthIncome = new BigDecimal(invest).divide(new BigDecimal(totalMonth), 2, BigDecimal.ROUND_DOWN);
        return monthIncome;
    }
    /**
     * 获取等额本金某一期的本金
     * @param invest 总借款额（贷款本金）
     * @param yearRate 年利率
     * @param totalmonth 还款总月数
     * @param period 期数
     * @return
     */
    public static BigDecimal getPerMonthPrincipal(double invest, double yearRate, int totalmonth,int period) {
    	if(null == principalThreadLocal.get()){
    		principalThreadLocal.set(getPerMonthPrincipal(invest, yearRate, totalmonth));
    	}
        return principalThreadLocal.get().get(period);
    }
    /**
     * 获取等额本金某一期的利息
     * @param invest 总借款额（贷款本金）
     * @param yearRate 年利率
     * @param totalmonth 还款总月数
     * @param period 期数
     * @return
     */
    public static BigDecimal getPerMonthInterest(double invest, double yearRate, int totalmonth,int period) {
    	if(null == interestThreadLocal.get()){
    		interestThreadLocal.set(getPerMonthInterest(invest, yearRate, totalmonth));
    	}
        return interestThreadLocal.get().get(period);
    }
    /**
     * 等额本金计算获取还款方式为等额本金的总利息
     * 
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 总利息
     */
    public static double getInterestCount(double invest, double yearRate, int totalMonth) {
        BigDecimal count = new BigDecimal(0);
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, totalMonth);
 
        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            count = count.add(entry.getValue());
        }
        return count.doubleValue();
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        double invest = 10000; // 本金
        int month = 12;
        double yearRate = 15; // 年利率
        Map<Integer, BigDecimal> getPerMonthPrincipalInterest = getPerMonthPrincipalInterest(invest, yearRate, month);
        System.out.println("等额本金---每月本息：" + getPerMonthPrincipalInterest);
        Map<Integer, BigDecimal> benjin = getPerMonthPrincipal(invest,yearRate, month);
        System.out.println("等额本金---每月本金:" + benjin);
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, month);
        System.out.println("等额本金---每月利息:" + mapInterest);
 
        double count = getInterestCount(invest, yearRate, month);
        System.out.println("等额本金---总利息：" + count);
    }
}

