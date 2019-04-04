/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 菜单记录DO
 * @author pp 2014-11-13 下午05:25:32
 */
/**
 * MenuLog abc_menu_log
 */
public class MenuLogDO {
    /**
     * 主键自增 abc_menu_log.ml_id
     */
    private Integer mlId;

    /**
     * abc_employee外键 abc_menu_log.emp_id
     */
    private Integer empId;

    /**
     * 操作类型 1:添加 2:修改 3:删除 abc_menu_log.operat_type
     */
    private Integer operatType;

    /**
     * 操作前的json值 abc_menu_log.operat_before
     */
    private String  operatBefore;

    /**
     * 操作后的json值 abc_menu_log.operat_after
     */
    private String  operatAfter;

    /**
     * 操作时间 abc_menu_log.operat_time
     */
    private Date    operatTime;

    public Integer getMlId() {
        return mlId;
    }

    public void setMlId(Integer mlId) {
        this.mlId = mlId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getOperatType() {
        return operatType;
    }

    public void setOperatType(Integer operatType) {
        this.operatType = operatType;
    }

    public String getOperatBefore() {
        return operatBefore;
    }

    public void setOperatBefore(String operatBefore) {
        this.operatBefore = operatBefore;
    }

    public String getOperatAfter() {
        return operatAfter;
    }

    public void setOperatAfter(String operatAfter) {
        this.operatAfter = operatAfter;
    }

    public Date getOperatTime() {
        return operatTime;
    }

    public void setOperatTime(Date operatTime) {
        this.operatTime = operatTime;
    }
}
