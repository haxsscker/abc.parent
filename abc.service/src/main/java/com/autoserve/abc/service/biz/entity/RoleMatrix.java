package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.dao.dataobject.MenuDO;

import java.util.List;

/**
 * 类RoleMatrix.java的实现描述：角色矩阵表 一个菜单对应多个角色 selects的index 和roleList的index 一一对应
 * selects 为1 代表 选择相应的角色 为0表示没有相应角色
 * 
 * @author pp 2014年11月19日 上午9:53:49
 */
public class RoleMatrix {

    private List<RoleWithRight> roleWithRight;
    private MenuDO              menuDO;

    public List<RoleWithRight> getRoleWithRight() {
        return roleWithRight;
    }

    public void setRoleWithRight(List<RoleWithRight> roleWithRight) {
        this.roleWithRight = roleWithRight;
    }

    public MenuDO getMenuDO() {
        return menuDO;
    }

    public void setMenuDO(MenuDO menuDO) {
        this.menuDO = menuDO;
    }

}
