/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 借款人类型
 *
 * @author segen189 2014年11月22日 下午4:04:42
 */
public enum LoaneeType {
    /**
     * 个人
     */
    PERSON(1, "个人"),

    /**
     * 企业
     */
    COMPANY(2, "企业"),

    /**
     * 借款机构
     */
    GOV(3, "借款机构"),

    /**
     * 平台
     */
    PLATFORM(4, "平台");

    LoaneeType(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public static LoaneeType valueOf(Integer type) {
        for (LoaneeType value : values()) {
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

    private final int    type;
    private final String prompt;
}
