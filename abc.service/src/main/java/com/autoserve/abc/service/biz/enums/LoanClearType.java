/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 结算方式
 *
 * @author segen189 2014年11月20日 下午9:02:06
 */
public enum LoanClearType {
    /**
     * 固定还款日
     */
    FIXED_DAY(1, "固定还款日"),

    /**
     * 非固定还款日
     */
    UN_FIXED_DAY(2, "非固定还款日");

    LoanClearType(int clearType, String prompt) {
        this.clearType = clearType;
        this.prompt = prompt;
    }

    public static LoanClearType valueOf(Integer clearType) {
        for (LoanClearType value : values()) {
            if (clearType != null && value.clearType == clearType) {
                return value;
            }
        }
        return null;
    }

    public int getClearType() {
        return clearType;
    }

    public String getPrompt() {
        return prompt;
    }

    public final int    clearType;
    public final String prompt;
}
