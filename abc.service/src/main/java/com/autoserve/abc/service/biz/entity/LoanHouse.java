package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuqing.zheng Created on 2014-12-13,21:10
 */
public class LoanHouse {
    /**
     * 主键id
     */
    private Integer    id;

    /**
     * 贷款id
     */
    private Integer    loanId;

    /**
     * 房产面积
     */
    private BigDecimal houseMeasure;

    /**
     * 占地面积
     */
    private BigDecimal coverMeasure;

    /**
     * 房产编号
     */
    private String     houseNo;

    /**
     * 所属小区
     */
    private String     houseArea;

    /**
     * 房龄
     */
    private BigDecimal houseAge;

    /**
     * 是否按揭
     */
    private Boolean    isMortgage;

    /**
     * 评估价格
     */
    private BigDecimal assessMoney;

    /**
     * 添加时间
     */
    private Date       createtime;

    /**
     * 修改时间
     */
    private Date       modifytime;

    /**
     * 是否被删除
     */
    private Boolean    isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getHouseMeasure() {
        return houseMeasure;
    }

    public void setHouseMeasure(BigDecimal houseMeasure) {
        this.houseMeasure = houseMeasure;
    }

    public BigDecimal getCoverMeasure() {
        return coverMeasure;
    }

    public void setCoverMeasure(BigDecimal coverMeasure) {
        this.coverMeasure = coverMeasure;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }

    public BigDecimal getHouseAge() {
        return houseAge;
    }

    public void setHouseAge(BigDecimal houseAge) {
        this.houseAge = houseAge;
    }

    public Boolean isMortgage() {
        return isMortgage;
    }

    public void setIsMortgage(Boolean isMortgage) {
        this.isMortgage = isMortgage;
    }

    public BigDecimal getAssessMoney() {
        return assessMoney;
    }

    public void setAssessMoney(BigDecimal assessMoney) {
        this.assessMoney = assessMoney;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public Boolean getIsMortgage() {
        return isMortgage;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
