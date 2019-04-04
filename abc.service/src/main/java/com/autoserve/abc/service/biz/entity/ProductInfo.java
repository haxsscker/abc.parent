/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;


/**
 * 类Refund.java的实现描述：TODO 类实现描述
 * 
 * @author weihuimin 2014年12月11日 
 */
public class ProductInfo {
    /**
     * 主键ID
     * abc_product_info.product_id
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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
