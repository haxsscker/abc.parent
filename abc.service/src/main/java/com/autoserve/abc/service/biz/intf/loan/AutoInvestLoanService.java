package com.autoserve.abc.service.biz.intf.loan;

/**
 * 自动投标<br>
 * 在发布项目时，用定时器调用，在开标时间执行，自动投资发布的标
 * 
 * @author Administrator
 *
 */
public interface AutoInvestLoanService {
	/**
	 * 自动投标
	 * @param loanId 发布的项目id
	 */
	void run(Integer loanId);

}
