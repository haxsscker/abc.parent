package com.autoserve.abc.dao.dataobject;

import java.util.Date;


/**
 * 项目发布统计表
 */
public class LoanReportDO {
    
	/**
	 * 发标日期
	 */
	private String loan_invest_starttime;
	/**
	 * 项目编号
	 */
	private String loan_no;
	/**
	 * 项目类型
	 */
	private String loan_category;
	/**
	 * 借款人
	 */
	private String user_name;
	/**
	 * 借款用途
	 */
	private String loan_use;
	/**
	 * 保证方式
	 */
	private String guaranty_mode;
	/**
	 * 还款方式
	 */
	private String loan_pay_type;
	/**
	 * 借款金额
	 */
	private String loan_money;
	/**
	 * 满标金额
	 */
	private String loan_current_valid_invest;
	/**
	 * 借款期限
	 */
	private String loan_period;
	/**
	 * 年化率
	 */
	private String loan_rate;
	/**
	 * 招标到期日
	 */
	private String loan_invest_endtime;
	/**
	 * 满标日
	 */
	private String loan_invest_fulltime;
	/**
	 * 借款到期日
	 */
	private String loan_expire_date;
	/**
	 * 付息日
	 */
	private String loan_pay_date;
	/**
	 * 实际借款天数
	 */
	private String collecttime;
	/**
	 * 平台服务费
	 */
	private String service_fee;
	/**
	 * 担保费
	 */
	private String guar_fee;
	/**
	 * 待还本金
	 */
	private String pp_pay_capital;
	/**
	 * 待还利息
	 */
	private String pp_pay_interest;
	/**
	 * 已还本金
	 */
	private String pp_pay_collect_capital;
	/**
	 * 已还利息
	 */
	private String pp_pay_collect_interest;
	/**
	 * 逾期本金
	 */
	private String yq_pay_capital;
	/**
	 * 逾期利息
	 */
	private String yq_pay_interest;
	/**
	 * 是否结清
	 */
	private String jieqing;
	
	private Date startLoanDate;
	private Date endLoanDate;
	
	public Date getStartLoanDate() {
		return startLoanDate;
	}
	public void setStartLoanDate(Date startLoanDate) {
		this.startLoanDate = startLoanDate;
	}
	public Date getEndLoanDate() {
		return endLoanDate;
	}
	public void setEndLoanDate(Date endLoanDate) {
		this.endLoanDate = endLoanDate;
	}
	public String getLoan_invest_starttime() {
		return loan_invest_starttime;
	}
	public void setLoan_invest_starttime(String loan_invest_starttime) {
		this.loan_invest_starttime = loan_invest_starttime;
	}
	public String getLoan_no() {
		return loan_no;
	}
	public void setLoan_no(String loan_no) {
		this.loan_no = loan_no;
	}
	public String getLoan_category() {
		return loan_category;
	}
	public void setLoan_category(String loan_category) {
		this.loan_category = loan_category;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getLoan_use() {
		return loan_use;
	}
	public void setLoan_use(String loan_use) {
		this.loan_use = loan_use;
	}
	public String getGuaranty_mode() {
		return guaranty_mode;
	}
	public void setGuaranty_mode(String guaranty_mode) {
		this.guaranty_mode = guaranty_mode;
	}
	public String getLoan_pay_type() {
		return loan_pay_type;
	}
	public void setLoan_pay_type(String loan_pay_type) {
		this.loan_pay_type = loan_pay_type;
	}
	public String getLoan_money() {
		return loan_money;
	}
	public void setLoan_money(String loan_money) {
		this.loan_money = loan_money;
	}
	public String getLoan_current_valid_invest() {
		return loan_current_valid_invest;
	}
	public void setLoan_current_valid_invest(String loan_current_valid_invest) {
		this.loan_current_valid_invest = loan_current_valid_invest;
	}
	public String getLoan_period() {
		return loan_period;
	}
	public void setLoan_period(String loan_period) {
		this.loan_period = loan_period;
	}
	public String getLoan_rate() {
		return loan_rate;
	}
	public void setLoan_rate(String loan_rate) {
		this.loan_rate = loan_rate;
	}
	public String getLoan_invest_endtime() {
		return loan_invest_endtime;
	}
	public void setLoan_invest_endtime(String loan_invest_endtime) {
		this.loan_invest_endtime = loan_invest_endtime;
	}
	public String getLoan_invest_fulltime() {
		return loan_invest_fulltime;
	}
	public void setLoan_invest_fulltime(String loan_invest_fulltime) {
		this.loan_invest_fulltime = loan_invest_fulltime;
	}
	public String getLoan_expire_date() {
		return loan_expire_date;
	}
	public void setLoan_expire_date(String loan_expire_date) {
		this.loan_expire_date = loan_expire_date;
	}
	public String getLoan_pay_date() {
		return loan_pay_date;
	}
	public void setLoan_pay_date(String loan_pay_date) {
		this.loan_pay_date = loan_pay_date;
	}
	public String getCollecttime() {
		return collecttime;
	}
	public void setCollecttime(String collecttime) {
		this.collecttime = collecttime;
	}
	public String getService_fee() {
		return service_fee;
	}
	public void setService_fee(String service_fee) {
		this.service_fee = service_fee;
	}
	public String getGuar_fee() {
		return guar_fee;
	}
	public void setGuar_fee(String guar_fee) {
		this.guar_fee = guar_fee;
	}
	public String getPp_pay_capital() {
		return pp_pay_capital;
	}
	public void setPp_pay_capital(String pp_pay_capital) {
		this.pp_pay_capital = pp_pay_capital;
	}
	public String getPp_pay_interest() {
		return pp_pay_interest;
	}
	public void setPp_pay_interest(String pp_pay_interest) {
		this.pp_pay_interest = pp_pay_interest;
	}
	public String getPp_pay_collect_capital() {
		return pp_pay_collect_capital;
	}
	public void setPp_pay_collect_capital(String pp_pay_collect_capital) {
		this.pp_pay_collect_capital = pp_pay_collect_capital;
	}
	public String getPp_pay_collect_interest() {
		return pp_pay_collect_interest;
	}
	public void setPp_pay_collect_interest(String pp_pay_collect_interest) {
		this.pp_pay_collect_interest = pp_pay_collect_interest;
	}
	public String getYq_pay_capital() {
		return yq_pay_capital;
	}
	public void setYq_pay_capital(String yq_pay_capital) {
		this.yq_pay_capital = yq_pay_capital;
	}
	public String getYq_pay_interest() {
		return yq_pay_interest;
	}
	public void setYq_pay_interest(String yq_pay_interest) {
		this.yq_pay_interest = yq_pay_interest;
	}
	public String getJieqing() {
		return jieqing;
	}
	public void setJieqing(String jieqing) {
		this.jieqing = jieqing;
	}
	
}
