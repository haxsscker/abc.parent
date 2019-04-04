package com.autoserve.abc.service.biz.enums;

/**
 * @author RJQ 2014/11/18 17:58.
 */
public enum GovUpdateField {

    /**
     * 字段名称*
     */
    FIELD("field"),

    /**
     * 字段修改前的值*
     */
    FIELD_OLD("field_old"),

    /**
     * 字段修改后的值*
     */
    FIELD_NEW("field_new");


    public String getValue() {
        return value;
    }

    GovUpdateField(String value) {

        this.value = value;
    }

    public final String value;
}
