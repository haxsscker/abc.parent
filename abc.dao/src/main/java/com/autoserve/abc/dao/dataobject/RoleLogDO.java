/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 角色变更记录DO
 * 
 * @author pp 2014-11-13 下午05:57:34
 */
public class RoleLogDO {
    /**
     * abc_authority_log.al_id
     */
    private Integer alId;
    /**
     * 操作人 abc_employee外键 abc_authority_log.emp_id
     */
    private Integer empId;
    /**
     * 被操作的menu abc_menu外键 abc_authority_log.menu_id
     */
    private Integer menuId;
    /**
     * 被操作的btn abc_btn外键 abc_authority_log.btn_id
     */
    private Integer btnId;
    /**
     * 被操作角色 abc_role外键 abc_authority_log.role_id
     */
    private Integer roleId;
    /**
     * abc_authority_log.operat_before
     */
    private String  operatBefore;
    /**
     * abc_authority_log.operat_after
     */
    private String  operatAfter;
    /**
     * abc_authority_log.operat_time
     */
    private Date    operatTime;

    public Integer getAlId() {
        return alId;
    }

    public void setAlId(Integer alId) {
        this.alId = alId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getBtnId() {
        return btnId;
    }

    public void setBtnId(Integer btnId) {
        this.btnId = btnId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getOperatBefore() {
        return operatBefore;
    }

    public void setOperatBefore(String operatBefore) {
        this.operatBefore = operatBefore;
    }

    public String getOperatAfter() {
        return operatAfter;
    }

    public void setOperatAfter(String operatAfter) {
        this.operatAfter = operatAfter;
    }

    public Date getOperatTime() {
        return operatTime;
    }

    public void setOperatTime(Date operatTime) {
        this.operatTime = operatTime;
    }
}
