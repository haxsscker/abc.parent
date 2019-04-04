package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * web层 红包JDO实体
 * 
 * @author lipeng 2014年12月26日 下午8:05:34
 */
public class RedsendJDO {
    /* 红包发送表字段 开始 */
    /**
     * 发放的红包记录编号 abc_red_send.rs_id
     */
    private Integer    rsId;

    /**
     * 红包机制 abc_red abc_red_send.rs_red_id
     */
    private Integer    rsRedId;

    /**
     * 红包发放金额 abc_red_send.rs_amt
     */
    private Double     rsAmt;

    /**
     * 红包奖励主题 abc_red_send.rs_theme
     */
    private String     rsTheme;

    /**
     * 红包有效金额 abc_red_send.rs_valid_amount
     */
    private Double     rsValidAmount;

    /**
     * 红包奖励类型：1：个人奖励；2：项目奖励 abc_red_send.rs_type
     */
    private Integer    rsType;

    /**
     * 红包用户id abc_red_send.rs_userid
     */
    private Integer    rsUserid;

    /**
     * 项目id abc_red_send.rs_loanid
     */
    private Integer    rsBizid;

    /**
     * 使用范围（如：投资|受让|收购）多个产品范围时，用“|”分割 abc_red_send.rs_use_scope
     */
    private String     rsUseScope;

    /**
     * 红包有效使用期限 即发送红包后的有效使用时间 单位/月 abc_red_send.rs_lifetime
     */
    private Integer    rsLifetime;

    /**
     * 投资奖励自动发放条件 abc_red_send.rs_invest_amount
     */
    private Double     rsInvestAmount;

    /**
     * 红包开始有效时间 abc_red_send.rs_starttime
     */
    private Date       rsStarttime;

    /**
     * 红包失效时间 abc_red_send.rs_closetime
     */
    private Date       rsClosetime;

    /**
     * 红包发放时间 abc_red_send.rs_sendtime
     */
    private Date       rsSendtime;

    /**
     * 发放红包的用户id abc_user abc_red_send.rs_sender
     */
    private Integer    rsSender;

    /**
     * 红包使用状态 : 0:失效 1:未使用，2：已使用 abc_red_send.rs_state
     */
    private Integer    rsState;

    /**
     * @author fangrui 2015年01月04日
     *         红包发放数，当红包为个人奖励类型时，记录个人奖励红包数；当红包为项目奖励类型时，记录项目奖励红包数
     *         abc_red_send.rs_reward_number
     */
    private Integer    redRewardNumber;
    /* 红包发送表字段 结束 */

    /* 红包表字段 开始 */
    /**
     * 红包名称 abc_red.red_name
     */
    private String     redName;
    /**
     * 红包类型 1：新手专享红包 2：新手特权红包 3：成功进阶红包 4：项目奖励红包 5：个人奖励红包 6：推荐邀请红包
     * abc_red.red_type
     */
    private Integer    redType;
    /**
     * 红包机制状态 0：无效 1：有效 abc_red.redenvelope_state
     */
    private Integer    redState;
    /**
     * 红包金额 abc_red.red_amount
     */
    private Double     redAmount;

    /* 红包表新加字段 开始 */
    /**
     * 红包发放金额 abc_red.red_amt
     */
    private Double     redAmt;

    /**
     * 红包主题 abc_red.red_theme
     */
    private String     redTheme;

    /**
     * 使用范围 abc_red.red_use_scope
     */
    private String     redUseScope;

    /**
     * 项目id abc_red.red_bizid
     */
    private Integer    redBizid;

    /**
     * 有效日期(红包失效时间) abc_red.red_closetime
     */
    private Date       redClosetime;

    /**
     * 红包发放时间 abc_red.red_sendtime
     */
    private Date       redSendtime;
    /* 红包表新加字段 结束 */
    /* 红包表字段 结束 */

    /* user表开始 */
    /**
     * 用户名 abc_user.user_name
     */
    private String     userName;
    /**
     * 用户类型 1：个人 2：企业 abc_user.user_type
     */
    private Integer    userType;
    /**
     * 是否启用 1：是 0：否 2:已删除 abc_user.user_state
     */
    private Integer    userState;
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
     * 业务相关状态 1：注册成功 2：账户已开户 3：已充值 4：已投资
     */
    private Integer    userBusinessState;
    /* user表结束 */

    /* 红包使用表开始 */
    /**
     * 红包使用时间 abc_red_use.ru_usetime
     */
    private Date       ruUsetime;

    /**
     * 红包使用描述 abc_red_use.ru_desc
     */
    private String     ruDesc;

    /**
     * 红包使用金额 abc_red_use.ru_amount
     */
    private Double     ruAmount;

    /**
     * 红包剩余金额 abc_red_use.ru_remainder_amount
     */
    private Double     ruRemainderAmount;

    /**
     * 抵扣手续费 abc_red_use.ru_deduct_fee
     */
    private Double     ruDeductFee;

    /**
     * 抵扣折让费 abc_red_use.ru_deduct_discount
     */
    private Double     ruDeductDiscount;

    /**
     * 红包使用次数 abc_red_use.ru_usecount
     */
    private Integer    ruUsecount;

    /**
     * @author fangrui 2015年0月04日 总计奖励金额 (冗余字段)
     */
    private Double     totalAmount;
    /* 红包使用表结束 */

    /* 邀请表字段开始 */
    /**
     * abc_invite.invite_id
     */
    private Integer    inviteId;

    /**
     * 邀请人id abc_invite.invite_user_id
     */
    private Integer    inviteUserId;

    /**
     * 被邀请人id abc_invite.invite_invitee_id
     */
    private Integer    inviteInviteeId;

    /**
     * 邀请人类型 1：前台网友 2：平台用户 abc_invite.invite_user_type
     */
    private Integer    inviteUserType;

    /**
     * 是否已生效 0：未生效 1：已生效 abc_invite.invite_is_valid
     */
    private Integer    inviteIsValid;

    /**
     * 开始日期 abc_invite.invite_start_date
     */
    private Date       inviteStartDate;

    /**
     * 到期日期 abc_invite.invite_end_date
     */
    private Date       inviteEndDate;

    /**
     * 奖励邀请人金额 abc_invite.invite_reward_money
     */
    private BigDecimal inviteRewardMoney;

    /**
     * 是否已使用 0：未使用 1：已使用 abc_invite.invite_reward_state
     */
    private Integer    inviteRewardState;

    private Date       inviteCreateTime;

    private Date       inviteLastModifyTime;

    /**
     * 被邀请人名称 （冗余字段）
     */
    private String     inviteeName;

    /* 邀请表字段结束 */

    /**
     * @return abc_red_send.rs_id
     */
    public Integer getRsId() {
        return rsId;
    }

    /**
     * @param Integer rsId (abc_red_send.rs_id )
     */
    public void setRsId(Integer rsId) {
        this.rsId = rsId;
    }

    /**
     * @return abc_red_send.rs_red_id
     */
    public Integer getRsRedId() {
        return rsRedId;
    }

    /**
     * @param Integer rsRedId (abc_red_send.rs_red_id )
     */
    public void setRsRedId(Integer rsRedId) {
        this.rsRedId = rsRedId;
    }

    /**
     * @return abc_red_send.rs_amt
     */
    public Double getRsAmt() {
        return rsAmt;
    }

    /**
     * @param BigDecimal rsAmt (abc_red_send.rs_amt )
     */
    public void setRsAmt(Double rsAmt) {
        this.rsAmt = rsAmt;
    }

    /**
     * @return abc_red_send.rs_theme
     */
    public String getRsTheme() {
        return rsTheme;
    }

    /**
     * @param String rsTheme (abc_red_send.rs_theme )
     */
    public void setRsTheme(String rsTheme) {
        this.rsTheme = rsTheme;
    }

    /**
     * @return abc_red_send.rs_valid_amount
     */
    public Double getRsValidAmount() {
        return rsValidAmount;
    }

    /**
     * @param BigDecimal rsValidAmount (abc_red_send.rs_valid_amount )
     */
    public void setRsValidAmount(Double rsValidAmount) {
        this.rsValidAmount = rsValidAmount;
    }

    /**
     * @return abc_red_send.rs_type
     */
    public Integer getRsType() {
        return rsType;
    }

    /**
     * @param Boolean rsType (abc_red_send.rs_type )
     */
    public void setRsType(Integer rsType) {
        this.rsType = rsType;
    }

    /**
     * @return abc_red_send.rs_userid
     */
    public Integer getRsUserid() {
        return rsUserid;
    }

    /**
     * @param Integer rsUserid (abc_red_send.rs_userid )
     */
    public void setRsUserid(Integer rsUserid) {
        this.rsUserid = rsUserid;
    }

    /**
     * @return abc_red_send.rs_use_scope
     */
    public String getRsUseScope() {
        return rsUseScope;
    }

    /**
     * @param String rsUseScope (abc_red_send.rs_use_scope )
     */
    public void setRsUseScope(String rsUseScope) {
        this.rsUseScope = rsUseScope;
    }

    /**
     * @return abc_red_send.rs_lifetime
     */
    public Integer getRsLifetime() {
        return rsLifetime;
    }

    /**
     * @param Integer rsLifetime (abc_red_send.rs_lifetime )
     */
    public void setRsLifetime(Integer rsLifetime) {
        this.rsLifetime = rsLifetime;
    }

    /**
     * @return abc_red_send.rs_invest_amount
     */
    public Double getRsInvestAmount() {
        return rsInvestAmount;
    }

    /**
     * @param BigDecimal rsInvestAmount (abc_red_send.rs_invest_amount )
     */
    public void setRsInvestAmount(Double rsInvestAmount) {
        this.rsInvestAmount = rsInvestAmount;
    }

    /**
     * @return abc_red_send.rs_starttime
     */
    public Date getRsStarttime() {
        return rsStarttime;
    }

    /**
     * @param Date rsStarttime (abc_red_send.rs_starttime )
     */
    public void setRsStarttime(Date rsStarttime) {
        this.rsStarttime = rsStarttime;
    }

    /**
     * @return abc_red_send.rs_closetime
     */
    public Date getRsClosetime() {
        return rsClosetime;
    }

    /**
     * @param Date rsClosetime (abc_red_send.rs_closetime )
     */
    public void setRsClosetime(Date rsClosetime) {
        this.rsClosetime = rsClosetime;
    }

    /**
     * @return abc_red_send.rs_sendtime
     */
    public Date getRsSendtime() {
        return rsSendtime;
    }

    /**
     * @param Date rsSendtime (abc_red_send.rs_sendtime )
     */
    public void setRsSendtime(Date rsSendtime) {
        this.rsSendtime = rsSendtime;
    }

    /**
     * @return abc_red_send.rs_sender
     */
    public Integer getRsSender() {
        return rsSender;
    }

    /**
     * @param Integer rsSender (abc_red_send.rs_sender )
     */
    public void setRsSender(Integer rsSender) {
        this.rsSender = rsSender;
    }

    /**
     * @return abc_red_send.rs_state
     */
    public Integer getRsState() {
        return rsState;
    }

    /**
     * @param Boolean rsState (abc_red_send.rs_state )
     */
    public void setRsState(Integer rsState) {
        this.rsState = rsState;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRedName() {
        return redName;
    }

    public void setRedName(String redName) {
        this.redName = redName;
    }

    public Integer getRedType() {
        return redType;
    }

    public void setRedType(Integer redType) {
        this.redType = redType;
    }

    public Double getRedAmount() {
        return redAmount;
    }

    public void setRedAmount(Double redAmount) {
        this.redAmount = redAmount;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public Integer getRedState() {
        return redState;
    }

    public void setRedState(Integer redState) {
        this.redState = redState;
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

    public Date getRuUsetime() {
        return ruUsetime;
    }

    public void setRuUsetime(Date ruUsetime) {
        this.ruUsetime = ruUsetime;
    }

    public String getRuDesc() {
        return ruDesc;
    }

    public void setRuDesc(String ruDesc) {
        this.ruDesc = ruDesc;
    }

    public Double getRuAmount() {
        return ruAmount;
    }

    public void setRuAmount(Double ruAmount) {
        this.ruAmount = ruAmount;
    }

    public Double getRuRemainderAmount() {
        return ruRemainderAmount;
    }

    public void setRuRemainderAmount(Double ruRemainderAmount) {
        this.ruRemainderAmount = ruRemainderAmount;
    }

    public Double getRuDeductFee() {
        return ruDeductFee;
    }

    public void setRuDeductFee(Double ruDeductFee) {
        this.ruDeductFee = ruDeductFee;
    }

    public Double getRuDeductDiscount() {
        return ruDeductDiscount;
    }

    public void setRuDeductDiscount(Double ruDeductDiscount) {
        this.ruDeductDiscount = ruDeductDiscount;
    }

    public Integer getRuUsecount() {
        return ruUsecount;
    }

    public void setRuUsecount(Integer ruUsecount) {
        this.ruUsecount = ruUsecount;
    }

    public Integer getRsBizid() {
        return rsBizid;
    }

    public void setRsBizid(Integer rsBizid) {
        this.rsBizid = rsBizid;
    }

    public Integer getRedRewardNumber() {
        return redRewardNumber;
    }

    public void setRedRewardNumber(Integer redRewardNumber) {
        this.redRewardNumber = redRewardNumber;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getInviteId() {
        return inviteId;
    }

    public void setInviteId(Integer inviteId) {
        this.inviteId = inviteId;
    }

    public Integer getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(Integer inviteUserId) {
        this.inviteUserId = inviteUserId;
    }

    public Integer getInviteInviteeId() {
        return inviteInviteeId;
    }

    public void setInviteInviteeId(Integer inviteInviteeId) {
        this.inviteInviteeId = inviteInviteeId;
    }

    public Integer getInviteUserType() {
        return inviteUserType;
    }

    public void setInviteUserType(Integer inviteUserType) {
        this.inviteUserType = inviteUserType;
    }

    public Integer getInviteIsValid() {
        return inviteIsValid;
    }

    public void setInviteIsValid(Integer inviteIsValid) {
        this.inviteIsValid = inviteIsValid;
    }

    public Date getInviteStartDate() {
        return inviteStartDate;
    }

    public void setInviteStartDate(Date inviteStartDate) {
        this.inviteStartDate = inviteStartDate;
    }

    public Date getInviteEndDate() {
        return inviteEndDate;
    }

    public void setInviteEndDate(Date inviteEndDate) {
        this.inviteEndDate = inviteEndDate;
    }

    public BigDecimal getInviteRewardMoney() {
        return inviteRewardMoney;
    }

    public void setInviteRewardMoney(BigDecimal inviteRewardMoney) {
        this.inviteRewardMoney = inviteRewardMoney;
    }

    public Integer getInviteRewardState() {
        return inviteRewardState;
    }

    public void setInviteRewardState(Integer inviteRewardState) {
        this.inviteRewardState = inviteRewardState;
    }

    public Date getInviteCreateTime() {
        return inviteCreateTime;
    }

    public void setInviteCreateTime(Date inviteCreateTime) {
        this.inviteCreateTime = inviteCreateTime;
    }

    public Date getInviteLastModifyTime() {
        return inviteLastModifyTime;
    }

    public void setInviteLastModifyTime(Date inviteLastModifyTime) {
        this.inviteLastModifyTime = inviteLastModifyTime;
    }

    public Double getRedAmt() {
        return redAmt;
    }

    public void setRedAmt(Double redAmt) {
        this.redAmt = redAmt;
    }

    public String getRedTheme() {
        return redTheme;
    }

    public void setRedTheme(String redTheme) {
        this.redTheme = redTheme;
    }

    public String getRedUseScope() {
        return redUseScope;
    }

    public void setRedUseScope(String redUseScope) {
        this.redUseScope = redUseScope;
    }

    public Integer getRedBizid() {
        return redBizid;
    }

    public void setRedBizid(Integer redBizid) {
        this.redBizid = redBizid;
    }

    public Date getRedClosetime() {
        return redClosetime;
    }

    public void setRedClosetime(Date redClosetime) {
        this.redClosetime = redClosetime;
    }

    public Date getRedSendtime() {
        return redSendtime;
    }

    public void setRedSendtime(Date redSendtime) {
        this.redSendtime = redSendtime;
    }

    public String getInviteeName() {
        return inviteeName;
    }

    public void setInviteeName(String inviteeName) {
        this.inviteeName = inviteeName;
    }

    public Integer getUserBusinessState() {
        return userBusinessState;
    }

    public void setUserBusinessState(Integer userBusinessState) {
        this.userBusinessState = userBusinessState;
    }

}
