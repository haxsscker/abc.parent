package com.autoserve.abc.web.module.screen.projectmanage;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.dao.intf.PaymentPlanDao;
import com.autoserve.abc.service.biz.intf.loan.repay.AheadRepayService;
/**
 * 提前还款审核页面
 * @author zhangkang
 *
 */
public class AheadRepayAuditView {
	@Autowired
	private AheadRepayService aheadRepayService;
	
	@Autowired
	private PaymentPlanDao paymentPlanDao;
	
	 public void execute(@Param("loanId") int loanId, @Param("loanFileUrl") String loanFileUrl, Context context) {
        context.put("loanId", loanId);
        List<PaymentPlanDO> paymentList = paymentPlanDao
				.findByLoanId(loanId);
		BigDecimal shouldCapital = aheadRepayService.calcShouldPayCapital(paymentList);
		BigDecimal shouldInterest = aheadRepayService.calcShouldPayInterest(paymentList);
		BigDecimal shouldServeFee = aheadRepayService.calcShouldPayServeFee(paymentList);
		context.put("shouldCapital", shouldCapital);
		context.put("shouldInterest", shouldInterest);
		context.put("shouldServeFee", shouldServeFee);
		context.put("shouldAll", shouldCapital.add(shouldInterest).add(shouldServeFee));
    }
}
