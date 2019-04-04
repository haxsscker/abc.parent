package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.BuyLoanSubscribeDO;

public interface BuyLoanSubscribeDao extends BaseDao<BuyLoanSubscribeDO, Integer> {

    int updateState(@Param("buyLoanId") int buyLoanId, @Param("userId") Integer userId,
                    @Param("oldState") int oldState, @Param("newState") int newState);

    List<BuyLoanSubscribeDO> findByBuyId(int buyLoanId);

    BuyLoanSubscribeDO findOneByParam(@Param("userId") int userId, @Param("loanId") int loanId,
                                      @Param("subscribeStates") List<Integer> subscribeStates);

    BuyLoanSubscribeDO findOneWithLock(@Param("buyId") Integer buyId, @Param("loanId") int loanId,
                                       @Param("userId") int userId, @Param("state") int state);

    int updateByBuyLoanIdAndUserId(BuyLoanSubscribeDO buyLoanSubscribeDO);

}
