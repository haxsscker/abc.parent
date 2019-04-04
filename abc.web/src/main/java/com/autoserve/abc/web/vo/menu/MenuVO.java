package com.autoserve.abc.web.vo.menu;

import java.io.Serializable;

/**
 * 类MenuVO.java的实现描述：TODO 类实现描述
 * 
 * @author pp 2014年11月21日 下午3:47:45
 */
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer           menuId;
    private String            menuName;
    private String            menuUrl;
    private Integer           parentId;
    private String            iconCls;
    private String            bigIcon;
    private Integer           isVisible;
    private Integer           menuSort;

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public void setBigIcon(String bigIcon) {
        this.bigIcon = bigIcon;
    }

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getIconCls() {
        return iconCls;
    }

    public String getBigIcon() {
        return bigIcon;
    }

    public Integer getIsVisible() {
        return isVisible;
    }

    public Integer getMenuSort() {
        return menuSort;
    }
}
