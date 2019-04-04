package com.autoserve.abc.web.module.screen.selfprove;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.UserHouse;
import com.autoserve.abc.service.biz.intf.user.UserHouseService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.UserHouseVOConverter;
import com.autoserve.abc.web.vo.user.UserHouseVO;

import javax.annotation.Resource;

public class HousePropertyInfoView {
    @Resource
    private UserHouseService userHouseService;

    public void execute(Context context, @Param("cinId") Integer userId) {
        PlainResult<UserHouse> plainResult = userHouseService.findUserHouseByUserId(userId);

        if (plainResult.isSuccess()) {
            UserHouseVO userHouseVO = UserHouseVOConverter.convertToUserHouseVO(plainResult.getData());
            context.put("userHouse", userHouseVO);
        }
    }
}
