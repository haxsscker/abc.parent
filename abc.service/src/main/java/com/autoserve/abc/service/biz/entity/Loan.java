/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.LoanCategory;
import com.autoserve.abc.service.biz.enums.LoanClearType;
import com.autoserve.abc.service.biz.enums.LoanPayType;
import com.autoserve.abc.service.biz.enums.LoanPeriodUnit;
import com.autoserve.abc.service.biz.enums.LoanState;
import com.autoserve.abc.service.biz.enums.LoanType;
import com.autoserve.abc.service.biz.enums.LoaneeType;

/**
 * 普通标
 * 
 * @author segen189 2014年11月22日 上午9:41:41
 */
public class Loan {
    /**
     * 主键id
     */
    private Integer        loanId;

    /**
     * 项目logo
     */
    private String         loanLogo;

    /**
     * 意向ID
     */
    private Boolean        loanFromIntent;

    /**
     * 意向ID
     */
    private Integer        loanIntentId;

    /**
     * 项目名称
     */
    private String         loanNo;

    /**
     * 借款人类型
     */
    private LoaneeType     loaneeType;

    /**
     * 借款人id
     */
    private Integer        loanUserId;

    /**
     * 借款机构id
     */
    private Integer        loanGov;

    /**
     * 担保机构id
     */
    private Integer        loanGuarGov;

    /**
     * 借款金额
     */
    private BigDecimal     loanMoney;

    /**
     * 年化收益率
     */
    private BigDecimal     loanRate;

    /**
     * 借款期限
     */
    private Integer        loanPeriod;

    /**
     * 期限类型
     */
    private LoanPeriodUnit loanPeriodUnit;

    /**
     * 最低投标金额
     */
    private BigDecimal     loanMinInvest;

    /**
     * 最高投标金额
     */
    private BigDecimal     loanMaxInvest;

    /**
     * 当前投标总额
     */
    private BigDecimal     loanCurrentInvest;

    /**
     * 当前有效投标总额
     */
    private BigDecimal     loanCurrentValidInvest;

    /**
     * 还款方式
     */
    private LoanPayType    loanPayType;

    /**
     * 项目类型
     */
    private LoanType       loanType;

    /**
     * 投资开始时间
     */
    private Date           loanInvestStarttime;

    /**
     * 投资结束时间
     */
    private Date           loanInvestEndtime;

    /**
     * 投资满标时间
     */
    private Date           loanInvestFulltime;

    /**
     * 放款成功日期
     */
    private Date           loanFullTransferedtime;

    /**
     * 结算方式
     */
    private LoanClearType  loanClearType;

    /**
     * 借款用途
     */
    private String         loanUse;

    /**
     * 项目状态
     */
    private LoanState      loanState;

    /**
     * 项目分类
     */
    private LoanCategory   loanCategory;

    /**
     * 项目分类
     */
    private Integer        loanCategoryId;

    /**
     * 附件url
     */
    private String         loanFileUrl;

    /**
     * 添加人
     */
    private Integer        loanCreator;

    /**
     * 修改人
     */
    private Integer        loanModifier;

    /**
     * 创建时间
     */
    private Date           loanCreatetime;

    /**
     * 修改时间
     */
    private Date           loanModifiytime;

    /**
     * 项目备注
     */
    private String         loanNote;
    /**
     * 借款人简介
     */
    private String         borrowerIntroduction;
    /**
     * 风控信息
     */
    private String         riskIntroduction;
    /**
     * 相关文件
     */
    private String         relevantIntroduction;
    /**
     * 二次分配标记：0：否 1：是 abc_loan.loan_secondary_allocation
     */
    //private LoanSecondaryAllocation         loanSecondaryAllocation;
    private String         loanSecondaryAllocation;
    /**
     * 二次分配收款人 abc_loan.loan_secondary_user
     */
    private Integer        loanSecondaryUser;
    /**
     * 还款日期abc_loan.loanPayDate
     */
    private Integer        loanPayDate;
    /**
     * 指定到期日 abc_loan.loan_expire_date
     */
    private Date           loanExpireDate;

    /**
     * 投资返送红包派发比例，按照1000整数倍派发。比例为零即不派发红包
     */
    private Double         investRedsendRatio;

    /**
     * 红包使用比例。比例为零即不可使用红包
     */
    private Double         investReduseRatio;
    /**
     * 红包使用范围
     */
    private String         loanRedUseScopes;
    /**
     * 项目发布时间
     */
    private Date loanReleaseDate;
    
    /**
     * 是否二次分配
     * 0 ： 否  ，1 ： 是
     */
    private String loanAutomaticBid;
    
    /**
     * 标的的合同类型
     */
    private String loanContractType;

    /**
     * 原始实际标号
     */
    private String loanOriginalNo;
    
    private String assId;
    
    /**
     * 贷后信息
     */
    private String postLoanIntroduction;
    
    private String isShow;
    private String investCode;
    //存量标的迁移流水号
    private String loanTransferSqNo;
    
    //借款合同是否已发送
    private String contractIssend;
    //借款手续费收取方式
    private String handFeeKind;
    /**
     * 收费类型，对应ChargeType枚举
     * abc_loan.fs_charge_type
     */
    //private Integer fsChargeType;
    /**
     * 服务费年化率
     * abc_loan.fs_rate
     */
    private Double fsRate;
    /**
     * 确定的金额，适用于收费类型为“按每笔收费”
     * abc_loan.fs_accurate_amount
     */
    //private BigDecimal fsAccurateAmount;
    
    public String getLoanAutomaticBid() {
		return loanAutomaticBid;
	}

	public void setLoanAutomaticBid(String loanAutomaticBid) {
		this.loanAutomaticBid = loanAutomaticBid;
	}

	public Date getLoanReleaseDate() {
		return loanReleaseDate;
	}

	public void setLoanReleaseDate(Date loanReleaseDate) {
		this.loanReleaseDate = loanReleaseDate;
	}

	public Integer getLoanSecondaryUser() {
        return loanSecondaryUser;
    }

    public void setLoanSecondaryUser(Integer loanSecondaryUser) {
        this.loanSecondaryUser = loanSecondaryUser;
    }

    public String getLoanSecondaryAllocation() {
        return loanSecondaryAllocation;
    }

    public void setLoanSecondaryAllocation(String loanSecondaryAllocation) {
        this.loanSecondaryAllocation = loanSecondaryAllocation;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getLoanLogo() {
        return loanLogo;
    }

    public void setLoanLogo(String loanLogo) {
        this.loanLogo = loanLogo;
    }

    public Boolean getLoanFromIntent() {
        return loanFromIntent;
    }

    public void setLoanFromIntent(Boolean loanFromIntent) {
        this.loanFromIntent = loanFromIntent;
    }

    public Integer getLoanIntentId() {
        return loanIntentId;
    }

    public void setLoanIntentId(Integer loanIntentId) {
        this.loanIntentId = loanIntentId;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public LoaneeType getLoaneeType() {
        return loaneeType;
    }

    public void setLoaneeType(LoaneeType loaneeType) {
        this.loaneeType = loaneeType;
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

    public LoanPeriodUnit getLoanPeriodUnit() {
        return loanPeriodUnit;
    }

    public void setLoanPeriodUnit(LoanPeriodUnit loanPeriodUnit) {
        this.loanPeriodUnit = loanPeriodUnit;
    }

    public BigDecimal getLoanMinInvest() {
        return loanMinInvest;
    }

    public void setLoanMinInvest(BigDecimal loanMinInvest) {
        this.loanMinInvest = loanMinInvest;
    }

    public BigDecimal getLoanMaxInvest() {
        return loanMaxInvest;
    }

    public void setLoanMaxInvest(BigDecimal loanMaxInvest) {
        this.loanMaxInvest = loanMaxInvest;
    }

    public BigDecimal getLoanCurrentInvest() {
        return loanCurrentInvest;
    }

    public void setLoanCurrentInvest(BigDecimal loanCurrentInvest) {
        this.loanCurrentInvest = loanCurrentInvest;
    }

    public BigDecimal getLoanCurrentValidInvest() {
        return loanCurrentValidInvest;
    }

    public void setLoanCurrentValidInvest(BigDecimal loanCurrentValidInvest) {
        this.loanCurrentValidInvest = loanCurrentValidInvest;
    }

    public LoanPayType getLoanPayType() {
        return loanPayType;
    }

    public void setLoanPayType(LoanPayType loanPayType) {
        this.loanPayType = loanPayType;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
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

    public Date getLoanFullTransferedtime() {
        return loanFullTransferedtime;
    }

    public void setLoanFullTransferedtime(Date loanFullTransferedtime) {
        this.loanFullTransferedtime = loanFullTransferedtime;
    }

    public LoanClearType getLoanClearType() {
        return loanClearType;
    }

    public void setLoanClearType(LoanClearType loanClearType) {
        this.loanClearType = loanClearType;
    }

    public String getLoanUse() {
        return loanUse;
    }

    public void setLoanUse(String loanUse) {
        this.loanUse = loanUse;
    }

    public LoanState getLoanState() {
        return loanState;
    }

    public void setLoanState(LoanState loanState) {
        this.loanState = loanState;
    }

    public LoanCategory getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(LoanCategory loanCategory) {
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

    public Integer getLoanCreator() {
        return loanCreator;
    }

    public void setLoanCreator(Integer loanCreator) {
        this.loanCreator = loanCreator;
    }

    public Integer getLoanModifier() {
        return loanModifier;
    }

    public void setLoanModifier(Integer loanModifier) {
        this.loanModifier = loanModifier;
    }

    public Date getLoanCreatetime() {
        return loanCreatetime;
    }

    public void setLoanCreatetime(Date loanCreatetime) {
        this.loanCreatetime = loanCreatetime;
    }

    public Date getLoanModifiytime() {
        return loanModifiytime;
    }

    public void setLoanModifiytime(Date loanModifiytime) {
        this.loanModifiytime = loanModifiytime;
    }

    public String getLoanNote() {
        return loanNote;
    }

    public void setLoanNote(String loanNote) {
        this.loanNote = loanNote;
    }

    public Double getInvestRedsendRatio() {
        return investRedsendRatio;
    }

    public void setInvestRedsendRatio(Double investRedsendRatio) {
        this.investRedsendRatio = investRedsendRatio;
    }

    public Double getInvestReduseRatio() {
        return investReduseRatio;
    }

    public void setInvestReduseRatio(Double investReduseRatio) {
        this.investReduseRatio = investReduseRatio;
    }

    public Integer getLoanPayDate() {
        return loanPayDate;
    }

    public void setLoanPayDate(Integer loanPayDate) {
        this.loanPayDate = loanPayDate;
    }

    public Date getLoanExpireDate() {
        return loanExpireDate;
    }

    public void setLoanExpireDate(Date loanExpireDate) {
        this.loanExpireDate = loanExpireDate;
    }

    public String getBorrowerIntroduction() {
        return borrowerIntroduction;
    }

    public void setBorrowerIntroduction(String borrowerIntroduction) {
        this.borrowerIntroduction = borrowerIntroduction;
    }

    public String getRiskIntroduction() {
        return riskIntroduction;
    }

    public void setRiskIntroduction(String riskIntroduction) {
        this.riskIntroduction = riskIntroduction;
    }

    public String getRelevantIntroduction() {
        return relevantIntroduction;
    }

    public void setRelevantIntroduction(String relevantIntroduction) {
        this.relevantIntroduction = relevantIntroduction;
    }

    public String getLoanRedUseScopes() {
        return loanRedUseScopes;
    }

    public void setLoanRedUseScopes(String loanRedUseScopes) {
        this.loanRedUseScopes = loanRedUseScopes;
    }

	public String getLoanContractType() {
		return loanContractType;
	}

	public void setLoanContractType(String loanContractType) {
		this.loanContractType = loanContractType;
	}

	public String getLoanOriginalNo() {
		return loanOriginalNo;
	}

	public void setLoanOriginalNo(String loanOriginalNo) {
		this.loanOriginalNo = loanOriginalNo;
	}

	public String getAssId() {
		return assId;
	}

	public void setAssId(String assId) {
		this.assId = assId;
	}

	public String getPostLoanIntroduction() {
		return postLoanIntroduction;
	}

	public void setPostLoanIntroduction(String postLoanIntroduction) {
		this.postLoanIntroduction = postLoanIntroduction;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getInvestCode() {
		return investCode;
	}

	public void setInvestCode(String investCode) {
		this.investCode = investCode;
	}

	public String getLoanTransferSqNo() {
		return loanTransferSqNo;
	}

	public void setLoanTransferSqNo(String loanTransferSqNo) {
		this.loanTransferSqNo = loanTransferSqNo;
	}

	public String getContractIssend() {
		return contractIssend;
	}

	public void setContractIssend(String contractIssend) {
		this.contractIssend = contractIssend;
	}

	public String getHandFeeKind() {
		return handFeeKind;
	}

	public void setHandFeeKind(String handFeeKind) {
		this.handFeeKind = handFeeKind;
	}

	/*public Integer getFsChargeType() {
		return fsChargeType;
	}

	public void setFsChargeType(Integer fsChargeType) {
		this.fsChargeType = fsChargeType;
	}*/

	public Double getFsRate() {
		return fsRate;
	}

	public void setFsRate(Double fsRate) {
		this.fsRate = fsRate;
	}

	/*public BigDecimal getFsAccurateAmount() {
		return fsAccurateAmount;
	}

	public void setFsAccurateAmount(BigDecimal fsAccurateAmount) {
		this.fsAccurateAmount = fsAccurateAmount;
	}*/
	
}
