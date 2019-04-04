/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.dao.dataobject.search;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 线下充值审核搜索DO
 *
 * @author J.YL 2015年1月8日 下午3:31:19
 */
public class DownRechargeSearchDO {
    /**
     * 充值人
     */
    private String     userRealName;
    /**
     * 充值金额初始
     */
    private BigDecimal rechargeMoneyFrom;
    /**
     * 充值金额结束
     */
    private BigDecimal rechargeMoneyTo;
    /**
     * 充值日期初始
     */
    private Date       rechargeDateFrom;
    /**
     * 充值日期结束
     */
    private Date       rechargeDateTo;
    /**
     * 充值的银行帐号
     */
    private String     rechargeBankNo;
    /**
     * 审核状态
     */
    private Integer    checkStatus;

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public BigDecimal getRechargeMoneyFrom() {
        return rechargeMoneyFrom;
    }

    public void setRechargeMoneyFrom(BigDecimal rechargeMoneyFrom) {
        this.rechargeMoneyFrom = rechargeMoneyFrom;
    }

    public BigDecimal getRechargeMoneyTo() {
        return rechargeMoneyTo;
    }

    public void setRechargeMoneyTo(BigDecimal rechargeMoneyTo) {
        this.rechargeMoneyTo = rechargeMoneyTo;
    }

    public Date getRechargeDateFrom() {
        return rechargeDateFrom;
    }

    public void setRechargeDateFrom(Date rechargeDateFrom) {
        this.rechargeDateFrom = rechargeDateFrom;
    }

    public Date getRechargeDateTo() {
        return rechargeDateTo;
    }

    public void setRechargeDateTo(Date rechargeDateTo) {
        this.rechargeDateTo = rechargeDateTo;
    }

    public String getRechargeBankNo() {
        return rechargeBankNo;
    }

    public void setRechargeBankNo(String rechargeBankNo) {
        this.rechargeBankNo = rechargeBankNo;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

}
