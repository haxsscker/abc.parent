/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

/**
 * 菜单按钮button
 * @author pp 2014-11-13 下午05:16:01
 */
/**
 * MenuBtn abc_menu_btn
 */
public class MenuBtnDO {
    /**
     * 主键自增 abc_menu_btn.mbt_id
     */
    private Integer mbtId;

    /**
     * abc_menu外键 abc_menu_btn.menu_id
     */
    private Integer menuId;

    /**
     * abc_btn外键 abc_menu_btn.btn_id
     */
    private Integer btnId;

    public Integer getMbtId() {
        return mbtId;
    }

    public void setMbtId(Integer mbtId) {
        this.mbtId = mbtId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getBtnId() {
        return btnId;
    }

    public void setBtnId(Integer btnId) {
        this.btnId = btnId;
    }
}
