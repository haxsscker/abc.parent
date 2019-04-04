/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.OrderState;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投资订单
 *
 * @author segen189 2014年11月22日 上午10:29:54
 */
public class InvestOrder {
    /**
     * 主键id
     */
    private Integer    id;

    /**
     * 贷款id
     */
    private Integer    originId;

    /**
     * 投资人
     */
    private Integer    userId;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 标类型
     */
    private BidType    bidType;

    /**
     * 标外键表id
     */
    private Integer    bidId;

    /**
     * 内部交易流水号
     */
    private String     innerSeqNo;

    /**
     * 订单状态
     */
    private OrderState orderState;
    /**
     * 外部交易流水号 abc_invest_order.io_out_seq_no
     */
    private String     ioOutSeqNo;
    
    /**
     * 投资人冻结编号
     */
    private String    inFreezeId;
    
    public String getIoOutSeqNo() {
		return ioOutSeqNo;
	}

	public void setIoOutSeqNo(String ioOutSeqNo) {
		this.ioOutSeqNo = ioOutSeqNo;
	}

	/**
     * 订单创建日期
     */
    private Date       createtime;

    /**
     * 订单更新日期
     */
    private Date       modifytime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public BidType getBidType() {
        return bidType;
    }

    public void setBidType(BidType bidType) {
        this.bidType = bidType;
    }

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public String getInnerSeqNo() {
        return innerSeqNo;
    }

    public void setInnerSeqNo(String innerSeqNo) {
        this.innerSeqNo = innerSeqNo;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
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

	public String getInFreezeId() {
		return inFreezeId;
	}

	public void setInFreezeId(String inFreezeId) {
		this.inFreezeId = inFreezeId;
	}

}
