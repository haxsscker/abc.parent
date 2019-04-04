package com.autoserve.abc.web.module.screen.review.json;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.loan.manage.TransferLoanManageService;
import com.autoserve.abc.service.biz.result.BaseResult;

public class CancelTransferLoan {
	
	@Resource
	private TransferLoanManageService transferLoanManageService;
	
	public BaseResult execute(@Param("transferLoanId") int transferLoanId){
		BaseResult result = transferLoanManageService.cancelTransferLoan(transferLoanId, 0,
                "手动流标");
        return result;
	}
}
