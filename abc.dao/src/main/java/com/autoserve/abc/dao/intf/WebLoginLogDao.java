package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.WebLoginLogDO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebLoginLogDao extends BaseDao<WebLoginLogDO, Integer> {

    int updateOneLoginLogState(@Param("webLogLogDO") WebLoginLogDO webLogLogDO);

    int deleteByIds(@Param("ids") List<Integer> ids);
    
    public WebLoginLogDO findByUserId(@Param("llUserId") Integer llUserId);
}
