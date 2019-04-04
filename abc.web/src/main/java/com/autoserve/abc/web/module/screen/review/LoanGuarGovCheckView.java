/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.review;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PlainResult;

import javax.annotation.Resource;

/**
 * 担保审核和信贷审核页面，审核按钮弹窗信息展示
 * 
 * @author J.YL 2015年1月7日 下午3:21:44
 */
public class LoanGuarGovCheckView {

    @Resource
    private LoanQueryService loanQueryService;

    public void execute(Context context, @Param("loanId") Integer loanId) {
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
