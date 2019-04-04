package com.autoserve.abc.web.module.screen.product;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.web.util.GetButtonUtils;

/**
 * 产品信息列表Button
 * 
 * @author wei.huimin 2014年12月18日 下午6:20:42
 */
public class ProductListView {

    @Resource
    private AuthorityService authorityService;

    public void execute(Context context, ParameterParser params) {
        GetButtonUtils.getButtons(authorityService, context, params);
    }
}
