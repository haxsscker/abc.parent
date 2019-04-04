package com.autoserve.abc.web.vo.review;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-23,11:36
 */
public class IntentCheckVO {
    /**
     * 意向编号
     */
    private String pro_intent_no;
    /**
     * 项目名称
     */
    private String pro_loan_no;

    /**
     * 项目类型
     */
    private String pdo_product_name;

    /**
     * 借款人
     */
    private String pro_user_name;
    
    /**
     * 借款人电话
     */
    private String pro_user_phone;
    

    /**
     * 借款金额
     */
    private double pro_loan_money;

    /**
     * 年化收益率
     */
    private double pro_loan_rate;

    /**
     * 借款期限
     */
    private String pro_loan_period;

    /**
     * 申请日期
     */
    private String pro_add_date;

    /**
     * 借款用途
     */
    private String pro_loan_use;

    /**
     * 还款方式
     */
    private String pro_pay_type;

    /**
     * 审核状态
     */
    private String pro_loan_intentstate;

    /**
     * 待审角色
     */
    private String pro_check_role;

    /**
     * 待审角色idx
     */
    private Integer pro_check_role_idx;

    /**
     * 审核ID
     */
    private Integer pro_review_id;

    /**
     * 相关项目ID
     */
    private Integer pro_loan_id;

    /**
     * 意向ID
     */
    private Integer pro_intent_id;

    /**
     * 能否进行意向审核
     * 注意：此属性只在该意向状态为“待意向审核”时为true，其余均为false
     */
    private boolean can_intent_check;

    /**
     * 发送状态
     */
    private String pro_send_status;

    /**
     * 已发送机构
     * 未发送为-1，已发送平台为0，已发送小贷为1，已发送担保为2
     */
    private Integer pro_send_gov;
    /**
     * 担保名称
     */
    private String pro_guar_gov;
    /**
     * 担保id
     */
    private Integer pro_gov_id;

    private String pro_invest_endDate;

    private boolean pro_has_loan;

    public Integer getPro_gov_id() {
		return pro_gov_id;
	}

	public void setPro_gov_id(Integer pro_gov_id) {
		this.pro_gov_id = pro_gov_id;
	}

	public String getPro_intent_no() {
        return pro_intent_no;
    }

    public void setPro_intent_no(String pro_intent_no) {
        this.pro_intent_no = pro_intent_no;
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

    public String getPro_loan_intentstate() {
        return pro_loan_intentstate;
    }

    public void setPro_loan_intentstate(String pro_loan_intentstate) {
        this.pro_loan_intentstate = pro_loan_intentstate;
    }

    public String getPro_check_role() {
        return pro_check_role;
    }

    public void setPro_check_role(String pro_check_role) {
        this.pro_check_role = pro_check_role;
    }

    public Integer getPro_review_id() {
        return pro_review_id;
    }

    public void setPro_review_id(Integer pro_review_id) {
        this.pro_review_id = pro_review_id;
    }

    public Integer getPro_loan_id() {
        return pro_loan_id;
    }

    public void setPro_loan_id(Integer pro_loan_id) {
        this.pro_loan_id = pro_loan_id;
    }

    public Integer getPro_intent_id() {
        return pro_intent_id;
    }

    public void setPro_intent_id(Integer pro_intent_id) {
        this.pro_intent_id = pro_intent_id;
    }

    public boolean isCan_intent_check() {
        return can_intent_check;
    }

    public void setCan_intent_check(boolean can_intent_check) {
        this.can_intent_check = can_intent_check;
    }

    public Integer getPro_check_role_idx() {
        return pro_check_role_idx;
    }

    public String getPro_loan_no() {
		return pro_loan_no;
	}

	public void setPro_loan_no(String pro_loan_no) {
		this.pro_loan_no = pro_loan_no;
	}

	public void setPro_check_role_idx(Integer pro_check_role_idx) {
        this.pro_check_role_idx = pro_check_role_idx;
    }

    public String getPro_send_status() {
        return pro_send_status;
    }

    public void setPro_send_status(String pro_send_status) {
        this.pro_send_status = pro_send_status;
    }

    public Integer getPro_send_gov() {
        return pro_send_gov;
    }

    public void setPro_send_gov(Integer pro_send_gov) {
        this.pro_send_gov = pro_send_gov;
    }

    public String getPro_guar_gov() {
        return pro_guar_gov;
    }

    public void setPro_guar_gov(String pro_guar_gov) {
        this.pro_guar_gov = pro_guar_gov;
    }

    public String getPro_invest_endDate() {
        return pro_invest_endDate;
    }

    public void setPro_invest_endDate(String pro_invest_endDate) {
        this.pro_invest_endDate = pro_invest_endDate;
    }

    public boolean isPro_has_loan() {
        return pro_has_loan;
    }

    public void setPro_has_loan(boolean pro_has_loan) {
        this.pro_has_loan = pro_has_loan;
    }

	public String getPro_user_phone() {
		return pro_user_phone;
	}

	public void setPro_user_phone(String pro_user_phone) {
		this.pro_user_phone = pro_user_phone;
	}
    
}
