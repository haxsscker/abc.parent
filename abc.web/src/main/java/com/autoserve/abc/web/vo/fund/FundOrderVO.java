/*
 * This software is the confidential and proprietary information ("Confidential Information"). 
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.fund;

/**
 * 类FundOrderVO.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月23日 下午1:40:06
 */
public class FundOrderVO {
    /**
     * 主键ID abc_fund_order.fo_order_id
     */
    private Integer foOrderId;

    /**
     * 有限合伙项目ID(外键表：abc_fund_apply) abc_fund_order.fo_fund_id
     */
    private Integer foFundId;

    /**
     * 预约人 abc_fund_order.fo_user_id
     */
    private Integer foUserId;

    /**
     * 预约日期 abc_fund_order.fo_order_date
     */
    private String  foOrderDate;

    /**
     * 预约人姓名 abc_fund_order.fo_user_name
     */
    private String  foUserName;

    /**
     * 预约人电话 abc_fund_order.fo_user_phone
     */
    private String  foUserPhone;

    /**
     * 预约状态(0：待审核 1：已确认 2：已放弃) abc_fund_order.fo_order_state
     */
    private String  foOrderState;

    /**
     * @return abc_fund_order.fo_order_id
     */
    public Integer getFoOrderId() {
        return foOrderId;
    }

    /**
     * @param Integer foOrderId (abc_fund_order.fo_order_id )
     */
    public void setFoOrderId(Integer foOrderId) {
        this.foOrderId = foOrderId;
    }

    /**
     * @return abc_fund_order.fo_fund_id
     */
    public Integer getFoFundId() {
        return foFundId;
    }

    /**
     * @param Integer foFundId (abc_fund_order.fo_fund_id )
     */
    public void setFoFundId(Integer foFundId) {
        this.foFundId = foFundId;
    }

    /**
     * @return abc_fund_order.fo_user_id
     */
    public Integer getFoUserId() {
        return foUserId;
    }

    /**
     * @param Integer foUserId (abc_fund_order.fo_user_id )
     */
    public void setFoUserId(Integer foUserId) {
        this.foUserId = foUserId;
    }

    /**
     * @return abc_fund_order.fo_order_date
     */
    public String getFoOrderDate() {
        return foOrderDate;
    }

    /**
     * @param Date foOrderDate (abc_fund_order.fo_order_date )
     */
    public void setFoOrderDate(String foOrderDate) {
        this.foOrderDate = foOrderDate;
    }

    /**
     * @return abc_fund_order.fo_user_name
     */
    public String getFoUserName() {
        return foUserName;
    }

    /**
     * @param String foUserName (abc_fund_order.fo_user_name )
     */
    public void setFoUserName(String foUserName) {
        this.foUserName = foUserName == null ? null : foUserName.trim();
    }

    /**
     * @return abc_fund_order.fo_user_phone
     */
    public String getFoUserPhone() {
        return foUserPhone;
    }

    /**
     * @param String foUserPhone (abc_fund_order.fo_user_phone )
     */
    public void setFoUserPhone(String foUserPhone) {
        this.foUserPhone = foUserPhone == null ? null : foUserPhone.trim();
    }

    /**
     * @return abc_fund_order.fo_order_state
     */
    public String getFoOrderState() {
        return foOrderState;
    }

    /**
     * @param Integer foOrderState (abc_fund_order.fo_order_state )
     */
    public void setFoOrderState(String foOrderState) {
        this.foOrderState = foOrderState;
    }

}
