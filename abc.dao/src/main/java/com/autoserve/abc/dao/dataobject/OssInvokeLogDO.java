package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 *  OssInvokeLogDO
 *  abc_oss_invoke_log
 */
public class OssInvokeLogDO {
    /**
     * 记录id
     * abc_oss_invoke_log.log_id
     */
    private Integer logId;

    /**
     * HTTP请求方法
     * abc_oss_invoke_log.log_req_method
     */
    private String logReqMethod;

    /**
     * HTTP请求内容
     * abc_oss_invoke_log.log_req_content
     */
    private String logReqContent;

    /**
     * HTTP响应码
     * abc_oss_invoke_log.log_resp_code
     */
    private Integer logRespCode;

    /**
     * HTTP响应内容
     * abc_oss_invoke_log.log_resp_content
     */
    private String logRespContent;

    /**
     * 其他信息
     * abc_oss_invoke_log.log_ext_msg
     */
    private String logExtMsg;

    /**
     * HTTP调用状态 0失败 1成功
     * abc_oss_invoke_log.log_status
     */
    private Integer logStatus;

    /**
     * 调用记录日期时间
     * abc_oss_invoke_log.log_time
     */
    private Date logTime;

    /**
     * 引用id
     * abc_oss_invoke_log.log_refer_id
     */
    private String logReferId;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getLogReqMethod() {
        return logReqMethod;
    }

    public void setLogReqMethod(String logReqMethod) {
        this.logReqMethod = logReqMethod;
    }

    public String getLogReqContent() {
        return logReqContent;
    }

    public void setLogReqContent(String logReqContent) {
        this.logReqContent = logReqContent;
    }

    public Integer getLogRespCode() {
        return logRespCode;
    }

    public void setLogRespCode(Integer logRespCode) {
        this.logRespCode = logRespCode;
    }

    public String getLogRespContent() {
        return logRespContent;
    }

    public void setLogRespContent(String logRespContent) {
        this.logRespContent = logRespContent;
    }

    public String getLogExtMsg() {
        return logExtMsg;
    }

    public void setLogExtMsg(String logExtMsg) {
        this.logExtMsg = logExtMsg;
    }

    public Integer getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Integer logStatus) {
        this.logStatus = logStatus;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getLogReferId() {
        return logReferId;
    }

    public void setLogReferId(String logReferId) {
        this.logReferId = logReferId;
    }
}
