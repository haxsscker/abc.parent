package com.autoserve.abc.service.biz.intf.loan;

import com.autoserve.abc.service.biz.entity.LoanCust;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-12-15,11:18
 */
public interface LoanCustService {

    public PlainResult<LoanCust> queryByLoanId(int loanId);

}
