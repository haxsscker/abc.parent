package com.autoserve.abc.dao.dataobject;

/**
 * 个人其他信息
 * @author RJQ 2014/11/13 18:21.
 */
public class UserOtherInfoDO {
    /**
     *
     * abc_user_other_info.uoi_id
     */
    private Integer uoiId;

    /**
     * 用户ID
     * abc_user_other_info.uoi_userid
     */
    private Integer uoiUserid;

    /**
     * 个人能力
     * abc_user_other_info.uoi_ability
     */
    private String uoiAbility;

    /**
     * 个人爱好
     * abc_user_other_info.uoi_hobby
     */
    private String uoiHobby;

    /**
     * 备注
     * abc_user_other_info.uoi_note
     */
    private String uoiNote;

    public Integer getUoiId() {
        return uoiId;
    }

    public void setUoiId(Integer uoiId) {
        this.uoiId = uoiId;
    }

    public Integer getUoiUserid() {
        return uoiUserid;
    }

    public void setUoiUserid(Integer uoiUserid) {
        this.uoiUserid = uoiUserid;
    }

    public String getUoiAbility() {
        return uoiAbility;
    }

    public void setUoiAbility(String uoiAbility) {
        this.uoiAbility = uoiAbility;
    }

    public String getUoiHobby() {
        return uoiHobby;
    }

    public void setUoiHobby(String uoiHobby) {
        this.uoiHobby = uoiHobby;
    }

    public String getUoiNote() {
        return uoiNote;
    }

    public void setUoiNote(String uoiNote) {
        this.uoiNote = uoiNote;
    }
}
