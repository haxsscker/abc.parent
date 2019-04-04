package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.UploadStatus;

import java.util.Date;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-08,15:06
 */
public class LogFileRecord {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 上传文件名
     */
    private String fileName;

    /**
     * 日志文件的日期
     */
    private String fileDate;

    /**
     * 任务执行状态
     */
    private UploadStatus status;

    /**
     * 任务尝试次数
     */
    private Integer tryTimes;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    /**
     * 引用id
     */
    private String referId;

    /**
     * 日志文件所属服务器的mac地址
     */
    private String serverMacAddress;

    /**
     * 日志上传至oss后返回的oss的url
     */
    private String ossUrl;

    /**
     * 日志文件大小
     */
    private String fileSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDate() {
        return fileDate;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public UploadStatus getStatus() {
        return status;
    }

    public void setStatus(UploadStatus status) {
        this.status = status;
    }

    public Integer getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(Integer tryTimes) {
        this.tryTimes = tryTimes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getReferId() {
        return referId;
    }

    public void setReferId(String referId) {
        this.referId = referId;
    }

    public String getServerMacAddress() {
        return serverMacAddress;
    }

    public void setServerMacAddress(String serverMacAddress) {
        this.serverMacAddress = serverMacAddress;
    }

    public String getOssUrl() {
        return ossUrl;
    }

    public void setOssUrl(String ossUrl) {
        this.ossUrl = ossUrl;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}

