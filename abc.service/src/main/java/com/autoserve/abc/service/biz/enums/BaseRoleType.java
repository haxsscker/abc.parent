package com.autoserve.abc.service.biz.enums;

/**
 * 
 * 类BaseRoleType.java的实现描述：TODO 类实现描述 
 * @author pp 2014-11-24 下午04:02:07
 */
public enum BaseRoleType {

    //TODO 实例化到数据库
    SYS_ADMIN(1,"管理员"),
    PLATFORM_SERVICE(2, "平台客服"),
    PLATFORM_FINANCIAL(3, "平台财务"),
    LOAN_GOVERNMENT(4, "借贷机构"),
    INSURANCE_GOVERNMENT(5, "担保机构"),
    FINANCIAL_MANAGER(6, "理财经理");

    public final int    index;
    public final String roleName;

    public static BaseRoleType valueOf(Integer index) {
        for (BaseRoleType role : values()) {
            if (index != null && role.index == index) {
                return role;
            }
        }

        return null;
    }

    public static BaseRoleType valueOfRoleName(String roleName) {
        for (BaseRoleType role : values()) {
            if (role.roleName.equals(roleName)) {
                return role;
            }
        }

        return null;
    }

    BaseRoleType(int index, String roleName) {
        this.index = index;
        this.roleName = roleName;
    }
}
