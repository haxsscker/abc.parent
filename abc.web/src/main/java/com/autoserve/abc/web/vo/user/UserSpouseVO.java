package com.autoserve.abc.web.vo.user;

import java.math.BigDecimal;

/**
 * 类UserSpouseVO.java
 * 
 * @author fangrui 2014年12月18日 上午10:21:53
 */

public class UserSpouseVO {

    /**
     * 主键id abc_user_spouse.us_id
     */
    private Integer    usId;

    /**
     * 用户id abc_user_spouse.us_user_id
     */
    private Integer    usUserId;

    /**
     * 配偶姓名 abc_user_spouse.us_name
     */
    private String     usName;

    /**
     * 配偶收入 abc_user_spouse.us_income
     */
    private BigDecimal usIncome;

    /**
     * 配偶移动电话 abc_user_spouse.us_phone
     */
    private String     usPhone;

    /**
     * 配偶工作电话 abc_user_spouse.us_work_phone
     */
    private String     usWorkPhone;

    /**
     * 配偶工作单位 abc_user_spouse.us_work_company
     */
    private String     usWorkCompany;

    /**
     * 配偶职位 abc_user_spouse.us_position
     */
    private String     usPosition;

    /**
     * 配偶工作地点 abc_user_spouse.us_work_address
     */
    private String     usWorkAddress;

    /**
     * @return abc_user_spouse.us_id
     */
    public Integer getUsId() {
        return usId;
    }

    /**
     * @param Integer usId (abc_user_spouse.us_id )
     */
    public void setUsId(Integer usId) {
        this.usId = usId;
    }

    /**
     * @return abc_user_spouse.us_user_id
     */
    public Integer getUsUserId() {
        return usUserId;
    }

    /**
     * @param Integer usUserId (abc_user_spouse.us_user_id )
     */
    public void setUsUserId(Integer usUserId) {
        this.usUserId = usUserId;
    }

    /**
     * @return abc_user_spouse.us_name
     */
    public String getUsName() {
        return usName;
    }

    /**
     * @param String usName (abc_user_spouse.us_name )
     */
    public void setUsName(String usName) {
        this.usName = usName == null ? "" : usName.trim();
    }

    /**
     * @return abc_user_spouse.us_income
     */
    public BigDecimal getUsIncome() {
        return usIncome;
    }

    /**
     * @param BigDecimal usIncome (abc_user_spouse.us_income )
     */
    public void setUsIncome(BigDecimal usIncome) {
        this.usIncome = usIncome;
    }

    /**
     * @return abc_user_spouse.us_phone
     */
    public String getUsPhone() {
        return usPhone;
    }

    /**
     * @param String usPhone (abc_user_spouse.us_phone )
     */
    public void setUsPhone(String usPhone) {
        this.usPhone = usPhone == null ? "" : usPhone.trim();
    }

    /**
     * @return abc_user_spouse.us_work_phone
     */
    public String getUsWorkPhone() {
        return usWorkPhone;
    }

    /**
     * @param String usWorkPhone (abc_user_spouse.us_work_phone )
     */
    public void setUsWorkPhone(String usWorkPhone) {
        this.usWorkPhone = usWorkPhone == null ? "" : usWorkPhone.trim();
    }

    /**
     * @return abc_user_spouse.us_work_company
     */
    public String getUsWorkCompany() {
        return usWorkCompany;
    }

    /**
     * @param String usWorkCompany (abc_user_spouse.us_work_company )
     */
    public void setUsWorkCompany(String usWorkCompany) {
        this.usWorkCompany = usWorkCompany == null ? "" : usWorkCompany.trim();
    }

    /**
     * @return abc_user_spouse.us_position
     */
    public String getUsPosition() {
        return usPosition;
    }

    /**
     * @param String usPosition (abc_user_spouse.us_position )
     */
    public void setUsPosition(String usPosition) {
        this.usPosition = usPosition == null ? "" : usPosition.trim();
    }

    /**
     * @return abc_user_spouse.us_work_address
     */
    public String getUsWorkAddress() {
        return usWorkAddress;
    }

    /**
     * @param String usWorkAddress (abc_user_spouse.us_work_address )
     */
    public void setUsWorkAddress(String usWorkAddress) {
        this.usWorkAddress = usWorkAddress == null ? "" : usWorkAddress.trim();
    }

}
