package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CarLoan abc_loan_car
 */
public class LoanCarDO {
    /**
     * 主键 abc_loan_car.lc_id
     */
    private Integer    lcId;

    /**
     * 贷款id abc_loan_car.lc_loan_id
     */
    private Integer    lcLoanId;

    /**
     * 汽车品牌 abc_loan_car.lc_car_brand
     */
    private String     lcCarBrand;

    /**
     * 汽车车系 abc_loan_car.lc_car_series
     */
    private String     lcCarSeries;

    /**
     * 汽车排量 abc_loan_car.lc_car_output
     */
    private String     lcCarOutput;

    /**
     * 汽车颜色 abc_loan_car.lc_car_color
     */
    private String     lcCarColor;

    /**
     * 购买年份 abc_loan_car.lc_buy_year
     */
    private Integer    lcBuyYear;

    /**
     * 上牌日期 abc_loan_car.lc_car_time
     */
    private Date       lcCarTime;

    /**
     * 里程数 abc_loan_car.lc_car_run
     */
    private BigDecimal lcCarRun;

    /**
     * 评估价格 abc_loan_car.lc_assess_money
     */
    private BigDecimal lcAssessMoney;

    /**
     * 汽车现址 abc_loan_car.lc_car_address
     */
    private String     lcCarAddress;

    /**
     * 添加时间
     */
    private Date       lcCreatetime;

    /**
     * 修改时间
     */
    private Date       lcModifytime;

    /**
     * 是否被删除
     */
    private Boolean    lcIsDeleted;

    public Integer getLcId() {
        return lcId;
    }

    public void setLcId(Integer lcId) {
        this.lcId = lcId;
    }

    public Integer getLcLoanId() {
        return lcLoanId;
    }

    public void setLcLoanId(Integer lcLoanId) {
        this.lcLoanId = lcLoanId;
    }

    public String getLcCarBrand() {
        return lcCarBrand;
    }

    public void setLcCarBrand(String lcCarBrand) {
        this.lcCarBrand = lcCarBrand;
    }

    public String getLcCarSeries() {
        return lcCarSeries;
    }

    public void setLcCarSeries(String lcCarSeries) {
        this.lcCarSeries = lcCarSeries;
    }

    public String getLcCarOutput() {
        return lcCarOutput;
    }

    public void setLcCarOutput(String lcCarOutput) {
        this.lcCarOutput = lcCarOutput;
    }

    public String getLcCarColor() {
        return lcCarColor;
    }

    public void setLcCarColor(String lcCarColor) {
        this.lcCarColor = lcCarColor;
    }

    public Integer getLcBuyYear() {
        return lcBuyYear;
    }

    public void setLcBuyYear(Integer lcBuyYear) {
        this.lcBuyYear = lcBuyYear;
    }

    public Date getLcCarTime() {
        return lcCarTime;
    }

    public void setLcCarTime(Date lcCarTime) {
        this.lcCarTime = lcCarTime;
    }

    public BigDecimal getLcCarRun() {
        return lcCarRun;
    }

    public void setLcCarRun(BigDecimal lcCarRun) {
        this.lcCarRun = lcCarRun;
    }

    public BigDecimal getLcAssessMoney() {
        return lcAssessMoney;
    }

    public void setLcAssessMoney(BigDecimal lcAssessMoney) {
        this.lcAssessMoney = lcAssessMoney;
    }

    public String getLcCarAddress() {
        return lcCarAddress;
    }

    public void setLcCarAddress(String lcCarAddress) {
        this.lcCarAddress = lcCarAddress;
    }

    public Date getLcCreatetime() {
        return lcCreatetime;
    }

    public void setLcCreatetime(Date lcCreatetime) {
        this.lcCreatetime = lcCreatetime;
    }

    public Date getLcModifytime() {
        return lcModifytime;
    }

    public void setLcModifytime(Date lcModifytime) {
        this.lcModifytime = lcModifytime;
    }

    public Boolean getLcIsDeleted() {
        return lcIsDeleted;
    }

    public void setLcIsDeleted(Boolean lcIsDeleted) {
        this.lcIsDeleted = lcIsDeleted;
    }

}
