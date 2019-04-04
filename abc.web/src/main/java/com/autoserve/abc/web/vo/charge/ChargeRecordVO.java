package com.autoserve.abc.web.vo.charge;

import java.math.BigDecimal;

/**
 * 收费记录查询VO
 * 
 * @author liuwei 2014年12月17日 上午11:10:10
 */
public class ChargeRecordVO {

    /**
     * 项目名称
     */
    private String     project_number;
    /**
     * 项目分类
     */
    private String     project_type;
    /**
     * 借款人
     */
    private String     borrower;
    /**
     * 借款金额
     */
    private BigDecimal borrowing_amount = BigDecimal.ZERO;
    /**
     * 年化收益率
     */
    private BigDecimal annual_rate;
    /**
     * 借款期限
     */
    private String     Loan_period;
    /**
     * 收取服务费用
     */
    private BigDecimal service_fee      = BigDecimal.ZERO;
    /**
     * 收取手续费用
     */
    private BigDecimal charge_fee       = BigDecimal.ZERO;
    /**
     * 开始日期
     */
    private String     star_date;
    /**
     * 到期日期
     */
    private String     end_date;
    /**
     * 担保机构
     */
    private String     guarantee_institutions;

    public String getProject_number() {
        return project_number;
    }

    public void setProject_number(String project_number) {
        this.project_number = project_number;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public BigDecimal getBorrowing_amount() {
        return borrowing_amount;
    }

    public void setBorrowing_amount(BigDecimal borrowing_amount) {
        this.borrowing_amount = borrowing_amount;
    }

    public BigDecimal getAnnual_rate() {
        return annual_rate;
    }

    public void setAnnual_rate(BigDecimal annual_rate) {
        this.annual_rate = annual_rate;
    }

    public String getLoan_period() {
        return Loan_period;
    }

    public void setLoan_period(String loan_period) {
        Loan_period = loan_period;
    }

    public BigDecimal getService_fee() {
        return service_fee;
    }

    public void setService_fee(BigDecimal service_fee) {
        this.service_fee = service_fee;
    }

    public BigDecimal getCharge_fee() {
        return this.charge_fee;
    }

    public void setCharge_fee(BigDecimal charge_fee) {
        this.charge_fee = charge_fee;
    }

    public String getStar_date() {
        return star_date;
    }

    public void setStar_date(String star_date) {
        this.star_date = star_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getGuarantee_institutions() {
        return guarantee_institutions;
    }

    public void setGuarantee_institutions(String guarantee_institutions) {
        this.guarantee_institutions = guarantee_institutions;
    }

    /**
     * 累计增加服务费
     * 
     * @param fee
     * @return
     */
    public BigDecimal addServicefee(BigDecimal fee) {
        if (this.service_fee == null) {
            service_fee = BigDecimal.ZERO;
        }
        if (fee == null) {
            fee = BigDecimal.ZERO;
        }
        return this.service_fee = this.service_fee.add(fee);
    }

    /**
     * 累计增加手续费
     * 
     * @param fee
     * @return
     */
    public BigDecimal addChargefee(BigDecimal fee) {
        if (this.charge_fee == null) {
            this.charge_fee = BigDecimal.ZERO;
        }
        if (fee == null) {
            fee = BigDecimal.ZERO;
        }
        return this.charge_fee = this.charge_fee.add(fee);
    }

}
