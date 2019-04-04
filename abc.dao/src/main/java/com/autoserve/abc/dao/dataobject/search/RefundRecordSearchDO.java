package com.autoserve.abc.dao.dataobject.search;

/**
 * 退费查询类
 *
 * @author liuwei 2014年12月25日 下午4:50:55
 */
public class RefundRecordSearchDO {

    /**
     * 退费对象
     */
    private String returnsObject;
    /**
     * 退费账号
     */
    private String refundAccount;
    /**
     * 最小退费对象
     */
    private String costsMin;
    /**
     * 最大退回费用
     */
    private String costsMax;
    /**
     * 手机号码
     */
    private String phoneNumber;

    public String getReturnsObject() {
        return returnsObject;
    }

    public void setReturnsObject(String returnsObject) {
        this.returnsObject = returnsObject;
    }

    public String getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(String refundAccount) {
        this.refundAccount = refundAccount;
    }

    public String getCostsMin() {
        return costsMin;
    }

    public void setCostsMin(String costsMin) {
        this.costsMin = costsMin;
    }

    public String getCostsMax() {
        return costsMax;
    }

    public void setCostsMax(String costsMax) {
        this.costsMax = costsMax;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
