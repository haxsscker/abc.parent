package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述: 公司或机构规模
 *
 * @author RJQ 2014/11/17 15:38.
 */
public enum ScaleType {

    LESS_THAN_50(1, "50人之内"),
    BETWEEN_50_AND_500(2, "50-500人之内"),
    MORE_THAN_500(3, "500人以上");

    ScaleType(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public int getType() {
        return type;
    }

    public String getPrompt() {
        return prompt;
    }

    public final int type;
    public final String prompt;

    public static ScaleType valueOf(Integer type) {
        for (ScaleType scaleType : values()) {
            if (type != null && scaleType.type == type) {
                return scaleType;
            }
        }

        return null;
    }
}
