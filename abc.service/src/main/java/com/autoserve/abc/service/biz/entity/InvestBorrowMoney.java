/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

/**
 * 用户资金账户entity
 * 
 * @author J.YL 2014年11月18日 上午11:13:54
 */
public class InvestBorrowMoney {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 投资金额
     */
    private String money;

    /**
     * 期限
     */
    private String loanMonth;

    /**
     * 本息合计
     */
    private String allMoney;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getLoanMonth() {
        return loanMonth;
    }

    public void setLoanMonth(String loanMonth) {
        this.loanMonth = loanMonth;
    }

    public String getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(String allMoney) {
        this.allMoney = allMoney;
    }

}
