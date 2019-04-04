package com.autoserve.abc.web.module.screen.account.myAccount;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

public class WithdrawCash {
    private final static Logger logger = LoggerFactory.getLogger(WithdrawCash.class); //加入日志
    @Autowired
    private HttpSession         session;
    @Autowired
    private UserService         userservice;
    @Autowired
    private AccountInfoService  accountInfoService;
    @Resource
    private DoubleDryService    doubleDryService;
    @Resource
    private BankInfoService     bankinfoservice;

    public void execute(Context context, ParameterParser params) {

        logger.info("into WithdrawCash execute");

        User user = (User) session.getAttribute("user");

        String Type = params.getString("Type");

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
        String userPhone=user.getUserPhone();
		String accountNo = account.getData().getAccountNo(); 
		List<BankInfoDO> banklist = doubleDryService.queryCard(accountNo, userPhone);
		context.put("banklist", banklist);
		context.put("banksize", banklist.size());
		context.put("user", user);
        context.put("Type", Type);
    }

}
