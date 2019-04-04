package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author RJQ 2014/11/22 10:40.
 */
public interface ScoreDao extends BaseDao<ScoreDO, Integer> {
    /**
     * 根据参数获取记录条数
     *
     * @param scoreDO 查询条件可选
     * @return int
     */
    public int countListByParam(ScoreDO scoreDO);

    /**
     * 按条件查询分页结果
     *
     * @param scoreDO       查询条件，为空的话new一个空对象
     * @param pageCondition 分页和排序条件，可选
     * @return List<ScoreDO>
     */
    List<ScoreDO> findListByParam(@Param("score") ScoreDO scoreDO,
                                  @Param("pageCondition") PageCondition pageCondition);

    /**
     * 更新积分类型信息
     * @param scoreDO 更新条件，可选
     * @return int
     */
    public int updateByPrimaryKeySelective(ScoreDO scoreDO);

    /**
     * 查询积分类型
     * @param code 积分代码（不允许重复）
     * @return ScoreDO
     */
    public ScoreDO findByScoreCode(String code);
}
