package com.autoserve.abc.service.biz.enums;
/**
 * 
 * 类EmailState.java的实现描述：邮箱绑定状态
 * @author ipeng 2014年12月22日 下午2:25:28
 */
public enum EmailState {

    DISABLE(0, "未绑定"),
    ENABLE(1, "已绑定");

    EmailState(int state, String des) {
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

    public static EmailState valueOf(Integer state) {
        for (EmailState emailState : values()) {
            if (state != null && emailState.state == state) {
                return emailState;
            }
        }

        return null;
    }
}
