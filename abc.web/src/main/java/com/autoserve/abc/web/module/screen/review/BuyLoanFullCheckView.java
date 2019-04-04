/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.review;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.user.UserService;

/**
 * 收购审核和收购满标审核 审核界面
 *
 * @author J.YL 2015年1月7日 下午3:32:15
 */
public class BuyLoanFullCheckView {
    @Resource
    private BuyLoanService buyLoanService;

    @Resource
    private UserService    userService;

    public void execute(Context context, @Param("loanId") Integer loanId, @Param("buyLoanId") Integer buyLoanId) {
        context.put("loanId", loanId);
        context.put("buyLoanId", buyLoanId);
        context.put("bidId", buyLoanId);
        context.put("bidType", BidType.BUY_LOAN.getType());

        BuyLoan buyLoan = buyLoanService.queryById(buyLoanId).getData();
        User cst_user = userService.findEntityById(buyLoan.getUserId()).getData();

        context.put("buyLoan", buyLoan);
        context.put("cst_user", cst_user);
    }
}
