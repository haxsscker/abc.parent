package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.InviteUserType;
import com.autoserve.abc.service.biz.enums.RewardState;
import com.autoserve.abc.service.biz.enums.ValidState;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  InviteDO
 */
public class Invite {

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
     * 邀请人类型 1：前台网友 2：平台用户
     */
    private InviteUserType inviteUserType;

    /**
     * 是否已生效  0：未生效 1：已生效
     */
    private ValidState inviteIsValid;

    /**
     * 开始日期
     */
    private Date inviteStartDate;

    /**
     * 到期日期
     */
    private Date inviteEndDate;

    /**
     * 奖励邀请人金额
     */
    private BigDecimal inviteRewardMoney;
    /**
     * 奖励邀请人积分
     */
    private Integer inviteRewardScore;

    /**
     * 是否已使用	0：未使用 1：已使用
     */
    private RewardState inviteRewardState;

    private Date inviteCreateTime;

    private Date inviteLastModifyTime;

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

    public InviteUserType getInviteUserType() {
        return inviteUserType;
    }

    public void setInviteUserType(InviteUserType inviteUserType) {
        this.inviteUserType = inviteUserType;
    }

    public ValidState getInviteIsValid() {
        return inviteIsValid;
    }

    public void setInviteIsValid(ValidState inviteIsValid) {
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

    public RewardState getInviteRewardState() {
        return inviteRewardState;
    }

    public void setInviteRewardState(RewardState inviteRewardState) {
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

	public Integer getInviteRewardScore() {
		return inviteRewardScore;
	}

	public void setInviteRewardScore(Integer inviteRewardScore) {
		this.inviteRewardScore = inviteRewardScore;
	}
}