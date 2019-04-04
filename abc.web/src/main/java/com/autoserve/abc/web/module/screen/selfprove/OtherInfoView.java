package com.autoserve.abc.web.module.screen.selfprove;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.UserOtherInfo;
import com.autoserve.abc.service.biz.intf.user.UserOtherInfoService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.UserOtherInfoVOConverter;
import com.autoserve.abc.web.vo.user.UserOtherInfoVO;

import javax.annotation.Resource;

/**
 * 类OtherInfoView.java的实现描述：TODO 类实现描述
 * 
 * @author lipeng 2014年12月18日 上午9:59:15
 */
public class OtherInfoView {

    @Resource
    private UserOtherInfoService userOtherInfoService;

    public void execute(Context context, @Param("cinId") Integer userId) {
        if (userId != 0) {

            PlainResult<UserOtherInfo> plainResult = userOtherInfoService.findUserOtherInfoByUserId(userId);

            if (plainResult.isSuccess()) {
                UserOtherInfoVO userOtherInfoVO = UserOtherInfoVOConverter.convertToUserOtherInfoVO(plainResult
                        .getData());
                context.put("userOtherInfo", userOtherInfoVO);
            }
        }
    }
}
