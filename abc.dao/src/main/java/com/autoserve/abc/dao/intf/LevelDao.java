package com.autoserve.abc.dao.intf;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LevelDO;

public interface LevelDao extends BaseDao<LevelDO, Integer> {

    /**
     * 根据积分值找对应等级
     *
     * @param score 积分值
     * @return LevelDO
     */
    public LevelDO findByScore(BigDecimal score);

    /**
     * 根据参数获取记录条数
     *
     * @param levelDO
     * @return
     */
    public int countListByParam(LevelDO levelDO);

    /**
     * 按条件查询分页结果
     *
     * @param levelDO 查询条件，为空的话new一个空对象
     * @param pageCondition 分页和排序条件，可选
     * @return List<UserDO>
     */
    List<LevelDO> findListByParam(@Param("level") LevelDO levelDO, @Param("pageCondition") PageCondition pageCondition);

    /**
     * 删除所有记录
     *
     * @return
     */
    public int deleteAll();

    /**
     * 批量添加
     *
     * @param levelDOs 等级列表
     * @return
     */
    @Override
    public int batchInsert(List<LevelDO> levelDOs);
}
