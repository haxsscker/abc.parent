package com.autoserve.abc.web.module.screen.account.myAccount;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;

public class Recharge {
    private final static Logger logger = LoggerFactory.getLogger(Recharge.class); //加入日志

    @Autowired
    private HttpSession         session;
    @Autowired
    private UserService         userservice;
    @Autowired
    private AccountInfoService  accountInfoService;
    @Resource
    private DoubleDryService    doubleDryService;
    @Resource
    private HuifuPayService     huiFuPayServcice;
    @Resource
    private UserService           userService;

    public void execute(Context context, ParameterParser params) {

        logger.info("into Recharge execute");

        User user = (User) session.getAttribute("user");
	    //投资户与融资户账号一样
		AccountInfoDO account = accountInfoService.qureyAccountByUserIdAndUserType(user.getUserId(),user.getUserType().getType());
   		String accountNo = account.getAccountNo();
   		Double[] accountBacance = { 0.00, 0.00, 0.00, 0.00, 0.00, 0.00};
	    //网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
	    if (accountNo != null && !"".equals(accountNo)) {
            accountBacance = this.doubleDryService.queryBalanceDetail(accountNo);
        }		
	    context.put("accountBacance", accountBacance);

        PlainResult<UserDO> userDO = this.userService.findById(user.getUserId());
        context.put("user", userDO.getData());
    }
}
