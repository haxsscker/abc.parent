package com.autoserve.abc.web.module.screen.selfprove;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.autoserve.abc.service.biz.entity.UserContact;
import com.autoserve.abc.service.biz.intf.user.UserContactService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.UserContactVOConverter;
import com.autoserve.abc.web.vo.user.UserContactVO;

import javax.annotation.Resource;

public class ContactInformationInfoView {
    @Resource
    private UserContactService userContactService;

    public void execute(Context context, ParameterParser params) {
        int cinId = params.getInt("cinId");
        if (cinId != 0) {
            PlainResult<UserContact> plainResult = userContactService.findUserContactByUserId(cinId);
            if (plainResult.isSuccess()) {
                UserContactVO userContactVO = UserContactVOConverter.convertToUserContactVO(plainResult.getData());
                context.put("userContactVO", userContactVO);
            }
        }
    }

}
