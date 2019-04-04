package com.autoserve.abc.web.module.screen.home;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;

public class Index {
    @Resource
    private AuthorityService authorityService;

    /**
     * 初始化左侧的菜单按钮
     * 
     * @param context
     */
    public void execute(Context context) {

    }
}
