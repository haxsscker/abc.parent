package com.autoserve.abc.web.vo.credit;

import java.math.BigDecimal;

/**
 * @author RJQ 2015/1/6 17:18.
 */
public class CreditApplyVO {
    private Integer cre_apply_id;

    /**
     * 申请人
     */
    private Integer cre_user_id;

    /**
     * 申请额度类型  1：借款信用额度 2：投资担保额度 3：借款担保额度
     */
    private String creditType;

    /**
     * 原来的信用额度
     */
    private BigDecimal cst_loan_credit;

    /**
     * 申请额度
     */
    private BigDecimal cre_apply_money;

    /**
     * 审核额度
     */
    private BigDecimal cre_check_money;

    /**
     * 申请日期
     */
    private String cre_apply_date;

    /**
     * 审核状态	0：待审核 1：审核已通过 2：审核未通过
     */
    private String cre_check_state;

    /**
     * 审核意见
     */
    private String cre_check_idear;

    /**
     * 最后修改时间
     */
    private String creditLastModifyTime;

    /**
     * 用户名
     */
    private String cst_user_name;

    /**
     * 真实姓名
     */
    private String cst_real_name;

    /**
     * 用户积分
     */
    private Integer cst_user_score;

    public Integer getCre_apply_id() {
        return cre_apply_id;
    }

    public void setCre_apply_id(Integer cre_apply_id) {
        this.cre_apply_id = cre_apply_id;
    }

    public Integer getCre_user_id() {
        return cre_user_id;
    }

    public void setCre_user_id(Integer cre_user_id) {
        this.cre_user_id = cre_user_id;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public BigDecimal getCst_loan_credit() {
        return cst_loan_credit;
    }

    public void setCst_loan_credit(BigDecimal cst_loan_credit) {
        this.cst_loan_credit = cst_loan_credit;
    }

    public BigDecimal getCre_apply_money() {
        return cre_apply_money;
    }

    public void setCre_apply_money(BigDecimal cre_apply_money) {
        this.cre_apply_money = cre_apply_money;
    }

    public BigDecimal getCre_check_money() {
        return cre_check_money;
    }

    public void setCre_check_money(BigDecimal cre_check_money) {
        this.cre_check_money = cre_check_money;
    }

    public String getCre_apply_date() {
        return cre_apply_date;
    }

    public void setCre_apply_date(String cre_apply_date) {
        this.cre_apply_date = cre_apply_date;
    }

    public String getCre_check_state() {
        return cre_check_state;
    }

    public void setCre_check_state(String cre_check_state) {
        this.cre_check_state = cre_check_state;
    }

    public String getCre_check_idear() {
        return cre_check_idear;
    }

    public void setCre_check_idear(String cre_check_idear) {
        this.cre_check_idear = cre_check_idear;
    }

    public String getCreditLastModifyTime() {
        return creditLastModifyTime;
    }

    public void setCreditLastModifyTime(String creditLastModifyTime) {
        this.creditLastModifyTime = creditLastModifyTime;
    }

    public String getCst_user_name() {
        return cst_user_name;
    }

    public void setCst_user_name(String cst_user_name) {
        this.cst_user_name = cst_user_name;
    }

    public String getCst_real_name() {
        return cst_real_name;
    }

    public void setCst_real_name(String cst_real_name) {
        this.cst_real_name = cst_real_name;
    }

    public Integer getCst_user_score() {
        return cst_user_score;
    }

    public void setCst_user_score(Integer cst_user_score) {
        this.cst_user_score = cst_user_score;
    }
}
