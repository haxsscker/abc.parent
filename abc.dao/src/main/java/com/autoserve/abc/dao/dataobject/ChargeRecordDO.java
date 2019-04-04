package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ChargeRecordDO abc_charge_record
 */
public class ChargeRecordDO {
    /**
     * 主键id abc_charge_record.cr_id
     */
    private Integer    crId;

    /**
     * 贷款id abc_charge_record.cr_loan_id
     */
    private Integer    crLoanId;

    /**
     * 贷款类型 abc_charge_record.cr_loan_type
     */
    private Integer    crLoanType;

    /**
     * 收取费用 abc_charge_record.cr_fee
     */
    private BigDecimal crFee;

    /**
     * 收费类型：1：平台放款手续费 2：平台服务费 3：担保服务费 4：转让手续费 5：收购手续费
     * abc_charge_record.cr_fee_type
     */
    private Integer    crFeeType;

    /**
     * 收费日期 abc_charge_record.cr_fee_date
     */
    private Date       crFeeDate;

    /**
     * 交易流水号 abc_charge_record.cr_seq_no
     */
    private String     crSeqNo;

    public Integer getCrId() {
        return crId;
    }

    public void setCrId(Integer crId) {
        this.crId = crId;
    }

    public Integer getCrLoanId() {
        return crLoanId;
    }

    public void setCrLoanId(Integer crLoanId) {
        this.crLoanId = crLoanId;
    }

    public Integer getCrLoanType() {
        return crLoanType;
    }

    public void setCrLoanType(Integer crLoanType) {
        this.crLoanType = crLoanType;
    }

    public BigDecimal getCrFee() {
        return crFee;
    }

    public void setCrFee(BigDecimal crFee) {
        this.crFee = crFee;
    }

    public Integer getCrFeeType() {
        return crFeeType;
    }

    public void setCrFeeType(Integer crFeeType) {
        this.crFeeType = crFeeType;
    }

    public Date getCrFeeDate() {
        return crFeeDate;
    }

    public void setCrFeeDate(Date crFeeDate) {
        this.crFeeDate = crFeeDate;
    }

    public String getCrSeqNo() {
        return crSeqNo;
    }

    public void setCrSeqNo(String crSeqNo) {
        this.crSeqNo = crSeqNo;
    }
}
