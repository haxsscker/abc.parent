/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.BuyLoanDO;
import com.autoserve.abc.service.biz.entity.BuyLoan;
import com.autoserve.abc.service.biz.enums.BuyLoanState;

/**
 * BuyLoan与BuyLoanDO互相转换
 *
 * @author segen189 2014年11月21日 下午8:47:13
 */
public class BuyLoanConverter {

    public static BuyLoanDO toBuyLoanDO(BuyLoan buyLoan) {
        if (buyLoan == null) {
            return null;
        }

        BuyLoanDO buyLoanDO = new BuyLoanDO();

        buyLoanDO.setBlId(buyLoan.getId());
        buyLoanDO.setBlOriginId(buyLoan.getOriginId());
        buyLoanDO.setBlUserId(buyLoan.getUserId());
        buyLoanDO.setBlBuyMoney(buyLoan.getBuyMoney());
        buyLoanDO.setBlBuyTotal(buyLoan.getBuyTotal());
        buyLoanDO.setBlBuyPeriod(buyLoan.getBuyPeriod());
        buyLoanDO.setBlFee(buyLoan.getFee());
        if (buyLoan.getBuyLoanState() != null) {
            buyLoanDO.setBlState(buyLoan.getBuyLoanState().getState());
        }
        buyLoanDO.setBlCreatetime(buyLoan.getCreatetime());
        buyLoanDO.setBlStartTime(buyLoan.getStartTime());
        buyLoanDO.setBlEndTime(buyLoan.getEndTime());
        buyLoanDO.setBlFullTime(buyLoan.getFullTime());
        buyLoanDO.setBlFullTransferedtime(buyLoan.getFullTransferedtime());
        buyLoanDO.setBlIsTransfer(BuyLoanState.MONEY_TRANSFERED.equals(buyLoan.getBuyLoanState()) ? 1 : 0);
        buyLoanDO.setBlCurrentInvest(buyLoan.getCurrentInvest());
        buyLoanDO.setBlCurrentValidInvest(buyLoan.getCurrentValidInvest());
        buyLoanDO.setBlNextPaymentId(buyLoan.getNextPaymentId());
        buyLoanDO.setBlFreezeSeqNo(buyLoan.getFreezeSeqNo());

        return buyLoanDO;
    }

    public static BuyLoan toBuyLoan(BuyLoanDO buyLoanDO) {
        if (buyLoanDO == null) {
            return null;
        }

        BuyLoan buyLoan = new BuyLoan();

        buyLoan.setId(buyLoanDO.getBlId());
        buyLoan.setOriginId(buyLoanDO.getBlOriginId());
        buyLoan.setUserId(buyLoanDO.getBlUserId());
        buyLoan.setBuyMoney(buyLoanDO.getBlBuyMoney());
        buyLoan.setBuyTotal(buyLoanDO.getBlBuyTotal());
        buyLoan.setBuyPeriod(buyLoanDO.getBlBuyPeriod());
        buyLoan.setFee(buyLoanDO.getBlFee());
        buyLoan.setBuyLoanState(BuyLoanState.valueOf(buyLoanDO.getBlState()));
        buyLoan.setCreatetime(buyLoanDO.getBlCreatetime());
        buyLoan.setStartTime(buyLoanDO.getBlStartTime());
        buyLoan.setEndTime(buyLoanDO.getBlEndTime());
        buyLoan.setFullTime(buyLoanDO.getBlFullTime());
        buyLoan.setFullTransferedtime(buyLoanDO.getBlFullTransferedtime());
        buyLoan.setCurrentInvest(buyLoanDO.getBlCurrentInvest());
        buyLoan.setCurrentValidInvest(buyLoanDO.getBlCurrentValidInvest());
        buyLoan.setNextPaymentId(buyLoanDO.getBlNextPaymentId());
        buyLoan.setFreezeSeqNo(buyLoanDO.getBlFreezeSeqNo());

        return buyLoan;
    }

    public static List<BuyLoan> toBuyLoanList(List<BuyLoanDO> buyLoanDOList) {
        if (buyLoanDOList == null) {
            return null;
        }

        List<BuyLoan> buyLoanList = new ArrayList<BuyLoan>(buyLoanDOList.size());
        for (BuyLoanDO buyLoanDO : buyLoanDOList) {
            buyLoanList.add(toBuyLoan(buyLoanDO));
        }

        return buyLoanList;
    }

}
