package com.autoserve.abc.web.module.screen.selfprove;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.UserSpouse;
import com.autoserve.abc.service.biz.intf.user.UserSpouseService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.UserSpouseVOConverter;
import com.autoserve.abc.web.vo.user.UserSpouseVO;

import javax.annotation.Resource;

/**
 * 类spouseInfoView.java的实现描述：TODO 类实现描述
 * 
 * @author fangrui 2014年12月18日 上午10:23:08
 */
public class SpouseInfoView {
    @Resource
    private UserSpouseService userSpouseService;

    public void execute(Context context, ParameterParser params) {
        int cinId = params.getInt("cinId");
        if (cinId != 0) {
            PlainResult<UserSpouse> plainResult = userSpouseService.findUserSpouseByUserId(cinId);
            if (plainResult.isSuccess()) {
                UserSpouseVO userSpouseVO = UserSpouseVOConverter.convertToUserSpouseVO(plainResult.getData());
                context.put("userSpouseVO", userSpouseVO);
            }
        }

    }

}
