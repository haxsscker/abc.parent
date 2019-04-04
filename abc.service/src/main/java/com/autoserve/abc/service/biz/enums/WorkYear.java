package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：工作年限 1：一年以内 2：一年以上 3：二年以上
 * 4：三年以上 5：四年以上 6：五年以上 7 六年以上
 *
 * @author RJQ 2014/11/17 20:50.
 */
public enum WorkYear {

    LESS_THAN_ONE_YEAR(1, "1年之内"),
    ONE_TO_TWO_YEAR(2, "1-2年"),
    TWO_TO_THREE_YEAR(3, "2-3年"),
    THREE_TO_FOUR_YEAR(4, "3-4年"),
    FOUR_TO_FIVE_YEAR(5, "4-5年"),
    FIVE_TO_SIX_YEAR(6, "5-6年"),
    MORE_THAN_SIX_YEAR(7, "6年以上");

    WorkYear(int state, String prompt) {
        this.state = state;
        this.prompt = prompt;
    }

    public static WorkYear valueOf(Integer state) {
        for (WorkYear wy : values()) {
            if (wy != null && wy.state == state) {
                return wy;
            }
        }

        return null;
    }

    public int getState() {
        return state;
    }

    public String getPrompt() {
        return prompt;
    }

    public final int state;
    public final String prompt;
}
