/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.account;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.service.biz.entity.UserAccountMoney;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.cash.UserAccountService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;
import com.autoserve.abc.web.vo.money.UserMoneyVO;

/**
 * 类settMoneyLookUpView.java的实现描述：用户账户信息查看
 * 
 * @author J.YL 2014年12月2日 下午4:21:58
 */
public class SettMoneyLookUpView {
    @Resource
    private UserAccountService userAccountService;
    @Resource
    private AccountInfoService accountService;

    public void execute(Context context) {
        LoginUserInfo user = LoginUserInfoHelper.getLoginUserInfo();
        if (user == null) {
            //TODO 
        }
        UserIdentity ui = new UserIdentity();
        ui.setUserId(user.getEmpId());
        ui.setUserType(UserType.PARTNER);
        AccountInfoDO aif = accountService.queryByUserIdentity(ui).getData();
        String accountNo = aif.getAccountNo();
        UserMoneyVO umv = new UserMoneyVO();
        PlainResult<UserAccountMoney> moneyRes = userAccountService.queryUserAccountByAccountNo(accountNo);
        if (!moneyRes.isSuccess()) {
            context.put("userMoney", umv);
            return;
        }
        UserAccountMoney money = moneyRes.getData();

        umv.setUserName(aif.getAccountUserName());
        umv.setUserAccountNo(aif.getAccountNo());
        umv.setFrozenMoney(money.getFrozeenMoney());
        umv.setTotalMoney(money.getTotalMoney());
        umv.setUseableMoney(money.getUseableMoney());
        context.put("userMoney", umv);
    }
}
