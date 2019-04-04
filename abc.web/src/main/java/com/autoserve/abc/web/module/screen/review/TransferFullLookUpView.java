package com.autoserve.abc.web.module.screen.review;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-17,16:29
 */
public class TransferFullLookUpView {
    private static final Logger logger = LoggerFactory.getLogger(TransferFullLookUpView.class);

    @Autowired
    private TransferLoanService transferService;

    @Autowired
    private UserService userService;

    public void execute(@Param("loanId") int loanId, @Param("transferId") int transferId,
                        Context context) {
        TransferLoan transfer = transferService.queryById(transferId).getData();
        // 转让人
        User cst_user = userService.findEntityById(transfer.getUserId()).getData();

        context.put("loanId", loanId);
        context.put("transfer", transfer);
        context.put("cst_user", cst_user);
    }
}
