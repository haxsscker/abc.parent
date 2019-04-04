package com.autoserve.abc.service.biz.intf.employee;

import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.service.biz.enums.BaseRoleType;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

import java.util.List;
import java.util.Map;

/**
 * 类AllocatRight.java的实现描述：对用户赋予角色的相关类
 *
 * @author pp 2014年11月19日 下午3:05:03
 */
public interface EmployeeRoleService {
    /**
     * 查询所有已经存在的角色
     *
     * @return
     */
    public ListResult<RoleDO> queryAllRole();

    /**
     * 根据用户ID 查询  用户具有的角色列表
     *
     * @param empId
     * @return
     */
    public ListResult<RoleDO> queryRoleBySingleEmp(Integer empId);

    /**
     * 根据员工id查询角色   一个员工可能对应多个角色
     *
     * @param emps
     * @return
     */
    public PlainResult<Map<Integer, List<RoleDO>>> queryRoleByEmps(List<Integer> emps);

    /**
     * 为员工分配角色列表
     *
     * @param empId
     * @param roles
     * @return
     */
    public BaseResult allocatRoleForEmps(Integer empId, List<Integer> roles);

    /**
     * 为员工分配角色列表
     *
     * @param empId
     * @param roleType
     * @return
     */
    public BaseResult allocatRoleForEmp(Integer empId, BaseRoleType roleType);

    /**
     * 删除员工的角色
     *
     * @param empId 员工id
     * @return BaseResult
     */
    public BaseResult removeAllRoleForEmp(Integer empId);
}
