package com.autoserve.abc.dao.intf;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.dataobject.BuyLoanDO;

public interface BuyLoanDao extends BaseBidDao<BuyLoanDO, Integer> {

    /**
     * 查询收购标，加行级独占锁
     *
     * @param buyLoanId 收购标id
     * @return BuyLoanDO 收购标
     */
    BuyLoanDO findByBuyLoanIdWithLock(int buyLoanId);

    /**
     * 查询收购标
     *
     * @param freezeSeqNo 冻结流水号
     * @return BuyLoanDO 收购标
     */
    BuyLoanDO findByFreezeSeqNo(String freezeSeqNo);

    /**
     * 更新收购标状态
     *
     * @param buyLoanId 收购标id，必选
     * @param oldState 旧状态，必选
     * @param newState 新状态，必选
     * @return int，更新条数
     */
    int updateState(@Param("buyLoanId") int buyLoanId, @Param("oldState") int oldState, @Param("newState") int newState);
}
