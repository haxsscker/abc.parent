package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：邀请用户类别
 *
 * @author RJQ 2014/11/17 20:25.
 */
public enum InviteUserType {

    PERSONAL(1, "前台用户"),
    PARTNER(2, "平台用户");

    InviteUserType(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    public static InviteUserType valueOf(Integer type) {
        for (InviteUserType value : values()) {
            if (type != null && value.type == type) {
                return value;
            }
        }
        return null;
    }

    public final int    type;
    public final String des;
}
