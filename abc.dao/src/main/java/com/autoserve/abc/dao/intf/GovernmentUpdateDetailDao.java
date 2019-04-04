package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.GovernmentUpdateDetailDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GovernmentUpdateDetailDao extends BaseDao<GovernmentUpdateDetailDO, Integer> {

    /**
     * 批量增加机构字段修改明细
     *
     * @param list 机构字段修改明细list
     * @return int
     */
    @Override
	public int batchInsert(List<GovernmentUpdateDetailDO> list);

    /**
     * 按条件查询分页结果
     *
     * @param detailDO      查询条件，为空的话传new GovernmentUpdateDetailDO()
     * @param pageCondition 分页和排序条件，可选
     * @return
     */
    List<GovernmentUpdateDetailDO> findListByParam(@Param("detail") GovernmentUpdateDetailDO detailDO,
                                                   @Param("pageCondition") PageCondition pageCondition);

    /**
     * 根据机构修改记录主键查找该次修改的所有字段信息
     * @param guhId 机构修改记录主键
     * @return
     */
    public List<GovernmentUpdateDetailDO> findListByGovUpdateHistoryId(Integer guhId);
}