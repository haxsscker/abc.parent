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
public enum ApplyFromType {

    /**
     * 0代表从我的借入中申请，1代表从我的账户中申请
     */

    /**
     * 一年以上
     */
    MYBORROW(0, "我的借入中申请"),

    MYACCOUNT(1, "账户中申请");

    ApplyFromType(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public static ApplyFromType valueOf(Integer type) {
        for (ApplyFromType value : values()) {
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
