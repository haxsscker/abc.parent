package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;

/**
 * 类IncomeJDO.java的实现描述：TODO 类实现描述
 *
 * @author liuwei 2014年12月19日 下午2:26:28
 */
public class IncomeJDO {
    /**
     * 还款计划id
     */
    private int        pro_pay_plan_id;
    /**
     * 投资人用户名
     */
    private String     cst_real_name;
    /**
     * 投资人真实姓名
     */
    private String user_real_name;
    /**
     * 投资人手机号
     */
    private String user_phone;
    /**
     * 投资金额
     */
    private BigDecimal pro_invest_money;
    /**
     * 投资日期
     */
    private String     pro_invest_date;
    /**
     * 本期应收本金
     */
    private BigDecimal pro_pay_money;
    /**
     * 本期应收利息
     */
    private BigDecimal pro_pay_rate;
    /**
     * 本期应收罚息
     */
    private BigDecimal pro_pay_over_rate;
    /**
     * 本期实收本金
     */
    private BigDecimal pro_collect_money;
    /**
     * 本期实收利息
     */
    private BigDecimal pro_collect_rate;
    /**
     * 本期实收罚息
     */
    private BigDecimal pro_collect_over_rate;

    public String getUser_real_name() {
		return user_real_name;
	}

	public void setUser_real_name(String user_real_name) {
		this.user_real_name = user_real_name;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public int getPro_pay_plan_id() {
        return pro_pay_plan_id;
    }

    public void setPro_pay_plan_id(int pro_pay_plan_id) {
        this.pro_pay_plan_id = pro_pay_plan_id;
    }

    public String getCst_real_name() {
        return cst_real_name;
    }

    public void setCst_real_name(String cst_real_name) {
        this.cst_real_name = cst_real_name;
    }

    public BigDecimal getPro_invest_money() {
        return pro_invest_money;
    }

    public void setPro_invest_money(BigDecimal pro_invest_money) {
        this.pro_invest_money = pro_invest_money;
    }

    public String getPro_invest_date() {
        return pro_invest_date;
    }

    public void setPro_invest_date(String pro_invest_date) {
        this.pro_invest_date = pro_invest_date;
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

    public BigDecimal getPro_pay_over_rate() {
        return pro_pay_over_rate;
    }

    public void setPro_pay_over_rate(BigDecimal pro_pay_over_rate) {
        this.pro_pay_over_rate = pro_pay_over_rate;
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

}
