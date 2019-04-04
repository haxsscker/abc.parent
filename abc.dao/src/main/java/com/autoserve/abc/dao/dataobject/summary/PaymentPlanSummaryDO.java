package com.autoserve.abc.dao.dataobject.summary;

import java.math.BigDecimal;
import java.util.Date;

/**
 * PaymentPlanDO abc_plan_payment
 */
public class PaymentPlanSummaryDO {

    /**
     * 主键id abc_plan_payment.pp_id
     */
    private Integer    totalId;

    /**
     * 实还本金 abc_plan_payment.pp_pay_collect_capital
     */
    private BigDecimal totalPayCollectCapital;

    /**
     * 实还利息 abc_plan_payment.pp_pay_collect_interest
     */
    private BigDecimal totalPayCollectInterest;

    /**
     * 实还罚金 abc_plan_payment.pp_pay_collect_fine
     */
    private BigDecimal totalPayCollectFine;

    /**
     * 实还平台服务费 abc_plan_payment.pp_collect_service_fee
     */
    private BigDecimal totalCollectServiceFee;

    /**
     * 实还担保服务费 abc_plan_payment.pp_collect_guar_fee
     */
    private BigDecimal totalCollectGuarFee;

    /**
     * 实还总额 abc_plan_payment.pp_collect_total
     */
    private BigDecimal totalCollectTotal;

    /**
     * 实还日期 abc_plan_payment.pp_collecttime
     */
    private Date       totalCollecttime;

    /**
     * 还款状态 －1:未激活 0:未还清 1:付款中 2:正常还清 3:平台代清 4:强制还清
     * abc_plan_payment.pp_pay_state
     */
    private Integer    payState;

    public Integer getTotalId() {
        return totalId;
    }

    public void setTotalId(Integer totalId) {
        this.totalId = totalId;
    }

    public BigDecimal getTotalPayCollectCapital() {
        return totalPayCollectCapital;
    }

    public void setTotalPayCollectCapital(BigDecimal totalPayCollectCapital) {
        this.totalPayCollectCapital = totalPayCollectCapital;
    }

    public BigDecimal getTotalPayCollectInterest() {
        return totalPayCollectInterest;
    }

    public void setTotalPayCollectInterest(BigDecimal totalPayCollectInterest) {
        this.totalPayCollectInterest = totalPayCollectInterest;
    }

    public BigDecimal getTotalPayCollectFine() {
        return totalPayCollectFine;
    }

    public void setTotalPayCollectFine(BigDecimal totalPayCollectFine) {
        this.totalPayCollectFine = totalPayCollectFine;
    }

    public BigDecimal getTotalCollectServiceFee() {
        return totalCollectServiceFee;
    }

    public void setTotalCollectServiceFee(BigDecimal totalCollectServiceFee) {
        this.totalCollectServiceFee = totalCollectServiceFee;
    }

    public BigDecimal getTotalCollectGuarFee() {
        return totalCollectGuarFee;
    }

    public void setTotalCollectGuarFee(BigDecimal totalCollectGuarFee) {
        this.totalCollectGuarFee = totalCollectGuarFee;
    }

    public BigDecimal getTotalCollectTotal() {
        return totalCollectTotal;
    }

    public void setTotalCollectTotal(BigDecimal totalCollectTotal) {
        this.totalCollectTotal = totalCollectTotal;
    }

    public Date getTotalCollecttime() {
        return totalCollecttime;
    }

    public void setTotalCollecttime(Date totalCollecttime) {
        this.totalCollecttime = totalCollecttime;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

}
