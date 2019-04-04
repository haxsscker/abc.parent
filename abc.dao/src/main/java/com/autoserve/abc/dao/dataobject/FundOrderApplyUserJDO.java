/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 类FundOrderApplyUserJDO.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月23日 下午6:48:03
 */
public class FundOrderApplyUserJDO {
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
    private Date       fcCheckDate;

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
    private Date       fcRechargeDate;

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
    private Date       foOrderDate;

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
    private Date       faAddDate;
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
    private Date       userRegisterDate;

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

    public Integer getFaFundId() {
        return faFundId;
    }

    public void setFaFundId(Integer faFundId) {
        this.faFundId = faFundId;
    }

    public String getFaFundNo() {
        return faFundNo;
    }

    public void setFaFundNo(String faFundNo) {
        this.faFundNo = faFundNo;
    }

    public String getFaFundName() {
        return faFundName;
    }

    public void setFaFundName(String faFundName) {
        this.faFundName = faFundName;
    }

    public String getFaFundComp() {
        return faFundComp;
    }

    public void setFaFundComp(String faFundComp) {
        this.faFundComp = faFundComp;
    }

    public BigDecimal getFaFundMoney() {
        return faFundMoney;
    }

    public void setFaFundMoney(BigDecimal faFundMoney) {
        this.faFundMoney = faFundMoney;
    }

    public BigDecimal getFaFundPeriod() {
        return faFundPeriod;
    }

    public void setFaFundPeriod(BigDecimal faFundPeriod) {
        this.faFundPeriod = faFundPeriod;
    }

    public BigDecimal getFaFundRate() {
        return faFundRate;
    }

    public void setFaFundRate(BigDecimal faFundRate) {
        this.faFundRate = faFundRate;
    }

    public BigDecimal getFaMinMoney() {
        return faMinMoney;
    }

    public void setFaMinMoney(BigDecimal faMinMoney) {
        this.faMinMoney = faMinMoney;
    }

    public String getFaFundIndustry() {
        return faFundIndustry;
    }

    public void setFaFundIndustry(String faFundIndustry) {
        this.faFundIndustry = faFundIndustry;
    }

    public Integer getFaPayType() {
        return faPayType;
    }

    public void setFaPayType(Integer faPayType) {
        this.faPayType = faPayType;
    }

    public String getFaFundType() {
        return faFundType;
    }

    public void setFaFundType(String faFundType) {
        this.faFundType = faFundType;
    }

    public BigDecimal getFaMartgageRate() {
        return faMartgageRate;
    }

    public void setFaMartgageRate(BigDecimal faMartgageRate) {
        this.faMartgageRate = faMartgageRate;
    }

    public String getFaMartgageIntrol() {
        return faMartgageIntrol;
    }

    public void setFaMartgageIntrol(String faMartgageIntrol) {
        this.faMartgageIntrol = faMartgageIntrol;
    }

    public String getFaFundIntrol() {
        return faFundIntrol;
    }

    public void setFaFundIntrol(String faFundIntrol) {
        this.faFundIntrol = faFundIntrol;
    }

    public String getFaFundUse() {
        return faFundUse;
    }

    public void setFaFundUse(String faFundUse) {
        this.faFundUse = faFundUse;
    }

    public String getFaFundRisk() {
        return faFundRisk;
    }

    public void setFaFundRisk(String faFundRisk) {
        this.faFundRisk = faFundRisk;
    }

    public String getFaPayResource() {
        return faPayResource;
    }

    public void setFaPayResource(String faPayResource) {
        this.faPayResource = faPayResource;
    }

    public String getFaProjectIntrol() {
        return faProjectIntrol;
    }

    public void setFaProjectIntrol(String faProjectIntrol) {
        this.faProjectIntrol = faProjectIntrol;
    }

    public String getFaCompIntrol() {
        return faCompIntrol;
    }

    public void setFaCompIntrol(String faCompIntrol) {
        this.faCompIntrol = faCompIntrol;
    }

    public Integer getFaFundState() {
        return faFundState;
    }

    public void setFaFundState(Integer faFundState) {
        this.faFundState = faFundState;
    }

    public Date getFaAddDate() {
        return faAddDate;
    }

    public void setFaAddDate(Date faAddDate) {
        this.faAddDate = faAddDate;
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

    public Integer getFcCheckId() {
        return fcCheckId;
    }

    public void setFcCheckId(Integer fcCheckId) {
        this.fcCheckId = fcCheckId;
    }

    public Integer getFcOrderId() {
        return fcOrderId;
    }

    public void setFcOrderId(Integer fcOrderId) {
        this.fcOrderId = fcOrderId;
    }

    public Integer getFcCheckEmp() {
        return fcCheckEmp;
    }

    public void setFcCheckEmp(Integer fcCheckEmp) {
        this.fcCheckEmp = fcCheckEmp;
    }

    public Date getFcCheckDate() {
        return fcCheckDate;
    }

    public void setFcCheckDate(Date fcCheckDate) {
        this.fcCheckDate = fcCheckDate;
    }

    public String getFcCheckIdear() {
        return fcCheckIdear;
    }

    public void setFcCheckIdear(String fcCheckIdear) {
        this.fcCheckIdear = fcCheckIdear;
    }

    public BigDecimal getFcRechargeMoney() {
        return fcRechargeMoney;
    }

    public void setFcRechargeMoney(BigDecimal fcRechargeMoney) {
        this.fcRechargeMoney = fcRechargeMoney;
    }

    public Date getFcRechargeDate() {
        return fcRechargeDate;
    }

    public void setFcRechargeDate(Date fcRechargeDate) {
        this.fcRechargeDate = fcRechargeDate;
    }

    public Integer getFcCheckState() {
        return fcCheckState;
    }

    public void setFcCheckState(Integer fcCheckState) {
        this.fcCheckState = fcCheckState;
    }

    public Integer getFoOrderId() {
        return foOrderId;
    }

    public void setFoOrderId(Integer foOrderId) {
        this.foOrderId = foOrderId;
    }

    public Integer getFoFundId() {
        return foFundId;
    }

    public void setFoFundId(Integer foFundId) {
        this.foFundId = foFundId;
    }

    public Integer getFoUserId() {
        return foUserId;
    }

    public void setFoUserId(Integer foUserId) {
        this.foUserId = foUserId;
    }

    public Date getFoOrderDate() {
        return foOrderDate;
    }

    public void setFoOrderDate(Date foOrderDate) {
        this.foOrderDate = foOrderDate;
    }

    public String getFoUserName() {
        return foUserName;
    }

    public void setFoUserName(String foUserName) {
        this.foUserName = foUserName;
    }

    public String getFoUserPhone() {
        return foUserPhone;
    }

    public void setFoUserPhone(String foUserPhone) {
        this.foUserPhone = foUserPhone;
    }

    public Integer getFoOrderState() {
        return foOrderState;
    }

    public void setFoOrderState(Integer foOrderState) {
        this.foOrderState = foOrderState;
    }

}
