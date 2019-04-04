package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 满标资金划转展示类
 *
 * @author liuwei 2014年12月26日 下午6:22:01
 */
public class FullTransferRecordJDO {
    /**
     * 普通借款标id
     */
    private int        proLoanId;
    /**
     * 转让标id
     */
    private int        proTransferLoanId;
    /**
     * 收购标id
     */
    private int        proBuyLoanId;
    /**
     * 项目编号
     */
    private String     proLoanNo;
    /**
     * 项目类型
     */
    private Integer    pdoProductName;
    /**
     * 借款人
     */
    private String     proAddEmp;
    
    /**
     * 借款人号码
     */
    private String proAddEmpPhone;
    /**
     * 借款金额
     */
    private BigDecimal proLoanMoney;
    /**
     * 年化收益率
     */
    private BigDecimal proLoanRate;
    /**
     * 借款期限
     */
    private Integer    proLoanPeriod;
    /**
     * 期限单位
     */
    private Integer    proLoanPeriodUnit;
    /**
     * 满标日期
     */
    private Date       proFullDate;
    /**
     * 借款用途
     */
    private String     proLoanUse;
    /**
     * 担保机构
     */
    private String     govName;
    /**
     * 转让人\收购人
     */
    private String     cstRealName;
    /**
     * 转让人ID\收购人ID
     */
    private Integer    cstRealId;
    /**
     * 收购金额
     */
    private BigDecimal proBuyMoney;
    /**
     * 收购费
     */
    private BigDecimal proBuyFee;
    /**
     * 收购日期
     */
    private Date       proBuyDate;
    /**
     * 转让金额(元)
     */
    private BigDecimal proTransferMoney;
    /**
     * 转让后利率
     */
    private Double    proTransferRate;
    /**
     * 转让手续费(元)
     */
    private Double    proTransferFee;
    /**
     * 转让折让率
     */
    private Double    proTransferHarfRate;
    /**
     * 转让折让费(元)
     */
    private Double    proTransferDeductFee;
    /**
     * 转让时间
     */
    private Date       proTransferDate;
    /**
     * 划转日期
     */
    private Date       proStartDate;
    /**
     * 划转状态
     */
    private Integer    pro_loan_state;
    /**
     * 标类型
     */
    private Integer    bidType;
    /**
     * 划转表 主键
     */
    private Integer    bidId;
    /**
     * 划转表主键
     */
    private Integer ftrId;
    
    /**
     * 放款流水
     */
    private String     seqNo;
    
    //借款合同是否已发送
    private String contractIssend;
    public Integer getFtrId() {
		return ftrId;
	}

	public void setFtrId(Integer ftrId) {
		this.ftrId = ftrId;
	}

	public int getProLoanId() {
        return proLoanId;
    }

    public void setProLoanId(int proLoanId) {
        this.proLoanId = proLoanId;
    }

    public int getProTransferLoanId() {
        return proTransferLoanId;
    }

    public void setProTransferLoanId(int proTransferLoanId) {
        this.proTransferLoanId = proTransferLoanId;
    }

    public int getProBuyLoanId() {
        return proBuyLoanId;
    }

    public void setProBuyLoanId(int proBuyLoanId) {
        this.proBuyLoanId = proBuyLoanId;
    }

    public String getProLoanNo() {
        return proLoanNo;
    }

    public void setProLoanNo(String proLoanNo) {
        this.proLoanNo = proLoanNo;
    }

    public Integer getPdoProductName() {
        return pdoProductName;
    }

    public void setPdoProductName(Integer pdoProductName) {
        this.pdoProductName = pdoProductName;
    }

    public String getProAddEmp() {
        return proAddEmp;
    }

    public void setProAddEmp(String proAddEmp) {
        this.proAddEmp = proAddEmp;
    }

    public BigDecimal getProLoanMoney() {
        return proLoanMoney;
    }

    public void setProLoanMoney(BigDecimal proLoanMoney) {
        this.proLoanMoney = proLoanMoney;
    }

    public BigDecimal getProLoanRate() {
        return proLoanRate;
    }

    public void setProLoanRate(BigDecimal proLoanRate) {
        this.proLoanRate = proLoanRate;
    }

    public Integer getProLoanPeriod() {
        return proLoanPeriod;
    }

    public void setProLoanPeriod(Integer proLoanPeriod) {
        this.proLoanPeriod = proLoanPeriod;
    }

    public Integer getProLoanPeriodUnit() {
        return proLoanPeriodUnit;
    }

    public void setProLoanPeriodUnit(Integer proLoanPeriodUnit) {
        this.proLoanPeriodUnit = proLoanPeriodUnit;
    }

    public Date getProFullDate() {
        return proFullDate;
    }

    public void setProFullDate(Date proFullDate) {
        this.proFullDate = proFullDate;
    }

    public String getProLoanUse() {
        return proLoanUse;
    }

    public void setProLoanUse(String proLoanUse) {
        this.proLoanUse = proLoanUse;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public Date getProStartDate() {
        return proStartDate;
    }

    public void setProStartDate(Date proStartDate) {
        this.proStartDate = proStartDate;
    }

    public Integer getPro_loan_state() {
        return pro_loan_state;
    }

    public void setPro_loan_state(Integer pro_loan_state) {
        this.pro_loan_state = pro_loan_state;
    }

    public String getCstRealName() {
        return cstRealName;
    }

    public void setCstRealName(String cstRealName) {
        this.cstRealName = cstRealName;
    }

    public BigDecimal getProTransferMoney() {
        return proTransferMoney;
    }

    public void setProTransferMoney(BigDecimal proTransferMoney) {
        this.proTransferMoney = proTransferMoney;
    }

    public Double getProTransferRate() {
        return proTransferRate;
    }

    public void setProTransferRate(Double proTransferRate) {
        this.proTransferRate = proTransferRate;
    }

    public Double getProTransferFee() {
        return proTransferFee;
    }

    public void setProTransferFee(Double proTransferFee) {
        this.proTransferFee = proTransferFee;
    }

    public Double getProTransferHarfRate() {
        return proTransferHarfRate;
    }

    public void setProTransferHarfRate(Double proTransferHarfRate) {
        this.proTransferHarfRate = proTransferHarfRate;
    }

    public Double getProTransferDeductFee() {
        return proTransferDeductFee;
    }

    public void setProTransferDeductFee(Double proTransferDeductFee) {
        this.proTransferDeductFee = proTransferDeductFee;
    }

    public Integer getCstRealId() {
        return cstRealId;
    }

    public void setCstRealId(Integer cstRealId) {
        this.cstRealId = cstRealId;
    }

    public BigDecimal getProBuyMoney() {
        return proBuyMoney;
    }

    public void setProBuyMoney(BigDecimal proBuyMoney) {
        this.proBuyMoney = proBuyMoney;
    }

    public Date getProBuyDate() {
        return proBuyDate;
    }

    public void setProBuyDate(Date proBuyDate) {
        this.proBuyDate = proBuyDate;
    }

    public Integer getBidType() {
        return bidType;
    }

    public void setBidType(Integer bidType) {
        this.bidType = bidType;
    }

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public Date getProTransferDate() {
        return proTransferDate;
    }

    public void setProTransferDate(Date proTransferDate) {
        this.proTransferDate = proTransferDate;
    }

    public BigDecimal getProBuyFee() {
        return proBuyFee;
    }

    public void setProBuyFee(BigDecimal proBuyFee) {
        this.proBuyFee = proBuyFee;
    }

	public String getProAddEmpPhone() {
		return proAddEmpPhone;
	}

	public void setProAddEmpPhone(String proAddEmpPhone) {
		this.proAddEmpPhone = proAddEmpPhone;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getContractIssend() {
		return contractIssend;
	}

	public void setContractIssend(String contractIssend) {
		this.contractIssend = contractIssend;
	}

}
