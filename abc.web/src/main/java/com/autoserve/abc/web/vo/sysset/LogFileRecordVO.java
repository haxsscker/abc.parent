package com.autoserve.abc.web.vo.sysset;

/**
 * @author RJQ 2015/1/11 15:50.
 */
public class LogFileRecordVO {
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
    private String status;

    /**
     * 任务尝试次数
     */
    private Integer tryTimes;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后更新时间
     */
    private String updateTime;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(Integer tryTimes) {
        this.tryTimes = tryTimes;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
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
