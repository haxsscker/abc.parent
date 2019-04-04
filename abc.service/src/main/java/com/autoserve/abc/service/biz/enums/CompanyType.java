package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述: 公司类别
 *
 * @author RJQ 2014/11/17 15:38.
 */
public enum CompanyType {

    GOVERNMENT_OFFICE(1, "政府机关"),
    STATE_OWNED_ENTERPRISE(2, "国有企业"),
    TAIWAN_HONGKONG_MACAO_FUNDED_ENTERPRISE(3, "台港澳资企业"),
    JOINT_VENTURE(4, "合资企业"),
    INDIVIDUAL_HOUSEHOLD(5, "个体户"),
    INSTITUTION(6, "事业单位"),
    PRIVATE_ENTERPRISE(7, "私营企业"),
    OTHER(8, "其他");

    CompanyType(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public static CompanyType valueOf(Integer type) {
        for (CompanyType ct : values()) {
            if (type != null && ct.type == type) {
                return ct;
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

    public final int type;
    public final String prompt;
}
