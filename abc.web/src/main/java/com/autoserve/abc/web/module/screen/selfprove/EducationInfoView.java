package com.autoserve.abc.web.module.screen.selfprove;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.entity.UserEducation;
import com.autoserve.abc.service.biz.intf.user.UserEducationService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.UserEducationInfoVOConverter;
import com.autoserve.abc.web.vo.user.UserEducationInfoVO;

import javax.annotation.Resource;

/**
 * 类EducationInfoView.java的实现描述：TODO 类实现描述
 * 
 * @author lipeng 2014年12月18日 上午10:50:21
 */
public class EducationInfoView {

    @Resource
    private UserEducationService userEducationService;

    public void execute(Context context, @Param("cinId") Integer userId) {
        if (userId != 0) {

            PlainResult<UserEducation> plainResult = userEducationService.findUserEducationByUserId(userId);

            if (plainResult.isSuccess()) {
                UserEducationInfoVO userEducationInfoVO = UserEducationInfoVOConverter
                        .convertToUserEducationInfoVO(plainResult.getData());
                context.put("UserEducation", userEducationInfoVO);
            }
        }
    }
}
