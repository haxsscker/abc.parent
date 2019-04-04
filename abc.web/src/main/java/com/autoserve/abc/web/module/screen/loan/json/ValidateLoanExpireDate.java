/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.loan.json;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilderByDayRate;
import com.autoserve.abc.service.util.DateUtil;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 验证项目到期日与借款期限
 * 
 * @author sunlu 2018年8月22日
 */
public class ValidateLoanExpireDate {
    private static final Log log = LogFactory.getLog(ValidateLoanExpireDate.class);

    public JsonBaseVO execute(Context context, ParameterParser params) {
    	JsonBaseVO result = new JsonBaseVO();
        try {
            String loanPeriodUnit = params.getString("loanPeriodUnit");//期限单位
            Integer pro_pay_date = Integer.valueOf(params.getString("pro_pay_date"));//付息日
            DateTime loanExpireDate = new DateTime(params.getString("loanExpireDate"));//项目到期日
            Integer pro_loan_period = Integer.valueOf(params.getString("pro_loan_period"));//借款期限
            DateTime loanInvestStarttime = new DateTime(DateUtil.parseDate(params.getString("loanInvestStarttime")));//投资开始时间
            DateTime pro_invest_endDate = new DateTime(params.getString("pro_invest_endDate"));//招标到期日
            if("1".equals(loanPeriodUnit)){//年标
            	pro_loan_period = pro_loan_period*12;
            }
            int months = buildTotalMonths(loanInvestStarttime,loanExpireDate,pro_pay_date);
            int months1 = buildTotalMonths(pro_invest_endDate,loanExpireDate,pro_pay_date);
            if(months > pro_loan_period+1){
            	result.setSuccess(false);
                result.setMessage("招标开始时间到项目到期日的实际期数，大于借款期限+1，请调整招标开始时间或项目到期日！");
                return result;
            }else if(months < pro_loan_period){
            	result.setSuccess(false);
                result.setMessage("招标开始时间到项目到期日的实际期数，小于借款期限，请调整招标开始时间或项目到期日！");
                return result;
            }
            if(months1 < pro_loan_period){
            	result.setSuccess(false);
                result.setMessage("招标结束日到项目到期日的实际期数，小于借款期限，请调整招标结束日或项目到期日！");
                return result;
            }
        } catch (Exception e) {
            if (log.isInfoEnabled()) {
                log.info("解析出错", e);
            }
            result.setSuccess(false);
            result.setMessage("参数解析失败");
            return result;
        }
        return result;
    }

	/**
	 * 计算2个日期之间的月数
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param payDate 还款日
	 * @return
	 */
    private int buildTotalMonths(DateTime startDate,DateTime endDate,Integer payDate) {

    	PlanBuilderByDayRate planBuilderByDayRate = PlanBuilderByDayRate.getInstance();

		int totalMonths = planBuilderByDayRate.getMonthSpace(startDate.toDate(),endDate.toDate());

		if (endDate.getDayOfMonth() > payDate && payDate > startDate.getDayOfMonth()) {
			totalMonths = totalMonths + 1;
		}
		if (endDate.getDayOfMonth() < payDate
				&& payDate < startDate.getDayOfMonth()) {
			return totalMonths;
		}
		if (endDate.getDayOfMonth() != payDate
				&& endDate.getDayOfMonth() <= startDate.getDayOfMonth()
				&& payDate != startDate.getDayOfMonth()) {
			totalMonths = totalMonths + 1;
		}

		return totalMonths;
	}
    public static void main(String[] args) {
    	ValidateLoanExpireDate ve = new ValidateLoanExpireDate();
    	DateTime loanInvestStarttime = new DateTime("2018-08-15");//投资开始时间
    	DateTime loanInvestendtime = new DateTime("2018-10-10");//投资结束时间
    	DateTime loanExpireDate = new DateTime("2018-10-15");//项目到期日
    	int r = ve.buildTotalMonths(loanInvestStarttime, loanExpireDate, 10);
    	int r1 = ve.buildTotalMonths(loanInvestendtime, loanExpireDate, 10);
    	System.out.println(r+"===="+r1);
	}
}
