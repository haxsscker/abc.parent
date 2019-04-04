package com.autoserve.abc.web.vo.refund;

import java.math.BigDecimal;

/**
 * 类RefundRecordVO.java的实现描述：前台VO
 * 
 * @author liuwei 2014年12月18日 上午11:02:01
 */
public class RefundRecordVO {
    /**
     * ID
     */
    private Integer    returns_id;
    /**
     * 退费对象
     */
    private String     returns_object;
    /**
     * 退费账号
     */
    private String     refund_account;
    /**
     * 手机号码
     */
    private String     phone_number;
    /**
     * 退回费用
     */
    private BigDecimal return_costs;
    /**
     * 退回原因
     */
    private String     return_reasons;
    /**
     * 退费状态
     */
    private int        refund_state;

    public String getReturns_object() {
        return returns_object;
    }

    public void setReturns_object(String returns_object) {
        this.returns_object = returns_object;
    }

    public String getRefund_account() {
        return refund_account;
    }

    public void setRefund_account(String refund_account) {
        this.refund_account = refund_account;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public BigDecimal getReturn_costs() {
        return return_costs;
    }

    public void setReturn_costs(BigDecimal return_costs) {
        this.return_costs = return_costs;
    }

    public String getReturn_reasons() {
        return return_reasons;
    }

    public void setReturn_reasons(String return_reasons) {
        this.return_reasons = return_reasons;
    }

    public Integer getReturns_id() {
        return returns_id;
    }

    public void setReturns_id(Integer returns_id) {
        this.returns_id = returns_id;
    }

    public int getRefund_state() {
        return refund_state;
    }

    public void setRefund_state(int refund_state) {
        this.refund_state = refund_state;
    }

}
