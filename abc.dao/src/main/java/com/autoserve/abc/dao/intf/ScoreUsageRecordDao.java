package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordDO;
import com.autoserve.abc.dao.dataobject.ScoreUsageRecordWithNameDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ScoreUsageRecordDao extends BaseDao<ScoreUsageRecordDO, Integer> {
    /**
     * 根据参数获取记录条数
     *
     * @param scoreUsageRecordDO 查询条件可选
     * @return int
     */
    public int countListByParam(@Param("sur") ScoreUsageRecordDO scoreUsageRecordDO);

    /**
     * 搜索用
     *
     * @param record
     * @param scoreStart
     * @param scoreEnd
     * @param toCashStartDate
     * @param toCashEndDate
     * @return
     */
    public int countListByMap(@Param("userName") String userName, @Param("sur") ScoreUsageRecordDO record, @Param("scoreStart") Integer scoreStart,
                              @Param("scoreEnd") Integer scoreEnd, @Param("toCashStartDate") Date toCashStartDate,
                              @Param("toCashEndDate") Date toCashEndDate);

    /**
     * 按条件查询分页结果
     *
     * @param scoreUsageRecordDO 查询条件，为空的话new一个空对象
     * @param pageCondition      分页和排序条件，可选
     * @return List<ScoreUsageRecordDO>
     */
    public List<ScoreUsageRecordWithNameDO> findListByParam(@Param("sur") ScoreUsageRecordDO scoreUsageRecordDO,
                                                            @Param("pageCondition") PageCondition pageCondition);

    public List<ScoreUsageRecordWithNameDO> findListByMap(@Param("userName") String userName, @Param("sur") ScoreUsageRecordDO record, @Param("scoreStart") Integer scoreStart,
                                                          @Param("scoreEnd") Integer scoreEnd, @Param("toCashStartDate") Date toCashStartDate,
                                                          @Param("toCashEndDate") Date toCashEndDate, @Param("pageCondition") PageCondition pageCondition);

    /**
     * 查询积分使用情况
     *
     * @param id 主键
     * @return
     */
    public ScoreUsageRecordWithNameDO findWithNameById(@Param("surId") Integer id);

    /**
     * 根据主键更新积分兑现信息
     *
     * @param recordDO 积分兑现信息
     * @return int
     */
    public int updateByPrimaryKeySelective(ScoreUsageRecordDO recordDO);
}
