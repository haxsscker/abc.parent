package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 操作日志
 */
public class OperateLogJDO {

    private Integer olId;

    /**
     * 操作人id
     */
    private Integer olEmpId;

    private String empName;

    /**
     * 登录IP
     */
    private String olIp;

    /**
     * 操作时间
     */
    private Date olOperateTime;

    /**
     * 操作类型
     */
    private String olOperateType;

    /**
     * 操作模块
     * abc_operate_log.ol_module
     */
    private String olModule;

    /**
     * 操作内容
     */
    private String olContent;

    /**
     * 操作日志状态 -1：已删除 1：启用
     */
    private Integer olState;

    public Integer getOlState() {
        return olState;
    }

    public void setOlState(Integer olState) {
        this.olState = olState;
    }

    /**
     * @return abc_operate_log.ol_id
     */
    public Integer getOlId() {
        return olId;
    }

    /**
     * @param Integer olId (abc_operate_log.ol_id )
     */
    public void setOlId(Integer olId) {
        this.olId = olId;
    }

    /**
     * @return abc_operate_log.ol_emp_id
     */
    public Integer getOlEmpId() {
        return olEmpId;
    }

    /**
     * @param Integer olEmpId (abc_operate_log.ol_emp_id )
     */
    public void setOlEmpId(Integer olEmpId) {
        this.olEmpId = olEmpId;
    }

    /**
     * @return abc_operate_log.ol_IP
     */
    public String getOlIp() {
        return olIp;
    }

    /**
     * @param String olIp (abc_operate_log.ol_IP )
     */
    public void setOlIp(String olIp) {
        this.olIp = olIp == null ? null : olIp.trim();
    }

    /**
     * @return abc_operate_log.ol_operate_time
     */
    public Date getOlOperateTime() {
        return olOperateTime;
    }

    /**
     * @param Date olOperateTime (abc_operate_log.ol_operate_time )
     */
    public void setOlOperateTime(Date olOperateTime) {
        this.olOperateTime = olOperateTime;
    }

    /**
     * @return abc_operate_log.ol_operate_type
     */
    public String getOlOperateType() {
        return olOperateType;
    }

    /**
     * @param String olOperateType (abc_operate_log.ol_operate_type )
     */
    public void setOlOperateType(String olOperateType) {
        this.olOperateType = olOperateType == null ? null : olOperateType.trim();
    }

    /**
     * @return abc_operate_log.ol_module
     */
    public String getOlModule() {
        return olModule;
    }

    /**
     * @param String olModule (abc_operate_log.ol_module )
     */
    public void setOlModule(String olModule) {
        this.olModule = olModule == null ? null : olModule.trim();
    }

    /**
     * @return abc_operate_log.ol_content
     */
    public String getOlContent() {
        return olContent;
    }

    /**
     * @param String olContent (abc_operate_log.ol_content )
     */
    public void setOlContent(String olContent) {
        this.olContent = olContent == null ? null : olContent.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}