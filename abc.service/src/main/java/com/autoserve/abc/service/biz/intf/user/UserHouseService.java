package com.autoserve.abc.service.biz.intf.user;

import com.autoserve.abc.service.biz.entity.UserHouse;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/12/26 21:44.
 */
public interface UserHouseService {
    /**
     * 查询用户房产信息
     *
     * @param id 用户id
     * @return PlainResult<UserHouse>
     */
    public PlainResult<UserHouse> findUserHouseByUserId(int id);
    /**
     * 修改用户房产信息
     *
     * @param userContactDO
     * @return
     */
    public BaseResult modifyUserHouse(UserHouse userHouse);
    /**
     * 创建用户房产信息
     *
     * @param userContactDO
     * @return
     */
    public BaseResult createUserHouse(UserHouse userHouse);
}
