package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.UserHouseDO;

public interface UserHouseDao extends BaseDao<UserHouseDO, Integer>{

    /**
     * 查询用户房产信息
     * @param id 用户id
     * @return UserHouseDO
     */
    public UserHouseDO findByUserId(int id);
}
