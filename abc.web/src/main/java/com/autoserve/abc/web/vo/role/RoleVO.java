package com.autoserve.abc.web.vo.role;

public class RoleVO {

    private Integer roleId;
    private String  roleName;
    private Integer roleDefault;
    private Integer roleSort;
    private String  roleNode;

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

    public Integer getRoleDefault() {
        return roleDefault;
    }

    public void setRoleDefault(Integer roleDefault) {
        this.roleDefault = roleDefault;
    }

    public Integer getRoleSort() {
        return roleSort;
    }

    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
    }

    public String getRoleNode() {
        return roleNode;
    }

    public void setRoleNode(String roleNode) {
        this.roleNode = roleNode;
    }

}
