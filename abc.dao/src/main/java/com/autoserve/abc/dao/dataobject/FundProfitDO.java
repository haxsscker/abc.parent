package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * FundProfitDO abc_fund_profit
 */
public class FundProfitDO {
    /**
     * 主键ID abc_fund_profit.fp_profit_id
     */
    private Integer    fpProfitId;

    /**
     * 有限合伙表ID,外键表：abc_fund_apply abc_fund_profit.fp_fund_id
     */
    private Integer    fpFundId;

    /**
     * 最小金额 abc_fund_profit.fp_min_money
     */
    private BigDecimal fpMinMoney;

    /**
     * 最大金额 abc_fund_profit.fp_max_money
     */
    private BigDecimal fpMaxMoney;

    /**
     * 年化收益率 abc_fund_profit.fp_profit_yields
     */
    private BigDecimal fpProfitYields;

    public Integer getFpProfitId() {
        return fpProfitId;
    }

    public void setFpProfitId(Integer fpProfitId) {
        this.fpProfitId = fpProfitId;
    }

    public Integer getFpFundId() {
        return fpFundId;
    }

    public void setFpFundId(Integer fpFundId) {
        this.fpFundId = fpFundId;
    }

    public BigDecimal getFpMinMoney() {
        return fpMinMoney;
    }

    public void setFpMinMoney(BigDecimal fpMinMoney) {
        this.fpMinMoney = fpMinMoney;
    }

    public BigDecimal getFpMaxMoney() {
        return fpMaxMoney;
    }

    public void setFpMaxMoney(BigDecimal fpMaxMoney) {
        this.fpMaxMoney = fpMaxMoney;
    }

    public BigDecimal getFpProfitYields() {
        return fpProfitYields;
    }

    public void setFpProfitYields(BigDecimal fpProfitYields) {
        this.fpProfitYields = fpProfitYields;
    }
}
