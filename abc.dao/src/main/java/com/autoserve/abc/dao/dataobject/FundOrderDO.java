package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * FundOrderDO abc_fund_order
 */
public class FundOrderDO {
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
    private Date    foOrderDate;

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
    private Integer foOrderState;

    public Integer getFoOrderId() {
        return foOrderId;
    }

    public void setFoOrderId(Integer foOrderId) {
        this.foOrderId = foOrderId;
    }

    public Integer getFoFundId() {
        return foFundId;
    }

    public void setFoFundId(Integer foFundId) {
        this.foFundId = foFundId;
    }

    public Integer getFoUserId() {
        return foUserId;
    }

    public void setFoUserId(Integer foUserId) {
        this.foUserId = foUserId;
    }

    public Date getFoOrderDate() {
        return foOrderDate;
    }

    public void setFoOrderDate(Date foOrderDate) {
        this.foOrderDate = foOrderDate;
    }

    public String getFoUserName() {
        return foUserName;
    }

    public void setFoUserName(String foUserName) {
        this.foUserName = foUserName;
    }

    public String getFoUserPhone() {
        return foUserPhone;
    }

    public void setFoUserPhone(String foUserPhone) {
        this.foUserPhone = foUserPhone;
    }

    public Integer getFoOrderState() {
        return foOrderState;
    }

    public void setFoOrderState(Integer foOrderState) {
        this.foOrderState = foOrderState;
    }
}
