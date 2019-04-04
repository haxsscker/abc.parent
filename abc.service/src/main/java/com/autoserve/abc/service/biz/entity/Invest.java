/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;

/**
 * 投资活动
 *
 * @author segen189 2014年11月21日 下午8:40:16
 */
public class Invest {
    /**
     * 主键id
     */
    private Integer     id;

    /**
     * 投资人
     */
    private Integer     userId;

    /**
     * 投资日期
     */
    private Date        createtime;

    /**
     * 更新日期
     */
    private Date        modifytime;

    /**
     * 投资金额
     */
    private BigDecimal  investMoney;

    /**
     * 有效投资金额
     */
    private BigDecimal  validInvestMoney;

    /**
     * 投资应支付金额
     */
    private BigDecimal  investPayMoney;

    /**
     * 投资状态
     */
    private InvestState investState;

    /**
     * 内部交易流水号
     */
    private String      innerSeqNo;

    /**
     * 撤投交易流水号
     */
    private String      withdrawSeqNo;

    /**
     * 原始贷款id
     */
    private Integer     originId;

    /**
     * 标类型
     */
    private BidType     bidType;

    /**
     * 标外键表id
     */
    private Integer     bidId;
    
    /**
     * 
     */
    private String investCode;
    
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
    private String     ioOutSeqNo;
    
    private String  verification;
    
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

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public BigDecimal getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(BigDecimal investMoney) {
        this.investMoney = investMoney;
    }

    public BigDecimal getInvestPayMoney() {
        return investPayMoney;
    }

    public void setInvestPayMoney(BigDecimal investPayMoney) {
        this.investPayMoney = investPayMoney;
    }

    public BigDecimal getValidInvestMoney() {
        return validInvestMoney;
    }

    public void setValidInvestMoney(BigDecimal validInvestMoney) {
        this.validInvestMoney = validInvestMoney;
    }

    public InvestState getInvestState() {
        return investState;
    }

    public void setInvestState(InvestState investState) {
        this.investState = investState;
    }

    public String getInnerSeqNo() {
        return innerSeqNo;
    }

    public void setInnerSeqNo(String innerSeqNo) {
        this.innerSeqNo = innerSeqNo;
    }

    public String getWithdrawSeqNo() {
        return withdrawSeqNo;
    }

    public void setWithdrawSeqNo(String withdrawSeqNo) {
        this.withdrawSeqNo = withdrawSeqNo;
    }

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
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

	public String getInvestCode() {
		return investCode;
	}

	public void setInvestCode(String investCode) {
		this.investCode = investCode;
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

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
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
