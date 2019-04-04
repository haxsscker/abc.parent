package com.autoserve.abc.web.vo.user;

import java.math.BigDecimal;

/**
 * @author RJQ 2014/12/16 15:47.
 */
public class UserVO {
    /**
     * 主键 abc_user.user_id
     */
    private Integer userId;

    /**
     * 用户名 abc_user.user_name
     */
    private String userName;

    /**
     * 真实姓名 abc_user.user_real_name
     */
    private String userRealName;

    /**
     * 用户类型 1：个人 2：企业 abc_user.user_type
     */
    private String userType;

    /**
     * 证件类型 abc_user.user_doc_type
     */
    private String userDocType;

    /**
     * 证件号码 abc_user.user_doc_no
     */
    private String userDocNo;

    /**
     * 性别 1:男 0:女 abc_user.user_sex
     */
    private String userSex;

    /**
     * 生日 abc_user.user_birthday
     */
    private String userBirthday;

    /**
     * 籍贯 abc_user.user_native
     */
    private String userNative;

    /**
     * 手机号码 abc_user.user_phone
     */
    private String userPhone;

    /**
     * 电子邮箱 abc_user.user_email
     */
    private String userEmail;

    /**
     * 用户积分 abc_user.user_score
     */
    private Integer userScore;

    /**
     * 积分最后修改时间 abc_user.user_score_lastmodifytime
     */
    private String userScoreLastmodifytime;

    /**
     * 是否启用 1：是 0：否 2:已删除 abc_user.user_state
     */
    private String userState;

    /**
     * 婚姻状况 1:已婚 2:未婚 3:离婚 4:丧偶 abc_user.user_marry
     */
    private String userMarry;

    /**
     * 月收入 abc_user.user_month_income
     */
    private BigDecimal userMonthIncome;

    /**
     * 注册日期 abc_user.user_register_date
     */
    private String userRegisterDate;
    /**
     * 手机绑定状态：1：已绑定  0：未绑定 abc_user.user_mobile_isbinded
     */
    private String userMobileIsbinded;
    /**
     * 邮箱绑定状态： 1：已绑定 0:未绑定 abc_user.user_email_isbinded
     */
    private String userEmailIsbinded;
    /**
     * 手机认证日期abc_user.user_mobile_verify_date
     */
    private String userMobileVerifyDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserDocType() {
        return userDocType;
    }

    public void setUserDocType(String userDocType) {
        this.userDocType = userDocType;
    }

    public String getUserDocNo() {
        return userDocNo;
    }

    public void setUserDocNo(String userDocNo) {
        this.userDocNo = userDocNo;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserNative() {
        return userNative;
    }

    public void setUserNative(String userNative) {
        this.userNative = userNative;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserScore() {
    	if(userScore==null)
    		return Integer.valueOf(0);
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public String getUserScoreLastmodifytime() {
        return userScoreLastmodifytime;
    }

    public void setUserScoreLastmodifytime(String userScoreLastmodifytime) {
        this.userScoreLastmodifytime = userScoreLastmodifytime;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserMarry() {
        return userMarry;
    }

    public void setUserMarry(String userMarry) {
        this.userMarry = userMarry;
    }

    public BigDecimal getUserMonthIncome() {
        return userMonthIncome;
    }

    public void setUserMonthIncome(BigDecimal userMonthIncome) {
        this.userMonthIncome = userMonthIncome;
    }

    public String getUserRegisterDate() {
        return userRegisterDate;
    }

    public void setUserRegisterDate(String userRegisterDate) {
        this.userRegisterDate = userRegisterDate;
    }

    public String getUserMobileIsbinded() {
        return userMobileIsbinded;
    }

    public void setUserMobileIsbinded(String userMobileIsbinded) {
        this.userMobileIsbinded = userMobileIsbinded;
    }

    public String getUserEmailIsbinded() {
        return userEmailIsbinded;
    }

    public void setUserEmailIsbinded(String userEmailIsbinded) {
        this.userEmailIsbinded = userEmailIsbinded;
    }

    public String getUserMobileVerifyDate() {
        return userMobileVerifyDate;
    }

    public void setUserMobileVerifyDate(String userMobileVerifyDate) {
        this.userMobileVerifyDate = userMobileVerifyDate;
    }
}
