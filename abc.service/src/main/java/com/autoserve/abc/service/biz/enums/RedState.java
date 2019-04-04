package com.autoserve.abc.service.biz.enums;

/**
 * 红包状态枚举
 * 
 * @author lipeng 2014年12月25日 下午2:50:39
 */
public enum RedState {
    /**
     * 红包的删除状态
     */
    DELETE(-1, "删除"),
    /**
     * 红包的状态失效
     */
    FAILURE(0, "失效"),
    /**
     * 红包的状态有效
     */
    EFFECTIVE(1, "有效");

    RedState(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public final int    state;
    public final String des;

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public static RedState valueOf(Integer state) {
        for (RedState value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }
}
