package com.autoserve.abc.dao.dataobject.search;

import java.math.BigDecimal;
import java.util.List;

/**
 * 资金划转搜索类
 *
 * @author liuwei 2014年12月27日 下午3:03:33
 */
public class FullTransferRecordSearchDO {
    /**
     * 项目编号
     */
    private String        proLoanNo;
    /**
     * 担保机构
     */
    private String        govName;
    /**
     * 满标日期开始
     */
    private String        startProFullDate;
    /**
     * 满标日期结束
     */
    private String        endProFullDate;
    /**
     * 项目类型
     */
    private String        pdoProductName;
    /**
     * 划转日期开始
     */
    private String        proStartDate;
    /**
     * 划转日期结束
     */
    private String        proEndDate;
    /**
     * 划转状态
     */
    private String        proLoanState;
    /**
     * 收购人
     */
    private String        cstRealName;
    /**
     * 收购金额最小
     */
    private BigDecimal    minBuyMoney;
    /**
     * 最大收购金额
     */
    private BigDecimal    maxBuyMoney;

    /**
     * 项目状态
     */
    private List<Integer> loanStates;

    public String getProLoanNo() {
        return proLoanNo;
    }

    public void setProLoanNo(String proLoanNo) {
        this.proLoanNo = proLoanNo;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getStartProFullDate() {
        return startProFullDate;
    }

    public void setStartProFullDate(String startProFullDate) {
        this.startProFullDate = startProFullDate;
    }

    public String getEndProFullDate() {
        return endProFullDate;
    }

    public void setEndProFullDate(String endProFullDate) {
        this.endProFullDate = endProFullDate;
    }

    public String getPdoProductName() {
        return pdoProductName;
    }

    public void setPdoProductName(String pdoProductName) {
        this.pdoProductName = pdoProductName;
    }

    public String getProStartDate() {
        return proStartDate;
    }

    public void setProStartDate(String proStartDate) {
        this.proStartDate = proStartDate;
    }

    public String getProEndDate() {
        return proEndDate;
    }

    public void setProEndDate(String proEndDate) {
        this.proEndDate = proEndDate;
    }

    public String getProLoanState() {
        return proLoanState;
    }

    public void setProLoanState(String proLoanState) {
        this.proLoanState = proLoanState;
    }

    public BigDecimal getMinBuyMoney() {
        return minBuyMoney;
    }

    public void setMinBuyMoney(BigDecimal minBuyMoney) {
        this.minBuyMoney = minBuyMoney;
    }

    public BigDecimal getMaxBuyMoney() {
        return maxBuyMoney;
    }

    public void setMaxBuyMoney(BigDecimal maxBuyMoney) {
        this.maxBuyMoney = maxBuyMoney;
    }

    public String getCstRealName() {
        return cstRealName;
    }

    public void setCstRealName(String cstRealName) {
        this.cstRealName = cstRealName;
    }

    public List<Integer> getLoanStates() {
        return loanStates;
    }

    public void setLoanStates(List<Integer> loanStates) {
        this.loanStates = loanStates;
    }

}
