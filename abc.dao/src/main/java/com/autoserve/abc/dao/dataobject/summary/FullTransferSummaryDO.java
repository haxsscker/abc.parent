/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject.summary;

import java.math.BigDecimal;

/**
 * 资金划转统计
 * 
 * @author J.YL 2015年1月4日 上午11:08:06
 */
public class FullTransferSummaryDO {

    //年份
    private Integer    year;
    //月份
    private Integer    month;
    //总额
    private BigDecimal totalMoney;

    /**
     * @return the year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return the month
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * @return the totalMoney
     */
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    /**
     * @param totalMoney the totalMoney to set
     */
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

}
