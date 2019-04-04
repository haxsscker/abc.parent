/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.account;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.dao.dataobject.GovAccountDO;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;

/**
 * 修改机构资金账户信息screen
 * 
 * @author J.YL 2014年12月4日 下午6:59:28
 */
public class ModifyOrgAccountView {
    private final Logger       logger = LoggerFactory.getLogger(ModifyOrgAccountView.class);
    @Resource
    private AccountInfoService accountService;

    public void execute(Context context, ParameterParser params) {
        Integer accountId = params.getInt("account_id");
        GovAccountDO gov = accountService.queryByAccountId(accountId);
        context.put("govAccount", gov);
    }
}
