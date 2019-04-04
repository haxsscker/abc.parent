package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.ChargeType;
import com.autoserve.abc.service.biz.enums.FeeType;
import com.autoserve.abc.service.biz.enums.LoanCategory;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuqing.zheng
 *         Created on 2014-11-25,17:03
 */
public class FeeSetting {
    private int id;

    /**
     * 费用类型，对应FeeType枚举
     */
    private FeeType feeType;

    /**
     * 项目类型，对应LoanCategory枚举
     */
    private LoanCategory loanCategory;

    /**
     * 收费类型，对应ChargeType枚举
     */
    private ChargeType chargeType;

    /**
     * 收费比例，适用于收费类型为“按比例收费”
     */
    private double rate;

    /**
     * 最小金额，适用于收费类型为“按比例收费”
     */
    private BigDecimal minAmount;

    /**
     * 最大金额，适用于收费类型为“按比例收费”
     */
    private BigDecimal maxAmount;

    /**
     * 确定的金额，适用于收费类型为“按每笔收费”
     */
    private BigDecimal accurateAmount;

    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public LoanCategory getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(LoanCategory loanCategory) {
        this.loanCategory = loanCategory;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public void setChargeType(ChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public BigDecimal getAccurateAmount() {
        return accurateAmount;
    }

    public void setAccurateAmount(BigDecimal accurateAmount) {
        this.accurateAmount = accurateAmount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
