package com.autoserve.abc.web.vo.product;

import java.math.BigDecimal;

/**
 * 
 * 类ProductRateInfoVO.java的实现描述：产品页面
 * @author Administrator 2014年12月18日 下午6:22:52
 */
public class ProductRateInfoVO {
    private Integer    pdo_product_id;

    private BigDecimal pdo_product_rate;

    private Integer    pdo_product_rate_Id;

    private Integer    pdo_min_period;

    private Integer    pdo_max_period;

    private String     pdo_product_name;

    private String     pdo_product_mark;

    public Integer getPdo_product_id() {
        return pdo_product_id;
    }

    public void setPdo_product_id(Integer pdo_product_id) {
        this.pdo_product_id = pdo_product_id;
    }

    public Integer getPdo_product_rate_Id() {
        return pdo_product_rate_Id;
    }

    public void setPdo_product_rate_Id(Integer pdo_product_rate_Id) {
        this.pdo_product_rate_Id = pdo_product_rate_Id;
    }

    public BigDecimal getPdo_product_rate() {
        return pdo_product_rate;
    }

    public void setPdo_product_rate(BigDecimal pdo_product_rate) {
        this.pdo_product_rate = pdo_product_rate;
    }

    public Integer getPdo_min_period() {
        return pdo_min_period;
    }

    public void setPdo_min_period(Integer pdo_min_period) {
        this.pdo_min_period = pdo_min_period;
    }

    public Integer getPdo_max_period() {
        return pdo_max_period;
    }

    public void setPdo_max_period(Integer pdo_max_period) {
        this.pdo_max_period = pdo_max_period;
    }

    public String getPdo_product_name() {
        return pdo_product_name;
    }

    public void setPdo_product_name(String pdo_product_name) {
        this.pdo_product_name = pdo_product_name;
    }

    public String getPdo_product_mark() {
        return pdo_product_mark;
    }

    public void setPdo_product_mark(String pdo_product_mark) {
        this.pdo_product_mark = pdo_product_mark;
    }

}
