package com.autoserve.abc.web.module.control.loan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.Loan;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.entity.UserContact;
import com.autoserve.abc.service.biz.intf.loan.LoanQueryService;
import com.autoserve.abc.service.biz.intf.user.UserCompanyService;
import com.autoserve.abc.service.biz.intf.user.UserContactService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author yuqing.zheng Created on 2014-12-25,15:36
 */
public class LoanUserAddView {
    private static final Logger logger = LoggerFactory.getLogger(LoanUserAddView.class);

    @Autowired
    private LoanQueryService    loanQueryService;

    @Autowired
    private UserService         userService;

    @Autowired
    private UserCompanyService  userCompanyService;

    @Autowired
    private UserContactService  userContactService;

    public void execute(Context context) {
        Integer loanId = Integer.valueOf(context.get("loanId").toString());
        if (loanId == null || loanId <= 0) {
            return;
        }

        // 普通标信息
        PlainResult<Loan> loanRes = loanQueryService.queryById(loanId);
        if (!loanRes.isSuccess()) {
            logger.warn("普通标信息查询失败", loanRes.getMessage());
            return;
        }

        int userId = loanRes.getData().getLoanUserId();

        // 用户信息
        PlainResult<User> userResult = userService.findEntityById(userId);
        if (!userResult.isSuccess()) {
            logger.warn("用户信息查询失败", userResult.getMessage());
            return;
        }

        context.put("user", userResult.getData());

        // 用户联系信息
        PlainResult<UserContact> contactRes = userContactService.findUserContactByUserId(userId);
        if (contactRes.isSuccess()) {
            context.put("contact", contactRes.getData());
        }

        // 用户单位信息
        PlainResult<UserCompany> companyRes = userCompanyService.queryUserCompanyByUserId(userId);
        if (companyRes.isSuccess()) {
            context.put("company", companyRes.getData());
        }
    }
}
