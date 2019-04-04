/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

/**
 * 菜单按钮操作记录DO
 * @author pp 2014-11-13 下午05:19:21
 */

import java.util.Date;

/**
 * MenuBtnLog abc_menu_btn_log
 */
public class MenuBtnLogDO {
    /**
     * 主键自增 abc_menu_btn_log.bl_id
     */
    private Integer blId;

    /**
     * 操作人 abc_employee外键 abc_menu_btn_log.emp_id
     */
    private Integer empId;

    /**
     * 操作的menu abc_menu外键 abc_menu_btn_log.menu_id
     */
    private Integer menuId;

    /**
     * 操作的btn abc_menu abc_menu_btn_log.btn_id
     */
    private Integer btnId;

    /**
     * 1:添加按钮 2:删除按钮 abc_menu_btn_log.operat_type
     */
    private Integer operatType;

    /**
     * 操作时间 abc_menu_btn_log.operat_time
     */
    private Date    operatTime;

    public Integer getBlId() {
        return blId;
    }

    public void setBlId(Integer blId) {
        this.blId = blId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
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

    public Integer getOperatType() {
        return operatType;
    }

    public void setOperatType(Integer operatType) {
        this.operatType = operatType;
    }

    public Date getOperatTime() {
        return operatTime;
    }

    public void setOperatTime(Date operatTime) {
        this.operatTime = operatTime;
    }
}
