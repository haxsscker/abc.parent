package com.autoserve.abc.web.module.screen.system.json;

import com.autoserve.abc.service.biz.intf.employee.EmployeeRoleService;
import com.autoserve.abc.web.convert.RoleVOConverter;
import com.autoserve.abc.web.vo.JsonListVO;
import com.autoserve.abc.web.vo.role.RoleVO;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author RJQ 2014/12/4 21:48.
 */
public class GetAllRole {
    @Resource
    private EmployeeRoleService roleService;

    public JsonListVO<RoleVO> execute() {
        List<RoleVO> list = RoleVOConverter.convertList(roleService.queryAllRole().getData());
        JsonListVO<RoleVO> jvo = new JsonListVO<RoleVO>();
        jvo.setRows(list);
        return jvo;
    }
}
