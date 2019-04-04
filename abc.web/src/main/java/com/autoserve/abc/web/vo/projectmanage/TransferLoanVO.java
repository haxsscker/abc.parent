/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.web.vo.projectmanage;

import java.math.BigDecimal;

import com.autoserve.abc.service.biz.entity.TransferLoan;

/**
 * 转让标
 * 
 * @author segen189 2014年12月22日 下午8:28:39
 */
public class TransferLoanVO extends TransferLoan {

    //--------------------------------------------------------------//
    //-------------------transferLoan 转换字段信息---------------------//
    //--------------------------------------------------------------//
    /**
     * 转让状态
     */
    private String     transferLoanStateStr;

    /**
     * 转让申请日期 1、当转让人部分转让时，存在剩余金额，且同一笔贷款时，若第一笔债权未满标时，不允许转让第二次；\n2、当未放款前，借款人还钱了，
     * 那么该转让申请作废，投资人钱重新进行返还，借款人把钱还给原债权人
     */
    private String     createtimeStr;

    /**
     * 投资开始时间
     */
    private String     investStarttimeStr;

    /**
     * 投资结束时间
     */
    private String     investEndtimeStr;

    /**
     * 满标日期
     */
    private String     fulltimeStr;

    /**
     * 放款成功时间
     */
    private String     fullTransferedtimeStr;

    /**
     * 转让人
     */
    private String     tranferUserName;

    /**
     * 已还期数
     */
    private Integer    payedPaymentPeriod;

    //--------------------------------------------------------------//
    //-------------------loan 字段信息-------------------------－-----//
    //--------------------------------------------------------------//
    /**
     * 项目名称
     */
    private String     loanNo;

    /**
     * 借款人类型
     */
    private String     loanEmpType;

    /**
     * 借款人id
     */
    private Integer    loanUserId;
    
    /**
     * 借款人号码
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
     * 还款方式
     */
    private String     loanPayType;

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
     * 结算方式
     */
    private String     loanClearType;

    /**
     * 项目分类
     */
    private String     loanCategory;

    /**
     * 项目分类外键id
     */
    private Integer    loanCategoryId;

    /**
     * 附件url
     */
    private String     loanFileUrl;

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

    public String getTransferLoanStateStr() {
        return transferLoanStateStr;
    }

    public void setTransferLoanStateStr(String transferLoanStateStr) {
        this.transferLoanStateStr = transferLoanStateStr;
    }

    public String getCreatetimeStr() {
        return createtimeStr;
    }

    public void setCreatetimeStr(String createtimeStr) {
        this.createtimeStr = createtimeStr;
    }

    public String getInvestStarttimeStr() {
        return investStarttimeStr;
    }

    public void setInvestStarttimeStr(String investStarttimeStr) {
        this.investStarttimeStr = investStarttimeStr;
    }

    public String getInvestEndtimeStr() {
        return investEndtimeStr;
    }

    public void setInvestEndtimeStr(String investEndtimeStr) {
        this.investEndtimeStr = investEndtimeStr;
    }

    public String getFulltimeStr() {
        return fulltimeStr;
    }

    public void setFulltimeStr(String fulltimeStr) {
        this.fulltimeStr = fulltimeStr;
    }

    public String getFullTransferedtimeStr() {
        return fullTransferedtimeStr;
    }

    public void setFullTransferedtimeStr(String fullTransferedtimeStr) {
        this.fullTransferedtimeStr = fullTransferedtimeStr;
    }

    public String getTranferUserName() {
        return tranferUserName;
    }

    public void setTranferUserName(String tranferUserName) {
        this.tranferUserName = tranferUserName;
    }

    public Integer getPayedPaymentPeriod() {
        return payedPaymentPeriod;
    }

    public void setPayedPaymentPeriod(Integer payedPaymentPeriod) {
        this.payedPaymentPeriod = payedPaymentPeriod;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }

    public String getLoanEmpType() {
        return loanEmpType;
    }

    public void setLoanEmpType(String loanEmpType) {
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

    public String getLoanPeriodUnit() {
        return loanPeriodUnit;
    }

    public void setLoanPeriodUnit(String loanPeriodUnit) {
        this.loanPeriodUnit = loanPeriodUnit;
    }

    public String getLoanPayType() {
        return loanPayType;
    }

    public void setLoanPayType(String loanPayType) {
        this.loanPayType = loanPayType;
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

    public String getLoanClearType() {
        return loanClearType;
    }

    public void setLoanClearType(String loanClearType) {
        this.loanClearType = loanClearType;
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

	public String getLoanUserPhone() {
		return loanUserPhone;
	}

	public void setLoanUserPhone(String loanUserPhone) {
		this.loanUserPhone = loanUserPhone;
	}

}
