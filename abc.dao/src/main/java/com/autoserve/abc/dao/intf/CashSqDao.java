package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.CashSqDO;

public interface CashSqDao extends BaseDao<CashSqDO, Integer> {
                                       
    /**
     * 根据推荐人取总数
     * 
     * @param userRecommendDO
     * @return
     */
    int countRecommendListByParam(@Param("cashSqDO") CashSqDO cashSqDO);

    /**
     * 按条件查询分页结果
     * 
     * @param userDO 查询条件，可选
     * @param pageCondition 分页和排序条件，可选
     * @return List<UserRecommendDO>
     */
    List<CashSqDO> findRecommendListByParam(@Param("cashSqDO") CashSqDO cashSqDO,
                                                   @Param("pageCondition") PageCondition pageCondition);

    public int updateByPrimaryKeySelective(CashSqDO cashSqDO);
}
