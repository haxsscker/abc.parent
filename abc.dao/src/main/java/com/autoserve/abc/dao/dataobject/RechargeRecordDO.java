package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * RechargeRecord abc_recharge_record
 */
public class RechargeRecordDO {
    /**
     * 充值记录表 abc_recharge_record.recharge_id
     */
    private Integer    rechargeId;

    /**
     * 充值用户id abc_recharge_record.recharge_user_id
     */
    private Integer    rechargeUserId;

    /**
     * 充值账户 abc_recharge_record.recharge_account_id
     */
    private String     rechargeAccountId;

    /**
     * 交易流水号 abc_recharge_record.recharge_seq_no
     */
    private String     rechargeSeqNo;

    /**
     * 充值金额 abc_recharge_record.recharge_amount
     */
    private BigDecimal rechargeAmount;
    /**
     * 充值手续费
     */
    private BigDecimal rechargeFeeAmt;
    /**
     * 充值状态，0充值中，1充值成功，2充值不成功 abc_recharge_record.recharge_state
     */
    private Integer    rechargeState;
    /**
     * 充值时间
     */
    private Date       rechargeDate;
    /**
     * 外部交易流水号
     */
    private String rechargeOutSeqNo;
    /**
     * 充值状态描述
     */
    private String rechargeStateDes;
    public Integer getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Integer rechargeId) {
        this.rechargeId = rechargeId;
    }

    public Integer getRechargeUserId() {
        return rechargeUserId;
    }

    public void setRechargeUserId(Integer rechargeUserId) {
        this.rechargeUserId = rechargeUserId;
    }

    public String getRechargeAccountId() {
        return rechargeAccountId;
    }

    /**
     * @param Integer rechargeAccountId (abc_recharge_record.recharge_account_id
     *            )
     */
    public void setRechargeAccountId(String rechargeAccountId) {
        this.rechargeAccountId = rechargeAccountId;
    }

    public String getRechargeSeqNo() {
        return rechargeSeqNo;
    }

    public void setRechargeSeqNo(String rechargeSeqNo) {
        this.rechargeSeqNo = rechargeSeqNo;
    }

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public BigDecimal getRechargeFeeAmt() {
		return rechargeFeeAmt;
	}

	public void setRechargeFeeAmt(BigDecimal rechargeFeeAmt) {
		this.rechargeFeeAmt = rechargeFeeAmt;
	}

	public Integer getRechargeState() {
        return rechargeState;
    }

    public void setRechargeState(Integer rechargeState) {
        this.rechargeState = rechargeState;
    }

    public Date getRechargeDate() {
        return rechargeDate;
    }

    public void setRechargeDate(Date rechargeDate) {
        this.rechargeDate = rechargeDate;
    }

	public String getRechargeOutSeqNo() {
		return rechargeOutSeqNo;
	}

	public void setRechargeOutSeqNo(String rechargeOutSeqNo) {
		this.rechargeOutSeqNo = rechargeOutSeqNo;
	}

	public String getRechargeStateDes() {
		return rechargeStateDes;
	}

	public void setRechargeStateDes(String rechargeStateDes) {
		this.rechargeStateDes = rechargeStateDes;
	}
    
    
}
