package com.autoserve.abc.service.biz.entity;

public class ContractBean {
    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 乙方姓名
     */
    private String PartyBName;
    /**
     * 乙方用户名
     */
    private String PartyBUserName;
    /**
     * 乙方身份证号
     */
    private String PartyBCardNo;
    /**
     * 丙方公司名称
     */
    private String PartyCName;
    /**
     * 丙方网址
     */
    private String PartyCUserName;
    /**
     * 丙方地址
     */
    private String PartyCCardNo;
    /**
     * 合同开始日期
     */
    private String contractStartDate;

    /**
     * 合同结束时间 格式2015-3-19日
     * 
     * @return
     */
    private String contractEndDate;
    /**
     * 债权转让金额
     */
    private String contractMoney;
    /**
     * 本金
     */
    private String contractPrincipal;
    /**
     * 利息
     */
    private String contractInterest;
    /**
     * 结息日
     * 
     * @return
     */
    private int    settlementDate;
    /**
     * 罚息利率
     * 
     * @return
     */
    private String punitiveInterest;
    /**
     * 年利率
     * 
     * @return
     */
    private String annualInterest;

    /**
     * 合同期限
     * 
     * @return
     */
    private long   contractTerm;
    /**
     * 合同对象
     */
    private int   contractObject;
    
    private String OriginContractNo = "";

    public long getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(long contractTerm) {
        this.contractTerm = contractTerm;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPartyBName() {
        return PartyBName;
    }

    public void setPartyBName(String partyBName) {
        PartyBName = partyBName;
    }

    public String getPartyBUserName() {
        return PartyBUserName;
    }

    public void setPartyBUserName(String partyBUserName) {
        PartyBUserName = partyBUserName;
    }

    public String getPartyBCardNo() {
        return PartyBCardNo;
    }

    public void setPartyBCardNo(String partyBCardNo) {
        PartyBCardNo = partyBCardNo;
    }

    public String getPartyCName() {
        return PartyCName;
    }

    public void setPartyCName(String partyCName) {
        PartyCName = partyCName;
    }

    public String getPartyCUserName() {
        return PartyCUserName;
    }

    public void setPartyCUserName(String partyCUserName) {
        PartyCUserName = partyCUserName;
    }

    public String getPartyCCardNo() {
        return PartyCCardNo;
    }

    public void setPartyCCardNo(String partyCCardNo) {
        PartyCCardNo = partyCCardNo;
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

    public String getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(String contractMoney) {
        this.contractMoney = contractMoney;
    }

    public String getContractPrincipal() {
        return contractPrincipal;
    }

    public void setContractPrincipal(String contractPrincipal) {
        this.contractPrincipal = contractPrincipal;
    }

    public String getContractInterest() {
        return contractInterest;
    }

    public void setContractInterest(String contractInterest) {
        this.contractInterest = contractInterest;
    }

    public int getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(int settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getPunitiveInterest() {
        return punitiveInterest;
    }

    public void setPunitiveInterest(String punitiveInterest) {
        this.punitiveInterest = punitiveInterest;
    }

    public String getAnnualInterest() {
        return annualInterest;
    }

    public void setAnnualInterest(String annualInterest) {
        this.annualInterest = annualInterest;
    }

	public String getOriginContractNo() {
		return OriginContractNo;
	}

	public void setOriginContractNo(String originContractNo) {
		OriginContractNo = originContractNo;
	}

	public int getContractObject() {
		return contractObject;
	}

	public void setContractObject(int contractObject) {
		this.contractObject = contractObject;
	}

}
