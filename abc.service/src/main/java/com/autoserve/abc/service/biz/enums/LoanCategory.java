/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.enums;

/**
 * 项目分类
 * 
 * @author segen189 2014年11月20日 下午9:02:06
 */
public enum LoanCategory {

    /**
     * 个人信用贷
     */
    LOAN_PERSON(1, "信用贷"),

    /**
     * 汽车抵押贷
     */
    LOAN_CAR(2, "抵押贷"),

    /**
     * 房屋抵押贷
     */
    LOAN_HOUSE(3, "担保贷"),

    /**
     * 企业经营贷
     */
    LOAN_CUST(4, "综合贷");

    LoanCategory(int category, String prompt) {
        this.category = category;
        this.prompt = prompt;
    }

    public static LoanCategory valueOf(Integer category) {
        for (LoanCategory value : values()) {
            if (category != null && value.category == category) {
                return value;
            }
        }
        return null;
    }

    public int getCategory() {
        return category;
    }

    public String getPrompt() {
        return prompt;
    }

    public final int    category;
    public final String prompt;
}
