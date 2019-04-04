package com.autoserve.abc.web.vo.review;

/**
 * 用于信贷审核和担保审核页面展示数据的VO
 * 
 * @author yuqing.zheng Created on 2015-01-11,14:14
 */
public class GovCheckVO {
    /**
     * 项目ID
     */
    private int     pro_loan_id;

    /**
     * 意向ID，如果是融资审核，则此字段为空
     */
    private int     pro_intent_id;

    /**
     * 项目名称
     */
    private String  pro_loan_no;

    /**
     * 项目类型
     */
    private String  pdo_product_name;

    /**
     * 借款人
     */
    private String  pro_user_name;

    /**
     * 借款金额
     */
    private double  pro_loan_money;

    /**
     * 年化收益率
     */
    private double  pro_loan_rate;

    /**
     * 借款期限
     */
    private String  pro_loan_period;

    /**
     * 申请日期
     */
    private String  pro_add_date;

    /**
     * 满标日期
     */
    private String  pro_full_date;

    /**
     * 招标到期日
     */
    private String  pro_invest_endDate;

    /**
     * 借款用途
     */
    private String  pro_loan_use;

    /**
     * 还款方式
     */
    private String  pro_pay_type;

    /**
     * 担保机构
     */
    private String  gov_name;

    /**
     * 审核的applyId
     */
    private Integer pro_check_id;

    /**
     * 审核类型 GovCheckVO可能用于信贷审核与担保审核， 审核类型可能是意向审核（1），也可能是融资审核（17）
     */
    private Integer pro_check_type;

    /**
     * 审核状态
     */
    private String  pro_check_state;

    /**
     * 能否进行审核 只有审核状态为”待审核“时为true，其他皆为false
     */
    private boolean can_check;

    /**
     * 发送状态
     */
    private String  pro_send_status;

    /**
     * 已发送机构 未发送为-1，已发送平台为0，已发送小贷为1，已发送担保为2
     */
    private Integer pro_send_gov;

    public int getPro_loan_id() {
        return pro_loan_id;
    }

    public void setPro_loan_id(int pro_loan_id) {
        this.pro_loan_id = pro_loan_id;
    }

    public int getPro_intent_id() {
        return pro_intent_id;
    }

    public void setPro_intent_id(int pro_intent_id) {
        this.pro_intent_id = pro_intent_id;
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

    public Integer getPro_check_id() {
        return pro_check_id;
    }

    public void setPro_check_id(Integer pro_check_id) {
        this.pro_check_id = pro_check_id;
    }

    public Integer getPro_check_type() {
        return pro_check_type;
    }

    public void setPro_check_type(Integer pro_check_type) {
        this.pro_check_type = pro_check_type;
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
}
