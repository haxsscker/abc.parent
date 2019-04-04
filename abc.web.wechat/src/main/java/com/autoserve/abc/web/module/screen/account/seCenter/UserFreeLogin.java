package com.autoserve.abc.web.module.screen.account.seCenter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;

public class UserFreeLogin {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService        userService;

    @Autowired
    private HttpSession        session;

    public void execute(Context context, Navigator nav, ParameterParser params) {
        //接收传来的id
        String userWechatId = request.getParameter("userWechatId");
        int userIsBound = 1;
        if (userWechatId == null || "".equals(userWechatId)) {
            //跳转到登陆界面
            nav.forwardTo("/login/login").end();
        }
        //用传来的id查询绑定的user，如果没有结果，直接返回登陆页面
        UserDO userDO = new UserDO();
        userDO.setUserWeChatID(userWechatId);
        userDO.setUserState(1);
        userDO.setUserIsBound(userIsBound);
        PageResult<UserDO> result = this.userService.queryList(userDO, new PageCondition());
        if (result.getTotalCount() == 0) {
            //跳转到登陆页面
            System.out.println("跳转");
            nav.forwardTo("/login/login").end();
        } else {
            //获得用户名和自动登陆标记
            UserDO user = result.getData().get(0);
            context.put("user", user);
        }
    }
}
