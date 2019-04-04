package com.autoserve.abc.web.module.screen.review;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-17,17:56
 */
public class LoanFullLookUpView {
    private static final Logger logger = LoggerFactory.getLogger(LoanFullLookUpView.class);

    @Resource
    private LoanQueryService loanQueryService;

    public void execute(@Param("loanId") int loanId, Context context) {
        context.put("loanId", loanId);

        PlainResult<Loan> result = loanQueryService.queryById(loanId);
        if (!result.isSuccess()) {
            return;
        }
        context.put("fileUploadClassType", FileUploadClassType.LOAN.getType());
        context.put("fileUploadSecondaryClass", FileUploadSecondaryClass.IMAGE_DATA.getType());
        context.put("dataId", result.getData().getLoanFileUrl());
    }
}
