package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：婚姻状况
 *
 * @author RJQ 2014/11/17 20:27.
 */
public enum MaritalStatus {

    MARRIED(1, "已婚"),
    NOT_MARRIED(2, "未婚"),
    DIVORCE(3, "离婚"),
    WIDOWED(4, "丧偶");

    MaritalStatus(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public final int state;
    public final String des;

    public static MaritalStatus valueOf(Integer state) {
        for (MaritalStatus maritalStatus : values()) {
            if (state != null && maritalStatus.state == state) {
                return maritalStatus;
            }
        }

        return null;
    }
}
