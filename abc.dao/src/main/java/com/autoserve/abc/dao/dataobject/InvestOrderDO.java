package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * InvestOrderDO abc_invest_order
 */
public class InvestOrderDO {
    /**
     * 主键id abc_invest_order.io_id
     */
    private Integer    ioId;

    /**
     * 原始贷款id abc_invest_order.io_origin_id
     */
    private Integer    ioOriginId;

    /**
     * 投资人 abc_invest_order.io_user_id
     */
    private Integer    ioUserId;

    /**
     * 订单金额 abc_invest_order.io_order_money
     */
    private BigDecimal ioOrderMoney;

    /**
     * 标类型 1：正常标 2：转让标 3：收购标 abc_invest_order.io_bid_type
     */
    private Integer    ioBidType;

    /**
     * 标类型外键表id abc_invest_order.io_bid_id
     */
    private Integer    ioBidId;

    /**
     * 内部交易流水号 abc_invest_order.io_inner_seq_no
     */
    private String     ioInnerSeqNo;
    /**
     * 内部交易流水号 abc_invest_order.io_out_seq_no
     */
    private String     ioOutSeqNo;

    /**
     * 订单状态 -1：已删除 0：未支付 1:支付失败 2:支付成功 abc_invest_order.io_order_state
     */
    private Integer    ioOrderState;

    /**
     * 订单创建日期 abc_invest_order.io_createtime
     */
    private Date       ioCreatetime;

    /**
     * 订单更新日期 abc_invest_order.io_modifytime
     */
    private Date       ioModifytime;
    /**
     * 投资人开户账号
     */
    private String    inAccountNo;
    /**
     * 冻结编号 abc_invest_order.io_freeze_id
     */
    private String     ioFreezeId;

    public Integer getIoId() {
        return ioId;
    }

    public void setIoId(Integer ioId) {
        this.ioId = ioId;
    }

    public Integer getIoOriginId() {
        return ioOriginId;
    }

    public void setIoOriginId(Integer ioOriginId) {
        this.ioOriginId = ioOriginId;
    }

    public Integer getIoUserId() {
        return ioUserId;
    }

    public void setIoUserId(Integer ioUserId) {
        this.ioUserId = ioUserId;
    }

    public BigDecimal getIoOrderMoney() {
        return ioOrderMoney;
    }

    public void setIoOrderMoney(BigDecimal ioOrderMoney) {
        this.ioOrderMoney = ioOrderMoney;
    }

    public Integer getIoBidType() {
        return ioBidType;
    }

    public void setIoBidType(Integer ioBidType) {
        this.ioBidType = ioBidType;
    }

    public Integer getIoBidId() {
        return ioBidId;
    }

    public void setIoBidId(Integer ioBidId) {
        this.ioBidId = ioBidId;
    }

    public String getIoInnerSeqNo() {
        return ioInnerSeqNo;
    }

    public void setIoInnerSeqNo(String ioInnerSeqNo) {
        this.ioInnerSeqNo = ioInnerSeqNo;
    }

    public Integer getIoOrderState() {
        return ioOrderState;
    }

    public void setIoOrderState(Integer ioOrderState) {
        this.ioOrderState = ioOrderState;
    }

    public Date getIoCreatetime() {
        return ioCreatetime;
    }

    public void setIoCreatetime(Date ioCreatetime) {
        this.ioCreatetime = ioCreatetime;
    }

    public Date getIoModifytime() {
        return ioModifytime;
    }

    public void setIoModifytime(Date ioModifytime) {
        this.ioModifytime = ioModifytime;
    }

	public String getIoOutSeqNo() {
		return ioOutSeqNo;
	}

	public void setIoOutSeqNo(String ioOutSeqNo) {
		this.ioOutSeqNo = ioOutSeqNo;
	}

	public String getInAccountNo() {
		return inAccountNo;
	}

	public void setInAccountNo(String inAccountNo) {
		this.inAccountNo = inAccountNo;
	}

	public String getIoFreezeId() {
		return ioFreezeId;
	}

	public void setIoFreezeId(String ioFreezeId) {
		this.ioFreezeId = ioFreezeId;
	}

    
}
