package com.autoserve.abc.service.biz.enums;

public enum CardType {
    DEBIT_CARD(0, "借记卡"),
    CREDIT_CARD(1, "信用卡");
    /**
     * 
     */
    CardType(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    public final int    type;
    public final String des;

    public static CardType valueOf(Integer type) {
        for (CardType cardType : values()) {
            if (type != null && cardType.type == type) {
                return cardType;
            }
        }

        return null;
    }
}
