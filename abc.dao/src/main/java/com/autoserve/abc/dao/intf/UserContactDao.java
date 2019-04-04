package com.autoserve.abc.dao.intf;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.UserContactDO;

public interface UserContactDao extends BaseDao<UserContactDO, Integer> {

    /**
     * 查询用户联系方式
     *
     * @param id 用户id
     * @return UserContactDO
     */
    public UserContactDO findByUserId(int id);

    /**
     * 有选择更新用户联系人
     *
     * @param userContactDO
     * @return
     */
    public int updateByPrimaryKeySelective(UserContactDO userContactDO);
}
