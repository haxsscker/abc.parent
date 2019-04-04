package com.autoserve.abc.web.module.screen.loan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.UuidUtil;

/**
 * @author yuqing.zheng Created on 2014-12-24,16:22
 */
public class LoanAddView {
    private static final Logger    log = LoggerFactory.getLogger(LoanAddView.class);

    @Autowired
    private LoanIntentApplyService intentService;

    @Autowired
    private LoanQueryService       loanQueryService;

    public void execute(Context context, @Param("loanId") int loanId, @Param("category") int category,
                        @Param("btnToAdd") int btnToAdd) {
        context.put("loanId", loanId);
        context.put("fileUploadClassType", FileUploadClassType.LOAN.getType());
        context.put("fileUploadSecondaryClass", FileUploadSecondaryClass.IMAGE_DATA.getType());
        context.put("btnToAdd", btnToAdd);
        // loanId>0 表明这是对项目资料的补全
        if (loanId > 0) {
            PlainResult<Loan> loanResult = loanQueryService.queryById(loanId);
            if (loanResult.isSuccess()) {
                context.put("category", loanResult.getData().getLoanCategory());
                context.put("dataId", loanResult.getData().getLoanFileUrl());
            }
        }
        // id<=0 表明这是对项目资料的新增
        else {
            context.put("category", LoanCategory.valueOf(category));
            context.put("dataId", UuidUtil.generateUuid());
        }

    }
}
