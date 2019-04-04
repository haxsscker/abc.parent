package com.autoserve.abc.web.module.screen.system.json;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.service.biz.intf.authority.RoleService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.convert.RoleVOConverter;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.role.RoleVO;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class AddRole {

    @Resource
    private RoleService roleService;

    private static Logger logger= LoggerFactory.getLogger(AddRole.class);
    public JsonBaseVO execute(@Params RoleVO vo) {
        RoleDO rdo= RoleVOConverter.convertToDo(vo);
        BaseResult result=roleService.createRole(rdo);
        return ResultMapper.toBaseVO(result);
    }
}
