package com.autoserve.abc.service.biz.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.autoserve.abc.service.util.MenuItemComparator;

/**
 * 罗列所有菜单的实体 类MenuItem.java的实现描述：
 * 
 * @author pp 2014年11月29日 下午4:52:55
 */
public class MenuItem {

    private static Integer ROOT_ID       = 0;
    private static String  DEFAULT_STATE = "open";

    public MenuItem() {
        this.state = DEFAULT_STATE;
    }

    public static MenuItem buildCenterTree(List<MenuItem> list) {
        MenuItem root = new MenuItem();
        root.setMenuId(ROOT_ID);
        return recursiveBuild(root, list);
    }

    /**
     * 递归构建 center 显示的菜单列表
     * 
     * @param root
     * @param list
     * @return
     */
    public static MenuItem recursiveBuild(MenuItem root, List<MenuItem> list) {
        List<MenuItem> children = new ArrayList<MenuItem>();
        for (int i = 0; i < list.size(); i++) {
            MenuItem item = list.get(i);
            if (item.getParentId().equals(root.getMenuId())) {
                children.add(item);
            }
        }
        Collections.sort(children, new MenuItemComparator());

        root.setChildren(children);
        for (int i = 0; i < children.size(); i++) {
            recursiveBuild(children.get(i), list);
        }
        return root;
    }

    private Integer      menuId;
    private String       menuName;
    private String       iconCls;
    private String       bigIcon;
    private String       menuUrl;
    private Integer      isVisible;
    private Integer      parentId;
    private Integer      menuSort;
    private final String state;
    List<MenuItem>       children = new ArrayList<MenuItem>();

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

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getBigIcon() {
        return bigIcon;
    }

    public void setBigIcon(String bigIcon) {
        this.bigIcon = bigIcon;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    public List<MenuItem> getChildren() {
        return children;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }

    public String getState() {
        return state;
    }

}
