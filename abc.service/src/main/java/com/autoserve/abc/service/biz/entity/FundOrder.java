/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue Dec 02 13:24:46 CST 2014
 * Description:
 */
package com.autoserve.abc.service.biz.entity;

import java.util.Date;

import com.autoserve.abc.service.biz.enums.FundOrderState;

/**
 * FundOrder abc_fund_order
 */
/**
 * 类FundOrder.java的实现描述：TODO 类实现描述
 * 
 * @author dongxuejiao 2014年12月23日 下午6:31:12
 */
public class FundOrder {
    /**
     * 主键ID abc_fund_order.fo_order_id
     */
    private Integer        foOrderId;

    /**
     * 有限合伙项目ID(外键表：abc_fund_apply) abc_fund_order.fo_fund_id
     */
    private Integer        foFundId;

    /**
     * 预约人 abc_fund_order.fo_user_id
     */
    private Integer        foUserId;

    /**
     * 预约日期 abc_fund_order.fo_order_date
     */
    private Date           foOrderDate;

    /**
     * 预约人姓名 abc_fund_order.fo_user_name
     */
    private String         foUserName;

    /**
     * 预约人电话 abc_fund_order.fo_user_phone
     */
    private String         foUserPhone;

    /**
     * 预约状态(0：待审核 1：已确认 2：已放弃) abc_fund_order.fo_order_state
     */
    private FundOrderState foOrderState;

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

    public FundOrderState getFoOrderState() {
        return foOrderState;
    }

    public void setFoOrderState(FundOrderState foOrderState) {
        this.foOrderState = foOrderState;
    }

}
