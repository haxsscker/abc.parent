package com.autoserve.abc.web.vo.review;

import java.io.Serializable;

/**
 * @author yuqing.zheng Created on 2014-12-02,14:12
 */
public class LoanCheckVO implements Serializable {
    private static final long serialVersionUID = -1979735618327266232L;

    /**
     * 项目ID
     */
    private int               pro_loan_id;

    /**
     * 项目名称
     */
    private String            pro_loan_no;

    /**
     * 项目类型
     */
    private String            pdo_product_name;

    /**
     * 借款人
     */
    private String            pro_user_name;
    
    /**
     * 借款人电话
     */
    private String            pro_user_phone;

    /**
     * 借款金额
     */
    private double            pro_loan_money;

    /**
     * 年化收益率
     */
    private double            pro_loan_rate;

    /**
     * 借款期限
     */
    private String            pro_loan_period;

    /**
     * 当前投资总额
     */
    private double            pro_invest_all;

    /**
     * 当前有效投资额
     */
    private double            pro_curr_invest;

    /**
     * 申请日期
     */
    private String            pro_add_date;

    /**
     * 满标日期
     */
    private String            pro_full_date;

    /**
     * 招标到期日
     */
    private String            pro_invest_endDate;

    /**
     * 借款用途
     */
    private String            pro_loan_use;

    /**
     * 还款方式
     */
    private String            pro_pay_type;

    /**
     * 担保机构
     */
    private String            gov_name;

    /**
     * 审核状态
     */
    private String            pro_check_state;

    /**
     * 能否进行审核 只有审核状态为”待审核“时为true，其他皆为false
     */
    private boolean           can_check;

    //    /**
    //     * 能否发送到“项目发布”页面
    //     * 只有审核状态为“审核已通过”，并且Loan的状态不为“待发布”时才为true，其余均为false
    //     */
    //    private boolean can_publish;
    //
    //    /**
    //     * 是否已发送到“项目发布”页面
    //     * 只有当项目状态为“待发布”时为true，其余皆为false
    //     */
    //    private boolean is_send;

    /**
     * 能否撤回 只有已通过审核，并且项目状态为待发布是才可撤回
     */
    public boolean            can_revoke;

    public int getPro_loan_id() {
        return pro_loan_id;
    }

    public void setPro_loan_id(int pro_loan_id) {
        this.pro_loan_id = pro_loan_id;
    }

    public String getPro_loan_no() {
        return pro_loan_no;
    }

    public void setPro_loan_no(String pro_loan_no) {
        this.pro_loan_no = pro_loan_no;
    }

    public String getPdo_product_name() {
        return pdo_product_name;
    }

    public void setPdo_product_name(String pdo_product_name) {
        this.pdo_product_name = pdo_product_name;
    }

    public String getPro_user_name() {
        return pro_user_name;
    }

    public void setPro_user_name(String pro_user_name) {
        this.pro_user_name = pro_user_name;
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

    public double getPro_invest_all() {
        return pro_invest_all;
    }

    public void setPro_invest_all(double pro_invest_all) {
        this.pro_invest_all = pro_invest_all;
    }

    public double getPro_curr_invest() {
        return pro_curr_invest;
    }

    public void setPro_curr_invest(double pro_curr_invest) {
        this.pro_curr_invest = pro_curr_invest;
    }

    public String getPro_loan_period() {
        return pro_loan_period;
    }

    public void setPro_loan_period(String pro_loan_period) {
        this.pro_loan_period = pro_loan_period;
    }

    public String getPro_add_date() {
        return pro_add_date;
    }

    public void setPro_add_date(String pro_add_date) {
        this.pro_add_date = pro_add_date;
    }

    public String getPro_full_date() {
        return pro_full_date;
    }

    public void setPro_full_date(String pro_full_date) {
        this.pro_full_date = pro_full_date;
    }

    public String getPro_invest_endDate() {
        return pro_invest_endDate;
    }

    public void setPro_invest_endDate(String pro_invest_endDate) {
        this.pro_invest_endDate = pro_invest_endDate;
    }

    public String getPro_loan_use() {
        return pro_loan_use;
    }

    public void setPro_loan_use(String pro_loan_use) {
        this.pro_loan_use = pro_loan_use;
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

    public String getPro_check_state() {
        return pro_check_state;
    }

    public void setPro_check_state(String pro_check_state) {
        this.pro_check_state = pro_check_state;
    }

    public boolean isCan_check() {
        return can_check;
    }

    public void setCan_check(boolean can_check) {
        this.can_check = can_check;
    }

    //    public boolean isCan_publish() {
    //        return can_publish;
    //    }
    //
    //    public void setCan_publish(boolean can_publish) {
    //        this.can_publish = can_publish;
    //    }
    //
    //    public boolean isIs_send() {
    //        return is_send;
    //    }
    //
    //    public void setIs_send(boolean is_send) {
    //        this.is_send = is_send;
    //    }

    public boolean isCan_revoke() {
        return can_revoke;
    }

    public void setCan_revoke(boolean can_revoke) {
        this.can_revoke = can_revoke;
    }

	public String getPro_user_phone() {
		return pro_user_phone;
	}

	public void setPro_user_phone(String pro_user_phone) {
		this.pro_user_phone = pro_user_phone;
	}
    
}
