package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 现场认证申请
 * @author RJQ 2014/11/13 18:14.
 */
public class SceneProveDO {
    /**
     *
     * abc_scene_prove.sp_id
     */
    private Integer spId;

    /**
     * 用户ID 关联user表
     * abc_scene_prove.sp_user_id
     */
    private Integer spUserId;

    /**
     * 申请日期
     * abc_scene_prove.sp_apply_date
     */
    private Date spApplyDate;

    /**
     * 备注
     * abc_scene_prove.sp_note
     */
    private String spNote;

    /**
     * 审核状态	0：待审核 1：审核已通过 2：审核未通过
     * abc_scene_prove.sp_prove_state
     */
    private Integer spProveState;

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getSpUserId() {
        return spUserId;
    }

    public void setSpUserId(Integer spUserId) {
        this.spUserId = spUserId;
    }

    public Date getSpApplyDate() {
        return spApplyDate;
    }

    public void setSpApplyDate(Date spApplyDate) {
        this.spApplyDate = spApplyDate;
    }

    public String getSpNote() {
        return spNote;
    }

    public void setSpNote(String spNote) {
        this.spNote = spNote;
    }

    public Integer getSpProveState() {
        return spProveState;
    }

    public void setSpProveState(Integer spProveState) {
        this.spProveState = spProveState;
    }
}
