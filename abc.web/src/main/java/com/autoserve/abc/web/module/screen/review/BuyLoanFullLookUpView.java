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
import com.autoserve.abc.service.biz.intf.loan.BuyLoanService;
import com.autoserve.abc.service.biz.intf.user.UserService;

/**
 * 收购满标详情查看
 *
 * @author J.YL 2014年12月26日 下午8:14:14
 */
public class BuyLoanFullLookUpView {
    @Resource
    private BuyLoanService buyLoanService;

    @Resource
    private UserService    userService;

    public void execute(@Param("loanId") int loanId, @Param("buyLoanId") int buyLoanId, Context context) {
        BuyLoan buyLoan = buyLoanService.queryById(buyLoanId).getData();
        User cst_user = userService.findEntityById(buyLoan.getUserId()).getData();

        context.put("loanId", loanId);
        context.put("cst_user", cst_user);
        context.put("buyLoan", buyLoan);

    }
}
