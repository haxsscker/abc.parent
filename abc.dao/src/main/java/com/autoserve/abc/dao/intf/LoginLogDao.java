package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.LoginLogDO;
import com.autoserve.abc.dao.dataobject.LoginLogJDO;
import com.autoserve.abc.dao.dataobject.search.LoginLogSearchDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginLogDao extends BaseDao<LoginLogDO, Integer> {

    List<LoginLogJDO> findListBySearchDO(@Param("searchDO") LoginLogSearchDO searchDO,
                                         @Param("pageCondition") PageCondition pageCondition);

    int countBySearchDO(@Param("searchDO") LoginLogSearchDO searchDO);

    int updateOneLoginLogState(@Param("logLogDO") LoginLogDO logLogDO);

    int deleteByIds(@Param("ids") List<Integer> ids);
}
