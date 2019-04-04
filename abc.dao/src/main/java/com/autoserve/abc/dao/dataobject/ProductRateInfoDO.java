/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * 类ProductRateInfoDO.java的实现描述：产品利率
 * 
 * @author weihuimin 2014年12月11日 下午6:12:27
 */
public class ProductRateInfoDO {
    /**
     * 主键ID
     * abc_product_rate.product_rate_id
     */
    private Integer productRateId;

    /**
     * 产品ID
     * abc_product_rate.product_id
     */
    private Integer productId;

    /**
     * 产品名称
     * abc_product_info.product_name
     */
    private String productName;

    /**
     * 备注
     * abc_product_info.product_mark
     */
    private String productMark;

    /**
     * 年化收益率
     * abc_product_rate.product_rate
     */
    private BigDecimal productRate;

    /**
     * 最小期限
     * abc_product_rate.product_min_period
     */
    private Integer productMinPeriod;

    /**
     * 最大期限
     * abc_product_rate.product_max_period
     */
    private Integer productMaxPeriod;

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductMark() {
		return productMark;
	}

	public void setProductMark(String productMark) {
		this.productMark = productMark;
	}
    
}
