/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.convert;

import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.service.biz.entity.InvestOrder;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.OrderState;

import java.util.ArrayList;
import java.util.List;

/**
 * InvestOrder与InvestOrderDO互相转换
 *
 * @author segen189 2014年11月22日 下午1:35:08
 */
public class InvestOrderConverter {
    public static InvestOrderDO toInvestOrderDO(InvestOrder investOrder) {
        if (investOrder == null) {
            return null;
        }

        InvestOrderDO investOrderDO = new InvestOrderDO();

        investOrderDO.setIoId(investOrder.getId());
        investOrderDO.setIoOriginId(investOrder.getOriginId());
        investOrderDO.setIoUserId(investOrder.getUserId());
        investOrderDO.setIoOrderMoney(investOrder.getOrderMoney());
        investOrderDO.setIoBidType(investOrder.getBidType().getType());
        investOrderDO.setIoBidId(investOrder.getBidId());
        investOrderDO.setIoInnerSeqNo(investOrder.getInnerSeqNo());
        investOrderDO.setIoOrderState(investOrder.getOrderState().getState());
        investOrderDO.setIoCreatetime(investOrder.getCreatetime());
        investOrderDO.setIoModifytime(investOrder.getModifytime());
        investOrderDO.setIoOutSeqNo(investOrder.getIoOutSeqNo());
        return investOrderDO;
    }

    public static InvestOrder toInvestOrder(InvestOrderDO investOrderDO) {
        if (investOrderDO == null) {
            return null;
        }

        InvestOrder investOrder = new InvestOrder();

        investOrder.setId(investOrderDO.getIoId());
        investOrder.setOriginId(investOrderDO.getIoOriginId());
        investOrder.setUserId(investOrderDO.getIoUserId());
        investOrder.setOrderMoney(investOrderDO.getIoOrderMoney());
        investOrder.setBidType(BidType.valueOf(investOrderDO.getIoBidType()));
        investOrder.setBidId(investOrderDO.getIoBidId());
        investOrder.setInnerSeqNo(investOrderDO.getIoInnerSeqNo());
        investOrder.setOrderState(OrderState.valueOf(investOrderDO.getIoOrderState()));
        investOrder.setCreatetime(investOrderDO.getIoCreatetime());
        investOrder.setModifytime(investOrderDO.getIoModifytime());
        investOrder.setIoOutSeqNo(investOrderDO.getIoOutSeqNo());

        return investOrder;
    }

    public static List<InvestOrder> toInvestOrderList(List<InvestOrderDO> investOrderDOList) {
        if (investOrderDOList == null) {
            return null;
        }

        List<InvestOrder> investOrderList = new ArrayList<InvestOrder>(investOrderDOList.size());
        for (InvestOrderDO invesOrderDO : investOrderDOList) {
            investOrderList.add(toInvestOrder(invesOrderDO));
        }

        return investOrderList;
    }
}
