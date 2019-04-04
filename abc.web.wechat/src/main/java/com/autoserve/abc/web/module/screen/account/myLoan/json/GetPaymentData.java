package com.autoserve.abc.web.module.screen.account.myLoan.json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.common.PageCondition.Order;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.PaymentPlan;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.loan.plan.PaymentPlanService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.module.screen.account.myLoan.PaymentPlanVO;

public class GetPaymentData
{
	@Resource
    private PaymentPlanService paymentPlanService;
	@Resource
	private LoanQueryService loanService;
	 public  List<PaymentPlanVO> execute(Context context, ParameterParser params)
	 {
		
		 Integer loanId = params.getInt("loanId");
		 PaymentPlan paymentPlan = new PaymentPlan();
 		 paymentPlan.setLoanId(loanId); 		
		 PageResult<PaymentPlan> paymentresult = paymentPlanService.queryPaymentPlanList(paymentPlan, new PageCondition(1,100,"loanPeriod",Order.ASC));
		 List<PaymentPlan> paymentList = paymentresult.getData();
		 List<PaymentPlanVO> paymentPlanVOList = new ArrayList<PaymentPlanVO>();
		 for (PaymentPlan paymentPlan2 : paymentList)
		{
			 PaymentPlanVO vo = new PaymentPlanVO();
			 vo.setId(paymentPlan2.getId());
			 vo.setPayTime(paymentPlan2.getPaytime());
			 vo.setLoanId(paymentPlan2.getLoanId());
			 vo.setLoanPeriod(paymentPlan2.getLoanPeriod());
			 if(paymentPlan2.getPayState()==PayState.CLEAR){
				 //应还
//				 vo.setPayCapital(paymentPlan2.getPayCapital());
//				 vo.setPayInterest(paymentPlan2.getPayInterest());
				 //实还
//				 vo.setPayCollectCapital(paymentPlan2.getPayCollectCapital());
//				 vo.setPayCollectInterest(paymentPlan2.getPayCollectInterest());
				 vo.setIsClear(1);
			 }
			 if(paymentPlan2.getPayState()==PayState.UNCLEAR){
				 //应还
//				 vo.setPayCapital(paymentPlan2.getPayCapital());
//				 vo.setPayInterest(paymentPlan2.getPayInterest());
				 //实还
//				 vo.setPayCollectCapital(paymentPlan2.getPayCollectCapital());
//				 vo.setPayCollectInterest(paymentPlan2.getPayCollectInterest());
				 vo.setIsClear(0);
			 }
			// 应还
			 vo.setPayCapital(paymentPlan2.getPayCapital().subtract(paymentPlan2.getPayCollectCapital()));
			 vo.setPayInterest(paymentPlan2.getPayInterest().subtract(paymentPlan2.getPayCollectInterest()));
			 vo.setPayFine(paymentPlan2.getPayFine().subtract(paymentPlan2.getPayCollectFine()));
			 vo.setPayServiceFee(paymentPlan2.getPayServiceFee().subtract(paymentPlan2.getCollectServiceFee()));
			 //实还
			 vo.setPayCollectCapital(paymentPlan2.getPayCollectCapital());
			 vo.setPayCollectInterest(paymentPlan2.getPayCollectInterest());
			 vo.setPayCollectFine(paymentPlan2.getPayCollectFine());
			 vo.setCollectServiceFee(paymentPlan2.getCollectServiceFee());
			 Integer loanid = paymentPlan2.getLoanId();
			 PlainResult<Loan> loanResult = loanService.queryById(loanid);
			 Loan loan = loanResult.getData();
			 vo.setLoanNo(loan.getLoanNo());
			 paymentPlanVOList.add(vo);
		}
		 
		 return paymentPlanVOList;
	 }
}
