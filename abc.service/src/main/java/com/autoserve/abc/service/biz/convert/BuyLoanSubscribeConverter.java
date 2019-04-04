/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.BuyLoanSubscribeDO;
import com.autoserve.abc.service.biz.entity.BuyLoanSubscribe;
import com.autoserve.abc.service.biz.enums.BuyLoanSubscribeState;

/**
 * BuyLoanSubscribe与BuyLoanSubscribeDO互相转换
 *
 * @author segen189 2014年12月15日 下午4:41:16
 */
public class BuyLoanSubscribeConverter {

    public static BuyLoanSubscribeDO toBuyLoanSubscribeDO(BuyLoanSubscribe buyLoanSubscribe) {
        if (buyLoanSubscribe == null) {
            return null;
        }

        BuyLoanSubscribeDO buyLoanSubscribeDO = new BuyLoanSubscribeDO();

        buyLoanSubscribeDO.setBlsId(buyLoanSubscribe.getId());
        buyLoanSubscribeDO.setBlsBuyId(buyLoanSubscribe.getBuyId());
        buyLoanSubscribeDO.setBlsLoanId(buyLoanSubscribe.getLoanId());
        buyLoanSubscribeDO.setBlsUserId(buyLoanSubscribe.getUserId());
        buyLoanSubscribeDO.setBlsTransferTime(buyLoanSubscribe.getTransferTime());
        buyLoanSubscribeDO.setBlsTransferMoney(buyLoanSubscribe.getTransferMoney());
        buyLoanSubscribeDO.setBlsEarnMoney(buyLoanSubscribe.getEarnMoney());
        buyLoanSubscribeDO.setBlsState(buyLoanSubscribe.getState().getState());

        return buyLoanSubscribeDO;
    }

    public static BuyLoanSubscribe toBuyLoanSubscribe(BuyLoanSubscribeDO buyLoanSubscribeDO) {
        if (buyLoanSubscribeDO == null) {
            return null;
        }

        BuyLoanSubscribe buyLoanSubscribe = new BuyLoanSubscribe();

        buyLoanSubscribe.setId(buyLoanSubscribeDO.getBlsId());
        buyLoanSubscribe.setBuyId(buyLoanSubscribeDO.getBlsBuyId());
        buyLoanSubscribe.setLoanId(buyLoanSubscribeDO.getBlsLoanId());
        buyLoanSubscribe.setUserId(buyLoanSubscribeDO.getBlsUserId());
        buyLoanSubscribe.setTransferTime(buyLoanSubscribeDO.getBlsTransferTime());
        buyLoanSubscribe.setTransferMoney(buyLoanSubscribeDO.getBlsTransferMoney());
        buyLoanSubscribe.setEarnMoney(buyLoanSubscribeDO.getBlsEarnMoney());
        buyLoanSubscribe.setState(BuyLoanSubscribeState.valueOf(buyLoanSubscribeDO.getBlsState()));

        return buyLoanSubscribe;
    }

    public static List<BuyLoanSubscribe> toBuyLoanList(List<BuyLoanSubscribeDO> buyLoanSubscribeDOList) {
        if (buyLoanSubscribeDOList == null) {
            return null;
        }

        List<BuyLoanSubscribe> buyLoanSubscribeList = new ArrayList<BuyLoanSubscribe>(buyLoanSubscribeDOList.size());
        for (BuyLoanSubscribeDO buyLoanSubscribeDO : buyLoanSubscribeDOList) {
            buyLoanSubscribeList.add(toBuyLoanSubscribe(buyLoanSubscribeDO));
        }

        return buyLoanSubscribeList;
    }

}
