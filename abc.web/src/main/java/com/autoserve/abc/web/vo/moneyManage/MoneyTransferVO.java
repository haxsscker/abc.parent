package com.autoserve.abc.web.vo.moneyManage;

import java.math.BigDecimal;

/**
 * 资金划转VO
 * 
 * @author liuwei 2014年12月31日 下午2:08:29
 */
public class MoneyTransferVO {

    /**
     * 应收担保费
     */
    private BigDecimal len_pay_guar_fee;
    /**
     * 应收手续费
     */
    private BigDecimal len_pay_fee;
    /**
     * 应划转金额
     */
    private BigDecimal len_pay_total;
    /**
     * 实收担保费
     */
    private BigDecimal len_collect_guar_fee;
    /**
     * 实收手续费
     */
    private BigDecimal len_collect_fee;
    /**
     * 实划转金额
     */
    private BigDecimal len_lend_money;

    public BigDecimal getLen_pay_guar_fee() {
        return len_pay_guar_fee;
    }

    public void setLen_pay_guar_fee(BigDecimal len_pay_guar_fee) {
        this.len_pay_guar_fee = len_pay_guar_fee;
    }

    public BigDecimal getLen_pay_fee() {
        return len_pay_fee;
    }

    public void setLen_pay_fee(BigDecimal len_pay_fee) {
        this.len_pay_fee = len_pay_fee;
    }

    public BigDecimal getLen_pay_total() {
        return len_pay_total;
    }

    public void setLen_pay_total(BigDecimal len_pay_total) {
        this.len_pay_total = len_pay_total;
    }

    public BigDecimal getLen_collect_guar_fee() {
        return len_collect_guar_fee;
    }

    public void setLen_collect_guar_fee(BigDecimal len_collect_guar_fee) {
        this.len_collect_guar_fee = len_collect_guar_fee;
    }

    public BigDecimal getLen_collect_fee() {
        return len_collect_fee;
    }

    public void setLen_collect_fee(BigDecimal len_collect_fee) {
        this.len_collect_fee = len_collect_fee;
    }

    public BigDecimal getLen_lend_money() {
        return len_lend_money;
    }

    public void setLen_lend_money(BigDecimal len_lend_money) {
        this.len_lend_money = len_lend_money;
    }

}
