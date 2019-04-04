package com.autoserve.abc.web.module.screen.account.seCenter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;

public class HeadPortrait {
    @Autowired
    private HttpServletRequest  request;

    @Autowired
    private UserService         userService;

    @Autowired
    private HttpSession         session;

    @Autowired
    private DeployConfigService deployConfigService;

    public void execute(Context context, Navigator nav) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            nav.redirectToLocation(deployConfigService.getLoginUrl(request));
            return;
        } else {
            PlainResult<UserDO> userDO = this.userService.findById(user.getUserId());
            context.put("user", userDO.getData());
        }
    }

}
