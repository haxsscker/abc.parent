/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 角色DO
 * @author pp 2014-11-13 下午05:29:54
 */
/**
 * Role abc_role
 */
public class RoleDO {
    /**
     * 主键，自增 abc_role.role_id
     */
    private Integer roleId;

    /**
     * 角色名称 abc_role.role_name
     */
    private String  roleName;

    /**
     * 角色排序 abc_role.role_sort
     */
    private Integer roleSort;

    private Integer roleDefault;

    /**
     * 备注 abc_role.role_des
     */
    private String  roleDes;

    /**
     * 建立时间 abc_role.role_createtime
     */
    private Date    roleCreatetime;

    /**
     * 修改时间 abc_role.role_modifytime
     */
    private Date    roleModifytime;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
    }

    public String getRoleDes() {
        return roleDes;
    }

    public void setRoleDes(String roleDes) {
        this.roleDes = roleDes;
    }

    public Date getRoleCreatetime() {
        return roleCreatetime;
    }

    public void setRoleCreatetime(Date roleCreatetime) {
        this.roleCreatetime = roleCreatetime;
    }

    public Date getRoleModifytime() {
        return roleModifytime;
    }

    public void setRoleModifytime(Date roleModifytime) {
        this.roleModifytime = roleModifytime;
    }

    public Integer getRoleDefault() {
        return roleDefault;
    }

    public void setRoleDefault(Integer roleDefault) {
        this.roleDefault = roleDefault;
    }

}
