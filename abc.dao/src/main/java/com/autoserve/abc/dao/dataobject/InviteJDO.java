package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

public class InviteJDO {
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
    private String inviteeName;

    /**
     * 注册日期
     */
    private Date registerDate;

    /**
     * 是否实名认证 	1：是 0：否
     */
    private Integer userRealnameIsproven;

    /**
     * 是否绑定手机号	1：是 0：否
     */
    private Integer userMobileIsbinded;

    /**
     * 是否绑定邮箱 	1：是 0：否
     */
    private Integer userEmailIsbinded;

    /**
     * 是否绑定银行卡 	1：是 0：否
     */
    private Integer userBankcardIsbinded;

    /**
     * 业务相关状态 1：注册成功 2：账户已开户 3：已充值 4：已投资
     */
    private Integer userBusinessState;

    /**
     * 业务相关状态:用于搜索，例如等于4表示要查询未投资的
     */
    private Integer excludeState4Search;

    /**
     * 邀请人类型 1：前台网友 2：平台用户
     */
    private Integer inviteUserType;

    /**
     * 是否已生效  0：未生效 1：已生效
     */
    private Integer inviteIsValid;

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
    private Integer inviteRewardState;

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

    public String getInviteeName() {
        return inviteeName;
    }

    public void setInviteeName(String inviteeName) {
        this.inviteeName = inviteeName;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
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

    public Integer getUserBusinessState() {
        return userBusinessState;
    }

    public void setUserBusinessState(Integer userBusinessState) {
        this.userBusinessState = userBusinessState;
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

    public Integer getExcludeState4Search() {
        return excludeState4Search;
    }

    public void setExcludeState4Search(Integer excludeState4Search) {
        this.excludeState4Search = excludeState4Search;
    }

	public Integer getInviteRewardScore() {
		return inviteRewardScore;
	}

	public void setInviteRewardScore(Integer inviteRewardScore) {
		this.inviteRewardScore = inviteRewardScore;
	}
    
}
