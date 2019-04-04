package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * ActivityRecordDO abc_activity_record
 */
public class ActivityRecordDO {
    /**
     * 主键id abc_activity_record.ar_id
     */
    private Integer arId;

    /**
     * 活动类型 1:投资活动 2:转让活动 3:收购活动 abc_activity_record.ar_type
     */
    private Integer arType;

    /**
     * 外键id abc_activity_record.ar_foreign_id
     */
    private Integer arForeignId;

    /**
     * 创建时间 abc_activity_record.ar_createtime
     */
    private Date    arCreatetime;

    /**
     * 创建人 abc_activity_record.ar_creator
     */
    private Integer arCreator;

    /**
     * 结束时间 abc_activity_record.ar_endtime
     */
    private Date    arEndtime;

    public Integer getArId() {
        return arId;
    }

    public void setArId(Integer arId) {
        this.arId = arId;
    }

    public Integer getArType() {
        return arType;
    }

    public void setArType(Integer arType) {
        this.arType = arType;
    }

    public Integer getArForeignId() {
        return arForeignId;
    }

    public void setArForeignId(Integer arForeignId) {
        this.arForeignId = arForeignId;
    }

    public Date getArCreatetime() {
        return arCreatetime;
    }

    public void setArCreatetime(Date arCreatetime) {
        this.arCreatetime = arCreatetime;
    }

    public Integer getArCreator() {
        return arCreator;
    }

    public void setArCreator(Integer arCreator) {
        this.arCreator = arCreator;
    }

    public Date getArEndtime() {
        return arEndtime;
    }

    public void setArEndtime(Date arEndtime) {
        this.arEndtime = arEndtime;
    }
}
