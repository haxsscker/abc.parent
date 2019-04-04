package com.autoserve.abc.web.vo.user;

/**
 * 类UserOtherInfoVO.java的实现描述：TODO 类实现描述
 * 
 * @author lipeng 2014年12月17日 下午7:14:29
 */
public class UserOtherInfoVO {

    /**
     * abc_user_other_info.uoi_id
     */
    private Integer uoiId;

    /**
     * 用户ID abc_user_other_info.uoi_userid
     */
    private Integer uoiUserid;

    /**
     * 个人能力 abc_user_other_info.uoi_ability
     */
    private String  uoiAbility;

    /**
     * 个人爱好 abc_user_other_info.uoi_hobby
     */
    private String  uoiHobby;

    /**
     * 备注 abc_user_other_info.uoi_note
     */
    private String  uoiNote;

    /**
     * @return abc_user_other_info.uoi_id
     */
    public Integer getUoiId() {
        return uoiId;
    }

    /**
     * @param Integer uoiId (abc_user_other_info.uoi_id )
     */
    public void setUoiId(Integer uoiId) {
        this.uoiId = uoiId;
    }

    /**
     * @return abc_user_other_info.uoi_userid
     */
    public Integer getUoiUserid() {
        return uoiUserid;
    }

    /**
     * @param Integer uoiUserid (abc_user_other_info.uoi_userid )
     */
    public void setUoiUserid(Integer uoiUserid) {
        this.uoiUserid = uoiUserid;
    }

    /**
     * @return abc_user_other_info.uoi_ability
     */
    public String getUoiAbility() {
        return uoiAbility;
    }

    /**
     * @param String uoiAbility (abc_user_other_info.uoi_ability )
     */
    public void setUoiAbility(String uoiAbility) {
        this.uoiAbility = uoiAbility == null ? "" : uoiAbility.trim();
    }

    /**
     * @return abc_user_other_info.uoi_hobby
     */
    public String getUoiHobby() {
        return uoiHobby;
    }

    /**
     * @param String uoiHobby (abc_user_other_info.uoi_hobby )
     */
    public void setUoiHobby(String uoiHobby) {
        this.uoiHobby = uoiHobby == null ? "" : uoiHobby.trim();
    }

    /**
     * @return abc_user_other_info.uoi_note
     */
    public String getUoiNote() {
        return uoiNote;
    }

    /**
     * @param String uoiNote (abc_user_other_info.uoi_note )
     */
    public void setUoiNote(String uoiNote) {
        this.uoiNote = uoiNote == null ? "" : uoiNote.trim();
    }

}
