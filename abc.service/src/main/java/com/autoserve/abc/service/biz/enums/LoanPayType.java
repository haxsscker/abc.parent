/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 还款方式
 * 
 * @author segen189 2014年11月20日 下午9:02:06
 */
public enum LoanPayType {
    /**
     * 等额本息
     */
    DEBX(1, "等额本息"),

    /**
     * 按月还息到期还本
     */
    AYHX_DQHB(2, "按月付息到期还本"),

    /**
     * 等额本金
     */
    DEBJ(3, "等额本金"),

    /**
     * 到期本息
     */
    DQBX(4, "利随本清"),
    /**
     * 等本等息
     */
    DBDX(5, "等本等息");

    LoanPayType(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public static LoanPayType valueOf(Integer type) {
        for (LoanPayType value : values()) {
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
