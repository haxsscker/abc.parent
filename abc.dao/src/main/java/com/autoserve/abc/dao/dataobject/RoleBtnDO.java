/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

/**
 * 角色按钮DO
 * @author pp 2014-11-13 下午05:33:12
 */
/**
 * RoleBtn abc_role_btn
 */
public class RoleBtnDO {

    /**
     * 主键自增 abc_role_btn.rbt_id
     */
    private Integer rbtId;

    /**
     * abc_role外键 abc_role_btn.role_id
     */
    private Integer roleId;

    /**
     * abc_btn外键 abc_role_btn.btn_id
     */
    private Integer btnId;

    /**
     * abc_menu外键 abc_role_btn.menu_id
     */
    private Integer menuId;

    public Integer getRbtId() {
        return rbtId;
    }

    public void setRbtId(Integer rbtId) {
        this.rbtId = rbtId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getBtnId() {
        return btnId;
    }

    public void setBtnId(Integer btnId) {
        this.btnId = btnId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
