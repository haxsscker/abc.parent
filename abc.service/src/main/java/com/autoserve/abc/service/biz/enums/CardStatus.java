package com.autoserve.abc.service.biz.enums;

public enum CardStatus {
	
    /**停用**/
    STATE_DISABLE(0, "停用"),

    /**启用**/
    STATE_ENABLE(1, "启用");

    CardStatus(int state, String des) {
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

    public static CardStatus valueOf(Integer state) {
        for (CardStatus cardStatus : values()) {
            if (state != null && cardStatus.state == state) {
                return cardStatus;
            }
        }

        return null;
    }
}
