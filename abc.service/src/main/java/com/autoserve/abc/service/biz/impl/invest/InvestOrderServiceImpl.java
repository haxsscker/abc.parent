/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.impl.invest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.dao.intf.InvestOrderDao;
import com.autoserve.abc.service.biz.convert.InvestOrderConverter;
import com.autoserve.abc.service.biz.entity.InvestOrder;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.OrderState;
import com.autoserve.abc.service.biz.intf.invest.InvestOrderService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 投资订单服务<br>
 *
 * @author segen189 2014年11月22日 上午10:28:16
 */
@Service
public class InvestOrderServiceImpl implements InvestOrderService {

    @Resource
    private InvestOrderDao investOrderDao;

    @Override
    public PlainResult<Integer> createInvestOrder(InvestOrder pojo) {
        PlainResult<Integer> result = new PlainResult<Integer>();
        if (pojo == null) {
            result.setError(CommonResultCode.ILLEGAL_PARAM_BLANK, "InvestOrder");
            return result;
        }

        InvestOrderDO investOrderDO = InvestOrderConverter.toInvestOrderDO(pojo);
        investOrderDO.setIoOrderState(OrderState.UNPAID.getState());
        investOrderDao.insert(investOrderDO);

        result.setData(investOrderDO.getIoId());
        return result;
    }

    @Override
    public BaseResult modifyInvestOrderState(String innerSeqNo, OrderState newState) {
        BaseResult result = new BaseResult();
        if (newState == null) {
            result.setError(CommonResultCode.ILLEGAL_PARAM_BLANK, "OrderState");
            return result;
        }

        InvestOrderDO toModify = new InvestOrderDO();
        toModify.setIoInnerSeqNo(innerSeqNo);
        toModify.setIoOrderState(newState.getState());
        investOrderDao.updateByInnerSeqNo(toModify);

        return result;
    }

    @Override
    public PlainResult<InvestOrder> queryInvestOrder(int orderId) {
        PlainResult<InvestOrder> result = new PlainResult<InvestOrder>();
        result.setData(InvestOrderConverter.toInvestOrder(investOrderDao.findById(orderId)));
        return result;
    }

    @Override
    public PlainResult<Boolean> queryExistence(int bidId, BidType bidType, int userId, List<OrderState> states) {
        PlainResult<Boolean> result = new PlainResult<Boolean>();

        List<Integer> intStates = new ArrayList<Integer>(states.size());
        for (OrderState state : states) {
            intStates.add(state.getState());
        }
        int existence = investOrderDao.findExistence(bidId, bidType.getType(), userId, intStates);
        if (existence <= 0) {
            result.setData(false);
        } else {
            result.setData(true);
        }
        return result;
    }

	@Override
	public List<InvestOrderDO> queryInvestOrderByTransLoanId(int orderId) {
		InvestOrderDO dao =new InvestOrderDO();
		dao.setIoOriginId(orderId);
		List<InvestOrderDO> dsa = investOrderDao.findByOriginId(orderId);
		return dsa;
	}

	
	@Override
	public List<InvestOrderDO> queryInvestOrderByBidId(int bidId,int type) {
		List<InvestOrderDO> dsa = investOrderDao.findByBidId(bidId,type);
		return dsa;
	}
	@Override
	public BaseResult updateBySeqNo(InvestOrderDO order) {
		int i = investOrderDao.updateByInnerSeqNo(order);
		BaseResult result = new BaseResult();
		if(i<=0){
			result.setSuccess(false);
		}else{
			result.setSuccess(true);
		}
		return result;
	}
	
	
	@Override
	public PlainResult<InvestOrder> queryInvestOrderByInnerSeqNo(String innerSeqNo) {
		PlainResult<InvestOrder> result = new PlainResult<InvestOrder>();
		InvestOrderDO investOrderDo = investOrderDao.findByInnerSeqNo(innerSeqNo);
		InvestOrder investOrder = InvestOrderConverter.toInvestOrder(investOrderDo);
		result.setData(investOrder);
//        result.setData(InvestOrderConverter.toInvestOrder(investOrderDao.findByInnerSeqNo(innerSeqNo)));
        return result;
	}

	@Override
	public List<InvestOrderDO> findRedOrderByOriginId(Integer loanId) {
		return investOrderDao.findRedOrderByOriginId(loanId);
	}

	@Override
	public int batchUpdateInvestOrderState(Integer loanId, int type, int state,
			int state2) {
		return investOrderDao.batchUpdateInvestOrderState(loanId,type,state,state2);
	}
	
}
