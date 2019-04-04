package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentPlanJDO {
	/**
	 * id
	 */
	private Integer pro_plan_id;
	/**
	 * loan_id
	 */
	private Integer pro_loan_id;
	/**
	 * 项目编号
	 */
	private String pro_loan_no;
	/**
	 * 项目类型
	 */
	private Integer pdo_product_name;
	/**
	 * 借款人
	 */
	private String pro_add_emp;
	/**
	 * 借款金额（元）
	 */
	private String pro_loan_money;
	/**
	 * 年化收益率
	 */
	private BigDecimal pro_loan_rate;
	/**
	 * 借款期限
	 */
	private Integer pro_borrowing_period;
	
	/**
	 * 期限类型
	 */
	private Integer pro_borrowing_period_unit;
	/**
	 * 期数
	 */
	private Integer pro_loan_period;
	/**
	 * 总期数
	 */
	private Integer pro_loan_total_period;
	/**
	 * 应还日期
	 */
	private Date pro_pay_date;

	/**
	 * 应还日期
	 */
	private String pro_pay_date_str;
	/**
	 * 逾期天数
	 */
	private Integer pro_overdue_days;
	/**
	 * 本期应还本金
	 */
	private BigDecimal pro_pay_money;
	/**
	 * 本期应还利息
	 */
	private BigDecimal pro_pay_rate;
	/**
	 * 本期应还服务费
	 */
	private BigDecimal pro_service_fee;
	/**
	 * 本期已还服务费
	 */
	private BigDecimal collect_service_fee;
	/**
	 * 本期已还本金
	 */
	private BigDecimal pro_collect_money;
	/**
	 * 本期已还利息
	 */
	private BigDecimal pro_collect_rate;
	/**
	 * 还款方式
	 */
	private String pro_pay_type;
	/**
	 * 担保机构
	 */
	private String gov_name;
	/**
	 * 是否还清
	 */
	private Boolean pro_is_clear;
	/**
	 * 还款类型
	 */
	private Integer ppPayType;
	/**
	 * 还款状态
	 */
	private Integer pro_payment_status;

	public Integer getPro_loan_total_period() {
		return pro_loan_total_period;
	}

	public void setPro_loan_total_period(Integer pro_loan_total_period) {
		this.pro_loan_total_period = pro_loan_total_period;
	}

	public Integer getPpPayType() {
		return ppPayType;
	}

	public void setPpPayType(Integer ppPayType) {
		this.ppPayType = ppPayType;
	}

	public String getPro_loan_no() {
		return pro_loan_no;
	}

	public void setPro_loan_no(String pro_loan_no) {
		this.pro_loan_no = pro_loan_no;
	}

	public Integer getPdo_product_name() {
		return pdo_product_name;
	}

	public void setPdo_product_name(Integer pdo_product_name) {
		this.pdo_product_name = pdo_product_name;
	}

	public String getPro_add_emp() {
		return pro_add_emp;
	}

	public void setPro_add_emp(String pro_add_emp) {
		this.pro_add_emp = pro_add_emp;
	}

	public String getPro_loan_money() {
		return pro_loan_money;
	}

	public void setPro_loan_money(String pro_loan_money) {
		this.pro_loan_money = pro_loan_money;
	}

	public BigDecimal getPro_loan_rate() {
		return pro_loan_rate;
	}

	public void setPro_loan_rate(BigDecimal pro_loan_rate) {
		this.pro_loan_rate = pro_loan_rate;
	}

	public Integer getPro_borrowing_period() {
		return pro_borrowing_period;
	}

	public void setPro_borrowing_period(Integer pro_borrowing_period) {
		this.pro_borrowing_period = pro_borrowing_period;
	}

	public Integer getPro_loan_period() {
		return pro_loan_period;
	}

	public void setPro_loan_period(Integer pro_loan_period) {
		this.pro_loan_period = pro_loan_period;
	}

	public Date getPro_pay_date() {
		return pro_pay_date;
	}

	public void setPro_pay_date(Date pro_pay_date) {
		this.pro_pay_date = pro_pay_date;
	}

	public String getPro_pay_date_str() {
		return pro_pay_date_str;
	}

	public void setPro_pay_date_str(String pro_pay_date_str) {
		this.pro_pay_date_str = pro_pay_date_str;
	}

	public Integer getPro_overdue_days() {
		return pro_overdue_days;
	}

	public void setPro_overdue_days(Integer pro_overdue_days) {
		this.pro_overdue_days = pro_overdue_days;
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

	public String getPro_pay_type() {
		return pro_pay_type;
	}

	public void setPro_pay_type(String pro_pay_type) {
		this.pro_pay_type = pro_pay_type;
	}

	public String getGov_name() {
		return gov_name;
	}

	public void setGov_name(String gov_name) {
		this.gov_name = gov_name;
	}

	public Boolean getPro_is_clear() {
		return pro_is_clear;
	}

	public void setPro_is_clear(Boolean pro_is_clear) {
		this.pro_is_clear = pro_is_clear;
	}

	public Integer getPro_payment_status() {
		return pro_payment_status;
	}

	public void setPro_payment_status(Integer pro_payment_status) {
		this.pro_payment_status = pro_payment_status;
	}

	public Integer getPro_plan_id() {
		return pro_plan_id;
	}

	public void setPro_plan_id(Integer pro_plan_id) {
		this.pro_plan_id = pro_plan_id;
	}

	public Integer getPro_loan_id() {
		return pro_loan_id;
	}

	public void setPro_loan_id(Integer pro_loan_id) {
		this.pro_loan_id = pro_loan_id;
	}

	public BigDecimal getPro_service_fee() {
		return pro_service_fee;
	}

	public void setPro_service_fee(BigDecimal pro_service_fee) {
		this.pro_service_fee = pro_service_fee;
	}

	public Integer getPro_borrowing_period_unit() {
		return pro_borrowing_period_unit;
	}

	public void setPro_borrowing_period_unit(Integer pro_borrowing_period_unit) {
		this.pro_borrowing_period_unit = pro_borrowing_period_unit;
	}

	public BigDecimal getCollect_service_fee() {
		return collect_service_fee;
	}

	public void setCollect_service_fee(BigDecimal collect_service_fee) {
		this.collect_service_fee = collect_service_fee;
	}

}
