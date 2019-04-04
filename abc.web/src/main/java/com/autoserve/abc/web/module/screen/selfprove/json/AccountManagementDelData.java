package com.autoserve.abc.web.module.screen.selfprove.json;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.vo.JsonBaseVO;

public class AccountManagementDelData {
    @Resource
    private UserService userService;

    public JsonBaseVO execute(@Param("cinId") Integer cinId) {
        BaseResult baseResult = userService.removeUser(cinId);

        JsonBaseVO vo = new JsonBaseVO();
        vo.setSuccess(baseResult.isSuccess());
        vo.setMessage(baseResult.getMessage());

        return vo;
    }

}
