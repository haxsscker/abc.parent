package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TocashRecord abc_tocash_record
 */
public class TocashRecordDO {
    /**
     * 提现id import java.math.BigDecimal; sh_id
     */
    private Integer    tocashId;

    /**
     * 提现用户id abc_tocash_record.tocash_user_id
     */
    private Integer    tocashUserId;

    /**
     * 提现账户id abc_tocash_record.tocash_account_id
     */
    private String     tocashAccountId;

    /**
     * 提现金额 abc_tocash_record.tocash_amount
     */
    private BigDecimal tocashAmount;

    /**
     * 提现交易流水号 abc_tocash_record.tocash_seq_no
     */
    private String     tocashSeqNo;

    /**
     * 提现状态：0提现中，1提现成功，2提现失败 abc_tocash_record.tocash_state
     */
    private Integer    tocashState;
    /**
     * 提现时间
     */
    private Date       tocashDate;
    
    /**
     * 外部交易流水号
     */
    private String tocashOutSeqNo;
    /**
     * 使用提现额度
     */
    private BigDecimal tocashQuota;
    /**
     * 有效使用提现额度
     */
    private BigDecimal tocashValidquota;
    /**
     * 平台承担的手续费比例
     */
    private BigDecimal tocashFeePercent;
    /**
     * 用户实际承担的手续费金额
     */
    private BigDecimal tocashFeeWithdraws;
    /**
     * 平台承担的手续费金额
     */
    private BigDecimal tocashFee;
   
    private BigDecimal tocashFeeLimit;

    public Integer getTocashId() {
        return tocashId;
    }

    public void setTocashId(Integer tocashId) {
        this.tocashId = tocashId;
    }

    public Integer getTocashUserId() {
        return tocashUserId;
    }

    public void setTocashUserId(Integer tocashUserId) {
        this.tocashUserId = tocashUserId;
    }

    public String getTocashAccountId() {
        return tocashAccountId;
    }

    public void setTocashAccountId(String tocashAccountId) {
        this.tocashAccountId = tocashAccountId;
    }

    public BigDecimal getTocashAmount() {
        return tocashAmount;
    }

    public void setTocashAmount(BigDecimal tocashAmount) {
        this.tocashAmount = tocashAmount;
    }

    public String getTocashSeqNo() {
        return tocashSeqNo;
    }

    public void setTocashSeqNo(String tocashSeqNo) {
        this.tocashSeqNo = tocashSeqNo;
    }

    public Integer getTocashState() {
        return tocashState;
    }

    public void setTocashState(Integer tocashState) {
        this.tocashState = tocashState;
    }

    public Date getTocashDate() {
        return tocashDate;
    }

    public void setTocashDate(Date tocashDate) {
        this.tocashDate = tocashDate;
    }

	public String getTocashOutSeqNo() {
		return tocashOutSeqNo;
	}

	public void setTocashOutSeqNo(String tocashOutSeqNo) {
		this.tocashOutSeqNo = tocashOutSeqNo;
	}

	public BigDecimal getTocashQuota() {
		return tocashQuota;
	}

	public void setTocashQuota(BigDecimal tocashQuota) {
		this.tocashQuota = tocashQuota;
	}

	public BigDecimal getTocashValidquota() {
		return tocashValidquota;
	}

	public void setTocashValidquota(BigDecimal tocashValidquota) {
		this.tocashValidquota = tocashValidquota;
	}

	public BigDecimal getTocashFeePercent() {
		return tocashFeePercent;
	}

	public void setTocashFeePercent(BigDecimal tocashFeePercent) {
		this.tocashFeePercent = tocashFeePercent;
	}

	public BigDecimal getTocashFeeWithdraws() {
		return tocashFeeWithdraws;
	}

	public void setTocashFeeWithdraws(BigDecimal tocashFeeWithdraws) {
		this.tocashFeeWithdraws = tocashFeeWithdraws;
	}

	public BigDecimal getTocashFee() {
		return tocashFee;
	}

	public void setTocashFee(BigDecimal tocashFee) {
		this.tocashFee = tocashFee;
	}

	public BigDecimal getTocashFeeLimit() {
		return tocashFeeLimit;
	}

	public void setTocashFeeLimit(BigDecimal tocashFeeLimit) {
		this.tocashFeeLimit = tocashFeeLimit;
	}
    

}
