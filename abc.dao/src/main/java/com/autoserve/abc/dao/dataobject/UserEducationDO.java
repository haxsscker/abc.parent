package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 个人教育背景
 *
 * @author RJQ 2014/11/13 18:18.
 */
public class UserEducationDO {
    /**
     * 主键
     * abc_user_education.ue_id
     */
    private Integer ueId;

    /**
     * 用户ID
     * abc_user_education.ue_userid
     */
    private Integer ueUserid;

    /**
     * 最高学历	0：小学 1：初中 2：高中 3：中专 4：专科 5：本科 6：研究生  7： 博士  8：博士后  9：其它
     * abc_user_education.ue_top_education
     */
    private Integer ueTopEducation;

    /**
     * 最高学历院校
     * abc_user_education.ue_top_school
     */
    private String ueTopSchool;

    /**
     * 专业
     * abc_user_education.ue_major
     */
    private String ueMajor;

    /**
     * 入学时间
     * abc_user_education.ue_start_time
     */
    private Date ueStartTime;

    /**
     * 毕业时间
     * abc_user_education.ue_end_time
     */
    private Date ueEndTime;

    public Integer getUeId() {
        return ueId;
    }

    public void setUeId(Integer ueId) {
        this.ueId = ueId;
    }

    public Integer getUeUserid() {
        return ueUserid;
    }

    public void setUeUserid(Integer ueUserid) {
        this.ueUserid = ueUserid;
    }

    public Integer getUeTopEducation() {
        return ueTopEducation;
    }

    public void setUeTopEducation(Integer ueTopEducation) {
        this.ueTopEducation = ueTopEducation;
    }

    public String getUeTopSchool() {
        return ueTopSchool;
    }

    public void setUeTopSchool(String ueTopSchool) {
        this.ueTopSchool = ueTopSchool;
    }

    public String getUeMajor() {
        return ueMajor;
    }

    public void setUeMajor(String ueMajor) {
        this.ueMajor = ueMajor;
    }

    public Date getUeStartTime() {
        return ueStartTime;
    }

    public void setUeStartTime(Date ueStartTime) {
        this.ueStartTime = ueStartTime;
    }

    public Date getUeEndTime() {
        return ueEndTime;
    }

    public void setUeEndTime(Date ueEndTime) {
        this.ueEndTime = ueEndTime;
    }
}
