package com.autoserve.abc.web.module.screen.system.json;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.service.biz.intf.authority.RoleService;
import com.autoserve.abc.web.convert.RoleVOConverter;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.role.RoleVO;

import javax.annotation.Resource;

/**
 * Created by pp on 2014/12/3.
 */
public class EditRole {

    @Resource
    private RoleService roleService;

    public JsonBaseVO execute(@Params RoleVO vo){
           return ResultMapper.toBaseVO(roleService.modifyRole(RoleVOConverter.convertToDo(vo)));
    }
}
