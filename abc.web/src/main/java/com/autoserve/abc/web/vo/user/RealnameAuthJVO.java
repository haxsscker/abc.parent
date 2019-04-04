package com.autoserve.abc.web.vo.user;

import com.autoserve.abc.service.biz.enums.DocType;
import com.autoserve.abc.service.biz.enums.ReviewState;
import com.autoserve.abc.service.biz.enums.SexType;
import com.autoserve.abc.service.biz.enums.UserRealnameIsproven;

public class RealnameAuthJVO {

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
     * 真实姓名 abc_realname_auth.rnp_name
     */
    private String               rnpName;

    /**
     * 是否实名认证 1：是 0：否 abc_user.user_realname_isproven
     */
    private UserRealnameIsproven userRealnameIsproven;

    /**
     * 用户积分 abc_user.user_score
     */
    private Integer              userScore;

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
    private String               rnpBirthday;

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
    private String               rnpApplyDate;

    /**
     * 审核状态 0：待审核 1：审核已通过 2：审核未通过 abc_realname_auth.rnp_review_state
     */
    private ReviewState          rnpReviewState;

    /**
     * 审核日期 abc_realname_auth.rnp_review_date
     */
    private String               rnpReviewDate;

    /**
     * 审核意见 abc_realname_auth.rnp_review_note
     */
    private String               rnpReviewNote;

    /**
     * @return abc_realname_auth.rnp_id
     */
    public Integer getRnpId() {
        return rnpId;
    }

    /**
     * @param Integer rnpId (abc_realname_auth.rnp_id )
     */
    public void setRnpId(Integer rnpId) {
        this.rnpId = rnpId;
    }

    /**
     * @return abc_realname_auth.rnp_userid
     */
    public Integer getRnpUserid() {
        return rnpUserid;
    }

    /**
     * @param Integer rnpUserid (abc_realname_auth.rnp_userid )
     */
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

    /**
     * @return abc_realname_auth.rnp_name
     */
    public String getRnpName() {
        return rnpName;
    }

    /**
     * @param String rnpName (abc_realname_auth.rnp_name )
     */
    public void setRnpName(String rnpName) {
        this.rnpName = rnpName;
    }

    /**
     * @return abc_realname_auth.rnp_sex
     */
    public SexType getRnpSex() {
        return rnpSex;
    }

    /**
     * @param Integer rnpSex (abc_realname_auth.rnp_sex )
     */
    public void setRnpSex(SexType rnpSex) {
        this.rnpSex = rnpSex;
    }

    /**
     * @return abc_realname_auth.rnp_nation
     */
    public String getRnpNation() {
        return rnpNation;
    }

    /**
     * @param String rnpNation (abc_realname_auth.rnp_nation )
     */
    public void setRnpNation(String rnpNation) {
        this.rnpNation = rnpNation;
    }

    /**
     * @return abc_realname_auth.rnp_birthday
     */
    public String getRnpBirthday() {
        return rnpBirthday;
    }

    /**
     * @param Date rnpBirthday (abc_realname_auth.rnp_birthday )
     */
    public void setRnpBirthday(String rnpBirthday) {
        this.rnpBirthday = rnpBirthday;
    }

    /**
     * @return abc_realname_auth.rnp_doc_type
     */
    public DocType getRnpDocType() {
        return rnpDocType;
    }

    /**
     * @param String rnpDocType (abc_realname_auth.rnp_doc_type )
     */
    public void setRnpDocType(DocType rnpDocType) {
        this.rnpDocType = rnpDocType;
    }

    /**
     * @return abc_realname_auth.rnp_doc_no
     */
    public String getRnpDocNo() {
        return rnpDocNo;
    }

    /**
     * @param String rnpDocNo (abc_realname_auth.rnp_doc_no )
     */
    public void setRnpDocNo(String rnpDocNo) {
        this.rnpDocNo = rnpDocNo;
    }

    /**
     * @return abc_realname_auth.rnp_native
     */
    public String getRnpNative() {
        return rnpNative;
    }

    /**
     * @param String rnpNative (abc_realname_auth.rnp_native )
     */
    public void setRnpNative(String rnpNative) {
        this.rnpNative = rnpNative;
    }

    /**
     * @return abc_realname_auth.rnp_idcard_front
     */
    public String getRnpIdcardFront() {
        return rnpIdcardFront;
    }

    /**
     * @param String rnpIdcardFront (abc_realname_auth.rnp_idcard_front )
     */
    public void setRnpIdcardFront(String rnpIdcardFront) {
        this.rnpIdcardFront = rnpIdcardFront;
    }

    /**
     * @return abc_realname_auth.rnp_idcard_back
     */
    public String getRnpIdcardBack() {
        return rnpIdcardBack;
    }

    /**
     * @param String rnpIdcardBack (abc_realname_auth.rnp_idcard_back )
     */
    public void setRnpIdcardBack(String rnpIdcardBack) {
        this.rnpIdcardBack = rnpIdcardBack;
    }

    public String getRnpApplyDate() {
        return rnpApplyDate;
    }

    public void setRnpApplyDate(String rnpApplyDate) {
        this.rnpApplyDate = rnpApplyDate;
    }

    public ReviewState getRnpReviewState() {
        return rnpReviewState;
    }

    public void setRnpReviewState(ReviewState rnpReviewState) {
        this.rnpReviewState = rnpReviewState;
    }

    public String getRnpReviewDate() {
        return rnpReviewDate;
    }

    public void setRnpReviewDate(String rnpReviewDate) {
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
