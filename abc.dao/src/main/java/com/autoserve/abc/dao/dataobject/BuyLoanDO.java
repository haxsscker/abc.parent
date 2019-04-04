package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * BuyLoanDO abc_buy_loan
 */
public class BuyLoanDO {
    /**
     * 主键id abc_buy_loan.bl_id
     */
    private Integer    blId;

    /**
     * 原始贷款id abc_buy_loan.bl_origin_id
     */
    private Integer    blOriginId;

    /**
     * 收购人 abc_buy_loan.bl_user_id
     */
    private Integer    blUserId;

    /**
     * 收购金额 abc_buy_loan.bl_buy_money
     */
    private BigDecimal blBuyMoney;

    /**
     * 收购债券总额 abc_buy_loan.bl_buy_total
     */
    private BigDecimal blBuyTotal;

    /**
     * 收购期数 abc_buy_loan.bl_buy_period
     */
    private Integer    blBuyPeriod;

    /**
     * 收购手续费 abc_buy_loan.bl_fee
     */
    private BigDecimal blFee;

    /**
     * 收购状态 -1:已删除 1:收购申请待审核 2：收购申请审核通过 3：收购申请审核未通过 4:收购中 5：满标待审 6：满标审核已通过
     * 7：满标审核未通过 8：已流标 9:划转中 10:已划转 abc_buy_loan.bl_state
     */
    private Integer    blState;

    /**
     * 创建日期 abc_buy_loan.bl_createtime
     */
    private Date       blCreatetime;

    /**
     * 认购开始日期 abc_buy_loan.bl_start_time
     */
    private Date       blStartTime;

    /**
     * 认购结束日期 abc_buy_loan.bl_start_time
     */
    private Date       blEndTime;

    /**
     * 满标日期 abc_buy_loan.bl_full_time
     */
    private Date       blFullTime;

    /**
     * 放款成功时间 abc_buy_loan.bl_full_transferedtime
     */
    private Date       blFullTransferedtime;

    /**
     * 划转状态 1:已划转 0:未划转 abc_buy_loan.bl_is_transfer
     */
    private Integer    blIsTransfer;

    /**
     * 当前投标总额 abc_transfer_loan.bl_current_invest
     */
    private BigDecimal blCurrentInvest;

    /**
     * 当前有效投标总额 abc_transfer_loan.bl_current_valid_invest
     */
    private BigDecimal blCurrentValidInvest;

    /**
     * 下一次还款计划id abc_transfer_loan.bl_next_payment_id
     */
    private Integer    blNextPaymentId;

    /**
     * 冻结流水号
     */
    private String     blFreezeSeqNo;

    public Integer getBlId() {
        return blId;
    }

    public void setBlId(Integer blId) {
        this.blId = blId;
    }

    public Integer getBlOriginId() {
        return blOriginId;
    }

    public void setBlOriginId(Integer blOriginId) {
        this.blOriginId = blOriginId;
    }

    public Integer getBlUserId() {
        return blUserId;
    }

    public void setBlUserId(Integer blUserId) {
        this.blUserId = blUserId;
    }

    public BigDecimal getBlBuyMoney() {
        return blBuyMoney;
    }

    public void setBlBuyMoney(BigDecimal blBuyMoney) {
        this.blBuyMoney = blBuyMoney;
    }

    public BigDecimal getBlBuyTotal() {
        return blBuyTotal;
    }

    public void setBlBuyTotal(BigDecimal blBuyTotal) {
        this.blBuyTotal = blBuyTotal;
    }

    public Integer getBlBuyPeriod() {
        return blBuyPeriod;
    }

    public void setBlBuyPeriod(Integer blBuyPeriod) {
        this.blBuyPeriod = blBuyPeriod;
    }

    public BigDecimal getBlFee() {
        return blFee;
    }

    public void setBlFee(BigDecimal blFee) {
        this.blFee = blFee;
    }

    public Integer getBlState() {
        return blState;
    }

    public void setBlState(Integer blState) {
        this.blState = blState;
    }

    public Date getBlCreatetime() {
        return blCreatetime;
    }

    public void setBlCreatetime(Date blCreatetime) {
        this.blCreatetime = blCreatetime;
    }

    public Date getBlStartTime() {
        return blStartTime;
    }

    public void setBlStartTime(Date blStartTime) {
        this.blStartTime = blStartTime;
    }

    public Date getBlEndTime() {
        return blEndTime;
    }

    public void setBlEndTime(Date blEndTime) {
        this.blEndTime = blEndTime;
    }

    public Date getBlFullTime() {
        return blFullTime;
    }

    public void setBlFullTime(Date blFullTime) {
        this.blFullTime = blFullTime;
    }

    public void setBlFullTransferedtime(Date blFullTransferedtime) {
        this.blFullTransferedtime = blFullTransferedtime;
    }

    public Date getBlFullTransferedtime() {
        return blFullTransferedtime;
    }

    public Integer getBlIsTransfer() {
        return blIsTransfer;
    }

    public void setBlIsTransfer(Integer blIsTransfer) {
        this.blIsTransfer = blIsTransfer;
    }

    public BigDecimal getBlCurrentInvest() {
        return blCurrentInvest;
    }

    public void setBlCurrentInvest(BigDecimal blCurrentInvest) {
        this.blCurrentInvest = blCurrentInvest;
    }

    public BigDecimal getBlCurrentValidInvest() {
        return blCurrentValidInvest;
    }

    public void setBlCurrentValidInvest(BigDecimal blCurrentValidInvest) {
        this.blCurrentValidInvest = blCurrentValidInvest;
    }

    public Integer getBlNextPaymentId() {
        return blNextPaymentId;
    }

    public void setBlNextPaymentId(Integer blNextPaymentId) {
        this.blNextPaymentId = blNextPaymentId;
    }

    public String getBlFreezeSeqNo() {
        return blFreezeSeqNo;
    }

    public void setBlFreezeSeqNo(String blFreezeSeqNo) {
        this.blFreezeSeqNo = blFreezeSeqNo;
    }

}
