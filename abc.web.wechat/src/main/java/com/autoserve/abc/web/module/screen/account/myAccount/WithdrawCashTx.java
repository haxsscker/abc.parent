package com.autoserve.abc.web.module.screen.account.myAccount;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.BankInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.Account;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.BankInfoService;
import com.autoserve.abc.service.biz.intf.cash.DoubleDryService;
import com.autoserve.abc.service.biz.intf.cash.HuifuPayService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.SystemGetPropeties;

public class WithdrawCashTx {
    private final static Logger logger = LoggerFactory.getLogger(WithdrawCashTx.class); //加入日志
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
    @Resource
    private HuifuPayService     huiFuPayServcice;
    @Resource
    private UserService userService;

    public void execute(Context context, ParameterParser params) {

        logger.info("into WithdrawCashTx execute");
        User user = (User) session.getAttribute("user");

        String Type = params.getString("Type");

        PlainResult<User> result = userservice.findEntityById(user.getUserId());
        PlainResult<UserDO> userDoResult = userService.findById(user.getUserId());
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

        //调用汇付查询接口
        Double[] balances = {0.00, 0.00, 0.00};
        if(StringUtils.isNotEmpty(accountMark)){
        	balances = doubleDryService.queryBalance(accountMark, "1");
        }
        String accountBacance = Double.toString(balances[1]);

        //网贷平台子账户可用余额|总可用余额(子账户可用余额+公共账户可用余额)|子账户冻结余额”（例:100.00|200.00|10.00）
        /// Double[] accountBacance = this.doubleDryService.queryBalance(accountMark,"1");      

        //查询用户提现卡号（后期完善）
        ListResult<BankInfoDO> banklist = bankinfoservice.queryListBankInfo(user.getUserId());
        
        if(banklist.isSuccess() && banklist.getData() != null && banklist.getData().size()>=1){
   		 context.put("bindCard", "1");
   	 }
        //免费提现额度
        BigDecimal cashQuota = result.getData().getUserCashQuota();
        if (cashQuota == null) {
            cashQuota = new BigDecimal(0);
        }
        context.put("businessstate",userDoResult.getData().getUserBusinessState());
        context.put("email",userDoResult.getData().getUserEmail());
        context.put("Type", Type);
        context.put("banklist", banklist.getData());
        context.put("banksize", banklist.getData().size());
        context.put("accountBacance", accountBacance);
        context.put("cashQuota", cashQuota);
        PlainResult<UserDO> userDO = this.userService.findById(user.getUserId());
        context.put("user", userDO.getData());
        context.put("userrealname", userDoResult.getData().getUserRealName());
    }

}
