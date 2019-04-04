package com.autoserve.abc.web.module.screen.loan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.LoanIntentApply;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;
import com.autoserve.abc.service.biz.intf.loan.LoanIntentApplyService;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-12-24,16:22
 */
public class LoanAddViewForIntent {
    private static final Logger    log = LoggerFactory.getLogger(LoanAddViewForIntent.class);

    @Autowired
    private LoanIntentApplyService intentService;

    @Autowired
    private LoanQueryService       loanQueryService;

    @Autowired
    private UserService            userService;

    public void execute(Context context, @Param("intentId") int intentId, @Param("category") int category) {
        context.put("intentId", intentId);
        context.put("fileUploadClassType", FileUploadClassType.LOAN.getType());
        context.put("fileUploadSecondaryClass", FileUploadSecondaryClass.IMAGE_DATA.getType());

        // 意向审核的资料补全
        PlainResult<LoanIntentApply> intentApplyResult = intentService.queryById(intentId);
        if (intentApplyResult.isSuccess()) {
            context.put("category", intentApplyResult.getData().getIntentCategory());
            context.put("dataId", intentApplyResult.getData().getFileUrl());
            context.put("loanId", intentApplyResult.getData().getLoanId());
            PlainResult<UserDO> userResult = this.userService.findById(intentApplyResult.getData().getUserId());
            context.put("loanType", userResult.getData().getUserType());
        }

    }
}
