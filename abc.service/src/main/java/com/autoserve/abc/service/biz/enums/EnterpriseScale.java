package com.autoserve.abc.service.biz.enums;

/**
 * @author RJQ 2014/12/18 17:50.
 */
public enum EnterpriseScale {
    /**
     * 企业规模	"0:50人以下
     * 1:50-500人
     * 2:500人以上"
     */
    SMALL_SCALE(0, "50人以下"),
    MEDIUM_SCALE(1, "50-500人"),
    LARGE_SCALE(2, "500人以上");

    EnterpriseScale(int scale, String des) {
        this.scale = scale;
        this.des = des;
    }

    public int getScale() {
        return scale;
    }

    public String getDes() {
        return des;
    }

    public final int scale;
    public final String des;

    public static EnterpriseScale valueOf(Integer scale) {
        for (EnterpriseScale enterpriseScale : values()) {
            if (scale != null && enterpriseScale.scale == scale) {
                return enterpriseScale;
            }
        }

        return null;
    }
}
