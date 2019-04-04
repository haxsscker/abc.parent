package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * RefundApplyDO abc_refund_apply
 */
public class RefundApplyDO {
    /**
     * 主键id abc_refund_apply.ra_id
     */
    private Integer    raId;

    /**
     * 用户id abc_refund_apply.ra_user_id
     */
    private Integer    raUserId;

    /**
     * 退回账户号 abc_refund_apply.ra_account_no
     */
    private String     raAccountNo;

    /**
     * 退费原因 abc_refund_apply.ra_reason
     */
    private String     raReason;

    /**
     * 用户手机号 abc_refund_apply.ra_user_phone
     */
    private String     raUserPhone;

    /**
     * 退费金额 abc_refund_apply.ra_amount
     */
    private BigDecimal raAmount;

    /**
     * 申请人id employee abc_refund_apply.ra_applicant_id
     */
    private Integer    raApplicantId;

    /**
     * 申请日期 abc_refund_apply.ra_apply_date
     */
    private Date       raApplyDate;

    /**
     * 申请状态 0待审核 1 通过 2 未通过 abc_refund_apply.ra_apply_state
     */
    private Integer    raApplyState;

    public Integer getRaId() {
        return raId;
    }

    public void setRaId(Integer raId) {
        this.raId = raId;
    }

    public Integer getRaUserId() {
        return raUserId;
    }

    public void setRaUserId(Integer raUserId) {
        this.raUserId = raUserId;
    }

    public String getRaAccountNo() {
        return raAccountNo;
    }

    public void setRaAccountNo(String raAccountNo) {
        this.raAccountNo = raAccountNo;
    }

    public String getRaReason() {
        return raReason;
    }

    public void setRaReason(String raReason) {
        this.raReason = raReason;
    }

    public String getRaUserPhone() {
        return raUserPhone;
    }

    public void setRaUserPhone(String raUserPhone) {
        this.raUserPhone = raUserPhone;
    }

    public BigDecimal getRaAmount() {
        return raAmount;
    }

    public void setRaAmount(BigDecimal raAmount) {
        this.raAmount = raAmount;
    }

    public Integer getRaApplicantId() {
        return raApplicantId;
    }

    public void setRaApplicantId(Integer raApplicantId) {
        this.raApplicantId = raApplicantId;
    }

    public Date getRaApplyDate() {
        return raApplyDate;
    }

    public void setRaApplyDate(Date raApplyDate) {
        this.raApplyDate = raApplyDate;
    }

    public Integer getRaApplyState() {
        return raApplyState;
    }

    public void setRaApplyState(Integer raApplyState) {
        this.raApplyState = raApplyState;
    }
}
