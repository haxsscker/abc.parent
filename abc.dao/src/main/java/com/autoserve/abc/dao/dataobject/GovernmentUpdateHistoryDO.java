package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 机构修改记录，记录修改批次
 * 详细的修改字段信息在GovernmentUpdateDetailDO中
 * abc_gov_update_history
 */
public class GovernmentUpdateHistoryDO {
    /**
     * abc_gov_update_history.guh_id
     */
    private Integer guhId;

    /**
     * 机构ID 关联机构信息表
     * abc_gov_update_history.guh_govid
     */
    private Integer guhGovid;

    /**
     * 修改人 关联员工表
     * abc_gov_update_history.guh_update_emp
     */
    private Integer guhUpdateEmp;

    /**
     * 修改日期
     * abc_gov_update_history.guh_update_date
     */
    private Date guhUpdateDate;

    /**
     * 修改批次
     * abc_gov_update_history.guh_update_number
     */
    private Integer guhUpdateNumber;

    /**
     * 审核人 关联员工表
     * abc_gov_update_history.guh_auditor_id
     */
    private Integer guhAuditorId;

    /**
     * 审核日期
     * abc_gov_update_history.guh_auditor_date
     */
    private Date guhAuditorDate;

    /**
     * 审核意见
     * abc_gov_update_history.guh_audit_note
     */
    private String guhAuditNote;

    /**
     * 审核状态	0：待审核 1：审核已通过 2：审核未通过
     * abc_gov_update_history.guh_audit_state
     */
    private Integer guhAuditState;

    public Integer getGuhId() {
        return guhId;
    }

    public void setGuhId(Integer guhId) {
        this.guhId = guhId;
    }

    public Integer getGuhGovid() {
        return guhGovid;
    }

    public void setGuhGovid(Integer guhGovid) {
        this.guhGovid = guhGovid;
    }

    public Integer getGuhUpdateEmp() {
        return guhUpdateEmp;
    }

    public void setGuhUpdateEmp(Integer guhUpdateEmp) {
        this.guhUpdateEmp = guhUpdateEmp;
    }

    public Date getGuhUpdateDate() {
        return guhUpdateDate;
    }

    public void setGuhUpdateDate(Date guhUpdateDate) {
        this.guhUpdateDate = guhUpdateDate;
    }

    public Integer getGuhUpdateNumber() {
        return guhUpdateNumber;
    }

    public void setGuhUpdateNumber(Integer guhUpdateNumber) {
        this.guhUpdateNumber = guhUpdateNumber;
    }

    public Integer getGuhAuditorId() {
        return guhAuditorId;
    }

    public void setGuhAuditorId(Integer guhAuditorId) {
        this.guhAuditorId = guhAuditorId;
    }

    public Date getGuhAuditorDate() {
        return guhAuditorDate;
    }

    public void setGuhAuditorDate(Date guhAuditorDate) {
        this.guhAuditorDate = guhAuditorDate;
    }

    public String getGuhAuditNote() {
        return guhAuditNote;
    }

    public void setGuhAuditNote(String guhAuditNote) {
        this.guhAuditNote = guhAuditNote;
    }

    public Integer getGuhAuditState() {
        return guhAuditState;
    }

    public void setGuhAuditState(Integer guhAuditState) {
        this.guhAuditState = guhAuditState;
    }
}
