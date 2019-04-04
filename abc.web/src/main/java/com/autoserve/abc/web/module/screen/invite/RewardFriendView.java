package com.autoserve.abc.web.module.screen.invite;

import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.intf.invite.InviteService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author RJQ 2015/1/4 18:10.
 */
public class RewardFriendView {
    @Autowired
    private InviteService inviteService;

    public void execute(Context context) {
        PlainResult<String> result = inviteService.generateInviteUrl(LoginUserUtil.getEmpId(), InviteUserType.PARTNER);
        if (result.isSuccess()) {
            String inviteUrl = result.getData();
            context.put("inviteUrl", inviteUrl);
        }
    }
}
