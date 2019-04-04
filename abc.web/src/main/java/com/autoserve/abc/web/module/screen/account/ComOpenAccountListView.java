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
 * 机构维护中账户维护
 * 
 * @author J.YL 2014年12月2日 下午4:16:24
 */
public class ComOpenAccountListView {

    @Resource
    private AuthorityService authorityService;

    public void execute(Context context, ParameterParser params) {
        GetButtonUtils.getButtons(authorityService, context, params);
    }
}
