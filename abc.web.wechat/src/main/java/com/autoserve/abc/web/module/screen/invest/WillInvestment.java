package com.autoserve.abc.web.module.screen.invest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.LoanSearchDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.Arith;

public class WillInvestment {

	@Resource
	LoanQueryService loanQueryService;

	public void execute(Context context, ParameterParser params, Navigator nav) {
		int pageSize = 10;
		int currentPage = 1;
		PageCondition pageCondition = new PageCondition(currentPage, pageSize);
		LoanSearchDO searchDO = new LoanSearchDO();
		searchDO.setLoanState(Arrays.asList(LoanState.BID_INVITING.state,
				LoanState.FULL_WAIT_REVIEW.state,
				LoanState.FULL_REVIEW_PASS.state,
				LoanState.FULL_REVIEW_FAIL.state, LoanState.BID_CANCELED.state,
				LoanState.MONEY_TRANSFERING.state, LoanState.REPAYING.state,
				LoanState.REPAY_COMPLETED.state));
		searchDO.setFlag(0);//排序
		PageResult<Loan> resultLoanList = this.loanQueryService
				.queryLoanListBySearchParam(searchDO, pageCondition);
		// 百分比
		Map<Integer, BigDecimal> resultLoanListMap = new HashMap<Integer, BigDecimal>();
		// 距离开始时间间隔
		Map<Integer, Long> duarStartTimeMap = new HashMap<Integer, Long>();
		// 距离结束时间间隔
		Map<Integer, Long> duarEndTimeMap = new HashMap<Integer, Long>();
		Long currTime = System.currentTimeMillis();
		context.put("resultLoanList", resultLoanList.getData());
		context.put("pages", resultLoanList);

		// 存储标的类型
		Map<Integer, Object> periodTypeMap = new HashMap<Integer, Object>();

		for (Loan tempLoan : resultLoanList.getData()) {
			BigDecimal percent = Arith.calcPercent(
					tempLoan.getLoanCurrentValidInvest(),
					tempLoan.getLoanMoney(), tempLoan.getLoanState());
			resultLoanListMap.put(tempLoan.getLoanId(), percent);
			duarStartTimeMap.put(tempLoan.getLoanId(), tempLoan
					.getLoanInvestStarttime().getTime() - currTime);
			duarEndTimeMap.put(tempLoan.getLoanId(), currTime
					- tempLoan.getLoanInvestEndtime().getTime());
			context.put("resultLoanListPercent", resultLoanListMap);
			context.put("duarStartTimeMap", duarStartTimeMap);
			context.put("duarEndTimeMap", duarEndTimeMap);

			// 标的种类
			int loanPeriod = tempLoan.getLoanPeriod();
			int periodUnit = tempLoan.getLoanPeriodUnit().getUnit();
			String periodType = "";
			if (periodUnit == 1) {
				// 年
				periodType = "wenwenying";
			} else if (periodUnit == 2) {
				// 月
				if (loanPeriod < 3 && loanPeriod >= 1) {
					periodType = "yueyingbao";
				} else if (loanPeriod < 6 && loanPeriod >= 3) {
					periodType = "jizunbao";
				} else if (loanPeriod >= 6) {
					periodType = "wenwenying";
				}
			} else if (periodUnit == 3) {
				// 日
				if (loanPeriod <= 27 && loanPeriod >= 1) {
					periodType = "xinshouzunxiang";
				} else if (loanPeriod > 27) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.DAY_OF_MONTH, loanPeriod);

					Calendar calendar2 = Calendar.getInstance();
					calendar2.set(Calendar.DAY_OF_MONTH, 1);
					int month = (calendar.get(Calendar.YEAR) - calendar2
							.get(Calendar.YEAR))
							* 12
							+ calendar.get(Calendar.MONTH)
							- calendar2.get(Calendar.MONTH);

					if (month < 3) {
						periodType = "yueyingbao";
					} else if (month < 6 && month >= 3) {
						periodType = "jizunbao";
					} else if (month >= 6) {
						periodType = "wenwenying";
					}
				}
			}
			periodTypeMap.put(tempLoan.getLoanId(), periodType);
		}

		context.put("periodTypeMap", periodTypeMap);
	}
}
