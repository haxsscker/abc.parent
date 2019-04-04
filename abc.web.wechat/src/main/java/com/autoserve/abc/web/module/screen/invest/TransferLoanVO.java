package com.autoserve.abc.web.module.screen.invest;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.TransferLoanState;

public class TransferLoanVO {
    /**
     * 主键id
     */
    private Integer           id;

    /**
     * 原始贷款id
     */
    private Integer           originId;

    /**
     * 投资id
     */
    private Integer           investId;

    /**
     * 转让人
     */
    private Integer           userId;

    /**
     * 转让债权总额
     */
    private BigDecimal        transferTotal;

    /**
     * 转让金额
     */
    private BigDecimal        transferMoney;

    /**
     * 转让后利率
     */
    private BigDecimal        transferRate;

    /**
     * 转让手续费
     */
    private BigDecimal        transferFee;

    /**
     * 转让折让率
     */
    private BigDecimal        transferDiscountRate;

    /**
     * 转让折让费
     */
    private BigDecimal        transferDiscountFee;

    /**
     * 转让期数 比如：12期的收回计划表，还了3期，第4期转让，那么转让期数=12-3 = 9（期）
     */
    private Integer           transferPeriod;

    /**
     * 转让状态
     */
    private TransferLoanState transferLoanState;

    /**
     * 转让申请日期 1、当转让人部分转让时，存在剩余金额，且同一笔贷款时，若第一笔债权未满标时，不允许转让第二次；\n2、当未放款前，借款人还钱了，
     * 那么该转让申请作废，投资人钱重新进行返还，借款人把钱还给原债权人
     */
    private Date              createtime;

    /**
     * 修改日期
     */
    private Date              modifytime;

    /**
     * 投资开始时间
     */
    private Date              investStarttime;

    /**
     * 投资结束时间
     */
    private Date              investEndtime;

    /**
     * 满标日期
     */
    private Date              fulltime;

    /**
     * 放款成功时间
     */
    private Date              fullTransferedtime;

    /**
     * 当前投标总额
     */
    private BigDecimal        currentInvest;

    /**
     * 当前有效投标总额
     */
    private BigDecimal        currentValidInvest;

    /**
     * 下一次还款计划id
     */
    private Integer           nextPaymentId;

    //----------------------------------------------------------------//
    //----------------loan 表字段信息开始--------------------------------//
    //----------------------------------------------------------------//
    /**
     * 项目名称 abc_loan.loan_no
     */
    private String            loanNo;

    /**
     * 借款人类型 1:个人 2:企业 3:借款结构 4:平台 abc_loan.loan_emp_type
     */
    private Integer           loanEmpType;

    /**
     * 借款人id abc_loan.loan_user_id
     */
    private Integer           loanUserId;

    /**
     * 借款机构id 外键 abc_loan.loan_gov
     */
    private Integer           loanGov;

    /**
     * 担保机构id 外键 abc_loan.loan_guar_gov
     */
    private Integer           loanGuarGov;

    /**
     * 借款金额 abc_loan.loan_money
     */
    private BigDecimal        loanMoney;

    /**
     * 年化收益率 abc_loan.loan_rate
     */
    private BigDecimal        loanRate;

    /**
     * 借款期限 abc_loan.loan_period
     */
    private Integer           loanPeriod;

    /**
     * 期限类型 1:年 2:月 3:日 abc_loan.loan_period_type
     */
    private Integer           loanPeriodType;

    /**
     * 还款方式 1:等额本息 2:按月还息到月还本 3:等额本金 abc_loan.loan_pay_type
     */
    private Integer           loanPayType;

    /**
     * 投资开始时间 abc_loan.loan_invest_starttime
     */
    private Date              loanInvestStarttime;

    /**
     * 投资结束时间 abc_loan.loan_invest_endtime
     */
    private Date              loanInvestEndtime;

    /**
     * 投资满标时间 abc_loan.loan_invest_fulltime
     */
    private Date              loanInvestFulltime;

    /**
     * 结算方式 1:固定还款日 2:非固定还款日 abc_loan.loan_clear_type
     */
    private Integer           loanClearType;

    /**
     * 项目分类 1:企业经营贷 2:房屋抵押贷 3:汽车抵押贷 4:个人轻松贷 abc_loan.loan_category
     */
    private Integer           loanCategory;

    /**
     * 项目分类 外键 abc_loan.loan_category_id
     */
    private Integer           loanCategoryId;

    /**
     * 附件url
     */
    private String            loanFileUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public Integer getInvestId() {
        return investId;
    }

    public void setInvestId(Integer investId) {
        this.investId = investId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTransferTotal() {
        return transferTotal;
    }

    public void setTransferTotal(BigDecimal transferTotal) {
        this.transferTotal = transferTotal;
    }

    public BigDecimal getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(BigDecimal transferMoney) {
        this.transferMoney = transferMoney;
    }

    public BigDecimal getTransferRate() {
        return transferRate;
    }

    public void setTransferRate(BigDecimal transferRate) {
        this.transferRate = transferRate;
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    public BigDecimal getTransferDiscountRate() {
        return transferDiscountRate;
    }

    public void setTransferDiscountRate(BigDecimal transferDiscountRate) {
        this.transferDiscountRate = transferDiscountRate;
    }

    public BigDecimal getTransferDiscountFee() {
        return transferDiscountFee;
    }

    public void setTransferDiscountFee(BigDecimal transferDiscountFee) {
        this.transferDiscountFee = transferDiscountFee;
    }

    public Integer getTransferPeriod() {
        return transferPeriod;
    }

    public void setTransferPeriod(Integer transferPeriod) {
        this.transferPeriod = transferPeriod;
    }

    public TransferLoanState getTransferLoanState() {
        return transferLoanState;
    }

    public void setTransferLoanState(TransferLoanState transferLoanState) {
        this.transferLoanState = transferLoanState;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public Date getInvestStarttime() {
        return investStarttime;
    }

    public void setInvestStarttime(Date investStarttime) {
        this.investStarttime = investStarttime;
    }

    public Date getInvestEndtime() {
        return investEndtime;
    }

    public void setInvestEndtime(Date investEndtime) {
        this.investEndtime = investEndtime;
    }

    public Date getFulltime() {
        return fulltime;
    }

    public void setFulltime(Date fulltime) {
        this.fulltime = fulltime;
    }

    public Date getFullTransferedtime() {
        return fullTransferedtime;
    }

    public void setFullTransferedtime(Date fullTransferedtime) {
        this.fullTransferedtime = fullTransferedtime;
    }

    public BigDecimal getCurrentInvest() {
        return currentInvest;
    }

    public void setCurrentInvest(BigDecimal currentInvest) {
        this.currentInvest = currentInvest;
    }

    public BigDecimal getCurrentValidInvest() {
        return currentValidInvest;
    }

    public void setCurrentValidInvest(BigDecimal currentValidInvest) {
        this.currentValidInvest = currentValidInvest;
    }

    public Integer getNextPaymentId() {
        return nextPaymentId;
    }

    public void setNextPaymentId(Integer nextPaymentId) {
        this.nextPaymentId = nextPaymentId;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public Integer getLoanEmpType() {
        return loanEmpType;
    }

    public void setLoanEmpType(Integer loanEmpType) {
        this.loanEmpType = loanEmpType;
    }

    public Integer getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(Integer loanUserId) {
        this.loanUserId = loanUserId;
    }

    public Integer getLoanGov() {
        return loanGov;
    }

    public void setLoanGov(Integer loanGov) {
        this.loanGov = loanGov;
    }

    public Integer getLoanGuarGov() {
        return loanGuarGov;
    }

    public void setLoanGuarGov(Integer loanGuarGov) {
        this.loanGuarGov = loanGuarGov;
    }

    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public Integer getLoanPeriodType() {
        return loanPeriodType;
    }

    public void setLoanPeriodType(Integer loanPeriodType) {
        this.loanPeriodType = loanPeriodType;
    }

    public Integer getLoanPayType() {
        return loanPayType;
    }

    public void setLoanPayType(Integer loanPayType) {
        this.loanPayType = loanPayType;
    }

    public Date getLoanInvestStarttime() {
        return loanInvestStarttime;
    }

    public void setLoanInvestStarttime(Date loanInvestStarttime) {
        this.loanInvestStarttime = loanInvestStarttime;
    }

    public Date getLoanInvestEndtime() {
        return loanInvestEndtime;
    }

    public void setLoanInvestEndtime(Date loanInvestEndtime) {
        this.loanInvestEndtime = loanInvestEndtime;
    }

    public Date getLoanInvestFulltime() {
        return loanInvestFulltime;
    }

    public void setLoanInvestFulltime(Date loanInvestFulltime) {
        this.loanInvestFulltime = loanInvestFulltime;
    }

    public Integer getLoanClearType() {
        return loanClearType;
    }

    public void setLoanClearType(Integer loanClearType) {
        this.loanClearType = loanClearType;
    }

    public Integer getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(Integer loanCategory) {
        this.loanCategory = loanCategory;
    }

    public Integer getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(Integer loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
    }

    public String getLoanFileUrl() {
        return loanFileUrl;
    }

    public void setLoanFileUrl(String loanFileUrl) {
        this.loanFileUrl = loanFileUrl;
    }

}
