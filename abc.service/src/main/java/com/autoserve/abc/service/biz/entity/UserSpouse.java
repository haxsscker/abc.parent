package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;

public class UserSpouse {
    /**
     * 主键id
     * abc_user_spouse.us_id
     */
    private Integer usId;

    /**
     * 用户id
     * abc_user_spouse.us_user_id
     */
    private Integer usUserId;

    /**
     * 配偶姓名
     * abc_user_spouse.us_name
     */
    private String usName;

    /**
     * 配偶收入
     * abc_user_spouse.us_income
     */
    private BigDecimal usIncome;

    /**
     * 配偶移动电话
     * abc_user_spouse.us_phone
     */
    private String usPhone;

    /**
     * 配偶工作电话
     * abc_user_spouse.us_work_phone
     */
    private String usWorkPhone;

    /**
     * 配偶工作单位
     * abc_user_spouse.us_work_company
     */
    private String usWorkCompany;

    /**
     * 配偶职位
     * abc_user_spouse.us_position
     */
    private String usPosition;

    /**
     * 配偶工作地点
     * abc_user_spouse.us_work_address
     */
    private String usWorkAddress;

    public Integer getUsId() {
        return usId;
    }

    public void setUsId(Integer usId) {
        this.usId = usId;
    }

    public Integer getUsUserId() {
        return usUserId;
    }

    public void setUsUserId(Integer usUserId) {
        this.usUserId = usUserId;
    }

    public String getUsName() {
        return usName;
    }

    public void setUsName(String usName) {
        this.usName = usName;
    }

    public BigDecimal getUsIncome() {
        return usIncome;
    }

    public void setUsIncome(BigDecimal usIncome) {
        this.usIncome = usIncome;
    }

    public String getUsPhone() {
        return usPhone;
    }

    public void setUsPhone(String usPhone) {
        this.usPhone = usPhone;
    }

    public String getUsWorkPhone() {
        return usWorkPhone;
    }

    public void setUsWorkPhone(String usWorkPhone) {
        this.usWorkPhone = usWorkPhone;
    }

    public String getUsWorkCompany() {
        return usWorkCompany;
    }

    public void setUsWorkCompany(String usWorkCompany) {
        this.usWorkCompany = usWorkCompany;
    }

    public String getUsPosition() {
        return usPosition;
    }

    public void setUsPosition(String usPosition) {
        this.usPosition = usPosition;
    }

    public String getUsWorkAddress() {
        return usWorkAddress;
    }

    public void setUsWorkAddress(String usWorkAddress) {
        this.usWorkAddress = usWorkAddress;
    }
}
