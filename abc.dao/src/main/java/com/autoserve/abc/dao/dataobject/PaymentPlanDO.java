package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * PaymentPlanDO abc_plan_payment
 */
public class PaymentPlanDO {

    /**
     * 主键id abc_plan_payment.pp_id
     */
    private Integer    ppId;

    /**
     * 满标资金划转记录id abc_plan_payment.pp_full_trans_record_id
     */
    private Integer    ppFullTransRecordId;

    /**
     * 原始标id abc_plan_payment.pp_loan_id
     */
    private Integer    ppLoanId;

    /**
     * 应还本金 abc_plan_payment.pp_pay_capital
     */
    private BigDecimal ppPayCapital;

    /**
     * 应还利息 abc_plan_payment.pp_pay_interest
     */
    private BigDecimal ppPayInterest;

    /**
     * 应还罚金 abc_plan_payment.pp_pay_fine
     */
    private BigDecimal ppPayFine;

    /**
     * 应还平台服务费 abc_plan_payment.pp_pay_service_fee
     */
    private BigDecimal ppPayServiceFee;

    /**
     * 应还平台担保费 abc_plan_payment.pp_pay_guar_fee
     */
    private BigDecimal ppPayGuarFee;

    /**
     * 应还总额 abc_plan_payment.pp_pay_total_money
     */
    private BigDecimal ppPayTotalMoney;

    /**
     * 实还本金 abc_plan_payment.pp_pay_collect_capital
     */
    private BigDecimal ppPayCollectCapital;

    /**
     * 实还利息 abc_plan_payment.pp_pay_collect_interest
     */
    private BigDecimal ppPayCollectInterest;

    /**
     * 实还罚金 abc_plan_payment.pp_pay_collect_fine
     */
    private BigDecimal ppPayCollectFine;

    /**
     * 实还平台服务费 abc_plan_payment.pp_collect_service_fee
     */
    private BigDecimal ppCollectServiceFee;

    /**
     * 实还担保服务费 abc_plan_payment.pp_collect_guar_fee
     */
    private BigDecimal ppCollectGuarFee;

    /**
     * 实还总额 abc_plan_payment.pp_collect_total
     */
    private BigDecimal ppCollectTotal;

    /**
     * 剩余罚金 因罚金是时时计算的，当第一次罚金未还清时，保留剩余未还罚金 abc_plan_payment.pp_remain_fine
     */
    private BigDecimal ppRemainFine;

    /**
     * 期数 abc_plan_payment.pp_loan_period
     */
    private Integer    ppLoanPeriod;

    /**
     * 是否还清 true:是 false:否 abc_plan_payment.pp_is_clear
     */
    private Boolean    ppIsClear;

    /**
     * 应还日期 abc_plan_payment.pp_paytime
     */
    private Date       ppPaytime;

    /**
     * 实还日期 abc_plan_payment.pp_collecttime
     */
    private Date       ppCollecttime;

    /**
     * 还款状态 －1:未激活 0:未还清 1:付款中 2:已还清 abc_plan_payment.pp_pay_state
     */
    private Integer    ppPayState;

    /**
     * 还款类型 1:正常还款 2:平台代还 3:强制还款 abc_plan_payment.pp_pay_type
     */
    private Integer    ppPayType;

    /**
     * 平台是否已经代还 true:是 false:否 abc_plan_payment.pp_replace_state
     */
    private Boolean    ppReplaceState;

    /**
     * 借款人 abc_plan_income.pp_loanee
     */
    private Integer    ppLoanee;

    /**
     * 内部交易流水号 abc_invest.pp_inner_seq_no
     */
    private String     ppInnerSeqNo;
    /**
     * 创建时间
     */
    private Date ppCreateTime;
    
    /**
     * 
     * @content:
     * @author:夏同同
     * @date:2016年5月10日 下午2:56:16
     */
    /** 应还违约罚金*/
    private BigDecimal ppPayBreachFine;
    /** 实还违约罚金*/
    private BigDecimal ppPayCollectBreachFine;
    /** 剩余违约罚金*/
    private BigDecimal ppRemainBreachFine;

    public Date getPpCreateTime() {
		return ppCreateTime;
	}

	public void setPpCreateTime(Date ppCreateTime) {
		this.ppCreateTime = ppCreateTime;
	}

	public Integer getPpId() {
        return ppId;
    }

    public void setPpId(Integer ppId) {
        this.ppId = ppId;
    }

    public Integer getPpFullTransRecordId() {
        return ppFullTransRecordId;
    }

    /**
     * @param Integer ppFullTransRecordId
     *            (abc_plan_income.pp_full_trans_record_id )
     */
    public void setPpFullTransRecordId(Integer ppFullTransRecordId) {
        this.ppFullTransRecordId = ppFullTransRecordId;
    }

    public Integer getPpLoanId() {
        return ppLoanId;
    }

    public void setPpLoanId(Integer ppLoanId) {
        this.ppLoanId = ppLoanId;
    }

    public BigDecimal getPpPayCapital() {
        return ppPayCapital;
    }

    public void setPpPayCapital(BigDecimal ppPayCapital) {
        this.ppPayCapital = ppPayCapital;
    }

    public BigDecimal getPpPayInterest() {
        return ppPayInterest;
    }

    public void setPpPayInterest(BigDecimal ppPayInterest) {
        this.ppPayInterest = ppPayInterest;
    }

    public BigDecimal getPpPayFine() {
        return ppPayFine;
    }

    public void setPpPayFine(BigDecimal ppPayFine) {
        this.ppPayFine = ppPayFine;
    }

    public BigDecimal getPpPayServiceFee() {
        return ppPayServiceFee;
    }

    public void setPpPayServiceFee(BigDecimal ppPayServiceFee) {
        this.ppPayServiceFee = ppPayServiceFee;
    }

    public BigDecimal getPpPayGuarFee() {
        return ppPayGuarFee;
    }

    public void setPpPayGuarFee(BigDecimal ppPayGuarFee) {
        this.ppPayGuarFee = ppPayGuarFee;
    }

    public BigDecimal getPpPayTotalMoney() {
        return ppPayTotalMoney;
    }

    public void setPpPayTotalMoney(BigDecimal ppPayTotalMoney) {
        this.ppPayTotalMoney = ppPayTotalMoney;
    }

    public BigDecimal getPpPayCollectCapital() {
        return ppPayCollectCapital;
    }

    public void setPpPayCollectCapital(BigDecimal ppPayCollectCapital) {
        this.ppPayCollectCapital = ppPayCollectCapital;
    }

    public BigDecimal getPpPayCollectInterest() {
        return ppPayCollectInterest;
    }

    public void setPpPayCollectInterest(BigDecimal ppPayCollectInterest) {
        this.ppPayCollectInterest = ppPayCollectInterest;
    }

    public BigDecimal getPpPayCollectFine() {
        return ppPayCollectFine;
    }

    public void setPpPayCollectFine(BigDecimal ppPayCollectFine) {
        this.ppPayCollectFine = ppPayCollectFine;
    }

    public BigDecimal getPpCollectServiceFee() {
        return ppCollectServiceFee;
    }

    public void setPpCollectServiceFee(BigDecimal ppCollectServiceFee) {
        this.ppCollectServiceFee = ppCollectServiceFee;
    }

    public BigDecimal getPpCollectGuarFee() {
        return ppCollectGuarFee;
    }

    public void setPpCollectGuarFee(BigDecimal ppCollectGuarFee) {
        this.ppCollectGuarFee = ppCollectGuarFee;
    }

    public BigDecimal getPpCollectTotal() {
        return ppCollectTotal;
    }

    public void setPpCollectTotal(BigDecimal ppCollectTotal) {
        this.ppCollectTotal = ppCollectTotal;
    }

    public BigDecimal getPpRemainFine() {
        return ppRemainFine;
    }

    public void setPpRemainFine(BigDecimal ppRemainFine) {
        this.ppRemainFine = ppRemainFine;
    }

    public Integer getPpLoanPeriod() {
        return ppLoanPeriod;
    }

    public void setPpLoanPeriod(Integer ppLoanPeriod) {
        this.ppLoanPeriod = ppLoanPeriod;
    }

    public Boolean getPpIsClear() {
        return ppIsClear;
    }

    public void setPpIsClear(Boolean ppIsClear) {
        this.ppIsClear = ppIsClear;
    }

    public Date getPpPaytime() {
        return ppPaytime;
    }

    public void setPpPaytime(Date ppPaytime) {
        this.ppPaytime = ppPaytime;
    }

    public Date getPpCollecttime() {
        return ppCollecttime;
    }

    public void setPpCollecttime(Date ppCollecttime) {
        this.ppCollecttime = ppCollecttime;
    }

    public Integer getPpPayState() {
        return ppPayState;
    }

    public void setPpPayState(Integer ppPayState) {
        this.ppPayState = ppPayState;
    }

    public Integer getPpPayType() {
        return ppPayType;
    }

    public void setPpPayType(Integer ppPayType) {
        this.ppPayType = ppPayType;
    }

    public Boolean getPpReplaceState() {
        return ppReplaceState;
    }

    public void setPpReplaceState(Boolean ppReplaceState) {
        this.ppReplaceState = ppReplaceState;
    }

    public Integer getPpLoanee() {
        return ppLoanee;
    }

    public void setPpLoanee(Integer ppLoanee) {
        this.ppLoanee = ppLoanee;
    }

    public String getPpInnerSeqNo() {
        return ppInnerSeqNo;
    }

    public void setPpInnerSeqNo(String ppInnerSeqNo) {
        this.ppInnerSeqNo = ppInnerSeqNo;
    }

	public BigDecimal getPpPayBreachFine() {
		return ppPayBreachFine;
	}

	public void setPpPayBreachFine(BigDecimal ppPayBreachFine) {
		this.ppPayBreachFine = ppPayBreachFine;
	}

	public BigDecimal getPpPayCollectBreachFine() {
		return ppPayCollectBreachFine;
	}

	public void setPpPayCollectBreachFine(BigDecimal ppPayCollectBreachFine) {
		this.ppPayCollectBreachFine = ppPayCollectBreachFine;
	}

	public BigDecimal getPpRemainBreachFine() {
		return ppRemainBreachFine;
	}

	public void setPpRemainBreachFine(BigDecimal ppRemainBreachFine) {
		this.ppRemainBreachFine = ppRemainBreachFine;
	}

}
