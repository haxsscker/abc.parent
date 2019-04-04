package com.autoserve.abc.web.module.screen.selfprove.json;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类AccountManagementLockStopData.java的实现描述：TODO 类实现描述
 * 
 * @author lipeng 2014年12月18日 下午3:18:17
 */
public class AccountManagementLockStopData {

    @Resource
    private UserService userService;

    /**
     * 个人客户的停用
     * 
     * @param userId
     * @return
     */
    public JsonBaseVO execute(@Param("cinId") Integer userId) {
        JsonBaseVO vo = new JsonBaseVO();
        BaseResult baseResult = userService.disableUser(userId);
        ResultMapper.toBaseVO(baseResult);
        return vo;
    }
}
