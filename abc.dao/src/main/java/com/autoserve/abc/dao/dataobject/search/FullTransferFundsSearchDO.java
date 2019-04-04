/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject.search;

import java.util.Date;

/**
 * 资金划转统计搜索
 *
 * @author J.YL 2015年1月5日 下午3:22:29
 */
public class FullTransferFundsSearchDO {

    /**
     * 项目编号
     */
    private String  loanNo;
    /**
     * 项目种类
     */
    private Integer loanCategory;

    /**
     * 标类型
     */
    private Integer bidType;
    /**
     * 放款日期开始
     */
    private Date    lendDateFrom;
    /**
     * 放款日期结束
     */
    private Date    lendDateTo;

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public Integer getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(Integer loanCategory) {
        this.loanCategory = loanCategory;
    }

    public Date getLendDateFrom() {
        return lendDateFrom;
    }

    public void setLendDateFrom(Date lendDateFrom) {
        this.lendDateFrom = lendDateFrom;
    }

    public Date getLendDateTo() {
        return lendDateTo;
    }

    public void setLendDateTo(Date lendDateTo) {
        this.lendDateTo = lendDateTo;
    }

    public Integer getBidType() {
        return bidType;
    }

    public void setBidType(Integer bidType) {
        this.bidType = bidType;
    }

}
