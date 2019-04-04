package com.autoserve.abc.service.biz.intf.user;

import com.autoserve.abc.dao.dataobject.UserContactDO;
import com.autoserve.abc.service.biz.entity.UserContact;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * @author RJQ 2014/12/26 9:59.
 */
public interface UserContactService {
    /**
     * 修改用户联系人
     *
     * @param userContactDO
     * @return
     */
    public BaseResult modifyUserContact(UserContactDO userContactDO);

    /**
     * 查询用户联系方式信息
     *
     * @param id 用户id
     * @return PlainResult<UserContact>
     */
    public PlainResult<UserContact> findUserContactByUserId(int id);
    /**
     * 创建用户联系方式信息
     * @param userContactDO
     * @return
     */
    public BaseResult CreateUserContactByUserId(UserContactDO userContactDO);

}
