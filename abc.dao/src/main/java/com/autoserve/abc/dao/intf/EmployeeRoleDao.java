package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.EmployeeRoleDO;

import java.util.List;

public interface EmployeeRoleDao extends BaseDao<EmployeeRoleDO, Integer> {

    public List<EmployeeRoleDO> findByEmpId(Integer empId);

    public int deleteByRoleId(Integer roleId);

    /**
     * 删除指定员工的所有角色
     *
     * @param empId 员工id
     * @return int
     */
    public int deleteByEmpId(Integer empId);
}
