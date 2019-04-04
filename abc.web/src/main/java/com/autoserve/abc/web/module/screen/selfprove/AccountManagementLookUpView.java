package com.autoserve.abc.web.module.screen.selfprove;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.user.UserService;

import javax.annotation.Resource;

/**
 * @author RJQ 2014/12/17 22:00.
 */
public class AccountManagementLookUpView {
    @Resource
    private UserService userService;

    public void execute(Context context, @Param("cinId") Integer cinId, @Param("MenuName") String menuName) {
        context.put("cinId", cinId);
        context.put("title", menuName);
    }
}