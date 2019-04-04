package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 证明材料
 * @author RJQ 2014/11/13 18:06.
 */
public class FileProveDO {
    /**
     *
     * abc_file_prove.fp_id
     */
    private Integer fpId;

    /**
     * 用户ID 关联user表
     * abc_file_prove.fp_userid
     */
    private Integer fpUserid;

    /**
     * 证明材料类型
     * abc_file_prove.fp_file_type
     */
    private String fpFileType;

    /**
     * 证明材料路径
     * abc_file_prove.fp_file_url
     */
    private String fpFileUrl;

    /**
     * 申请日期
     * abc_file_prove.fp_prove_date
     */
    private Date fpProveDate;

    /**
     * 审核状态	0：待审核 1：审核已通过 2：审核未通过
     * abc_file_prove.fp_prove_state
     */
    private Integer fpProveState;

    /**
     * 备注
     * abc_file_prove.fp_note
     */
    private String fpNote;

    public Integer getFpId() {
        return fpId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    public Integer getFpUserid() {
        return fpUserid;
    }

    public void setFpUserid(Integer fpUserid) {
        this.fpUserid = fpUserid;
    }

    public String getFpFileType() {
        return fpFileType;
    }

    public void setFpFileType(String fpFileType) {
        this.fpFileType = fpFileType;
    }

    public String getFpFileUrl() {
        return fpFileUrl;
    }

    public void setFpFileUrl(String fpFileUrl) {
        this.fpFileUrl = fpFileUrl;
    }

    public Date getFpProveDate() {
        return fpProveDate;
    }

    public void setFpProveDate(Date fpProveDate) {
        this.fpProveDate = fpProveDate;
    }

    public Integer getFpProveState() {
        return fpProveState;
    }

    public void setFpProveState(Integer fpProveState) {
        this.fpProveState = fpProveState;
    }

    public String getFpNote() {
        return fpNote;
    }

    public void setFpNote(String fpNote) {
        this.fpNote = fpNote;
    }
}
