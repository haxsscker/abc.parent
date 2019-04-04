package com.autoserve.abc.dao.intf;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.InvestOrderDO;

public interface InvestOrderDao extends BaseDao<InvestOrderDO, Integer> {

    /**
     * 根据内部交易流水号更新订单
     */
    int updateByInnerSeqNo(InvestOrderDO toModify);

    /**
     * 查询单条订单
     */
    InvestOrderDO findByParam(InvestOrderDO order);
    /**
     * 根绝原始贷款标号查询所有投资的纪录
     */
    public List<InvestOrderDO> findByOriginId(int order);
    
    
    /**
     * 根绝原始转让标号查询所有投资的纪录
     */
    public List<InvestOrderDO> findByBidId(@Param("ioBidId")int bidId,@Param("ioBidType") int type);
    
    

    /**
     * 根据条件查询是否存在订单
     */
    int findExistence(@Param("bidId") int bidId, @Param("bidType") int bidType, @Param("userId") int userId,
                      @Param("intStates") List<Integer> intStates);
    
    
    
    /**
     * 根绝原始转让标号查询所有投资的纪录
     */
    public InvestOrderDO findByInnerSeqNo(String innerSeqNo);
    /**
     * 根据外部流水号查询这次投资使用的红包金额
     * @return
     */
    BigDecimal findRedAmountByOutSeqNo(String outSeqNo);

	List<InvestOrderDO> findRedOrderByOriginId(Integer loanId);

	int batchUpdateInvestOrderState(@Param("bidId") int bidId, @Param("bidType") int bidType,
                               @Param("oldState") int oldState, @Param("newState") int newState);

	int batchUpdateInvestOrderStateByList(@Param("updateInvestOrderDoIdList")List<Integer> updateInvestOrderDoIdList, 
			@Param("newState") int newState);
}
