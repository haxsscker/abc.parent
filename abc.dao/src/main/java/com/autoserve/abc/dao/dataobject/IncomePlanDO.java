package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * IncomePlanDO abc_plan_income
 */
public class IncomePlanDO {

    /**
     * 主键id abc_plan_income.pi_id
     */
    private Integer    piId;

    /**
     * 满标资金划转记录id abc_plan_income.pi_full_trans_record_id
     */
    private Integer    piFullTransRecordId;

    /**
     * 借款人还款计划表id abc_plan_income.pi_payment_plan_id
     */
    private Integer    piPaymentPlanId;

    /**
     * 投资记录id abc_plan_income.pi_invest_id
     */
    private Integer    piInvestId;

    /**
     * 普通标id abc_plan_income.pi_loan_id
     */
    private Integer    piLoanId;

    /**
     * 应还本金 abc_plan_income.pi_pay_capital
     */
    private BigDecimal piPayCapital;

    /**
     * 应还利息 abc_plan_income.pi_pay_interest
     */
    private BigDecimal piPayInterest;

    /**
     * 应还罚金 abc_plan_income.pi_pay_fine
     */
    private BigDecimal piPayFine;

    /**
     * 应还总额 abc_plan_income.pi_pay_total_money
     */
    private BigDecimal piPayTotalMoney;

    /**
     * 实还本金 abc_plan_income.pi_collect_capital
     */
    private BigDecimal piCollectCapital;

    /**
     * 实还利息 abc_plan_income.pi_collect_interest
     */
    private BigDecimal piCollectInterest;

    /**
     * 实还罚金 abc_plan_income.pi_collect_fine
     */
    private BigDecimal piCollectFine;

    /**
     * 实还总额 abc_plan_income.pi_collect_total
     */
    private BigDecimal piCollectTotal;

    /**
     * 应还日期 abc_plan_income.pi_paytime
     */
    private Date       piPaytime;

    /**
     * 实还日期 abc_plan_income.pi_collecttime
     */
    private Date       piCollecttime;

    /**
     * 剩余罚金 因罚金是时时计算的，当第一次罚金未还清时，保留剩余未还罚金 abc_plan_income.pi_remain_fine
     */
    private Long       piRemainFine;

    /**
     * 期数 abc_plan_income.pi_loan_period
     */
    private Integer    piLoanPeriod;

    /**
     * 收益计划状态 -2:未激活 -1: 已删除 0:待收益 2:已结清 3:已被转出 4:已被收购
     * abc_plan_income.pi_income_plan_state
     */
    private Integer    piIncomePlanState;

    /**
     * 收益人 abc_plan_income.pi_beneficiary
     */
    private Integer    piBeneficiary;

    /**
     * 内部交易流水号 abc_plan_income.pi_inner_seq_no
     */
    private String     piInnerSeqNo;
    
    /**
     * 项目名称 loan_no
     */
    private String     loanNo;
    /**
     * 创建时间 pi_createtime
     */
    private Date       piCreateTime;
    /**
     * 是否是转让人最后一期收益
     */
    private Boolean piTransferLast;
    
   

	public Boolean getPiTransferLast() {
		return piTransferLast;
	}

	public void setPiTransferLast(Boolean piTransferLast) {
		this.piTransferLast = piTransferLast;
	}

	public Integer getPiId() {
        return piId;
    }

    public void setPiId(Integer piId) {
        this.piId = piId;
    }

    public Integer getPiFullTransRecordId() {
        return piFullTransRecordId;
    }

    /**
     * @param Integer piFullTransRecordId
     *            (abc_plan_income.pi_full_trans_record_id )
     */
    public void setPiFullTransRecordId(Integer piFullTransRecordId) {
        this.piFullTransRecordId = piFullTransRecordId;
    }

    public Integer getPiPaymentPlanId() {
        return piPaymentPlanId;
    }

    public void setPiPaymentPlanId(Integer piPaymentPlanId) {
        this.piPaymentPlanId = piPaymentPlanId;
    }

    public Integer getPiInvestId() {
        return piInvestId;
    }

    public void setPiInvestId(Integer piInvestId) {
        this.piInvestId = piInvestId;
    }

    public Integer getPiLoanId() {
        return piLoanId;
    }

    public void setPiLoanId(Integer piLoanId) {
        this.piLoanId = piLoanId;
    }

    public BigDecimal getPiPayCapital() {
        return piPayCapital;
    }

    public void setPiPayCapital(BigDecimal piPayCapital) {
        this.piPayCapital = piPayCapital;
    }

    public BigDecimal getPiPayInterest() {
        return piPayInterest;
    }

    public void setPiPayInterest(BigDecimal piPayInterest) {
        this.piPayInterest = piPayInterest;
    }

    public BigDecimal getPiPayFine() {
        return piPayFine;
    }

    public void setPiPayFine(BigDecimal piPayFine) {
        this.piPayFine = piPayFine;
    }

    public BigDecimal getPiPayTotalMoney() {
        return piPayTotalMoney;
    }

    public void setPiPayTotalMoney(BigDecimal piPayTotalMoney) {
        this.piPayTotalMoney = piPayTotalMoney;
    }

    public BigDecimal getPiCollectCapital() {
        return piCollectCapital;
    }

    public void setPiCollectCapital(BigDecimal piCollectCapital) {
        this.piCollectCapital = piCollectCapital;
    }

    public BigDecimal getPiCollectInterest() {
        return piCollectInterest;
    }

    public void setPiCollectInterest(BigDecimal piCollectInterest) {
        this.piCollectInterest = piCollectInterest;
    }

    public BigDecimal getPiCollectFine() {
        return piCollectFine;
    }

    public void setPiCollectFine(BigDecimal piCollectFine) {
        this.piCollectFine = piCollectFine;
    }

    public BigDecimal getPiCollectTotal() {
        return piCollectTotal;
    }

    public void setPiCollectTotal(BigDecimal piCollectTotal) {
        this.piCollectTotal = piCollectTotal;
    }

    public Date getPiPaytime() {
        return piPaytime;
    }

    public void setPiPaytime(Date piPaytime) {
        this.piPaytime = piPaytime;
    }

    public Date getPiCollecttime() {
        return piCollecttime;
    }

    public void setPiCollecttime(Date piCollecttime) {
        this.piCollecttime = piCollecttime;
    }

    public Long getPiRemainFine() {
        return piRemainFine;
    }

    public void setPiRemainFine(Long piRemainFine) {
        this.piRemainFine = piRemainFine;
    }

    public Integer getPiLoanPeriod() {
        return piLoanPeriod;
    }

    public void setPiLoanPeriod(Integer piLoanPeriod) {
        this.piLoanPeriod = piLoanPeriod;
    }

    public Integer getPiIncomePlanState() {
        return piIncomePlanState;
    }

    public void setPiIncomePlanState(Integer piIncomePlanState) {
        this.piIncomePlanState = piIncomePlanState;
    }

    public Integer getPiBeneficiary() {
        return piBeneficiary;
    }

    public void setPiBeneficiary(Integer piBeneficiary) {
        this.piBeneficiary = piBeneficiary;
    }

    public String getPiInnerSeqNo() {
        return piInnerSeqNo;
    }

    public void setPiInnerSeqNo(String piInnerSeqNo) {
        this.piInnerSeqNo = piInnerSeqNo;
    }

	/**
	 * @return the loanNo
	 */
	public String getLoanNo() {
		return loanNo;
	}

	/**
	 * @param loanNo the loanNo to set
	 */
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}

	public Date getPiCreateTime() {
		return piCreateTime;
	}

	public void setPiCreateTime(Date piCreateTime) {
		this.piCreateTime = piCreateTime;
	}
}
