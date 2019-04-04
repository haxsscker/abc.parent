package com.autoserve.abc.dao.dataobject;

import java.util.Date;


/**
 * 红包统计明细DO
 * 
 * @author lipeng 2014年12月26日 下午8:05:22
 */
public class RedReportDO {
    
	/**
	 * 派发日期
	 */
	private String rs_sendtime;
	/**
	 * 派发类型
	 */
	private String rs_type;
	/**
	 * 派发类型名称
	 */
	private String rs_type_name;
	/**
	 * 派发来源
	 */
	private String rs_theme;
	/**
	 * 派发金额
	 */
	private String rs_amt;
	/**
	 * 注册用户名
	 */
	private String user_name;
	/**
	 * 姓名
	 */
	private String user_real_name;
	/**
	 * 手机号
	 */
	private String user_phone;
	/**
	 * 截止有效期
	 */
	private String rs_closetime;
	/**
	 * 有效标志
	 */
	private String rs_state;
	/**
	 * 有效标志名称
	 */
	private String red_type_name;
	/**
	 * 使用红包项目代码
	 */
	private String loan_no;
	/**
	 * 使用红包金额
	 */
	private String ru_amount;
	/**
	 * 投资金额
	 */
	private String in_invest_money;
	/**
	 * 红包使用时间
	 */
	private String redUseTime;
	
	/***************************以下是查询条件***************************/
	
	/**派发日期查询-开始时间*/
	private Date startRedDate;
	/**派发日期查询-结束时间*/
	private Date endRedDate;
	
	/**红包使用时间-开始时间*/
	private Date startRedUseTime;
	/**红包使用时间-结束时间*/
	private Date endRedUseTime;
	
	public Date getStartRedUseTime() {
		return startRedUseTime;
	}
	public void setStartRedUseTime(Date startRedUseTime) {
		this.startRedUseTime = startRedUseTime;
	}
	public Date getEndRedUseTime() {
		return endRedUseTime;
	}
	public void setEndRedUseTime(Date endRedUseTime) {
		this.endRedUseTime = endRedUseTime;
	}
	public String getRedUseTime() {
		return redUseTime;
	}
	public void setRedUseTime(String redUseTime) {
		this.redUseTime = redUseTime;
	}
	public Date getStartRedDate() {
		return startRedDate;
	}
	public void setStartRedDate(Date startRedDate) {
		this.startRedDate = startRedDate;
	}
	public Date getEndRedDate() {
		return endRedDate;
	}
	public void setEndRedDate(Date endRedDate) {
		this.endRedDate = endRedDate;
	}
	public String getRs_type() {
		return rs_type;
	}
	public void setRs_type(String rs_type) {
		this.rs_type = rs_type;
	}
	public String getRs_type_name() {
		return rs_type_name;
	}
	public void setRs_type_name(String rs_type_name) {
		this.rs_type_name = rs_type_name;
	}
	public String getRs_theme() {
		return rs_theme;
	}
	public void setRs_theme(String rs_theme) {
		this.rs_theme = rs_theme;
	}
	public String getRs_amt() {
		return rs_amt;
	}
	public void setRs_amt(String rs_amt) {
		this.rs_amt = rs_amt;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
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

	public String getRs_sendtime() {
		return rs_sendtime;
	}
	public void setRs_sendtime(String rs_sendtime) {
		this.rs_sendtime = rs_sendtime;
	}
	public String getRs_closetime() {
		return rs_closetime;
	}
	public void setRs_closetime(String rs_closetime) {
		this.rs_closetime = rs_closetime;
	}
	public String getRs_state() {
		return rs_state;
	}
	public void setRs_state(String rs_state) {
		this.rs_state = rs_state;
	}
	public String getRed_type_name() {
		return red_type_name;
	}
	public void setRed_type_name(String red_type_name) {
		this.red_type_name = red_type_name;
	}
	public String getLoan_no() {
		return loan_no;
	}
	public void setLoan_no(String loan_no) {
		this.loan_no = loan_no;
	}
	public String getRu_amount() {
		return ru_amount;
	}
	public void setRu_amount(String ru_amount) {
		this.ru_amount = ru_amount;
	}
	public String getIn_invest_money() {
		return in_invest_money;
	}
	public void setIn_invest_money(String in_invest_money) {
		this.in_invest_money = in_invest_money;
	}

}
