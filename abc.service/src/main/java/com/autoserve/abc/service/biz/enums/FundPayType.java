/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 类FundPayType.java的实现描述：TODO 类实现描述
 * 
 * @author wangyongheng 2014/12/04
 */
public enum FundPayType {
	EQUAL_CORPUS_INTERESTS(1, "等额本息"),
	MONTHLY_INTEREST_CORPUS_BACK(2, "按月还息到期还本"),
	EQUAL_CORPUS(3, "等额本金");
    
    FundPayType(int payType, String des) {
        this.payType = payType;
        this.des = des;
    }

    public int getType() {
        return payType;
    }

    public String getDes() {
        return des;
    }

    private final int    payType;
    private final String des;
    
    public static FundPayType valueOf(Integer payType) {
        for (FundPayType value : values()) {
            if (payType != null && value.payType == payType) {
                return value;
            }
        }
        return null;
    }
}
