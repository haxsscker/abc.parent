package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysConfigDO;

public interface SysConfigDao extends BaseDao<SysConfigDO, Integer> {
    SysConfigDO findByConfigKey(String key);

    int updateByConfigKey(SysConfigDO configDO);

    @Override
	int batchInsert(List<SysConfigDO> list);

    List<SysConfigDO> findListByList(List<String> list);

    List<SysConfigDO> findListWithPrefix(@Param("keyPrefix") String keyPrefix,
                                         @Param("pageCondition") PageCondition pageCondition);

    int countListWithPrefix(@Param("keyPrefix") String keyPrefix);

    int deleteByConfigKey(String configKey);
}
