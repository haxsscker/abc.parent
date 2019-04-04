package com.autoserve.abc.web.module.screen.selfprove;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.UserOwner;
import com.autoserve.abc.service.biz.intf.user.UserOwnerService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.UserOwnerVOConverter;
import com.autoserve.abc.web.vo.user.UserOwnerVO;

/**
 * 私营业主资料
 * 
 * @author dongxuejiao 2014年12月18日 下午1:20:06
 */

public class PrivateOwnersInfoView {
    @Resource
    private UserOwnerService userOwnerService;

    public void execute(Context context, @Param("cinId") Integer userId) {
        if (userId != 0) {
            PlainResult<UserOwner> result = userOwnerService.findUserOwnerByUserId(userId);
            if (result.isSuccess()) {
                // User user = UserConverter.toUser(result.getData());
                UserOwnerVO vo = UserOwnerVOConverter.convertToUserOwnerVO(result.getData());
                context.put("userOwner", vo);
            }
        }
    }
}
