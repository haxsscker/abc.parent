package com.autoserve.abc.service.biz.enums;

/**
 * @author RJQ 2015/1/5 10:55.
 */
public enum ValidState {

    NOT_VALID_STATE(0, "未生效"),

    VALID_STATE(1,"已生效") ;

    ValidState(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public static ValidState valueOf(Integer state) {
        for (ValidState value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }

    public final int state;
    public final String des;
}
