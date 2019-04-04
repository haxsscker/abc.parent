package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：教育水平
 *
 * @author RJQ 2014/11/18 9:28.
 */
public enum EducationLevel {

    PRIMARY(0,"小学"),
    JUNIOR_HIGH(1,"初中"),
    SENIOR_HIGH(2,"高中"),
    TECHNICAL_SECONDARY(3,"中专"),
    JUNIOR_COLLEGE(4,"专科"),
    UNDERGRADUATE(5,"本科"),
    GRADUATE(6,"研究生"),
    DOCTOR(7,"博士"),
    POST_DOCTOR(8,"博士后"),
    OTHER(9,"其他");

    EducationLevel(int level, String des) {
        this.level = level;
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public int getLevel() {

        return level;
    }

    public final int level;
    public final String des;
    public static EducationLevel valueOf(Integer level) {
        for (EducationLevel value : values()) {
            if (level != null && value.level == level) {
                return value;
            }
        }
        return null;
    }

}
