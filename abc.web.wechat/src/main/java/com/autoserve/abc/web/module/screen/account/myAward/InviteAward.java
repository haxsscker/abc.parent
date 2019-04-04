package com.autoserve.abc.web.module.screen.account.myAward;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.enums.UserType;
import com.autoserve.abc.service.biz.intf.invite.InviteService;
import com.autoserve.abc.service.biz.intf.redenvelope.RedService;
import com.autoserve.abc.service.biz.intf.score.ScoreService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;

/**
 * 类InviteAward.java的实现描述：好友分享
 * 
 * @author WangMing 2015年5月16日 上午10:54:15
 */
public class InviteAward {

    @Autowired
    private HttpSession         session;
    @Autowired
    private DeployConfigService deployConfigService;
    @Resource
    private UserService         userService;
    @Resource
    private HttpServletRequest  request;
    @Resource
    private InviteService       inviteService;
    @Resource
    private ScoreService        scoreService;
    @Resource
    private RedService          redService;
    @Resource
    private HttpServletResponse response;

    public void execute(Context context, Navigator nav, ParameterParser params) {
        /*
         * User user = (User) session.getAttribute("user"); if (user == null) {
         * nav.redirectToLocation(deployConfigService.getLoginUrl(request));
         * return; }
         */

//        User user = new User();
//        user.setUserType(UserType.PERSONAL);
//        user.setUserId(163);
    	User user=(User) session.getAttribute("user");

        //邀请地址
        InviteUserType userType = InviteUserType.PERSONAL;
        switch (user.getUserType()) {
            case PERSONAL:
                userType = InviteUserType.PERSONAL;
                break;
            case PARTNER:
                userType = InviteUserType.PARTNER;
                break;
            default:
                break;
        }
        PlainResult<String> inviteString = inviteService.generateInviteUrl(user.getUserId(), userType);
        context.put("inviteString", inviteString.getData());
    }
}
