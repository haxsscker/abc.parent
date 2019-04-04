package com.autoserve.abc.web.module.screen.bank;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.web.util.GetButtonUtils;

/**
 * 类MonBankInfoListView.java的实现描述：TODO 类实现描述
 * 
 * @author dengjingyu 2014年12月23日 下午2:59:19
 */
public class MonBankInfoListView {
    @Resource
    private AuthorityService authorityService;

    public void execute(Context context, ParameterParser params) {
        GetButtonUtils.getButtons(authorityService, context, params);
    }
}
