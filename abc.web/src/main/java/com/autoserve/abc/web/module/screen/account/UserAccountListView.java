/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.account;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.web.util.GetButtonUtils;

/**
 * 用户账户信息
 * 
 * @author J.YL 2014年12月22日 下午8:50:50
 */
public class UserAccountListView {
    @Resource
    private AuthorityService authorityService;

    public void execute(Context context, ParameterParser params) {
        String duty = params.getString("duty");
        GetButtonUtils.getButtons(authorityService, context, params);
        context.put("duty", duty);
    }
}
