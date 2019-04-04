package com.autoserve.abc.service.biz.enums;

/**
 * 类MenuBtnOpType.java的实现描述：对菜单关联按钮的操作类型
 * 
 * @author pp 2014年11月17日 下午3:52:16
 */
public enum MenuBtnOpType {

    ADD_BTN(1, "菜单添加按钮"),
    DELETE_BTN(2, "菜单删除按钮");
    int    value;
    String des;

    MenuBtnOpType(int value, String des) {
        this.value = value;
        this.des = des;
    }
}
