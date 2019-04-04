package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * FullTransferRecordDO abc_full_transfer_record
 */
public class FullTransferRecordDO {
    /**
     * 主键id abc_full_transfer_record.ftr_id
     */
    private Integer    ftrId;

    /**
     * 标id abc_full_transfer_record.ftr_bid_id
     */
    private Integer    ftrBidId;

    /**
     * 原始借款标id abc_full_transfer_record.ftr_origin_id
     */
    private Integer    ftrOriginId;

    /**
     * 资金划转操作人 abc_full_transfer_record.ftr_operator
     */
    private Integer    ftrOperator;

    /**
     * 放款对象：用户id abc_full_transfer_record.ftr_user_id
     */
    private Integer    ftrUserId;

    /**
     * 标类型:1：正常标 2：转让标 3：收购标 abc_full_transfer_record.ftr_bid_type
     */
    private Integer    ftrBidType;

    /**
     * 实际划转金额 abc_full_transfer_record.ftr_transfer_money
     */
    private BigDecimal ftrTransferMoney;

    /**
     * 应收手续费 abc_full_transfer_record.ftr_pay_fee
     */
    private BigDecimal ftrPayFee;

    /**
     * 应收担保费 abc_full_transfer_record.ftr_guar_fee
     */
    private BigDecimal ftrGuarFee;

    /**
     * 实收手续费 abc_full_transfer_record.ftr_real_pay_fee
     */
    private BigDecimal ftrRealPayFee;

    /**
     * 实收担保费 abc_full_transfer_record.ftr_real_guar_fee
     */
    private BigDecimal ftrRealGuarFee;

    /**
     * 放款日期 abc_full_transfer_record.ftr_transfer_date
     */
    private Date       ftrTransferDate;

    /**
     * 交易流水号 abc_full_transfer_record.ftr_seq_no
     */
    private String     ftrSeqNo;

    /**
     * 交易状态 abc_full_transfer_record.ftr_deal_state
     */
    private Integer    ftrDealState;

    /**
     * 资金划转类型 abc_full_transfer_record.ftr_transfer_type
     */
    private Integer    ftrTransferType;

    public Integer getFtrId() {
        return ftrId;
    }

    public void setFtrId(Integer ftrId) {
        this.ftrId = ftrId;
    }

    public Integer getFtrBidId() {
        return ftrBidId;
    }

    public void setFtrBidId(Integer ftrBidId) {
        this.ftrBidId = ftrBidId;
    }

    public Integer getFtrOriginId() {
        return ftrOriginId;
    }

    public void setFtrOriginId(Integer ftrOriginId) {
        this.ftrOriginId = ftrOriginId;
    }

    public Integer getFtrOperator() {
        return ftrOperator;
    }

    public void setFtrOperator(Integer ftrOperator) {
        this.ftrOperator = ftrOperator;
    }

    public Integer getFtrUserId() {
        return ftrUserId;
    }

    public void setFtrUserId(Integer ftrUserId) {
        this.ftrUserId = ftrUserId;
    }

    public Integer getFtrBidType() {
        return ftrBidType;
    }

    public void setFtrBidType(Integer ftrBidType) {
        this.ftrBidType = ftrBidType;
    }

    public BigDecimal getFtrTransferMoney() {
        return ftrTransferMoney;
    }

    /**
     * @param BigDecimal ftrTransferMoney
     *            (abc_full_transfer_record.ftr_transfer_money )
     */
    public void setFtrTransferMoney(BigDecimal ftrTransferMoney) {
        this.ftrTransferMoney = ftrTransferMoney;
    }

    public BigDecimal getFtrPayFee() {
        return ftrPayFee;
    }

    public void setFtrPayFee(BigDecimal ftrPayFee) {
        this.ftrPayFee = ftrPayFee;
    }

    public BigDecimal getFtrGuarFee() {
        return ftrGuarFee;
    }

    public void setFtrGuarFee(BigDecimal ftrGuarFee) {
        this.ftrGuarFee = ftrGuarFee;
    }

    public BigDecimal getFtrRealPayFee() {
        return ftrRealPayFee;
    }

    /**
     * @param BigDecimal ftrRealPayFee
     *            (abc_full_transfer_record.ftr_real_pay_fee )
     */
    public void setFtrRealPayFee(BigDecimal ftrRealPayFee) {
        this.ftrRealPayFee = ftrRealPayFee;
    }

    public BigDecimal getFtrRealGuarFee() {
        return ftrRealGuarFee;
    }

    /**
     * @param BigDecimal ftrRealGuarFee
     *            (abc_full_transfer_record.ftr_real_guar_fee )
     */
    public void setFtrRealGuarFee(BigDecimal ftrRealGuarFee) {
        this.ftrRealGuarFee = ftrRealGuarFee;
    }

    public Date getFtrTransferDate() {
        return ftrTransferDate;
    }

    public void setFtrTransferDate(Date ftrTransferDate) {
        this.ftrTransferDate = ftrTransferDate;
    }

    public String getFtrSeqNo() {
        return ftrSeqNo;
    }

    public void setFtrSeqNo(String ftrSeqNo) {
        this.ftrSeqNo = ftrSeqNo;
    }

    public Integer getFtrDealState() {
        return ftrDealState;
    }

    public void setFtrDealState(Integer ftrDealState) {
        this.ftrDealState = ftrDealState;
    }

    public Integer getFtrTransferType() {
        return ftrTransferType;
    }

    /**
     * @param Integer ftrTransferType
     *            (abc_full_transfer_record.ftr_transfer_type )
     */
    public void setFtrTransferType(Integer ftrTransferType) {
        this.ftrTransferType = ftrTransferType;
    }

}
