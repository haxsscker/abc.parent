/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Fri Dec 19 13:23:55 CST 2014
 * Description:
 */
package com.autoserve.abc.web.vo.fund;

import java.math.BigDecimal;

/**
 *  FundProfitVO
 */
public class FundProfitVO {
    /**
     * 主键ID
     */
    private Integer fpProfitId;

    /**
     * 有限合伙表ID,外键表：abc_fund_apply
     */
    private Integer fpFundId;

    /**
     * 最小金额
     */
    private BigDecimal fpMinMoney;

    /**
     * 最大金额
     */
    private BigDecimal fpMaxMoney;

    /**
     * 年化收益率
     */
    private BigDecimal fpProfitYields;

    /**
     * @return abc_fund_profit.fp_profit_id
     */
    public Integer getFpProfitId() {
        return fpProfitId;
    }

    /**
     * @param Integer fpProfitId (abc_fund_profit.fp_profit_id )
     */
    public void setFpProfitId(Integer fpProfitId) {
        this.fpProfitId = fpProfitId;
    }

    /**
     * @return abc_fund_profit.fp_fund_id
     */
    public Integer getFpFundId() {
        return fpFundId;
    }

    /**
     * @param Integer fpFundId (abc_fund_profit.fp_fund_id )
     */
    public void setFpFundId(Integer fpFundId) {
        this.fpFundId = fpFundId;
    }

    /**
     * @return abc_fund_profit.fp_min_money
     */
    public BigDecimal getFpMinMoney() {
        return fpMinMoney;
    }

    /**
     * @param BigDecimal fpMinMoney (abc_fund_profit.fp_min_money )
     */
    public void setFpMinMoney(BigDecimal fpMinMoney) {
        this.fpMinMoney = fpMinMoney;
    }

    /**
     * @return abc_fund_profit.fp_max_money
     */
    public BigDecimal getFpMaxMoney() {
        return fpMaxMoney;
    }

    /**
     * @param BigDecimal fpMaxMoney (abc_fund_profit.fp_max_money )
     */
    public void setFpMaxMoney(BigDecimal fpMaxMoney) {
        this.fpMaxMoney = fpMaxMoney;
    }

    /**
     * @return abc_fund_profit.fp_profit_yields
     */
    public BigDecimal getFpProfitYields() {
        return fpProfitYields;
    }

    /**
     * @param BigDecimal fpProfitYields (abc_fund_profit.fp_profit_yields )
     */
    public void setFpProfitYields(BigDecimal fpProfitYields) {
        this.fpProfitYields = fpProfitYields;
    }
}