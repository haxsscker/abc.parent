package com.autoserve.abc.web.module.screen.selfprove;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.UserCompany;
import com.autoserve.abc.service.biz.intf.user.UserCompanyService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.UserCompanyVOConverter;
import com.autoserve.abc.web.vo.user.UserCompanyVO;

public class CompanyInfoView {
    @Resource
    private UserCompanyService userCompanyService;
    @Resource
    private UserService        userService;

    public void execute(Context context, @Param("cinId") Integer userId) {
        PlainResult<UserCompany> plainResult = userCompanyService.queryUserCompanyByUserId(userId);
        PlainResult<UserDO> userResult = this.userService.findById(userId);
        if (plainResult.isSuccess()) {
            UserCompanyVO userCompanyVO = UserCompanyVOConverter.convertToUserCompanyVO(plainResult.getData());
            if (userResult.getData().getUserMonthIncome() != null)
                userCompanyVO.setUserMonthIncome(userResult.getData().getUserMonthIncome().toString());
            context.put("userCompany", userCompanyVO);
        }
    }
}
