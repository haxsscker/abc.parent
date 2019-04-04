package com.autoserve.abc.web.module.screen.review;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.TransferLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.loan.TransferLoanService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yuqing.zheng
 *         Created on 2015-01-15,23:47
 */
public class LoanRedeemCheckView {
    private static final Logger logger = LoggerFactory.getLogger(LoanRedeemCheckView.class);

    @Autowired
    private TransferLoanService transferLoanService;

    @Autowired
    private UserService userService;

    public void execute(Context context, @Param("loanId") Integer loanId, @Param("transferId") Integer transferId) {
        if (loanId == null || loanId <= 0) {
            logger.warn("参数校验失败，loanId不能为空并且需大于0，当前loanId={}", loanId);
            return;
        }
        context.put("loanId", loanId);

        if (transferId == null || transferId <= 0) {
            logger.warn("参数校验失败，transferId不能为空并且需大于0，当前transferId={}", transferId);
            return;
        }
        context.put("transferId", transferId);

        PlainResult<TransferLoan> transRes = transferLoanService.queryById(transferId);
        if (!transRes.isSuccess() || transRes.getData() == null) {
            logger. error("未根据转让ID找到相关转让标信息，transferId={}", transferId);
            return;
        }

        TransferLoan transfer = transRes.getData();
        context.put("transfer", transfer);

        Integer userId = transfer.getUserId();
        PlainResult<User> userRes = userService.findEntityById(userId);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            logger.error("未根据转让对象中的转让人ID查询到相关的用户，transferId={}, userId={}", transferId, userId);
            return;
        }

        User user = userRes.getData();
        context.put("user", user);
    }
}
