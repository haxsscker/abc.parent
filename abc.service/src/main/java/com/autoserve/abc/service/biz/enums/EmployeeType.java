package com.autoserve.abc.service.biz.enums;

/**
 * 类的实现描述：员工类型
 *
 * @author RJQ 2014/11/17 15:38.
 */
public enum EmployeeType {

    /**
     * 平台员工*
     */
    PLATFORM_EMP(1, "平台员工"),

    /**
     * 小贷/担保机构员工*
     */
    LOAN_GUAR_GOVERNMENT_EMP(2, "小贷/担保机构员工");

    EmployeeType(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    public final int type;
    public final String des;
}
