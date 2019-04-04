package com.autoserve.abc.web.vo.review;

import java.io.Serializable;

/**
 * @author yuqing.zheng Created on 2014-12-03,21:06
 */
public class TransferCheckVO implements Serializable {
    private static final long serialVersionUID = 103266015247129073L;

    /**
     * 转让ID
     */
    private int               pro_transfer_id;

    /**
     * 项目ID
     */
    private int               pro_loan_id;

    /**
     * 项目名称
     */
    private String            pro_loan_no;
    /**
     * 转让项目编号
     */
    private String            pro_transferloan_no;

    /**
     * 项目类型
     */
    private String            pdo_product_name;

    /**
     * 借款人
     */
    private String            loan_user_name;
    
    /**
     * 借款人电话
     */
    private String            loan_user_phone;

    /**
     * 借款金额
     */
    private double            pro_loan_money;

    /**
     * 年化收益率
     */
    private double            pro_loan_rate;

    /**
     * 当前转让投资总额
     */
    private double            pro_trans_invest;

    /**
     * 当前转让有效投资
     */
    private double            pro_valid_trans_invest;

    /**
     * 借款期限
     */
    private String            pro_loan_period;

    /**
     * 转让人
     */
    private String            cst_real_name;

    /**
     * 转让金额
     */
    private double            pro_transfer_money;

    /**
     * 转让期数
     */
    private int               payCount;

    /**
     * 申请日期
     */
    private String            pro_transfer_date;

    /**
     * 满标日期
     */
    private String            pro_full_date;

    /**
     * 手续费
     */
    private double            pro_transfer_fee;

    /**
     * 转让标状态
     */
    private String            pro_transfer_state;

    /**
     * 审核状态
     */
    private String            pro_check_state;

    /**
     * 是否待转让初审 只有当状态为”待转让初审“时为true，其他皆为false
     */
    private boolean           can_check;

    /**
     * 是否满标待审 只有当状态为”满标待审“时为true，其他皆为false
     */
    private boolean           can_full_check;

    public int getPro_transfer_id() {
        return pro_transfer_id;
    }

    public void setPro_transfer_id(int pro_transfer_id) {
        this.pro_transfer_id = pro_transfer_id;
    }

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

    public double getPro_trans_invest() {
        return pro_trans_invest;
    }

    public void setPro_trans_invest(double pro_trans_invest) {
        this.pro_trans_invest = pro_trans_invest;
    }

    public double getPro_valid_trans_invest() {
        return pro_valid_trans_invest;
    }

    public void setPro_valid_trans_invest(double pro_valid_trans_invest) {
        this.pro_valid_trans_invest = pro_valid_trans_invest;
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

    public double getPro_transfer_money() {
        return pro_transfer_money;
    }

    public void setPro_transfer_money(double pro_transfer_money) {
        this.pro_transfer_money = pro_transfer_money;
    }

    public int getPayCount() {
        return payCount;
    }

    public void setPayCount(int payCount) {
        this.payCount = payCount;
    }

    public String getPro_transfer_date() {
        return pro_transfer_date;
    }

    public void setPro_transfer_date(String pro_transfer_date) {
        this.pro_transfer_date = pro_transfer_date;
    }

    public String getPro_full_date() {
        return pro_full_date;
    }

    public void setPro_full_date(String pro_full_date) {
        this.pro_full_date = pro_full_date;
    }

    public double getPro_transfer_fee() {
        return pro_transfer_fee;
    }

    public void setPro_transfer_fee(double pro_transfer_fee) {
        this.pro_transfer_fee = pro_transfer_fee;
    }

    public String getPro_transfer_state() {
        return pro_transfer_state;
    }

    public void setPro_transfer_state(String pro_transfer_state) {
        this.pro_transfer_state = pro_transfer_state;
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

    public boolean isCan_full_check() {
        return can_full_check;
    }

    public void setCan_full_check(boolean can_full_check) {
        this.can_full_check = can_full_check;
    }

    public String getPro_transferloan_no() {
        return pro_transferloan_no;
    }

    public void setPro_transferloan_no(String pro_transferloan_no) {
        this.pro_transferloan_no = pro_transferloan_no;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

	public String getLoan_user_phone() {
		return loan_user_phone;
	}

	public void setLoan_user_phone(String loan_user_phone) {
		this.loan_user_phone = loan_user_phone;
	}

}
