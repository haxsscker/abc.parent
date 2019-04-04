/**
 * Copyright (C) 2006-2012 Tuniu All rights reserved
 * Author: 
 * Date: Tue Dec 02 13:24:46 CST 2014
 * Description:
 */
package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  FundCheckDO
 *  abc_fund_check
 */
public class FundCheckDO {
    /**
     * 主键ID
     * abc_fund_check.fc_check_id
     */
    private Integer fcCheckId;

    /**
     * 预约ID(外键表：abc_fund_order)
     * abc_fund_check.fc_order_id
     */
    private Integer fcOrderId;

    /**
     * 审核人ID(外键表：abc_employee)
     * abc_fund_check.fc_check_emp
     */
    private Integer fcCheckEmp;

    /**
     * 审核日期
     * abc_fund_check.fc_check_date
     */
    private Date fcCheckDate;

    /**
     * 审核意见
     * abc_fund_check.fc_check_idear
     */
    private String fcCheckIdear;

    /**
     * 充值金额
     * abc_fund_check.fc_recharge_money
     */
    private BigDecimal fcRechargeMoney;

    /**
     * 充值日期
     * abc_fund_check.fc_recharge_date
     */
    private Date fcRechargeDate;

    /**
     * 审核状态(1：已确认 2：已放弃)
     * abc_fund_check.fc_check_state
     */
    private Integer fcCheckState;

    public Integer getFcCheckId() {
        return fcCheckId;
    }

    public void setFcCheckId(Integer fcCheckId) {
        this.fcCheckId = fcCheckId;
    }

    public Integer getFcOrderId() {
        return fcOrderId;
    }

    public void setFcOrderId(Integer fcOrderId) {
        this.fcOrderId = fcOrderId;
    }

    public Integer getFcCheckEmp() {
        return fcCheckEmp;
    }

    public void setFcCheckEmp(Integer fcCheckEmp) {
        this.fcCheckEmp = fcCheckEmp;
    }

    public Date getFcCheckDate() {
        return fcCheckDate;
    }

    public void setFcCheckDate(Date fcCheckDate) {
        this.fcCheckDate = fcCheckDate;
    }

    public String getFcCheckIdear() {
        return fcCheckIdear;
    }

    public void setFcCheckIdear(String fcCheckIdear) {
        this.fcCheckIdear = fcCheckIdear;
    }

    public BigDecimal getFcRechargeMoney() {
        return fcRechargeMoney;
    }

    public void setFcRechargeMoney(BigDecimal fcRechargeMoney) {
        this.fcRechargeMoney = fcRechargeMoney;
    }

    public Date getFcRechargeDate() {
        return fcRechargeDate;
    }

    public void setFcRechargeDate(Date fcRechargeDate) {
        this.fcRechargeDate = fcRechargeDate;
    }

    public Integer getFcCheckState() {
        return fcCheckState;
    }

    public void setFcCheckState(Integer fcCheckState) {
        this.fcCheckState = fcCheckState;
    }
}
