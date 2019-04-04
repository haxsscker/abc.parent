package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * ProductRateDO abc_product_rate
 */
public class ProductRateDO {
    /**
     * 主键ID abc_product_rate.product_rate_id
     */
    private Integer    productRateId;

    /**
     * 产品ID abc_product_rate.product_id
     */
    private Integer    productId;

    /**
     * 年化收益率 abc_product_rate.product_rate
     */
    private BigDecimal productRate;

    /**
     * 最小期限 abc_product_rate.product_min_period
     */
    private Integer    productMinPeriod;

    /**
     * 最大期限 abc_product_rate.product_max_period
     */
    private Integer    productMaxPeriod;

    public Integer getProductRateId() {
        return productRateId;
    }

    public void setProductRateId(Integer productRateId) {
        this.productRateId = productRateId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getProductRate() {
        return productRate;
    }

    public void setProductRate(BigDecimal productRate) {
        this.productRate = productRate;
    }

    public Integer getProductMinPeriod() {
        return productMinPeriod;
    }

    public void setProductMinPeriod(Integer productMinPeriod) {
        this.productMinPeriod = productMinPeriod;
    }

    public Integer getProductMaxPeriod() {
        return productMaxPeriod;
    }

    public void setProductMaxPeriod(Integer productMaxPeriod) {
        this.productMaxPeriod = productMaxPeriod;
    }
}
