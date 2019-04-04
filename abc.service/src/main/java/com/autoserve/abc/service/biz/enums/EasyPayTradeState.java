/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 易生支付交易状态
 * 
 * @author J.YL 2014年11月24日 下午1:50:59
 */
public enum EasyPayTradeState {
    WAIT_BUYER_PAY("WAIT_BUYER_PAY", "等待支付"),
    TRADE_FINISHED("TRADE_FINISHED", "交易成功"),
    TRADE_FAILURE("TRADE_FAILURE", "交易失败");
    EasyPayTradeState(String state, String des) {
        this.state = state;
        this.des = des;
    }

    public String getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public final String state;
    public final String des;

}
