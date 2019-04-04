package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 投资人明细表
 *
 */
public class InvestorsReportDO {

	/**
	 * 项目代码
	 */
	private String loan_no;
	
	/**
	 * 原项目代码
	 */
	private String origin_loan_no;
	
	/**
	 * 投资人用户名
	 */
	private String user_name;
	/**
	 * 姓名
	 */
	private String user_real_name;
	/**
	 * 身份证号
	 */
	private String user_doc_no;
	/**
	 * 手机号
	 */
	private String user_phone;
	/**
	 * 邮箱
	 */
	private String user_email;
	/**
	 * 推荐人
	 */
	private String recommend_name;
	/**
	 * 投资时间
	 */
	private String in_createtime;
	/**
	 * 投资金额
	 */
	private String in_invest_money;
	/**
	 * 应收利息
	 */
	private String pay_interest;
	/**
	 * 已收利息
	 */
	private String collect_interest;
	/**
	 * 已收本金
	 */
	private String collect_capital;
	/**
	 * 借款到期日
	 */
	private String loan_expire_date;
	/**
	 * 是否转让
	 */
	private String in_invest_state;
	/**
	 * 转让日期
	 */
	private String in_transfer_date;
	/**
	 * 是否结清
	 */
	private String income_end;
	//投资开始时间
	private Date startInvestDate;
	//投资结束时间
	private Date endInvestDate;
	
	public Date getStartInvestDate() {
		return startInvestDate;
	}

	public void setStartInvestDate(Date startInvestDate) {
		this.startInvestDate = startInvestDate;
	}

	public Date getEndInvestDate() {
		return endInvestDate;
	}

	public void setEndInvestDate(Date endInvestDate) {
		this.endInvestDate = endInvestDate;
	}

	public String getLoan_no() {
		return loan_no;
	}

	public void setLoan_no(String loan_no) {
		this.loan_no = loan_no;
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


	public String getLoan_expire_date() {
		return loan_expire_date;
	}

	public void setLoan_expire_date(String loan_expire_date) {
		this.loan_expire_date = loan_expire_date;
	}

	public String getUser_doc_no() {
		return user_doc_no;
	}

	public void setUser_doc_no(String user_doc_no) {
		this.user_doc_no = user_doc_no;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getRecommend_name() {
		return recommend_name;
	}

	public void setRecommend_name(String recommend_name) {
		this.recommend_name = recommend_name;
	}


	public String getIn_invest_money() {
		return in_invest_money;
	}


	public String getIn_createtime() {
		return in_createtime;
	}

	public void setIn_createtime(String in_createtime) {
		this.in_createtime = in_createtime;
	}

	public void setIn_invest_money(String in_invest_money) {
		this.in_invest_money = in_invest_money;
	}

	public String getPay_interest() {
		return pay_interest;
	}

	public void setPay_interest(String pay_interest) {
		this.pay_interest = pay_interest;
	}

	public String getCollect_interest() {
		return collect_interest;
	}

	public void setCollect_interest(String collect_interest) {
		this.collect_interest = collect_interest;
	}

	public String getCollect_capital() {
		return collect_capital;
	}

	public void setCollect_capital(String collect_capital) {
		this.collect_capital = collect_capital;
	}

	public String getIn_invest_state() {
		return in_invest_state;
	}

	public void setIn_invest_state(String in_invest_state) {
		this.in_invest_state = in_invest_state;
	}

	public String getIncome_end() {
		return income_end;
	}

	public void setIncome_end(String income_end) {
		this.income_end = income_end;
	}

	public String getOrigin_loan_no() {
		return origin_loan_no;
	}

	public void setOrigin_loan_no(String origin_loan_no) {
		this.origin_loan_no = origin_loan_no;
	}

	public String getIn_transfer_date() {
		return in_transfer_date;
	}

	public void setIn_transfer_date(String in_transfer_date) {
		this.in_transfer_date = in_transfer_date;
	}

}
