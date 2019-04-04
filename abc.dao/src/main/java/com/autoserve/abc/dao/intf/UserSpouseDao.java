package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.UserSpouseDO;

public interface UserSpouseDao extends BaseDao<UserSpouseDO, Integer>{

    /**
     * 查询用户配偶信息
     * @param id 用户id
     * @return UserSpouseDO
     */
    public UserSpouseDO findByUserId(int id);
}
