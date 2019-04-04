package com.autoserve.abc.service.biz.enums;

/**
 * 类TransferAction.java的实现描述：TODO 类实现描述
 * 
 * @author Tiuwer 2015年4月23日 上午9:52:55
 */
public enum TransferActionStste {
    INVEST("1", "投标"),
    REPAY("2", "还款"),
    OTHER("3", "其他"),
    STAGE_OTHER("5", "其他");

    TransferActionStste(String state, String des) {
        this.state = state;
        this.des = des;
    }

    public String getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public final String state;
    public final String des;
}
