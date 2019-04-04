/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.account;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.AccountInfoDO;
import com.autoserve.abc.service.biz.entity.UserIdentity;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.web.helper.LoginUserInfo;
import com.autoserve.abc.web.helper.LoginUserInfoHelper;

/**
 * 类openAccountView.java的实现描述：开户
 * 
 * @author J.YL 2014年12月2日 下午4:20:16
 */
public class OpenAccountView {
    @Resource
    private AccountInfoService accountService;

    public void execute(Context context, ParameterParser params) {
        LoginUserInfo user = LoginUserInfoHelper.getLoginUserInfo();
        if (user == null) {
            //TODO 
        }
        UserIdentity ui = new UserIdentity();
        ui.setUserId(user.getEmpId());
        ui.setUserType(UserType.PARTNER);
        AccountInfoDO account = accountService.queryByUserIdentity(ui).getData();
        if (account == null) {
            account = new AccountInfoDO();
        }
        context.put("account", account);
    }
}
