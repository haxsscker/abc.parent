package com.autoserve.abc.dao.dataobject.search;

import java.util.Date;

/**
 * 操作日志查询DO
 */
public class OperateLogSearchDO {

    private Integer empId;

    /**
     * 操作开始时间
     */
    private Date olOperateStartTime;

    /**
     * 操作结束时间
     */
    private Date olOperateEndTime;

    /**
     * 操作类型
     */
    private String olOperateType;

    /**
     * 操作日志状态 -1：已删除 1：启用
     */
    private Integer olState;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Date getOlOperateStartTime() {
        return olOperateStartTime;
    }

    public void setOlOperateStartTime(Date olOperateStartTime) {
        this.olOperateStartTime = olOperateStartTime;
    }

    public Date getOlOperateEndTime() {
        return olOperateEndTime;
    }

    public void setOlOperateEndTime(Date olOperateEndTime) {
        this.olOperateEndTime = olOperateEndTime;
    }

    public String getOlOperateType() {
        return olOperateType;
    }

    public void setOlOperateType(String olOperateType) {
        this.olOperateType = olOperateType;
    }

    public Integer getOlState() {
        return olState;
    }

    public void setOlState(Integer olState) {
        this.olState = olState;
    }
}