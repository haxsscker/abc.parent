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
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.dao.dataobject.GovAccountDO;
import com.autoserve.abc.service.biz.intf.cash.AccountInfoService;
import com.autoserve.abc.service.biz.intf.common.AreaService;

/**
 * 机构资金账户查看screen
 * 
 * @author J.YL 2014年12月2日 下午4:21:04
 */
public class OrgOpenAccountLookView {
    private final Logger       logger = LoggerFactory.getLogger(OrgOpenAccountLookView.class);

    @Resource
    private AreaService        areaService;

    @Resource
    private AccountInfoService accountService;

    public void execute(Context context, ParameterParser params) {

        Integer accountId = params.getInt("account_id");

        GovAccountDO govAccount = accountService.queryByAccountId(accountId);
        String areaString = areaService.queryByAreaCode(govAccount.getAccountBankArea());
        govAccount.setAccountBankArea(areaString);
        logger.debug(JSON.toJSONString(govAccount));
        context.put("govAccount", govAccount);
    }
}
