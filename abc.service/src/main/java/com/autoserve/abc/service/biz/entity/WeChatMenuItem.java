package com.autoserve.abc.service.biz.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.autoserve.abc.service.util.WeChatMenuItemComparator;

public class WeChatMenuItem {

    private static Integer ROOT_ID       = 0;
    private static String  DEFAULT_STATE = "open";
    private final String   state;

    private Integer        menuId;
    private String         menuName;
    private String         menuUrl;
    private Integer        isVisible;
    private Integer        parentId;
    private Integer        menuSort;
    private String         menuType;
    private String         menuKey;
    private String         menuImage;
    private Integer        menuSafe;

    public Integer getMenuSafe() {
        return menuSafe;
    }

    public void setMenuSafe(Integer menuSafe) {
        this.menuSafe = menuSafe;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    List<WeChatMenuItem> children = new ArrayList<WeChatMenuItem>();

    public WeChatMenuItem() {
        this.state = DEFAULT_STATE;
    }

    public static WeChatMenuItem buildCenterTree(List<WeChatMenuItem> list) {
        WeChatMenuItem root = new WeChatMenuItem();
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
    public static WeChatMenuItem recursiveBuild(WeChatMenuItem root, List<WeChatMenuItem> list) {
        List<WeChatMenuItem> children = new ArrayList<WeChatMenuItem>();
        for (int i = 0; i < list.size(); i++) {
            WeChatMenuItem item = list.get(i);
            if (item.getParentId().equals(root.getMenuId())) {
                children.add(item);
            }
        }
        Collections.sort(children, new WeChatMenuItemComparator());

        root.setChildren(children);
        for (int i = 0; i < children.size(); i++) {
            recursiveBuild(children.get(i), list);
        }
        return root;
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

    public List<WeChatMenuItem> getChildren() {
        return children;
    }

    public void setChildren(List<WeChatMenuItem> children) {
        this.children = children;
    }

    public String getState() {
        return state;
    }
}
