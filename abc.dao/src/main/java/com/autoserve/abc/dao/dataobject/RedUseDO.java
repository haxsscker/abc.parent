package com.autoserve.abc.dao.dataobject;

import java.util.Date;

/**
 * 类RedUseDO.java的实现描述：dao层 红包使用实体类
 * 
 * @author fangrui 2014年12月26日 下午8:12:10
 */
public class RedUseDO {
    /**
     * abc_red_use.ru_id
     */
    private Integer ruId;

    /**
     * 红包发放id abc_red_use.ru_redvsend_id
     */
    private Integer ruRedvsendId;

    /**
     * 红包使用类型 ：如投资 abc_red_use.ru_type
     */
    private Integer ruType;

    /**
     * 红包使用投资id 如果收购也可以使用就再添加一个收购id abc_red_use.ru_biz_id
     */
    private Integer ruBizId;

    /**
     * 红包用户id abc_red_use.ru_userid
     */
    private Integer ruUserid;

    /**
     * 红包使用时间 abc_red_use.ru_usetime
     */
    private Date    ruUsetime;

    /**
     * 红包使用描述 abc_red_use.ru_desc
     */
    private String  ruDesc;

    /**
     * 红包使用金额 abc_red_use.ru_amount
     */
    private Double  ruAmount;

    /**
     * 红包剩余金额 abc_red_use.ru_remainder_amount
     */
    private Double  ruRemainderAmount;

    /**
     * 抵扣手续费 abc_red_use.ru_deduct_fee
     */
    private Double  ruDeductFee;

    /**
     * 抵扣折让费 abc_red_use.ru_deduct_discount
     */
    private Double  ruDeductDiscount;

    /**
     * 红包使用次数 abc_red_use.ru_usecount
     */
    private Integer ruUsecount;

    /**
     * @return abc_red_use.ru_id
     */
    public Integer getRuId() {
        return ruId;
    }

    /**
     * @param Integer ruId (abc_red_use.ru_id )
     */
    public void setRuId(Integer ruId) {
        this.ruId = ruId;
    }

    /**
     * @return abc_red_use.ru_redvsend_id
     */
    public Integer getRuRedvsendId() {
        return ruRedvsendId;
    }

    /**
     * @param Integer ruRedvsendId (abc_red_use.ru_redvsend_id )
     */
    public void setRuRedvsendId(Integer ruRedvsendId) {
        this.ruRedvsendId = ruRedvsendId;
    }

    /**
     * @return abc_red_use.ru_type
     */
    public Integer getRuType() {
        return ruType;
    }

    /**
     * @param Integer ruType (abc_red_use.ru_type )
     */
    public void setRuType(Integer ruType) {
        this.ruType = ruType;
    }

    /**
     * @return abc_red_use.ru_biz_id
     */
    public Integer getRuBizId() {
        return ruBizId;
    }

    /**
     * @param Integer ruBizId (abc_red_use.ru_biz_id )
     */
    public void setRuBizId(Integer ruBizId) {
        this.ruBizId = ruBizId;
    }

    /**
     * @return abc_red_use.ru_userid
     */
    public Integer getRuUserid() {
        return ruUserid;
    }

    /**
     * @param Integer ruUserid (abc_red_use.ru_userid )
     */
    public void setRuUserid(Integer ruUserid) {
        this.ruUserid = ruUserid;
    }

    /**
     * @return abc_red_use.ru_usetime
     */
    public Date getRuUsetime() {
        return ruUsetime;
    }

    /**
     * @param Date ruUsetime (abc_red_use.ru_usetime )
     */
    public void setRuUsetime(Date ruUsetime) {
        this.ruUsetime = ruUsetime;
    }

    /**
     * @return abc_red_use.ru_desc
     */
    public String getRuDesc() {
        return ruDesc;
    }

    /**
     * @param String ruDesc (abc_red_use.ru_desc )
     */
    public void setRuDesc(String ruDesc) {
        this.ruDesc = ruDesc;
    }

    /**
     * @return abc_red_use.ru_amount
     */
    public Double getRuAmount() {
        return ruAmount;
    }

    /**
     * @param BigDecimal ruAmount (abc_red_use.ru_amount )
     */
    public void setRuAmount(Double ruAmount) {
        this.ruAmount = ruAmount;
    }

    /**
     * @return abc_red_use.ru_remainder_amount
     */
    public Double getRuRemainderAmount() {
        return ruRemainderAmount;
    }

    /**
     * @param BigDecimal ruRemainderAmount (abc_red_use.ru_remainder_amount )
     */
    public void setRuRemainderAmount(Double ruRemainderAmount) {
        this.ruRemainderAmount = ruRemainderAmount;
    }

    /**
     * @return abc_red_use.ru_deduct_fee
     */
    public Double getRuDeductFee() {
        return ruDeductFee;
    }

    /**
     * @param BigDecimal ruDeductFee (abc_red_use.ru_deduct_fee )
     */
    public void setRuDeductFee(Double ruDeductFee) {
        this.ruDeductFee = ruDeductFee;
    }

    /**
     * @return abc_red_use.ru_deduct_discount
     */
    public Double getRuDeductDiscount() {
        return ruDeductDiscount;
    }

    /**
     * @param BigDecimal ruDeductDiscount (abc_red_use.ru_deduct_discount )
     */
    public void setRuDeductDiscount(Double ruDeductDiscount) {
        this.ruDeductDiscount = ruDeductDiscount;
    }

    /**
     * @return abc_red_use.ru_usecount
     */
    public Integer getRuUsecount() {
        return ruUsecount;
    }

    /**
     * @param Integer ruUsecount (abc_red_use.ru_usecount )
     */
    public void setRuUsecount(Integer ruUsecount) {
        this.ruUsecount = ruUsecount;
    }
}
