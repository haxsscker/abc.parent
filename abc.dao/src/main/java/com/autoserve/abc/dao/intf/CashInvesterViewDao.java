package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashInvesterViewDO;

public interface CashInvesterViewDao extends BaseDao<CashInvesterViewDO, Integer> {

    /**
     * 获取投资人总数
     * 
     * @return
     */
    public int countInvesterNum(@Param("investorName") String investorName,
                                @Param("investorRealName") String investorRealName);

    /**
     * 获取投资人id
     * 
     * @return
     */
    public List<CashInvesterViewDO> findInvesters(@Param("investorName") String investorName,
                                                  @Param("investorRealName") String investorRealName,
                                                  @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询投资人投资收益信息
     * 
     * @param pageCondition
     * @return
     */
    public List<CashInvesterViewDO> findInvesterIncome(List<Integer> userIds);

    /**
     * 查询投资人收购信息
     * 
     * @param userIds
     * @return
     */
    public List<CashInvesterViewDO> findInvesterPurchase(List<Integer> userIds);

    /**
     * 查询投资人转让信息
     * 
     * @param userIds
     * @return
     */
    public List<CashInvesterViewDO> findInvesterTransfer(List<Integer> userIds);

    /**
     * 查询投资人投资信息
     * 
     * @param userIds
     * @return
     */
    public List<CashInvesterViewDO> findInvesterInvester(List<Integer> userIds);

    /**
     * 查询投资人被收购的转让信息
     * 
     * @param userIds
     * @return
     */
    public List<CashInvesterViewDO> findInvesterTransferByPurchase(List<Integer> userIds);

    /**
     * 根据userId查询资金对账表
     * @param userId
     * @return
     */
	public CashInvesterViewDO findInvestersByUserId(@Param("userId")int userId);

}
