package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 实名认证申请
 * 
 * @author RJQ 2014/11/13 18:12.
 */
public class RealnameAuthDO {
    /**
     * abc_realname_auth.rnp_id
     */
    private Integer rnpId;

    /**
     * 用户ID abc_realname_auth.rnp_userid
     */
    private Integer rnpUserid;

    /**
     * 真实姓名 abc_realname_auth.rnp_name
     */
    private String  rnpName;

    /**
     * 性别 0：女 1：男 abc_realname_auth.rnp_sex
     */
    private Integer rnpSex;

    /**
     * 民族 abc_realname_auth.rnp_nation
     */
    private String  rnpNation;

    /**
     * 出生日期 abc_realname_auth.rnp_birthday
     */
    private Date    rnpBirthday;

    /**
     * 证件类型 abc_realname_auth.rnp_doc_type
     */
    private String  rnpDocType;

    /**
     * 证件号码 abc_realname_auth.rnp_doc_no
     */
    private String  rnpDocNo;

    /**
     * 籍贯 abc_realname_auth.rnp_native
     */
    private String  rnpNative;

    /**
     * 身份证正面照片 abc_realname_auth.rnp_idcard_front
     */
    private String  rnpIdcardFront;

    /**
     * 身份证背面照片 abc_realname_auth.rnp_idcard_back
     */
    private String  rnpIdcardBack;

    /**
     * 申请日期 abc_realname_auth.rnp_apply_date
     */
    private Date    rnpApplyDate;

    /**
     * 审核状态 0：待审核 1：审核已通过 2：审核未通过 abc_realname_auth.rnp_review_state
     */
    private Integer rnpReviewState;
    /**
     * 审核类型 1：自动审核 2：自动审核 3：发送平台审核 abc_realname_auth.rnp_review_type
     */
    private Integer rnpReviewType;

    public Integer getRnpReviewType() {
        return rnpReviewType;
    }

    public void setRnpReviewType(Integer rnpReviewType) {
        this.rnpReviewType = rnpReviewType;
    }

    /**
     * 审核日期 abc_realname_auth.rnp_review_date
     */
    private Date   rnpReviewDate;

    /**
     * 审核意见 abc_realname_auth.rnp_review_note
     */
    private String rnpReviewNote;

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

    public String getRnpName() {
        return rnpName;
    }

    public void setRnpName(String rnpName) {
        this.rnpName = rnpName;
    }

    public Integer getRnpSex() {
        return rnpSex;
    }

    public void setRnpSex(Integer rnpSex) {
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

    public String getRnpDocType() {
        return rnpDocType;
    }

    public void setRnpDocType(String rnpDocType) {
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

    public Integer getRnpReviewState() {
        return rnpReviewState;
    }

    public void setRnpReviewState(Integer rnpReviewState) {
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
}
