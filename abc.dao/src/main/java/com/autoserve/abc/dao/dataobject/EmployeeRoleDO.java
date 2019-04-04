/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

/**
 * 员工角色DO
 * @author pp 2014-11-13 下午06:02:13
 */
/**
 * EmployeeRole abc_employee_role
 */
public class EmployeeRoleDO {
    /**
     * 主键自增 abc_employee_role.er_id
     */
    private Integer erId;
    /**
     * abc_role外键 abc_employee_role.role_id
     */
    private Integer roleId;
    /**
     * abc_employee外键 abc_employee_role.emp_id
     */
    private Integer empId;

    public Integer getErId() {
        return erId;
    }

    public void setErId(Integer erId) {
        this.erId = erId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
}
