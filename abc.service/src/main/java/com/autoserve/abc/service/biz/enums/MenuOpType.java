package com.autoserve.abc.service.biz.enums;

/**
 * 类MenuOpType.java的实现描述：菜单操作的类型
 * 
 * @author pp 2014年11月17日 下午3:37:19
 */
public enum MenuOpType {

    ADD(1, "添加菜单"),
    MODIFY(2, "修改菜单"),
    DELETE(3, "删除菜单");

    int    value;
    String des;

    MenuOpType(int value, String des) {
        this.value = value;
        this.des = des;
    }
}
