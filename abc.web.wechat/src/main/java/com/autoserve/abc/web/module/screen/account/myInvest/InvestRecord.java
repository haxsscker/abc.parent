package com.autoserve.abc.web.module.screen.account.myInvest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.search.InvestSearchJDO;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestClean;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestReceived;
import com.autoserve.abc.dao.dataobject.stage.myinvest.MyInvestTender;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.intf.invest.InvestQueryService;
import com.autoserve.abc.service.biz.intf.loan.LoanService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.util.Arith;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.Pagebean;

/**
 * @author DS 2015年1月23日下午2:58:17
 */
public class InvestRecord {
	@Autowired
	private DeployConfigService deployConfigService;
	@Autowired
	private HttpSession session;
	@Autowired
	private InvestQueryService investQueryService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private PaymentPlanService paymentPlanService;
	@Resource
	private HttpServletRequest request;

	public void execute(Context context, ParameterParser params, Navigator nav) {

		User user = (User) session.getAttribute("user");
		if (user == null) {
			nav.redirectToLocation(deployConfigService.getLoginUrl(request));
			return;
		}

		context.put("user", user);
		String investType = params.getString("investType");
		if (investType == null || "".equals(investType)) {
			investType = "HKZ";
		}
		// 开始时间
		String startDate = params.getString("startDate");
		// 结束时间
		String endDate = params.getString("endDate");
		// 时间区间
		String Recent = params.getString("Recent");
		// 项目名称
		String loanno = params.getString("loanno");
		Integer currentPage = params.getInt("currentPage");
		Integer pageSize = 5;
		if (currentPage == 0)
			currentPage = 1;// 默认情况
		PageCondition pageCondition = new PageCondition(currentPage, pageSize);
		// 查询条件
		InvestSearchJDO investSearchJDO = new InvestSearchJDO();
		investSearchJDO.setUserId(user.getUserId());
		// investSearchJDO.setUserId(1);
		investSearchJDO.setLoanName(loanno);

		Calendar calendar = Calendar.getInstance();
		if (Recent != null && !"".equals(Recent)) {
			if ("0".equals(Recent)) {
				calendar.add(Calendar.WEEK_OF_YEAR, -1);
			} else {
				calendar.add(Calendar.MONTH, 0 - Integer.valueOf(Recent));
			}
			startDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar
					.getTime());
			endDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(new java.util.Date());
		}

		if (startDate != null) {
			try {
				Date start = new SimpleDateFormat("yyyy-MM-dd")
						.parse(startDate);
				investSearchJDO.setStartDate(start);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (endDate != null) {
			try {
				Date end = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
				investSearchJDO.setEndDate(end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// 回款中
		if (investType != null && "HKZ".equals(investType)) {
			// 未支付
			PageResult<MyInvestReceived> pageResult = investQueryService
					.queryMyInvestReceived(investSearchJDO, pageCondition);
			// 分页处理
			Pagebean<MyInvestReceived> pagebean = new Pagebean<MyInvestReceived>(
					currentPage, pageSize, pageResult.getData(),
					pageResult.getTotalCount());
			context.put("pagebean", pagebean);
		}
		// 投标中
		else if (investType != null && "TBZ".equals(investType)) {
			// 待收益
			PageResult<MyInvestTender> pageResult = investQueryService
					.queryMyInvestTender(investSearchJDO, pageCondition);
			// 分页处理
			Pagebean<MyInvestTender> pagebean = new Pagebean<MyInvestTender>(
					currentPage, pageSize, pageResult.getData(),
					pageResult.getTotalCount());
			context.put("pagebean", pagebean);
			Map<String, BigDecimal> resultLoanListMap = new HashMap<String, BigDecimal>();
			for (MyInvestTender temp : pagebean.getRecordList()) {
				// BigDecimal percent =
				// temp.getValidInvest().divide(temp.getLoanMoney(), 50,
				// BigDecimal.ROUND_HALF_UP)
				// .multiply(new BigDecimal(100));
				BigDecimal percent = Arith.calcPercent(temp.getValidInvest(),
						temp.getLoanMoney(),
						LoanState.valueOf(temp.getLoanState()));
				resultLoanListMap.put(temp.getLoanId(), percent);
				context.put("resultLoanListPercent", resultLoanListMap);
			}
		}
		// 已回款
		else if (investType != null && "YHK".equals(investType)) {
			PageResult<MyInvestClean> pageResult = investQueryService
					.queryMyInvestClean(investSearchJDO, pageCondition);
			// 分页处理
			Pagebean<MyInvestClean> pagebean = new Pagebean<MyInvestClean>(
					currentPage, pageSize, pageResult.getData(),
					pageResult.getTotalCount());
			context.put("pagebean", pagebean);
		}
		context.put("Recent", Recent);
		context.put("investType", investType);
		context.put("startDate", startDate);
		context.put("endDate", endDate);
		context.put("loanno", loanno);
	}
}
