/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 借款期限单位
 *
 * @author segen189 2014年11月22日 下午4:04:42
 */
public enum LoanPeriodUnit {
    /**
     * 年
     */
    YEAR(1, "年"),

    /**
     * 月
     */
    MONTH(2, "个月"),

    /**
     * 天
     */
    DAY(3, "天");

    LoanPeriodUnit(int unit, String prompt) {
        this.unit = unit;
        this.prompt = prompt;
    }

    public static LoanPeriodUnit valueOf(Integer unit) {
        for (LoanPeriodUnit value : values()) {
            if (unit != null && value.unit == unit) {
                return value;
            }
        }
        return null;
    }

    public int getUnit() {
        return unit;
    }

    public String getPrompt() {
        return prompt;
    }

    private final int    unit;
    private final String prompt;
}
