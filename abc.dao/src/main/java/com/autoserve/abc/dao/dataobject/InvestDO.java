package com.autoserve.abc.dao.dataobject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * InvestDO abc_invest
 */
public class InvestDO {
    /**
     * 主键id abc_invest.in_id
     */
    private Integer    inId;

    /**
     * 原始贷款id abc_invest.in_origin_id
     */
    private Integer    inOriginId;

    /**
     * 投资人 abc_invest.in_user_id
     */
    private Integer    inUserId;

    /**
     * 投资日期 abc_invest.in_createtime
     */
    private Date       inCreatetime;

    /**
     * 更新日期 abc_invest.in_modifytime
     */
    private Date       inModifytime;

    /**
     * 投资金额 abc_invest.in_invest_money
     */
    private BigDecimal inInvestMoney;

    /**
     * 投资金额 abc_invest.in_valid_invest_money
     */
    private BigDecimal inValidInvestMoney;

    /**
     * 投资状态 -1：已删除 0：未支付 1:支付失败 2:支付成功 3:已撤资 4:待收益 5:被转让 6:被收购 7:收益完成
     * abc_invest.in_invest_state
     */
    private Integer    inInvestState;

    /**
     * 内部交易流水号 abc_invest.in_inner_seq_no
     */
    private String     inInnerSeqNo;

    /**
     * 撤投交易流水号 abc_invest.in_withdraw_seq_no
     */
    private String     inWithdrawSeqNo;

    /**
     * 标类型 1：正常标 2：转让标 3：收购标 abc_invest.in_bid_type
     */
    private Integer    inBidType;

    /**
     * 标类型外键表id abc_invest.in_bid_id
     */
    private Integer    inBidId;
    
    /**
     * 奖励类型
     */
    private Integer		prizeType;
    /**
     * 投资人开户账号
     */
    private String    inAccountNo;
    /**
     * 投资人冻结编号
     */
    private String    inFreezeId;
    
    /**
     * 外部交易流水号 abc_invest_order.io_out_seq_no
     */
    private String  ioOutSeqNo;
    
    /**
     * 普通标投资借款合同地址
     */
    private String  inCommContract;
    
    /**
     * 转让标标投资借款合同地址
     */
    private String  inTransContract;
    /**
     * 普通标投资借款合同存证编号
     */
    private String  commDepositNumber;
    
    /**
     * 转让标投资借款合同存证编号
     */
    private String  TransDepositNumber;

    public Integer getInId() {
        return inId;
    }

    public void setInId(Integer inId) {
        this.inId = inId;
    }

    public Integer getInOriginId() {
        return inOriginId;
    }

    public void setInOriginId(Integer inOriginId) {
        this.inOriginId = inOriginId;
    }

    public Integer getInUserId() {
        return inUserId;
    }

    public void setInUserId(Integer inUserId) {
        this.inUserId = inUserId;
    }

    public Date getInCreatetime() {
        return inCreatetime;
    }

    public void setInCreatetime(Date inCreatetime) {
        this.inCreatetime = inCreatetime;
    }

    public Date getInModifytime() {
        return inModifytime;
    }

    public void setInModifytime(Date inModifytime) {
        this.inModifytime = inModifytime;
    }

    public BigDecimal getInInvestMoney() {
        return inInvestMoney;
    }

    public void setInInvestMoney(BigDecimal inInvestMoney) {
        this.inInvestMoney = inInvestMoney;
    }

    public BigDecimal getInValidInvestMoney() {
        return inValidInvestMoney;
    }

    public void setInValidInvestMoney(BigDecimal inValidInvestMoney) {
        this.inValidInvestMoney = inValidInvestMoney;
    }

    public Integer getInInvestState() {
        return inInvestState;
    }

    public void setInInvestState(Integer inInvestState) {
        this.inInvestState = inInvestState;
    }

    public String getInInnerSeqNo() {
        return inInnerSeqNo;
    }

    public void setInInnerSeqNo(String inInnerSeqNo) {
        this.inInnerSeqNo = inInnerSeqNo;
    }

    public String getInWithdrawSeqNo() {
        return inWithdrawSeqNo;
    }

    public void setInWithdrawSeqNo(String inWithdrawSeqNo) {
        this.inWithdrawSeqNo = inWithdrawSeqNo;
    }

    public Integer getInBidType() {
        return inBidType;
    }

    public void setInBidType(Integer inBidType) {
        this.inBidType = inBidType;
    }

    public Integer getInBidId() {
        return inBidId;
    }

    public void setInBidId(Integer inBidId) {
        this.inBidId = inBidId;
    }

	public Integer getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(Integer prizeType) {
		this.prizeType = prizeType;
	}

	public String getInAccountNo() {
		return inAccountNo;
	}

	public void setInAccountNo(String inAccountNo) {
		this.inAccountNo = inAccountNo;
	}

	public String getInFreezeId() {
		return inFreezeId;
	}

	public void setInFreezeId(String inFreezeId) {
		this.inFreezeId = inFreezeId;
	}

	public String getIoOutSeqNo() {
		return ioOutSeqNo;
	}

	public void setIoOutSeqNo(String ioOutSeqNo) {
		this.ioOutSeqNo = ioOutSeqNo;
	}

	public String getInCommContract() {
		return inCommContract;
	}

	public void setInCommContract(String inCommContract) {
		this.inCommContract = inCommContract;
	}

	public String getInTransContract() {
		return inTransContract;
	}

	public void setInTransContract(String inTransContract) {
		this.inTransContract = inTransContract;
	}

	public String getCommDepositNumber() {
		return commDepositNumber;
	}

	public void setCommDepositNumber(String commDepositNumber) {
		this.commDepositNumber = commDepositNumber;
	}

	public String getTransDepositNumber() {
		return TransDepositNumber;
	}

	public void setTransDepositNumber(String transDepositNumber) {
		TransDepositNumber = transDepositNumber;
	}
	
}
