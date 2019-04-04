package com.autoserve.abc.service.biz.enums;

public enum UserRealnameIsproven {
    YES(1, "是"),
    NO(2, "否");
    UserRealnameIsproven(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public static UserRealnameIsproven valueOf(Integer state) {
        for (UserRealnameIsproven value : values()) {
            if (state != null && value.state == state) {
                return value;
            }
        }
        return null;
    }

    public final int    state;
    public final String des;
}
