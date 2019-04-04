package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreHistoryDO;
import com.autoserve.abc.dao.dataobject.ScoreHistoryWithValueDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author RJQ 2014/11/22 10:41.
 */
public interface ScoreHistoryDao extends BaseDao<ScoreHistoryDO, Integer> {
    /**
     * 根据参数获取记录条数
     *
     * @param scoreHistoryDO 查询条件可选
     * @return int
     */
    public int countListByParam(ScoreHistoryDO scoreHistoryDO);

    /**
     * 按条件查询分页结果
     *
     * @param scoreHistoryDO 查询条件，为空的话new一个空对象
     * @param pageCondition  分页和排序条件，可选
     * @return List<ScoreHistoryWithValueDO>
     */
    List<ScoreHistoryWithValueDO> findListByParam(@Param("sh") ScoreHistoryDO scoreHistoryDO,
                                         @Param("pageCondition") PageCondition pageCondition);
    
    /**
     * 查询用户获取的总积分
     * 
     * @param userid
     * @return
     */
    public int findCountScoreByUserId(int userid);

    /**
     * 查询用户积分使用和获得记录，带分页
     * 
     * @param scoreHistoryDO
     * @param pageCondition
     * @return
     */
    List<ScoreHistoryWithValueDO> findScoreListByUserId(@Param("sh") ScoreHistoryDO scoreHistoryDO,
                                                        @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询用户积分使用和获得记录总数
     * 
     * @param scoreHistoryDO
     * @return
     */
    int countScoreListByUserId(@Param("sh") ScoreHistoryDO scoreHistoryDO);
}
