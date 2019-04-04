package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.UserOwnerDO;

public interface UserOwnerDao extends BaseDao<UserOwnerDO, Integer>{

    /**
     * 查询私营业主信息
     * @param id 用户id
     * @return UserOwnerDO
     */
    public UserOwnerDO findByUserId(int id);
}
