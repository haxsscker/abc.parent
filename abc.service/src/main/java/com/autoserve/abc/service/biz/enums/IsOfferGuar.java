package com.autoserve.abc.service.biz.enums;

public enum IsOfferGuar {
    /**
     * 是否提供担保	1：是 0：否
     */
    NOT_OFFER_GUAR(0, "不提供担保"),
    OFFER_GUAR(1, "提供担保");

    IsOfferGuar(int offer, String des) {
        this.offer = offer;
        this.des = des;
    }

    public int getOffer() {
        return offer;
    }

    public String getDes() {
        return des;
    }

    public static IsOfferGuar valueOf(Integer offer) {
        for (IsOfferGuar isOfferGuar : values()) {
            if (offer != null && isOfferGuar.offer == offer) {
                return isOfferGuar;
            }
        }

        return null;
    }

    public final int offer;
    public final String des;
}
