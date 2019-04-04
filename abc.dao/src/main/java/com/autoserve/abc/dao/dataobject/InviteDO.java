package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  InviteDO
 */
public class InviteDO {
    /**
     * 
     * abc_invite.invite_id
     */
    private Integer inviteId;

    /**
     * 邀请人id
     * abc_invite.invite_user_id
     */
    private Integer inviteUserId;

    /**
     * 被邀请人id
     * abc_invite.invite_invitee_id
     */
    private Integer inviteInviteeId;

    /**
     * 邀请人类型 1：前台网友 2：平台用户
     * abc_invite.invite_user_type
     */
    private Integer inviteUserType;

    /**
     * 是否已生效  0：未生效 1：已生效
     * abc_invite.invite_is_valid
     */
    private Integer inviteIsValid;

    /**
     * 开始日期
     * abc_invite.invite_start_date
     */
    private Date inviteStartDate;

    /**
     * 到期日期
     * abc_invite.invite_end_date
     */
    private Date inviteEndDate;

    /**
     * 奖励邀请人积分
     * abc_invite.invite_reward_score
     */
    private Integer inviteRewardScore;
    /**
     * 奖励邀请人金额
     * abc_invite.invite_reward_money
     */
    private BigDecimal inviteRewardMoney;

    /**
     * 是否已使用	0：未使用 1：已使用
     * abc_invite.invite_reward_state
     */
    private Integer inviteRewardState;

    private Date inviteCreateTime;

    private Date inviteLastModifyTime;

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

	public Integer getInviteRewardScore() {
		return inviteRewardScore;
	}

	public void setInviteRewardScore(Integer inviteRewardScore) {
		this.inviteRewardScore = inviteRewardScore;
	}
    
    
}