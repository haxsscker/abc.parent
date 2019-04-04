package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 *  LogFileRecordDO
 *  abc_log_file_record
 */
public class LogFileRecordDO {
    /**
     * 主键id
     * abc_log_file_record.record_id
     */
    private Integer recordId;

    /**
     * 上传文件名
     * abc_log_file_record.record_file_name
     */
    private String recordFileName;

    /**
     * 日志文件的日期
     * abc_log_file_record.record_file_date
     */
    private String recordFileDate;

    /**
     * 任务执行状态 0失败（不可重试） 1成功 -1上次失败（仍可重试）
     * abc_log_file_record.record_status
     */
    private Integer recordStatus;

    /**
     * 任务尝试次数
     * abc_log_file_record.record_try_times
     */
    private Integer recordTryTimes;

    /**
     * 创建时间
     * abc_log_file_record.record_create_time
     */
    private Date recordCreateTime;

    /**
     * 最后更新时间
     * abc_log_file_record.record_update_time
     */
    private Date recordUpdateTime;

    /**
     * 日志文件所属服务器的mac地址
     * abc_log_file_record.record_server_mac
     */
    private String recordServerMac;

    /**
     * 日志上传至oss后返回的oss的url
     * abc_log_file_record.record_oss_url
     */
    private String recordOssUrl;

    /**
     * 引用id（暂时无用，未来可通过此字段与http请求记录关联）
     * abc_log_file_record.record_refer_id
     */
    private String recordReferId;

    /**
     * 日志文件大小
     */
    private String recordFileSize;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getRecordFileName() {
        return recordFileName;
    }

    public void setRecordFileName(String recordFileName) {
        this.recordFileName = recordFileName;
    }

    public String getRecordFileDate() {
        return recordFileDate;
    }

    public void setRecordFileDate(String recordFileDate) {
        this.recordFileDate = recordFileDate;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getRecordTryTimes() {
        return recordTryTimes;
    }

    public void setRecordTryTimes(Integer recordTryTimes) {
        this.recordTryTimes = recordTryTimes;
    }

    public Date getRecordCreateTime() {
        return recordCreateTime;
    }

    public void setRecordCreateTime(Date recordCreateTime) {
        this.recordCreateTime = recordCreateTime;
    }

    public Date getRecordUpdateTime() {
        return recordUpdateTime;
    }

    public void setRecordUpdateTime(Date recordUpdateTime) {
        this.recordUpdateTime = recordUpdateTime;
    }

    public String getRecordReferId() {
        return recordReferId;
    }

    public void setRecordReferId(String recordReferId) {
        this.recordReferId = recordReferId;
    }

    public String getRecordServerMac() {
        return recordServerMac;
    }

    public void setRecordServerMac(String recordServerMac) {
        this.recordServerMac = recordServerMac;
    }

    public String getRecordOssUrl() {
        return recordOssUrl;
    }

    public void setRecordOssUrl(String recordOssUrl) {
        this.recordOssUrl = recordOssUrl;
    }

    public String getRecordFileSize() {
        return recordFileSize;
    }

    public void setRecordFileSize(String recordFileSize) {
        this.recordFileSize = recordFileSize;
    }
}
