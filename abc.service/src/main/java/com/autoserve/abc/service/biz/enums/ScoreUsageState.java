package com.autoserve.abc.service.biz.enums;

/**
 * 积分兑现使用状态
 *
 * @author RJQ 2014/11/25 17:19.
 */
public enum ScoreUsageState {

    NOT_USE(0, "未使用"),

    HAVE_USED(1, "已使用"),

    OUT_OF_TIME(2, "已过期");

    ScoreUsageState(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public final int state;
    public final String des;

    public static ScoreUsageState valueOf(Integer state) {
        for (ScoreUsageState usageState : values()) {
            if (state != null && usageState.state == state) {
                return usageState;
            }
        }

        return null;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }
}