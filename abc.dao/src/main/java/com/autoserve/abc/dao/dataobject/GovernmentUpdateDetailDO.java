package com.autoserve.abc.dao.dataobject;

/**
 * 记录机构信息在某个修改批次中修改的字段和修改前，修改后的值
 * abc_gov_update_detail
 */
public class GovernmentUpdateDetailDO {
    /**
     * abc_gov_update_detail.guh_id
     */
    private Integer guhId;

    /**
     * 关联更新记录表ID
     * abc_gov_update_detail.guh_update_history_id
     */
    private Integer guhUpdateHistoryId;

    /**
     * 修改字段
     * abc_gov_update_detail.guh_field
     */
    private String guhField;

    /**
     * 修改前值
     * abc_gov_update_detail.guh_field_old
     */
    private String guhFieldOld;

    /**
     * 修改后值
     * abc_gov_update_detail.guh_filed_new
     */
    private String guhFiledNew;

    public Integer getGuhId() {
        return guhId;
    }

    public void setGuhId(Integer guhId) {
        this.guhId = guhId;
    }

    public Integer getGuhUpdateHistoryId() {
        return guhUpdateHistoryId;
    }

    public void setGuhUpdateHistoryId(Integer guhUpdateHistoryId) {
        this.guhUpdateHistoryId = guhUpdateHistoryId;
    }

    public String getGuhField() {
        return guhField;
    }

    public void setGuhField(String guhField) {
        this.guhField = guhField;
    }

    public String getGuhFieldOld() {
        return guhFieldOld;
    }

    public void setGuhFieldOld(String guhFieldOld) {
        this.guhFieldOld = guhFieldOld;
    }

    public String getGuhFiledNew() {
        return guhFiledNew;
    }

    public void setGuhFiledNew(String guhFiledNew) {
        this.guhFiledNew = guhFiledNew;
    }
}
