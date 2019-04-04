package com.autoserve.abc.web.vo.plan;

import java.math.BigDecimal;

/**
 * 类PaymentPlanVO.java的实现描述：TODO 类实现描述
 *
 * @author liuwei 2014年12月19日 上午10:56:52
 */
public class PaymentPlanVO {
    /**
     * 应还日期
     */
    private String     pro_pay_date;
    /**
     * 应还本金
     */
    private BigDecimal pro_pay_money;
    /**
     * 应还利息
     */
    private BigDecimal pro_pay_rate;
    /**
     * 逾期罚金
     */
    private BigDecimal DeInterest;
    /**
     * 违约罚金
     * add by 夏同同   20160412
     */
    private BigDecimal deBreachInterest;
    /**
     * 应还服务费
     */
    private BigDecimal pro_pay_serve_fee;
    /**
     * 应还总额
     */
    private BigDecimal pro_pay_total;
    /**
     * 实还日期
     */
    private String     pro_collect_date;
    /**
     * 已还本金
     */
    private BigDecimal pro_collect_money;
    /**
     * 已还利息
     */
    private BigDecimal pro_collect_rate;
    /**
     * 已还逾期罚金
     */
    private BigDecimal pro_collect_over_rate;
    /**
     * 已还违约罚金
     * add by 夏同同   20160412
     */
    private BigDecimal pro_collect_breach_rate;
    
    /**
     * 实还平台服务费
     */
    private BigDecimal pro_collect_serve_fee;
    /**
     * 是否结清
     */
    private Boolean pro_is_clear;
    /**
     * 备注
     */
    private String pro_pay_type;

    public String getPro_pay_date() {
        return pro_pay_date;
    }

    public void setPro_pay_date(String pro_pay_date) {
        this.pro_pay_date = pro_pay_date;
    }

    public BigDecimal getPro_pay_money() {
        return pro_pay_money;
    }

    public void setPro_pay_money(BigDecimal pro_pay_money) {
        this.pro_pay_money = pro_pay_money;
    }

    public BigDecimal getPro_pay_rate() {
        return pro_pay_rate;
    }

    public void setPro_pay_rate(BigDecimal pro_pay_rate) {
        this.pro_pay_rate = pro_pay_rate;
    }

    public BigDecimal getDeInterest() {
        return DeInterest;
    }

    public void setDeInterest(BigDecimal deInterest) {
        DeInterest = deInterest;
    }

    public BigDecimal getPro_pay_serve_fee() {
        return pro_pay_serve_fee;
    }

    public void setPro_pay_serve_fee(BigDecimal pro_pay_serve_fee) {
        this.pro_pay_serve_fee = pro_pay_serve_fee;
    }

    public BigDecimal getPro_pay_total() {
        return pro_pay_total;
    }

    public void setPro_pay_total(BigDecimal pro_pay_total) {
        this.pro_pay_total = pro_pay_total;
    }

    public String getPro_collect_date() {
        return pro_collect_date;
    }

    public void setPro_collect_date(String pro_collect_date) {
        this.pro_collect_date = pro_collect_date;
    }

    public BigDecimal getPro_collect_money() {
        return pro_collect_money;
    }

    public void setPro_collect_money(BigDecimal pro_collect_money) {
        this.pro_collect_money = pro_collect_money;
    }

    public BigDecimal getPro_collect_rate() {
        return pro_collect_rate;
    }

    public void setPro_collect_rate(BigDecimal pro_collect_rate) {
        this.pro_collect_rate = pro_collect_rate;
    }

    public BigDecimal getPro_collect_over_rate() {
        return pro_collect_over_rate;
    }

    public void setPro_collect_over_rate(BigDecimal pro_collect_over_rate) {
        this.pro_collect_over_rate = pro_collect_over_rate;
    }

    public Boolean getPro_is_clear() {
        return pro_is_clear;
    }

    public void setPro_is_clear(Boolean pro_is_clear) {
        this.pro_is_clear = pro_is_clear;
    }

    public String getPro_pay_type() {
        return pro_pay_type;
    }

    public void setPro_pay_type(String pro_pay_type) {
        this.pro_pay_type = pro_pay_type;
    }

	public BigDecimal getDeBreachInterest() {
		return deBreachInterest;
	}

	public void setDeBreachInterest(BigDecimal deBreachInterest) {
		this.deBreachInterest = deBreachInterest;
	}

	public BigDecimal getPro_collect_breach_rate() {
		return pro_collect_breach_rate;
	}

	public void setPro_collect_breach_rate(BigDecimal pro_collect_breach_rate) {
		this.pro_collect_breach_rate = pro_collect_breach_rate;
	}

	public BigDecimal getPro_collect_serve_fee() {
		return pro_collect_serve_fee;
	}

	public void setPro_collect_serve_fee(BigDecimal pro_collect_serve_fee) {
		this.pro_collect_serve_fee = pro_collect_serve_fee;
	}
}
