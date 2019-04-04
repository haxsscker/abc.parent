package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * BuyLoanSubscribeDO abc_buy_loan_subscribe
 */
public class BuyLoanSubscribeDO {
    /**
     * 主键id abc_buy_loan_subscribe.bls_id
     */
    private Integer    blsId;

    /**
     * 收购申请id abc_buy_loan_subscribe.bls_buy_id
     */
    private Integer    blsBuyId;

    /**
     * 贷款id abc_buy_loan_subscribe.bls_loan_id
     */
    private Integer    blsLoanId;

    /**
     * 转让人 abc_buy_loan_subscribe.bls_user_id
     */
    private Integer    blsUserId;

    /**
     * 转让日期 abc_buy_loan_subscribe.bls_transfer_time
     */
    private Date       blsTransferTime;

    /**
     * 转让金额 abc_buy_loan_subscribe.bls_transfer_money
     */
    private BigDecimal blsTransferMoney;

    /**
     * 收益金额 abc_buy_loan_subscribe.bls_earn_money
     */
    private BigDecimal blsEarnMoney;

    /**
     * 状态 －1:已删除 1:待认购 2:认购中 3:认购成功 5:拒绝认购 6:暂时忽略
     * abc_buy_loan_subscribe.bls_state
     */
    private Integer    blsState;

    /**
     * 创建时间 abc_buy_loan_subscribe.bls_createtime
     */
    private Date       blsCreatetime;

    public Integer getBlsId() {
        return blsId;
    }

    public void setBlsId(Integer blsId) {
        this.blsId = blsId;
    }

    public Integer getBlsBuyId() {
        return blsBuyId;
    }

    public void setBlsBuyId(Integer blsBuyId) {
        this.blsBuyId = blsBuyId;
    }

    public Integer getBlsLoanId() {
        return blsLoanId;
    }

    public void setBlsLoanId(Integer blsLoanId) {
        this.blsLoanId = blsLoanId;
    }

    public Integer getBlsUserId() {
        return blsUserId;
    }

    public void setBlsUserId(Integer blsUserId) {
        this.blsUserId = blsUserId;
    }

    public Date getBlsTransferTime() {
        return blsTransferTime;
    }

    public void setBlsTransferTime(Date blsTransferTime) {
        this.blsTransferTime = blsTransferTime;
    }

    public BigDecimal getBlsTransferMoney() {
        return blsTransferMoney;
    }

    /**
     * @param BigDecimal blsTransferMoney
     *            (abc_buy_loan_subscribe.bls_transfer_money )
     */
    public void setBlsTransferMoney(BigDecimal blsTransferMoney) {
        this.blsTransferMoney = blsTransferMoney;
    }

    public BigDecimal getBlsEarnMoney() {
        return blsEarnMoney;
    }

    public void setBlsEarnMoney(BigDecimal blsEarnMoney) {
        this.blsEarnMoney = blsEarnMoney;
    }

    public Integer getBlsState() {
        return blsState;
    }

    public void setBlsState(Integer blsState) {
        this.blsState = blsState;
    }

    public Date getBlsCreatetime() {
        return blsCreatetime;
    }

    public void setBlsCreatetime(Date blsCreatetime) {
        this.blsCreatetime = blsCreatetime;
    }

}
