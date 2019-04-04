package com.autoserve.abc.service.biz.enums;

public enum ButtonSelectType {

    SELECT(1, "按钮被选取"),
    NOT_SELECT(0, "按钮没有被选取");

    public int    value;
    public String des;

    ButtonSelectType(int v, String d) {
        this.value = v;
        this.des = d;
    }
}
