package com.autoserve.abc.web.module.screen.employee.json;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.service.biz.intf.employee.EmployeeRoleService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.vo.JsonPlainVO;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author RJQ 2014/12/4 19:59.
 */
public class GetEmployeeRole {
    @Resource
    private EmployeeRoleService roleService;

    public JsonPlainVO<String> execute(@Param("empId") int empId) {
        JsonPlainVO<String> vo = new JsonPlainVO<String>();
        //查询用户角色
        ListResult<RoleDO> roleResult = roleService.queryRoleBySingleEmp(empId);
        if (!roleResult.isSuccess()) {
            vo.setSuccess(roleResult.isSuccess());
            vo.setMessage(roleResult.getMessage());
            return vo;
        }

        String role = getRoleIdString(roleResult.getData());
        vo.setData(role);
        return vo;
    }

    private String getRoleIdString(List<RoleDO> roleList) {
        StringBuffer strRoleId = new StringBuffer();
        if (roleList != null && roleList.size() != 0) {
            for (RoleDO r : roleList) {
                strRoleId.append(r.getRoleId()).append(",");
            }
            strRoleId.delete(strRoleId.length() - 1, strRoleId.length());
        }
        return strRoleId.toString();
    }
}
