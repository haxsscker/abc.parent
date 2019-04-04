package com.autoserve.abc.web.module.screen.extr;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.web.util.GetButtonUtils;

/**
 * 类GuarGovExtrListView.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年12月24日 下午3:18:39
 */
public class GuarGovExtrListView {
    @Resource
    private AuthorityService authorityService;

    public void execute(Context context, ParameterParser params) {
        GetButtonUtils.getButtons(authorityService, context, params);
    }
}
