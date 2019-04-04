package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * HouseLoan abc_loan_house
 */
public class LoanHouseDO {
    /**
     * 主键id abc_loan_house.lh_id
     */
    private Integer    lhId;

    /**
     * 贷款id abc_loan_house.lh_loan_id
     */
    private Integer    lhLoanId;

    /**
     * 房产面积 abc_loan_house.lh_house_measure
     */
    private BigDecimal lhHouseMeasure;

    /**
     * 占地面积 abc_loan_house.lh_cover_measure
     */
    private BigDecimal lhCoverMeasure;

    /**
     * 房产编号 abc_loan_house.lh_house_no
     */
    private String     lhHouseNo;

    /**
     * 所属小区 abc_loan_house.lh_house_area
     */
    private String     lhHouseArea;

    /**
     * 房龄 abc_loan_house.lh_house_age
     */
    private BigDecimal lhHouseAge;

    /**
     * 是否按揭 1:是 0:否 abc_loan_house.lh_is_mortgage
     */
    private Boolean    lhIsMortgage;

    /**
     * 评估价格 abc_loan_house.lh_assess_money
     */
    private BigDecimal lhAssessMoney;

    /**
     * 添加时间
     */
    private Date       lhCreatetime;

    /**
     * 修改时间
     */
    private Date       lhModifytime;

    /**
     * 是否被删除
     */
    private Boolean    lhIsDeleted;

    public Integer getLhId() {
        return lhId;
    }

    public void setLhId(Integer lhId) {
        this.lhId = lhId;
    }

    public Integer getLhLoanId() {
        return lhLoanId;
    }

    public void setLhLoanId(Integer lhLoanId) {
        this.lhLoanId = lhLoanId;
    }

    public BigDecimal getLhHouseMeasure() {
        return lhHouseMeasure;
    }

    public void setLhHouseMeasure(BigDecimal lhHouseMeasure) {
        this.lhHouseMeasure = lhHouseMeasure;
    }

    public BigDecimal getLhCoverMeasure() {
        return lhCoverMeasure;
    }

    public void setLhCoverMeasure(BigDecimal lhCoverMeasure) {
        this.lhCoverMeasure = lhCoverMeasure;
    }

    public String getLhHouseNo() {
        return lhHouseNo;
    }

    public void setLhHouseNo(String lhHouseNo) {
        this.lhHouseNo = lhHouseNo;
    }

    public String getLhHouseArea() {
        return lhHouseArea;
    }

    public void setLhHouseArea(String lhHouseArea) {
        this.lhHouseArea = lhHouseArea;
    }

    public BigDecimal getLhHouseAge() {
        return lhHouseAge;
    }

    public void setLhHouseAge(BigDecimal lhHouseAge) {
        this.lhHouseAge = lhHouseAge;
    }

    public Boolean getLhIsMortgage() {
        return lhIsMortgage;
    }

    public void setLhIsMortgage(Boolean lhIsMortgage) {
        this.lhIsMortgage = lhIsMortgage;
    }

    public BigDecimal getLhAssessMoney() {
        return lhAssessMoney;
    }

    public void setLhAssessMoney(BigDecimal lhAssessMoney) {
        this.lhAssessMoney = lhAssessMoney;
    }

    public Date getLhCreatetime() {
        return lhCreatetime;
    }

    public void setLhCreatetime(Date lhCreatetime) {
        this.lhCreatetime = lhCreatetime;
    }

    public Date getLhModifytime() {
        return lhModifytime;
    }

    public void setLhModifytime(Date lhModifytime) {
        this.lhModifytime = lhModifytime;
    }

    public Boolean getLhIsDeleted() {
        return lhIsDeleted;
    }

    public void setLhIsDeleted(Boolean lhIsDeleted) {
        this.lhIsDeleted = lhIsDeleted;
    }

}
