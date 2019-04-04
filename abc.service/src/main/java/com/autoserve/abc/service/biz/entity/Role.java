package com.autoserve.abc.service.biz.entity;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-17,15:00
 */
public class Role {
    private int roleId;

    public Role() {
    }

    public Role(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
