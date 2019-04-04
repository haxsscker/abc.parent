/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 类FundOrderState.java的实现描述：TODO 类实现描述
 * 
 * @author wangyongheng 2014/12/08
 */
public enum FundOrderState {
	PENDING_REVIEW(0, "待审核"),
	CONFIRMED(1, "已确认"),
	IS_GIVE_UP(2, "已放弃");
    
    FundOrderState(int orderState, String des) {
        this.orderState = orderState;
        this.des = des;
    }

    public int getOrderState() {
        return orderState;
    }

    public String getDes() {
        return des;
    }

    private final int    orderState;
    private final String des;
    
    public static FundOrderState valueOf(Integer orderState) {
        for (FundOrderState value : values()) {
            if (orderState != null && value.orderState == orderState) {
                return value;
            }
        }
        return null;
    }
}
