package com.autoserve.abc.service.biz.intf.user;

import com.autoserve.abc.service.biz.entity.UserOtherInfo;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/12/26 21:54.
 */
public interface UserOtherInfoService {

    /**
     * 查询用户其他信息
     *
     * @param id 用户id
     * @return PlainResult<UserOtherInfo>
     */
    public PlainResult<UserOtherInfo> findUserOtherInfoByUserId(int id);
}
