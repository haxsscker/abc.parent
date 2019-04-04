/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Wed Dec 03 21:01:21 CST 2014
 * Description:
 */
package com.autoserve.abc.dao.dataobject;

/**
 *  ProductInfoDO
 *  abc_product_info
 */
public class ProductInfoDO {
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
