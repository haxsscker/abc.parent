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
public class PayMentBorrowMoney {

    /**
     * 还款日
     */
    private String date;

    /**
     * 本金
     */
    private String money;

    /**
     * 利息
     */
    private String allMoney;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(String allMoney) {
        this.allMoney = allMoney;
    }

}
