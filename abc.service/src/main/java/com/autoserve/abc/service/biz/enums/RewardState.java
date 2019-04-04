package com.autoserve.abc.service.biz.enums;

/**
 * @author RJQ 2015/1/5 10:55.
 */
public enum RewardState {

    NOT_USED(0, "未使用"),

    USED(1, "已使用");

    RewardState(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public static RewardState valueOf(Integer state) {
        for (RewardState value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }

    public final int state;
    public final String des;
}
