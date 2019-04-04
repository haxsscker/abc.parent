/*
 * This software is the confidential and proprietary information ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement.
 */
package com.autoserve.abc.service.biz.intf.invest;

import java.util.List;

import com.autoserve.abc.dao.dataobject.InvestOrderDO;
import com.autoserve.abc.service.biz.entity.InvestOrder;
import com.autoserve.abc.service.biz.enums.BidType;
import com.autoserve.abc.service.biz.enums.OrderState;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 订单管理服务<br>
 * 创建订单的场景：<br>
 * 1) 前台用户对普通项目进行投资时<br>
 * 2) 前台用户对转让项目进行投资时<br>
 * 3) 前台用户对收购项目进行认购时
 *
 * @author segen189 2014年11月19日 下午12:05:04
 */
public interface InvestOrderService {
    /**
     * ［内部］添加订单
     *
     * @param pojo 待添加的订单信息，必选
     * @return BaseResult
     */
    PlainResult<Integer> createInvestOrder(InvestOrder pojo);

    /**
     * ［内部］更新订单状态, 同时更新投资信息
     *
     * @param innerSeqNo 内部交易流水号，必选
     * @param newState 新的订单状态，必选
     * @return BaseResult
     */
    BaseResult modifyInvestOrderState(String innerSeqNo, OrderState newState);

    /**
     * 根据订单id查询某订单
     *
     * @param orderId 订单id，必选
     * @return PlainResult<InvestOrder>
     */
    PlainResult<InvestOrder> queryInvestOrder(int orderId);
    
    /**
     * 根据原始标号查询所有订单信息
     *
     * @param orderId 原始标号，必选
     * @return List
     */
   public List<InvestOrderDO> queryInvestOrderByTransLoanId(int orderId);

    /**
     * 根据条件查询是否存在订单
     *
     * @param bidId 投资订单的标id查询条件，必选
     * @param bidType 投资订单的标类型查询条件，必选
     * @param userId 投资订单的用户id查询条件，必选
     * @param states 投资订单的标的状态集查询条件，必选
     * @return PlainResult<Boolean> 是否存在
     */
    PlainResult<Boolean> queryExistence(int bidId, BidType bidType, int userId, List<OrderState> states);
    
    /**
     * 根据条件查询是否存在订单
     *
     * @param bidId 投资订单的标id查询条件，必选
     * @param bidType 投资订单的标类型查询条件，必选
     * @param userId 投资订单的用户id查询条件，必选
     * @param states 投资订单的标的状态集查询条件，必选
     * @return PlainResult<Boolean> 是否存在
     */
    BaseResult updateBySeqNo(InvestOrderDO order);
    /**
     * 根据转让标号查询所有订单信息
     *
     * @param bidId 转让标号，必选
     * @return List
     */
    public List<InvestOrderDO> queryInvestOrderByBidId(int bidId,int type);
    
    /**
     * 根据内部交易流水号查询所有订单信息
     *
     * @param innerSeqNo 转让标号，必选
     * @return List
     */
    public PlainResult<InvestOrder> queryInvestOrderByInnerSeqNo(String innerSeqNo);
    /**
     * 根据原始标id查询红包投资订单记录
     * @param loanId
     * @return
     */
	public List<InvestOrderDO> findRedOrderByOriginId(Integer loanId);

	int batchUpdateInvestOrderState(Integer loanId, int type, int state,
			int state2);

}
