package com.autoserve.abc.service.biz.entity;

public class AgreementMessage {

    /**
     * 合同编号
     */
    private String  AgreementNo;
    /**
     * 甲方名称
     */
    private String  FName;
    /**
     * 甲方代理人名称
     */
    private String  FAgentName;
    /**
     * 代理人用户名
     */
    private String  FAgentUserName;
    /**
     * 代理人身份证号
     */
    private String  FAgentIdCard;

    /**
     * 更新日期
     */
    private String  date;
    /**
     * 丙方名称
     */
    private String  SName;
    /**
     * 丙方地址
     */
    private String  SAddress;
    /**
     * 网址
     */

    /**
     * 借款用途
     * 
     * @return
     */
    private String  loanUse;
    /**
     * 借款金额
     * 
     * @return
     */
    private String  loanMoney;

    /**
     * 利息
     * 
     * @return
     */
    private String  loanMoneyInterest;
    /**
     * 合同期限
     * 
     * @return
     */
    private long    contractTerm;
    /**
     * 合同开始时间 格式2015-3-19日
     * 
     * @return
     */
    private String  contractStartDate;
    /**
     * 合同结束时间 格式2015-3-19日
     * 
     * @return
     */
    private String  contractEndDate;

    /**
     * 年利率
     * 
     * @return
     */
    private String  annualInterest;
    /**
     * 结息日
     * 
     * @return
     */
    private int     settlementDate;

    /**
     * 罚息利率
     * 
     * @return
     */
    private String  punitiveInterest;

    private String  SWEBSITE;
    /**
     * 企业营业执照号
     */
    private String  ccBusinessLicense;
    /**
     * 是否是企业
     */
    private boolean isCompany;
    
    /**
     * 普通标合同类型 0:点对点 1:转让
     */
    private String loanContractType;
    
    /**
     * 原始标的合同编号
     */
    private String loanOriginalNo;
    /**
     * 合同对象
     */
    private int contractObject;
    
    public boolean isCompany() {
        return isCompany;
    }

    public void setCompany(boolean isCompany) {
        this.isCompany = isCompany;
    }

    public String getCcBusinessLicense() {
        return ccBusinessLicense;
    }

    public void setCcBusinessLicense(String ccBusinessLicense) {
        this.ccBusinessLicense = ccBusinessLicense;
    }

    public String getSWEBSITE() {
        return SWEBSITE;
    }

    public void setSWEBSITE(String sWEBSITE) {
        SWEBSITE = sWEBSITE;
    }

    public String getPunitiveInterest() {
        return punitiveInterest;
    }

    public void setPunitiveInterest(String punitiveInterest) {
        this.punitiveInterest = punitiveInterest;
    }

    public int getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(int settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getAnnualInterest() {
        return annualInterest;
    }

    public void setAnnualInterest(String annualInterest) {
        this.annualInterest = annualInterest;
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public long getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(long contractTerm) {
        this.contractTerm = contractTerm;
    }

    public String getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(String loanMoney) {
        this.loanMoney = loanMoney;
    }

    public String getLoanUse() {
        return loanUse;
    }

    public void setLoanUse(String loanUse) {
        this.loanUse = loanUse;
    }

    public String getAgreementNo() {
        return AgreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        AgreementNo = agreementNo;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String fName) {
        FName = fName;
    }

    public String getFAgentName() {
        return FAgentName;
    }

    public void setFAgentName(String fAgentName) {
        FAgentName = fAgentName;
    }

    public String getFAgentUserName() {
        return FAgentUserName;
    }

    public void setFAgentUserName(String fAgentUserName) {
        FAgentUserName = fAgentUserName;
    }

    public String getFAgentIdCard() {
        return FAgentIdCard;
    }

    public void setFAgentIdCard(String fAgentIdCard) {
        FAgentIdCard = fAgentIdCard;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String sName) {
        SName = sName;
    }

    public String getSAddress() {
        return SAddress;
    }

    public void setSAddress(String sAddress) {
        SAddress = sAddress;
    }

    public String getLoanMoneyInterest() {
        return loanMoneyInterest;
    }

    public void setLoanMoneyInterest(String loanMoneyInterest) {
        this.loanMoneyInterest = loanMoneyInterest;
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

	public int getContractObject() {
		return contractObject;
	}

	public void setContractObject(int contractObject) {
		this.contractObject = contractObject;
	}

}
