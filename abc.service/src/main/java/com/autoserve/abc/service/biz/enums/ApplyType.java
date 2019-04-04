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
public enum ApplyType {

    /**
     * 借款方式 0 个人、1 企业
     */

    /**
     * 一年以上
     */
    PERSON(0, "个人"),

    ENTERPRISE(1, "企业");

    ApplyType(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public static ApplyType valueOf(Integer type) {
        for (ApplyType value : values()) {
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
