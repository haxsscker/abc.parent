package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * LoanIntentApplyDO abc_loan_intent_apply
 */
public class LoanIntentApplyDO {
    /**
     * 申请编号 id 主键 唯一 abc_loan_intent_apply.lia_id
     */
    private Integer    liaId;

    /**
     * 借款人类型 1个人、2企业 3、借款机构 4 平台 abc_loan_intent_apply.lia_intent_emp_type
     */
    private Integer    liaIntentEmpType;

    /**
     * 联系人ID abc_loan_intent_apply.lia_user_id
     */
    private Integer    liaUserId;

    /**
     * 联系人名称 abc_loan_intent_apply.lia_user_name
     */
    private String     liaUserName;

    /**
     * 手机号码 abc_loan_intent_apply.lia_phone
     */
    private String     liaPhone;

    /**
     * 融资金额 abc_loan_intent_apply.lia_intent_money
     */
    private BigDecimal liaIntentMoney;

    /**
     * 所在区域 以数字代替身份的名字 0：云南 1：北京 2：上海 3：重庆 abc_loan_intent_apply.lia_area
     */
    private Integer    liaArea;

    /**
     * 备注说明 abc_loan_intent_apply.lia_note
     */
    private String     liaNote;

    /**
     * 申请时间 abc_loan_intent_apply.lia_intent_time
     */
    private Date       liaIntentTime;

    /**
     * 意向标状态 -1.已删除 1 意向待审核 2 意向审核通过 3 意向审核未通过
     * abc_loan_intent_apply.lia_intent_status
     */
    private Integer    liaIntentStatus;

    /**
     * 审核时间 abc_loan_intent_apply.lia_audit_time
     */
    private Date       liaAuditTime;

    /**
     * 审核意见 abc_loan_intent_apply.lia_audit_opinion
     */
    private String     liaAuditOpinion;

    /**
     * 融资利率(年化收益率) abc_loan_intent_apply.lia_intent_rate
     */
    private BigDecimal liaIntentRate;

    /**
     * 申请标题 abc_loan_intent_apply.lia_intent_title
     */
    private String     liaIntentTitle;

    /**
     * 产品类型 1.个人信用贷、2.汽车抵押贷、3.房产抵押贷 4.企业经营贷
     * abc_loan_intent_apply.lia_intent_category
     */
    private Integer    liaIntentCategory;

    /**
     * 意向编号 abc_loan_intent_apply.lia_intent_no
     */
    private String     liaIntentNo;

    /**
     * 融资用途 abc_loan_intent_apply.lia_intent_use
     */
    private String     liaIntentUse;

    /**
     * 借款期限 abc_loan_intent_apply.lia_intent_period
     */
    private Integer    liaIntentPeriod;

    /**
     * 期限类型 1.年 2.个月 3.日 abc_loan_intent_apply.lia_intent_period_unit
     */
    private Integer    liaIntentPeriodUnit;

    /**
     * 还款方式 1 等额本息 2.按月还息到期还本 3.等额本金 4.到期本息
     * abc_loan_intent_apply.lia_intent_paytype
     */
    private Integer    liaIntentPayType;

    /**
     * 相关项目ID abc_loan_intent_apply.lia_loan_id
     */
    private Integer    liaLoanId;

    /**
     * 附件url
     */
    private String     liaFileUrl;

    public Integer getLiaId() {
        return liaId;
    }

    public void setLiaId(Integer liaId) {
        this.liaId = liaId;
    }

    public Integer getLiaIntentEmpType() {
        return liaIntentEmpType;
    }

    public void setLiaIntentEmpType(Integer liaIntentEmpType) {
        this.liaIntentEmpType = liaIntentEmpType;
    }

    public Integer getLiaUserId() {
        return liaUserId;
    }

    public void setLiaUserId(Integer liaUserId) {
        this.liaUserId = liaUserId;
    }

    public String getLiaUserName() {
        return liaUserName;
    }

    public void setLiaUserName(String liaUserName) {
        this.liaUserName = liaUserName;
    }

    public String getLiaPhone() {
        return liaPhone;
    }

    public void setLiaPhone(String liaPhone) {
        this.liaPhone = liaPhone;
    }

    public BigDecimal getLiaIntentMoney() {
        return liaIntentMoney;
    }

    public void setLiaIntentMoney(BigDecimal liaIntentMoney) {
        this.liaIntentMoney = liaIntentMoney;
    }

    public Integer getLiaArea() {
        return liaArea;
    }

    public void setLiaArea(Integer liaArea) {
        this.liaArea = liaArea;
    }

    public String getLiaNote() {
        return liaNote;
    }

    public void setLiaNote(String liaNote) {
        this.liaNote = liaNote;
    }

    public Date getLiaIntentTime() {
        return liaIntentTime;
    }

    public void setLiaIntentTime(Date liaIntentTime) {
        this.liaIntentTime = liaIntentTime;
    }

    public Integer getLiaIntentStatus() {
        return liaIntentStatus;
    }

    public void setLiaIntentStatus(Integer liaIntentStatus) {
        this.liaIntentStatus = liaIntentStatus;
    }

    public Date getLiaAuditTime() {
        return liaAuditTime;
    }

    public void setLiaAuditTime(Date liaAuditTime) {
        this.liaAuditTime = liaAuditTime;
    }

    public String getLiaAuditOpinion() {
        return liaAuditOpinion;
    }

    public void setLiaAuditOpinion(String liaAuditOpinion) {
        this.liaAuditOpinion = liaAuditOpinion;
    }

    public BigDecimal getLiaIntentRate() {
        return liaIntentRate;
    }

    public void setLiaIntentRate(BigDecimal liaIntentRate) {
        this.liaIntentRate = liaIntentRate;
    }

    public String getLiaIntentTitle() {
        return liaIntentTitle;
    }

    public void setLiaIntentTitle(String liaIntentTitle) {
        this.liaIntentTitle = liaIntentTitle;
    }

    public Integer getLiaIntentCategory() {
        return liaIntentCategory;
    }

    public void setLiaIntentCategory(Integer liaIntentCategory) {
        this.liaIntentCategory = liaIntentCategory;
    }

    public String getLiaIntentNo() {
        return liaIntentNo;
    }

    public void setLiaIntentNo(String liaIntentNo) {
        this.liaIntentNo = liaIntentNo;
    }

    public String getLiaIntentUse() {
        return liaIntentUse;
    }

    public void setLiaIntentUse(String liaIntentUse) {
        this.liaIntentUse = liaIntentUse;
    }

    public Integer getLiaIntentPeriod() {
        return liaIntentPeriod;
    }

    public void setLiaIntentPeriod(Integer liaIntentPeriod) {
        this.liaIntentPeriod = liaIntentPeriod;
    }

    public Integer getLiaIntentPeriodUnit() {
        return liaIntentPeriodUnit;
    }

    public void setLiaIntentPeriodUnit(Integer liaIntentPeriodUnit) {
        this.liaIntentPeriodUnit = liaIntentPeriodUnit;
    }

    public Integer getLiaIntentPayType() {
        return liaIntentPayType;
    }

    public void setLiaIntentPayType(Integer liaIntentPayType) {
        this.liaIntentPayType = liaIntentPayType;
    }

    public Integer getLiaLoanId() {
        return liaLoanId;
    }

    public void setLiaLoanId(Integer liaLoanId) {
        this.liaLoanId = liaLoanId;
    }

    public String getLiaFileUrl() {
        return liaFileUrl;
    }

    public void setLiaFileUrl(String liaFileUrl) {
        this.liaFileUrl = liaFileUrl;
    }
}
