package com.autoserve.abc.web.module.screen.employee;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.autoserve.abc.dao.dataobject.EmployeeDO;
import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.service.biz.intf.common.AreaService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeRoleService;
import com.autoserve.abc.service.biz.intf.employee.EmployeeService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.convert.EmployeeConverter;
import com.autoserve.abc.web.vo.employee.EmployeeVO;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author RJQ 2014/12/4 19:59.
 */
public class EmployeeLookUpView {
    @Resource
    private EmployeeService employeeService;

    @Resource
    private EmployeeRoleService roleService;

    @Resource
    private AreaService areaService;

    public void execute(Context context, @Param("empId") int empId) {
        //查询用户信息
        PlainResult<EmployeeDO> empResult = employeeService.findById(empId);
        //查询用户角色
        ListResult<RoleDO> roleResult = roleService.queryRoleBySingleEmp(empId);
        if (!empResult.isSuccess() || !roleResult.isSuccess()) {
            return;
        }

        EmployeeVO employeeVO = EmployeeConverter.convert(empResult.getData());
        //返回roleName字符串
        String role = getRoleNameString(roleResult.getData());
        String areaStr = areaService.queryByAreaCode(employeeVO.getEmpArea());
        employeeVO.setEmpArea(areaStr);

        employeeVO.setEmpRole(role);
        context.put("emp", employeeVO);
    }

    /**
     * 获取角色名字符串
     *
     * @param roleList 角色list
     * @return String
     */
    private String getRoleNameString(List<RoleDO> roleList) {
        StringBuffer strRole = new StringBuffer();
        if (roleList != null && roleList.size() != 0) {
            for (RoleDO r : roleList) {
                strRole.append(r.getRoleName()).append(",");
            }
            strRole.delete(strRole.length() - 1, strRole.length());
        }
        return strRole.toString();
    }

}
