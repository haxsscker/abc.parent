package com.autoserve.abc.service.biz.intf.loan.repay;

import java.math.BigDecimal;
import java.util.List;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.AheadRepay;
import com.autoserve.abc.dao.dataobject.PaymentPlanDO;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.PayState;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

/**
 * 提前还款service
 * 
 * @author zhangkang
 *
 */
public interface AheadRepayService {
	
	/**
	 * 提前还款计算应还本金
	 * @param loanId
	 * @return
	 */
	BigDecimal calcShouldPayCapital(List<PaymentPlanDO> list);
	
	/**
	 * 提前还款计算应还利息
	 * @param list
	 * @return
	 */
	BigDecimal calcShouldPayInterest(List<PaymentPlanDO> list);
	
	/**
	 * 提前还款申请
	 * @param aheadRepay 提供loanId,userId
	 * @return
	 */
	BaseResult apply(AheadRepay aheadRepay);
	/**
	 * 分页查询
	 * @param aheadRepay
	 * @param pageCondition
	 * @return
	 */
	PageResult<AheadRepay> findPage(AheadRepay aheadRepay,
			PageCondition pageCondition);
	
	/**
	 * 提前还款审核
	 * @param id
	 * @param auditUserId
	 * @param pass
	 * @return
	 */
	BaseResult audit(int id, int auditUserId, boolean pass, String auditOpinion);
	/**
	 * 检查是否可提前还款
	 * @param paymentList
	 * @return
	 */
	BaseResult check(List<PaymentPlanDO> paymentList, Loan loan);
	
	PaymentPlanDO getNextPayment(List<PaymentPlanDO> paymentList);

	BigDecimal calcShouldPayServeFee(List<PaymentPlanDO> paymentList);
}
