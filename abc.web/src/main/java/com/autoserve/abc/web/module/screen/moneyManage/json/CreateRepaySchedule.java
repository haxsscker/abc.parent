package com.autoserve.abc.web.module.screen.moneyManage.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.impl.loan.plan.PlanBuilderByDayRate;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.PaymentPlanVOConverter;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.plan.PaymentPlanVO;

/**
 * 生成还款计划
 * 
 * @author liuwei 2014年12月30日 下午4:35:08
 */
public class CreateRepaySchedule {

	@Resource
	private LoanQueryService loanQueryService;

	public JsonPageVO<PaymentPlanVO> execute(ParameterParser params,
			@Param("loanId") int loanId, @Param("servFee") double servFee ,@Param("periods") int periods) {

		JsonPageVO<PaymentPlanVO> result = new JsonPageVO<PaymentPlanVO>();

		int page = params.getInt("page", 1);
		int rows = params.getInt("rows", 10);

		PlainResult<Loan> loanResult = loanQueryService.queryById(loanId);
		if (!loanResult.isSuccess() || loanResult.getData() == null) {
			result.setSuccess(false);
			result.setMessage("普通标信息不存在");
			return result;
		}

		PlanBuilderByDayRate planBuilderByDayRate = PlanBuilderByDayRate
				.getInstance();
		List<PaymentPlan> totalList = new ArrayList<PaymentPlan>();
		if("1".equals(loanResult.getData().getHandFeeKind())){
			totalList = planBuilderByDayRate
					.buildPaymentPlanListIncludeHandFee(loanResult.getData(), 0, 0, periods);
		}else if("2".equals(loanResult.getData().getHandFeeKind())){
			totalList = planBuilderByDayRate
					.buildPaymentPlanListIncludeHandFee(loanResult.getData(), servFee, 0, periods);
		}
		
		if (totalList != null) {
			List<PaymentPlan> curPageList = totalList.subList(
					(page - 1) * rows, page * rows < totalList.size() ? page
							* rows : totalList.size());
			result.setRows(PaymentPlanVOConverter
					.toPaymentPlanVOList(curPageList));
			result.setTotal(totalList.size());

		} else {
			result.setMessage("还款总期数必须要能被借款总月数整除");
			result.setSuccess(false);

		}
		return result;

	}

}
