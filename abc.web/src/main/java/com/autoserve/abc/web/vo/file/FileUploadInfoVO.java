package com.autoserve.abc.web.vo.file;

import com.autoserve.abc.service.biz.enums.EntityState;

/**
 * @author RJQ 2014/12/28 18:21.
 */
public class FileUploadInfoVO {
    private Integer file_Id;

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
    private String file_DataId;

    /**
     * 文件名称
     */
    private String file_Name;

    /**
     * 文件路径
     */
    private String file_Path;

    /**
     * 文件上传时间
     */
    private String file_CreateTime;

    /**
     * 上传文件状态：0停用，1启用
     */
    private EntityState fuiState;
    
    private int loanId;

    public Integer getFile_Id() {
        return file_Id;
    }

    public void setFile_Id(Integer file_Id) {
        this.file_Id = file_Id;
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

    public String getFile_DataId() {
        return file_DataId;
    }

    public void setFile_DataId(String file_DataId) {
        this.file_DataId = file_DataId;
    }

    public String getFile_Name() {
        return file_Name;
    }

    public void setFile_Name(String file_Name) {
        this.file_Name = file_Name;
    }

    public String getFile_Path() {
        return file_Path;
    }

    public void setFile_Path(String file_Path) {
        this.file_Path = file_Path;
    }

    public String getFile_CreateTime() {
        return file_CreateTime;
    }

    public void setFile_CreateTime(String file_CreateTime) {
        this.file_CreateTime = file_CreateTime;
    }

    public EntityState getFuiState() {
        return fuiState;
    }

    public void setFuiState(EntityState fuiState) {
        this.fuiState = fuiState;
    }

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
    
}
