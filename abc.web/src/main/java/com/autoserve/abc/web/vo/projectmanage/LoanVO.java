/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.projectmanage;

import java.math.BigDecimal;

/**
 * 普通标
 * 
 * @author segen189 2014年12月22日 下午8:28:39
 */
public class LoanVO {
    //--------------------------------------------------------------//
    //-------------------loan 字段信息---------------------－－－-------//
    //--------------------------------------------------------------//
    /**
     * 主键id
     */
    private Integer    loanId;

    /**
     * 项目logo
     */
    private String     loanLogo;

    /**
     * 意向编号
     */
    private Integer    loanIntentNo;

    /**
     * 项目名称
     */
    private String     loanNo;

    /**
     * 借款人类型
     */
    private String     loaneeType;

    /**
     * 借款人id
     */
    private Integer    loanUserId;

    /**
     * 借款人电话
     */
    private String    loanUserPhone;
    
    /**
     * 借款机构id
     */
    private Integer    loanGov;

    /**
     * 担保机构id
     */
    private Integer    loanGuarGov;

    /**
     * 借款金额
     */
    private BigDecimal loanMoney;

    /**
     * 年化收益率
     */
    private BigDecimal loanRate;

    /**
     * 借款期限
     */
    private Integer    loanPeriod;

    /**
     * 期限类型
     */
    private String     loanPeriodUnit;

    /**
     * 最低投标金额
     */
    private BigDecimal loanMinInvest;

    /**
     * 最高投标金额
     */
    private BigDecimal loanMaxInvest;

    /**
     * 当前投标总额
     */
    private BigDecimal loanCurrentInvest;

    /**
     * 当前有效投标总额
     */
    private BigDecimal loanCurrentValidInvest;

    /**
     * 还款方式
     */
    private String     loanPayType;
    
    /**
     * 项目类型
     */
    private String    loanType;

    /**
     * 投资开始时间
     */
    private String     loanInvestStarttime;

    /**
     * 投资结束时间
     */
    private String     loanInvestEndtime;

    /**
     * 投资满标时间
     */
    private String     loanInvestFulltime;

    /**
     * 放款成功日期
     */
    private String     loanFullTransferedtime;

    /**
     * 结算方式
     */
    private String     loanClearType;

    /**
     * 借款用途
     */
    private String     loanUse;

    /**
     * 项目状态
     */
    private String     loanState;

    /**
     * 项目分类
     */
    private String     loanCategory;

    /**
     * 项目分类
     */
    private Integer    loanCategoryId;

    /**
     * 附件url
     */
    private String     loanFileUrl;

    /**
     * 添加人
     */
    private Integer    loanCreator;

    /**
     * 修改人
     */
    private Integer    loanModifier;

    /**
     * 创建时间
     */
    private String     loanCreatetime;
    
    /**
     * 失效/过期时间
     */
    private String     loanExpireDate;

    /**
     * 修改时间
     */
    private String     loanModifiytime;
    /**
     * 发送状态
     */
    private Integer loanSendState;
   

    public Integer getLoanSendState() {
		return loanSendState;
	}

	public void setLoanSendState(Integer loanSendState) {
		this.loanSendState = loanSendState;
	}

	public String getLoanExpireDate() {
		return loanExpireDate;
	}

	public void setLoanExpireDate(String loanExpireDate) {
		this.loanExpireDate = loanExpireDate;
	}

	/**
     * 项目备注
     */
    private String     loanNote;

    /**
     * 投资返送红包派发比例，按照1000整数倍派发。比例为零即不派发红包
     */
    private Double     investRedsendRatio;

    /**
     * 红包使用比例。比例为零即不可使用红包
     */
    private Double     investReduseRatio;

    //--------------------------------------------------------------//
    //-------------------loan 转换字段信息----------------------------//
    //--------------------------------------------------------------//
    /**
     * 借款人姓名
     */
    private String     loaneeName;

    /**
     * 担保机构名称
     */
    private String     govName;

    /**
     * 项目进度
     */
    private double     loanSpeed;

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

    public Integer getLoanIntentNo() {
        return loanIntentNo;
    }

    public void setLoanIntentNo(Integer loanIntentNo) {
        this.loanIntentNo = loanIntentNo;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getLoaneeType() {
        return loaneeType;
    }

    public void setLoaneeType(String loaneeType) {
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

    public String getLoanPeriodUnit() {
        return loanPeriodUnit;
    }

    public void setLoanPeriodUnit(String loanPeriodUnit) {
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

    public String getLoanPayType() {
        return loanPayType;
    }

    public void setLoanPayType(String loanPayType) {
        this.loanPayType = loanPayType;
    }


	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getLoanInvestStarttime() {
        return loanInvestStarttime;
    }

    public void setLoanInvestStarttime(String loanInvestStarttime) {
        this.loanInvestStarttime = loanInvestStarttime;
    }

    public String getLoanInvestEndtime() {
        return loanInvestEndtime;
    }

    public void setLoanInvestEndtime(String loanInvestEndtime) {
        this.loanInvestEndtime = loanInvestEndtime;
    }

    public String getLoanInvestFulltime() {
        return loanInvestFulltime;
    }

    public void setLoanInvestFulltime(String loanInvestFulltime) {
        this.loanInvestFulltime = loanInvestFulltime;
    }

    public String getLoanFullTransferedtime() {
        return loanFullTransferedtime;
    }

    public void setLoanFullTransferedtime(String loanFullTransferedtime) {
        this.loanFullTransferedtime = loanFullTransferedtime;
    }

    public String getLoanClearType() {
        return loanClearType;
    }

    public void setLoanClearType(String loanClearType) {
        this.loanClearType = loanClearType;
    }

    public String getLoanUse() {
        return loanUse;
    }

    public void setLoanUse(String loanUse) {
        this.loanUse = loanUse;
    }

    public String getLoanState() {
        return loanState;
    }

    public void setLoanState(String loanState) {
        this.loanState = loanState;
    }

    public String getLoanCategory() {
        return loanCategory;
    }

    public void setLoanCategory(String loanCategory) {
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

    public String getLoanCreatetime() {
        return loanCreatetime;
    }

    public void setLoanCreatetime(String loanCreatetime) {
        this.loanCreatetime = loanCreatetime;
    }

    public String getLoanModifiytime() {
        return loanModifiytime;
    }

    public void setLoanModifiytime(String loanModifiytime) {
        this.loanModifiytime = loanModifiytime;
    }

    public String getLoanNote() {
        return loanNote;
    }

    public void setLoanNote(String loanNote) {
        this.loanNote = loanNote;
    }

    public String getLoaneeName() {
        return loaneeName;
    }

    public void setLoaneeName(String loaneeName) {
        this.loaneeName = loaneeName;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public double getLoanSpeed() {
        return loanSpeed;
    }

    public void setLoanSpeed(double loanSpeed) {
        this.loanSpeed = loanSpeed;
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

	public String getLoanUserPhone() {
		return loanUserPhone;
	}

	public void setLoanUserPhone(String loanUserPhone) {
		this.loanUserPhone = loanUserPhone;
	}


}
