package com.autoserve.abc.web.module.screen.account.myAccount;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.AccountCategory;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class AutoTransferAddView {
    @Autowired
    private HttpSession        session;
    @Autowired
    private UserService        userservice;
    @Autowired
    private AccountInfoService accountInfoService;
    @Resource
    private DoubleDryService   doubleDryService;

    public void execute(Context context, ParameterParser params) {
        String Type = params.getString("Type");
        User user = (User) session.getAttribute("user");
        PlainResult<User> result = userservice.findEntityById(user.getUserId());
        session.setAttribute("user", result.getData());
        UserIdentity userIdentity = new UserIdentity();
        userIdentity.setUserId(user.getUserId());
        if (user.getUserType() == null || user.getUserType().getType() == 1) {
            user.setUserType(UserType.PERSONAL);
        } else {
            user.setUserType(UserType.ENTERPRISE);
        }
        userIdentity.setUserType(user.getUserType());
        PlainResult<Account> account = accountInfoService.queryByUserId(userIdentity);
        String accountMark = account.getData().getAccountMark();
        //网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
        Double[] accountBacance = this.doubleDryService.queryBalance(accountMark, "1");
        context.put("Type", Type);
        context.put("accountBacance", accountBacance);
        context.put("accountMark", accountMark);
        context.put("user", user);

    }
}
