/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 意向融资期限
 *
 * @author 杜武强
 * @Date 2014年12月20日17:41:32
 */
public enum ApplyMethodType {

    /**
     * 借款类型 1 信用借款、2 抵押借款、3 担保借款
     */

    /**
     * 一年以上
     */
    CREDITLOAN(1, "信用借款"),

    MORTGAGELOAN(2, "抵押借款"),

    GUARANTEELOAN(3, "担保借款");

    ApplyMethodType(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public static ApplyMethodType valueOf(Integer type) {
        for (ApplyMethodType value : values()) {
            if (type != null && value.type == type) {
                return value;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public String getPrompt() {
        return prompt;
    }

    public final int    type;
    public final String prompt;

}
