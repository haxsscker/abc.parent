package com.autoserve.abc.service.biz.enums;

public enum ButtonCanSelectType {

    CAN_SELECT(1, "按钮可以被选取"),
    CAN_NOT_SELECT(0, "按钮不可以被选取");

    public int    value;
    public String des;

    ButtonCanSelectType(int v, String d) {
        this.value = v;
        this.des = d;
    }

}
