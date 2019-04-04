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
public enum IntentTimeLimitType {

    /**
     * 意向融资期限，302代表15天以内，303代表15-30天，304代表1-3个月，305代表3-6个月，306代表6个月到一年，307代表一年以上
     * ，308代表其他期限
     */
    /**
     * 15天以内
     */
    WITHINHALFMONTHS(302, "15天以内"),

    /**
     * 15-30天
     */
    WITHINHALFANDFULLMONTHS(303, "15-30天"),

    /**
     * 1-3个月
     */
    ONETOTHREEMONTHS(304, "1-3个月"),

    /**
     * 3-6个月
     */
    THREETOSIXMONTHS(305, "3-6个月"),

    /**
     * 6个月到一年
     */
    SIXTOTWELVEMONTHS(306, "6个月到一年"),

    /**
     * 一年以上
     */
    OVERONEYEARS(307, "一年以上");

    IntentTimeLimitType(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public static IntentTimeLimitType valueOf(Integer type) {
        for (IntentTimeLimitType value : values()) {
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
