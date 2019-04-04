package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.FeeType;

/**
 * @author J.YL 2015年1月11日 下午9:11:37
 */
public class ChargeRecord {
    /**
     * 主键id abc_charge_record.cr_id
     */
    private Integer    id;

    /**
     * 贷款id abc_charge_record.cr_loan_id
     */
    private Integer    loanId;

    /**
     * 贷款类型 abc_charge_record.cr_loan_type
     */
    private Integer    loanType;

    /**
     * 收取费用 abc_charge_record.cr_fee
     */
    private BigDecimal fee;

    /**
     * 收费类型：1：平台放款手续费 2：平台服务费 3：担保服务费 4：转让手续费 5：收购手续费
     * abc_charge_record.cr_fee_type
     */
    private FeeType    feeType;

    /**
     * 收费日期 abc_charge_record.cr_fee_date
     */
    private Date       feeDate;

    /**
     * 交易流水号 abc_charge_record.cr_seq_no
     */
    private String     seqNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getLoanType() {
        return loanType;
    }

    public void setLoanType(Integer loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public Date getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Date feeDate) {
        this.feeDate = feeDate;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

}
