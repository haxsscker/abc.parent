package com.autoserve.abc.service.biz.enums;
/**
 * 
 * 类RealnameState.java的实现描述：实名认证的枚举
 * @author lipeng 2014年12月27日 下午7:21:41
 */
public enum RealnameState {

    DISABLE(0, "未认证"),
    ENABLE(1, "已认证");

    RealnameState(int state, String des) {
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

    public static RealnameState valueOf(Integer state) {
        for (RealnameState realnameState : values()) {
            if (state != null && realnameState.state == state) {
                return realnameState;
            }
        }

        return null;
    }

}
