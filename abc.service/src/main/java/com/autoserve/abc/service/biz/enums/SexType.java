package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：员工/用户性别
 *
 * @author RJQ 2014/11/17 15:38.
 */
public enum SexType {

    /**女**/
    SEX_FEMALE(0, "女"),

    /**男**/
    SEX_MALE(1, "男");

    SexType(int sex, String des) {
        this.sex = sex;
        this.des = des;
    }


    public int getSex() {
        return sex;
    }

    public String getDes() {
        return des;
    }

    public final int sex;
    public final String des;

    public static SexType valueOf(Integer sex) {
        for (SexType sexType : values()) {
            if (sex != null && sexType.sex == sex) {
                return sexType;
            }
        }

        return null;
    }
}
