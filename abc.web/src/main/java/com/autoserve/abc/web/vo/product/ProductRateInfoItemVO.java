package com.autoserve.abc.web.vo.product;

import java.util.List;

/**
 * 
 * 类ProductRateInfoItemVO.java的实现描述：产品利率
 * @author Administrator 2014年12月18日 下午6:22:22
 */
public class ProductRateInfoItemVO {
    private Integer             total;
    private List<ProductRateInfoVO> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ProductRateInfoVO> getRows() {
        return rows;
    }

    public void setRows(List<ProductRateInfoVO> rows) {
        this.rows = rows;
    }

}
