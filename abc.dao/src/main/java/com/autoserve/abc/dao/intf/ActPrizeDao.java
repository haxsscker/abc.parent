package com.autoserve.abc.dao.intf;

import java.util.List;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.ActPrizeDO;

public interface ActPrizeDao extends BaseDao<ActPrizeDO, Integer> {
    /**
     * 根据活动ID获取奖项信息  act_id
     * 
     * @return List<ActPrizeDO>
     */
    List<ActPrizeDO> findAllPrize(Integer actId);
    
    /**
     * 统计中奖人数
     * @param apId
     * @return
     */
    int countPrizeUser(Integer apId, Integer actId);
}
