package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.ActHolidayDO;

public interface ActHolidayDao extends BaseDao<ActHolidayDO, Integer> {
    /**
     * 获取该活动的特殊   act_id
     * 
     * @return List<ActPrizeDO>
     */
    List<ActHolidayDO> findAllHoliday(@Param("actId") int actId, @Param("ahDay") String ahDay);
}
