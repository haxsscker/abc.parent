package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.UserEducationDO;

public interface UserEducationDao extends BaseDao<UserEducationDO, Integer> {

    /**
     * 查询用户教育背景
     *
     * @param id 用户id
     * @return UserEducationDO
     */
    public UserEducationDO findByUserId(int id);
}
