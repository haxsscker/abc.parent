package com.autoserve.abc.service.biz.enums;

public enum DocType {
    ID_CARD("1", "身份证"),
    TEMPORARY_ID_CARDS("2", "临时身份证"),
    ACCOUNTS_BOOK("3", "户口簿"),
    PASSPORT("4", "护照"),
    MILITARY_OFFICER("5", "军官证");

    DocType(String type, String des) {
        this.type = type;
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    public final String type;
    public final String des;

    public static DocType valueOfStrs(String type) {
        for (DocType docType : values()) {
            if (type != null && docType.type.equals(type)) {
                return docType;
            }
        }

        return null;
    }
}
