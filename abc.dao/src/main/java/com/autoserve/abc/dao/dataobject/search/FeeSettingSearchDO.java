package com.autoserve.abc.dao.dataobject.search;

import java.math.BigDecimal;

/**
 * 类实现描述 费用设置搜索类
 * 
 * @author liuwei 2015年1月15日 上午9:46:49
 */
public class FeeSettingSearchDO {

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private Integer    selFeeType;

    private Integer    productType;
    
    private Integer    notIncludeId;

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

    public Integer getSelFeeType() {
        return selFeeType;
    }

    public void setSelFeeType(Integer selFeeType) {
        this.selFeeType = selFeeType;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

	public Integer getNotIncludeId() {
		return notIncludeId;
	}

	public void setNotIncludeId(Integer notIncludeId) {
		this.notIncludeId = notIncludeId;
	}
    
    

}
