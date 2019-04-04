/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.fund;

import java.math.BigDecimal;

/**
 * 类FundOrderApplyUserJVO.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月24日 下午8:01:52
 */
public class FundOrderApplyUserJVO {
    /**
     * 主键ID abc_fund_check.fc_check_id
     */
    private Integer    fcCheckId;

    /**
     * 预约ID(外键表：abc_fund_order) abc_fund_check.fc_order_id
     */
    private Integer    fcOrderId;

    /**
     * 审核人ID(外键表：abc_employee) abc_fund_check.fc_check_emp
     */
    private Integer    fcCheckEmp;

    /**
     * 审核日期 abc_fund_check.fc_check_date
     */
    private String     fcCheckDate;

    /**
     * 审核意见 abc_fund_check.fc_check_idear
     */
    private String     fcCheckIdear;

    /**
     * 充值金额 abc_fund_check.fc_recharge_money
     */
    private BigDecimal fcRechargeMoney;

    /**
     * 充值日期 abc_fund_check.fc_recharge_date
     */
    private String     fcRechargeDate;

    /**
     * 审核状态(1：已确认 2：已放弃) abc_fund_check.fc_check_state
     */
    private Integer    fcCheckState;

    /**
     * 主键ID abc_fund_order.fo_order_id
     */
    private Integer    foOrderId;

    /**
     * 有限合伙项目ID(外键表：abc_fund_apply) abc_fund_order.fo_fund_id
     */
    private Integer    foFundId;

    /**
     * 预约人 abc_fund_order.fo_user_id
     */
    private Integer    foUserId;

    /**
     * 预约日期 abc_fund_order.fo_order_date
     */
    private String     foOrderDate;

    /**
     * 预约人姓名 abc_fund_order.fo_user_name
     */
    private String     foUserName;

    /**
     * 预约人电话 abc_fund_order.fo_user_phone
     */
    private String     foUserPhone;

    /**
     * 预约状态(0：待审核 1：已确认 2：已放弃) abc_fund_order.fo_order_state
     */
    private Integer    foOrderState;

    /**
     * 主键ID abc_fund_apply.fa_fund_id
     */
    private Integer    faFundId;

    /**
     * 目项编号 abc_fund_apply.fa_fund_no
     */
    private String     faFundNo;

    /**
     * 基金名称 abc_fund_apply.fa_fund_name
     */
    private String     faFundName;

    /**
     * 基金公司 abc_fund_apply.fa_fund_comp
     */
    private String     faFundComp;

    /**
     * 预计发行规模 abc_fund_apply.fa_fund_money
     */
    private BigDecimal faFundMoney;

    /**
     * 存续期 abc_fund_apply.fa_fund_period
     */
    private BigDecimal faFundPeriod;

    /**
     * 预期年收益率 abc_fund_apply.fa_fund_rate
     */
    private BigDecimal faFundRate;

    /**
     * 最低认购金额 abc_fund_apply.fa_min_money
     */
    private BigDecimal faMinMoney;

    /**
     * 投资行业 abc_fund_apply.fa_fund_industry
     */
    private String     faFundIndustry;

    /**
     * 还款方式(1：等额本息 2：按月还息到期还本 3：等额本金) abc_fund_apply.fa_pay_type
     */
    private Integer    faPayType;

    /**
     * 基金类型 abc_fund_apply.fa_fund_type
     */
    private String     faFundType;

    /**
     * 抵押率 abc_fund_apply.fa_martgage_rate
     */
    private BigDecimal faMartgageRate;

    /**
     * 抵押物 abc_fund_apply.fa_martgage_introl
     */
    private String     faMartgageIntrol;

    /**
     * 产品说明 abc_fund_apply.fa_fund_introl
     */
    private String     faFundIntrol;

    /**
     * 资金用途 abc_fund_apply.fa_fund_use
     */
    private String     faFundUse;

    /**
     * 风险控制 abc_fund_apply.fa_fund_risk
     */
    private String     faFundRisk;

    /**
     * 还款来源 abc_fund_apply.fa_pay_resource
     */
    private String     faPayResource;

    /**
     * 项目简介 abc_fund_apply.fa_project_introl
     */
    private String     faProjectIntrol;

    /**
     * 基金公司简介 abc_fund_apply.fa_comp_introl
     */
    private String     faCompIntrol;

    /**
     * 是否发布 abc_fund_apply.fa_fund_state
     */
    private Integer    faFundState;

    /**
     * 发售日期 abc_fund_apply.fa_add_date
     */
    private String     faAddDate;
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
    private String     userBirthday;

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
    private String     userScoreLastmodifytime;

    /**
     * 是否启用 1：是 0：否 2:已删除 abc_user.user_state
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
    private String     userRegisterDate;

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
    private String     userEmailVerifyDate;

    /**
     * 手机认证日期 abc_user.user_mobile_verify_date
     */
    private String     userMobileVerifyDate;

    /**
     * 1：已在线 0：未在线 abc_user.user_isonline
     */
    private Integer    userIsonline;

    /**
     * @return the fcCheckId
     */
    public Integer getFcCheckId() {
        return fcCheckId;
    }

    /**
     * @param fcCheckId the fcCheckId to set
     */
    public void setFcCheckId(Integer fcCheckId) {
        this.fcCheckId = fcCheckId;
    }

    /**
     * @return the fcOrderId
     */
    public Integer getFcOrderId() {
        return fcOrderId;
    }

    /**
     * @param fcOrderId the fcOrderId to set
     */
    public void setFcOrderId(Integer fcOrderId) {
        this.fcOrderId = fcOrderId;
    }

    /**
     * @return the fcCheckEmp
     */
    public Integer getFcCheckEmp() {
        return fcCheckEmp;
    }

    /**
     * @param fcCheckEmp the fcCheckEmp to set
     */
    public void setFcCheckEmp(Integer fcCheckEmp) {
        this.fcCheckEmp = fcCheckEmp;
    }

    /**
     * @return the fcCheckDate
     */
    public String getFcCheckDate() {
        return fcCheckDate;
    }

    /**
     * @param fcCheckDate the fcCheckDate to set
     */
    public void setFcCheckDate(String fcCheckDate) {
        this.fcCheckDate = fcCheckDate;
    }

    /**
     * @return the fcCheckIdear
     */
    public String getFcCheckIdear() {
        return fcCheckIdear;
    }

    /**
     * @param fcCheckIdear the fcCheckIdear to set
     */
    public void setFcCheckIdear(String fcCheckIdear) {
        this.fcCheckIdear = fcCheckIdear;
    }

    /**
     * @return the fcRechargeMoney
     */
    public BigDecimal getFcRechargeMoney() {
        return fcRechargeMoney;
    }

    /**
     * @param fcRechargeMoney the fcRechargeMoney to set
     */
    public void setFcRechargeMoney(BigDecimal fcRechargeMoney) {
        this.fcRechargeMoney = fcRechargeMoney;
    }

    /**
     * @return the fcRechargeDate
     */
    public String getFcRechargeDate() {
        return fcRechargeDate;
    }

    /**
     * @param fcRechargeDate the fcRechargeDate to set
     */
    public void setFcRechargeDate(String fcRechargeDate) {
        this.fcRechargeDate = fcRechargeDate;
    }

    /**
     * @return the fcCheckState
     */
    public Integer getFcCheckState() {
        return fcCheckState;
    }

    /**
     * @param fcCheckState the fcCheckState to set
     */
    public void setFcCheckState(Integer fcCheckState) {
        this.fcCheckState = fcCheckState;
    }

    /**
     * @return the foOrderId
     */
    public Integer getFoOrderId() {
        return foOrderId;
    }

    /**
     * @param foOrderId the foOrderId to set
     */
    public void setFoOrderId(Integer foOrderId) {
        this.foOrderId = foOrderId;
    }

    /**
     * @return the foFundId
     */
    public Integer getFoFundId() {
        return foFundId;
    }

    /**
     * @param foFundId the foFundId to set
     */
    public void setFoFundId(Integer foFundId) {
        this.foFundId = foFundId;
    }

    /**
     * @return the foUserId
     */
    public Integer getFoUserId() {
        return foUserId;
    }

    /**
     * @param foUserId the foUserId to set
     */
    public void setFoUserId(Integer foUserId) {
        this.foUserId = foUserId;
    }

    /**
     * @return the foOrderDate
     */
    public String getFoOrderDate() {
        return foOrderDate;
    }

    /**
     * @param foOrderDate the foOrderDate to set
     */
    public void setFoOrderDate(String foOrderDate) {
        this.foOrderDate = foOrderDate;
    }

    /**
     * @return the foUserName
     */
    public String getFoUserName() {
        return foUserName;
    }

    /**
     * @param foUserName the foUserName to set
     */
    public void setFoUserName(String foUserName) {
        this.foUserName = foUserName;
    }

    /**
     * @return the foUserPhone
     */
    public String getFoUserPhone() {
        return foUserPhone;
    }

    /**
     * @param foUserPhone the foUserPhone to set
     */
    public void setFoUserPhone(String foUserPhone) {
        this.foUserPhone = foUserPhone;
    }

    /**
     * @return the foOrderState
     */
    public Integer getFoOrderState() {
        return foOrderState;
    }

    /**
     * @param foOrderState the foOrderState to set
     */
    public void setFoOrderState(Integer foOrderState) {
        this.foOrderState = foOrderState;
    }

    /**
     * @return the faFundId
     */
    public Integer getFaFundId() {
        return faFundId;
    }

    /**
     * @param faFundId the faFundId to set
     */
    public void setFaFundId(Integer faFundId) {
        this.faFundId = faFundId;
    }

    /**
     * @return the faFundNo
     */
    public String getFaFundNo() {
        return faFundNo;
    }

    /**
     * @param faFundNo the faFundNo to set
     */
    public void setFaFundNo(String faFundNo) {
        this.faFundNo = faFundNo;
    }

    /**
     * @return the faFundName
     */
    public String getFaFundName() {
        return faFundName;
    }

    /**
     * @param faFundName the faFundName to set
     */
    public void setFaFundName(String faFundName) {
        this.faFundName = faFundName;
    }

    /**
     * @return the faFundComp
     */
    public String getFaFundComp() {
        return faFundComp;
    }

    /**
     * @param faFundComp the faFundComp to set
     */
    public void setFaFundComp(String faFundComp) {
        this.faFundComp = faFundComp;
    }

    /**
     * @return the faFundMoney
     */
    public BigDecimal getFaFundMoney() {
        return faFundMoney;
    }

    /**
     * @param faFundMoney the faFundMoney to set
     */
    public void setFaFundMoney(BigDecimal faFundMoney) {
        this.faFundMoney = faFundMoney;
    }

    /**
     * @return the faFundPeriod
     */
    public BigDecimal getFaFundPeriod() {
        return faFundPeriod;
    }

    /**
     * @param faFundPeriod the faFundPeriod to set
     */
    public void setFaFundPeriod(BigDecimal faFundPeriod) {
        this.faFundPeriod = faFundPeriod;
    }

    /**
     * @return the faFundRate
     */
    public BigDecimal getFaFundRate() {
        return faFundRate;
    }

    /**
     * @param faFundRate the faFundRate to set
     */
    public void setFaFundRate(BigDecimal faFundRate) {
        this.faFundRate = faFundRate;
    }

    /**
     * @return the faMinMoney
     */
    public BigDecimal getFaMinMoney() {
        return faMinMoney;
    }

    /**
     * @param faMinMoney the faMinMoney to set
     */
    public void setFaMinMoney(BigDecimal faMinMoney) {
        this.faMinMoney = faMinMoney;
    }

    /**
     * @return the faFundIndustry
     */
    public String getFaFundIndustry() {
        return faFundIndustry;
    }

    /**
     * @param faFundIndustry the faFundIndustry to set
     */
    public void setFaFundIndustry(String faFundIndustry) {
        this.faFundIndustry = faFundIndustry;
    }

    /**
     * @return the faPayType
     */
    public Integer getFaPayType() {
        return faPayType;
    }

    /**
     * @param faPayType the faPayType to set
     */
    public void setFaPayType(Integer faPayType) {
        this.faPayType = faPayType;
    }

    /**
     * @return the faFundType
     */
    public String getFaFundType() {
        return faFundType;
    }

    /**
     * @param faFundType the faFundType to set
     */
    public void setFaFundType(String faFundType) {
        this.faFundType = faFundType;
    }

    /**
     * @return the faMartgageRate
     */
    public BigDecimal getFaMartgageRate() {
        return faMartgageRate;
    }

    /**
     * @param faMartgageRate the faMartgageRate to set
     */
    public void setFaMartgageRate(BigDecimal faMartgageRate) {
        this.faMartgageRate = faMartgageRate;
    }

    /**
     * @return the faMartgageIntrol
     */
    public String getFaMartgageIntrol() {
        return faMartgageIntrol;
    }

    /**
     * @param faMartgageIntrol the faMartgageIntrol to set
     */
    public void setFaMartgageIntrol(String faMartgageIntrol) {
        this.faMartgageIntrol = faMartgageIntrol;
    }

    /**
     * @return the faFundIntrol
     */
    public String getFaFundIntrol() {
        return faFundIntrol;
    }

    /**
     * @param faFundIntrol the faFundIntrol to set
     */
    public void setFaFundIntrol(String faFundIntrol) {
        this.faFundIntrol = faFundIntrol;
    }

    /**
     * @return the faFundUse
     */
    public String getFaFundUse() {
        return faFundUse;
    }

    /**
     * @param faFundUse the faFundUse to set
     */
    public void setFaFundUse(String faFundUse) {
        this.faFundUse = faFundUse;
    }

    /**
     * @return the faFundRisk
     */
    public String getFaFundRisk() {
        return faFundRisk;
    }

    /**
     * @param faFundRisk the faFundRisk to set
     */
    public void setFaFundRisk(String faFundRisk) {
        this.faFundRisk = faFundRisk;
    }

    /**
     * @return the faPayResource
     */
    public String getFaPayResource() {
        return faPayResource;
    }

    /**
     * @param faPayResource the faPayResource to set
     */
    public void setFaPayResource(String faPayResource) {
        this.faPayResource = faPayResource;
    }

    /**
     * @return the faProjectIntrol
     */
    public String getFaProjectIntrol() {
        return faProjectIntrol;
    }

    /**
     * @param faProjectIntrol the faProjectIntrol to set
     */
    public void setFaProjectIntrol(String faProjectIntrol) {
        this.faProjectIntrol = faProjectIntrol;
    }

    /**
     * @return the faCompIntrol
     */
    public String getFaCompIntrol() {
        return faCompIntrol;
    }

    /**
     * @param faCompIntrol the faCompIntrol to set
     */
    public void setFaCompIntrol(String faCompIntrol) {
        this.faCompIntrol = faCompIntrol;
    }

    /**
     * @return the faFundState
     */
    public Integer getFaFundState() {
        return faFundState;
    }

    /**
     * @param faFundState the faFundState to set
     */
    public void setFaFundState(Integer faFundState) {
        this.faFundState = faFundState;
    }

    /**
     * @return the faAddDate
     */
    public String getFaAddDate() {
        return faAddDate;
    }

    /**
     * @param faAddDate the faAddDate to set
     */
    public void setFaAddDate(String faAddDate) {
        this.faAddDate = faAddDate;
    }

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userRealName
     */
    public String getUserRealName() {
        return userRealName;
    }

    /**
     * @param userRealName the userRealName to set
     */
    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    /**
     * @return the userPwd
     */
    public String getUserPwd() {
        return userPwd;
    }

    /**
     * @param userPwd the userPwd to set
     */
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    /**
     * @return the userDealPwd
     */
    public String getUserDealPwd() {
        return userDealPwd;
    }

    /**
     * @param userDealPwd the userDealPwd to set
     */
    public void setUserDealPwd(String userDealPwd) {
        this.userDealPwd = userDealPwd;
    }

    /**
     * @return the userType
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * @return the userDocType
     */
    public String getUserDocType() {
        return userDocType;
    }

    /**
     * @param userDocType the userDocType to set
     */
    public void setUserDocType(String userDocType) {
        this.userDocType = userDocType;
    }

    /**
     * @return the userDocNo
     */
    public String getUserDocNo() {
        return userDocNo;
    }

    /**
     * @param userDocNo the userDocNo to set
     */
    public void setUserDocNo(String userDocNo) {
        this.userDocNo = userDocNo;
    }

    /**
     * @return the userSex
     */
    public Integer getUserSex() {
        return userSex;
    }

    /**
     * @param userSex the userSex to set
     */
    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    /**
     * @return the userBirthday
     */
    public String getUserBirthday() {
        return userBirthday;
    }

    /**
     * @param userBirthday the userBirthday to set
     */
    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    /**
     * @return the userNation
     */
    public String getUserNation() {
        return userNation;
    }

    /**
     * @param userNation the userNation to set
     */
    public void setUserNation(String userNation) {
        this.userNation = userNation;
    }

    /**
     * @return the userNative
     */
    public String getUserNative() {
        return userNative;
    }

    /**
     * @param userNative the userNative to set
     */
    public void setUserNative(String userNative) {
        this.userNative = userNative;
    }

    /**
     * @return the userPhone
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * @param userPhone the userPhone to set
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * @return the userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @return the userMarry
     */
    public Integer getUserMarry() {
        return userMarry;
    }

    /**
     * @param userMarry the userMarry to set
     */
    public void setUserMarry(Integer userMarry) {
        this.userMarry = userMarry;
    }

    /**
     * @return the userMonthIncome
     */
    public BigDecimal getUserMonthIncome() {
        return userMonthIncome;
    }

    /**
     * @param userMonthIncome the userMonthIncome to set
     */
    public void setUserMonthIncome(BigDecimal userMonthIncome) {
        this.userMonthIncome = userMonthIncome;
    }

    /**
     * @return the userLoginCount
     */
    public Integer getUserLoginCount() {
        return userLoginCount;
    }

    /**
     * @param userLoginCount the userLoginCount to set
     */
    public void setUserLoginCount(Integer userLoginCount) {
        this.userLoginCount = userLoginCount;
    }

    /**
     * @return the userHeadImg
     */
    public String getUserHeadImg() {
        return userHeadImg;
    }

    /**
     * @param userHeadImg the userHeadImg to set
     */
    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    /**
     * @return the userScore
     */
    public Integer getUserScore() {
        return userScore;
    }

    /**
     * @param userScore the userScore to set
     */
    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    /**
     * @return the userScoreLastmodifytime
     */
    public String getUserScoreLastmodifytime() {
        return userScoreLastmodifytime;
    }

    /**
     * @param userScoreLastmodifytime the userScoreLastmodifytime to set
     */
    public void setUserScoreLastmodifytime(String userScoreLastmodifytime) {
        this.userScoreLastmodifytime = userScoreLastmodifytime;
    }

    /**
     * @return the userState
     */
    public Integer getUserState() {
        return userState;
    }

    /**
     * @param userState the userState to set
     */
    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    /**
     * @return the userAutoInvest
     */
    public Integer getUserAutoInvest() {
        return userAutoInvest;
    }

    /**
     * @param userAutoInvest the userAutoInvest to set
     */
    public void setUserAutoInvest(Integer userAutoInvest) {
        this.userAutoInvest = userAutoInvest;
    }

    /**
     * @return the userRecommendUserid
     */
    public Integer getUserRecommendUserid() {
        return userRecommendUserid;
    }

    /**
     * @param userRecommendUserid the userRecommendUserid to set
     */
    public void setUserRecommendUserid(Integer userRecommendUserid) {
        this.userRecommendUserid = userRecommendUserid;
    }

    /**
     * @return the userLoanCredit
     */
    public BigDecimal getUserLoanCredit() {
        return userLoanCredit;
    }

    /**
     * @param userLoanCredit the userLoanCredit to set
     */
    public void setUserLoanCredit(BigDecimal userLoanCredit) {
        this.userLoanCredit = userLoanCredit;
    }

    /**
     * @return the userCreditSett
     */
    public BigDecimal getUserCreditSett() {
        return userCreditSett;
    }

    /**
     * @param userCreditSett the userCreditSett to set
     */
    public void setUserCreditSett(BigDecimal userCreditSett) {
        this.userCreditSett = userCreditSett;
    }

    /**
     * @return the userRegisterDate
     */
    public String getUserRegisterDate() {
        return userRegisterDate;
    }

    /**
     * @param userRegisterDate the userRegisterDate to set
     */
    public void setUserRegisterDate(String userRegisterDate) {
        this.userRegisterDate = userRegisterDate;
    }

    /**
     * @return the userRealnameIsproven
     */
    public Integer getUserRealnameIsproven() {
        return userRealnameIsproven;
    }

    /**
     * @param userRealnameIsproven the userRealnameIsproven to set
     */
    public void setUserRealnameIsproven(Integer userRealnameIsproven) {
        this.userRealnameIsproven = userRealnameIsproven;
    }

    /**
     * @return the userMobileIsbinded
     */
    public Integer getUserMobileIsbinded() {
        return userMobileIsbinded;
    }

    /**
     * @param userMobileIsbinded the userMobileIsbinded to set
     */
    public void setUserMobileIsbinded(Integer userMobileIsbinded) {
        this.userMobileIsbinded = userMobileIsbinded;
    }

    /**
     * @return the userEmailIsbinded
     */
    public Integer getUserEmailIsbinded() {
        return userEmailIsbinded;
    }

    /**
     * @param userEmailIsbinded the userEmailIsbinded to set
     */
    public void setUserEmailIsbinded(Integer userEmailIsbinded) {
        this.userEmailIsbinded = userEmailIsbinded;
    }

    /**
     * @return the userBankcardIsbinded
     */
    public Integer getUserBankcardIsbinded() {
        return userBankcardIsbinded;
    }

    /**
     * @param userBankcardIsbinded the userBankcardIsbinded to set
     */
    public void setUserBankcardIsbinded(Integer userBankcardIsbinded) {
        this.userBankcardIsbinded = userBankcardIsbinded;
    }

    /**
     * @return the userEmailVerifyCode
     */
    public String getUserEmailVerifyCode() {
        return userEmailVerifyCode;
    }

    /**
     * @param userEmailVerifyCode the userEmailVerifyCode to set
     */
    public void setUserEmailVerifyCode(String userEmailVerifyCode) {
        this.userEmailVerifyCode = userEmailVerifyCode;
    }

    /**
     * @return the userEmailVerifyDate
     */
    public String getUserEmailVerifyDate() {
        return userEmailVerifyDate;
    }

    /**
     * @param userEmailVerifyDate the userEmailVerifyDate to set
     */
    public void setUserEmailVerifyDate(String userEmailVerifyDate) {
        this.userEmailVerifyDate = userEmailVerifyDate;
    }

    /**
     * @return the userMobileVerifyDate
     */
    public String getUserMobileVerifyDate() {
        return userMobileVerifyDate;
    }

    /**
     * @param userMobileVerifyDate the userMobileVerifyDate to set
     */
    public void setUserMobileVerifyDate(String userMobileVerifyDate) {
        this.userMobileVerifyDate = userMobileVerifyDate;
    }

    /**
     * @return the userIsonline
     */
    public Integer getUserIsonline() {
        return userIsonline;
    }

    /**
     * @param userIsonline the userIsonline to set
     */
    public void setUserIsonline(Integer userIsonline) {
        this.userIsonline = userIsonline;
    }

}
