/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;

/**
 * 类TransferFundsDetailInfo.java的实现描述：资金划转详情Entity
 * 
 * @author J.YL 2015年1月4日 下午6:06:38
 */
public class TransferFundsDetailInfo {

    /**
     * 项目名称
     */
    private String     loanNo;
    /**
     * 借款人
     */
    private String     realName;
    /**
     * 借款金额
     */
    private BigDecimal loanMoney;
    /**
     * 年化率
     */
    private BigDecimal loanRate;
    /**
     * 项目类型
     */
    private String     productName;
    /**
     * 标种
     */
    private Integer    loanType;
    /**
     * 放款金额
     */
    private BigDecimal lendMoney;
    /**
     * 已收手续费
     */
    private BigDecimal collectFee;
    /**
     * 已收担保费
     */
    private BigDecimal collectGuarFee;
    /**
     * 放款时间
     */
    private String     lendDate;

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getLoanType() {
        return loanType;
    }

    public void setLoanType(Integer loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getLendMoney() {
        return lendMoney;
    }

    public void setLendMoney(BigDecimal lendMoney) {
        this.lendMoney = lendMoney;
    }

    public BigDecimal getCollectFee() {
        return collectFee;
    }

    public void setCollectFee(BigDecimal collectFee) {
        this.collectFee = collectFee;
    }

    public BigDecimal getCollectGuarFee() {
        return collectGuarFee;
    }

    public void setCollectGuarFee(BigDecimal collectGuarFee) {
        this.collectGuarFee = collectGuarFee;
    }

    public String getLendDate() {
        return lendDate;
    }

    public void setLendDate(String lendDate) {
        this.lendDate = lendDate;
    }
}
