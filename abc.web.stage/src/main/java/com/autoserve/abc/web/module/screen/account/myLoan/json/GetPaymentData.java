package com.autoserve.abc.web.module.screen.account.myLoan.json;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.common.PageCondition.Order;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.web.module.screen.account.myLoan.PaymentPlanVO;

public class GetPaymentData {
	@Resource
	private PaymentPlanService paymentPlanService;
	@Resource
	private LoanQueryService loanService;
	@Resource
	private SysConfigService sysConfigService;

	public List<PaymentPlanVO> execute(Context context, ParameterParser params) {

		Integer loanId = params.getInt("loanId");
		PaymentPlan paymentPlan = new PaymentPlan();
		paymentPlan.setLoanId(loanId);
		PageResult<PaymentPlan> paymentresult = paymentPlanService.queryPaymentPlanList(paymentPlan,
				new PageCondition(1, 65535, "loanPeriod", Order.ASC));
		List<PaymentPlan> paymentList = paymentresult.getData();
		List<PaymentPlanVO> paymentPlanVOList = new ArrayList<PaymentPlanVO>();
		for (PaymentPlan paymentPlan2 : paymentList) {
			PaymentPlanVO vo = new PaymentPlanVO();
			vo.setId(paymentPlan2.getId());
			vo.setPayTime(paymentPlan2.getPaytime());
			vo.setLoanId(paymentPlan2.getLoanId());
			vo.setLoanPeriod(paymentPlan2.getLoanPeriod());
			vo.setInnerSeqNo(paymentPlan2.getInnerSeqNo());
			if (paymentPlan2.getPayState() == PayState.CLEAR) {
				vo.setIsClear(1);
				vo.setPayFine(paymentPlan2.getPayFine().subtract(paymentPlan2.getPayCollectFine()));
				/**
				 * @content:新增违约罚金
				 * @author:夏同同
				 * @date:2016年4月10日 上午10:01:23
				 */
				vo.setPayBreachFine(paymentPlan2.getPayBreachFine().subtract(paymentPlan2.getPayCollectBreachFine()));
				vo.setPayServiceFee(paymentPlan2.getPayServiceFee().subtract(paymentPlan2.getCollectServiceFee()));
			}
			if (paymentPlan2.getPayState() == PayState.UNCLEAR 
					|| paymentPlan2.getPayState() == PayState.PAYING) {
				vo.setIsClear(0);	
				/**
				 * @content:修改以下代码
				 * 		判断是否逾期，同时计算逾期罚金和违约罚金
				 * @author:夏同同
				 * @date:2016年4月10日 上午10:01:23
				 */
				// 判断是否逾期，同时计算罚金
				Map<String, BigDecimal> pulishMoneys = computePulishMoney(paymentPlan2);
				BigDecimal pulishMoney = pulishMoneys.get("pulishMoney");
				if(paymentPlan2.getPayState() == PayState.PAYING){
					vo.setPayFine(paymentPlan2.getPayFine().subtract(paymentPlan2.getPayCollectFine()));
				}else{
					vo.setPayFine(paymentPlan2.getPayFine().subtract(paymentPlan2.getPayCollectFine()).add(pulishMoney));

				}
				BigDecimal pulishBreachMoney = pulishMoneys.get("pulishBreachMoney");
				if(paymentPlan2.getPayState() == PayState.PAYING){
					vo.setPayBreachFine(paymentPlan2.getPayBreachFine().subtract(paymentPlan2.getPayCollectBreachFine()));
				}else{
					vo.setPayBreachFine(paymentPlan2.getPayBreachFine().subtract(paymentPlan2.getPayCollectBreachFine()).add(pulishBreachMoney));
				}
				vo.setPayServiceFee(paymentPlan2.getPayServiceFee().subtract(paymentPlan2.getCollectServiceFee()));
			}
			// add by 夏同同 20160506  前台借款人已结清时展示还款记录
			if (paymentPlan2.getPayState() == PayState.CANCELED) {
				vo.setIsClear(1);	
				vo.setPayFine(new BigDecimal(0));
				vo.setPayBreachFine(new BigDecimal(0));
				vo.setPayServiceFee(new BigDecimal(0));
			}
			vo.setPayState(paymentPlan2.getPayState().state);
			//add by 夏同同 20160510
			if(paymentPlan2.getPayType()!=null)
				vo.setPayType(paymentPlan2.getPayType().type);
			// 应还
			vo.setPayCapital(paymentPlan2.getPayCapital().subtract(paymentPlan2.getPayCollectCapital()));
			vo.setPayInterest(paymentPlan2.getPayInterest().subtract(paymentPlan2.getPayCollectInterest()));
			// 实还
			vo.setPayCollectCapital(paymentPlan2.getPayCollectCapital());
			vo.setPayCollectInterest(paymentPlan2.getPayCollectInterest());
			vo.setPayCollectFine(paymentPlan2.getPayCollectFine());
			/**
			 * @content:新增实还违约罚金
			 * @author:夏同同
			 * @date:2016年4月11日 上午09:36:23
			 */
			vo.setPayCollectBreachFine(paymentPlan2.getPayCollectBreachFine());
			vo.setCollectServiceFee(paymentPlan2.getCollectServiceFee());
			
			Integer loanid = paymentPlan2.getLoanId();
			PlainResult<Loan> loanResult = loanService.queryById(loanid);
			Loan loan = loanResult.getData();
			vo.setLoanNo(loan.getLoanNo());
			// 原先代还后借款人还需要线上还款，现在都是借款人线下还给担保机构，所以这段代码屏蔽
//			if (paymentPlan2.getReplaceState()) {
//				vo.setPayCollectCapital(BigDecimal.ZERO);
//				vo.setPayCollectInterest(BigDecimal.ZERO);
//				vo.setPayCapital(paymentPlan2.getPayCapital());
//				vo.setPayInterest(paymentPlan2.getPayInterest());
//			}
			paymentPlanVOList.add(vo);
		}

		return paymentPlanVOList;
	}

//	/**
//	 * 查询罚息利率并计算罚息
//   * 注释该方法，夏同同
//	 */
//	private BigDecimal computePulishMoney(PaymentPlan repayPlan) {
//		BigDecimal pulishMoney;
//
//		// 判断是否逾期
//		DateTime nowTime = DateTime.now();
//		DateTime planPayTime = new DateTime(repayPlan.getPaytime());
//
//		// 如果逾期则查询罚息利率并计算罚息
//		if (nowTime.isAfter(planPayTime)
//				&& !nowTime.toString("MM/dd/yyyy").equals(planPayTime.toString("MM/dd/yyyy"))) {
//			// 罚息利率
//			PlainResult<SysConfig> punishRateResult = sysConfigService
//					.querySysConfig(SysConfigEntry.PUNISH_INTEREST_RATE);
//			if (!punishRateResult.isSuccess()) {
//				throw new BusinessException("罚息利率查询失败");
//			}
//			// MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
//
//			// 天罚息利率
//			BigDecimal rate = new BigDecimal(punishRateResult.getData().getConfValue())
//					.divide(new BigDecimal(100 * 360), 10, BigDecimal.ROUND_HALF_UP);
//			double punishRate = rate.doubleValue();
//
//			// 天数
//			int expiryDays = Days.daysBetween(planPayTime, nowTime).getDays();
//			// 未还本金
//			PlainResult<BigDecimal> remainingPrincipalResult = paymentPlanService
//					.queryRemainPrincipalByLoanidAndPeriod(repayPlan.getLoanId(), repayPlan.getLoanPeriod());
//			BigDecimal remainingPrincipal = remainingPrincipalResult.getData();
//			/**
//			 * 罚息 = 剩余本金 * 罚息利率 * 逾期天数 + 剩余罚金<br>
//			 * 剩余本金 = 应还本金 - 实还本金<br>
//			 * 罚息利率=罚息月利率/100/30<br>
//			 * 逾期天数 = 当前日期 - （实还日期（如果借款人还过部分款） 或 应还日期（如果借款人没有还过款））<br>
//			 */
//
//			/**
//			 * 当前为 罚金 = 未还本金* 天罚息利率* 逾期天数<br>
//			 */
//			pulishMoney = remainingPrincipal.multiply(new BigDecimal(punishRate * expiryDays)).setScale(2,
//					BigDecimal.ROUND_HALF_UP);
//
//			// pulishMoney=pulishMoney.abs(mc);
//		} else {
//			pulishMoney = BigDecimal.ZERO;
//		}
//
//		return pulishMoney;
//	}


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
				throw new BusinessException("逾期罚息利率查询失败");
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
}
