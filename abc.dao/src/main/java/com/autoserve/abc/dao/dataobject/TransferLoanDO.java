package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TransferLoanDO abc_transfer_loan
 */
public class TransferLoanDO {
    /**
     * 主键id abc_transfer_loan.tl_id
     */
    private Integer    tlId;

    /**
     * 原始贷款id abc_transfer_loan.tl_origin_id
     */
    private Integer    tlOriginId;

    /**
     * 投资id abc_transfer_loan.tl_invest_id
     */
    private Integer    tlInvestId;

    /**
     * 转让人 abc_transfer_loan.tl_user_id
     */
    private Integer    tlUserId;

    /**
     * 转让债权总额 abc_transfer_loan.tl_transfer_total
     */
    private BigDecimal tlTransferTotal;

    /**
     * 转让金额 abc_transfer_loan.tl_transfer_money
     */
    private BigDecimal tlTransferMoney;

    /**
     * 转让后利率 abc_transfer_loan.tl_transfer_rate
     */
    private BigDecimal tlTransferRate;

    /**
     * 转让手续费 abc_transfer_loan.tl_transfer_fee
     */
    private BigDecimal tlTransferFee;

    /**
     * 转让折让率 abc_transfer_loan.tl_transfer_discount_rate
     */
    private BigDecimal tlTransferDiscountRate;

    /**
     * 转让折让费 abc_transfer_loan.tl_transfer_discount_fee
     */
    private BigDecimal tlTransferDiscountFee;

    /**
     * 转让期数 比如：12期的收回计划表，还了3期，第4期转让，那么转让期数=12-3 = 9（期）
     * abc_transfer_loan.tl_transfer_period
     */
    private Integer    tlTransferPeriod;

    /**
     * 转让状态 0：待审核 1：初审已通过 2：初审未通过 3：转让中 4：满标待审 5：满标审核通过 6：满标审核未通过 7：已流标 8：划转中
     * 9：已划转 abc_transfer_loan.tl_state
     */
    private Integer    tlState;
    private List<Integer> tlStates;

    /**
     * 转让申请日期 1、当转让人部分转让时，存在剩余金额，且同一笔贷款时，若第一笔债权未满标时，不允许转让第二次；\n2、当未放款前，借款人还钱了，
     * 那么该转让申请作废，投资人钱重新进行返还，借款人把钱还给原债权人 abc_transfer_loan.tl_createtime
     */
    private Date       tlCreatetime;

    /**
     * 修改日期
     */
    private Date       tlModifytime;

    /**
     * 投资开始时间 abc_transfer_loan.tl_invest_starttime
     */
    private Date       tlInvestStarttime;

    /**
     * 投资结束时间 abc_transfer_loan.tl_invest_endtime
     */
    private Date       tlInvestEndtime;

    /**
     * 满标日期 abc_transfer_loan.tl_fulltime
     */
    private Date       tlFulltime;

    /**
     * 放款成功时间 abc_transfer_loan.tl_full_transferedtime
     */
    private Date       tlFullTransferedtime;
    /**
     * 转让到期日
     */
    private Date       tlLoanExpireDate;

    /**
     * 当前投标总额 abc_transfer_loan.tl_current_invest
     */
    private BigDecimal tlCurrentInvest;

    /**
     * 当前有效投标总额 abc_transfer_loan.tl_current_valid_invest
     */
    private BigDecimal tlCurrentValidInvest;

    /**
     * 下一次还款计划id abc_transfer_loan.tl_next_payment_id
     */
    private Integer    tlNextPaymentId;
    /**
     * 转让投资标号 abc_transfer_loan.tl_loan_no
     */
    private String     tlLoanNo;

    private String contractPath;
    /**
     * 转让合同存证编号
     */
    private String contractDepositNumber;
    /**
     * 转让标发布时间
     */
    private Date tlReleaseDate;
    
    public Date getTlReleaseDate() {
		return tlReleaseDate;
	}

	public void setTlReleaseDate(Date tlReleaseDate) {
		this.tlReleaseDate = tlReleaseDate;
	}

	public Integer getTlId() {
        return tlId;
    }

    public void setTlId(Integer tlId) {
        this.tlId = tlId;
    }

    public Integer getTlOriginId() {
        return tlOriginId;
    }

    public void setTlOriginId(Integer tlOriginId) {
        this.tlOriginId = tlOriginId;
    }

    public Integer getTlInvestId() {
        return tlInvestId;
    }

    public void setTlInvestId(Integer tlInvestId) {
        this.tlInvestId = tlInvestId;
    }

    public Integer getTlUserId() {
        return tlUserId;
    }

    public void setTlUserId(Integer tlUserId) {
        this.tlUserId = tlUserId;
    }

    public BigDecimal getTlTransferTotal() {
        return tlTransferTotal;
    }

    public void setTlTransferTotal(BigDecimal tlTransferTotal) {
        this.tlTransferTotal = tlTransferTotal;
    }

    public BigDecimal getTlTransferMoney() {
        return tlTransferMoney;
    }

    public void setTlTransferMoney(BigDecimal tlTransferMoney) {
        this.tlTransferMoney = tlTransferMoney;
    }

    public BigDecimal getTlTransferRate() {
        return tlTransferRate;
    }

    public void setTlTransferRate(BigDecimal tlTransferRate) {
        this.tlTransferRate = tlTransferRate;
    }

    public BigDecimal getTlTransferFee() {
        return tlTransferFee;
    }

    public void setTlTransferFee(BigDecimal tlTransferFee) {
        this.tlTransferFee = tlTransferFee;
    }

    public BigDecimal getTlTransferDiscountRate() {
        return tlTransferDiscountRate;
    }

    /**
     * @param BigDecimal tlTransferDiscountRate
     *            (abc_transfer_loan.tl_transfer_discount_rate )
     */
    public void setTlTransferDiscountRate(BigDecimal tlTransferDiscountRate) {
        this.tlTransferDiscountRate = tlTransferDiscountRate;
    }

    public BigDecimal getTlTransferDiscountFee() {
        return tlTransferDiscountFee;
    }

    /**
     * @param BigDecimal tlTransferDiscountFee
     *            (abc_transfer_loan.tl_transfer_discount_fee )
     */
    public void setTlTransferDiscountFee(BigDecimal tlTransferDiscountFee) {
        this.tlTransferDiscountFee = tlTransferDiscountFee;
    }

    public Integer getTlTransferPeriod() {
        return tlTransferPeriod;
    }

    public void setTlTransferPeriod(Integer tlTransferPeriod) {
        this.tlTransferPeriod = tlTransferPeriod;
    }

    public Integer getTlState() {
        return tlState;
    }

    public void setTlState(Integer tlState) {
        this.tlState = tlState;
    }

    public Date getTlCreatetime() {
        return tlCreatetime;
    }

    public void setTlCreatetime(Date tlCreatetime) {
        this.tlCreatetime = tlCreatetime;
    }

    public Date getTlModifytime() {
        return tlModifytime;
    }

    public void setTlModifytime(Date tlModifytime) {
        this.tlModifytime = tlModifytime;
    }

    public Date getTlInvestStarttime() {
        return tlInvestStarttime;
    }

    public void setTlInvestStarttime(Date tlInvestStarttime) {
        this.tlInvestStarttime = tlInvestStarttime;
    }

    public Date getTlInvestEndtime() {
        return tlInvestEndtime;
    }

    public void setTlInvestEndtime(Date tlInvestEndtime) {
        this.tlInvestEndtime = tlInvestEndtime;
    }

    public Date getTlFulltime() {
        return tlFulltime;
    }

    public void setTlFulltime(Date tlFulltime) {
        this.tlFulltime = tlFulltime;
    }

    public void setTlFullTransferedtime(Date tlFullTransferedtime) {
        this.tlFullTransferedtime = tlFullTransferedtime;
    }

    public Date getTlFullTransferedtime() {
        return tlFullTransferedtime;
    }

    public BigDecimal getTlCurrentInvest() {
        return tlCurrentInvest;
    }

    public void setTlCurrentInvest(BigDecimal tlCurrentInvest) {
        this.tlCurrentInvest = tlCurrentInvest;
    }

    public BigDecimal getTlCurrentValidInvest() {
        return tlCurrentValidInvest;
    }

    public void setTlCurrentValidInvest(BigDecimal tlCurrentValidInvest) {
        this.tlCurrentValidInvest = tlCurrentValidInvest;
    }

    public Integer getTlNextPaymentId() {
        return tlNextPaymentId;
    }

    public void setTlNextPaymentId(Integer tlNextPaymentId) {
        this.tlNextPaymentId = tlNextPaymentId;
    }

    public String getTlLoanNo() {
        return tlLoanNo;
    }

    public void setTlLoanNo(String tlLoanNo) {
        this.tlLoanNo = tlLoanNo;
    }

    public Date getTlLoanExpireDate() {
        return tlLoanExpireDate;
    }

    public void setTlLoanExpireDate(Date tlLoanExpireDate) {
        this.tlLoanExpireDate = tlLoanExpireDate;
    }

	public String getContractPath() {
		return contractPath;
	}

	public void setContractPath(String contractPath) {
		this.contractPath = contractPath;
	}

	public List<Integer> getTlStates() {
		return tlStates;
	}

	public void setTlStates(List<Integer> tlStates) {
		this.tlStates = tlStates;
	}

	public String getContractDepositNumber() {
		return contractDepositNumber;
	}

	public void setContractDepositNumber(String contractDepositNumber) {
		this.contractDepositNumber = contractDepositNumber;
	}

}
