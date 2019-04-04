package com.autoserve.abc.web.vo.menu;

public class WeChatMenuVO {
    private static final long serialVersionUID = 1L;
    private Integer           menuId;
    private String            menuName;
    private String            menuUrl;
    private Integer           parentId;
    private Integer           isVisible;
    private Integer           menuSort;
    private String            menuType;
    private String            menuKey;
    private String            menKey;
    private String            menuImage;
    private Integer           menuSafe;

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

    public String getMenKey() {
        return menKey;
    }

    public void setMenKey(String menKey) {
        this.menKey = menKey;
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

    public Integer getIsVisible() {
        return isVisible;
    }

    public Integer getMenuSort() {
        return menuSort;
    }
}
