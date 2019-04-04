package com.autoserve.abc.service.biz.intf.user;

import com.autoserve.abc.service.biz.entity.UserOwner;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/12/26 21:48.
 */
public interface UserOwnerService {

    /**
     * 查询私营业主信息
     *
     * @param id 用户id
     * @return PlainResult<UserOwner>
     */
    public PlainResult<UserOwner> findUserOwnerByUserId(int id);
    
    /**
     * 修改私营业主信息
     *
     * @param UserOwner
     * @return
     */
    public BaseResult modifyUserOwner(UserOwner userOwner);
    /**
     * 创建私营业主信息
     *
     * @param UserOwner
     * @return
     */
    public BaseResult createUserOwner(UserOwner userOwner);
}
