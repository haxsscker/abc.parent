package com.autoserve.abc.service.biz.enums;

/**
 * 类MenuVisibleType.java的实现描述：
 * 
 * @author pp 2014年11月19日 下午4:58:46
 */
public enum MenuVisibleType {

    VISIBLE(1, "显示"),
    NOT_VISIBLE(0, "不显示");

    int    value;
    String desc;

    private MenuVisibleType(int value, String des) {
        this.value = value;
        this.desc = des;
    }

}
