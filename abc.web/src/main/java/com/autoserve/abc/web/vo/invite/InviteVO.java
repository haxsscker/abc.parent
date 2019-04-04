package com.autoserve.abc.web.vo.invite;

import java.math.BigDecimal;

public class InviteVO {
    private Integer inviteId;

    /**
     * 邀请人id
     */
    private Integer inviteUserId;

    /**
     * 被邀请人id
     */
    private Integer inviteInviteeId;

    /**
     * 被邀请人姓名
     */
    private String cst_user_name;

    /**
     * 注册日期
     */
    private String cst_add_date;

    /**
     * 是否实名认证 	1：是 0：否
     */
    private String cst_realname_prove;

    /**
     * 是否绑定手机号	1：是 0：否
     */
    private String cst_binding_mobile;

    /**
     * 是否绑定邮箱 	1：是 0：否
     */
    private String cst_binding_email;

    /**
     * 是否绑定银行卡 	1：是 0：否
     */
    private String cst_binding_bankcard;

    /**
     * 业务相关状态 1：注册成功 2：账户已开户 3：已充值 4：已投资
     */
    /**
     * 账户开户状态
     */
    private Integer open_account_state;

    /**
     * 充值状态
     */
    private Integer recharge_state;

    /**
     * 投资状态
     */
    private Integer invest_state;

    /**
     * 邀请人类型 1：前台网友 2：平台用户
     */
    private String inviteUserType;

    /**
     * 是否已生效  0：未生效 1：已生效
     */
    private String inviteIsValid;

    /**
     * 开始日期
     */
    private String inviteStartDate;

    /**
     * 到期日期
     */
    private String inviteEndDate;

    /**
     * 奖励邀请人金额
     */
    private BigDecimal sys_reward_money;

    /**
     * 是否已使用	0：未使用 1：已使用
     */
    private String inviteRewardState;

    private String inviteCreateTime;

    private String inviteLastModifyTime;

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

    public String getCst_user_name() {
        return cst_user_name;
    }

    public void setCst_user_name(String cst_user_name) {
        this.cst_user_name = cst_user_name;
    }

    public String getCst_add_date() {
        return cst_add_date;
    }

    public void setCst_add_date(String cst_add_date) {
        this.cst_add_date = cst_add_date;
    }

    public String getCst_realname_prove() {
        return cst_realname_prove;
    }

    public void setCst_realname_prove(String cst_realname_prove) {
        this.cst_realname_prove = cst_realname_prove;
    }

    public String getCst_binding_mobile() {
        return cst_binding_mobile;
    }

    public void setCst_binding_mobile(String cst_binding_mobile) {
        this.cst_binding_mobile = cst_binding_mobile;
    }

    public String getCst_binding_email() {
        return cst_binding_email;
    }

    public void setCst_binding_email(String cst_binding_email) {
        this.cst_binding_email = cst_binding_email;
    }

    public String getCst_binding_bankcard() {
        return cst_binding_bankcard;
    }

    public void setCst_binding_bankcard(String cst_binding_bankcard) {
        this.cst_binding_bankcard = cst_binding_bankcard;
    }

    public Integer getOpen_account_state() {
        return open_account_state;
    }

    public void setOpen_account_state(Integer open_account_state) {
        this.open_account_state = open_account_state;
    }

    public Integer getRecharge_state() {
        return recharge_state;
    }

    public void setRecharge_state(Integer recharge_state) {
        this.recharge_state = recharge_state;
    }

    public Integer getInvest_state() {
        return invest_state;
    }

    public void setInvest_state(Integer invest_state) {
        this.invest_state = invest_state;
    }

    public String getInviteUserType() {
        return inviteUserType;
    }

    public void setInviteUserType(String inviteUserType) {
        this.inviteUserType = inviteUserType;
    }

    public String getInviteIsValid() {
        return inviteIsValid;
    }

    public void setInviteIsValid(String inviteIsValid) {
        this.inviteIsValid = inviteIsValid;
    }

    public String getInviteStartDate() {
        return inviteStartDate;
    }

    public void setInviteStartDate(String inviteStartDate) {
        this.inviteStartDate = inviteStartDate;
    }

    public String getInviteEndDate() {
        return inviteEndDate;
    }

    public void setInviteEndDate(String inviteEndDate) {
        this.inviteEndDate = inviteEndDate;
    }

    public BigDecimal getSys_reward_money() {
        return sys_reward_money;
    }

    public void setSys_reward_money(BigDecimal sys_reward_money) {
        this.sys_reward_money = sys_reward_money;
    }

    public String getInviteRewardState() {
        return inviteRewardState;
    }

    public void setInviteRewardState(String inviteRewardState) {
        this.inviteRewardState = inviteRewardState;
    }

    public String getInviteCreateTime() {
        return inviteCreateTime;
    }

    public void setInviteCreateTime(String inviteCreateTime) {
        this.inviteCreateTime = inviteCreateTime;
    }

    public String getInviteLastModifyTime() {
        return inviteLastModifyTime;
    }

    public void setInviteLastModifyTime(String inviteLastModifyTime) {
        this.inviteLastModifyTime = inviteLastModifyTime;
    }
}
