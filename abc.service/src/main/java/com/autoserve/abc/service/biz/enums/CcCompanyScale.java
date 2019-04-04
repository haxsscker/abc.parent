package com.autoserve.abc.service.biz.enums;


/**
 * 类CcCompanyScale.java的实现描述：企业规模 1: 50人之内 2: 50~500人之间 3: 500人以上
 * 
 * @author fangrui 2014年12月19日 下午2:14:54
 */
public enum CcCompanyScale {
    BLOW_FIFTY_PERSON(1, "50人之内"),
    BETWEEN_FIFTY_AND_FIVEHUNDRED(2, "50~500人之间"),
    OVER_FIVEHUNDRED(3, "500人以上");

    CcCompanyScale(int state, String des) {
        this.state = state;
        this.des = des;
    }

    public int getState() {
        return state;
    }

    public String getDes() {
        return des;
    }

    public final int    state;
    public final String des;

    public static CcCompanyScale valueOf(Integer state) {
        for (CcCompanyScale ccCompanyScale : values()) {
            if (state != null && ccCompanyScale.state == state) {
                return ccCompanyScale;
            }
        }

        return null;
    }

}
