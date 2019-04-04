package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 *  影像上传文件信息
 */
public class FileUploadInfoDO {

    private Integer fuiId;

    /**
     * 模块类型
     */
    private Integer fuiClassType;

    /**
     * 二级模块类型
     */
    private Integer fuiSecondaryClass;

    /**
     * 处理信息id
     */
    private String fuiDataId;

    /**
     * 文件名称
     */
    private String fuiFileName;

    /**
     * 文件路径
     */
    private String fuiFilePath;

    /**
     * 文件上传时间
     */
    private Date fuiCreateTime;

    /**
     * 上传文件状态：0停用，1启用
     */
    private Integer fuiState;
    
    private Integer loanId;

    public Integer getFuiId() {
        return fuiId;
    }

    public void setFuiId(Integer fuiId) {
        this.fuiId = fuiId;
    }

    public Integer getFuiClassType() {
        return fuiClassType;
    }

    public void setFuiClassType(Integer fuiClassType) {
        this.fuiClassType = fuiClassType;
    }

    public Integer getFuiSecondaryClass() {
        return fuiSecondaryClass;
    }

    public void setFuiSecondaryClass(Integer fuiSecondaryClass) {
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
        this.fuiFileName = fuiFileName == null ? null : fuiFileName.trim();
    }

    public String getFuiFilePath() {
        return fuiFilePath;
    }


    public void setFuiFilePath(String fuiFilePath) {
        this.fuiFilePath = fuiFilePath == null ? null : fuiFilePath.trim();
    }

    public Date getFuiCreateTime() {
        return fuiCreateTime;
    }

    public void setFuiCreateTime(Date fuiCreateTime) {
        this.fuiCreateTime = fuiCreateTime;
    }

    public Integer getFuiState() {
        return fuiState;
    }

    public void setFuiState(Integer fuiState) {
        this.fuiState = fuiState;
    }

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}
}