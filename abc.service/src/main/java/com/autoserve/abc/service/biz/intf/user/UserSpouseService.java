package com.autoserve.abc.service.biz.intf.user;

import com.autoserve.abc.service.biz.entity.UserSpouse;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/12/26 21:50.
 */
public interface UserSpouseService {
    /**
     * 查询用户配偶信息
     *
     * @param id 用户id
     * @return PlainResult<UserSpouse>
     */
    public PlainResult<UserSpouse> findUserSpouseByUserId(int id);
    
    /**
     * 修改用户配偶信息
     *
     * @param UserSpouse
     * @param userMarry 
     * @return
     */
    public BaseResult modifyUserSpouse(UserSpouse userSpouse, Integer userMarry);
    /**
     * 创建用户配偶信息
     *
     * @param UserSpouse
     * @param userMarry 
     * @return
     */
    public BaseResult createUserSpouse(UserSpouse userSpouse, Integer userMarry);
}
