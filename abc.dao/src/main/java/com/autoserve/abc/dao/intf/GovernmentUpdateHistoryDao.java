package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentUpdateHistoryDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author RJQ 2014/11/18 16:39.
 */
public interface GovernmentUpdateHistoryDao extends BaseDao<GovernmentUpdateHistoryDO, Integer> {
    /**
     * 新增机构修改信息，部分字段可为空
     *
     * @param updateHistoryDO 待插入信息
     * @return int
     */
    public int insertSelective(GovernmentUpdateHistoryDO updateHistoryDO);

    /**
     * 找到最后一次更新信息
     *
     * @param govId 机构id
     * @return GovernmentUpdateHistoryDO
     */
    public GovernmentUpdateHistoryDO findLastUpdateHistory(int govId);

    /**
     * 查询最后一次修改信息列表
     *
     * @param govIds 机构id
     * @return
     */
    public List<GovernmentUpdateHistoryDO> findLastUpdateHistoryList(@Param("govIds") List<Integer> govIds,
                                                                     @Param("size") Integer size);

    /**
     * 根据参数获取记录条数
     *
     * @param historyDO
     * @return
     */
    public int countListByParam(@Param("history") GovernmentUpdateHistoryDO historyDO);

    /**
     * 根据参数获取记录条数
     *
     * @param historyDO
     * @param updateStartDate
     * @param updateEndDate
     * @return
     */
    public int countListByMap(@Param("history") GovernmentUpdateHistoryDO historyDO,
                              @Param("updateStartDate") Date updateStartDate,
                              @Param("updateEndDate") Date updateEndDate,
                              @Param("updateEmpName") String updateEmpName);

    /**
     * 按条件查询分页结果
     *
     * @param historyDO     查询条件，为空的话传new GovernmentUpdateHistoryDO()
     * @param pageCondition 分页和排序条件，可选
     * @return List<LoanDO>
     */
    public List<GovernmentUpdateHistoryDO> findListByParam(@Param("history") GovernmentUpdateHistoryDO historyDO,
                                                           @Param("pageCondition") PageCondition pageCondition);

    /**
     * 按条件搜索分页结果
     *
     * @param historyDO
     * @param updateStartDate
     * @param updateEndDate
     * @param pageCondition
     * @return
     */
    public List<GovernmentUpdateHistoryDO> findListByMap(@Param("history") GovernmentUpdateHistoryDO historyDO,
                                                         @Param("updateStartDate") Date updateStartDate,
                                                         @Param("updateEndDate") Date updateEndDate,
                                                         @Param("updateEmpName") String updateEmpName,
                                                         @Param("pageCondition") PageCondition pageCondition);

    /**
     * 更新机构修改记录部分信息
     *
     * @param historyDO 机构修改记录
     * @return int
     */
    public int updateByPrimaryKeySelective(GovernmentUpdateHistoryDO historyDO);
}
