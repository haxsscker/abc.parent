package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.enums.FileUploadClassType;
import com.autoserve.abc.service.biz.enums.FileUploadSecondaryClass;

/**
 * 影像上传文件信息
 * 
 * @author RJQ 2014/12/25 14:32.
 */
public class FileUploadInfo {

    private Integer                  fuiId;

    /**
     * 模块类型
     */
    private FileUploadClassType      fuiClassType;

    /**
     * 二级模块类型
     */
    private FileUploadSecondaryClass fuiSecondaryClass;

    /**
     * 处理信息id
     */
    private String                   fuiDataId;

    /**
     * 文件名称
     */
    private String                   fuiFileName;

    /**
     * 文件路径
     */
    private String                   fuiFilePath;

    /**
     * 文件上传时间
     */
    private Date                     fuiCreateTime;

    /**
     * 上传文件状态：0停用，1启用
     */
    private EntityState              fuiState;

    private Integer                  loanId;

    public Integer getFuiId() {
        return fuiId;
    }

    public void setFuiId(Integer fuiId) {
        this.fuiId = fuiId;
    }

    public FileUploadClassType getFuiClassType() {
        return fuiClassType;
    }

    public void setFuiClassType(FileUploadClassType fuiClassType) {
        this.fuiClassType = fuiClassType;
    }

    public FileUploadSecondaryClass getFuiSecondaryClass() {
        return fuiSecondaryClass;
    }

    public void setFuiSecondaryClass(FileUploadSecondaryClass fuiSecondaryClass) {
        this.fuiSecondaryClass = fuiSecondaryClass;
    }

    public String getFuiDataId() {
        return fuiDataId;
    }

    public void setFuiDataId(String fuiDataId) {
        this.fuiDataId = fuiDataId;
    }

    public String getFuiFileName() {
        return fuiFileName;
    }

    public void setFuiFileName(String fuiFileName) {
        this.fuiFileName = fuiFileName;
    }

    public String getFuiFilePath() {
        return fuiFilePath;
    }

    public void setFuiFilePath(String fuiFilePath) {
        this.fuiFilePath = fuiFilePath;
    }

    public Date getFuiCreateTime() {
        return fuiCreateTime;
    }

    public void setFuiCreateTime(Date fuiCreateTime) {
        this.fuiCreateTime = fuiCreateTime;
    }

    public EntityState getFuiState() {
        return fuiState;
    }

    public void setFuiState(EntityState fuiState) {
        this.fuiState = fuiState;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

}
