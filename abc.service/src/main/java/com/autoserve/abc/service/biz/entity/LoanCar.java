package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yuqing.zheng Created on 2014-12-13,21:10
 */
public class LoanCar {
    /**
     * 主键
     */
    private Integer    id;

    /**
     * 贷款id
     */
    private Integer    loanId;

    /**
     * 汽车品牌
     */
    private String     brand;

    /**
     * 汽车车系
     */
    private String     series;

    /**
     * 汽车排量
     */
    private String     output;

    /**
     * 汽车颜色
     */
    private String     color;

    /**
     * 购买年份
     */
    private Integer    buyYear;

    /**
     * 上牌日期
     */
    private Date       time;

    /**
     * 里程数
     */
    private BigDecimal run;

    /**
     * 评估价格
     */
    private BigDecimal assessMoney;

    /**
     * 汽车现址
     */
    private String     carAddress;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getBuyYear() {
        return buyYear;
    }

    public void setBuyYear(Integer buyYear) {
        this.buyYear = buyYear;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public BigDecimal getRun() {
        return run;
    }

    public void setRun(BigDecimal run) {
        this.run = run;
    }

    public BigDecimal getAssessMoney() {
        return assessMoney;
    }

    public void setAssessMoney(BigDecimal assessMoney) {
        this.assessMoney = assessMoney;
    }

    public String getCarAddress() {
        return carAddress;
    }

    public void setCarAddress(String carAddress) {
        this.carAddress = carAddress;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
