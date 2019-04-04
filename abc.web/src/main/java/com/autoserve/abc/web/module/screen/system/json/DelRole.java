package com.autoserve.abc.web.module.screen.system.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.service.biz.intf.authority.RoleService;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;

import javax.annotation.Resource;

/**
 * Created by pp on 2014/12/3.
 */
public class DelRole {
    @Resource
    private RoleService roleService;

    public JsonBaseVO execute(@Param("id") Integer roleId){
        return ResultMapper.toBaseVO(roleService.removeRole(roleId));
    }
}
