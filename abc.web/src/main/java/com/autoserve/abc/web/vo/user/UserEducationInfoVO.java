package com.autoserve.abc.web.vo.user;

import com.autoserve.abc.service.biz.enums.EducationLevel;

/**
 * 类UserEducationVO.java的实现描述：TODO 类实现描述
 * 
 * @author lipeng 2014年12月18日 上午10:07:42
 */
public class UserEducationInfoVO {

    /**
     * 主键 abc_user_education.ue_id
     */
    private Integer        ueId;

    /**
     * 用户ID abc_user_education.ue_userid
     */
    private Integer        ueUserid;

    /**
     * 最高学历 0：小学 1：初中 2：高中 3：中专 4：专科 5：本科 6：研究生 7： 博士 8：博士后 9：其它
     * abc_user_education.ue_top_education
     */
    private EducationLevel ueTopEducation;

    /**
     * 最高学历院校 abc_user_education.ue_top_school
     */
    private String         ueTopSchool;

    /**
     * 专业 abc_user_education.ue_major
     */
    private String         ueMajor;

    /**
     * 入学时间 abc_user_education.ue_start_time
     */
    private String         ueStartTime;

    /**
     * 毕业时间 abc_user_education.ue_end_time
     */
    private String         ueEndTime;

    /**
     * @return abc_user_education.ue_id
     */
    public Integer getUeId() {
        return ueId;
    }

    /**
     * @param Integer ueId (abc_user_education.ue_id )
     */
    public void setUeId(Integer ueId) {
        this.ueId = ueId;
    }

    /**
     * @return abc_user_education.ue_userid
     */
    public Integer getUeUserid() {
        return ueUserid;
    }

    /**
     * @param Integer ueUserid (abc_user_education.ue_userid )
     */
    public void setUeUserid(Integer ueUserid) {
        this.ueUserid = ueUserid;
    }

    public EducationLevel getUeTopEducation() {
        return ueTopEducation;
    }

    public void setUeTopEducation(EducationLevel ueTopEducation) {
        this.ueTopEducation = ueTopEducation;
    }

    /**
     * @return abc_user_education.ue_top_school
     */
    public String getUeTopSchool() {
        return ueTopSchool;
    }

    /**
     * @param String ueTopSchool (abc_user_education.ue_top_school )
     */
    public void setUeTopSchool(String ueTopSchool) {
        this.ueTopSchool = ueTopSchool == null ? "" : ueTopSchool.trim();
    }

    /**
     * @return abc_user_education.ue_major
     */
    public String getUeMajor() {
        return ueMajor;
    }

    /**
     * @param String ueMajor (abc_user_education.ue_major )
     */
    public void setUeMajor(String ueMajor) {
        this.ueMajor = ueMajor == null ? "" : ueMajor.trim();
    }

    /**
     * @return abc_user_education.ue_start_time
     */
    public String getUeStartTime() {
        return ueStartTime;
    }

    /**
     * @param Date ueStartTime (abc_user_education.ue_start_time )
     */
    public void setUeStartTime(String ueStartTime) {
        this.ueStartTime = ueStartTime;
    }

    /**
     * @return abc_user_education.ue_end_time
     */
    public String getUeEndTime() {
        return ueEndTime;
    }

    /**
     * @param Date ueEndTime (abc_user_education.ue_end_time )
     */
    public void setUeEndTime(String ueEndTime) {
        this.ueEndTime = ueEndTime;
    }

}
