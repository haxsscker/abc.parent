package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 类WeChatMenuDO.java的实现描述：微信菜单,对应表为:abc_wechat_menu
 * 
 * @author WangMing 2015年6月25日 上午10:23:55
 */
public class WeChatMenuDO {

    /**
     * 菜单ID：abc_wechat_menu.menu_id
     */
    private Integer menuId;
    /**
     * 菜单名称：abc_wechat_menu.menu_name
     */
    private String  menuName;
    /**
     * 菜单url:abc_wechat_menu.menu_url
     */
    private String  menuUrl;
    /**
     * 父级菜单编号:abc_wechat_menu.menu_parent
     */
    private Integer menuParent;
    /**
     * 位置排序:abc_wechat_menu.menu_sort
     */
    private Integer menuSort;
    /**
     * 是启用 0 不启用 1启用 :abc_wechat_menu.menu_visible
     */
    private Integer menuVisible;
    /**
     * 菜单创建时间:abc_wechat_menu.menu_createtime
     */
    private Date    menuCreatetime;
    /**
     * 菜单修改时间:abc_wechat_menu.menu_modiftime
     */
    private Date    menuModiftime;
    /**
     * 按钮类型:abc_wechat_menu.menu_type
     */
    private String  menuType;
    /**
     * 按钮关键字:abc_wechat_menu.menu_key
     */
    private String  menuKey;
    /**
     * 按钮图片：abc_wechat_menu.menu_image
     */
    private String  menuImage;

    /**
     * 是否为网页授权访问 abc_wechat_menu.menu_safe 0为非授权网页,1为授权网页
     */
    private Integer menuSafe;

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

    public void setMenuKey(String menyKey) {
        this.menuKey = menyKey;
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

    public String getMenuDes() {
        return menuDes;
    }

    public void setMenuDes(String menuDes) {
        this.menuDes = menuDes;
    }

    /**
     * 备注:abc_wechat_menu.menu_des
     */
    private String menuDes;

}
