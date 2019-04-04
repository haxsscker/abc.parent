package com.autoserve.abc.web.vo.sysset;

/**
 * @author RJQ 2015/1/10 22:09.
 */
public class OperateLogVO {
    private Integer olo_Id;

    /**
     * 操作人id
     */
    private Integer olEmpId;

    private String emp_Name;

    /**
     * 登录IP
     */
    private String olo_IP;

    /**
     * 操作时间
     */
    private String olo_OperateTime;

    /**
     * 操作类型
     */
    private String olo_OperateType;

    /**
     * 操作模块
     */
    private String olo_Model;

    /**
     * 操作内容
     */
    private String olo_Content;

    public Integer getOlo_Id() {
        return olo_Id;
    }

    public void setOlo_Id(Integer olo_Id) {
        this.olo_Id = olo_Id;
    }

    public Integer getOlEmpId() {
        return olEmpId;
    }

    public void setOlEmpId(Integer olEmpId) {
        this.olEmpId = olEmpId;
    }

    public String getEmp_Name() {
        return emp_Name;
    }

    public void setEmp_Name(String emp_Name) {
        this.emp_Name = emp_Name;
    }

    public String getOlo_IP() {
        return olo_IP;
    }

    public void setOlo_IP(String olo_IP) {
        this.olo_IP = olo_IP;
    }

    public String getOlo_OperateTime() {
        return olo_OperateTime;
    }

    public void setOlo_OperateTime(String olo_OperateTime) {
        this.olo_OperateTime = olo_OperateTime;
    }

    public String getOlo_OperateType() {
        return olo_OperateType;
    }

    public void setOlo_OperateType(String olo_OperateType) {
        this.olo_OperateType = olo_OperateType;
    }

    public String getOlo_Model() {
        return olo_Model;
    }

    public void setOlo_Model(String olo_Model) {
        this.olo_Model = olo_Model;
    }

    public String getOlo_Content() {
        return olo_Content;
    }

    public void setOlo_Content(String olo_Content) {
        this.olo_Content = olo_Content;
    }
}
