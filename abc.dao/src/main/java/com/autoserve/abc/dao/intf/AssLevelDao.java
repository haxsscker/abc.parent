package com.autoserve.abc.dao.intf;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.AssessLevelDO;

public interface AssLevelDao extends BaseDao<AssessLevelDO, Integer> {
    /**
     * 更新用户评估等级
     */
    void updateUserAssess(@Param("assScore") int assScore, @Param("userId") int userId);
    
    /**
     * 更新用户tsign
     */
    void updateUserTsignId(@Param("tsignAccountId") String tsignAccountId, @Param("userId") int userId);
    
    /**
     * 投资是否有效
     * @param investedMoney
     * @param userId
     * @return
     */
    int isValidInvest(@Param("investedMoney") BigDecimal investedMoney, @Param("userId") int userId, @Param("assId") int assId);

}
