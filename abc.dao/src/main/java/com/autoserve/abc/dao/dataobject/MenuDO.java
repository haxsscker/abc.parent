/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * Menu abc_menu
 */
public class MenuDO {
    /**
     * 主键自增 abc_menu.menu_id
     */
    private Integer           menuId;

    /**
     * 菜单名称 abc_menu.menu_name
     */                       
    private String            menuName;

    /**
     * 菜单url abc_menu.menu_url
     */
    private String            menuUrl;

    /**
     * 父级菜单编号 abc_menu.menu_parent
     */
    private Integer           menuParent;

    /**
     * 小图标路径 abc_menu.menu_smallicon
     */
    private String            menuSmallicon;

    /**
     * 大图标路径 abc_menu.menu_bigicon
     */
    private String            menuBigicon;

    /**
     * 排序位置 abc_menu.menu_sort
     */
    private Integer           menuSort;

    /**
     * 是否可见 abc_menu.menu_visible
     */
    private Integer           menuVisible;

    /**
     * 备注 abc_menu.menu_des
     */
    private String            menuDes;

    /**
     * abc_menu.menu_createtime
     */
    private Date              menuCreatetime;

    /**
     * abc_menu.menu_modiftime
     */
    private Date              menuModiftime;

    public MenuDO() {
        this.menuSort=0;
        this.menuVisible=0;
    }

    public MenuDO(Integer mid, String mName, Integer mp) {
        this.menuId = mid;
        this.menuName = mName;
        this.menuParent = mp;
    }

    public MenuDO(Integer mid, String mName, Integer mp, Integer sort) {
        this.menuId = mid;
        this.menuName = mName;
        this.menuParent = mp;
        this.menuSort = sort;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getMenuParent() {
        return menuParent;
    }

    public void setMenuParent(Integer menuParent) {
        this.menuParent = menuParent;
    }

    public String getMenuSmallicon() {
        return menuSmallicon;
    }

    public void setMenuSmallicon(String menuSmallicon) {
        this.menuSmallicon = menuSmallicon;
    }

    public String getMenuBigicon() {
        return menuBigicon;
    }

    public void setMenuBigicon(String menuBigicon) {
        this.menuBigicon = menuBigicon;
    }

    public Integer getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    public Integer getMenuVisible() {
        return menuVisible;
    }

    public void setMenuVisible(Integer menuVisible) {
        this.menuVisible = menuVisible;
    }

    public String getMenuDes() {
        return menuDes;
    }

    public void setMenuDes(String menuDes) {
        this.menuDes = menuDes;
    }

    public Date getMenuCreatetime() {
        return menuCreatetime;
    }

    public void setMenuCreatetime(Date menuCreatetime) {
        this.menuCreatetime = menuCreatetime;
    }

    public Date getMenuModiftime() {
        return menuModiftime;
    }

    public void setMenuModiftime(Date menuModiftime) {
        this.menuModiftime = menuModiftime;
    }

}
