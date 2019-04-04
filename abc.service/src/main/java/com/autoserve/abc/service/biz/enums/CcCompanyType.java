package com.autoserve.abc.service.biz.enums;

/**
 * 类CcCompanyType.java的实现描述：企业性质 1：政府机关 2：国有企业 3：台（港、澳）资企业 4：合资企业 5：个体户 6：事业性单位
 * 7：私营企业
 * 
 * @author fangrui 2014年12月19日 下午2:01:22
 */
public enum CcCompanyType {
    GOVERNMENT_AGENCIES(1, "政府机关 "),
    NATIONALIZED_ENTERPRISE(2, "国有企业"),
    TAIWAN_HONGKONG_MACAO_ENTERPRISE(3, "台（港、澳）资企业"),
    JOINT_VENTURE(4, "合资企业 "),
    SMALL_PRIVATE_BUSINESS(5, "个体户"),
    INSTITUTIONAL_UNITS(6, "事业性单位"),
    PRIVATE_ENTERPRISE(7, "私营企业");

    CcCompanyType(int state, String des) {
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

    public static CcCompanyType valueOf(Integer state) {
        for (CcCompanyType ccCompanyType : values()) {
            if (state != null && ccCompanyType.state == state) {
                return ccCompanyType;
            }
        }

        return null;
    }
}
