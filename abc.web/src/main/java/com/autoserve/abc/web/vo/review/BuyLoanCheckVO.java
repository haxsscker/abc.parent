package com.autoserve.abc.web.vo.review;

import java.math.BigDecimal;

/**
 * @author yuqing.zheng Created on 2014-12-26,14:05
 */
public class BuyLoanCheckVO {
    /**
     * 项目名称
     */
    private String     pro_loan_no;

    /**
     * 项目id
     */
    private Integer    pro_loan_id;

    /**
     * 收购id
     */
    private Integer    pro_buy_loan_id;

    /**
     * 项目类型
     */
    private String     pdo_product_name;

    /**
     * 借款人
     */
    private String     loan_user_name;

    /**
     * 借款金额
     */
    private double     pro_loan_money;

    /**
     * 年化收益率
     */
    private double     pro_loan_rate;

    /**
     * 借款期限
     */
    private String     pro_loan_period;

    /**
     * 收购人
     */
    private String     cst_real_name;

    /**
     * 收购份额
     */
    private BigDecimal pro_buy_total;
    /**
     * 收购金额（出价）
     */
    private BigDecimal pro_buy_money;

    /**
     * 收购手续费
     */
    private BigDecimal pro_buy_loan_fee;

    /**
     * 申请日期
     */
    private String     pro_buy_loan_date;

    /**
     * 满标日期
     */
    private String     pro_full_date;

    /**
     * 审核状态
     */
    private String     pro_buy_loan_state;

    /**
     * 是否待收购初审 只有当状态为”待收购初审“时为true，其他皆为false
     */
    private boolean    can_check;

    /**
     * 是否收购满标待审 只有当状态为”收购满标待审“时为true，其他皆为false
     */
    private boolean    can_full_check;

    public String getPro_loan_no() {
        return pro_loan_no;
    }

    public void setPro_loan_no(String pro_loan_no) {
        this.pro_loan_no = pro_loan_no;
    }

    public Integer getPro_loan_id() {
        return pro_loan_id;
    }

    public void setPro_loan_id(Integer pro_loan_id) {
        this.pro_loan_id = pro_loan_id;
    }

    public Integer getPro_buy_loan_id() {
        return pro_buy_loan_id;
    }

    public void setPro_buy_loan_id(Integer pro_buy_loan_id) {
        this.pro_buy_loan_id = pro_buy_loan_id;
    }

    public String getPdo_product_name() {
        return pdo_product_name;
    }

    public void setPdo_product_name(String pdo_product_name) {
        this.pdo_product_name = pdo_product_name;
    }

    public String getLoan_user_name() {
        return loan_user_name;
    }

    public void setLoan_user_name(String loan_user_name) {
        this.loan_user_name = loan_user_name;
    }

    public double getPro_loan_money() {
        return pro_loan_money;
    }

    public void setPro_loan_money(double pro_loan_money) {
        this.pro_loan_money = pro_loan_money;
    }

    public double getPro_loan_rate() {
        return pro_loan_rate;
    }

    public void setPro_loan_rate(double pro_loan_rate) {
        this.pro_loan_rate = pro_loan_rate;
    }

    public String getPro_loan_period() {
        return pro_loan_period;
    }

    public void setPro_loan_period(String pro_loan_period) {
        this.pro_loan_period = pro_loan_period;
    }

    public String getCst_real_name() {
        return cst_real_name;
    }

    public void setCst_real_name(String cst_real_name) {
        this.cst_real_name = cst_real_name;
    }

    public BigDecimal getPro_buy_money() {
        return pro_buy_money;
    }

    public void setPro_buy_money(BigDecimal pro_buy_money) {
        this.pro_buy_money = pro_buy_money;
    }

    public BigDecimal getPro_buy_loan_fee() {
        return pro_buy_loan_fee;
    }

    public void setPro_buy_loan_fee(BigDecimal pro_buy_loan_fee) {
        this.pro_buy_loan_fee = pro_buy_loan_fee;
    }

    public String getPro_buy_loan_date() {
        return pro_buy_loan_date;
    }

    public void setPro_buy_loan_date(String pro_buy_loan_date) {
        this.pro_buy_loan_date = pro_buy_loan_date;
    }

    public String getPro_full_date() {
        return pro_full_date;
    }

    public void setPro_full_date(String pro_full_date) {
        this.pro_full_date = pro_full_date;
    }

    public String getPro_buy_loan_state() {
        return pro_buy_loan_state;
    }

    public void setPro_buy_loan_state(String pro_buy_loan_state) {
        this.pro_buy_loan_state = pro_buy_loan_state;
    }

    public boolean isCan_check() {
        return can_check;
    }

    public void setCan_check(boolean can_check) {
        this.can_check = can_check;
    }

    public boolean isCan_full_check() {
        return can_full_check;
    }

    public void setCan_full_check(boolean can_full_check) {
        this.can_full_check = can_full_check;
    }

    /**
     * @return the pro_buy_total
     */
    public BigDecimal getPro_buy_total() {
        return pro_buy_total;
    }

    /**
     * @param pro_buy_total the pro_buy_total to set
     */
    public void setPro_buy_total(BigDecimal pro_buy_total) {
        this.pro_buy_total = pro_buy_total;
    }
}
