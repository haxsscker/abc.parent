package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreConfigDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreConfigDao extends BaseDao<ScoreConfigDO, Integer> {
    /**
     * 根据参数获取记录条数
     *
     * @param scoreConfigDO 查询条件可选
     * @return int
     */
    public int countListByParam(ScoreConfigDO scoreConfigDO);

    /**
     * 按条件查询分页结果
     *
     * @param scoreConfigDO 查询条件，为空的话new一个空对象
     * @param pageCondition 分页和排序条件，可选
     * @return List<ScoreConfigDO>
     */
    List<ScoreConfigDO> findListByParam(@Param("sc") ScoreConfigDO scoreConfigDO,
                                        @Param("pageCondition") PageCondition pageCondition);

    public int updateByPrimaryKey(ScoreConfigDO scoreConfigDO);
    
    /**
     * 根据积分兑换类型ID查询积分兑换信息
     * 
     * @param creditTypeId
     * @return
     */
    public ScoreConfigDO findScoreKindsById(Integer creditTypeId);
}
