/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 公司用户DO
 * 
 * @author dongxuejiao 2014年12月19日 下午8:02:29
 */
public class CompanyUserJDO {
    /**
     * abc_company_customer.cc_id
     */
    private Integer    ccId;

    /**
     * 用户ID abc_company_customer.cc_userid
     */
    private Integer    ccUserid;

    /**
     * 公司名称 abc_company_customer.cc_company_name
     */
    private String     ccCompanyName;

    /**
     * 组织机构代码 abc_company_customer.cc_no
     */
    private String     ccNo;

    /**
     * 企业性质 1：政府机关 2：国有企业 3：台（港、澳）资企业 4：合资企业 5：个体户 6：事业性单位 7：私营企业
     * abc_company_customer.cc_company_type
     */
    private Integer    ccCompanyType;

    /**
     * 企业规模 1: 50人之内 2: 50~500人之间 3: 500人以上
     * abc_company_customer.cc_company_scale
     */
    private Integer    ccCompanyScale;

    /**
     * 资产总额 abc_company_customer.cc_total_capital
     */
    private BigDecimal ccTotalCapital;

    /**
     * 注册资金 abc_company_customer.cc_register_capital
     */
    private BigDecimal ccRegisterCapital;

    /**
     * 注册日期 abc_company_customer.cc_register_date
     */
    private Date       ccRegisterDate;
    /**
     * 注册日期字符串 abc_company_customer.cc_register_date
     */
    private String       ccRegisterDateStr;

    /**
     * 注册地址 abc_company_customer.cc_register_address
     */
    private String     ccRegisterAddress;

    /**
     * 联系人姓名 abc_company_customer.cc_contact_name
     */
    private String     ccContactName;

    /**
     * 联系电话 abc_company_customer.cc_contact_phone
     */
    private String     ccContactPhone;

    /**
     * 联系地址 abc_company_customer.cc_contact_address
     */
    private String     ccContactAddress;

    /**
     * 法定代表人 abc_company_customer.cc_corporate
     */
    private String     ccCorporate;

    /**
     * 证件类型 abc_company_customer.cc_doc_type
     */
    private String     ccDocType;

    /**
     * 证件号码 abc_company_customer.cc_doc_no
     */
    private String     ccDocNo;

    /**
     * 公司地址 abc_company_customer.cc_company_address
     */
    private String     ccCompanyAddress;

    /**
     * 营业执照 abc_company_customer.cc_business_license
     */
    private String     ccBusinessLicense;

    /**
     * 营业执照号码 abc_company_customer.cc_license_no
     */
    private String     ccLicenseNo;

    /**
     * 公司简介 abc_company_customer.cc_company_profile
     */
    private String     ccCompanyProfile;

    /**
     * 公司成就 abc_company_customer.cc_company_achievement
     */
    private String     ccCompanyAchievement;
    /**
     * 主键 abc_user.user_id
     */
    private Integer    userId;

    /**
     * 用户名 abc_user.user_name
     */
    private String     userName;

    /**
     * 真实姓名 abc_user.user_real_name
     */
    private String     userRealName;

    /**
     * 登录密码 abc_user.user_pwd
     */
    private String     userPwd;

    /**
     * 交易密码 abc_user.user_deal_pwd
     */
    private String     userDealPwd;

    /**
     * 用户类型 1：个人 2：企业 abc_user.user_type
     */
    private Integer    userType;

    /**
     * 证件类型 abc_user.user_doc_type
     */
    private String     userDocType;

    /**
     * 证件号码 abc_user.user_doc_no
     */
    private String     userDocNo;

    /**
     * 性别 1:男 0:女 abc_user.user_sex
     */
    private Integer    userSex;

    /**
     * 生日 abc_user.user_birthday
     */
    private Date       userBirthday;

    /**
     * 民族 abc_user.user_nation
     */
    private String     userNation;

    /**
     * 籍贯 abc_user.user_native
     */
    private String     userNative;

    /**
     * 手机号码 abc_user.user_phone
     */
    private String     userPhone;

    /**
     * 电子邮箱 abc_user.user_email
     */
    private String     userEmail;

    /**
     * 婚姻状况 1:已婚 2:未婚 3:离婚 4:丧偶 abc_user.user_marry
     */
    private Integer    userMarry;

    /**
     * 月收入 abc_user.user_month_income
     */
    private BigDecimal userMonthIncome;

    /**
     * 登录次数 abc_user.user_login_count
     */
    private Integer    userLoginCount;

    /**
     * 网站头像 abc_user.user_head_img
     */
    private String     userHeadImg;

    /**
     * 用户积分 abc_user.user_score
     */
    private Integer    userScore;

    /**
     * 积分最后修改时间 abc_user.user_score_lastmodifytime
     */
    private Date       userScoreLastmodifytime;

    /**
     * 是否启用 1：是 0：否 -1:已删除 abc_user.user_state
     */
    private Integer    userState;

    /**
     * 是否开启自动投标计划 1：开启 0：未开启 abc_user.user_auto_invest
     */
    private Integer    userAutoInvest;

    /**
     * 推荐人id abc_user.user_recommend_userid
     */
    private Integer    userRecommendUserid;

    /**
     * 信用额度 abc_user.user_loan_credit
     */
    private BigDecimal userLoanCredit;

    /**
     * 可用信用额度 abc_user.user_credit_sett
     */
    private BigDecimal userCreditSett;

    /**
     * 注册日期 abc_user.user_register_date
     */
    private Date       userRegisterDate;
    //注册开始日期
    private Date 	   startRegisterDate;
    //注册结束日期
    private Date 	   endRegisterDate;
    /**
     * 是否实名认证 1：是 0：否 abc_user.user_realname_isproven
     */
    private Integer    userRealnameIsproven;

    /**
     * 是否绑定手机号 1：是 0：否 abc_user.user_mobile_isbinded
     */
    private Integer    userMobileIsbinded;

    /**
     * 是否绑定邮箱 1：是 0：否 abc_user.user_email_isbinded
     */
    private Integer    userEmailIsbinded;

    /**
     * 是否绑定银行卡 1：是 0：否 abc_user.user_bankcard_isbinded
     */
    private Integer    userBankcardIsbinded;

    /**
     * 邮箱激活时验证码 abc_user.user_email_verify_code
     */
    private String     userEmailVerifyCode;

    /**
     * 邮箱激活时时间 abc_user.user_email_verify_date
     */
    private Date       userEmailVerifyDate;

    /**
     * 手机认证日期 abc_user.user_mobile_verify_date
     */
    private Date       userMobileVerifyDate;

    /**
     * 1：已在线 0：未在线 abc_user.user_isonline
     */
    private Integer    userIsonline;
    /**
     * 免费提现额度
     */
    private BigDecimal userCashQuota;

   

	public BigDecimal getUserCashQuota() {
		return userCashQuota;
	}

	public void setUserCashQuota(BigDecimal userCashQuota) {
		this.userCashQuota = userCashQuota;
	}

	public Integer getCcId() {
        return ccId;
    }

    public void setCcId(Integer ccId) {
        this.ccId = ccId;
    }

    public Integer getCcUserid() {
        return ccUserid;
    }

    public Date getStartRegisterDate() {
		return startRegisterDate;
	}

	public void setStartRegisterDate(Date startRegisterDate) {
		this.startRegisterDate = startRegisterDate;
	}

	public Date getEndRegisterDate() {
		return endRegisterDate;
	}

	public void setEndRegisterDate(Date endRegisterDate) {
		this.endRegisterDate = endRegisterDate;
	}

	public void setCcUserid(Integer ccUserid) {
        this.ccUserid = ccUserid;
    }

    public String getCcCompanyName() {
        return ccCompanyName;
    }

    public void setCcCompanyName(String ccCompanyName) {
        this.ccCompanyName = ccCompanyName;
    }

    public String getCcNo() {
        return ccNo;
    }

    public void setCcNo(String ccNo) {
        this.ccNo = ccNo;
    }

    public Integer getCcCompanyType() {
        return ccCompanyType;
    }

    public void setCcCompanyType(Integer ccCompanyType) {
        this.ccCompanyType = ccCompanyType;
    }

    public Integer getCcCompanyScale() {
        return ccCompanyScale;
    }

    public void setCcCompanyScale(Integer ccCompanyScale) {
        this.ccCompanyScale = ccCompanyScale;
    }

    public BigDecimal getCcTotalCapital() {
        return ccTotalCapital;
    }

    public void setCcTotalCapital(BigDecimal ccTotalCapital) {
        this.ccTotalCapital = ccTotalCapital;
    }

    public BigDecimal getCcRegisterCapital() {
        return ccRegisterCapital;
    }

    public void setCcRegisterCapital(BigDecimal ccRegisterCapital) {
        this.ccRegisterCapital = ccRegisterCapital;
    }

    public Date getCcRegisterDate() {
        return ccRegisterDate;
    }

    public void setCcRegisterDate(Date ccRegisterDate) {
        this.ccRegisterDate = ccRegisterDate;
    }

    public String getCcRegisterAddress() {
        return ccRegisterAddress;
    }

    public void setCcRegisterAddress(String ccRegisterAddress) {
        this.ccRegisterAddress = ccRegisterAddress;
    }

    public String getCcContactName() {
        return ccContactName;
    }

    public void setCcContactName(String ccContactName) {
        this.ccContactName = ccContactName;
    }

    public String getCcContactPhone() {
        return ccContactPhone;
    }

    public void setCcContactPhone(String ccContactPhone) {
        this.ccContactPhone = ccContactPhone;
    }

    public String getCcContactAddress() {
        return ccContactAddress;
    }

    public void setCcContactAddress(String ccContactAddress) {
        this.ccContactAddress = ccContactAddress;
    }

    public String getCcCorporate() {
        return ccCorporate;
    }

    public void setCcCorporate(String ccCorporate) {
        this.ccCorporate = ccCorporate;
    }

    public String getCcDocType() {
        return ccDocType;
    }

    public void setCcDocType(String ccDocType) {
        this.ccDocType = ccDocType;
    }

    public String getCcDocNo() {
        return ccDocNo;
    }

    public void setCcDocNo(String ccDocNo) {
        this.ccDocNo = ccDocNo;
    }

    public String getCcCompanyAddress() {
        return ccCompanyAddress;
    }

    public void setCcCompanyAddress(String ccCompanyAddress) {
        this.ccCompanyAddress = ccCompanyAddress;
    }

    public String getCcBusinessLicense() {
        return ccBusinessLicense;
    }

    public void setCcBusinessLicense(String ccBusinessLicense) {
        this.ccBusinessLicense = ccBusinessLicense;
    }

    public String getCcLicenseNo() {
        return ccLicenseNo;
    }

    public void setCcLicenseNo(String ccLicenseNo) {
        this.ccLicenseNo = ccLicenseNo;
    }

    public String getCcCompanyProfile() {
        return ccCompanyProfile;
    }

    public void setCcCompanyProfile(String ccCompanyProfile) {
        this.ccCompanyProfile = ccCompanyProfile;
    }

    public String getCcCompanyAchievement() {
        return ccCompanyAchievement;
    }

    public void setCcCompanyAchievement(String ccCompanyAchievement) {
        this.ccCompanyAchievement = ccCompanyAchievement;
    }

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

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserDealPwd() {
        return userDealPwd;
    }

    public void setUserDealPwd(String userDealPwd) {
        this.userDealPwd = userDealPwd;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
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

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserNation() {
        return userNation;
    }

    public void setUserNation(String userNation) {
        this.userNation = userNation;
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

    public Integer getUserMarry() {
        return userMarry;
    }

    public void setUserMarry(Integer userMarry) {
        this.userMarry = userMarry;
    }

    public BigDecimal getUserMonthIncome() {
        return userMonthIncome;
    }

    public void setUserMonthIncome(BigDecimal userMonthIncome) {
        this.userMonthIncome = userMonthIncome;
    }

    public Integer getUserLoginCount() {
        return userLoginCount;
    }

    public void setUserLoginCount(Integer userLoginCount) {
        this.userLoginCount = userLoginCount;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public Date getUserScoreLastmodifytime() {
        return userScoreLastmodifytime;
    }

    public void setUserScoreLastmodifytime(Date userScoreLastmodifytime) {
        this.userScoreLastmodifytime = userScoreLastmodifytime;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public Integer getUserAutoInvest() {
        return userAutoInvest;
    }

    public void setUserAutoInvest(Integer userAutoInvest) {
        this.userAutoInvest = userAutoInvest;
    }

    public Integer getUserRecommendUserid() {
        return userRecommendUserid;
    }

    public void setUserRecommendUserid(Integer userRecommendUserid) {
        this.userRecommendUserid = userRecommendUserid;
    }

    public BigDecimal getUserLoanCredit() {
        return userLoanCredit;
    }

    public void setUserLoanCredit(BigDecimal userLoanCredit) {
        this.userLoanCredit = userLoanCredit;
    }

    public BigDecimal getUserCreditSett() {
        return userCreditSett;
    }

    public void setUserCreditSett(BigDecimal userCreditSett) {
        this.userCreditSett = userCreditSett;
    }

    public Date getUserRegisterDate() {
        return userRegisterDate;
    }

    public void setUserRegisterDate(Date userRegisterDate) {
        this.userRegisterDate = userRegisterDate;
    }

    public Integer getUserRealnameIsproven() {
        return userRealnameIsproven;
    }

    public void setUserRealnameIsproven(Integer userRealnameIsproven) {
        this.userRealnameIsproven = userRealnameIsproven;
    }

    public Integer getUserMobileIsbinded() {
        return userMobileIsbinded;
    }

    public void setUserMobileIsbinded(Integer userMobileIsbinded) {
        this.userMobileIsbinded = userMobileIsbinded;
    }

    public Integer getUserEmailIsbinded() {
        return userEmailIsbinded;
    }

    public void setUserEmailIsbinded(Integer userEmailIsbinded) {
        this.userEmailIsbinded = userEmailIsbinded;
    }

    public Integer getUserBankcardIsbinded() {
        return userBankcardIsbinded;
    }

    public void setUserBankcardIsbinded(Integer userBankcardIsbinded) {
        this.userBankcardIsbinded = userBankcardIsbinded;
    }

    public String getUserEmailVerifyCode() {
        return userEmailVerifyCode;
    }

    public void setUserEmailVerifyCode(String userEmailVerifyCode) {
        this.userEmailVerifyCode = userEmailVerifyCode;
    }

    public Date getUserEmailVerifyDate() {
        return userEmailVerifyDate;
    }

    public void setUserEmailVerifyDate(Date userEmailVerifyDate) {
        this.userEmailVerifyDate = userEmailVerifyDate;
    }

    public Date getUserMobileVerifyDate() {
        return userMobileVerifyDate;
    }

    public void setUserMobileVerifyDate(Date userMobileVerifyDate) {
        this.userMobileVerifyDate = userMobileVerifyDate;
    }

    public Integer getUserIsonline() {
        return userIsonline;
    }

    public void setUserIsonline(Integer userIsonline) {
        this.userIsonline = userIsonline;
    }

	public String getCcRegisterDateStr() {
		return ccRegisterDateStr;
	}

	public void setCcRegisterDateStr(String ccRegisterDateStr) {
		this.ccRegisterDateStr = ccRegisterDateStr;
	} 
}
