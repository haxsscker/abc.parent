/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 项目类型
 * 
 * @author yehui 2015年4月14日
 */
public enum LoanType {
    /**
     * 个人信用
     */
    PERSONAL_LOAN(1, "个人贷"),

    /**
     * 抵押
     */
    ENTERPRISE_LOAN(2, "企业贷");

    LoanType(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public static LoanType valueOf(Integer type) {
        for (LoanType value : values()) {
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
