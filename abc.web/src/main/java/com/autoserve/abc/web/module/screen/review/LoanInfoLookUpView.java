package com.autoserve.abc.web.module.screen.review;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-12-17,18:27
 */
public class LoanInfoLookUpView {
    private static final Logger logger = LoggerFactory.getLogger(LoanFullLookUpView.class);
    @Resource
    private LoanQueryService    loanQueryService;

    public void execute(@Param("loanId") int loanId, Context context) {
        context.put("loanId", loanId);
        PlainResult<Loan> loanRes = loanQueryService.queryById(loanId);
        if (!loanRes.isSuccess()) {
            return;
        }
        Loan loan = loanRes.getData();
        if (loan == null) {
            return;
        }
        context.put("fileUploadClassType", FileUploadClassType.LOAN.getType());
        context.put("fileUploadSecondaryClass", FileUploadSecondaryClass.IMAGE_DATA.getType());
        context.put("dataId", loan.getLoanFileUrl());
    }

}
