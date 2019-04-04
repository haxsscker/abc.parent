package com.autoserve.abc.service.biz.impl.loan.plan;

/**
 * Description:等额本息工具类
 * @author: sunlu
 */
 
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
 
/**
 * 等额本息还款，也称定期付息，即借款人每月按相等的金额偿还贷款本息，其中每月贷款利息按月初剩余贷款本金计算并逐月结清。把按揭贷款的本金总额与利息总额相加，
 * 然后平均分摊到还款期限的每个月中。作为还款人，每个月还给银行固定金额，但每月还款额中的本金比重逐月递增、利息比重逐月递减。
 */
 
public class AverageCapitalPlusInterestUtils {
	
	private static ThreadLocal<Map<Integer, BigDecimal>>  principalThreadLocal = new ThreadLocal<Map<Integer, BigDecimal>>();
	
	private static ThreadLocal<Map<Integer, BigDecimal>> interestThreadLocal = new ThreadLocal<Map<Integer, BigDecimal>>();
 
    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还本金和利息
     * 
     * 公式：每月偿还本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
     * 
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 每月偿还本金和利息
     */
    public static BigDecimal getPerMonthPrincipalInterest(double invest, double yearRate, int totalmonth) {
        double monthRate = yearRate/12/100;
        BigDecimal monthIncome = new BigDecimal(invest)
                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth)))
                .divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth) - 1), 2, BigDecimal.ROUND_HALF_UP);
        return monthIncome;
    }
 
    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还利息
     * 
     * 每月利息  = 剩余本金 x 贷款月利率
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 每月偿还利息
     */
    public static Map<Integer, BigDecimal> getPerMonthInterest(double invest, double yearRate, int totalmonth) {
        Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
        BigDecimal monthIncome = getPerMonthPrincipalInterest(invest,yearRate,totalmonth);
        Map<Integer, BigDecimal> mapPrincipal = getPerMonthPrincipal(invest, yearRate, totalmonth);
        for (Map.Entry<Integer, BigDecimal> entry : mapPrincipal.entrySet()) {
        	map.put(entry.getKey(), monthIncome.subtract(entry.getValue()));
        }
        return map;
    }
    
    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还本金
     * 每月本金 = 本金×月利率×(1+月利率)^(还款月序号-1)÷((1+月利率)^还款月数-1))
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 每月偿还本金
     */
    public static Map<Integer, BigDecimal> getPerMonthPrincipal(double invest, double yearRate, int totalmonth) {
    	double monthRate = yearRate/12/100;
    	Map<Integer, BigDecimal> mapPrincipal = new HashMap<Integer, BigDecimal>();
    	BigDecimal frontTotalPrincipal=BigDecimal.ZERO;// 前几期的总本金
        BigDecimal monthCapital;
        for (int i = 1; i < totalmonth + 1; i++) {
        	BigDecimal multiply = new BigDecimal(invest).multiply(new BigDecimal(monthRate));
        	BigDecimal sub  = new BigDecimal(Math.pow(1 + monthRate, i-1));
        	monthCapital = multiply.multiply(sub).divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth)-1), 6, BigDecimal.ROUND_DOWN);
        	monthCapital = monthCapital.setScale(2, BigDecimal.ROUND_HALF_UP);
        	if(i<totalmonth){
        		mapPrincipal.put(i,monthCapital);
        	}else{
        		mapPrincipal.put(i,new BigDecimal(invest).subtract(frontTotalPrincipal));
        	}
        	frontTotalPrincipal = frontTotalPrincipal.add(monthCapital);
        }
        return mapPrincipal;
    }
    /**
     * 获取等额本息某一期的本金
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
     * 获取等额本息某一期的利息
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
     * 等额本息计算获取还款方式为等额本息的总利息
     * 
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 总利息
     */
    public static double getInterestCount(double invest, double yearRate, int totalmonth) {
        BigDecimal count = new BigDecimal(0);
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, totalmonth);
 
        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            count = count.add(entry.getValue());
        }
        return count.doubleValue();
    }
    /**
     * 等额本息计算获取还款方式为等额本息的总本金
     * 
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 总利息
     */
    public static double getPrincipalCount(double invest, double yearRate, int totalmonth) {
        BigDecimal count = new BigDecimal(0);
        Map<Integer, BigDecimal> mapInterest = getPerMonthPrincipal(invest, yearRate, totalmonth);
 
        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            count = count.add(entry.getValue());
        }
        return count.doubleValue();
    }
    /**
     * 应还本金利息总和
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 应还本金总和
     */
    public static double getPrincipalInterestCount(double invest, double yearRate, int totalmonth) {
        BigDecimal monthIncome = getPerMonthPrincipalInterest(invest,yearRate,totalmonth);
        BigDecimal count = monthIncome.multiply(new BigDecimal(totalmonth));
        count = count.setScale(2, BigDecimal.ROUND_DOWN);
        return count.doubleValue();
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        double invest = 10000; // 本金
        int month = 12;
        double yearRate = 15; // 年利率
        BigDecimal perMonthPrincipalInterest = getPerMonthPrincipalInterest(invest, yearRate, month);
        System.out.println("等额本息---每月还款本息：" + perMonthPrincipalInterest);
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, month);
        System.out.println("等额本息---每月还款利息：" + mapInterest);
        Map<Integer, BigDecimal> mapPrincipal = getPerMonthPrincipal(invest, yearRate, month);
        System.out.println("等额本息---每月还款本金：" + mapPrincipal);
        double interestCount = getInterestCount(invest, yearRate, month);
        System.out.println("等额本息---总利息：" + interestCount);
        double principalCount = getPrincipalCount(invest, yearRate, month);
        System.out.println("等额本息---总本金：" + principalCount);
        double principalInterestCount = getPrincipalInterestCount(invest, yearRate, month);
        System.out.println("等额本息---应还本息总和：" + principalInterestCount);
        BigDecimal principal1 = getPerMonthPrincipal(invest, yearRate, month,2);
        System.out.println("等额本息---应还第2期本金：" + principal1);
        BigDecimal interest1 = getPerMonthInterest(invest, yearRate, month,2);
        System.out.println("等额本息---应还第2期利息：" + interest1);
    }
}

