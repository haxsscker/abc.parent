/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.InvestDO;
import com.autoserve.abc.service.biz.entity.Invest;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.InvestState;

/**
 * Invest与InvestDO互相转换
 * 
 * @author segen189 2014年11月21日 下午8:47:13
 */
public class InvestConverter {

    public static InvestDO toInvestDO(Invest invest) {
        if (invest == null) {
            return null;
        }

        InvestDO investDO = new InvestDO();

        investDO.setInId(invest.getId());
        investDO.setInOriginId(invest.getOriginId());
        investDO.setInUserId(invest.getUserId());
        investDO.setInCreatetime(invest.getCreatetime());
        investDO.setInModifytime(invest.getModifytime());
        investDO.setInInvestMoney(invest.getInvestMoney());
        investDO.setInValidInvestMoney(invest.getValidInvestMoney() == null ? BigDecimal.ZERO : invest
                .getValidInvestMoney());
        if (invest.getInvestState() != null) {
            investDO.setInInvestState(invest.getInvestState().getState());
        }
        investDO.setInInnerSeqNo(invest.getInnerSeqNo());
        investDO.setInWithdrawSeqNo(invest.getWithdrawSeqNo());
        if (invest.getBidType() != null) {
            investDO.setInBidType(invest.getBidType().getType());
        }
        investDO.setInBidId(invest.getBidId());
        investDO.setPrizeType(invest.getPrizeType());
        investDO.setInCommContract(invest.getInCommContract());
        investDO.setInTransContract(invest.getInTransContract());
        return investDO;
    }

    public static Invest toInvest(InvestDO investDO) {
        if (investDO == null) {
            return null;
        }

        Invest invest = new Invest();

        invest.setId(investDO.getInId());
        invest.setOriginId(investDO.getInOriginId());
        invest.setUserId(investDO.getInUserId());
        invest.setCreatetime(investDO.getInCreatetime());
        invest.setModifytime(investDO.getInModifytime());
        invest.setInvestMoney(investDO.getInInvestMoney());
        invest.setValidInvestMoney(investDO.getInValidInvestMoney() == null ? BigDecimal.ZERO : investDO
                .getInValidInvestMoney());
        if (investDO.getInInvestState() != null) {
            invest.setInvestState(InvestState.valueOf(investDO.getInInvestState()));
        }
        invest.setInnerSeqNo(investDO.getInInnerSeqNo());
        invest.setWithdrawSeqNo(investDO.getInWithdrawSeqNo());
        if (investDO.getInBidType() != null) {
            invest.setBidType(BidType.valueOf(investDO.getInBidType()));
        }
        invest.setBidId(investDO.getInBidId());
        invest.setPrizeType(investDO.getPrizeType());
        invest.setInAccountNo(investDO.getInAccountNo());
        invest.setInFreezeId(investDO.getInFreezeId());
        invest.setInCommContract(investDO.getInCommContract());
        invest.setInTransContract(investDO.getInTransContract());
        invest.setCommDepositNumber(investDO.getCommDepositNumber());
        invest.setTransDepositNumber(investDO.getTransDepositNumber());
        return invest;
    }

    public static List<Invest> toInvestList(List<InvestDO> investDOList) {
        if (investDOList == null) {
            return null;
        }

        List<Invest> investList = new ArrayList<Invest>(investDOList.size());
        for (InvestDO invesDO : investDOList) {
            investList.add(toInvest(invesDO));
        }

        return investList;
    }

}
