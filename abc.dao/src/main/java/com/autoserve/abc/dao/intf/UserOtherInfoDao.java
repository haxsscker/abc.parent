package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.UserOtherInfoDO;

public interface UserOtherInfoDao extends BaseDao<UserOtherInfoDO, Integer> {

    /**
     * 查询用户其他信息
     *
     * @param id 用户id
     * @return UserOtherInfoDO
     */
    public UserOtherInfoDO findByUserId(int id);

}
