/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.module.screen.selfprove.json;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 类CompanyCustomersDelData.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月18日 下午6:35:04
 */
public class CompanyCustomersDelData {
    @Resource
    private UserService userService;

    /**
     * 个人客户的启用
     * 
     * @param userId
     * @return
     */
    public JsonBaseVO execute(@Param("cinId") Integer userId) {
        JsonBaseVO vo = new JsonBaseVO();
        BaseResult baseResult = userService.removeUser(userId);
        ResultMapper.toBaseVO(baseResult);
        return vo;
    }

}
