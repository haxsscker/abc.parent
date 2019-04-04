package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.DocType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.SexType;
import com.autoserve.abc.service.biz.enums.UserRealnameIsproven;

/**
 * 类RealnameAuthJ.java的实现描述：实名认证表与用户表连表的entity
 * 
 * @author fangrui 2014年12月23日 下午1:09:27
 */
public class RealnameAuthJ {

    /**
     * abc_realname_auth.rnp_id
     */
    private Integer              rnpId;

    /**
     * 用户ID abc_realname_auth.rnp_userid
     */
    private Integer              rnpUserid;

    /**
     * 用户名 abc_user.user_name
     */
    private String               userName;

    /**
     * 用户积分 abc_user.user_score
     */
    private Integer              userScore;

    /**
     * 真实姓名 abc_realname_auth.rnp_name
     */
    private String               rnpName;

    /**
     * 是否实名认证 1：是 0：否 abc_user.user_realname_isproven
     */
    private UserRealnameIsproven userRealnameIsproven;

    /**
     * 性别 0：女 1：男 abc_realname_auth.rnp_sex
     */
    private SexType              rnpSex;

    /**
     * 民族 abc_realname_auth.rnp_nation
     */
    private String               rnpNation;

    /**
     * 出生日期 abc_realname_auth.rnp_birthday
     */
    private Date                 rnpBirthday;

    /**
     * 证件类型 abc_realname_auth.rnp_doc_type
     */
    private DocType              rnpDocType;

    /**
     * 证件号码 abc_realname_auth.rnp_doc_no
     */
    private String               rnpDocNo;

    /**
     * 籍贯 abc_realname_auth.rnp_native
     */
    private String               rnpNative;

    /**
     * 身份证正面照片 abc_realname_auth.rnp_idcard_front
     */
    private String               rnpIdcardFront;

    /**
     * 身份证背面照片 abc_realname_auth.rnp_idcard_back
     */
    private String               rnpIdcardBack;

    /**
     * 申请日期 abc_realname_auth.rnp_apply_date
     */
    private Date                 rnpApplyDate;

    /**
     * 审核状态 0：待审核 1：审核已通过 2：审核未通过 abc_realname_auth.rnp_review_state
     */
    private ReviewState          rnpReviewState;

    /**
     * 审核日期 abc_realname_auth.rnp_review_date
     */
    private Date                 rnpReviewDate;

    /**
     * 审核意见 abc_realname_auth.rnp_review_note
     */
    private String               rnpReviewNote;

    public Integer getRnpId() {
        return rnpId;
    }

    public void setRnpId(Integer rnpId) {
        this.rnpId = rnpId;
    }

    public Integer getRnpUserid() {
        return rnpUserid;
    }

    public void setRnpUserid(Integer rnpUserid) {
        this.rnpUserid = rnpUserid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserRealnameIsproven getUserRealnameIsproven() {
        return userRealnameIsproven;
    }

    public void setUserRealnameIsproven(UserRealnameIsproven userRealnameIsproven) {
        this.userRealnameIsproven = userRealnameIsproven;
    }

    public String getRnpName() {
        return rnpName;
    }

    public void setRnpName(String rnpName) {
        this.rnpName = rnpName;
    }

    public SexType getRnpSex() {
        return rnpSex;
    }

    public void setRnpSex(SexType rnpSex) {
        this.rnpSex = rnpSex;
    }

    public String getRnpNation() {
        return rnpNation;
    }

    public void setRnpNation(String rnpNation) {
        this.rnpNation = rnpNation;
    }

    public Date getRnpBirthday() {
        return rnpBirthday;
    }

    public void setRnpBirthday(Date rnpBirthday) {
        this.rnpBirthday = rnpBirthday;
    }

    public DocType getRnpDocType() {
        return rnpDocType;
    }

    public void setRnpDocType(DocType rnpDocType) {
        this.rnpDocType = rnpDocType;
    }

    public String getRnpDocNo() {
        return rnpDocNo;
    }

    public void setRnpDocNo(String rnpDocNo) {
        this.rnpDocNo = rnpDocNo;
    }

    public String getRnpNative() {
        return rnpNative;
    }

    public void setRnpNative(String rnpNative) {
        this.rnpNative = rnpNative;
    }

    public String getRnpIdcardFront() {
        return rnpIdcardFront;
    }

    public void setRnpIdcardFront(String rnpIdcardFront) {
        this.rnpIdcardFront = rnpIdcardFront;
    }

    public String getRnpIdcardBack() {
        return rnpIdcardBack;
    }

    public void setRnpIdcardBack(String rnpIdcardBack) {
        this.rnpIdcardBack = rnpIdcardBack;
    }

    public Date getRnpApplyDate() {
        return rnpApplyDate;
    }

    public void setRnpApplyDate(Date rnpApplyDate) {
        this.rnpApplyDate = rnpApplyDate;
    }

    public ReviewState getRnpReviewState() {
        return rnpReviewState;
    }

    public void setRnpReviewState(ReviewState rnpReviewState) {
        this.rnpReviewState = rnpReviewState;
    }

    public Date getRnpReviewDate() {
        return rnpReviewDate;
    }

    public void setRnpReviewDate(Date rnpReviewDate) {
        this.rnpReviewDate = rnpReviewDate;
    }

    public String getRnpReviewNote() {
        return rnpReviewNote;
    }

    public void setRnpReviewNote(String rnpReviewNote) {
        this.rnpReviewNote = rnpReviewNote;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

}
